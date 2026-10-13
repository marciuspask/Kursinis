package modelis;

import java.util.List;

public class Vadovas extends Vartotojas {
    private PaslauguImone imone; // Rysys "1"

    public Vadovas(String vardas, String pavarde, String elPastas, String telefonas, String slaptazodis) {
        super(vardas, pavarde, elPastas, telefonas, slaptazodis);
    }

    // Vadovas keicia savo imones priziuretojo darbo laika
    public void nustatytiDarboLaika(Priziuretojas priziuretojas, String darboDienos, String darboValandos) {
        if (priziuretojas.getImone() != this.imone) {
            System.out.println("KLAIDA: " + priziuretojas.getVardas() + " nedirba jūsų įmonėje!");
            return;
        }
        if (priziuretojas.getDarboGrafikas() == null) {
            System.out.println("KLAIDA: " + priziuretojas.getVardas() + " dar neturi darbo grafiko!");
            return;
        }
        priziuretojas.getDarboGrafikas().setDarboDienos(darboDienos);
        priziuretojas.getDarboGrafikas().setDarboValandos(darboValandos);
        System.out.println("Vadovas " + vardas + " pakeitė " + priziuretojas.getVardas()
                + " darbo laiką: " + darboDienos + ", " + darboValandos);
    }

    // Pagalbinis metodas: ar paslauga priklauso vadovo imonei
    private boolean arMano(Paslauga paslauga) {
        if (paslauga.getImone() != this.imone) {
            System.out.println("KLAIDA: paslauga " + paslauga.getPavadinimas() + " nepriklauso jūsų įmonei!");
            return false;
        }
        return true;
    }

    public Paslauga sukurtiPaslauga(String pavadinimas, String tipas, double kaina, int trukmeMin,
                                    String teikimoVieta, String leidziamosRusys, String leidziamasDydis) {
        Paslauga paslauga = new Paslauga(pavadinimas, tipas, kaina, trukmeMin,
                teikimoVieta, leidziamosRusys, leidziamasDydis);
        imone.pridetiPaslauga(paslauga);
        System.out.println("Vadovas " + vardas + " sukūrė paslaugą: " + pavadinimas);
        return paslauga;
    }

    public void redaguotiPaslauga(Paslauga paslauga, String naujasPavadinimas, int naujaTrukme, String naujaVieta) {
        if (!arMano(paslauga)) {
            return;
        }
        paslauga.setPavadinimas(naujasPavadinimas);
        paslauga.setTrukmeMin(naujaTrukme);
        paslauga.setTeikimoVieta(naujaVieta);
        System.out.println("Paslauga atnaujinta: " + paslauga.gautiInformacija());
    }

    public void keistiKaina(Paslauga paslauga, double naujaKaina) {
        if (!arMano(paslauga)) {
            return;
        }
        if (naujaKaina <= 0) {
            System.out.println("KLAIDA: kaina turi būti didesnė už 0!");
            return;
        }
        double senaKaina = paslauga.getKaina();
        paslauga.setKaina(naujaKaina);
        System.out.println("Paslaugos " + paslauga.getPavadinimas() + " kaina pakeista: "
                + senaKaina + " -> " + naujaKaina + " EUR");
    }

    public void priskirtiPriziuretojus(Paslauga paslauga, List<Priziuretojas> priziuretojai) {
        if (!arMano(paslauga)) {
            return;
        }
        for (Priziuretojas p : priziuretojai) {
            if (p.getImone() != this.imone) {
                System.out.println("KLAIDA: " + p.getVardas() + " nedirba jūsų įmonėje!");
                continue;
            }
            if (!paslauga.getPriziuretojai().contains(p)) {
                paslauga.getPriziuretojai().add(p); // Abipusis rysys (daug su daug)
                p.getPaslaugos().add(paslauga);
                System.out.println("Prižiūrėtojas " + p.getVardas() + " priskirtas paslaugai " + paslauga.getPavadinimas());
            }
        }
    }

    public void pazymetiNeaktyvia(Paslauga paslauga) {
        if (!arMano(paslauga)) {
            return;
        }
        paslauga.setArAktyvi(false);
        System.out.println("Paslauga " + paslauga.getPavadinimas() + " pažymėta kaip NEAKTYVI");
    }

    public void perziuretiRezervacijuIstorija() {
        System.out.println("Įmonės " + imone.getPavadinimas() + " rezervacijų istorija:");
        int kiekis = 0;
        for (Paslauga p : imone.getPaslaugos()) {
            for (Rezervacija r : p.getRezervacijos()) {
                System.out.println("  " + r.gautiInformacija());
                kiekis++;
            }
        }
        System.out.println("Iš viso rezervacijų: " + kiekis);
    }

    public void nustatytiLeidziamusGyvunus(Paslauga paslauga, String leidziamosRusys, String leidziamasDydis) {
        if (!arMano(paslauga)) {
            return;
        }
        paslauga.setLeidziamosRusys(leidziamosRusys);
        paslauga.setLeidziamasDydis(leidziamasDydis);
        System.out.println("Paslaugai " + paslauga.getPavadinimas() + " leidžiami gyvūnai: "
                + leidziamosRusys + " | Dydis: " + leidziamasDydis);
    }

    // Paslauga su rezervacijomis nepasalinama, o tik pazymima neaktyvia
    public void salintiPaslauga(Paslauga paslauga) {
        if (!arMano(paslauga)) {
            return;
        }
        if (!paslauga.getRezervacijos().isEmpty()) {
            paslauga.setArAktyvi(false);
            System.out.println("Paslauga " + paslauga.getPavadinimas() + " turi " + paslauga.getRezervacijos().size()
                    + " rezervacija(-u), todėl nepašalinta, o pažymėta NEAKTYVIA");
            return;
        }
        for (Priziuretojas p : paslauga.getPriziuretojai()) {
            p.getPaslaugos().remove(paslauga);
        }
        paslauga.getPriziuretojai().clear();
        imone.getPaslaugos().remove(paslauga);
        System.out.println("Paslauga " + paslauga.getPavadinimas() + " PAŠALINTA");
    }

    // Metodu perkrovimas: vienos paslaugos rezervaciju istorija
    public void perziuretiRezervacijuIstorija(Paslauga paslauga) {
        if (!arMano(paslauga)) {
            return;
        }
        System.out.println("Paslaugos " + paslauga.getPavadinimas() + " rezervacijų istorija ("
                + paslauga.getRezervacijos().size() + "):");
        for (Rezervacija r : paslauga.getRezervacijos()) {
            System.out.println("  " + r.gautiInformacija());
        }
    }

    // Getteriai ir Setteriai

    public PaslauguImone getImone() {
        return imone;
    }

    public void setImone(PaslauguImone imone) {
        this.imone = imone;
    }

    @Override
    public String getRole() {
        return "Vadovas";
    }
}
