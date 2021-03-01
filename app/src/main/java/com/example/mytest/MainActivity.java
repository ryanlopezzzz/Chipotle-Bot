package com.example.mytest;
import android.os.Bundle;
import android.content.Intent;
import android.widget.Toast;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import android.telephony.SmsManager;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LocalBroadcastManager.getInstance(this).registerReceiver(onNotice, new IntentFilter("Msg"));
    }

    public void sendSMS(String myText) {
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage("888222", null, myText, null, null);
            Toast.makeText(getApplicationContext(), "SMS sent.",
                    Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(),
                    "SMS failed, please try again.",
                    Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }


    private BroadcastReceiver onNotice= new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            String pack = intent.getStringExtra("package");
            String text = intent.getStringExtra("text");

            if (pack.equals("com.twitter.android")) {
                int i = text.indexOf(" to 888222");
                if (i != -1) {
                    int j = text.lastIndexOf(" ", i - 1);
                    sendSMS(text.substring(j + 1, i));
                }
            }
        }
    };
}



