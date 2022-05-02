package com.king.RegisterEvent;

import com.king.NewShopClass.NewShop;
import com.king.ReadFiles.ReadLanguage;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.ArrayList;
import java.util.List;

public class EventUpItem implements Listener {

    public static List<String> playerName = new ArrayList<>();

    @EventHandler
    public void UpItem(PlayerInteractEvent event) {

        //不位于暂存库则结束
        if(! playerName.contains(event.getPlayer().getName())){
            return;
        }

        //右击取消
        if ((event.getAction().name().equalsIgnoreCase("RIGHT_CLICK_AIR")) || (
                event.getAction().name().equalsIgnoreCase("RIGHT_CLICK_BLOCK"))) {
            event.getPlayer().sendMessage(ReadLanguage.Cancel_up_item);
            playerName.remove(event.getPlayer().getName()); //删除出暂存库
            return;

        }


        //判断玩家动作 为 持道具左击
        if ((event.getAction().name().equalsIgnoreCase("LEFT_CLICK_AIR")) || (
                event.getAction().name().equalsIgnoreCase("LEFT_CLICK_AIR"))) {
            playerName.remove(event.getPlayer().getName()); //删除出暂存库

                if(event.getItem() == null){
                    event.getPlayer().sendMessage(ReadLanguage.Cancel_up_item);
                    playerName.remove(event.getPlayer().getName()); //删除出暂存库
                }

                NewShop.Up_Item(event.getPlayer(),event.getItem());
                //调用对话

            return;

        }

    }
}