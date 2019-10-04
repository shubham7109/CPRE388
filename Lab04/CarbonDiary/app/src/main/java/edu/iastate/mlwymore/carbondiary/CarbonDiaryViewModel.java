package edu.iastate.mlwymore.carbondiary;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CarbonDiaryViewModel extends ViewModel {
    public CarbonDiary carbonDiary;

    public CarbonDiaryViewModel() {
        carbonDiary = new CarbonDiary();
    }

    public CarbonDiaryEntry addEntry(String type, double amount) {
        return carbonDiary.addEntry(type,amount);
    }

    public CarbonDiaryEntry removeEntry(CarbonDiaryEntry entry) {
        return carbonDiary.removeEntry(entry);
    }
}
