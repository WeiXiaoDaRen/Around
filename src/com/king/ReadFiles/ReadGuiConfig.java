package com.king.ReadFiles;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;

public class ReadGuiConfig {

    public static ItemStack TYZS_lattice; //通用装饰格子

    public static ItemStack SPGM_lattice; //购买商品按钮

    public static ItemStack SJSP_lattice; //上架商品按钮

    public static ItemStack XJSP_lattice; //下架商品按钮

    //店铺信息按钮 采用 热创建 (每次打开GUI都单独创建) 不可自定义
    public static String DPXX_TYPE; //只可以自定义材质

    public static ItemStack DPGY_lattice; //店铺改名

    public static ItemStack SXWP_lattice; //刷新列表按钮

    public static ItemStack FYGZ_lattice; //等级不足时封印的上架店铺空间

    public static ItemStack DPDZ_lattice; //赞一个！




    public static void ReloadGuiConfig(){

        FileConfiguration fileConfiguration = YamlConfiguration.loadConfiguration(new File(
                com.king.AroundMain.getPlugin(com.king.AroundMain.class
                ).getDataFolder(),"GuiConfig.yml"));

        DPXX_TYPE = fileConfiguration.getString("DPXX_TYPE");

        //正式读取
        ItemMeta itemMeta;

        TYZS_lattice = new ItemStack(Material.getMaterial(fileConfiguration.getString("TYZS_lattice.Type")));
        itemMeta = TYZS_lattice.getItemMeta();
        itemMeta.setDisplayName(fileConfiguration.getString("TYZS_lattice.Name"));
        itemMeta.setLore(fileConfiguration.getStringList("TYZS_lattice.Lore"));
        TYZS_lattice.setItemMeta(itemMeta);

        XJSP_lattice = new ItemStack(Material.getMaterial(fileConfiguration.getString("XJSP_lattice.Type")));
        itemMeta = XJSP_lattice.getItemMeta();
        itemMeta.setDisplayName(fileConfiguration.getString("XJSP_lattice.Name"));
        itemMeta.setLore(fileConfiguration.getStringList("XJSP_lattice.Lore"));
        XJSP_lattice.setItemMeta(itemMeta);

        SJSP_lattice = new ItemStack(Material.getMaterial(fileConfiguration.getString("SJSP_lattice.Type")));
        itemMeta = SJSP_lattice.getItemMeta();
        itemMeta.setDisplayName(fileConfiguration.getString("SJSP_lattice.Name"));
        itemMeta.setLore(fileConfiguration.getStringList("SJSP_lattice.Lore"));
        SJSP_lattice.setItemMeta(itemMeta);

        DPGY_lattice = new ItemStack(Material.getMaterial(fileConfiguration.getString("DPGY_lattice.Type")));
        itemMeta = DPGY_lattice.getItemMeta();
        itemMeta.setDisplayName(fileConfiguration.getString("DPGY_lattice.Name"));
        itemMeta.setLore(fileConfiguration.getStringList("DPGY_lattice.Lore"));
        DPGY_lattice.setItemMeta(itemMeta);

        FYGZ_lattice = new ItemStack(Material.getMaterial(fileConfiguration.getString("FYGZ_lattice.Type")));
        itemMeta = FYGZ_lattice.getItemMeta();
        itemMeta.setDisplayName(fileConfiguration.getString("FYGZ_lattice.Name"));
        itemMeta.setLore(fileConfiguration.getStringList("FYGZ_lattice.Lore"));
        FYGZ_lattice.setItemMeta(itemMeta);

        SPGM_lattice = new ItemStack(Material.getMaterial(fileConfiguration.getString("SPGM_lattice.Type")));
        itemMeta = SPGM_lattice.getItemMeta();
        itemMeta.setDisplayName(fileConfiguration.getString("SPGM_lattice.Name"));
        itemMeta.setLore(fileConfiguration.getStringList("SPGM_lattice.Lore"));
        SPGM_lattice.setItemMeta(itemMeta);

        SXWP_lattice = new ItemStack(Material.getMaterial(fileConfiguration.getString("SXWP_lattice.Type")));
        itemMeta = SXWP_lattice.getItemMeta();
        itemMeta.setDisplayName(fileConfiguration.getString("SXWP_lattice.Name"));
        itemMeta.setLore(fileConfiguration.getStringList("SXWP_lattice.Lore"));
        SXWP_lattice.setItemMeta(itemMeta);

        DPDZ_lattice = new ItemStack(Material.getMaterial(fileConfiguration.getString("DPDZ_lattice.Type")));
        itemMeta = DPDZ_lattice.getItemMeta();
        itemMeta.setDisplayName(fileConfiguration.getString("DPDZ_lattice.Name"));
        itemMeta.setLore(fileConfiguration.getStringList("DPDZ_lattice.Lore"));
        DPDZ_lattice.setItemMeta(itemMeta);

    }
}
