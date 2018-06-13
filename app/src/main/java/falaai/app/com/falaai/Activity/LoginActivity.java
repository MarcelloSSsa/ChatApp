package falaai.app.com.falaai.Activity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;

import java.util.Random;

import falaai.app.com.falaai.R;
import falaai.app.com.falaai.helper.Permissao;
import falaai.app.com.falaai.helper.Preferencias;

public class LoginActivity extends Activity {

    private EditText email;
    private EditText senha;
    private Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.emailId);
        senha = findViewById(R.id.senhaId);
        login = findViewById(R.id.botaoEntrar);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
    public void abrirCadastroUsuario(View view){
            Intent intent = new Intent(LoginActivity.this, CadastroUsuario.class);
            startActivity(intent);
    }
}