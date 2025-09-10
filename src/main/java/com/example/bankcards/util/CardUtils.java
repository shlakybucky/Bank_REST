package com.example.bankcards.util;

public class CardUtils {
    public static String maskCardNumber(String number){
        if(number == null || number.length() < 4) {
            return "****";
        }else{
            return "**** **** ****" + number.substring(number.length() - 4);
        }
    }
}
