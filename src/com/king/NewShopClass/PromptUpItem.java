package com.king.NewShopClass;

import com.king.ReadFiles.ReadLanguage;
import com.king.ReadFiles.ReadPlayerData;
import com.king.ToolClass;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.conversations.ValidatingPrompt;
import org.bukkit.inventory.ItemStack;

public class PromptUpItem extends ValidatingPrompt {

    String name;
    ItemStack itemStack;
    public PromptUpItem(String name, ItemStack itemStack){
        this.name = name;
        this.itemStack = itemStack;
    }

    @Override
    public String getPromptText(ConversationContext context) {
        return ReadLanguage.Enter_pricing; //给玩家的提示消息
    }

    @Override
    protected boolean isInputValid(ConversationContext context, String input) {
        return true;
    }

    //处理玩家的输入
    @Override
    protected Prompt acceptValidatedInput(ConversationContext context, String input) {

        input = input.replaceAll("\\.",""); //去除 .
        input = input.replaceAll("-",""); //去除 负数

        //输入是否为数字
        if(ToolClass.isNumber(input)){
            //是数字则开始上架

            ReadPlayerData.PlayerShop.get(name).upItem(itemStack,Integer.parseInt(input));

            context.getForWhom().sendRawMessage(ReadLanguage.Up_Item_ok);

        }else{
            //非数字取消上架
            context.getForWhom().sendRawMessage(ReadLanguage.Cancel_up_item);
        }
        return Prompt.END_OF_CONVERSATION; //结束对话

    }
}
