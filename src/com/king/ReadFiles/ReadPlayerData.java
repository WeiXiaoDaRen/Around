package com.king.ReadFiles;

import com.king.AroundMain;
import com.king.PojoClass.ShopPojo;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;

public class ReadPlayerData {

public static HashMap<String, ShopPojo> PlayerShop = new HashMap<>(); //店铺集合

public static HashSet<String> ShopName = new HashSet<>();    //店铺名称集合

   public static File file; //插件的根目录文件对象

    //读取 玩家数据文件
    public static void ReloadPlayerData(){

        for (String s : Objects.requireNonNull(new File(file,"/PlayerData").list())) {                   //遍历PlayerData文件夹下的文件

            if (!s.endsWith(".yml")) {    //结尾如果不是 .yml
                AroundMain.SendErrorMessage("\33[31m 文件" + s +"并不是以yml结尾的有效文件！\33[0m ");
                continue;
            }
            FileConfiguration fileConfiguration = YamlConfiguration.loadConfiguration(new File(file, "/PlayerData/"+s));    //创建一个读取PlayerData文件夹下 任务文件的工具类


            //正式读取

            PlayerShop.put(s.replaceAll(".yml",""),new ShopPojo(
                    fileConfiguration.getString("Shop_name"),
                    fileConfiguration.getString("Shop_lore"),
                    fileConfiguration.getBoolean("Normal"),
                    fileConfiguration.getInt("Penalty"),
                    fileConfiguration.getInt("Levels"),
                    fileConfiguration.getInt("Shop_Sell"),
                    fileConfiguration.getString("Warehouse"),
                    fileConfiguration.getStringList("Player_Thumbs_up"),
                    fileConfiguration.getStringList("Shop_items"),
                    s.replaceAll(".yml",""),
                    fileConfiguration.getInt("Grade")
            ));

        }

        AroundMain.SendErrorMessage("共读取到 "+PlayerShop.size()+" 个玩家数据。");

    }

    //对PlayerData文件夹内 新建 数据文件
    public static void NewPlayerDataFile(String PlayerName){

        File Mulu = new File(file,"/PlayerData/"+PlayerName+".yml");

        if(Mulu.exists()){  //假如此文件存在,则意味着这个商店创建过,于是乎只需要重新写入即可。

            SavePlayerData(PlayerName,"全部");
            return;

        }

        InputStream input = AroundMain.getPlugin(AroundMain.class).getResource("playerdata.yml"); //获取玩家数据母版
        try {

            OutputStream oput = new FileOutputStream(Mulu);

            byte[] ls = new byte[1024];
            int a;

            while(true){
                assert input != null;
                if (!((a = input.read(ls)) > 0)) break;

                oput.write(ls,0,a);

            }


        }catch (Exception e){
            AroundMain.SendErrorMessage(" 插件出现错误,错误区域: RPD1Try / 文件产生失败 ");
            AroundMain.SendErrorMessage(e.getMessage());
        }

        AroundMain.SendErrorMessage("为玩家 " + PlayerName + " 创建了新的数据文件。");

        SavePlayerData(PlayerName,"全部");

    }


    //对指定玩家的数据内容进行保存
    //当 Key 为 "全部" 时,将内容全部保存
    public static void SavePlayerData(String PlayerName,String Key){

        FileConfiguration fileConfiguration = YamlConfiguration.loadConfiguration(new File(file, "/PlayerData/"+PlayerName+".yml"));
        //创建一个读取PlayerData文件夹下 任务文件的工具类

        ShopPojo shopPojo = PlayerShop.get(PlayerName);

        if(Key.equalsIgnoreCase("全部")){

                    try {
                        fileConfiguration.set("Shop_name", shopPojo.Shop_name);
                        fileConfiguration.set("Shop_lore", shopPojo.Shop_lore);
                        fileConfiguration.set("Normal", shopPojo.Normal);
                        fileConfiguration.set("Penalty", shopPojo.Penalty);
                        fileConfiguration.set("Levels", shopPojo.Levels);
                        fileConfiguration.set("Shop_Sell", shopPojo.Shop_Sell);
                        fileConfiguration.set("Warehouse", shopPojo.Warehouse);
                        fileConfiguration.set("Player_Thumbs_up", shopPojo.Player_Thumbs_up);
                        fileConfiguration.set("Shop_items", shopPojo.Shop_items);
                        fileConfiguration.set("Grade", shopPojo.Grade);
                    }catch (Exception e){
                    }
        }else{

            switch (Key){

                case "Shop_name":{
                    fileConfiguration.set("Shop_name", shopPojo.Shop_name);
                    break;
                }
                case "Shop_lore":{
                    fileConfiguration.set("Shop_lore", shopPojo.Shop_lore);
                    break;
                }
                case "Normal":{
                    fileConfiguration.set("Normal", shopPojo.Normal);
                    break;
                }
                case "Penalty":{
                    fileConfiguration.set("Penalty", shopPojo.Penalty);
                    break;
                }
                case "Levels":{
                    fileConfiguration.set("Levels", shopPojo.Levels);
                    break;
                }
                case "Shop_Sell":{
                    fileConfiguration.set("Shop_Sell", shopPojo.Shop_Sell);
                    break;
                }
                case "Warehouse":{
                    fileConfiguration.set("Warehouse", shopPojo.Warehouse);
                    break;
                }
                case "Player_Thumbs_up":{
                    fileConfiguration.set("Player_Thumbs_up", shopPojo.Player_Thumbs_up);
                    break;
                }
                case "Shop_items":{
                    fileConfiguration.set("Shop_items", shopPojo.Shop_items);
                    break;
                }
                case "Grade":{
                    fileConfiguration.set("Grade", shopPojo.Grade);
                    break;
                }
                default:{
                    break;
                }

            }

        }

        try {
            fileConfiguration.save(new File(file, "/PlayerData/" + PlayerName + ".yml"));
        }catch (Exception e){
            AroundMain.SendErrorMessage(" 插件出现错误,错误区域: RPD3Try / 文件保存失败 ");
            AroundMain.SendErrorMessage(e.getMessage());
        }
    }

}
