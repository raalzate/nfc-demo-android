package com.espaciounido.demonfc;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Spinner;

import java.io.PrintStream;


public class MainActivity extends AppCompatActivity {

    PrintStream console = System.out;
    private NfcAdapter mNfcAdapter;
    private INfcView iNfcView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);

        // Setup spinner
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setAdapter(new MyAdapter(
                toolbar.getContext(),
                new String[]{
                        "Obtener UID"
                }));

        spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                iNfcView = PlaceholderFragment.newInstance(position + 1);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, iNfcView.getFragment())
                        .commit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }



    public void onResume() {
        super.onResume();
        setupForegroundDispatch(this, mNfcAdapter);
    }


    private void resolveIntent(Intent intent) {
        String action = intent.getAction();
        if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(action)
                || NfcAdapter.ACTION_TECH_DISCOVERED.equals(action)
                || NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action)) {

            new NdefReaderTask().execute(intent);

        }
    }


    public static void setupForegroundDispatch(final Activity activity, NfcAdapter adapter) {
        final Intent intent = new Intent(activity, activity.getClass());
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

        final PendingIntent pendingIntent = PendingIntent.getActivity(activity, 0, intent, 0);

        adapter.enableForegroundDispatch(activity, pendingIntent, null, null);
    }


    private class NdefReaderTask extends AsyncTask<Intent, Void, String> {
        @Override
        protected String doInBackground(Intent... params) {
            Intent intent = params[0];
            Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);

            String action = intent.getAction();
            String tagToString = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG).toString();
            if (tag != null) {
                byte[] tagId = tag.getId();
                return "UID: " + Coverter.getHexString(tagId, tagId.length) +"" +
                                ", tagToString: " + tagToString + ", action: " + action;
            }
            return "UID: " + Coverter.getUid(intent);
        }

        @Override
        protected void onPostExecute(String s) {
            iNfcView.setText(s);
        }
    }

    @Override
    public void onNewIntent(Intent intent) {
        setIntent(intent);
        resolveIntent(intent);
    }
}
