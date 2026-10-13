package duomenys;

import modelis.Administratorius;
import modelis.Vadovas;
import modelis.Vartotojas;

public class Sesija {
    private static Administratorius administratorius; // jei prisijunges admin
    private static Vadovas vadovas;                   // jei prisijunges vadovas

    public static void prisijungti(Administratorius a) {
        administratorius = a;
        vadovas = null;
    }

    public static void prisijungti(Vadovas v) {
        vadovas = v;
        administratorius = null;
    }

    public static void atsijungti() {
        administratorius = null;
        vadovas = null;
    }

    public static boolean arAdministratorius() {
        return administratorius != null;
    }

    public static Administratorius getAdministratorius() {
        return administratorius;
    }

    public static Vadovas getVadovas() {
        return vadovas;
    }

    // Grazina prisijungusi vartotoja nepriklausomai nuo roles (polimorfizmas)
    public static Vartotojas getVartotojas() {
        if (administratorius != null) {
            return administratorius;
        }
        return vadovas;
    }
}
