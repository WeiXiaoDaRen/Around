package com.king.RegisterEvent;

import com.king.AroundMain;
import com.king.GuiPack.OpenPlayershopGui;
import com.king.NewShopClass.NewShop;
import com.king.PojoClass.ShopPojo;
import com.king.ReadFiles.ReadConfig;
import com.king.ReadFiles.ReadGuiConfig;
import com.king.ReadFiles.ReadLanguage;
import com.king.ReadFiles.ReadPlayerData;
import com.king.ToolClass;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class EventOpenGui implements Listener {

    @EventHandler
    public void operate(InventoryClickEvent event){


        if(event.getView().getTitle().equalsIgnoreCase(ReadConfig.Player_stor_Title)) {

            event.setCancelled(true); //不允许操作物品


            if (!(event.getWhoClicked() instanceof Player)) { return;} //如果不是玩家就 直接结束

            int Slot = event.getSlot(); //获取点击的格子

            ShopPojo shopPojo = ReadPlayerData.PlayerShop.get(event.getWhoClicked().getName());
            shopPojo.ReloadInventory();

            if(Slot > 26 && Slot < 55){ //点击的格子 属于 物品区

                if (ToolClass.cooling((Player) event.getWhoClicked())) {
                    return;
                }

                ItemStack itemStack = Objects.requireNonNull(event.getInventory()).getItem(Slot);

                if(itemStack != null){ //点击的格子不是空的

                    if(! itemStack.equals(ReadGuiConfig.FYGZ_lattice)){ //点击的不是铁栅栏

                        event.getClickedInventory().setItem(16,itemStack);

                    }

                }

            }

            // 10号格 上架物品
            // 11号格 下架物品
            // 12号格 缴纳押金
            // 13号格 修改名称
            switch (Slot){
                case 10:{ // 10号格 上架物品 √

                    if (ToolClass.cooling((Player) event.getWhoClicked())) {
                        break;
                    }
                    //是否可以继续上架

                    if(! shopPojo.isSJ()){

                        break;
                    }

                    //关闭Gui 然后让玩家 右击需要上架的物品 然后 输入价格 (单价)
                    if(shopPojo.inventory.firstEmpty() == -1){
                        event.getWhoClicked().sendMessage(ReadLanguage.firstEmpty_Null);
                        break;
                    }

                    event.getWhoClicked().closeInventory();
                    event.getWhoClicked().sendMessage(ReadLanguage.Shop_up_item);
                    EventUpItem.playerName.add(event.getWhoClicked().getName());

                    shopPojo.reloadItemStacks();
                    break;
                }
                case 11:{ // 11号格 下架物品 √

                    if (ToolClass.cooling((Player) event.getWhoClicked())) {
                        break;
                    }

                    int CWM = 0;

                    if(event.getInventory().getItem(16)==null){
                        break;
                    }

                    //处理信息
                    String string = ToolClass.GetShopItemMessage((ArrayList<String>) event.getInventory().getItem(16).getItemMeta().getLore(),true);
                    CWM = Integer.parseInt(string.split(",")[0]);

                    //判断玩家背包是否充足
                    if(ToolClass.YouKong(1,(Player) event.getWhoClicked())){
                        //将物品从仓库取出
                        ItemStack itemStack = new ItemStack(shopPojo.inventory.getItem(CWM));
                        //将物品给玩家
                        event.getWhoClicked().getInventory().setItem(
                                event.getWhoClicked().getInventory().firstEmpty(),
                                itemStack
                        );

                        //将物品从仓库删除
                        shopPojo.inventory.getItem(CWM).setAmount(0);

                        shopPojo.Shop_items.remove(string); //将此内容从商品集合删除

                        ReadPlayerData.SavePlayerData(event.getWhoClicked().getName(),"Shop_items"); //保存文件

                        event.getWhoClicked().sendMessage(ReadLanguage.ok_XJ_Item);

                        event.getWhoClicked().closeInventory(); //关闭GUI

                    }else{
                        event.getWhoClicked().sendMessage(ReadLanguage.Player_BB_NO);
                    }

                    shopPojo.reloadItemStacks();
                    break;
                }
                case 12:{ // 12号格 缴纳罚金 √

                    if (ToolClass.cooling((Player) event.getWhoClicked())) {
                        break;
                    }

                    if(shopPojo.Penalty != 0){ //需要缴纳罚金

                        if(AroundMain.getEconomy().has(((Player) event.getWhoClicked()).getPlayer(),shopPojo.Penalty)){

                            //扣除玩家相应的金币
                            AroundMain.getEconomy().withdrawPlayer(((Player) event.getWhoClicked()).getPlayer(),shopPojo.Penalty);

                            //清除玩家的罚金记录
                            shopPojo.Penalty = 0;
                            ReadPlayerData.SavePlayerData(event.getWhoClicked().getName(),"Penalty");

                            event.getWhoClicked().sendMessage(ReadLanguage.Fines_are_paid);
                            event.getWhoClicked().closeInventory();

                            break;
                        }
                        event.getWhoClicked().sendMessage(ReadLanguage.No_money);
                    }else{

                        ToolClass.SendShop(shopPojo.Player_name);

                    }

                    break;
                }
                case 13:{ // 13号格 修改名称 √

                    if (ToolClass.cooling((Player) event.getWhoClicked())) {
                        break;
                    }

                    if(AroundMain.getEconomy().has(((Player) event.getWhoClicked()).getPlayer(),ReadConfig.New_shop_name_money)){

                        NewShop.NewName(Objects.requireNonNull(((Player) event.getWhoClicked()).getPlayer()));
                        event.getWhoClicked().closeInventory();
                        break;
                    }else{
                        event.getWhoClicked().sendMessage(ReadLanguage.No_money);
                    }

                    break;
                }
                default:{
                    break;
                }
            }

        }else if(event.getView().getTitle().equalsIgnoreCase(ReadConfig.Player_shop_Title)){

            event.setCancelled(true); //不允许操作物品

            Inventory ls = event.getInventory();

            int Slot = event.getSlot(); //获取点击的格子

            int JG = 0;     //价格
            int CWM = 0; //仓位码
            String Name = ""; //所属玩家

            //操作的格子是商品格
            if(Slot <= 41 && Slot >= 10){
                if((Slot % 9 >= 1) && (Slot % 9 <= 5)){

                    if (ToolClass.cooling((Player) event.getWhoClicked())) {
                        return;
                    }

                    ls.setItem(16,new ItemStack(ls.getItem(Slot)));

                    ItemMeta itemMeta1 = ls.getItem(34).getItemMeta();

                    ItemMeta itemMeta = ls.getItem(16).getItemMeta();

                    Name = itemMeta.getLore().get(itemMeta.getLore().size() - 3).replaceAll("§7所属玩家:","");

                    itemMeta1.setLore(Arrays.asList(
                            "",
                            "§a所属店铺",
                            ReadPlayerData.PlayerShop.get(Name).Shop_name.replace("&","§"),
                            "",
                            "§e累计成交",
                            "§7"+ReadPlayerData.PlayerShop.get(Name).Shop_Sell
                    ));

                    ls.getItem(34).setItemMeta(itemMeta1);

                }
            }


            switch (Slot){

                case 52:{ //刷新

                    if (ToolClass.cooling((Player) event.getWhoClicked())) {
                        break;
                    }

                    ls.clear(16);

                        if(ReadPlayerData.PlayerShop.size() == 0){ //没有店铺直接结束
                            break;
                        }

                        int i = 0;
                        int j = 0;
                        int k = 0;

                        ArrayList<String> shops = new ArrayList<>();
                        for(String s : ReadPlayerData.PlayerShop.keySet()){
                            shops.add(s);
                        }

                        while (true) {
                            if (shops.size() == 1) { //只有一家店铺就不随机了

                                ReadPlayerData.PlayerShop.get(shops.get(0)).reloadItemStacks();
                                for (ItemStack itemStack : ReadPlayerData.PlayerShop.get(shops.get(0)).itemStacks) {

                                    k++;
                                    if (k > 20) {
                                        break;
                                    }

                                    ls.setItem(10 + (i * 9) + j, itemStack);

                                    j++;
                                    if (j == 5) {
                                        j = 0;
                                        i++;
                                    }

                                }

                                break;

                            } else {
                                Random random = new Random();
                                String playername = shops.get(random.nextInt(shops.size() - 1));
                                shops.remove(playername);
                                for (ItemStack itemStack : ReadPlayerData.PlayerShop.get(playername).itemStacks) {

                                    k++;
                                    if (k > 20) {
                                        break;
                                    }

                                    ls.setItem(10 + (i * 9) + j, itemStack);

                                    j++;
                                    if (j == 5) {
                                        j = 0;
                                        i++;
                                    }

                                }
                            }

                        }

                    event.getWhoClicked().sendMessage(ReadLanguage.Shop_flushed);
                    break;
                }
                case 43:{ //购买

                    if (ToolClass.cooling((Player) event.getWhoClicked())) {
                        break;
                    }

                    if(ls.getItem(16) == null){
                        break;
                    }

                    List<String> lore = ls.getItem(16).getItemMeta().getLore();
                    JG = Integer.parseInt(lore.get(lore.size() - 1).replaceAll("§7商品价格:",""));
                    Name = lore.get(lore.size() - 3).replaceAll("§7所属玩家:","");
                    CWM =  Integer.parseInt(lore.get(lore.size() - 2).replaceAll("§7商品库存码:",""));

                    if(Name.equalsIgnoreCase(event.getWhoClicked().getName())){ //不可以购买自己的商品

                        event.getWhoClicked().sendMessage(ReadLanguage.No_purchase_me_item);

                        break;
                    }

                    if(AroundMain.getEconomy().has((Player) event.getWhoClicked(),JG)){ //金钱是否足够?
                        //对方库存是否有此物？
                        ShopPojo shopPojo = ReadPlayerData.PlayerShop.get(Name);

                        //库存中是否有物品
                        if(shopPojo.Shop_KC(CWM)){


                            //将物品给予到此玩家
                            event.getWhoClicked().getInventory().setItem(
                                    event.getWhoClicked().getInventory().firstEmpty(),
                                    shopPojo.getItemStack(CWM,JG)
                            );

                            shopPojo.Levels += ReadConfig.Shop_lecel;
                            ReadPlayerData.SavePlayerData(shopPojo.Player_name,"Levels");

                            //买家扣除钱
                            AroundMain.getEconomy().withdrawPlayer(((Player) event.getWhoClicked()).getPlayer(),JG);

                            double money = JG;

                            //低于100不收税
                            if((JG+"").length() >= 3){
                                money = (money / 100) * ReadConfig.Taxations.get(shopPojo.Grade);
                            }

                            //卖家打钱
                            if(AroundMain.getPlugin(AroundMain.class).getServer().getPlayer(Name) == null){
                                AroundMain.getEconomy().depositPlayer(AroundMain.getPlugin(AroundMain.class).getServer().getOfflinePlayer(Name), money);
                            }else {
                                Player player = AroundMain.getPlugin(AroundMain.class).getServer().getPlayer(Name);
                                AroundMain.getEconomy().depositPlayer(player, money);
                                String Ti = ReadLanguage.Shop_business;
                                Ti = Ti.replaceAll("<player>",event.getWhoClicked().getName());
                                Ti = Ti.replaceAll("<money>",money+"");
                                player.sendMessage(Ti);
                            }

                            event.getWhoClicked().sendMessage(ReadLanguage.purchase_shop);
                        }else{
                            event.getWhoClicked().sendMessage(ReadLanguage.purchase_ERROR);
                        }

                    }else{
                        event.getWhoClicked().sendMessage(ReadLanguage.No_money);
                    }

                    break;
                } case 34:{ //前往此玩家店铺

                    if (ToolClass.cooling((Player) event.getWhoClicked())) {
                        break;
                    }

                    if(ls.getItem(16) == null){
                        break;
                    }

                    List<String> lore = ls.getItem(16).getItemMeta().getLore();
                    Name = lore.get(lore.size() - 3).replaceAll("§7所属玩家:","");

                    event.getWhoClicked().closeInventory();
                    event.getWhoClicked().sendMessage(ReadLanguage.Goint_player_shop.replaceAll("<player>",Name));
                    event.getWhoClicked().sendMessage(ReadPlayerData.PlayerShop.get(Name).Shop_name.replace("&","§") + "对你说 :");
                    event.getWhoClicked().sendMessage(ReadPlayerData.PlayerShop.get(Name).Shop_lore.replace("&","§"));
                    new OpenPlayershopGui((Player) event.getWhoClicked(),Name);

                    break;
                }
                default:{
                    break;
                }
            }

        } else if(event.getView().getTitle().equalsIgnoreCase(ReadConfig.Goin_To_player_shop)){

            event.setCancelled(true); //不允许操作物品

            Inventory ls = event.getInventory();

            List<String> a = ls.getItem(11).getItemMeta().getLore();

            ShopPojo shopPojo = ReadPlayerData.PlayerShop.get(a.get(a.size()-1).replaceAll("§7",""));
            shopPojo.ReloadInventory();

            int Slot = event.getSlot(); //获取点击的格子

            if(Slot > 26 && Slot < 55){ //点击的格子 属于 物品区

                if (ToolClass.cooling((Player) event.getWhoClicked())) {
                    return;
                }

                ItemStack itemStack = Objects.requireNonNull(event.getInventory()).getItem(Slot);

                if(itemStack != null){ //点击的格子不是空的

                    if(! itemStack.equals(ReadGuiConfig.FYGZ_lattice)){ //点击的不是铁栅栏

                        event.getClickedInventory().setItem(16,itemStack);

                    }

                }

            }

            switch (Slot){

                case 10:{ //购买格子
                    if (ToolClass.cooling((Player) event.getWhoClicked())) {
                        return;
                    }

                    if(ls.getItem(16) == null){
                        break;
                    }

                    List<String> lore = ls.getItem(16).getItemMeta().getLore();
                    int JG = Integer.parseInt(lore.get(lore.size() - 1).replaceAll("§7商品价格:",""));
                    String Name = lore.get(lore.size() - 3).replaceAll("§7所属玩家:","");
                    int CWM =  Integer.parseInt(lore.get(lore.size() - 2).replaceAll("§7商品库存码:",""));

                    if(Name.equalsIgnoreCase(event.getWhoClicked().getName())){ //不可以购买自己的商品

                        event.getWhoClicked().sendMessage(ReadLanguage.No_purchase_me_item);

                        break;
                    }

                    if(AroundMain.getEconomy().has((Player) event.getWhoClicked(),JG)){ //金钱是否足够?

                        //库存中是否有物品
                        if(shopPojo.Shop_KC(CWM)){


                            //将物品给予到此玩家
                            event.getWhoClicked().getInventory().setItem(
                                    event.getWhoClicked().getInventory().firstEmpty(),
                                    shopPojo.getItemStack(CWM,JG)
                            );

                            //买家扣除钱
                            AroundMain.getEconomy().withdrawPlayer(((Player) event.getWhoClicked()).getPlayer(),JG);

                            double money = JG;

                            //低于100不收税
                            if((JG+"").length() >= 3){
                                money = (money / 100) * ReadConfig.Taxations.get(shopPojo.Grade);
                            }

                            //卖家打钱
                            if(AroundMain.getPlugin(AroundMain.class).getServer().getPlayer(Name) == null){
                                AroundMain.getEconomy().depositPlayer(AroundMain.getPlugin(AroundMain.class).getServer().getOfflinePlayer(Name), money);
                            }else {
                                Player player = AroundMain.getPlugin(AroundMain.class).getServer().getPlayer(Name);
                                AroundMain.getEconomy().depositPlayer(player, money);
                                String Ti = ReadLanguage.Shop_business;
                                Ti = Ti.replaceAll("<player>",event.getWhoClicked().getName());
                                Ti = Ti.replaceAll("<money>",money+"");
                                player.sendMessage(Ti);
                            }

                            event.getWhoClicked().sendMessage(ReadLanguage.purchase_shop);
                        }else{
                            event.getWhoClicked().sendMessage(ReadLanguage.purchase_ERROR);
                        }

                    }else{
                        event.getWhoClicked().sendMessage(ReadLanguage.No_money);
                    }

                    break;

                }
                case 12:{ //点赞格子
                    if (ToolClass.cooling((Player) event.getWhoClicked())) {
                        return;
                    }

                    Player player = (Player) event.getWhoClicked();

                    //检查是否已经点过赞了
                    if(shopPojo.Player_Thumbs_up.contains(event.getWhoClicked().getName())){

                        player.sendMessage(ReadLanguage.Good_Error);

                        break;
                    }

                    shopPojo.Player_Thumbs_up.add(event.getWhoClicked().getName());
                    ReadPlayerData.SavePlayerData(shopPojo.Player_name,"Player_Thumbs_up"); //保存

                    player.sendMessage(ReadLanguage.Good_shop.replaceAll("<shopname>",shopPojo.Shop_name));

                    if(player.getName().equalsIgnoreCase(shopPojo.Player_name)){
                        player.sendMessage(ReadLanguage.Player_Good_me);
                        break;
                    }

                    if(AroundMain.getPlugin(AroundMain.class).getServer().getPlayer(shopPojo.Player_name) != null){ //在线

                        AroundMain.getPlugin(AroundMain.class).getServer().getPlayer(shopPojo.Player_name).sendMessage(
                                ReadLanguage.Player_Good_You.replaceAll("<player>",event.getWhoClicked().getName())
                        );
                        break;
                    }

                    break;
                }
                default:{
                    break;
                }

            }

        }
    }
}