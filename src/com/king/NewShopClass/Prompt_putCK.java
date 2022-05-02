package com.king.NewShopClass;

import com.king.ReadFiles.ReadLanguage;
import com.king.ReadFiles.ReadPlayerData;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.conversations.ValidatingPrompt;

public class Prompt_putCK extends ValidatingPrompt {

    String name;
    public Prompt_putCK(String name){
        this.name = name;
    }

    @Override
    public String getPromptText(ConversationContext context) {

        NewShop.PlayerName.add(name);
        return ReadLanguage.anew_put_Warehouse; //给玩家的提示消息

    }

    @Override
    protected boolean isInputValid(ConversationContext context, String input) {

        if(input.equalsIgnoreCase("放了")){

            //判断到底放了没
            if(! NewShop.PlayerName.contains(name)) { //没就是放了 有就是没放

                context.getForWhom().sendRawMessage(ReadLanguage.Steps_String.get(10));

                //保存文件
                ReadPlayerData.SavePlayerData(name,"全部");

                return true;

            }
            context.getForWhom().sendRawMessage("§c你并没有放置仓库哦,请注意一定要是 §l原版的普通箱子 ");
            return false;
        }else if (input.equalsIgnoreCase("中止")){

            NewShop.PlayerName.remove(name);

            return true;
        }

        context.getForWhom().sendRawMessage("§c输入错误！");
        return false;

    }

    //处理玩家的输入
    @Override
    protected Prompt acceptValidatedInput(ConversationContext context, String input) {

            return Prompt.END_OF_CONVERSATION; //结束对话

    }
}