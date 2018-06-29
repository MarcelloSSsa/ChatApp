package falaai.app.com.falaai.Activity;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import falaai.app.com.falaai.R;
import falaai.app.com.falaai.adapter.MensagemAdapter;
import falaai.app.com.falaai.helper.Base64Custom;
import falaai.app.com.falaai.helper.Preferencias;
import falaai.app.com.falaai.model.Mensagem;

public class ConversaActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private String idUsuarioLogado;
    private String idUsuarioDestinatario;
    private String nomeUsuarioDestinatario;
    private EditText textoMensagem;
    private ImageButton botaoEnviar;
    private ListView listaConversa;
    private ArrayAdapter<Mensagem> arrayAdapter;
    private ArrayList<Mensagem> mensagens;
    private ValueEventListener valueEventListenerMensagem;

    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversa);

        toolbar = findViewById(R.id.toobarConversas);
        textoMensagem = findViewById(R.id.editMensagem);
        botaoEnviar = findViewById(R.id.botaoEnviar);
        listaConversa = findViewById(R.id.listViewMensagens);

        Preferencias preferencias = new Preferencias(ConversaActivity.this);
        idUsuarioLogado = preferencias.getIdentificador();

        final Bundle extra = getIntent().getExtras();

        if (extra != null){
            nomeUsuarioDestinatario = extra.getString("Nome");
            idUsuarioDestinatario = Base64Custom.converterBase64(extra.getString("Email"));
        }

        toolbar.setTitle(nomeUsuarioDestinatario);
        toolbar.setNavigationIcon(R.drawable.ic_action_arrow_left);
        setSupportActionBar(toolbar);

        mensagens = new ArrayList<>();

        /*arrayAdapter = new ArrayAdapter<>(
                ConversaActivity.this,
                android.R.layout.simple_list_item_1,
                mensagens
        );*/

        arrayAdapter = new MensagemAdapter(ConversaActivity.this, mensagens);
        listaConversa.setAdapter(arrayAdapter);

        mDatabase = FirebaseDatabase.getInstance().getReference()
                .child("Mensagens").child(idUsuarioLogado).child(idUsuarioDestinatario);

        valueEventListenerMensagem = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mensagens.clear();

                for (DataSnapshot dados :dataSnapshot.getChildren()){
                    Mensagem mensagem = dados.getValue(Mensagem.class);
                    mensagens.add(mensagem);
                }
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        mDatabase.addValueEventListener(valueEventListenerMensagem);

        botaoEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mensagemDigitada = textoMensagem.getText().toString();

                if (mensagemDigitada.isEmpty()){
                    Toast.makeText(ConversaActivity.this, "Digite uma mensagem.", Toast.LENGTH_SHORT).show();
                }
                else {
                    Mensagem mensagem = new Mensagem();
                    mensagem.setIdUsuario(idUsuarioLogado);
                    mensagem.setMensagem(mensagemDigitada);

                    salvaMensagemFirebase(idUsuarioLogado, idUsuarioDestinatario, mensagem);

                    textoMensagem.setText("");
                }
            }
        });

    }

    private boolean salvaMensagemFirebase(String idRemetente, String idDestinatario, Mensagem mensagem) {
        try{
            mDatabase = FirebaseDatabase.getInstance().getReference();
            mDatabase.child("Mensagens").child(idRemetente)
                                        .child(idDestinatario).push()
                                        .setValue(mensagem);
            return true;
        }
        catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }
}
