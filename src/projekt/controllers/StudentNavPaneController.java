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

        JFXButton[] jfxButtons = new JFXButton[]{personal_details_button, academic_details_button,
                college_list_button, upload_documents_button,
                summary_button, about_navpane};

        for (final JFXButton jfxButton : jfxButtons) {
            jfxButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {

                    String formLayout;

                    switch (jfxButton.getText()) {
                        case "Personal Details":
                            formLayout = Main.PERSONAL_DETAILS_FORM;
                            break;
                        case "Academic Details":
                            formLayout = Main.ACADEMIC_FORM;
                            break;
                        case "College List":
                            formLayout = Main.COLLEGE_LISTING_FORM;
                            break;
                        case "Upload Documents":
                            formLayout = Main.UPLOAD_DOCS_FORM;
                            break;
                        case "Form Summary":
                            formLayout = Main.SUMMARY_FORM;
                            break;
                        case "About":
                            formLayout = Main.ABOUT_PAGE;
                            break;
                        case "Log Out":
                            formLayout = Main.LOGIN_PAGE;
                            break;
                        default: // Humans make errors :P
                            formLayout = Main.SUMMARY_FORM;
                    }

                    try {
                        scc.setForm(formLayout, jfxButton.getText());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
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
