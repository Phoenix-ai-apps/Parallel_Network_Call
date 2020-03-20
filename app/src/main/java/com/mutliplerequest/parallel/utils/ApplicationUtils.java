package com.mutliplerequest.parallel.utils;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

public class ApplicationUtils {

    public static String first20Char(String content){
        String value = "";
        if(!TextUtils.isEmpty(content) && content.length() > 20){
            for(int i = 0; i < 20; i++){
                value = value.concat(String.valueOf(content.charAt(i)));
            }
        }
        return value;
    }

    public  static  String get10thChar(String content){
        String value = "";
        if(!TextUtils.isEmpty(content) && content.length() > 10){
            value = String.valueOf(content.charAt(9));
            if(value.matches(" ")){
                value = "Whitespace";
            }
        }
        return value;
    }

    public static List<String> getEvery10thChar(String content){
        List<String> list = new ArrayList<>();
        String value = "";
        if(!TextUtils.isEmpty(content) && content.length() > 10){
            char[] ch = content.toCharArray();
            for (int i = 0 ; i < ch.length; i++){
                if(i != 0 && i%9 == 0){
                    value = String.valueOf(content.charAt(i));
                    if(value.matches(" ")){
                        value = "Whitespace";
                    }
                    list.add(value);
                }
            }
        }
        return list;
    }
}
