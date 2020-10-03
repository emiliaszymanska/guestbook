package org.example.helpers;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

public class Parser {

    public static Map<String, String> parseData(String data) throws UnsupportedEncodingException {
        Map<String, String> map = new HashMap<>();
        String[] pairs = data.split("&");

        for (String pair : pairs) {
            String[] keyValue = pair.split("=");
            String value = new URLDecoder().decode(keyValue[1], "UTF-8");

            map.put(keyValue[0], value);
        }
        return map;
    }
}
