package com.nativeninjas.vista;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.telephony.TelephonyManager;

import java.lang.reflect.Method;

public class RejectCallActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Aquí puedes realizar la lógica para rechazar la llamada
        // Por ejemplo, puedes silenciar el teléfono o enviar la llamada a correo de voz
        // También puedes realizar otras acciones necesarias

        // Crear una instancia de la clase CallHelper y llamar al método endCall()
        CallHelper callHelper = new CallHelper(this);
        callHelper.endCall();
    }

    // Clase interna para ayudar a manejar la llamada
    private class CallHelper {
        private Context context;
        private TelephonyManager telephonyManager;

        public CallHelper(Context context) {
            this.context = context;
            telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        }

        public void endCall() {
            try {
                Class<?> telephonyClass = Class.forName(telephonyManager.getClass().getName());
                Method methodEndCall = telephonyClass.getDeclaredMethod("endCall");
                methodEndCall.invoke(telephonyManager);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
