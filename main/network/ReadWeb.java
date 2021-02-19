package network;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;

/*
 * REFERENCES:
 * --parse JSON
 * https://www.geeksforgeeks.org/parse-json-java/
 * --read web
 * Shamelessly copied from
 * https://stackoverflow.com/questions/4308554/simplest-way-to-read-json-from-a-url-in-java
 *
 */

public class ReadWeb {
    private String apikey = "fa3adc9c44ef42e0b7450ed22efe342f";
    private String vancouverQ = "https://api.openweathermap.org/data/2.5/weather?q=Vancouver,CAN&appid="; //this can point to any URL
    private String theURL = vancouverQ + apikey;

    //EFFECT: return the temperature in Vancouver right now, in one decimal place, in Celsius
    public String getCurrentTempInVancouver() throws IOException, JSONException {
        JSONObject json = readJsonFromUrl(theURL);
        JSONObject main = (JSONObject) json.get("main");
        double kelvinD = 987654;
        int kelvinI = 987654;
        double c;
        try {
            kelvinD = (Double) main.get("temp");
        } catch (ClassCastException e) {
            kelvinI = (Integer) main.get("temp");
        }
        if (kelvinD != 987654) {
            c = kelvinD - 273.15;
        } else {
            c = kelvinI - 273.15;
        }
        double ccOneDecimal = Math.round(c * 10) / 10.0;
        return ccOneDecimal + "°C";
    }

    private String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    private JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
        try (InputStream is = new URL(url).openStream()) {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            String jsonText = readAll(rd);
            return new JSONObject(jsonText);
        }
    }


}
