package edu.iastate.mlwymore.carbondiary;

import java.util.ArrayList;

public class CarbonDiary {
    private ArrayList<CarbonDiaryEntry> mEntries = new ArrayList<>();
    private double mTotalCarbon = 0.0;

    public void addEntry(String type, double amount) {
        CarbonDiaryEntry entry = new CarbonDiaryEntry(type, amount);
        mEntries.add(entry);
        mTotalCarbon += entry.getCarbon();
    }

    public void removeEntry(CarbonDiaryEntry entry) {
        mTotalCarbon -= entry.getCarbon();
        mEntries.remove(entry);
    }

    public ArrayList<CarbonDiaryEntry> getEntries() {
        return mEntries;
    }

    public double getTotalCarbon() {
        return mTotalCarbon;
    }
}
