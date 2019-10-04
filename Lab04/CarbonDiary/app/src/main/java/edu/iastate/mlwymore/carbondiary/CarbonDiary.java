package edu.iastate.mlwymore.carbondiary;

import java.util.ArrayList;

public class CarbonDiary {
    private ArrayList<CarbonDiaryEntry> mEntries = new ArrayList<>();
    private double mTotalCarbon = 0.0;
    private double avgCarbon = 0;
    private int numDays =0;

    public CarbonDiaryEntry addEntry(String type, double amount) {
        CarbonDiaryEntry entry = new CarbonDiaryEntry(type, amount);
        mEntries.add(entry);
        mTotalCarbon += entry.getCarbon();
        return entry;
    }

    public CarbonDiaryEntry removeEntry(CarbonDiaryEntry entry) {
        mTotalCarbon -= entry.getCarbon();
        mEntries.remove(entry);
        return entry;
    }

    public ArrayList<CarbonDiaryEntry> getEntries() {
        return mEntries;
    }

    public double getTotalCarbon() {
        return mTotalCarbon;
    }

    public void updateNewDay(){
        avgCarbon = ((avgCarbon*numDays) + mTotalCarbon)/(++numDays);
    }

    public double getDailyAvg(){
        return avgCarbon;
    }

    public void clearEntries(){
        mTotalCarbon = 0;
        mEntries.clear();
    }
}
