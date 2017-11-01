package projekt.controllers.forms;

import com.jfoenix.controls.JFXButton;
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
import java.sql.Statement;
import java.util.ResourceBundle;

public class AcademicController implements Initializable {

    static boolean isAcademicFormComplete = false;
    private final String TAG = "AcademicController";
    @FXML
    private JFXTextField ssc_aggregate_percent, hsc_aggregate_percent;
    @FXML
    private JFXTextField jee_marks;
    @FXML
    private JFXTextField cet_marks;
    @FXML
    private JFXButton submit_academic_form;
    private Connection conn = null;
    private Statement st;
    private String sql;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        final JFXTextField[] jfxTextFields = {ssc_aggregate_percent, hsc_aggregate_percent,
                cet_marks, jee_marks};

        submit_academic_form.setOnMouseClicked(new EventHandler<MouseEvent>() {

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

                        sql = "INSERT INTO ACAD (id, ssc_aggregate_percent, hsc_aggregate_percent, cet_marks, jee_marks)"
                                + " VALUES (" + getColumnValues(jfxTextFields) + ");";
                        st.executeQuery(sql);

                        conn.close();
                        isAcademicFormComplete = true;

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

