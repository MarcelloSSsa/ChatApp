package falaai.app.com.falaai.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.support.v7.widget.Toolbar;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;

import falaai.app.com.falaai.R;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
            case R.id.item_pesquisa:
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void deslogarUsuario() {
        mAuth.signOut();
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
