package com.king.NewShopClass;

import com.king.ReadFiles.ReadConfig;
import com.king.ReadFiles.ReadLanguage;
import com.king.ReadFiles.ReadPlayerData;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.conversations.ValidatingPrompt;

public class PromptD extends ValidatingPrompt {

    String name;
    public PromptD(String name){
        this.name = name;
    }

    @Override
    public String getPromptText(ConversationContext context) {

        return ReadLanguage.Steps_String.get(7); //给玩家的提示消息

    }

    @Override
    protected boolean isInputValid(ConversationContext context, String input) {

        if(input.equalsIgnoreCase("中止")){return true;}

        if(input.length() < ReadConfig.Shop_lore_length+1){ //只允许输入是或否

            return true;
        }
        context.getForWhom().sendRawMessage("§c长度不可超过"+ReadConfig.Shop_lore_length);
        return false;

    }

    //处理玩家的输入
    @Override
    protected Prompt acceptValidatedInput(ConversationContext context, String input) {
        if(input.equalsIgnoreCase("中止")){

            ReadPlayerData.ShopName.remove(ReadPlayerData.PlayerShop.get(name).Shop_name);
            ReadPlayerData.PlayerShop.remove(name); //删除此玩家的数据

            return Prompt.END_OF_CONVERSATION; //结束对话
        }else{

            //设置lore
            ReadPlayerData.PlayerShop.get(name).Shop_lore = input;

            return new PromptE(name);
        }

    }
}