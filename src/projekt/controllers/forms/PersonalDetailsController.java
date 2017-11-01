package projekt.controllers.forms;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Paint;
import projekt.LoginAuthenticator;
import projekt.Main;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

public class PersonalDetailsController implements Initializable {

    static boolean isPersonalDetailsFormComplete = false;
    private final String TAG = "PersonalDetailsController";
    @FXML
    private JFXButton submit_personal_details_form;
    @FXML
    private JFXDatePicker dateofbirth;
    @FXML
    private JFXTextField surname, firstname, lastname,
            fathername, mothername, aadharno,
            address, state, district, pincode,
            teleprefix, telenumber, mobileno, email;
    private Connection conn = null;
    private Statement st;
    private String sql;
    private int id;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        final JFXTextField[] jfxTextFields = {surname, firstname, lastname,
                fathername, mothername, aadharno,
                address, state, district, pincode,
                teleprefix, telenumber, mobileno, email};

        try {
            new org.mariadb.jdbc.Driver();
            conn = DriverManager.getConnection(Main.DB_URL, "root", "");
            if (conn != null)
                Main.log(TAG, "Connected to Database");
            else {
                Main.log(TAG, "Couldn't connect to Database");
                return;
            }

            st = conn.createStatement();
            sql = "SELECT id from LOGIN WHERE id=" + LoginAuthenticator.id;
            ResultSet rs = st.executeQuery(sql);
            while (rs.next())
                id = rs.getInt("id");
        } catch (Exception e) {
            e.printStackTrace();
        }

        String process;
        if (id == LoginAuthenticator.id) {
            process = "UPDATE";
        } else {
            process = "INSERT";
        }

        submit_personal_details_form.setOnMouseClicked(new EventHandler<MouseEvent>() {
            int isIncorrect = 0;

            @Override
            public void handle(MouseEvent event) {
                isIncorrect = 0;
                for (JFXTextField jfxTextField : jfxTextFields) {
                    if (jfxTextField.getText().isEmpty()) {
                        Main.log(TAG, jfxTextField.toString());
                        setBackground(jfxTextField, "#F44336");
                        isIncorrect = 1;
                    } else {
                        setBackground(jfxTextField, "#FFFFFF");
                    }
                }
                if (isIncorrect == 1) {
                    Main.log(TAG, "Submission finished with ERRORS");
                } else {
                    Main.log(TAG, "Submitting Form to DB");
                    isPersonalDetailsFormComplete = true;
                    try {
                        new org.mariadb.jdbc.Driver();
                        conn = DriverManager.getConnection(Main.DB_URL, "root", "");

                        if (conn != null)
                            Main.log(TAG, "Connected to Database");
                        else {
                            Main.log(TAG, "Couldn't connect to Database");
                            return;
                        }

                        st = conn.createStatement();

                        System.out.println("LoginAuthenticator.id " + LoginAuthenticator.id);
                        sql = "INSERT INTO PDFORM (id, surname, firstname, lastname, fathername, mothername, aadharno, address, state, district, pincode, teleprefix, telenumber, mobileno, email)"
                                + " VALUES (" + getColumnValues(jfxTextFields) + ");";
                        Main.log(TAG, sql);
                        st.executeQuery(sql);
                        conn.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private String getColumnValues(JFXTextField[] jfxTextFields) {
        int i;
        StringBuilder columns = new StringBuilder();
        columns.append(LoginAuthenticator.id);
        for (i = 0; i < jfxTextFields.length; i++) {
            columns.append(", '");
            columns.append(jfxTextFields[i].getText());
            columns.append("'");
        }
        return columns.toString();
    }

    private void setBackground(JFXTextField jfxTextField, String hex) {
        jfxTextField.setBackground(new Background
                (
                        new BackgroundFill(
                                Paint.valueOf(hex),
                                new CornerRadii(2),
                                new Insets(5, 0, 5, 0)
                        )
                )
        );

    }
}
