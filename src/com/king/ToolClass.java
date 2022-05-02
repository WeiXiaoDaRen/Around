package com.king;


import com.king.PojoClass.ShopLevel;
import com.king.ReadFiles.ReadConfig;
import com.king.ReadFiles.ReadLanguage;
import com.king.ReadFiles.ReadPlayerData;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.regex.Pattern;


public class ToolClass {

    //获取店铺等级
    public static String getShopLevel(String PlayerName){

        //先获得店铺经验
        Integer level = ReadPlayerData.PlayerShop.get(PlayerName).Levels;

        int a = 9;
        for (ShopLevel shopLevel : ReadConfig.ShopLevels){

         if(shopLevel.IsLevel(level)){

             ReadPlayerData.PlayerShop.get(PlayerName).Grade = a;
             ReadPlayerData.SavePlayerData(PlayerName,"Grade");
            return shopLevel.Name+","+a;

         }

         a--;

        }

        AroundMain.SendErrorMessage("\33[31m 插件出现了错误,通常是你设置的第一个等级的经验不为0导致！ \33[0m");
        return "未知错误";
    }

    //获得下一级所需要的经验 无则满级
    public static  String getAsLeves(Integer integer){

        Integer asWZ = 9-integer;

        if(asWZ == 0){
            return "满级";
        }

        asWZ--;

        return ReadConfig.ShopLevels.get(asWZ).Number+"";

    }

    //获取输入是否为数字
    public static boolean isNumber(String str){

        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();

    }

    //背包空间是否充足
    public static boolean YouKong(int a, Player player){

        int b = 0;
        while (player.getInventory().firstEmpty() != -1){
            b++;
            if(b>a){
                break;
            }
        }

        return b>a;
    }

    //处理信息
    public static String GetShopItemMessage(ArrayList<String> lore,boolean isStor){

        if(isStor){ //浅获取
            String a = lore.get(lore.size() - 2); //仓位码
            String b = lore.get(lore.size() - 1); //商品价格

            a = a.replaceAll("§7商品库存码:","");
            b = b.replaceAll("§7商品价格:","");

            return a+","+b;
        }else{ //深度获取
            return lore.get(lore.size() - 3)+","+lore.get(lore.size() - 2)+","+lore.get(lore.size() - 1);
        }

    }

    //冷却系统
    public static boolean cooling(Player player){

        //判断玩家是否在冷却集合中?
        if(AroundMain.coolings.keySet().contains(player.getName())){

            //在的话获取 集合里的毫秒值 是否 小于当前毫秒值?

            long Time =  AroundMain.coolings.get(player.getName()) - System.currentTimeMillis() ;

            if(Time < 0){

                //意味着时间到了
                AroundMain.coolings.put(player.getName(),System.currentTimeMillis()+ReadConfig.Operation_delay);

            }else{

                //冷却还没结束
                player.sendMessage(ReadLanguage.cooling.replaceAll("<Time>",Time+""));
                return true;
            }

        }else{            //不在的话添加进冷却集合 值就是获取当前毫秒值 + 3000
            AroundMain.coolings.put(player.getName(),System.currentTimeMillis()+ReadConfig.Operation_delay);
        }

        return false;
    }

    //发送店铺邀请
    public static void SendShop(String name){

        BaseComponent url = new TextComponent("玩家 "+name+" 分享了他的店铺!");
        url.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,new Text("点击前往")));
        url.setClickEvent(new ClickEvent(
                ClickEvent.Action.RUN_COMMAND, // 动作：打开 URL
                "/around open "+name // 要打开的 URL
        ));
        url.setColor(ChatColor.BLUE); // 颜色
        url.setBold(true); // 加粗

        for (Player player : AroundMain.getPlugin(AroundMain.class).getServer().getOnlinePlayers()){

            player.spigot().sendMessage(url);

        }

    }

}
