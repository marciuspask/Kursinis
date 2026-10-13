package modelis;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Komentaras {
    private String tekstas;
    private LocalDateTime sukurimoData;
    private boolean arRedaguotas;
    private Priziuretojas priziuretojas;     // Rysys "1" (kurio profilyje komentaras)
    private Savininkas autorius;             // Rysys "0..1" (null = rase pats priziuretojas)
    private Komentaras tevinisKomentaras;    // Rysys "0..1" (i kuri atsakoma)
    private List<Komentaras> atsakymai;      // Rysys "0..*" (atsakymai i si komentara)

    public Komentaras(String tekstas, Priziuretojas priziuretojas, Savininkas autorius) {
        this.tekstas = tekstas;
        this.priziuretojas = priziuretojas;
        this.autorius = autorius;
        this.sukurimoData = LocalDateTime.now().withSecond(0).withNano(0);
        this.arRedaguotas = false;
        this.tevinisKomentaras = null;
        this.atsakymai = new ArrayList<>();
    }

    public void pridetiAtsakyma(Komentaras atsakymas) {
        atsakymas.tevinisKomentaras = this; // Abipusis rysys
        this.atsakymai.add(atsakymas);
    }

    // Redaguoti gali tik komentaro autorius
    public void redaguoti(Savininkas redaguotojas, String naujasTekstas) {
        if (autorius != redaguotojas) {
            System.out.println("KLAIDA: tik komentaro autorius gali jį redaguoti!");
            return;
        }
        atnaujintiTeksta(naujasTekstas);
    }

    public void redaguoti(Priziuretojas redaguotojas, String naujasTekstas) {
        if (autorius != null || priziuretojas != redaguotojas) {
            System.out.println("KLAIDA: tik komentaro autorius gali jį redaguoti!");
            return;
        }
        atnaujintiTeksta(naujasTekstas);
    }

    private void atnaujintiTeksta(String naujasTekstas) {
        this.tekstas = naujasTekstas;
        this.arRedaguotas = true;
        System.out.println("Komentaras paredaguotas: " + naujasTekstas);
    }

    public String gautiAutoriausVarda() {
        if (autorius != null) {
            return autorius.getVardas();
        }
        return priziuretojas.getVardas() + " (prižiūrėtojas)";
    }

    // Rekursija: atspausdina komentara ir visus jo atsakymus su atitraukimu
    public void spausdinti(int gylis) {
        String tarpai = "    ".repeat(gylis);
        String rodykle = (gylis > 0) ? "-> " : "";
        System.out.println(tarpai + rodykle + gautiAutoriausVarda() + ": " + tekstas
                + (arRedaguotas ? " (redaguotas)" : ""));
        for (Komentaras atsakymas : atsakymai) {
            atsakymas.spausdinti(gylis + 1);
        }
    }

    // Getteriai ir Setteriai
    public String getTekstas() {
        return tekstas;
    }

    public LocalDateTime getSukurimoData() {
        return sukurimoData;
    }

    public boolean isArRedaguotas() {
        return arRedaguotas;
    }

    public Priziuretojas getPriziuretojas() {
        return priziuretojas;
    }

    public Savininkas getAutorius() {
        return autorius;
    }

    public Komentaras getTevinisKomentaras() {
        return tevinisKomentaras;
    }

    public List<Komentaras> getAtsakymai() {
        return atsakymai;
    }
}
