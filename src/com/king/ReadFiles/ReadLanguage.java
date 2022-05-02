package com.king.ReadFiles;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ReadLanguage {

    public static List<String> Rules_String = new ArrayList<>(); //店铺行为准则

    public static List<String> Steps_String = new ArrayList<>(); //店铺创建步骤指引

    public static String Plugin_prefix; //插件前缀

    public static String Open_shop_Error_NoShop; //检测不到店铺

    public static String Open_shop; //店铺打开成功

    public static String Open_shop_Error1; //打开店铺异常 无法访问店铺仓库

    public static String Open_shop_Error2; //无法在仓库找到此物品

    public static String Shop_up_item; //上架物品

    public static String Shop_up_Money; //输入价格

    public static String shop_ban; //店铺被封禁

    public static String Shop_Warehouse_ERROR_1; //找不到仓库

    public static String Shop_Warehouse_ERROR_2; //将为您跳转到放置仓库

    public static String anew_put_Warehouse; //放置仓库

    public static String Cancel_up_item; //取消上架物品

    public static String Enter_pricing; //输入定价

    public static String Up_Item_ok; //上架成功

    public static String Player_BB_NO; //背包空间不足

    public static String ok_XJ_Item; //成功下架物品

    public static String firstEmpty_Null; //仓库无空间

    public static String No_money; //金钱不足

    public static String Fines_are_paid; //罚金缴纳完毕

    public static String New_shop_name; //修改完成店铺名称

    public static String Go_shop; //打开了逛逛

    public static String purchase_shop; //输入购买数量

    public static String purchase_ERROR; //购买失败 找不到物品

    public static String Shop_business; //被购买提示

    public static String Shop_flushed; //刷新完成

    public static String cooling; //操作过快

    public static String No_purchase_me_item; //不可以购买自己的商品

    public static String Goint_player_shop; //进入店铺

    public static String Good_Error; //已经点过赞了

    public static String Good_shop; //点赞店铺

    public static String Player_Good_You; //为你点赞

    public static String Player_Good_me; //自己给自己点赞

    public static String Shop_KJ_Null; //店铺空间不足

    public static String PromptInPlayers; //有在执行的对话

    public static void ReloadLanguage(){

        FileConfiguration fileConfiguration = YamlConfiguration.loadConfiguration(new File(
                com.king.AroundMain.getPlugin(com.king.AroundMain.class
                ).getDataFolder(),"Language.yml"));

        //正式读取

        for(String s : fileConfiguration.getStringList("Steps_String")){
            Steps_String.add(s.replace("&","§"));
        }

        for (String s : fileConfiguration.getStringList("Rules_String")){
            Rules_String.add(s.replace("&","§"));
        }

        Plugin_prefix = fileConfiguration.getString("Plugin_prefix").replace("&","§");

        Open_shop_Error_NoShop = Plugin_prefix + fileConfiguration.getString("Open_shop_Error_NoShop").replace("&","§");

        Open_shop = Plugin_prefix + fileConfiguration.getString("Open_shop").replace("&","§");

        Open_shop_Error1 = Plugin_prefix + fileConfiguration.getString("Open_shop_Error1").replace("&","§");

        Shop_up_item = Plugin_prefix + fileConfiguration.getString("Shop_up_item").replace("&","§");

        Shop_up_Money = Plugin_prefix + fileConfiguration.getString("Shop_up_Money").replace("&","§");

        shop_ban = Plugin_prefix + fileConfiguration.getString("shop_ban").replace("&","§");

        Shop_Warehouse_ERROR_1 = Plugin_prefix + fileConfiguration.getString("Shop_Warehouse_ERROR_1").replace("&","§");

        Shop_Warehouse_ERROR_2 = Plugin_prefix + fileConfiguration.getString("Shop_Warehouse_ERROR_2").replace("&","§");

        anew_put_Warehouse = Plugin_prefix + fileConfiguration.getString("anew_put_Warehouse").replace("&","§");

        Cancel_up_item = Plugin_prefix + fileConfiguration.getString("Cancel_up_item").replace("&","§");

        Enter_pricing = Plugin_prefix + fileConfiguration.getString("Enter_pricing").replace("&","§");

        Up_Item_ok = Plugin_prefix + fileConfiguration.getString("Up_Item_ok").replace("&","§");

        Open_shop_Error2 = Plugin_prefix + fileConfiguration.getString("Open_shop_Error2").replace("&","§");

        Player_BB_NO = Plugin_prefix + fileConfiguration.getString("Player_BB_NO").replace("&","§");

        ok_XJ_Item = Plugin_prefix + fileConfiguration.getString("ok_XJ_Item").replace("&","§");

        firstEmpty_Null = Plugin_prefix + fileConfiguration.getString("firstEmpty_Null").replace("&","§");

        No_money = Plugin_prefix + fileConfiguration.getString("No_money").replace("&","§");

        Fines_are_paid = Plugin_prefix + fileConfiguration.getString("Fines_are_paid").replace("&","§");

        New_shop_name = Plugin_prefix + fileConfiguration.getString("New_shop_name").replace("&","§");

        Go_shop = Plugin_prefix + fileConfiguration.getString("Go_shop").replace("&","§");

        purchase_shop = Plugin_prefix+fileConfiguration.getString("purchase_shop").replace("&","§");

        purchase_ERROR = Plugin_prefix+fileConfiguration.getString("purchase_ERROR").replace("&","§");

        Shop_business = Plugin_prefix+fileConfiguration.getString("Shop_business").replace("&","§");

        Shop_flushed = Plugin_prefix+fileConfiguration.getString("Shop_flushed").replace("&","§");

        cooling = Plugin_prefix+fileConfiguration.getString("cooling").replace("&","§");

        No_purchase_me_item = Plugin_prefix+fileConfiguration.getString("No_purchase_me_item").replace("&","§");

        Goint_player_shop = Plugin_prefix+fileConfiguration.getString("Goint_player_shop").replace("&","§");

        Good_Error = Plugin_prefix+fileConfiguration.getString("Good_Error").replace("&","§");

        Good_shop = Plugin_prefix + fileConfiguration.getString("Good_shop").replace("&","§");

        Player_Good_You = Plugin_prefix + fileConfiguration.getString("Player_Good_You").replace("&","§");

        Player_Good_me = Plugin_prefix + fileConfiguration.getString("Player_Good_me").replace("&","§");

        Shop_KJ_Null = Plugin_prefix + fileConfiguration.getString("Shop_KJ_Null").replace("&","§");

        PromptInPlayers = Plugin_prefix + fileConfiguration.getString("PromptInPlayer").replace("&","§");
    }

}
