package projekt.controllers;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.JFXToolbar;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import projekt.Main;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class StudentConsoleController implements Initializable {

    @FXML
    public VBox form_box;
    @FXML
    public Label student_console_toolbar_title;
    @FXML
    private AnchorPane student_console_pane;
    @FXML
    private JFXHamburger hamburger;
    @FXML
    private JFXToolbar student_console_toolbar;
    @FXML
    private JFXDrawer student_nav_drawer;
    private HamburgerBackArrowBasicTransition transition;

    private boolean isShown = false;
    private ScrollPane scrollPane;

    @FXML
    public void studentNavDrawerToggle() {
        if (isShown) {
            getTransition().setRate(-1);
            student_nav_drawer.close();
        } else {
            System.out.println("to Front");
            student_nav_drawer.toFront();
            student_nav_drawer.open();
            getTransition().setRate(1);
        }

        isShown = !isShown;

        getTransition().play();

        if (!isShown) {
            student_nav_drawer.toBack();
            System.out.println("to Back");
        }

        hamburger.toFront();
    }

    private HamburgerBackArrowBasicTransition getTransition() {
        return transition;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            FXMLLoader studentNavPaneLoader = new FXMLLoader();
            studentNavPaneLoader.setLocation(Main.getResource(Main.STUDENT_NAV_PANE));
            transition = new HamburgerBackArrowBasicTransition(hamburger);
            VBox studentSidePanel = studentNavPaneLoader.load();
            StudentNavPaneController studentNavPaneController = studentNavPaneLoader.getController();
            System.out.println("Student NavPane Controller : " + studentNavPaneController.toString());
            studentNavPaneController.setStudentConsoleController(this);

            student_nav_drawer.setSidePane(studentSidePanel);

            scrollPane = new ScrollPane();
            scrollPane.setFitToWidth(true);
            scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
            scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

            setForm(Main.SUMMARY_FORM, "Form Summary");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setForm(String formResourceFile, String toolBarTitle) throws IOException {
        VBox someBox = FXMLLoader.load(Main.getFormResource(formResourceFile));
        student_console_toolbar_title.setText(toolBarTitle);
        studentNavDrawerToggle();
        scrollPane.setContent(someBox);
        form_box.getChildren().clear();
        form_box.getChildren().add(scrollPane);
    }
}