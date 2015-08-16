package htp.skout;

import android.util.JsonReader;

import com.google.android.gms.maps.model.LatLng;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.InputStreamReader;
import java.net.URI;

/**
 * Created by TheFreshDuke on 8/15/15.
 */
public class Weather {

    public static Tuple<String, Double[]> getWeather (LatLng xy) {

        HttpClient client = new DefaultHttpClient();//initClient();
        HttpGet request = new HttpGet();

        String apiURL = "api.openweathermap.org/data/2.5/weather?lat=" + xy.latitude + "&lon=" + xy.longitude;
        try {
            request.setURI(new URI(apiURL));
            HttpResponse response = client.execute(request);
            JsonReader reader = new JsonReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));

            String desc = null;
            Double[] highLow = new Double[2];

            reader.beginObject();
            while (reader.hasNext()) {
                String temp = reader.nextName();
                if (temp.equals("description")) {
                    desc = reader.nextString();
                } else if (temp.equals("temp_min")) {
                    highLow[0] = Double.parseDouble(reader.nextString());
                } else if (temp.equals("temp_max")) {
                    highLow[1] = Double.parseDouble(reader.nextString());
                } else {
                    reader.skipValue();
                }
            }
            reader.endObject();
            return new Tuple<>(desc, highLow);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
