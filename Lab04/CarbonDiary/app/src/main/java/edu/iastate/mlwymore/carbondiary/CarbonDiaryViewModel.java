package edu.iastate.mlwymore.carbondiary;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CarbonDiaryViewModel extends ViewModel {
    public MutableLiveData<CarbonDiary> carbonDiary = new MutableLiveData<>();

    public CarbonDiaryViewModel() {
        carbonDiary.setValue(new CarbonDiary());
    }

    public void addEntry(String type, double amount) {
        CarbonDiary diary = carbonDiary.getValue();
        if(diary != null) {
            diary.addEntry(type, amount);
            carbonDiary.setValue(diary);
        }
    }

    public void removeEntry(CarbonDiaryEntry entry) {
        CarbonDiary diary = carbonDiary.getValue();
        if(diary != null) {
            diary.removeEntry(entry);
            carbonDiary.setValue(diary);
        }
    }
}
