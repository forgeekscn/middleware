package cn.forgeeks.awesome.testnull.common;

import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Test1 {

    public static void main(String[] args) {

        // string
        String a = null;
        print(Optional.ofNullable(a).isPresent());
        print(Optional.ofNullable(a).orElse(""));

        // integer
        Integer b= null;
        print(b);
        print(Optional.ofNullable(b).isPresent());

        // stream

        List<String> c = new ArrayList<>();
        c.add("aaa");
        c.add("aaa");
        c.stream().filter(k->k.contains("b")).forEach(k->print(k));
        c.stream().filter(k->k.contains("b")).map(k->k).collect(Collectors.toList());

        //

        Object a1="11";
        Integer b1= Integer.valueOf(String.valueOf(a1));
        print(b1);

        Object a2="11.1";
        Double b2= Double.valueOf(String.valueOf(a2));
        Float b3= Float.parseFloat(String.valueOf(a2));
        print(b2);

    }


    public static  <T> void print(T t){
        if(null==t){
            System.out.println("### null obj");
        }else{
            System.out.println(JSONObject.toJSONString(t));
        }
    }



}
