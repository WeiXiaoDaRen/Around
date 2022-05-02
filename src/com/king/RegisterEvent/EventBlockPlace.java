package com.king.RegisterEvent;

import com.king.NewShopClass.NewShop;
import com.king.ReadFiles.ReadPlayerData;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class EventBlockPlace implements Listener {

    @EventHandler
    public void EvenEBP(BlockPlaceEvent event){

        //放置的是箱子
        if(event.getBlockPlaced().getType().equals(Material.CHEST)){

            //需要监听的玩家中 有此玩家
            if(NewShop.PlayerName.contains(event.getPlayer().getName())){

                //获取位置
                Location location = event.getBlockPlaced().getLocation();

                ReadPlayerData.PlayerShop.get(event.getPlayer().getName()).Warehouse = location.getWorld().getName()+","
                        +location.getBlockX()+","+location.getBlockY()+","+location.getBlockZ();

                NewShop.PlayerName.remove(event.getPlayer().getName()); //删除此玩家

            }

        }

    }

}
