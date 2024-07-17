package com.project.smartfrigde.view;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.credentials.CredentialManager;
import androidx.credentials.CredentialManagerCallback;
import androidx.credentials.GetCredentialRequest;
import androidx.credentials.GetCredentialResponse;
import androidx.credentials.exceptions.GetCredentialException;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.Observable;

import com.google.android.libraries.identity.googleid.GetGoogleIdOption;
import com.google.common.reflect.TypeToken;
import com.project.smartfrigde.R;
import com.project.smartfrigde.databinding.ActivityLoginBinding;
import com.project.smartfrigde.model.Food;
import com.project.smartfrigde.utils.ProgressDialog;
import com.project.smartfrigde.utils.SecurePreferencesManager;
import com.project.smartfrigde.utils.UserSecurePreferencesManager;
import com.project.smartfrigde.utils.Validation;
import com.project.smartfrigde.viewmodel.HomeViewmodel;
import com.project.smartfrigde.viewmodel.LoginViewModel;

import java.lang.reflect.Type;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class LoginActivity extends AppCompatActivity {
    private ProgressDialog progressDialog;
    private LinearLayout groupImages;
    private LinearLayout groupText;
    ActivityLoginBinding loginBinding;
    LoginViewModel loginViewModel;
    private SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         sharedPreferences = getSharedPreferences(Validation.PREF_NAME, Context.MODE_PRIVATE);
        progressDialog = new ProgressDialog(LoginActivity.this,R.layout.progressdialog);
        loginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        SecurePreferencesManager.init(this);
        UserSecurePreferencesManager.init(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        loginViewModel = new LoginViewModel(sharedPreferences);
        loginBinding.setLoginViewModel(loginViewModel);
        groupImages = findViewById(R.id.group_images);
        groupText = findViewById(R.id.group_text);

        loginViewModel.getClick_oauth2().addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                if ( Boolean.TRUE.equals(loginViewModel.getClick_oauth2().get())){
                    progressDialog.dismiss();
                    initiateGoogleSignIn(editor);
                }else {
                    progressDialog.show();
                }
                loginViewModel.getClick_oauth2().set(false);
            }
        });
        groupImages.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                groupImages.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                animateViews();
            }
        });
    }
    private void animateViews() {
        int parentHeight = ((View) groupImages.getParent()).getHeight();
        int groupImagesHeight = groupImages.getHeight();
        int groupTextHeight = groupText.getHeight();

        int margin = 20;
        int totalHeight = groupImagesHeight + groupTextHeight + margin;

        float groupImagesTranslationY = (parentHeight / 2f) - totalHeight / 2f - groupImages.getTop();
        float groupTextTranslationY = groupImagesTranslationY + groupImagesHeight + margin - groupText.getTop();

        ObjectAnimator animatorGroupImages = ObjectAnimator.ofFloat(groupImages, "translationY", groupImagesTranslationY);
        ObjectAnimator animatorGroupText = ObjectAnimator.ofFloat(groupText, "translationY", groupTextTranslationY);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(animatorGroupImages, animatorGroupText);
        animatorSet.setDuration(1000); // duration in milliseconds
        animatorSet.start();
    }
    private void initiateGoogleSignIn(SharedPreferences.Editor editor){
        Executor executor = Executors.newSingleThreadExecutor();
        CredentialManager credentialManager = CredentialManager.create(LoginActivity.this);
        GetGoogleIdOption googleIdOption = new GetGoogleIdOption.Builder()
                .setFilterByAuthorizedAccounts(false)
                .setServerClientId("756081284225-qk5ijqli6cuope3q2j3mdlm2ckgtvedb.apps.googleusercontent.com")
                .setAutoSelectEnabled(false)
                .build();
        GetCredentialRequest request = new GetCredentialRequest.Builder()
                .addCredentialOption(googleIdOption)
                .build();
        credentialManager.getCredentialAsync(
                LoginActivity.this,
                request,
                new CancellationSignal(),
                executor,
                new CredentialManagerCallback<GetCredentialResponse, GetCredentialException>() {
                    @Override
                    public void onResult(GetCredentialResponse result) {
                        if (loginViewModel.handleSignIn(editor,result)){
                            String jsonDeviceItems = sharedPreferences.getString(Validation.KEY_DEVICE_ITEMS, null);
                            if (jsonDeviceItems == null){
                                loginViewModel.getListDeviceItemByUserID(editor,UserSecurePreferencesManager.getUser().getUser_id());
                            }
                            loginViewModel.getUserLiveData().addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
                                @Override
                                public void onPropertyChanged(Observable sender, int propertyId) {
                                    checkAndNavigate(loginViewModel,LoginActivity.this);
                                }
                            });
                        }else {
                            Log.e("LoginActivity", "Login failed: " + result.toString());
                        }
                    }

                    @Override
                    public void onError(GetCredentialException e) {
                        Log.e("LoginActivity", "Error getting Google credentials: " + e.getMessage());
                        loginViewModel.setMessage(e);
                    }
                });
        loginViewModel.is_loadded_data.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                if (Boolean.TRUE.equals(loginViewModel.is_loadded_data.get())){
                    checkAndNavigate(loginViewModel,LoginActivity.this);
                }
            }
        });
    }
    private void checkAndNavigate(LoginViewModel viewModel, Context context) {
        if (Boolean.TRUE.equals(viewModel.is_loadded_data.get()) &&
                viewModel.getUserLiveData().get() != null) {
                progressDialog.dismiss();
                Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
                startActivity(intent);
        }
    }
}