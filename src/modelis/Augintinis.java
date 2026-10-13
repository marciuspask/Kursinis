package modelis;

import java.util.ArrayList;
import java.util.List;
public class Augintinis {
    private String vardas;
    private String rusis;
    private String veisle;
    private String dydis;
    private int amzius;
    private Savininkas savininkas; // Rysys "1" (augintinis turi 1 savininka)
    private List<Rezervacija> rezervacijos; // Rysys "0..*"

    public Augintinis(String vardas, String rusis, String veisle, String dydis, int amzius) {
        this.vardas = vardas;
        this.rusis = rusis;
        this.veisle = veisle;
        this.dydis = dydis;
        this.amzius = amzius;
        this.rezervacijos = new ArrayList<>();
    }

    public String gautiInformacija() {
        return "Augintinis: " + vardas + " | Rūšis: " + rusis + " | Veislė: " + veisle
                + " | Dydis: " + dydis + " | Amžius: " + amzius + " m.";
    }

    // Pagal rusi ir svori nustato dydzio kategorija
    public static String nustatytiDydi(String rusis, double svoris) {
        double mazoRiba;
        double vidutinioRiba;

        switch (rusis) {
            case "Šuo":
                mazoRiba = 10;
                vidutinioRiba = 25;
                break;
            case "Katė":
                mazoRiba = 4;
                vidutinioRiba = 6;
                break;
            case "Triušis":
                mazoRiba = 1.5;
                vidutinioRiba = 3;
                break;
            case "Paukštis":
                mazoRiba = 0.1;
                vidutinioRiba = 0.5;
                break;
            default:
                return "Vidutinis"; // Nezinoma rusis
        }

        if (svoris < mazoRiba) {
            return "Mažas";
        } else if (svoris < vidutinioRiba) {
            return "Vidutinis";
        } else {
            return "Didelis";
        }
    }

    // Getteriai ir Setteriai
    public String getVardas() {
        return vardas;
    }
    public String getRusis() {
        return rusis;
    }
    public String getVeisle() {
        return veisle;
    }
    public String getDydis() {
        return dydis;
    }
    public int getAmzius() {
        return amzius;
    }

    public Savininkas getSavininkas() { return savininkas; }
    public void setSavininkas(Savininkas savininkas) { this.savininkas = savininkas; }

    public List<Rezervacija> getRezervacijos() {
        return rezervacijos;
    }
}