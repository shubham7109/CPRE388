package edu.iastate.netid.thingslab;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

public class ZipResponseViewModel extends ViewModel {
    private MutableLiveData<ZipResponse> zipResponseLiveData;

    public ZipResponseViewModel() {
        zipResponseLiveData = new MutableLiveData<>();
    }

    public void observe(LifecycleOwner owner, Observer<ZipResponse> observer) {
        zipResponseLiveData.observe(owner, observer);
    }

    public void setResponse(String JSONresponse) {
        ZipResponse zipResponse = ZipResponse.getZipResponseFromJSON(JSONresponse);
        zipResponseLiveData.setValue(zipResponse);
    }

    public ZipResponse getZipResponse() {
        return zipResponseLiveData.getValue();
    }
}
