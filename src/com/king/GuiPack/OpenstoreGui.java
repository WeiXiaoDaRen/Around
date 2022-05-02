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

public class OpenstoreGui {

    public OpenstoreGui(Player player){

        ShopPojo shopPojo = ReadPlayerData.PlayerShop.get(player.getName());
        shopPojo.ReloadInventory();

        if(!shopPojo.Normal){

            player.sendMessage(ReadLanguage.shop_ban);
            return;

        }

        if(shopPojo.inventory == null){

            player.sendMessage(ReadLanguage.Open_shop_Error1);
            return;

        }

        Inventory ls = Bukkit.createInventory(null, 54, ReadConfig.Player_stor_Title);

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

        //放入功能格子
            //10号位上架
            ls.setItem(10,ReadGuiConfig.SJSP_lattice);
            //11号位下架
            ls.setItem(11,ReadGuiConfig.XJSP_lattice);
                //12号位信息 (热创建)

                ItemStack XX_ITEM = new ItemStack(Material.getMaterial(ReadGuiConfig.DPXX_TYPE));

                //获得店铺信息
                String[] strings = ToolClass.getShopLevel(player.getName()).split(",");

                //根据店铺等级设置 堆叠数
                XX_ITEM.setAmount(Integer.parseInt(strings[1]));

                //正式设置店铺信息
                ItemMeta itemMeta = XX_ITEM.getItemMeta();


                    //设置名字
                    itemMeta.setDisplayName(shopPojo.Shop_name.replaceAll("&","§"));
                    //设置具体lore
                    if(shopPojo.Penalty != 0){
                        itemMeta.setLore(Arrays.asList(
                                "§4§l无法分享店铺！ (你有处罚未执行)",
                                "",
                                "§e交易次数:",
                                "§e§l" + shopPojo.Shop_Sell,
                                "§b获得的赞:",
                                "§b§l" + shopPojo.Player_Thumbs_up.size(),
                                "§a店铺等级",
                                strings[0] + " §a§l( §7" + shopPojo.Levels + " §a§l/ §7§l" + ToolClass.getAsLeves(Integer.valueOf(strings[1])) + " §a§l) ",
                                "",
                                shopPojo.Shop_lore.replaceAll("&", "§"),
                                "",
                                "§c你有待缴纳的罚金 : §l" + shopPojo.Penalty,
                                "§c点击缴纳"
                        ));
                    }else {
                        itemMeta.setLore(Arrays.asList(
                                "§a点击分享店铺",
                                "",
                                "§e交易次数:",
                                "§e§l" + shopPojo.Shop_Sell,
                                "§b获得的赞:",
                                "§b§l" + shopPojo.Player_Thumbs_up.size(),
                                "§a店铺等级",
                                strings[0] + " §a§l( §7" + shopPojo.Levels + " §a§l/ §7§l" + ToolClass.getAsLeves(Integer.valueOf(strings[1])) + " §a§l) ",
                                "",
                                shopPojo.Shop_lore.replaceAll("&", "§")
                        ));
                    }
                    XX_ITEM.setItemMeta(itemMeta);

            ls.setItem(12,XX_ITEM);
            //13号位改格言
            ls.setItem(13,ReadGuiConfig.DPGY_lattice);

            //栅栏
                if(Integer.parseInt(strings[1]) != 9) {
                    int SuoYing = 27 + Integer.parseInt(strings[1]);
                    int Cishu = 36 - SuoYing;
                    for (int j = 0; j < 3; j++) {


                        for (int i = 0; i < Cishu; i++) {

                            ls.setItem(SuoYing+i, ReadGuiConfig.FYGZ_lattice);

                        }

                        SuoYing += 9;

                    }
                }


            int LuJin = 27;
            //上架的商品
            shopPojo.reloadItemStacks();
            for (ItemStack itemStack : shopPojo.itemStacks){

                ls.setItem(LuJin,itemStack);

                LuJin+=9;

                if(LuJin >= 54){
                    LuJin = LuJin-(9*2)-8;
                }

            }

        player.closeInventory();
        player.openInventory(ls);

    }

}
