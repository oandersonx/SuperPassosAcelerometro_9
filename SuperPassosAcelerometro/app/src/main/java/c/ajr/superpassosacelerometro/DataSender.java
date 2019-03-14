package c.ajr.superpassosacelerometro;

import android.os.AsyncTask;

import java.io.DataOutput;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by Anderson on 12/03/2019.
 */

public class DataSender extends AsyncTask<String, Void, Void> {
    Socket s;
    DataOutput dos;
    PrintWriter pw;

    @Override
    protected Void doInBackground(String... voids) {

        String message = voids[0];

        try {
            s = new Socket("192.168.43.174",7801);
            pw = new PrintWriter(s.getOutputStream());
            pw.write(message);
            pw.flush();
            pw.close();
            s.close();

        }catch (IOException e){
            e.printStackTrace();
        }



        return null;
    }
}
