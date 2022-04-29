package com.example.testing;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Timer;
import java.util.TimerTask;

public class Dashboard extends AppCompatActivity {
    TextView message;
    TimePicker alarmTime;
    TextClock currentTime;
    Button setAlarm, stopAlarm, changeRing, logout;
    Ringtone ring;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        ring = RingtoneManager.getRingtone(getApplicationContext(), RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE));
        NotificationCompat.Builder builder = new NotificationCompat.Builder(Dashboard.this, "My Notification");
        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(Dashboard.this);

        message = findViewById(R.id.welcomeMessage);
        currentTime = findViewById(R.id.currentTime);
        stopAlarm = findViewById(R.id.stopAlarm);
        setAlarm = findViewById(R.id.setAlarm);
        changeRing = findViewById(R.id.btnSelRingtone);
        logout = findViewById(R.id.logoutBtn);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("My Notification", "notify", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (currentTime.getText().equals(getAlarmTime())) {
                    ring.play();
                    builder.setContentTitle("Lucid Dreaming Alarm");
                    builder.setContentText("This is a lucid trigger... go back to sleep to dream.");
                    builder.setSmallIcon(R.drawable.ic_launcher_background);
                    builder.setAutoCancel(true);
                    builder.setContentIntent(goToDash());
                    builder.setDefaults(Notification.DEFAULT_VIBRATE);
                    builder.setAutoCancel(true);
                    managerCompat.notify(1, builder.build());
                } else {
                    ring.stop();
                    managerCompat.cancelAll();
                }
            }
        }, 0, 1000);

        logout.setOnClickListener(view -> logUserOut());
        setAlarm.setOnClickListener(view -> alarmTime = findViewById(R.id.configAlarm));
        stopAlarm.setOnClickListener(view -> alarmTime = null);
        changeRing.setOnClickListener(view -> {
            final Uri currentTone= RingtoneManager.getActualDefaultRingtoneUri(Dashboard.this, RingtoneManager.TYPE_ALARM);
            Intent intent = new Intent(RingtoneManager.ACTION_RINGTONE_PICKER);
            intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TYPE, RingtoneManager.TYPE_RINGTONE);
            intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TITLE, "Select Tone");
            intent.putExtra(RingtoneManager.EXTRA_RINGTONE_EXISTING_URI, currentTone);
            intent.putExtra(RingtoneManager.EXTRA_RINGTONE_SHOW_SILENT, false);
            intent.putExtra(RingtoneManager.EXTRA_RINGTONE_SHOW_DEFAULT, true);
            startActivity(intent);

        });
    }



    public String getAlarmTime() {
        if (!(alarmTime == null)) {
            Integer hour = alarmTime.getCurrentHour();
            Integer min = alarmTime.getCurrentMinute();

            if (hour > 12) {
                hour = hour - 12;
                return hour.toString().concat(":").concat(min.toString()).concat(" PM");
            } else
                return hour.toString().concat(":").concat(min.toString()).concat(" AM");
        }
        return null;
    }

    @SuppressLint("UnspecifiedImmutableFlag")
    private PendingIntent goToDash(){
        Intent gotoDash = new Intent(this, Dashboard.class);
        return PendingIntent.getActivity(this, 1, gotoDash, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    public void logUserOut(){
        startActivity(new Intent(this, Login.class));
    }
}