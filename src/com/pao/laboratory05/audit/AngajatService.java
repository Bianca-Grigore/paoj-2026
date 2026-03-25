package com.pao.laboratory05.audit;
import java.time.LocalDateTime;
import java.util.Arrays;

public class AngajatService {
    private Angajat[] angajati;
    private AuditEntry[] auditLog;

    private AngajatService() {
        this.angajati = new Angajat[0];
        this.auditLog = new AuditEntry[0];
    }

    private static class Holder {
        private static final AngajatService INSTANCE = new AngajatService();
    }

    public static AngajatService getInstance(){
        return AngajatService.Holder.INSTANCE;
    }

    public void addAngajat(Angajat a){
        System.arraycopy(angajati, 0, angajati = new Angajat[angajati.length + 1], 0, angajati.length - 1);
        angajati[angajati.length - 1] = a;
        System.out.println("Angajat adaugat: " + a.getNume());
        logAction("ADD", a.getNume());
    }

    public void printAll(){
        for (Angajat a : angajati){
            System.out.println(a.getNume());
        }
    }
    public void listBySalary(){
        Angajat[] copy = angajati.clone();
        Arrays.sort(copy, (a1, a2) -> Double.compare(a2.getSalariu(), a1.getSalariu()));
        for (Angajat a : copy){
            System.out.println(a.getNume());
        }

    }

    public void findByDepartament(String numeDept){
        boolean gasit = false;
        logAction("FIND_BY_DEPT", numeDept);

        for(Angajat angajat : angajati ){
            if (angajat.getDepartament().nume().equalsIgnoreCase(numeDept)){
                System.out.println(angajat.getNume());
                gasit = true;
            }
        }
        if(!gasit)
            System.out.println("Niciun angajat in departamentul " + numeDept);

    }

    private void logAction(String action, String target){
        AuditEntry auditNou = new AuditEntry(action, target, LocalDateTime.now().toString());
        AuditEntry[] aux = new AuditEntry[auditLog.length + 1];
        System.arraycopy(auditLog, 0, aux, 0, auditLog.length);
        aux[aux.length-1] = auditNou;
        auditLog = aux;
    }
    public void printAuditLog(){
        for (AuditEntry e : auditLog){
            System.out.println(e);
        }
    }
}
