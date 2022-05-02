package com.king.NewShopClass;

import com.king.ReadFiles.ReadLanguage;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.conversations.ValidatingPrompt;

public class PromptB extends ValidatingPrompt {

    String name;
    public PromptB(String name){
        this.name = name;
    }

    @Override
    public String getPromptText(ConversationContext context) {

        for (String s : ReadLanguage.Rules_String) {
            context.getForWhom().sendRawMessage(s);
        }

        return ReadLanguage.Steps_String.get(2); //给玩家的提示消息

    }

    @Override
    protected boolean isInputValid(ConversationContext context, String input) {

            return true;

    }

    //处理玩家的输入
    @Override
    protected Prompt acceptValidatedInput(ConversationContext context, String input) {
        if (input.equalsIgnoreCase("可以")) {
            context.getForWhom().sendRawMessage(ReadLanguage.Steps_String.get(3));
            return new PromptC(name);
        } else {
            return Prompt.END_OF_CONVERSATION; //结束对话
        }

    }
}
