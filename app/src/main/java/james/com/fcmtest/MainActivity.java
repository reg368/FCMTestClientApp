package james.com.fcmtest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONObject;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LocalBroadcastManager.getInstance(this).registerReceiver(tokenReceiver,
                new IntentFilter("tokenReceiver"));
        Button locationBtn = (Button)findViewById(R.id.btnDevice);

        locationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Thread thread = new Thread(requireThread);
                    thread.start();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    BroadcastReceiver tokenReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String token = intent.getStringExtra("token");
            if(token != null)
            {
                Log.d(TAG,"device token : "+token);
            }

        }
    };

    private void requireGPS(String token)throws Exception {
        OKHttp http = new OKHttp();
        String result = http.post(getResources().getString(R.string.registerUrl),"token",token);
        Log.d(TAG," register result : "+result);
    }

    private Runnable requireThread = new Runnable(){
        public void run(){
            OKHttp http = new OKHttp();
            String result = null;
            try {
                result = http.post(getResources().getString(R.string.requireGPSUrl),"token", FirebaseInstanceId.getInstance().getToken());
                Log.d(TAG," register result : "+result);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    };

}
