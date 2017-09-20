package com.example.cam;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.telephony.SmsManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
//import android.provider.Telephony;
import android.util.Log;
import android.telephony.SmsMessage ;
//import android.Telephony; 
public class SmsReceiver extends BroadcastReceiver {
        
        private static SmsListener mListener;

        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle data  = intent.getExtras();

            Object[] pdus = (Object[]) data.get("pdus");

            for(int i=0;i<pdus.length;i++){
                SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) pdus[i]);

                String sender = smsMessage.getDisplayOriginatingAddress();
                //You must check here if the sender is your provider and not another one with same text.

                String messageBody = smsMessage.getMessageBody();

                //Pass on the text to our listener.
                mListener.messageReceived(messageBody);
            }

        }

        public static void bindListener(SmsListener listener) {
            mListener = listener;
        }
    }