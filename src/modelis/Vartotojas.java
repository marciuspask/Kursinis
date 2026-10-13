package modelis;

import java.time.LocalDateTime;

// Bendra visu vartotoju tevine klase. "abstract" - tiesiogiai kurti negalima.
public abstract class Vartotojas {
    protected String vardas;
    protected String pavarde;
    protected String elPastas;
    protected String telefonas;
    private String slaptazodis;
    protected boolean arUzblokuotas;
    protected LocalDateTime sukurimoData;

    public Vartotojas(String vardas, String pavarde, String elPastas, String telefonas, String slaptazodis) {
        this.vardas = vardas;
        this.pavarde = pavarde;
        this.elPastas = elPastas;
        this.telefonas = telefonas;
        this.slaptazodis = slaptazodis;
        this.arUzblokuotas = false;
        this.sukurimoData = LocalDateTime.now().withNano(0);
    }

    public boolean tikrintiSlaptazodi(String ivestas) {
        return slaptazodis.equals(ivestas);
    }

    // Kiekvienas vaikas PRIVALO pasakyti savo role
    public abstract String getRole();

    @Override
    public String toString() {
        return vardas + " " + pavarde;
    }

    // Getteriai ir Setteriai

    public String getVardas() {
        return vardas;
    }

    public void setVardas(String vardas) {
        this.vardas = vardas;
    }

    public String getPavarde() {
        return pavarde;
    }

    public void setPavarde(String pavarde) {
        this.pavarde = pavarde;
    }

    public String getElPastas() {
        return elPastas;
    }

    public void setElPastas(String elPastas) {
        this.elPastas = elPastas;
    }

    public String getTelefonas() {
        return telefonas;
    }

    public void setTelefonas(String telefonas) {
        this.telefonas = telefonas;
    }

    public String getSlaptazodis() {
        return slaptazodis;
    }

    public void setSlaptazodis(String slaptazodis) {
        this.slaptazodis = slaptazodis;
    }

    public boolean isArUzblokuotas() {
        return arUzblokuotas;
    }

    public void setArUzblokuotas(boolean arUzblokuotas) {
        this.arUzblokuotas = arUzblokuotas;
    }

    public LocalDateTime getSukurimoData() {
        return sukurimoData;
    }

    public void setSukurimoData(LocalDateTime sukurimoData) {
        this.sukurimoData = sukurimoData;
    }
}