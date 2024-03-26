package com.nativeninjas.modelo.DAO;
import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;

public class DAOfactory {
    public UsuarioDAO getUsarioDAO(Context context){
        return new UsuarioDAO(context);

    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public PartidaDAO getPartidaDAO(Context context){
        return new PartidaDAO(context);

    }
}
