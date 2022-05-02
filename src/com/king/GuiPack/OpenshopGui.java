package com.king.GuiPack;

import com.king.ReadFiles.ReadConfig;
import com.king.ReadFiles.ReadGuiConfig;
import com.king.ReadFiles.ReadPlayerData;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class OpenshopGui {

    public OpenshopGui(Player player){

        Inventory ls = Bukkit.createInventory(null, 54, ReadConfig.Player_shop_Title);

        //打印装饰格子
        for (int i = 0; i < 9; i++) {

            ls.setItem(i, ReadGuiConfig.TYZS_lattice);

        }

        int a = 9;
        for (int i = 0; i < 5; i++) {
            ls.setItem(a,ReadGuiConfig.TYZS_lattice);
            a += 9;
        }

        for (int i = 45; i < 52; i++) {
            ls.setItem(i,ReadGuiConfig.TYZS_lattice);
        }
        
        a = 15;
        for (int i = 0; i < 4; i++) {
            ls.setItem(a,ReadGuiConfig.TYZS_lattice);
            a+=9;
        }

        ls.setItem(17,ReadGuiConfig.TYZS_lattice);
        ls.setItem(25,ReadGuiConfig.TYZS_lattice);
        ls.setItem(26,ReadGuiConfig.TYZS_lattice);

        //打印功能格子

            //购买
            ls.setItem(43,ReadGuiConfig.SPGM_lattice);
            //前往店铺
            ItemStack itemStack = new ItemStack(Material.getMaterial(ReadGuiConfig.DPXX_TYPE));
            ItemMeta itemMeta = itemStack.getItemMeta();
            itemMeta.setDisplayName("§b查看卖家店铺");
            itemMeta.setLore(Arrays.asList(
                    "",
                    "§a所属店铺",
                    "§c暂未点击任何商品",
                    "",
                    "§e累计成交",
                    ""
            ));
            itemStack.setItemMeta(itemMeta);
            ls.setItem(34,itemStack);
            //刷新
            ls.setItem(52,ReadGuiConfig.SXWP_lattice);



                if(ReadPlayerData.PlayerShop.keySet().size() == 0){ //一家店铺都没有
                    player.closeInventory();
                    player.openInventory(ls);
                    return;
                }

        //打印商品格子
        int i = 0;
        int j = 0;
        int k = 0;

                for (String s : ReadPlayerData.PlayerShop.keySet()){
                    ReadPlayerData.PlayerShop.get(s).reloadItemStacks();
                    for (ItemStack itemStack1 : ReadPlayerData.PlayerShop.get(s).itemStacks){

                        ls.setItem(10 + (i * 9) + j,itemStack1);

                        j++;
                        if (j == 5) {
                            j = 0;
                            i++;
                        }
                        k++;
                        if(k >= 20){
                            break;
                        }

                    }

                    if(k >= 20){
                        break;
                    }

                }


        player.closeInventory();
        player.openInventory(ls);
    }

}
