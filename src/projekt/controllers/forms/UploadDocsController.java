package projekt.controllers.forms;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSnackbar;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import projekt.LoginAuthenticator;
import projekt.Main;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.ResourceBundle;

public class UploadDocsController implements Initializable {

    public static boolean isUploadDocsFormComplete = false;
    @FXML
    private VBox upload_docs_pane;
    @FXML
    private JFXButton upload_image_button;
    @FXML
    private ImageView photo_image;
    @FXML
    private JFXButton upload_signature_button;
    @FXML
    private ImageView signature_image;
    @FXML
    private JFXButton submit_upload_docs_button;
    private Connection conn = null;
    private String TAG = " UploadDocsController";
    private File imageFile, signatureFile;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        submit_upload_docs_button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    new org.mariadb.jdbc.Driver();
                    conn = DriverManager.getConnection(Main.DB_URL, "root", "");

                    if (conn != null)
                        Main.log(TAG, "Connected to Database");

                    PreparedStatement preparedStatement = conn.prepareStatement("INSERT INTO DOCS (id, photo_image, signature_image) VALUES(?,?,?)");

                    preparedStatement.setInt(1, LoginAuthenticator.id);
                    FileInputStream fis = new FileInputStream(imageFile);
                    preparedStatement.setBinaryStream(2, fis, fis.available());
                    fis = new FileInputStream(signatureFile);
                    preparedStatement.setBinaryStream(3, fis, fis.available());

                    int status = preparedStatement.executeUpdate();

                    JFXSnackbar snackBar = new JFXSnackbar(upload_docs_pane);
                    if (status > 0) {
                        snackBar.show("Images uploaded Successfully", 2000);
                        Main.log(TAG, "Uploaded Images to DB");
                        isUploadDocsFormComplete = true;
                    } else {
                        snackBar.show("Failed to Upload Images", 2000);
                        Main.log(TAG, "Failed to Upload Images");
                        isUploadDocsFormComplete = false;
                    }

                    conn.close();

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        upload_image_button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Open Photo Image");
                imageFile = fileChooser.showOpenDialog(new Stage());
                if (imageFile != null) {
                    Image image = new Image(imageFile.toURI().toString());
                    photo_image.setImage(image);
                }
            }
        });

        upload_signature_button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Open Signature Image");
                signatureFile = fileChooser.showOpenDialog(new Stage());
                if (imageFile != null) {
                    Image image = new Image(signatureFile.toURI().toString());
                    signature_image.setImage(image);
                }
            }
        });
    }
}
