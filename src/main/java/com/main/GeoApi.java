package com.main;

import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

public class GeoApi {

    public String getTimeZoneByLongitudeLatitudeCoordinates(String latitude, String longitude) throws IOException {

        String encodedLatitude = encodeUrlParam(latitude);
        String encodedLongitude = encodeUrlParam(longitude);
        String encodedUsername = encodeUrlParam("sushant786");

        String urlString = "http://api.geonames.org/timezoneJSON?lat=" + encodedLatitude + "&lng=" + encodedLongitude + "&username=" + encodedUsername;

        URL url = new URL(urlString);
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

        try {
            InputStream in = url.openStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in, Charset.forName("UTF-8")));
            String jsonText = readDataFromBuffer(reader);
            JSONObject json = new JSONObject(jsonText);
            return Optional.ofNullable(json.get("timezoneId").toString())
                    .orElse(null);
        } finally {
            urlConnection.disconnect();
        }
    }

    private String encodeUrlParam(String value) {
        try {
            return URLEncoder.encode(value, StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException ex) {
            throw new RuntimeException(ex.getCause());
        }
    }

    private String readDataFromBuffer(BufferedReader reader) throws IOException {
        StringBuffer sb = new StringBuffer();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        return sb.toString();
    }
}
