package com.king.ReadFiles;

import com.king.PojoClass.ShopLevel;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.*;

public class ReadConfig {

    public static List<ShopLevel> ShopLevels = new ArrayList<>();

    public static FileConfiguration fileConfiguration;

    public static String Player_stor_Title; //店铺的Gui标题

    public static Integer Shop_lecel; //店铺收获的经验

    public static String Promise; //失信被执行人

    public static Integer New_shop_name_money; //修改店铺名称所需花费金钱

    public static String Player_shop_Title; //商店的Gui标题

    public static String Goin_To_player_shop; //第三方店铺

    public static Integer Operation_delay; //操作冷却

    public static Integer Shop_name_length; //店铺名称长度限制

    public static Integer Shop_lore_length; //店铺描述长度限制

    public static HashMap<Integer,Integer> Taxations = new HashMap<>(); //税收 键是等级

    public ReadConfig(FileConfiguration fileConfiguration) {

        this.fileConfiguration = fileConfiguration;
        ReloadConfig();

    }

    public static void ReloadConfig(){

        int a= 1;

        for (Object o : fileConfiguration.getList("levels")){

            LinkedHashMap linkedHashMap = (LinkedHashMap) o;

            ShopLevels.add(new ShopLevel(
                    (String) linkedHashMap.get("Name"),
                    (Integer) linkedHashMap.get("Tax"),
                    (Integer) linkedHashMap.get("Number")
            ));

            Taxations.put(a,(Integer) linkedHashMap.get("Tax"));
            a++;
        }

        Collections.reverse(ShopLevels); //倒序

        Player_stor_Title = fileConfiguration.getString("Player_stor_Title").replace("&","§");

        Promise = fileConfiguration.getString("Promise").replace("&","§");

        New_shop_name_money = fileConfiguration.getInt("New_shop_name_money");

        Player_shop_Title = fileConfiguration.getString("Player_shop_Title").replace("&","§");

        Operation_delay = fileConfiguration.getInt("Operation_delay");

        Goin_To_player_shop = fileConfiguration.getString("Goin_To_player_shop").replace("&","§");

        Shop_lecel = fileConfiguration.getInt("Shop_lecel");

        Shop_name_length = fileConfiguration.getInt("Shop_name_length");

        Shop_lore_length = fileConfiguration.getInt("Shop_lore_length");

    }
}
