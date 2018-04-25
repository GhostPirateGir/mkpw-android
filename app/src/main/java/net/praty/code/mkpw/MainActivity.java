package net.praty.code.mkpw;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Switch;
import android.widget.TextView;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void createPassword(View view) {
        final String MD5 = "MD5";
        try {
            MessageDigest digest = java.security.MessageDigest.getInstance(MD5);
            EditText masterE = (EditText) findViewById(R.id.master);
            EditText domainE = (EditText) findViewById(R.id.domain);
            String domain = domainE.getText().toString().trim();
            String master = masterE.getText().toString();
            String digestInput = domain + ":" + master;
            digest.update(digestInput.getBytes());
            int encFlags = Base64.DEFAULT;
            Switch altAlphaS = (Switch) findViewById(R.id.switch_alternative_alpha);
            if (altAlphaS.isChecked()) {
                encFlags += Base64.URL_SAFE;
            }
            byte prepass[] = android.util.Base64.encode(digest.digest(), encFlags);
            String password = new String(Arrays.copyOfRange(prepass, 0, 10));
            TextView passwordE = (TextView) findViewById(R.id.password);
            passwordE.setText(password);
            masterE.setText(null);
        }
        catch (NoSuchAlgorithmException e) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("Error");
            builder.setMessage("Unsupported hash algorithm MD5");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // nothing to do, just close the dialog
                }
            });
            AlertDialog dialog = builder.create();
        }
    }

    public void clearValues(View view) {
        EditText domainE = (EditText) findViewById(R.id.domain);
        EditText masterE = (EditText) findViewById(R.id.master);
        TextView passwordT = (TextView) findViewById(R.id.password);

        domainE.setText(null);
        masterE.setText(null);
        passwordT.setText(null);
    }

    public Context getActivity() {
        return this.getActivity();
    }
}
