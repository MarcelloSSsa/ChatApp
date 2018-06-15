package falaai.app.com.falaai.Activity;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import falaai.app.com.falaai.R;
import falaai.app.com.falaai.model.Usuario;

public class CadastroUsuario extends AppCompatActivity {

    private EditText nomeUsuario;
    private EditText emailUsuario;
    private EditText senhaUsuario;
    private Button cadastrarUsuario;
    private Usuario usuario;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_usuario);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference mref = database.getReference("");

        nomeUsuario = findViewById(R.id.NomeCadId);
        emailUsuario = findViewById(R.id.emailCadId);
        senhaUsuario = findViewById(R.id.senhaCadId);
        cadastrarUsuario = findViewById(R.id.botaoCadId);

        cadastrarUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usuario = new Usuario();
                usuario.setNome(nomeUsuario.getText().toString());
                usuario.setEmail(emailUsuario.getText().toString());
                usuario.setSenha(senhaUsuario.getText().toString());
                
                cadastrarUsuario();
            }
        });
    }

    private void cadastrarUsuario() {
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.createUserWithEmailAndPassword(usuario.getEmail(), usuario.getSenha())
                .addOnCompleteListener(CadastroUsuario.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) { //TRUE, Sucesso ao criar o usuário

                    usuario.setId(task.getResult().getUser().getUid().toString());
                    usuario.salvar();
                    finish();
                    Toast.makeText(CadastroUsuario.this, "Usuário cadastrado com sucesso.", Toast.LENGTH_SHORT).show();
                }

                else {
                    Toast.makeText(CadastroUsuario.this, "Erro ao cadastrar", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
