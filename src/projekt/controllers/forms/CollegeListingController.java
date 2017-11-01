package projekt.controllers.forms;

import com.google.common.collect.HashBiMap;
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
import projekt.controllers.CollegeDB;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ResourceBundle;

public class CollegeListingController implements Initializable {

    static boolean isCollegeListingFormComplete = false;
    private final String TAG = "CollegeListingController";
    private final CollegeDB collegeDB = new CollegeDB();
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
    private Connection conn = null;
    private Statement st;
    private String sql;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        for (HashBiMap.Entry<Integer, String> Entry : collegeDB.getMap().entrySet()) {
            Main.log(TAG, "Adding College : " + Entry.getKey());
            available_college_list.getItems().add(Entry.getValue());
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

                    // Selected Colleges' Code Array
                    int[] selectedColleges = new int[selected_college_list.getItems().size()];

                    for (int i = 0; i < selectedColleges.length; i++) {
                        selectedColleges[i] = collegeDB.getMap().inverse().get(selected_college_list.getItems().get(i));
                        Main.log(TAG, "College " + i + " : " + selectedColleges[i]);
                    }

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

                        sql = "INSERT INTO COLLEGELIST (" + getPrefs(selectedColleges.length) + ")"
                                + " VALUES (" + getColumns(selectedColleges) + ");";

                        Main.log(TAG, "sql : " + sql);

                        st.executeQuery(sql);

                        jfxSnackbar.show("Submitted College List Successfully", 2000);

                        conn.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    isCollegeListingFormComplete = true;
                }
            }
        });
    }

    private String getPrefs(int prefLength) {
        StringBuilder columns = new StringBuilder();
        columns.append("id");
        for (int i = 1; i <= prefLength; i++) {
            columns.append(", pref");
            columns.append(i);
        }
        return columns.toString();
    }

    private String getColumns(int[] collegeList) {
        StringBuilder columns = new StringBuilder();
        columns.append(LoginAuthenticator.id);
        for (int college : collegeList) {
            columns.append(", ");
            columns.append(college);
        }
        return columns.toString();
    }

}

