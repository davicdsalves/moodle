package br.com.unirio.moodle.activity;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import br.com.unirio.moodle.R;
import br.com.unirio.moodle.client.MoodleClient;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class LoginActivity extends Activity {

    @InjectView(R.id.editTextEmail)
    private EditText mEditTextEmail;

    @InjectView(R.id.editTextPassword)
    private EditText mEditTextPassword;

    @InjectView(R.id.buttonLogin)
    private Button mButtonLogin;

    private String mEmail;
    private String mPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.inject(this);
    }

    @OnClick(R.id.buttonLogin)
    public void onLoginClick() {
        new AuthenticateTask().execute();
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
