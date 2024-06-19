package com.project.smartfrigde.utils;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

public class ProgressDialog {
    private Dialog progressDialog;
    private Context context;

    private int resource;

    public ProgressDialog(Context context, int resource) {
        this.context = context;
        this.resource = resource;

    }
    public Context getContext(){
        return  progressDialog.getContext();
    }
    public void show() {
        progressDialog = new Dialog(context);
        progressDialog.setCancelable(false);
        View view = LayoutInflater.from(context).inflate(resource, null);
        progressDialog.setContentView(view);
        progressDialog.show();
    }

    public void dismiss() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }
}
