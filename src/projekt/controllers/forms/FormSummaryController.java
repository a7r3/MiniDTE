package projekt.controllers.forms;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSnackbar;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import projekt.Main;

import java.net.URL;
import java.util.ResourceBundle;

public class FormSummaryController implements Initializable {

    @FXML
    private Label form_summary_status;

    @FXML
    private ImageView personal_details_status;

    @FXML
    private ImageView academic_details_status;

    @FXML
    private ImageView college_listing_status;

    @FXML
    private ImageView upload_docs_status;

    @FXML
    private VBox form_summary_pane;

    @FXML
    private JFXButton submit_form_button;

    private String TAG = "FormSummaryController";

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        int totalForms = 4;
        int completedForms = 0;

        Image image = null;
        try {
            image = new Image(getClass().getResource("../../resources/images/tick.png").toURI().toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (AcademicFormController.isAcademicFormComplete) {
            Main.log(TAG, "AcademicForm : Complete");
            completedForms++;
            personal_details_status.setImage(image);
        }
        if (PersonalDetailsFormController.isPersonalDetailsFormComplete) {
            Main.log(TAG, "PersonalDetailsForm : Complete");
            completedForms++;
            academic_details_status.setImage(image);
        }
        if (CollegeListingFormController.isCollegeListingFormComplete) {
            Main.log(TAG, "CollegeListingForm : Complete");
            completedForms++;
            college_listing_status.setImage(image);
        }
        if (UploadDocsController.isUploadDocsFormComplete) {
            Main.log(TAG, "UploadDocsForm : Complete");
            completedForms++;
            upload_docs_status.setImage(image);
        }

        String status;
        if (completedForms == 4) {
            Main.log(TAG, "Submitted All Forms");
            status = null;
            submit_form_button.setDisable(false);
            submit_form_button.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    JFXSnackbar snackBar = new JFXSnackbar(form_summary_pane);
                    snackBar.show("Submitted Form Successfully, You may Log-Out", 5000);
                }
            });
        } else
            status = "not ";

        form_summary_status.setText("Your Form is " + status + "complete");
    }
}
