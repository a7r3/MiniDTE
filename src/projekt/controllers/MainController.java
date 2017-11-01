package projekt.controllers;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXProgressBar;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import projekt.LoginAuthenticator;
import projekt.Main;

import static projekt.LoginAuthenticator.isAdmin;

public class MainController {

    private static Stage consoleStage;
    @FXML
    private AnchorPane login_pane;
    @FXML
    private JFXTextField login_field;
    @FXML
    private JFXPasswordField password_field;
    @FXML
    private JFXProgressBar login_progress;

    public static void closeConsoleStage() {
        consoleStage.close();
    }

    @FXML
    void attemptLogin(ActionEvent event) throws Exception {
        String username = login_field.getText();
        String password = password_field.getText();
        String TAG = "MainController";
        Main.log(TAG, username + ":" + password);

        LoginAuthenticator la = new LoginAuthenticator(username, password);

        login_progress.setDisable(false);

        if (la.isRegistered()) {
            JFXSnackbar snackBar = new JFXSnackbar(login_pane);
            snackBar.show("Logged-In successfully", 2000);
            Main.log(TAG, "Login Successful");
            Main.log(TAG, "Closing Login and Opening DTE Console");
            try {
                FXMLLoader consoleLoader = new FXMLLoader();
                String consoleTitle;
                if (isAdmin) {
                    consoleLoader.setLocation(Main.getResource(Main.ADMIN_CONSOLE_PANE));
                    consoleTitle = "DTE | Admin Console";
                } else {
                    consoleLoader.setLocation(Main.getResource(Main.STUDENT_CONSOLE_PANE));
                    consoleTitle = "DTE | Student Console";
                }

                // LOAD CONSOLE PANE //
                AnchorPane console = consoleLoader.load();
                consoleStage = new Stage();
                consoleStage.setScene(new Scene(console));
                consoleStage.setTitle(consoleTitle);
                consoleStage.setMaxWidth(610);
                consoleStage.setMaxHeight(460);
                consoleStage.show();
                Main.closeLoginForm();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            JFXSnackbar jfxSnackbar = new JFXSnackbar(login_pane);
            jfxSnackbar.show("Invalid Login Credentials Provided", 2000);
        }
        login_progress.setDisable(true);
    }

}
