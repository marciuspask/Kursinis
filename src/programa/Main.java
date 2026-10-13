package programa;

import modelis.Administratorius;
import modelis.Augintinis;
import modelis.DarboGrafikas;
import modelis.Komentaras;
import modelis.Paslauga;
import modelis.PaslaugosVykdymas;
import modelis.PaslauguImone;
import modelis.Priziuretojas;
import modelis.Rezervacija;
import modelis.Savininkas;
import modelis.Vadovas;
import modelis.Zinute;

import java.util.List;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== AUGINTINIŲ PRIEŽIŪROS SISTEMA ===\n");

        Savininkas savininkas1 = new Savininkas("Jonas", "Jonaitis", "jonas@gmail.com", "+37060000000", "slaptas123");

        Augintinis augintinis1 = new Augintinis("Reksas", "Šuo", "Vokiečių aviganis", "Didelis", 3);
        Augintinis augintinis2 = new Augintinis("Murka", "Katė", "Britų", "Mažas", 5);

        savininkas1.registruotiAugintini(augintinis1);
        savininkas1.registruotiAugintini(augintinis2);

        System.out.println("\n--- Augintiniai ---");
        for (Augintinis a : savininkas1.getAugintiniai()) {
            System.out.println(a.gautiInformacija());
            System.out.println("Savininkas: " + a.getSavininkas().getVardas());
        }
        // --- 2 ZINGSNIS: Imone, vadovas, priziuretojai ---
        System.out.println("\n--- Įmonės kūrimas ---");
        PaslauguImone imone1 = new PaslauguImone("Laimingos Letos", "Gedimino pr. 1", "Vilnius");

        Vadovas vadovas1 = new Vadovas("Ona", "Onaitė", "ona@letos.lt", "+37061111111", "vadove123");
        imone1.pridetiVadova(vadovas1);

        Priziuretojas priziuretojas1 = new Priziuretojas("Tomas", "Tomaitis", "tomas@letos.lt",
                "+37062222222", "tomas123", "Patirtis su dideliais šunimis");
        Priziuretojas priziuretojas2 = new Priziuretojas("Ieva", "Ievaitė", "ieva@letos.lt",
                "+37063333333", "ieva123", "Kačių priežiūros specialiste");
        imone1.pridetiPriziuretoja(priziuretojas1);
        imone1.pridetiPriziuretoja(priziuretojas2);

        System.out.println("\n--- Darbo grafikai ---");
        priziuretojas1.nustatytiDarboGrafika(new DarboGrafikas("Pr-Pn", "08:00-16:00", "12:00-12:30", 3));
        priziuretojas2.nustatytiDarboGrafika(new DarboGrafikas("Pr-Tr", "10:00-18:00", "14:00-14:30", 2));

        System.out.println("\n--- Vadovas keičia darbo laiką ---");
        vadovas1.nustatytiDarboLaika(priziuretojas2, "Pr-Sk", "09:00-17:00");

        System.out.println("\n" + imone1.gautiInformacija());
        System.out.println("Prižiūrėtojų skaičius: " + imone1.getPriziuretojai().size());

        // --- 3 ZINGSNIS: Paslaugos ---
        System.out.println("\n--- Paslaugų kūrimas ---");
        Paslauga vedziojimas = vadovas1.sukurtiPaslauga("Šunų vedžiojimas", "Vedžiojimas", 15.0, 60,
                "Pas klientą", "Šuo", "Vidutinis,Didelis");
        Paslauga kaciuPrieziura = vadovas1.sukurtiPaslauga("Kačių priežiūra", "Priežiūra", 20.0, 120,
                "Pas klientą", "Katė", "Visi");
        Paslauga kirpimas = vadovas1.sukurtiPaslauga("Kirpimas", "Grooming", 35.0, 90,
                "Salone", "Šuo,Katė", "Mažas,Vidutinis");

        System.out.println("\n--- Prižiūrėtojų priskyrimas ---");
        vadovas1.priskirtiPriziuretojus(vedziojimas, List.of(priziuretojas1));
        vadovas1.priskirtiPriziuretojus(kaciuPrieziura, List.of(priziuretojas2));
        vadovas1.priskirtiPriziuretojus(kirpimas, List.of(priziuretojas1, priziuretojas2));

        System.out.println("\n--- Paslaugų valdymas ---");
        vadovas1.keistiKaina(vedziojimas, 18.0);
        vadovas1.redaguotiPaslauga(kaciuPrieziura, "Kačių priežiūra namuose", 90, "Pas klientą");
        vadovas1.pazymetiNeaktyvia(kirpimas);

        System.out.println("\n--- Paslaugų paieška ---");
        List<Paslauga> rastos = savininkas1.ieskotiPaslaugu(imone1, augintinis1);
        for (Paslauga p : rastos) {
            System.out.println("  " + p.gautiInformacija());
        }
        List<Paslauga> rastos2 = savininkas1.ieskotiPaslaugu(imone1, augintinis2);
        for (Paslauga p : rastos2) {
            System.out.println("  " + p.gautiInformacija());
        }

        System.out.println("\nTomas teikia paslaugų: " + priziuretojas1.getPaslaugos().size());

        // --- 4 ZINGSNIS: Rezervacijos ---
        LocalDate diena = LocalDate.of(2026, 10, 15);

        System.out.println("\n--- Laisvi laikai ---");
        System.out.println("Tomo laisvi laikai " + diena + ": "
                + priziuretojas1.getDarboGrafikas().rodytiLaisvusLaikus(diena));

        System.out.println("\n--- Rezervacijų kūrimas ---");
        Rezervacija rez1 = savininkas1.uzsakytiPaslauga(augintinis1, vedziojimas, diena, LocalTime.of(10, 0));
        Rezervacija rez2 = savininkas1.uzsakytiPaslauga(augintinis2, kaciuPrieziura, diena, LocalTime.of(11, 0));
        savininkas1.uzsakytiPaslauga(augintinis2, vedziojimas, diena, LocalTime.of(12, 0)); // Kates vedzioti negalima
        savininkas1.uzsakytiPaslauga(augintinis1, kirpimas, diena, LocalTime.of(13, 0));    // Kirpimas neaktyvus

        System.out.println("\n--- Prižiūrėtojų priskyrimas ---");
        rez1.priskirtiPriziuretoja(priziuretojas1);
        rez2.priskirtiPriziuretoja(priziuretojas1); // Tomas neteikia kaciu prieziuros -> klaida
        rez2.priskirtiPriziuretoja(priziuretojas2);

        System.out.println("\n--- Būsenos keitimas ---");
        rez1.keistiBusena("Vykdoma");
        rez1.keistiBusena("Pamiršta"); // Neleistina busena -> klaida

        System.out.println("\n--- Rezervacijų peržiūra ---");
        savininkas1.matytiSavoRezervacijas();
        priziuretojas1.matytiPriskirtasRezervacijas();
        vadovas1.perziuretiRezervacijuIstorija();

        // --- 5 ZINGSNIS: Paslaugos vykdymas ---
        System.out.println("\n--- Paslaugos vykdymas ---");
        priziuretojas2.vykdytiPaslauga(rez1); // Ne Ievos rezervacija -> klaida

        PaslaugosVykdymas vykdymas1 = priziuretojas1.vykdytiPaslauga(rez1);
        vykdymas1.pridetiNuotrauka("https://nuotraukos.lt/reksas_parke.jpg");
        vykdymas1.pridetiNuotrauka("https://nuotraukos.lt/reksas_prie_ezero.jpg");
        vykdymas1.pridetiIncidenta("Reksas susipešė su kitu šunimi, nesužeistas");
        vykdymas1.uzbaigti("Pasivaikščiojimas Vingio parke", 4.5, true);

        vykdymas1.pridetiNuotrauka("https://nuotraukos.lt/per_velai.jpg"); // Jau uzbaigta -> klaida
        priziuretojas1.vykdytiPaslauga(rez1);                              // Jau ivykdyta -> klaida

        System.out.println();
        System.out.println(vykdymas1.gautiAtaskaita());

        // --- 6 ZINGSNIS: Zinutes, atsiliepimai, komentarai ---
        System.out.println("\n--- Žinutės ---");
        rez1.siustiZinute(savininkas1, "Laba diena, Reksas bijo dviračių, atsargiai :)");
        Zinute tomoZinute = rez1.siustiZinute(priziuretojas1, "Supratau, eisime ramesniais takais.");
        rez1.siustiZinute(vadovas1, "Primename: po paslaugos galite palikti atsiliepimą.");
        rez1.siustiZinute(priziuretojas2, "Labas!"); // Ieva nepriskirta rez1 -> klaida

        tomoZinute.redaguoti("Supratau, eisime ramesniais parko takais.");
        tomoZinute.perskaityti(priziuretojas1); // Savo zinutes pazymeti negalima -> klaida
        tomoZinute.perskaityti(savininkas1);
        tomoZinute.redaguoti("Bandau dar pakeisti"); // Jau perskaityta -> klaida
        rez1.rodytiZinutes();

        System.out.println("\n--- Atsiliepimai ---");
        savininkas1.vertinti(rez1, "Prižiūrėtojas", 5, "Puikus prižiūrėtojas, Reksas grįžo laimingas!");
        savininkas1.vertinti(rez1, "Įmonė", 4, "Dar kartą");      // Tik vienas atsiliepimas -> klaida
        savininkas1.vertinti(rez2, "Prižiūrėtojas", 5, "Super");  // Dar neatlikta -> klaida
        priziuretojas1.vertintiKlienta(rez1, 5, 9, "Test");       // Elgesys ne 1-5 -> klaida
        priziuretojas1.vertintiKlienta(rez1, 5, 4, "Malonus savininkas, Reksas kiek nerimastingas.");

        System.out.println("Tomo vidutinis įvertinimas: " + priziuretojas1.getVidutinisIvertinimas());
        System.out.println("Jono (kliento) vidutinis įvertinimas: " + savininkas1.getVidutinisIvertinimas());
        System.out.println(imone1.gautiInformacija());

        System.out.println("\n--- Komentarai ---");
        Komentaras kom1 = savininkas1.rasytiKomentara(priziuretojas1, "Ar vedžiojate ir savaitgaliais?");
        Komentaras kom2 = priziuretojas1.atsakytiIKomentara(kom1, "Kol kas tik darbo dienomis.");
        savininkas1.atsakytiIKomentara(kom2, "Aišku, ačiū!");
        priziuretojas2.atsakytiIKomentara(kom1, "Aš galiu!"); // Ne Ievos profilis -> klaida
        kom1.redaguoti(priziuretojas1, "Bandau pakeisti svetimą komentarą"); // Ne autorius -> klaida
        kom1.redaguoti(savininkas1, "Ar vedžiojate ir savaitgaliais? Turiu du augintinius.");

        System.out.println();
        priziuretojas1.rodytiKomentarus();

        // --- 7 ZINGSNIS: Administratorius ---
        System.out.println("\n--- Administratorius ---");
        Administratorius admin = new Administratorius("Admin", "Adminas", "admin@sistema.lt", "+37069999999", "admin123");
        Savininkas savininkas2 = new Savininkas("Petras", "Petraitis", "petras@gmail.com", "+37064444444", "petras123");

        List<Savininkas> visiSavininkai = new ArrayList<>(List.of(savininkas1, savininkas2));
        List<PaslauguImone> visosImones = new ArrayList<>(List.of(imone1));

        System.out.println("\n[Vartotojų valdymas]");
        admin.valdytiVartotojus(visiSavininkai, savininkas1, "šalinti", null); // Turi aktyvia rezervacija -> klaida
        admin.valdytiVartotojus(visiSavininkai, savininkas2, "keistiElPasta", "petras.naujas@gmail.com");
        admin.valdytiVartotojus(visiSavininkai, savininkas2, "blokuoti", null); // Nezinomas veiksmas -> klaida
        admin.valdytiVartotojus(visiSavininkai, savininkas2, "šalinti", null);

        System.out.println("\n[Paslaugų valdymas]");
        admin.valdytiPaslaugas(kirpimas, "aktyvuoti");

        System.out.println("\n[Rezervacijų valdymas]");
        admin.valdytiRezervacijas(rez2, "Atšaukta");
        admin.valdytiRezervacijas(rez1, "Atšaukta"); // Jau atlikta -> klaida

        System.out.println("\n[Atsiliepimų moderavimas]");
        admin.pasleptiAtsiliepima(rez1.getAtsiliepimai().get(0));
        System.out.println("Tomo įvertinimas po paslėpimo: " + priziuretojas1.getVidutinisIvertinimas());

        System.out.println("\n[Komentarų šalinimas]");
        admin.salintiKomentara(kom2); // Pasalina Tomo atsakyma ir Jono atsakyma i ji
        priziuretojas1.rodytiKomentarus();

        System.out.println();
        admin.perziuretiVisusDuomenis(visiSavininkai, visosImones);

        // --- 8 ZINGSNIS: Paslaugu salinimas ir istorija ---
        System.out.println("\n--- Paslaugų šalinimas ir istorija ---");
        Paslauga dresura = vadovas1.sukurtiPaslauga("Dresūra", "Mokymas", 25.0, 60, "Salone", "Šuo", "Visi");
        vadovas1.nustatytiLeidziamusGyvunus(dresura, "Šuo", "Vidutinis,Didelis");
        vadovas1.salintiPaslauga(dresura);     // Neturi rezervaciju -> pasalinama
        vadovas1.salintiPaslauga(vedziojimas); // Turi rezervaciju -> tik neaktyvi
        vadovas1.perziuretiRezervacijuIstorija(vedziojimas);
        System.out.println("Įmonės paslaugų skaičius: " + imone1.getPaslaugos().size());
    }

}