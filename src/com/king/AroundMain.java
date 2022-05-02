package com.king;

import com.king.AroundMainCommand.AroundCommandExecutor;
import com.king.ReadFiles.ReadConfig;
import com.king.ReadFiles.ReadGuiConfig;
import com.king.ReadFiles.ReadLanguage;
import com.king.ReadFiles.ReadPlayerData;
import com.king.RegisterEvent.EventBlockPlace;
import com.king.RegisterEvent.EventOpenGui;
import com.king.RegisterEvent.EventSendMessage;
import com.king.RegisterEvent.EventUpItem;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.HashMap;
import java.util.Objects;

public class AroundMain extends JavaPlugin {

    private static Economy econ = null;

    public static HashMap<String,Long> coolings = new HashMap<>();

    @Override
    public void onEnable() {
        getLogger().info("加载中 ... ");
        getLogger().info("作者: BIDE  版本: \33[1;32m 测试版 \33[0m");
        getLogger().info("测试版将可能遇到一些不确定问题");
        getLogger().info("\33[31m 只在BBS发布,禁止在其他平台二次上传! \33[0m");


        getLogger().info("检测必要前置 Vault");
        if( setupEconomy()){ //注册中
            getLogger().info("\33[1;32m Vault加载成功 \33[0m");
        }else{
            getLogger().info("\33[31m Vault安装失败！ \33[0m");
            return;
        }

        getLogger().info("读取各种文件 ... ");

            //配置文件
            saveDefaultConfig();    //创建配置文件
            new ReadConfig(getConfig());

            //语言文件

            if(!new File(getDataFolder(),"Language.yml").exists()) { //文件不存在才会创建
                saveResource("Language.yml", false);   //创建语言文件
            }
            ReadLanguage.ReloadLanguage();

            //Gui文件

            if(!new File(getDataFolder(),"GuiConfig.yml").exists()) { //文件不存在才会创建
                 saveResource("GuiConfig.yml", false);   //创建语言文件
            }
            ReadGuiConfig.ReloadGuiConfig();

            //玩家数据文件
            ReadPlayerData.file = getDataFolder();

                //创建文件夹

                File mulu = new File(getDataFolder(), "/PlayerData");   //创建 Tasks 目录

                if (!mulu.exists()) {mulu.mkdirs();} //如果文件不存在,创建文件夹

                //开始读取玩家数据文件

                ReadPlayerData.ReloadPlayerData();


        //注册指令
        getLogger().info("注册指令中 ... ");

            Objects.requireNonNull(this.getCommand("around")).setExecutor(new AroundCommandExecutor()); //注册指令

        getLogger().info("必要事件注册 ... ");

            //打开Gui事件
                Bukkit.getPluginManager().registerEvents(new EventOpenGui(),this); //打开Gui

            //放置方块事件
                Bukkit.getPluginManager().registerEvents(new EventBlockPlace(), this); //玩家放置方块

            //玩家右击物品
                Bukkit.getPluginManager().registerEvents(new EventUpItem(),this); //玩家右击物品

            //玩家说话事件
                Bukkit.getPluginManager().registerEvents(new EventSendMessage(),this); //玩家说话

        getLogger().info("插件加载完成");

    }

    @Override
    public void onDisable() {
        getLogger().info("欢迎下次使用");
    }


    /*
    *
    * 当插件的其他部分出现错误时,可以调用此方法
    * 向服务器日志 发送错误消息
    * 也可用于 对控制台输出 指令消息
    *
    * */
    public static void SendErrorMessage(String s){
      getPlugin(AroundMain.class).getLogger().info(s);
    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }


    //给予经济对象
    public static Economy getEconomy() {
        return econ;
    }

}
