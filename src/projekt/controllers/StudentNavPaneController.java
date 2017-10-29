package projekt.controllers;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import projekt.Main;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class StudentNavPaneController implements Initializable {

    @FXML
    private JFXButton personal_details_button;

    @FXML
    private JFXButton academic_details_button;

    @FXML
    private JFXButton college_list_button;

    @FXML
    private JFXButton upload_documents_button;

    @FXML
    private JFXButton summary_button;

    @FXML
    private JFXButton about_navpane;

    @FXML
    private JFXButton logout_button_navpane;

    private StudentConsoleController scc = null;

    public void setStudentConsoleController(StudentConsoleController scc) {
        this.scc = scc;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        personal_details_button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    scc.setForm(Main.PERSONAL_DETAILS_FORM, "Personal Details");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        academic_details_button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    scc.setForm(Main.ACADEMIC_FORM, "Academic Details");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        college_list_button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    scc.setForm(Main.COLLEGE_LISTING_FORM, "College Listing");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        upload_documents_button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    scc.setForm(Main.UPLOAD_DOCS_FORM, "Upload Documents");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        summary_button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    scc.setForm(Main.SUMMARY_FORM, "Summary");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        about_navpane.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    scc.setForm(Main.ABOUT_PAGE, "About");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        logout_button_navpane.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                scc.studentNavDrawerToggle();
                MainController.closeConsoleStage();
                Main.startLoginForm();
            }
        });

    }

}
