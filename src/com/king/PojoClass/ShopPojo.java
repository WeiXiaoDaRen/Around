package com.king.PojoClass;

import com.king.AroundMain;
import com.king.ReadFiles.ReadLanguage;
import com.king.ReadFiles.ReadPlayerData;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ShopPojo {

    public String Player_name; //玩家名
    public String Shop_name; //店铺名
    public String Shop_lore; //店铺格言
    public boolean Normal;   //店铺是否正常
    public Integer Penalty;  //待缴纳罚金
    public Integer Levels;   //经验
    public Integer Shop_Sell;//累计达成交易次数
    public Integer Grade; //等级
    public String Warehouse; //仓库位置
    public List<String> Player_Thumbs_up; //点赞的玩家集合
    public List<String> Shop_items; //店铺商品

    public List<ItemStack> itemStacks = new ArrayList<>(); //用于展示

    public Inventory inventory; //商品物品栏

    public ShopPojo(String shop_name, String shop_lore, boolean normal, Integer penalty, Integer levels, Integer shop_Sell,
                    String warehouse, List<String> player_Thumbs_up, List<String> shop_items,String player_name,Integer grade) {
        Shop_name = shop_name;
        Shop_lore = shop_lore;
        Normal = normal;
        Penalty = penalty;
        Levels = levels;
        Shop_Sell = shop_Sell;
        Warehouse = warehouse;
        Player_Thumbs_up = new ArrayList<>(player_Thumbs_up);
        Shop_items = new ArrayList<>(shop_items);
        Player_name = player_name;
        Grade = grade;

    }

    public void ReloadInventory(){

        if(Warehouse.equalsIgnoreCase("暂无")){
            return;
        }

        String[] strings = Warehouse.split(","); //得出仓库位置
        //创建 位置
        Location location = new Location(AroundMain.getPlugin(AroundMain.class).getServer().getWorld(strings[0]),
                                        Integer.parseInt(strings[1]),
                                        Integer.parseInt(strings[2]),
                                        Integer.parseInt(strings[3]));

        Block block = location.getBlock();
        if(! block.getType().equals(Material.CHEST)){

            //此位置不是箱子
            itemStacks.clear();
            Warehouse = "暂无";
            Shop_items = new ArrayList<>(Arrays.asList("暂无"));
            inventory = null;
            ReadPlayerData.SavePlayerData(Player_name,"Warehouse");
            ReadPlayerData.SavePlayerData(Player_name,"Shop_items");
            return;

        }

        Chest chest = (Chest) block.getState(); //强制转换

        inventory = chest.getInventory();

    }

    //上架物品
    public void upItem(ItemStack itemStack,Integer integer){ //传入的是物品和价格

        ReloadInventory(); //刷新一次箱子
        int integer1 = inventory.firstEmpty(); //获得空位置
        inventory.setItem(integer1,new ItemStack(itemStack)); //存入箱子
        Shop_items.add(integer1+","+integer); //位置 + 价格

        //删除物品
        itemStack.setAmount(0);

        ReadPlayerData.SavePlayerData(Player_name,"Shop_items");
        reloadItemStacks();
    }


    //刷新物品
    public void reloadItemStacks(){
        itemStacks.clear();
        ReloadInventory();

        for (String s : Shop_items){
            if( s.equalsIgnoreCase("暂无")){
                continue;
            }

            //位置 , 价格
            String[] strings = s.split(",");

            if(inventory.getItem(Integer.parseInt(strings[0])) == null){//物品消失
                continue;
            }

            ItemStack itemStack = new ItemStack(inventory.getItem(Integer.parseInt(strings[0]))); //获取这个物品

            ItemMeta itemMeta = itemStack.getItemMeta(); //获得 itemMeta

            //这是显示在自己店铺中的 所以不需要标注 店铺名称和店主昵称
            if(itemMeta.getLore() == null){
                itemMeta.setLore(Arrays.asList(
                        "§7==========",
                        "§7所属玩家:" +Player_name,
                        "§7商品库存码:"+strings[0],
                        "§7商品价格:" + strings[1]
                ));
            }else{

                List<String> lore = itemMeta.getLore();

                lore.add("§7==========");
                lore.add("§7所属玩家:" +Player_name);
                lore.add("§7商品库存码:"+strings[0]);
                lore.add("§7商品价格:" + strings[1]);

                itemMeta.setLore(lore);
            }

            itemStack.setItemMeta(itemMeta);
            itemStacks.add(itemStack);

        }

    }

    //检查库存
    public boolean Shop_KC(int s){ //库存码 , 数量

        ReloadInventory();
        ItemStack itemStack = inventory.getItem(s);
        if(itemStack == null){ //空物品不必说
            return false;
        }
        return true;
    }

    //发出指定物品
    public ItemStack getItemStack(int CWM,int JG){
        ReloadInventory();
        ItemStack itemStack = new ItemStack(inventory.getItem(CWM));    //构建一个副本
        inventory.getItem(CWM).setAmount(0); //删除物品

        Shop_items.remove(CWM+","+JG);
        Shop_Sell ++;
        ReadPlayerData.SavePlayerData(Player_name,"Shop_Sell");
        ReadPlayerData.SavePlayerData(Player_name,"Shop_items");
        reloadItemStacks();
        return itemStack;

    }

    //是否可以继续上架
    public boolean isSJ(){

        if(itemStacks.size() <= Grade*3){ //店铺无空间

            //店铺有空间那么箱子满了吗?
            if(inventory.firstEmpty() == -1){
                AroundMain.getPlugin(AroundMain.class).getServer().getPlayer(Player_name).sendMessage(ReadLanguage.firstEmpty_Null);
                return false;
            }
            return true;
        }
        AroundMain.getPlugin(AroundMain.class).getServer().getPlayer(Player_name).sendMessage(ReadLanguage.Shop_KJ_Null);
        return false;

    }

}
