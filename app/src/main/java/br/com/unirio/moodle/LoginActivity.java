package br.com.unirio.moodle;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import br.com.unirio.moodle.client.MoodleClient;

public class LoginActivity extends Activity {

    private EditText mEditTextEmail;
    private EditText mEditTextPassword;

    private Button mButtonLogin;

    private String mEmail;
    private String mPassword;
    private View.OnClickListener loginListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            new AuthenticateTask().execute();

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEditTextEmail = (EditText) findViewById(R.id.editTextEmail);
        mEditTextPassword = (EditText) findViewById(R.id.editTextPassword);
        mButtonLogin = (Button) findViewById(R.id.buttonLogin);

        mButtonLogin.setOnClickListener(loginListener);
    }

    private class AuthenticateTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            MoodleClient client = new MoodleClient();
            client.authenticate(mEmail, mPassword);
            return null;
        }
    }
}
