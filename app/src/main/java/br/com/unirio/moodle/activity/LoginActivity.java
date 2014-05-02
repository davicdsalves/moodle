package br.com.unirio.moodle.activity;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
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
    public EditText mEditTextEmail;

    @InjectView(R.id.editTextPassword)
    public EditText mEditTextPassword;

    @InjectView(R.id.buttonLogin)
    public Button mButtonLogin;

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
        Editable emailEditable = mEditTextEmail.getEditableText();
        Editable passwordEditable = mEditTextPassword.getEditableText();
        if (emailEditable != null && passwordEditable != null) {
            mEmail = emailEditable.toString();
            mPassword = passwordEditable.toString();
            new AuthenticateTask().execute();
        }
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
