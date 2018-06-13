package falaai.app.com.falaai.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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
        fire
    }
}
