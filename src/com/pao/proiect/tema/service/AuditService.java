package com.pao.proiect.tema.service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.locks.ReentrantLock;

public final class AuditService {

    private static AuditService instance;

    private static final String AUDIT_FILE = "audit.csv";
    private final ReentrantLock lock = new ReentrantLock();

    private final DateTimeFormatter formatter =
            DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

    private AuditService() {
        try {
            File file = new File(AUDIT_FILE);
            if (!file.exists()) {
                try (PrintWriter pw = new PrintWriter(new FileWriter(file))) {
                    pw.println("nume_actiune,timestamp");
                }
            }
        } catch (IOException e) {
            System.err.println("[AUDIT] Error initializing audit file: " + e.getMessage());
        }
    }

    public static AuditService getInstance() {
        if (instance == null) {
            instance = new AuditService();
        }
        return instance;
    }

    public void log(String actionName) {
        lock.lock();
        try (PrintWriter pw = new PrintWriter(
                new FileWriter(AUDIT_FILE, true))) {
            String timestamp = LocalDateTime.now().format(formatter);
            pw.println(actionName + "," + timestamp);
        } catch (IOException e) {
            System.err.println("[AUDIT] Writing error: " + e.getMessage());
        } finally {
            lock.unlock();
        }
    }
}
