package edu.iastate.mlwymore.carbondiary;


import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class CarbonDiaryEntry {
    private final static HashMap<String, Double> mTypesAmounts = new HashMap<>();
    private final static HashMap<String, String> mTypesUnits = new HashMap<>();
    static {
            //Carbon footprints in kg carbon per unit
            //(Very) rough estimates from the Internet
            mTypesAmounts.put("Driving", 0.411);
            mTypesUnits.put("Driving", "miles");
            mTypesAmounts.put("Electronics use", 0.088);
            mTypesUnits.put("Electronics use", "hours");
            mTypesAmounts.put("Air conditioning use", 0.4597 * 1.5);
            mTypesUnits.put("Air conditioning use", "hours");
            mTypesAmounts.put("Meat consumption", 6.8);
            mTypesUnits.put("Meat consumption", "pounds");
    }

    public static String[] getEntryTypes() {
        ArrayList<String> types = new ArrayList<>(mTypesAmounts.keySet());
        Collections.sort(types);
        return types.toArray(new String[]{""});
    }

    public static String getUnitForType(String type) {
        if(mTypesUnits.containsKey(type)) {
            return mTypesUnits.get(type);
        }
        return "";
    }

    private String mType;
    private double mAmount;

    public CarbonDiaryEntry(String type, double amount) {
        mAmount = amount;
        mType = type;
    }

    public double getCarbon() {
        if(mTypesAmounts.containsKey(mType)) {
            return mAmount * mTypesAmounts.get(mType);
        } else {
            return 0.0;
        }
    }

    public String getType() {
        return mType;
    }

    public double getAmount() {
        return mAmount;
    }
}
