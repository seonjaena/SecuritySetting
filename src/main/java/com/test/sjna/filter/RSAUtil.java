package com.test.sjna.filter;

import javax.crypto.Cipher;
import javax.servlet.http.HttpServletRequest;
import java.security.PrivateKey;

public class RSAUtil {
    public final static String RSA_INSTANCE = "RSA";
    public final static String RSA_WEB_KEY = "_RSA_WEB_Key_";

    public static String decryptRsa(HttpServletRequest request, String securedValue) throws Exception {
        if(securedValue == null || securedValue.isEmpty()) return securedValue;

        PrivateKey privateKey = (PrivateKey)request.getSession().getAttribute(RSA_WEB_KEY);

        return decryptRsa(privateKey, securedValue);
    }

    public static String decryptRsa(PrivateKey privateKey, String securedValue) throws Exception {
        Cipher cipher = Cipher.getInstance(RSA_INSTANCE);
        byte[] encryptedBytes = hexToByteArray(securedValue);
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
        String decryptedValue = new String(decryptedBytes, "utf-8");
        return decryptedValue;
    }


    /**
     * 16진 문자열을 byte 배열로 변환한다.
     *
     * @param hex
     * @return
     */
    private static byte[] hexToByteArray(String hex) {
        if (hex == null || hex.length() % 2 != 0) { return new byte[] {}; }

        byte[] bytes = new byte[hex.length() / 2];
        for (int i = 0; i < hex.length(); i += 2) {
            byte value = (byte) Integer.parseInt(hex.substring(i, i + 2), 16);
            bytes[(int) Math.floor(i / 2)] = value;
        }
        return bytes;
    }

}

