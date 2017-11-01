package projekt;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;

public class Main extends Application {

    /////////////
    // LAYOUTS //
    /////////////
    public final static String ADMIN_CONSOLE_PANE = "AdminConsole.fxml";
    public final static String ABOUT_PAGE = "About.fxml";
    public final static String LOGIN_PAGE = "Main.fxml";
    public final static String STUDENT_CONSOLE_PANE = "StudentConsole.fxml";
    public final static String STUDENT_NAV_PANE = "StudentNavPane.fxml";

    ////////////////////////////
    // FORM LAYOUT REFERENCES //
    ////////////////////////////
    public final static String ACADEMIC_FORM = "Academic.fxml";
    public final static String COLLEGE_LISTING_FORM = "CollegeListing.fxml";
    public final static String SUMMARY_FORM = "FormSummary.fxml";
    public final static String PERSONAL_DETAILS_FORM = "PersonalDetails.fxml";
    public final static String UPLOAD_DOCS_FORM = "UploadDocs.fxml";

    // DATABASE URL
    public final static String DB_URL = "jdbc:mariadb://127.0.0.1:3306/login";

    private static Stage mainStage;

    public static void closeLoginForm() {
        mainStage.close();
    }

    public static void startLoginForm() {
        mainStage.show();
    }

    public static URL getResource(String resourceFile) {
        return Main.class.getResource("resources/fxml/" + resourceFile);
    }

    public static URL getFormResource(String formResourceFile) {
        return Main.class.getResource("resources/fxml/forms/" + formResourceFile);
    }

    public static void main(String[] args) {
        String TAG = "Main";
        Main.log(TAG, "Firing up DTE Console, v0.2");
        launch(args);
    }

    public static void log(String TAG, String msg) {
        System.out.println(TAG + " : " + msg);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        AnchorPane root = FXMLLoader.load(getResource(LOGIN_PAGE));
        Scene scene = new Scene(root, 600, 400);
        scene.getStylesheets().add(getClass().getResource("resources/css/application.css").toExternalForm());
        primaryStage.setTitle("Login - DTE Console");
        primaryStage.setMaxWidth(600);
        primaryStage.setScene(scene);
        primaryStage.show();
        mainStage = primaryStage;
    }
}
