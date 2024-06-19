package com.project.smartfrigde.service;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;

public class JobSchedulerHelper {
    public static void scheduleJob(Context context) {
        ComponentName serviceComponent = new ComponentName(context, SaveDataJobService.class);
        JobInfo.Builder builder = new JobInfo.Builder(0, serviceComponent);

        builder.setPersisted(true);
        builder.setRequiresDeviceIdle(false);
        builder.setRequiresCharging(false);
        builder.setPeriodic(24 * 60 * 60 * 1000); // 24 giờ

        JobScheduler jobScheduler = context.getSystemService(JobScheduler.class);
        jobScheduler.schedule(builder.build());
    }
}
