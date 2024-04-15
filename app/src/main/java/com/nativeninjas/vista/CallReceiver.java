package com.nativeninjas.vista;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.nativeninjas.prod1.R;

public class CallReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(TelephonyManager.ACTION_PHONE_STATE_CHANGED)) {
            String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
            if (state != null && state.equals(TelephonyManager.EXTRA_STATE_RINGING)) {
                String incomingNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
                Log.d("CallReceiver", "Incoming call from: " + incomingNumber);

                // Crear una notificación para la llamada entrante
                createNotification(context, incomingNumber);
            }
        }
    }

    private void createNotification(Context context, String incomingNumber) {
        // Crear un intent para la acción de contestar la llamada
        Intent answerIntent = new Intent(context, AnswerCallActivity.class);
        answerIntent.setAction("ANSWER_CALL");
        PendingIntent answerPendingIntent = PendingIntent.getActivity(context, 0, answerIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        // Crear un intent para la acción de rechazar la llamada
        Intent rejectIntent = new Intent(context, RejectCallActivity.class);
        rejectIntent.setAction("REJECT_CALL");
        PendingIntent rejectPendingIntent = PendingIntent.getActivity(context, 0, rejectIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        // Construir la notificación
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "incoming_call_channel")
                .setSmallIcon(R.drawable.telefono)
                .setContentTitle("Llamada entrante")
                .setContentText("Número: " + incomingNumber)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .addAction(R.drawable.aceptar, "Contestar", answerPendingIntent)
                .addAction(R.drawable.colgar, "Rechazar", rejectPendingIntent)
                .setAutoCancel(true);

        // Mostrar la notificación
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(1, builder.build());
    }
}