package com.periodicals.util;

import com.liqpay.LiqPayUtil;

public class LiqPayService {

    private final static String PRIVATE_KEY = "sandbox_6a5vVJL8YvUtlmIINZsAiZsayb33EdOSgy7QcqWK";

    public static String createSignatureFromJSONString(String jsonDataString) {
        return createSignature(createEncodedData(jsonDataString));
    }

    public static String createSignature(String base64EncodedData) {
        return str_to_sign(PRIVATE_KEY + base64EncodedData + PRIVATE_KEY);
    }

    private static String str_to_sign(String str) {
        return LiqPayUtil.base64_encode(LiqPayUtil.sha1(str));
    }

    public static String createEncodedData(String jsonDataString) {
        return LiqPayUtil.base64_encode(jsonDataString);
    }

}