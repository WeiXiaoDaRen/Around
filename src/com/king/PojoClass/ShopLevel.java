package com.king.PojoClass;

/*
*
* 关于店铺等级
* 读取时用一个 Map
* 键为 Integer 值为此对象
*
* 可以方便的通过计算店铺经营状况 得出经验
* 获得对应的等级 对象
*
* */

public class ShopLevel {

    public String Name;

    public Integer Tax;

    public Integer Number;

    public ShopLevel(String name, Integer tax,Integer number) {

        Name = name.replace("&","§");
        Tax = tax;
        Number = number;

    }

    //判断店铺等级是否达到经验要求
    public boolean IsLevel(Integer integer){

        return integer >= Number;

    }

}
