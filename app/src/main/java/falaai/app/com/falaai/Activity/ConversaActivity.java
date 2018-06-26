package falaai.app.com.falaai.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import falaai.app.com.falaai.R;

public class ConversaActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private String nomeUsuarioDestinatario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversa);

        toolbar = findViewById(R.id.toobarConversas);

        Bundle extra = getIntent().getExtras();

        if (extra != null){
            nomeUsuarioDestinatario = extra.getString("Nome");
        }

        toolbar.setTitle(nomeUsuarioDestinatario);
        toolbar.setNavigationIcon(R.drawable.ic_action_arrow_left);
        setSupportActionBar(toolbar);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }
}
