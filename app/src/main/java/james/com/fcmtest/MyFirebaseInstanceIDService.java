package james.com.fcmtest;

/**
 * Created by R30 on 2016/9/14.
 */
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import org.json.JSONObject;

import java.io.IOException;


public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    private static final String TAG = "MyFirebaseIIDService";

    /**
     * Called if InstanceID token is updated. This may occur if the security of
     * the previous token had been compromised. Note that this is called when the InstanceID token
     * is initially generated so this is where you would retrieve the token.
     */
    // [START refresh_token]
    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);

        final Intent intent = new Intent("tokenReceiver");
        // You can also include some extra data.
        final LocalBroadcastManager broadcastManager = LocalBroadcastManager.getInstance(this);
        intent.putExtra("token",refreshedToken);
        broadcastManager.sendBroadcast(intent);


        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        try {
            sendRegistrationToServer(refreshedToken);
        }catch(Exception e){
            Log.d(TAG," register fail : "+e.getMessage());
        }
    }
    // [END refresh_token]

    /**
     * Persist token to third-party servers.
     *
     * Modify this method to associate the user's FCM InstanceID token with any server-side account
     * maintained by your application.
     *
     * @param token The new token.
     */
    private void sendRegistrationToServer(String token)throws Exception {
       String deviceToken = "c0U0vd-QLZ4:APA91bFYIijijLhN1Df7DqJjdVbE8AdLvYbRbdwNKwPX8lgbUiE8bqHTij0E1ymdXI3bb0-rcJwOnzUBnBECgbezMvv89m0ce9awFFmqHiiJ-wxadgYjBVtKIbVRM2b6P-JrO4BxZVta";
        JSONObject json = new JSONObject();
        json.put("clientpaltform","android");
        json.put("clienttoken",token);
        json.put("devicetoken",deviceToken);
        json.put("packagename","james.com.fcmtest");
        OKHttp http = new OKHttp();
        String result = http.post(getResources().getString(R.string.registerUrl),json.toString());
        Log.d(TAG," register result : "+result);
    }
}