package cn.sdt.pushclient;

import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import cn.sdt.pushclient.connect.ConnectionManager;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button btnConnect;
    Button btnSend;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnConnect = findViewById(R.id.connect);
        btnSend = findViewById(R.id.send);
        progressBar = findViewById(R.id.progress);
        btnConnect.setOnClickListener(this);
        btnSend.setOnClickListener(this);

        IntentFilter intentFilter=new IntentFilter();
        intentFilter.addAction(Constants.ACTION_SHOW_NOTIFICATION);
        LocalBroadcastManager.getInstance(this).registerReceiver(new LocalReceiver(),intentFilter);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.connect) {
            connect();
        } else if (v.getId() == R.id.send) {
            send();
        }
    }

    private void send() {

    }

    private void connect() {
        ConnectionManager.getInstance().connect();
    }


    @Override
    protected void onDestroy() {
        ConnectionManager.getInstance().disconnect();
        super.onDestroy();
    }
}
