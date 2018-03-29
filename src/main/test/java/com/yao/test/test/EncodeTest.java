package com.yao.test.test;

import com.sun.xml.internal.bind.v2.runtime.output.Encoded;
import sun.nio.cs.UnicodeEncoder;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * Created by 单耀 on 2018/2/5.
 */
public class EncodeTest {
    public static void main(String[] args) {
        String str = "%3A";
        try {
//            UnicodeEncoder
            String re = URLDecoder.decode(str,"UTF-8");
            System.out.println(re);
            String ss = URLEncoder.encode(re,"UTF-8");
            System.out.println(ss);
            if (ss.equals(str)) {
                System.out.println("==");
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
