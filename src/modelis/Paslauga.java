package modelis;

import java.util.ArrayList;
import java.util.List;

public class Paslauga {
    private String pavadinimas;
    private String tipas;
    private double kaina;
    private int trukmeMin;
    private String teikimoVieta;
    private String leidziamosRusys;
    private String leidziamasDydis;
    private boolean arAktyvi;
    private int populiarumas;
    private PaslauguImone imone;               // Rysys "1"
    private List<Priziuretojas> priziuretojai; // Rysys "1..*" (daug su daug)
    private List<Rezervacija> rezervacijos;     // Rysys "0..*"

    public Paslauga(String pavadinimas, String tipas, double kaina, int trukmeMin,
                    String teikimoVieta, String leidziamosRusys, String leidziamasDydis) {
        this.pavadinimas = pavadinimas;
        this.tipas = tipas;
        this.kaina = kaina;
        this.trukmeMin = trukmeMin;
        this.teikimoVieta = teikimoVieta;
        this.leidziamosRusys = leidziamosRusys;
        this.leidziamasDydis = leidziamasDydis;
        this.arAktyvi = true;   // Nauja paslauga is karto aktyvi
        this.populiarumas = 0;  // Dar niekas neuzsake
        this.priziuretojai = new ArrayList<>();
        this.rezervacijos = new ArrayList<>();
    }

    // Tikrina, ar paslauga tinka konkreciam augintiniui (pagal rusi ir dydi)
    public boolean arTinkaAugintiniui(Augintinis augintinis) {
        boolean rusisTinka = leidziamosRusys.contains(augintinis.getRusis());
        boolean dydisTinka = leidziamasDydis.equals("Visi") || leidziamasDydis.contains(augintinis.getDydis());
        return rusisTinka && dydisTinka;
    }

    public String gautiInformacija() {
        return "Paslauga: " + pavadinimas + " (" + tipas + ") | Kaina: " + kaina + " EUR | Trukmė: "
                + trukmeMin + " min | Vieta: " + teikimoVieta + " | Aktyvi: " + (arAktyvi ? "Taip" : "Ne");
    }

    // Getteriai ir Setteriai
    public String getPavadinimas() {
        return pavadinimas;
    }

    public void setPavadinimas(String pavadinimas) {
        this.pavadinimas = pavadinimas;
    }

    public String getTipas() {
        return tipas;
    }

    public void setTipas(String tipas) {
        this.tipas = tipas;
    }

    public double getKaina() {
        return kaina;
    }

    public void setKaina(double kaina) {
        this.kaina = kaina;
    }

    public int getTrukmeMin() {
        return trukmeMin;
    }

    public void setTrukmeMin(int trukmeMin) {
        this.trukmeMin = trukmeMin;
    }

    public String getTeikimoVieta() {
        return teikimoVieta;
    }

    public void setTeikimoVieta(String teikimoVieta) {
        this.teikimoVieta = teikimoVieta;
    }

    public String getLeidziamosRusys() {
        return leidziamosRusys;
    }

    public void setLeidziamosRusys(String leidziamosRusys) {
        this.leidziamosRusys = leidziamosRusys;
    }

    public String getLeidziamasDydis() {
        return leidziamasDydis;
    }

    public void setLeidziamasDydis(String leidziamasDydis) {
        this.leidziamasDydis = leidziamasDydis;
    }

    public boolean isArAktyvi() {
        return arAktyvi;
    }

    public void setArAktyvi(boolean arAktyvi) {
        this.arAktyvi = arAktyvi;
    }

    public int getPopuliarumas() {
        return populiarumas;
    }

    public void setPopuliarumas(int populiarumas) {
        this.populiarumas = populiarumas;
    }

    public PaslauguImone getImone() {
        return imone;
    }

    public void setImone(PaslauguImone imone) {
        this.imone = imone;
    }

    public List<Priziuretojas> getPriziuretojai() {
        return priziuretojai;
    }

    public List<Rezervacija> getRezervacijos() {
        return rezervacijos;
    }
}
