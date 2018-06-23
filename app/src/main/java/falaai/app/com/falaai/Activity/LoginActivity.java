package falaai.app.com.falaai.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import falaai.app.com.falaai.R;
import falaai.app.com.falaai.model.Usuario;

public class LoginActivity extends Activity {

    private EditText email;
    private EditText senha;
    private Button botaoLogin;
    private Usuario usuario;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        verificarUsuarioLogado();

        email = findViewById(R.id.emailId);
        senha = findViewById(R.id.senhaId);
        botaoLogin = findViewById(R.id.botaoEntrar);

        botaoLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usuario = new Usuario();
                usuario.setEmail(email.getText().toString());
                usuario.setSenha(senha.getText().toString());
                validarLogin();
            }
        });
    }

    private void validarLogin() {
        mAuth.signInWithEmailAndPassword(usuario.getEmail().toString(), usuario.getSenha().toString())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            abrirTelaPrincipal();
                        }
                        else {
                            Toast.makeText(LoginActivity.this, "Não foi possível entrar, verifique se tem uma conta.", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    private void abrirTelaPrincipal() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void verificarUsuarioLogado(){
        if (mAuth.getCurrentUser() != null){
            abrirTelaPrincipal();
        }
    }

    public void abrirCadastroUsuario(View view){
            Intent intent = new Intent(LoginActivity.this, CadastroUsuario.class);
            startActivity(intent);
    }
}