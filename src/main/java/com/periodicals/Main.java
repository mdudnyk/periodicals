package com.periodicals;


import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws ParseException {
        String s = "{\"1\" : \"ііііфвфіє\"}";
        JSONParser sd = new JSONParser();
        JSONObject js = (JSONObject) sd.parse(s);
        System.out.println(js);
    }
}
