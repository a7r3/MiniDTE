package projekt.controllers;

import java.util.HashMap;
import java.util.Map;

public class CollegeDB {

    private static Map<Integer, String> collegeDB = new HashMap<>();


    public CollegeDB() {
        collegeDB.put(600, "IIT Bombay");
        collegeDB.put(601, "SIES Graduate School of Technology");
        collegeDB.put(602, "Veermata Jijabai Technological Institute");
        collegeDB.put(603, "K. J. Somaiya College of Engineering");
        collegeDB.put(604, "Thadomal Shahani Engineering College");
        collegeDB.put(605, "VESIT Chembur");
        collegeDB.put(606, "Dwarkadas J. Sanghvi College of Engineering");
    }

    public static Map<Integer, String> getCollegeDB() {
        return collegeDB;
    }

}