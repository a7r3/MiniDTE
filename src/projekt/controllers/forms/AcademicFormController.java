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
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

public class AcademicFormController implements Initializable {


    public static boolean isAcademicFormComplete = false;
    @FXML
    private JFXTextField ssc_board, ssc_passyear, ssc_seatno,
            ssc_maths_marks, ssc_maths_total, ssc_maths_percent,
            ssc_aggregate_marks, ssc_aggregate_total, ssc_aggregate_percent,
            hsc_board, hsc_passyear, hsc_seat_no, hsc_stream,
            hsc_maths_marks, hsc_maths_total, hsc_maths_percent,
            hsc_aggregate_marks, hsc_aggregate_total, hsc_aggregate_percent;
    @FXML
    private JFXButton submit_academic_form;
    private String TAG = "AcademicFormController";

    private Connection conn = null;
    private Statement st;
    private String sql;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        final JFXTextField[] jfxTextFields = {ssc_board, ssc_passyear, ssc_seatno,
                ssc_maths_marks, ssc_maths_total, ssc_maths_percent,
                ssc_aggregate_marks, ssc_aggregate_total, ssc_aggregate_percent,
                hsc_board, hsc_passyear, hsc_seat_no, hsc_stream,
                hsc_maths_marks, hsc_maths_total, hsc_maths_percent,
                hsc_aggregate_marks, hsc_aggregate_total, hsc_aggregate_percent};

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
                    System.out.println("Submission finished with ERRORS");
                } else {
                    System.out.println("Submitting Form to DB");
                    isAcademicFormComplete = true;
                    try {
                        new org.mariadb.jdbc.Driver();
                        conn = DriverManager.getConnection(Main.DB_URL, "root", "");

                        if (conn != null)
                            Main.log(TAG, "Connected to Database");

                        st = conn.createStatement();

                        System.out.println("LoginAuthenticator.id " + LoginAuthenticator.id);
                        sql = "INSERT INTO ACAD (id, ssc_board, ssc_passyear, ssc_seatno, ssc_maths_marks, ssc_maths_total, ssc_maths_percent, ssc_aggregate_marks, ssc_aggregate_total, ssc_aggregate_percent, hsc_board, hsc_passyear, hsc_seat_no, hsc_stream, hsc_maths_marks, hsc_maths_total, hsc_maths_percent, hsc_aggregate_marks, hsc_aggregate_total, hsc_aggregate_percent)"
                                + " VALUES (" + getColumnValues(jfxTextFields) + ");";
                        st.executeQuery(sql);
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
            columns.append(", '" + jfxTextFields[i].getText() + "'");
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

