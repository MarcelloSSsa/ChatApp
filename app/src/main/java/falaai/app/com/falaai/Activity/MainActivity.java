package falaai.app.com.falaai.Activity;

import android.app.Activity;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;

import falaai.app.com.falaai.R;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
