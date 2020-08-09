package com.example.myapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.EditText;
import android.widget.Toast;

public class MyReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        //Toast.makeText(context, "SMS RECEIVED", Toast.LENGTH_SHORT).show();
        if (intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")){
            Bundle bundle = intent.getExtras();
            SmsMessage[] msgs = null;
            String msg_from;
            String msg_body;

            if (bundle != null) {
                try {
                    Object[] pdus = (Object[]) bundle.get("pdus");
                    msgs = new SmsMessage[pdus.length];
                    for (int i = 0; i < msgs.length; i++) {
                        msgs[i] = SmsMessage.createFromPdu((byte[])pdus[i]);

                        msg_from = msgs[i].getOriginatingAddress();
                        msg_body = msgs[i].getMessageBody();

                        Intent transfer = new Intent("Transfer");
                        transfer.putExtra("message", msg_body);
                        context.sendBroadcast(transfer);

                        Intent transfer1 = new Intent("Transfer1");
                        transfer1.putExtra("message", msg_from);
                        context.sendBroadcast(transfer1);


                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }

    }
}
