package duomenys;

import modelis.Administratorius;
import modelis.Augintinis;
import modelis.PaslauguImone;
import modelis.Priziuretojas;
import modelis.Savininkas;
import modelis.Vadovas;

import java.util.ArrayList;
import java.util.List;

// "Laikina duomenu baze": static sarasai islieka tarp skirtingu langu
public class DuomenuBaze {
    private static final List<Administratorius> administratoriai = new ArrayList<>();
    private static final List<PaslauguImone> imones = new ArrayList<>();
    private static final List<Vadovas> vadovai = new ArrayList<>();
    private static final List<Priziuretojas> priziuretojai = new ArrayList<>();
    private static final List<Savininkas> savininkai = new ArrayList<>();
    private static final List<Augintinis> augintiniai = new ArrayList<>();

    // Static blokas ivykdomas VIENA karta, kai programa pirma karta panaudoja DuomenuBaze.
    // Kol nera tikros duomenu bazes, cia sukuriame pradinius duomenis.
    static {
        // --- Administratorius ---
        administratoriai.add(new Administratorius("Admin", "Adminas", "admin@sistema.lt",
                "+37060000000", "admin123"));

        // --- Imones ---
        PaslauguImone letos = new PaslauguImone("Laimingos Letos", "Gedimino pr. 1", "Vilnius");
        PaslauguImone uodegos = new PaslauguImone("Linksmos Uodegos", "Laisvės al. 10", "Kaunas");
        imones.add(letos);
        imones.add(uodegos);

        // --- Vadovai (po viena kiekvienai imonei) ---
        Vadovas ona = new Vadovas("Ona", "Onaitė", "ona@letos.lt", "+37061111111", "ona123");
        letos.pridetiVadova(ona);
        vadovai.add(ona);

        Vadovas petras = new Vadovas("Petras", "Petraitis", "petras@uodegos.lt", "+37061111112", "petras123");
        uodegos.pridetiVadova(petras);
        vadovai.add(petras);

        // --- Priziuretojai ---
        Priziuretojas tomas = new Priziuretojas("Tomas", "Tomaitis", "tomas@letos.lt",
                "+37062222222", "tomas123", "Patirtis su dideliais šunimis");
        letos.pridetiPriziuretoja(tomas);
        priziuretojai.add(tomas);

        Priziuretojas ieva = new Priziuretojas("Ieva", "Ievaitė", "ieva@letos.lt",
                "+37063333333", "ieva123", "Kačių priežiūros specialistė");
        letos.pridetiPriziuretoja(ieva);
        priziuretojai.add(ieva);

        Priziuretojas lukas = new Priziuretojas("Lukas", "Lukaitis", "lukas@uodegos.lt",
                "+37064444444", "lukas123", "Triušių ir paukščių priežiūra");
        uodegos.pridetiPriziuretoja(lukas);
        priziuretojai.add(lukas);

        // --- Savininkai ir ju augintiniai ---
        Savininkas jonas = new Savininkas("Jonas", "Jonaitis", "jonas@gmail.com", "+37065555555", "jonas123");
        savininkai.add(jonas);
        Augintinis reksas = new Augintinis("Reksas", "Šuo", "Vokiečių aviganis",
                Augintinis.nustatytiDydi("Šuo", 35), 3);
        Augintinis murka = new Augintinis("Murka", "Katė", "Britų",
                Augintinis.nustatytiDydi("Katė", 3.5), 5);
        jonas.registruotiAugintini(reksas);
        jonas.registruotiAugintini(murka);
        augintiniai.add(reksas);
        augintiniai.add(murka);

        Savininkas rasa = new Savininkas("Rasa", "Rasaitė", "rasa@gmail.com", "+37066666666", "rasa123");
        savininkai.add(rasa);
        Augintinis kakadu = new Augintinis("Kiki", "Paukštis", "Kakadu",
                Augintinis.nustatytiDydi("Paukštis", 0.8), 12);
        rasa.registruotiAugintini(kakadu);
        augintiniai.add(kakadu);
        petras.setArUzblokuotas(true); // TESTUI: Petras uzblokuotas
    }

    public static List<Administratorius> getAdministratoriai() {
        return administratoriai;
    }

    public static List<PaslauguImone> getImones() {
        return imones;
    }

    public static List<Vadovas> getVadovai() {
        return vadovai;
    }

    public static List<Priziuretojas> getPriziuretojai() {
        return priziuretojai;
    }

    public static List<Savininkas> getSavininkai() {
        return savininkai;
    }

    public static List<Augintinis> getAugintiniai() {
        return augintiniai;
    }
}