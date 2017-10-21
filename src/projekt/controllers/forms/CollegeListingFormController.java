package projekt.controllers.forms;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXSnackbar;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;
import projekt.LoginAuthenticator;
import projekt.Main;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ResourceBundle;

public class CollegeListingFormController implements Initializable {

    public static boolean isCollegeListingFormComplete = false;
    @FXML
    private VBox college_listing_vbox;
    @FXML
    private JFXListView<String> available_college_list;
    @FXML
    private JFXButton add_college_button;
    @FXML
    private JFXButton remove_college_button;
    @FXML
    private JFXListView<String> selected_college_list;
    @FXML
    private JFXButton submit_college_listing;
    private String TAG = "CollegeListingFormController";

    private Connection conn = null;
    private Statement st;
    private String sql;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        String[] colleges = {
                "IIT Bombay",
                "SIES Graduate School of Technology",
                "Veermata Jijabai Technological Institute",
                "K. J. Somaiya College of Engineering",
                "Thadomal Shahani Engineering College",
                "VESIT Chembur",
                "Dwarkadas J. Sanghvi College of Engineering"
        };

        for (int i = 0; i < colleges.length; i++) {
            available_college_list.getItems().add(colleges[i]);
        }

        add_college_button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String collegeToAdd = available_college_list.getSelectionModel().getSelectedItem();
                if (collegeToAdd != null) {
                    selected_college_list.getItems().add(collegeToAdd);
                    available_college_list.getItems().remove(collegeToAdd);
                }
            }
        });

        remove_college_button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String collegeToRemove = selected_college_list.getSelectionModel().getSelectedItem();
                if (collegeToRemove != null) {
                    available_college_list.getItems().add(collegeToRemove);
                    selected_college_list.getItems().remove(collegeToRemove);
                }
            }
        });

        final JFXSnackbar jfxSnackbar = new JFXSnackbar(college_listing_vbox);

        submit_college_listing.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                if (selected_college_list.getItems().size() == 0) {
                    jfxSnackbar.show("Please select the Colleges", 2000);
                } else {
                    String[] selectedColleges = new String[selected_college_list.getItems().size()];
                    for (int i = 0; i < selectedColleges.length; i++) {
                        selectedColleges[i] = selected_college_list.getItems().get(i);
                        Main.log(TAG, "College " + i + " : " + selectedColleges[i]);
                    }

                    try {
                        new org.mariadb.jdbc.Driver();
                        conn = DriverManager.getConnection(Main.DB_URL, "root", "");
                        if (conn != null)
                            Main.log(TAG, "Connected to Database");

                        st = conn.createStatement();

                        sql = "INSERT INTO COLLEGELIST (" + getPrefs(selectedColleges.length) + ")"
                                + " VALUES (" + getColumns(selectedColleges) + ");";

                        st.executeQuery(sql);
                        conn.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    isCollegeListingFormComplete = true;
                }
            }
        });
    }

    public String getPrefs(int prefLength) {
        StringBuilder columns = new StringBuilder();
        columns.append("id");
        for (int i = 1; i <= prefLength; i++) {
            columns.append(", pref" + i);
        }
        return columns.toString();
    }

    public String getColumns(String[] collegeList) {
        StringBuilder columns = new StringBuilder();
        columns.append(LoginAuthenticator.id);
        for (int i = 0; i < 5; i++) {
             if (collegeList[i] == null)
                columns.append(", '" + collegeList[i] + "'");
        }
        return columns.toString();
    }

}

