package com.king.GuiPack;

import com.king.PojoClass.ShopPojo;
import com.king.ReadFiles.ReadConfig;
import com.king.ReadFiles.ReadGuiConfig;
import com.king.ReadFiles.ReadLanguage;
import com.king.ReadFiles.ReadPlayerData;
import com.king.ToolClass;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class OpenPlayershopGui {

    public OpenPlayershopGui(Player player, String Name){

            //Name 是目标商家的玩家名字
            //Player 是被打开Gui的玩家

            ShopPojo shopPojo = ReadPlayerData.PlayerShop.get(Name);
            shopPojo.ReloadInventory();

            if(shopPojo.inventory == null){

                player.sendMessage(ReadLanguage.Open_shop_Error1);
                return;

            }

            Inventory ls = Bukkit.createInventory(null, 54, ReadConfig.Goin_To_player_shop);

            //放入装饰格子

            for (int i = 0; i < 9; i++) {
                ls.setItem(i, ReadGuiConfig.TYZS_lattice);
            }

            ls.setItem(9,ReadGuiConfig.TYZS_lattice);
            ls.setItem(15,ReadGuiConfig.TYZS_lattice);
            ls.setItem(17,ReadGuiConfig.TYZS_lattice);

            for (int i = 18; i < 27; i++) {
                ls.setItem(i, ReadGuiConfig.TYZS_lattice);
            }
            //购买
            ls.setItem(10,ReadGuiConfig.SPGM_lattice);
            //店铺信息

                ItemStack XX_ITEM = new ItemStack(Material.getMaterial(ReadGuiConfig.DPXX_TYPE));

                //根据店铺等级设置 堆叠数
                XX_ITEM.setAmount(shopPojo.Grade);

                //正式设置店铺信息
                ItemMeta itemMeta = XX_ITEM.getItemMeta();


                //设置名字
                itemMeta.setDisplayName(shopPojo.Shop_name.replaceAll("&","§"));
                //设置具体lore
                    itemMeta.setLore(Arrays.asList(
                    "",
                    "§e交易次数:",
                    "§e§l" + shopPojo.Shop_Sell,
                    "",
                    "§b获得的赞:",
                    "§b§l" + shopPojo.Player_Thumbs_up.size(),
                    "",
                     shopPojo.Shop_lore.replaceAll("&", "§"),
                     "§7"+shopPojo.Player_name
                    ));

                XX_ITEM.setItemMeta(itemMeta);

            ls.setItem(11,XX_ITEM);


            //点赞

            ls.setItem(12,ReadGuiConfig.DPDZ_lattice);

            //打印此商家拥有的商品
            shopPojo.reloadItemStacks();
            int Sy = 27;
            for (ItemStack itemStack : shopPojo.itemStacks){

                ls.setItem(Sy,itemStack);
                Sy++;

            }


        player.closeInventory();
        player.openInventory(ls);
    }

}
