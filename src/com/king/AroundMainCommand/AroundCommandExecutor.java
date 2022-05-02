package com.king.AroundMainCommand;

import com.king.AroundMain;
import com.king.GuiPack.OpenPlayershopGui;
import com.king.GuiPack.OpenshopGui;
import com.king.GuiPack.OpenstoreGui;
import com.king.NewShopClass.NewShop;
import com.king.ReadFiles.ReadConfig;
import com.king.ReadFiles.ReadGuiConfig;
import com.king.ReadFiles.ReadLanguage;
import com.king.ReadFiles.ReadPlayerData;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class AroundCommandExecutor implements CommandExecutor, TabExecutor {

    public String[] Commands = {"shop","store","ban","fine","renamed","reload","level"};  //代表着指令的数组 用于指令补全

    public String[] PlayerCommands = {"shop","store"};  //代表着指令的数组 用于指令补全

    /*
    * 玩家指令 :
    *   /around shop     打开逛逛商城
    *   /around store    打开自己的店铺
    *
    * 管理员命令:
    *   /around ban <ShopName>              封禁/解封 某店铺
    *   /around fine <Number> <ShopName>    对某店铺处以罚金 (未缴纳罚金的话 店铺将处于封禁状态)
    *   /around renamed <ShopName> <NewName> 强制修改某店铺名称
    *   /around reload                      重载配置文件
    * */

    /*
    *
    * 指令独立类
    *
    * */
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        //命令帮助

        if(strings.length == 0){

            if(commandSender.isOp()){

                SendMessage(commandSender,"§c********§4***** - §l管理员指令集");
                SendMessage(commandSender,"§7▸ /around ban <Player>               §8封禁/解封 某店铺");
                SendMessage(commandSender,"§7▸ /around fine <Player> <Number>     §8对某店铺处以罚金 (未缴纳罚金的话 店铺将无法正常使用)");
                SendMessage(commandSender,"§7▸ /around renamed <Player> <NewName> §8强制修改某店铺名称");
                SendMessage(commandSender,"§7▸ /around level <Player> <Number>    §8给予店铺经验 (负数为减去经验,但不可使对方经验低于0)");
                SendMessage(commandSender,"§7▸ /around reload                       §8重载配置文件");
                SendMessage(commandSender,"§c********§4***** - §l管理员指令集");

            }else {

                SendMessage(commandSender,"§a********** - §l普通玩家指令集");
                SendMessage(commandSender,"§7▸ /around shop     §8打开逛逛商城");
                SendMessage(commandSender,"§7▸ /around store    §8打开自己的店铺");
                SendMessage(commandSender,"§a********** - §l普通玩家指令集");

            }

            return true;
        }

        //下面的指令 只有玩家能完成
        if(commandSender instanceof Player){

            switch (strings[0]){
                case "shop":{ //√
                    // 打开逛逛商城

                    new OpenshopGui((Player) commandSender);
                    SendMessage(commandSender,ReadLanguage.Go_shop);

                    return true;
                }
                case "store":{ //√
                    // 打开自己的店铺

                    //检查有没有店铺
                    if(ReadPlayerData.PlayerShop.keySet().contains(commandSender.getName())){ //打开店铺

                        if(ReadPlayerData.PlayerShop.get(commandSender.getName()).Warehouse.equalsIgnoreCase("暂无")){
                            //没有仓库！
                            SendMessage(commandSender,ReadLanguage.Shop_Warehouse_ERROR_2);

                            NewShop.put_CK((Player) commandSender);

                            return true;
                        }
                        SendMessage(commandSender, ReadLanguage.Open_shop);
                        new OpenstoreGui((Player) commandSender);

                    }else{ //进入创建店铺

                        NewShop.NewShop((Player) commandSender);

                    }

                    return true;
                }
                case "open":{

                    //打开他人店铺 （不对外开放 留作JSON用）
                    if(strings.length > 1) {
                        new OpenPlayershopGui((Player) commandSender, strings[1]);
                    }

                    return true;
                }
                default:{
                    break;
                }
            }

        }


        //下面的指令 只有op权限能执行
        if(commandSender.hasPermission("around.set") || commandSender.isOp()){

            switch (strings[0]){

                case "ban":{ //√

                    //检查自变量
                    if(strings.length < 2){
                        SendMessage(commandSender,ReadLanguage.Plugin_prefix +"§c自变量个数不足！");
                        break;
                    }

                    if(! ReadPlayerData.PlayerShop.keySet().contains(strings[1])){
                        SendMessage(commandSender,ReadLanguage.Plugin_prefix + "§c未有此玩家的数据！");
                        break;
                    }

                    //封禁or解封店铺店铺
                    ReadPlayerData.PlayerShop.get(strings[1]).Normal = !ReadPlayerData.PlayerShop.get(strings[1]).Normal;
                    if(ReadPlayerData.PlayerShop.get(strings[1]).Normal){
                        SendMessage(commandSender,ReadLanguage.Plugin_prefix +"§a你解封了玩家的店铺");
                    }else{
                        SendMessage(commandSender,ReadLanguage.Plugin_prefix +"§c你封禁了玩家的店铺！");
                    }
                    ReadPlayerData.SavePlayerData(strings[1],"Normal");
                    break;
                }
                case "fine":{ //√

                    //检查自变量
                    if(strings.length < 3){
                        SendMessage(commandSender,ReadLanguage.Plugin_prefix +"§c自变量个数不足！");
                        break;
                    }

                    if(! ReadPlayerData.PlayerShop.keySet().contains(strings[1])){
                        SendMessage(commandSender,ReadLanguage.Plugin_prefix + "§c未有此玩家的数据！");
                        break;
                    }

                    //对店铺处以罚金
                    ReadPlayerData.PlayerShop.get(strings[1]).Penalty += Integer.parseInt(strings[2]);
                    SendMessage(commandSender,ReadLanguage.Plugin_prefix +"§c成功对其处以罚金");
                    ReadPlayerData.SavePlayerData(strings[1],"Penalty");
                    break;
                }
                case "renamed":{ //√

                    //检查自变量
                    if(strings.length < 3){
                        SendMessage(commandSender,ReadLanguage.Plugin_prefix +"§c自变量个数不足！");
                        break;
                    }

                    if(! ReadPlayerData.PlayerShop.keySet().contains(strings[1])){
                        SendMessage(commandSender,ReadLanguage.Plugin_prefix + "§c未有此玩家的数据！");
                        break;
                    }

                    if(ReadPlayerData.ShopName.contains(strings[2])){
                        SendMessage(commandSender,ReadLanguage.Plugin_prefix +"§c此名称已存在");
                        break;
                    }

                    //强制修改店铺名称
                    ReadPlayerData.ShopName.add(strings[2]);
                    ReadPlayerData.ShopName.remove(ReadPlayerData.PlayerShop.get(strings[1]).Shop_name);
                    ReadPlayerData.PlayerShop.get(strings[1]).Shop_name = strings[2];
                    SendMessage(commandSender,ReadLanguage.Plugin_prefix +"§a修改完毕");
                    ReadPlayerData.SavePlayerData(strings[1],"Shop_name");
                    break;
                }
                case "level":{ //√

                    //检查自变量
                    if(strings.length < 3){
                        SendMessage(commandSender,ReadLanguage.Plugin_prefix +"§c自变量个数不足！");
                        break;
                    }

                    if(! ReadPlayerData.PlayerShop.keySet().contains(strings[1])){
                        SendMessage(commandSender,ReadLanguage.Plugin_prefix + "§c未有此玩家的数据！");
                        break;
                    }

                    //对店铺处以罚金
                    ReadPlayerData.PlayerShop.get(strings[1]).Levels += Integer.parseInt(strings[2]);
                    SendMessage(commandSender,ReadLanguage.Plugin_prefix +"§c成功修改其经验");
                    ReadPlayerData.SavePlayerData(strings[1],"Levels");
                    break;
                }
                case "reload":{ //√

                    //重载配置文件
                    ReadGuiConfig.ReloadGuiConfig();
                    ReadLanguage.ReloadLanguage();
                    ReadConfig.ReloadConfig();
                    SendMessage(commandSender,ReadLanguage.Plugin_prefix +"§a重载完毕 (不包括数据文件)");
                    break;
                }
                default:{
                    SendMessage(commandSender,ReadLanguage.Plugin_prefix +"§a/around 查看命令帮助");
                    break;
                }
            }
            return true;
        }

        return false;
    }


    /*
    *
    * Tab补全指令系统
    *
    * */
    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {

        if(commandSender.isOp()){

            if (strings.length > 1) return Collections.singletonList(commandSender.getName()); //如果你已经输入了则返回空列表

            if (strings.length == 0) return Arrays.asList(Commands); //如果只输入了 指令头 则返回所有的子命令

            return Arrays.stream(Commands).filter(S -> S.startsWith(strings[0])).collect(Collectors.toList());

        }else{


            if (strings.length > 1) return Collections.singletonList(commandSender.getName()); //如果你已经输入了则返回空列表

            if (strings.length == 0) return Arrays.asList(PlayerCommands); //如果只输入了 指令头 则返回所有的子命令

            return Arrays.stream(PlayerCommands).filter(S -> S.startsWith(strings[0])).collect(Collectors.toList());

        }


    }

    //发送消息
    public void SendMessage(CommandSender commandSender,String s){
        if(commandSender instanceof  Player){
            commandSender.sendMessage(s);
        }else{
            commandSender.getServer().getLogger().info(s);
        }
    }
}
