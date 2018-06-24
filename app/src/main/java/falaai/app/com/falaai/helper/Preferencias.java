package falaai.app.com.falaai.helper;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

public class Preferencias {
    private Context contexto;
    private SharedPreferences preferences;
    private static final String NOME_ARQUIVO = "falaai.preferencias";
    private static final String CHAVE_IDENTIFICADOR = "identificadorUsuario";

    private static final int MODE = 0;
    private SharedPreferences.Editor editor;

    public Preferencias(Context conetextoParametro) {
        contexto = conetextoParametro;
        preferences = contexto.getSharedPreferences(NOME_ARQUIVO, MODE);
        editor = preferences.edit();
    }

    public void salvarDados(String identificador){
        editor.putString(CHAVE_IDENTIFICADOR, identificador);
        editor.commit();
    }

    public String getIdentificador() {
        return preferences.getString(CHAVE_IDENTIFICADOR, null);
    }
}
