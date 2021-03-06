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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import falaai.app.com.falaai.R;
import falaai.app.com.falaai.helper.Base64Custom;
import falaai.app.com.falaai.helper.Preferencias;
import falaai.app.com.falaai.model.Usuario;

public class LoginActivity extends Activity {

    private EditText email;
    private EditText senha;
    private Button botaoLogin;
    private Usuario usuario;
    private FirebaseAuth mAuth;
    private String idUsuarioLogado;

    private DatabaseReference mDatabase;


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

                            //Recuperar dados do usuário
                            idUsuarioLogado = Base64Custom.converterBase64(usuario.getEmail());
                            mDatabase = FirebaseDatabase.getInstance().getReference()
                                    .child("Usuarios").child(idUsuarioLogado);

                            mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                    Usuario usuario = dataSnapshot.getValue(Usuario.class);

                                    //Salvar email nas preferências
                                    String identificadorUsuarioLogado = Base64Custom.converterBase64(usuario.getEmail().toString());
                                    Preferencias preferencias = new Preferencias(LoginActivity.this);
                                    preferencias.salvarDados(identificadorUsuarioLogado, usuario.getNome());

                                    abrirTelaPrincipal();
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });


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