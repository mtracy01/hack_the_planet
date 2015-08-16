package htp.skout.Objects;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class SpeedLimitService extends Service {

    public static String speedLimitUrl = "http://www.overpass-api.de/api/xapi?*[maxspeed=*][bbox=5.6283473,50.5348043,5.6285261,50.534884]";

    public void getSpeedLimit() throws IOException {


        //Runnable runnable = new Runnable() throws IOException{


           // public void run() {
                URL url = new URL(speedLimitUrl);
                URLConnection uc = url.openConnection();
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(
                                uc.getInputStream()));
                String inputLine;

                while ((inputLine = in.readLine()) != null){
                    if (inputLine.contains("maxspeed")){
                        
                    }
                }
                in.close();
           //}




        //};
        //Thread thread = new Thread(runnable);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
