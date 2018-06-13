package falaai.app.com.falaai.helper;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

public class Preferencias {
    private Context contexto;
    private SharedPreferences preferences;
    private static final String NOME_ARQUIVO = "falaai.preferencias";
    private static final String CHAVE_NOME = "nome";
    private static final String CHAVE_TELEFONE = "telefone";
    private static final String CHAVE_TOKEN = "token";
    private static final int MODE = 0;
    private SharedPreferences.Editor editor;

    public Preferencias(Context conetextoParametro) {
        contexto = conetextoParametro;
        preferences = contexto.getSharedPreferences(NOME_ARQUIVO, MODE);
        editor = preferences.edit();
    }

    public void salvarUsuarioPreferencias(String nome, String telefone, String token){
        editor.putString(CHAVE_NOME, nome);
        editor.putString(CHAVE_TELEFONE, telefone);
        editor.putString(CHAVE_TOKEN, token);
        editor.commit();
    }

    public HashMap<String,String> getDadosUsuario() {

        HashMap<String, String> dadosUsuario = new HashMap<>();

        dadosUsuario.put(CHAVE_NOME, preferences.getString(CHAVE_NOME, null));
        dadosUsuario.put(CHAVE_TELEFONE, preferences.getString(CHAVE_TELEFONE, null));
        dadosUsuario.put(CHAVE_TOKEN, preferences.getString(CHAVE_TOKEN, null));

        return dadosUsuario;
    }
}
