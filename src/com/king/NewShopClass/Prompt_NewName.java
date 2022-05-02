package com.king.NewShopClass;

import com.king.AroundMain;
import com.king.PojoClass.ShopPojo;
import com.king.ReadFiles.ReadConfig;
import com.king.ReadFiles.ReadLanguage;
import com.king.ReadFiles.ReadPlayerData;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.conversations.ValidatingPrompt;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;

public class Prompt_NewName extends ValidatingPrompt {

    String name;
    public Prompt_NewName(String name){
        this.name = name;
    }

    @Override
    public String getPromptText(ConversationContext context) {

        return ReadLanguage.Steps_String.get(4); //给玩家的提示消息

    }

    @Override
    protected boolean isInputValid(ConversationContext context, String input) {

        if(input.equalsIgnoreCase("中止")){
            return true;
        }

        if(input.length() < 9){ //只允许输入是或否

            if(!ReadPlayerData.ShopName.contains(input)){ //是否有重复名
                context.getForWhom().sendRawMessage(ReadLanguage.Steps_String.get(5));

                ReadPlayerData.ShopName.add(input);

                //确定没有重复名
                return true;
            }

            context.getForWhom().sendRawMessage(ReadLanguage.Steps_String.get(6));
            return false;
        }
        context.getForWhom().sendRawMessage("§c长度不可超过 8");
        return false;

    }

    //处理玩家的输入
    @Override
    protected Prompt acceptValidatedInput(ConversationContext context, String input) {
        if(!input.equalsIgnoreCase("中止")){

            ReadPlayerData.PlayerShop.get(name).Shop_name = input;
            ReadPlayerData.SavePlayerData(name,"Shop_name");
            AroundMain.getEconomy().withdrawPlayer( AroundMain.getPlugin(AroundMain.class).getServer().getPlayer(name), ReadConfig.New_shop_name_money);
        }

        return Prompt.END_OF_CONVERSATION; //结束对话

    }
}
