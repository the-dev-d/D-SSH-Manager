package in.dhaivathlal.dsshmanager;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import java.net.InetSocketAddress;
import java.net.Socket;

public class AsyncUpChecker extends AsyncTask<String, Void, Boolean> {
    @Override
    protected Boolean doInBackground(String... strings) {

        try {
            InetSocketAddress socketAddress = new InetSocketAddress(strings[0], 22);
            Socket socket = new Socket();
            socket.connect(socketAddress, 3000);
            return true;
        }catch (Exception e) {
            Log.d("ex", e.toString());
        }

        return false;
    }
}
