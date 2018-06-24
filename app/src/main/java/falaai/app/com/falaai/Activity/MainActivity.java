package falaai.app.com.falaai.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.support.v7.widget.Toolbar;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import falaai.app.com.falaai.R;
import falaai.app.com.falaai.adapter.TabAdapter;
import falaai.app.com.falaai.helper.Base64Custom;
import falaai.app.com.falaai.helper.Preferencias;
import falaai.app.com.falaai.helper.SlidingTabLayout;
import falaai.app.com.falaai.model.Contato;
import falaai.app.com.falaai.model.Usuario;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private Toolbar toolbar;
    private SlidingTabLayout slidingTabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        slidingTabLayout = findViewById(R.id.slideTab1);
        viewPager = findViewById(R.id.vpViewPagina);
        slidingTabLayout.setDistributeEvenly(true);

        TabAdapter tabAdapter = new TabAdapter(getSupportFragmentManager());
        viewPager.setAdapter(tabAdapter);
        slidingTabLayout.setViewPager(viewPager);
        slidingTabLayout.setSelectedIndicatorColors(ContextCompat.getColor(this, R.color.fontColorLogin));

        mAuth = FirebaseAuth.getInstance();

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Fala Ai");
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.item_sair:
                deslogarUsuario();
                return true;
            case R.id.item_adicionar:
                abrirAddContato();
                return true;
            case R.id.item_pesquisa:
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void abrirAddContato() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Novo Contato");
        alertDialog.setMessage("E-mail do usuário");
        alertDialog.setCancelable(false);

        final EditText editText = new EditText(this);

        alertDialog.setView(editText);

        //positive button click event
        alertDialog.setPositiveButton("Cadastrar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String emailContato = editText.getText().toString();
                if (emailContato.isEmpty()){
                    Toast.makeText(MainActivity.this, "Digite um email válido.", Toast.LENGTH_SHORT).show();
                }
                else {
                    final String identificadorContato = Base64Custom.converterBase64(emailContato);

                    mDatabase = FirebaseDatabase.getInstance().getReference();
                    mDatabase = mDatabase.child("Usuarios").child(identificadorContato);

                    mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.getValue() != null){
                                Usuario usuarioContato = new Usuario();
                                Contato contato = new Contato();
                                usuarioContato = dataSnapshot.getValue(Usuario.class);

                                Preferencias preferencias = new Preferencias(MainActivity.this);
                                String identificadorUsuarioLogado = preferencias.getIdentificador();

                                contato.setIdUsuario(identificadorContato);
                                contato.setNome(usuarioContato.getNome());
                                contato.setEmail(usuarioContato.getEmail());

                                /*
                                Obter a estrutura:
                                *Usuarios
                                *   email na base64
                                *       contato adicionado
                                 */
                                mDatabase = FirebaseDatabase.getInstance().getReference()
                                                                          .child("Contatos")
                                                                          .child(identificadorUsuarioLogado)
                                                                          .child(identificadorContato);
                                mDatabase.setValue(contato);

                            }
                            else {
                                Toast.makeText(MainActivity.this, "Usuário não cadastrado", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }
        });

        //negative button click event
        alertDialog.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        alertDialog.create();
        alertDialog.show();
    }

    private void deslogarUsuario() {
        mAuth.signOut();
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
