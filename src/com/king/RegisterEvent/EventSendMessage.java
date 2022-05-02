package com.king.RegisterEvent;

import com.king.ReadFiles.ReadConfig;
import com.king.ReadFiles.ReadPlayerData;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class EventSendMessage implements Listener {

    @EventHandler
    public void sendmessage(AsyncPlayerChatEvent event){

        if(ReadPlayerData.PlayerShop.get(event.getPlayer().getName()).Penalty != 0){

            event.setMessage(event.getMessage() + ReadConfig.Promise);

        }

    }

}
