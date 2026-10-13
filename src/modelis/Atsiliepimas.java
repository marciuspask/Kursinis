package modelis;

import java.time.LocalDateTime;

public class Atsiliepimas {
    private int ivertinimas;
    private String komentaras;
    private LocalDateTime sukurimoData;
    private boolean arAktyvus;
    private String tipas;                // Ka vertina: "Priziuretojas", "Imone" arba "Klientas"
    private int gyvunoElgesys;           // 1-5, tik kai priziuretojas vertina klienta (0 = nevertinta)
    private Rezervacija rezervacija;     // Rysys "1"
    private Savininkas savininkas;       // Rysys "0..1" (autorius)
    private Priziuretojas priziuretojas; // Rysys "0..1" (autorius)

    public Atsiliepimas(int ivertinimas, String komentaras, Rezervacija rezervacija, String tipas) {
        this.ivertinimas = ivertinimas;
        this.komentaras = komentaras;
        this.rezervacija = rezervacija;
        this.tipas = tipas;
        this.gyvunoElgesys = 0;
        this.sukurimoData = LocalDateTime.now().withSecond(0).withNano(0);
        this.arAktyvus = true;
    }

    public String gautiAutoriausVarda() {
        if (savininkas != null) {
            return savininkas.getVardas() + " (savininkas)";
        }
        if (priziuretojas != null) {
            return priziuretojas.getVardas() + " (prižiūrėtojas)";
        }
        return "Nežinomas";
    }

    public String gautiInformacija() {
        return gautiAutoriausVarda() + " -> vertina: " + tipas + " | " + ivertinimas + "/5"
                + (gyvunoElgesys > 0 ? " | Gyvūno elgesys: " + gyvunoElgesys + "/5" : "")
                + " | " + komentaras
                + (arAktyvus ? "" : " [PASLĖPTAS]");
    }

    // Getteriai ir Setteriai
    public int getIvertinimas() {
        return ivertinimas;
    }

    public String getKomentaras() {
        return komentaras;
    }

    public LocalDateTime getSukurimoData() {
        return sukurimoData;
    }

    public boolean isArAktyvus() {
        return arAktyvus;
    }

    public void setArAktyvus(boolean arAktyvus) {
        this.arAktyvus = arAktyvus;
    }

    public String getTipas() {
        return tipas;
    }

    public int getGyvunoElgesys() {
        return gyvunoElgesys;
    }

    public void setGyvunoElgesys(int gyvunoElgesys) {
        this.gyvunoElgesys = gyvunoElgesys;
    }

    public Rezervacija getRezervacija() {
        return rezervacija;
    }

    public Savininkas getSavininkas() {
        return savininkas;
    }

    public void setSavininkas(Savininkas savininkas) {
        this.savininkas = savininkas;
    }

    public Priziuretojas getPriziuretojas() {
        return priziuretojas;
    }

    public void setPriziuretojas(Priziuretojas priziuretojas) {
        this.priziuretojas = priziuretojas;
    }
}