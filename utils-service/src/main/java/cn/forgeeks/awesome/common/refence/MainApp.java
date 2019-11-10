package cn.forgeeks.awesome.common.refence;

import com.alibaba.fastjson.JSON;

import java.util.HashMap;
import java.util.Map;

public class MainApp {




    private void setMap(Map paramMap){
        paramMap.put("key","Value1");
        paramMap=new HashMap();
    }

    private  Map setAnGetMap(Map paramMap){
        paramMap.put("key","value2");
        paramMap=new HashMap();
        return paramMap;
    }


    private void setNum(Integer num){
        num=new Integer(1);
    }

    private Integer setAndGetNum(Integer num){
        num=new Integer(2);
        return num;
    }













    public static void main(String[] args) {
        MainApp app=new MainApp();

//        Map map =new HashMap();
//        map=app.setAnGetMap(map);
//        System.out.println(JSON.toJSONString(map));



        Integer num= new Integer(0);
        app.setNum(num);
        System.out.println(JSON.toJSONString(num));


    }


















}
