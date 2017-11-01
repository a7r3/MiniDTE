package projekt.controllers;

import com.google.common.collect.HashBiMap;

import java.util.HashMap;

public class CollegeDB {

    private final HashBiMap<Integer, String> map = HashBiMap.create();

    private HashMap<Integer, Integer> seatsMap = new HashMap<>();

    public CollegeDB() {
        map.put(601, "SIES GST : Computer Engineering");
        seatsMap.put(601, 4);
        map.put(602, "SIES GST : Information Technology");
        seatsMap.put(602, 4);
        map.put(701, "KJSCE : Computer Engineering");
        seatsMap.put(701, 4);
        map.put(702, "KJSCE : Information Technology");
        seatsMap.put(702, 4);
        map.put(801, "Thadomal Shahani C.E. : Computer Engineering");
        seatsMap.put(801, 4);
        map.put(802, " Thadomal Shahani C.E. : Information Technology");
        seatsMap.put(802, 4);
        map.put(901, "VESIT : Computer Engineering");
        seatsMap.put(901, 4);
        map.put(902, "VESIT : Information Technology");
        seatsMap.put(902, 4);
    }

    public HashBiMap<Integer, String> getMap() {
        return map;
    }

    public HashMap<Integer, Integer> getSeatsMap() {
        return seatsMap;
    }

}