package com.xinzy.essence.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by xinzy on 17/1/21.
 */
public class Utils
{

    public static String md5(String str)
    {
        try
        {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.digest(str.getBytes());
            return new BigInteger(1, md.digest()).toString(16);
        } catch (NoSuchAlgorithmException e)
        {
            return "";
        }
    }
}
