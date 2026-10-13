package valdikliai;

import modelis.Administratorius;
import duomenys.DuomenuBaze;
import duomenys.Sesija;
import modelis.Vadovas;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {

    // Sie laukai uzpildomi AUTOMATISKAI is fxml pagal fx:id
    @FXML
    private TextField elPastasField;
    @FXML
    private PasswordField slaptazodisField;
    @FXML
    private Label klaidosLabel;

    @FXML
    public void prisijungti(ActionEvent event) {
        String elPastas = elPastasField.getText();
        String slaptazodis = slaptazodisField.getText();

        // 1) Ieskome tarp administratoriu
        for (Administratorius a : DuomenuBaze.getAdministratoriai()) {
            if (a.getElPastas().equals(elPastas) && a.tikrintiSlaptazodi(slaptazodis)) {
                if (a.isArUzblokuotas()) {
                    klaidosLabel.setText("Vartotojas užblokuotas");
                    return;
                }
                Sesija.prisijungti(a);
                atidarytiPagrindiniLanga(event);
                return; // Radome - toliau neieskome
            }
        }

        // 2) Ieskome tarp vadovu
        for (Vadovas v : DuomenuBaze.getVadovai()) {
            if (v.getElPastas().equals(elPastas) && v.tikrintiSlaptazodi(slaptazodis)) {
                if (v.isArUzblokuotas()) {
                    klaidosLabel.setText("Vartotojas užblokuotas");
                    return;
                }
                Sesija.prisijungti(v);
                atidarytiPagrindiniLanga(event);
                return;
            }
        }

        // 3) Jei atejome iki cia - niekas nerastas
        klaidosLabel.setText("Neteisingas el. paštas arba slaptažodis");
        slaptazodisField.clear();
    }

    // Pakeicia lango turini is prisijungimo i pagrindini
    private void atidarytiPagrindiniLanga(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/pagrindinis-view.fxml"));
            Stage langas = (Stage) ((Node) event.getSource()).getScene().getWindow();
            langas.setScene(new Scene(loader.load(), 1000, 650));
            langas.sizeToScene();      // pritaikome lango dydi naujam turiniui
            langas.centerOnScreen();
        } catch (Exception e) {
            klaidosLabel.setText("Nepavyko atidaryti lango: " + e.getMessage());
        }
    }
}
