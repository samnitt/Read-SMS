package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        registerReceiver (broadcastReceiver, new IntentFilter("Transfer"));
        registerReceiver (broadcastReceiver1, new IntentFilter("Transfer1"));

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.RECEIVE_SMS)
        != PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[]{Manifest.permission.RECEIVE_SMS},1000);

        }

        Button button = findViewById(R.id.nameService);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startService(new Intent(getApplicationContext(),MyService.class));
            }
        });


        }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode==1000){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this, "PERMISSION GRANTED", Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(this, "PERMISSION NOT GRANTED", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle b = intent.getExtras();
            String message = b.getString("message");
            ((TextView)findViewById(R.id.nameMessage)).setText(message);
        }
    };

    BroadcastReceiver broadcastReceiver1 = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle b = intent.getExtras();
            String message = b.getString("message");
            ((TextView)findViewById(R.id.nameNumber)).setText(message);
        }
    };

}