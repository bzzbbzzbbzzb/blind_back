package com.blind.book.constant;

import com.blind.book.domain.WebSocket;

import java.util.HashMap;
import java.util.HashSet;

public class WSMap{
    public static HashMap<String, WebSocket> wsMap = new HashMap<>();
    public static HashMap<Integer,String> bindMap =new HashMap<>();
    public static HashMap<String,WebSocket> notBindSet =new HashMap<>();
    public static boolean isGet = false;
}
