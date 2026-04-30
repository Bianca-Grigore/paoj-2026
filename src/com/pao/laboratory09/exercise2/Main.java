package com.pao.laboratory09.exercise2;

import com.pao.laboratory09.exercise1.TipTranzactie;
import com.pao.laboratory09.exercise1.Tranzactie;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class Main {
    private static final String OUTPUT_FILE = "output/lab09_ex2.bin";
    private static final int RECORD_SIZE = 32;

    public static void main(String[] args) throws Exception {
        // TODO: Implementează conform Readme.md
        //
        // 1. Citește N din stdin, apoi cele N tranzacții (id suma data tip)
        // 2. Scrie toate înregistrările în OUTPUT_FILE cu DataOutputStream (format binar, RECORD_SIZE=32 bytes/înreg.)
        //    - bytes 0-3:   id (int, little-endian via ByteBuffer)
        //    - bytes 4-11:  suma (double, little-endian via ByteBuffer)
        //    - bytes 12-21: data (String, 10 chars ASCII, paddat cu spații la dreapta)
        //    - byte 22:     tip (0=CREDIT, 1=DEBIT)
        //    - byte 23:     status (0=PENDING, 1=PROCESSED, 2=REJECTED)
        //    - bytes 24-31: padding (zerouri)
        // 3. Procesează comenzile din stdin până la EOF cu RandomAccessFile:
        //    - READ idx       → seek(idx * RECORD_SIZE), citește și afișează înregistrarea
        //    - UPDATE idx ST  → seek(idx * RECORD_SIZE + 23), scrie noul status (0/1/2)
        //                       afișează "Updated [idx]: STATUS"
        //    - PRINT_ALL      → citește și afișează toate înregistrările
        //
        // Format linie output:
        //   [idx] id=<id> data=<data> tip=<CREDIT|DEBIT> suma=<suma:.2f> RON status=<STATUS>

        Scanner scanner = new Scanner(System.in).useLocale(Locale.US);
        int N = Integer.parseInt(scanner.nextLine());

        try(DataOutputStream dos = new DataOutputStream(new FileOutputStream(OUTPUT_FILE))){
            for(int i=0; i<N; i++){
                int id = scanner.nextInt();
                double suma = scanner.nextDouble();
                String data = scanner.nextLine();
                String tipInput = scanner.next();
                byte tip = (byte) (tipInput.equals("1") || tipInput.equalsIgnoreCase("DEBIT") ? 1 : 0);
                byte status = 0;
                byte[] idBytes = ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN).putInt(id).array();
                dos.write(idBytes);
                byte[] sumaBytes = ByteBuffer.allocate(8).order(ByteOrder.LITTLE_ENDIAN).putDouble(suma).array();
                dos.write(sumaBytes);
                String paddedData = String.format("%-10s", data);
                if (paddedData.length() > 10) {
                    paddedData = paddedData.substring(0, 10);
                }
                dos.write(paddedData.getBytes(StandardCharsets.US_ASCII));
                dos.writeByte(tip);
                dos.writeByte(status);
                dos.write(new byte[8]);
            }

        }catch (IOException e){
            System.err.println("Eroare la scrierea fișierului: " + e.getMessage());
        }
        try (RandomAccessFile raf = new RandomAccessFile(OUTPUT_FILE, "rw")) {
            while (scanner.hasNext()) {
                String cmd = scanner.next();

                switch (cmd) {
                    case "READ": {
                        int idx = scanner.nextInt();
                        printRecord(raf, idx);
                        break;
                    }
                    case "UPDATE": {
                        int idx = scanner.nextInt();
                        int st = scanner.nextInt();

                        raf.seek(idx * RECORD_SIZE + 23L);
                        raf.writeByte(st);
                        System.out.println("Updated [" + idx + "]: " + getStatusString(st));
                        break;
                    }
                    case "PRINT_ALL": {

                        long count = raf.length() / RECORD_SIZE;
                        for (int i = 0; i < count; i++) {
                            printRecord(raf, i);
                        }
                        break;
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Eroare la operarea fișierului: " + e.getMessage());
        }
    }

    private static void printRecord(RandomAccessFile raf, int idx) throws IOException {
        raf.seek(idx * (long) RECORD_SIZE);

        byte[] buffer = new byte[RECORD_SIZE];
        raf.readFully(buffer);

        ByteBuffer bb = ByteBuffer.wrap(buffer).order(ByteOrder.LITTLE_ENDIAN);

        int id = bb.getInt(0);
        double suma = bb.getDouble(4);

        String data = new String(buffer, 12, 10, StandardCharsets.US_ASCII).trim();

        byte tip = buffer[22];
        byte status = buffer[23];

        System.out.printf(Locale.US, "[%d] id=%d data=%s tip=%s suma=%.2f RON status=%s%n",
                idx, id, data, getTipString(tip), suma, getStatusString(status));
    }

    private static String getTipString(int tip) {
        return tip == 1 ? "DEBIT" : "CREDIT";
    }

    private static String getStatusString(int status) {
        switch (status) {
            case 1: return "PROCESSED";
            case 2: return "REJECTED";
            default: return "PENDING";
        }
    }
}
