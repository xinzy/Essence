package com.xinzy.essence.util;

import java.security.MessageDigest;

/**
 * Created by xinzy on 17/1/21.
 */
public class Utils
{

    public static String md5(String string)
    {
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        try
        {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(string.getBytes());
            byte[] data = md.digest();
            int j = data.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (byte b : data)
            {
                str[k++] = hexDigits[b >>> 4 & 0xf];
                str[k++] = hexDigits[b & 0xf];
            }
            return new String(str);
        } catch (Exception e)
        {
            return "";
        }
    }
}
