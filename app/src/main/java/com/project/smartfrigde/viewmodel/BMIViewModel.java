package com.project.smartfrigde.viewmodel;

import android.content.SharedPreferences;
import android.inputmethodservice.AbstractInputMethodService;

import androidx.databinding.ObservableField;
import androidx.lifecycle.ViewModel;

import com.google.gson.Gson;
import com.project.smartfrigde.data.dto.request.BmiRequest;
import com.project.smartfrigde.data.dto.response.ResponseObject;
import com.project.smartfrigde.data.remote.api.BmiAPIService;
import com.project.smartfrigde.model.User;
import com.project.smartfrigde.utils.Gender;
import com.project.smartfrigde.utils.UserSecurePreferencesManager;
import com.project.smartfrigde.utils.Validation;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class BMIViewModel extends ViewModel {
    Gson gson = new Gson();
    private CompositeDisposable disposable = new CompositeDisposable();
    private ObservableField<String> weight = new ObservableField<>();
    private ObservableField<String> height = new ObservableField<>();
    private ObservableField<String> age = new ObservableField<>();
    private ObservableField<String> gender = new ObservableField<>();
    private ObservableField<String> total_calories = new ObservableField<>();
    private ObservableField<Boolean> isLoadCalories = new ObservableField<>(false);
    private ObservableField<Boolean> is_show_calories = new ObservableField<>(false);
    private ObservableField<Boolean> isInValidInput = new ObservableField<>(false);
    private ObservableField<Boolean> isCaculateCalories = new ObservableField<>(false);
    private ObservableField<Boolean> is_loadded_data = new ObservableField<>(false);

    public ObservableField<Boolean> getIsLoadCalories() {
        return isLoadCalories;
    }

    public void setIsLoadCalories(ObservableField<Boolean> isLoadCalories) {
        this.isLoadCalories = isLoadCalories;
    }

    private BmiRequest bmiRequest;

    public ObservableField<Boolean> getIs_loadded_data() {
        return is_loadded_data;
    }

    public BmiRequest getBmiRequest() {
        return bmiRequest;
    }

    public void setBmiRequest(BmiRequest bmiRequest) {
        this.bmiRequest = bmiRequest;
    }

    public void setIs_loadded_data(ObservableField<Boolean> is_loadded_data) {
        this.is_loadded_data = is_loadded_data;
    }

    public ObservableField<Boolean> getIsCaculateCalories() {
        return isCaculateCalories;
    }
    public void caculateCalories(){
        isCaculateCalories.set(true);
    }
    public void setIsCaculateCalories(ObservableField<Boolean> isCaculateCalories) {
        this.isCaculateCalories = isCaculateCalories;
    }

    public ObservableField<String> getTotal_calories() {
        return total_calories;
    }

    public void setTotal_calories(ObservableField<String> total_calories) {
        this.total_calories = total_calories;
    }

    public ObservableField<String> getWeight() {
        return weight;
    }

    public ObservableField<Boolean> getIs_show_calories() {
        return is_show_calories;
    }

    public void setIs_show_calories(ObservableField<Boolean> is_show_calories) {
        this.is_show_calories = is_show_calories;
    }

    public ObservableField<Boolean> getIsInValidInput() {
        return isInValidInput;
    }

    public void setIsInValidInput(ObservableField<Boolean> isInValidInput) {
        this.isInValidInput = isInValidInput;
    }

    public void setWeight(ObservableField<String> weight) {
       if (!Validation.isValidNumber(weight.get())){
           return;
       }
        this.weight = weight;
    }

    public ObservableField<String> getHeight() {
        return height;
    }

    public void setHeight(ObservableField<String> height) {
        if (!Validation.isValidNumber(height.get())){
            return;
        }
        this.height = height;
    }

    public ObservableField<String> getAge() {
        return age;
    }

    public void setAge(ObservableField<String> age) {
        if (!Validation.isValidNumber(age.get())){
            return;
        }
        this.age = age;
    }

    public ObservableField<String> getGender() {
        return gender;
    }

    public void setGender(ObservableField<String> gender) {
        gender.get().equals(Gender.valueOf(gender.get()));
        this.gender = gender;
    }
    public void saveBmi(){
        bmiRequest = new BmiRequest(weight.get(),height.get(),age.get(),Gender.valueOf(gender.get()));
        BmiAPIService.BMI_API_SERVICE.saveBmi(bmiRequest).observeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseObject>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull ResponseObject responseObject) {
                        is_loadded_data.set(true);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        is_loadded_data.set(false);
                    }

                    @Override
                    public void onComplete() {
                        is_loadded_data.set(true);
                    }
                });
    }
    public void getBmi(SharedPreferences.Editor editor){
        BmiAPIService.BMI_API_SERVICE.getBmiByUser_ID(UserSecurePreferencesManager.getUser().getUser_id())

                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseObject>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull ResponseObject responseObject) {

                        String bmiJson = gson.toJson(responseObject.getData());
                        if (bmiJson != null){
                            editor.putString(Validation.KEY_BMI, bmiJson);
                            editor.apply();
                            bmiRequest = gson.fromJson(bmiJson,BmiRequest.class);
                            bindingData();
                        }
                        isLoadCalories.set(true);
                    }
                    @Override
                    public void onError(@NonNull Throwable e) {
                        isLoadCalories.set(false);
                    }

                    @Override
                    public void onComplete() {
                        isLoadCalories.set(true);
                    }
                });
    }
    public void bindingData(){

        weight.set(bmiRequest.getWeight()+" Kg");
        height.set(bmiRequest.getHeight()+" cm");
        age.set(bmiRequest.getAge());
        gender.set(bmiRequest.getGender().name());
        total_calories.set("You Need To Eat: "+String.valueOf(bmiRequest.getCalories().intValue()));
        isLoadCalories.set(true);
    }
    public void bindingCaloriesToday( BmiRequest bmiRequest){

        weight.set(this.bmiRequest.getWeight()+" kg");
        height.set(this.bmiRequest.getHeight()+" cm");
        age.set(this.bmiRequest.getAge());
        gender.set(this.bmiRequest.getGender().name());
        total_calories.set("Today You ate: " + bmiRequest.getCalories().intValue() + "/"+bmiRequest.getCalories().intValue());
        isLoadCalories.set(true);
    }
    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();
    }
}
