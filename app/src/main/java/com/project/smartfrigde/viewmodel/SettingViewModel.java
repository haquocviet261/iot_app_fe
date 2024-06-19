package com.project.smartfrigde.viewmodel;

import androidx.databinding.ObservableField;
import androidx.lifecycle.ViewModel;

public class SettingViewModel extends ViewModel {
    ObservableField<Boolean> isLogout = new ObservableField<>(false);
    public void onClickLogout(){
        isLogout.set(true);
    }

    public ObservableField<Boolean> getIsLogout() {
        return isLogout;
    }

    public void setIsLogout(ObservableField<Boolean> isLogout) {
        this.isLogout = isLogout;
    }
}
