package projekt.controllers;

import com.jfoenix.controls.JFXButton;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import projekt.Main;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

public class AdminConsoleController implements Initializable {

    @FXML
    private Pane result_pane;

    @FXML
    private JFXButton generate_results_button;

    @FXML
    private JFXButton logOutButton;

    private String TAG = "AdminConsoleController";

    private static Student[] s;

    private static void setStudent(Student[] s) {
        AdminConsoleController.s = s;
    }

    private static Student[] getStudent() {
        return s;
    }

    private ObservableList<StudentList> data = FXCollections.observableArrayList();

    public static class StudentList {

        private final SimpleStringProperty name;
        private final SimpleStringProperty college;
        private final SimpleStringProperty stream;

        private StudentList(String name, String college) {
            this.name = new SimpleStringProperty(name);
            String[] someString = college.split(" : ");
            this.college = new SimpleStringProperty(someString[0]);
            this.stream = new SimpleStringProperty(someString[1]);
        }

        public String getStream() {
            return stream.get();
        }

        public String getName() {
            return name.get();
        }

        public String getCollege() {
            return college.get();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        logOutButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                MainController.closeConsoleStage();
                Main.startLoginForm();
            }
        });

        generate_results_button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                getDatabase();
                Main.log(TAG, "getDataBase : Done");
                arrangeSeat();
                Main.log(TAG, "arrangeSeat : Done");
                StudentList studentList[] = allocateSeat();
                Main.log(TAG, "allocateSeat : Done");
                for (StudentList s : studentList) {
                    if (s.getCollege().contains("--")) {
                        data.add(s);
                    } else {
                        data.add(s);
                    }
                }

                TableView table = new TableView();
                table.setEditable(false);
                TableColumn nameColumn = new TableColumn("Name");
                TableColumn streamColumn = new TableColumn("Stream");
                TableColumn collegeColumn = new TableColumn("College");

                nameColumn.setCellValueFactory(
                        new PropertyValueFactory<>("name"));
                streamColumn.setCellValueFactory(
                        new PropertyValueFactory<>("stream"));
                collegeColumn.setCellValueFactory(
                        new PropertyValueFactory<>("college"));

                table.getColumns().addAll(nameColumn, streamColumn, collegeColumn);
                table.setItems(data);
                table.setPrefSize(600, 250);
                result_pane.getChildren().add(table);
            }
        });
    }

    private boolean isSeatAvailable(int cid) {
        return collegeDB.getSeatsMap().get(cid) > 0;
    }

    class Student {

        String nick;
        int id;
        int prefs[] = new int[5];
        int marks[] = new int[4];

        Student(String nick, int id, int marks[], int prefs[]) {
            this.nick = nick;
            this.id = id;
            this.marks = marks;
            this.prefs = prefs;
        }
    }

    CollegeDB collegeDB = new CollegeDB();

    private void getDatabase() {
        try {
            new org.mariadb.jdbc.Driver();
            Connection conn = DriverManager.getConnection(Main.DB_URL, "root", "");
            if (conn != null)
                Main.log(TAG, "Connected to Database");

            Statement st = conn.createStatement();

            String sql = "SELECT * FROM ACAD";
            ResultSet rs = st.executeQuery(sql);

            // Get Marks from DB //
            int marks[][] = new int[32][4];
            int i = 0;
            String markFields[] = {"cet_marks", "hsc_aggregate_percent", "ssc_aggregate_percent", "jee_marks"};
            while (rs.next()) {
                for (int j = 0; j < 4; j++) {
                    marks[i][j] = rs.getInt(markFields[j]);
                    Main.log(TAG, "marks[j] : " + marks[i][j]);
                }
                i++;
            }

            // Get Names from DB //
            String nick[] = new String[32];
            sql = "SELECT nick FROM LOGIN WHERE id!=0";
            rs = st.executeQuery(sql);
            i = 0;
            while (rs.next()) {
                nick[i] = rs.getString("nick");
                i++;
            }

            // Get College preferences from DB //
            sql = "SELECT * FROM COLLEGELIST";
            rs = st.executeQuery(sql);

            Student student[] = new Student[32];
            i = 0;
            while (rs.next()) {

                /*
                Construct a Student Object with the Details recently grabbed from DB
                Details
                > Student name
                > Student unique id
                > Student marks
                > Student preferences
                */

                student[i] = new Student(nick[i], rs.getInt("id"), marks[i], new int[]{
                        rs.getInt("pref1"),
                        rs.getInt("pref2"),
                        rs.getInt("pref3"),
                        rs.getInt("pref4"),
                        rs.getInt("pref5"),
                });
                i++;
            }

            setStudent(student);

            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void arrangeSeat() {
        Student temp;
        Student[] s = getStudent();
        for (int markField = 0; markField < 4; markField++) {
            for (int i = 0; i < 32; i++) {
                for (int j = 0; j < 32; j++) {
                    if (s[i].marks[markField] < s[j].marks[markField]) {
                        temp = s[i];
                        s[i] = s[j];
                        s[j] = temp;
                    }
                }
            }
        }
    }

    private StudentList[] allocateSeat() {
        Student[] s = getStudent();
        StudentList[] studentLists = new StudentList[32];
        int seats;
        for (int i = 0; i < 32; i++) {
            for (int j = 0; j < 5; j++) {
                if (isSeatAvailable(s[i].prefs[j])) {
                    seats = collegeDB.getSeatsMap().get(s[i].prefs[j]);
                    collegeDB.getSeatsMap().put(s[i].prefs[j], seats - 1);
                    Main.log(TAG, "College Allocated for " + s[i].nick + " : " + collegeDB.getMap().get(s[i].prefs[j]));
                    studentLists[i] = new StudentList(s[i].nick, collegeDB.getMap().get(s[i].prefs[j]));
                    break;
                } else if (j == 4) {
                    // No College Allocated for this guy
                    Main.log(TAG, "No College for " + s[i].nick + ". Better Luck next time!");
                    studentLists[i] = new StudentList(s[i].nick, "-- : No College Allocated.");
                }
            }
        }

        Main.log(TAG, " -- Seats Left --");
        Main.log(TAG, "" + collegeDB.getSeatsMap().get(601));
        Main.log(TAG, "" + collegeDB.getSeatsMap().get(602));
        Main.log(TAG, "" + collegeDB.getSeatsMap().get(701));
        Main.log(TAG, "" + collegeDB.getSeatsMap().get(702));
        Main.log(TAG, "" + collegeDB.getSeatsMap().get(801));
        Main.log(TAG, "" + collegeDB.getSeatsMap().get(802));
        Main.log(TAG, "" + collegeDB.getSeatsMap().get(901));
        Main.log(TAG, "" + collegeDB.getSeatsMap().get(902));
        Main.log(TAG, "-----------------");

        return studentLists;
    }

}

