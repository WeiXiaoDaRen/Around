package com.king.NewShopClass;

import com.king.ReadFiles.ReadLanguage;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.conversations.ValidatingPrompt;

public class PromptA extends ValidatingPrompt {

    String name;
    public PromptA(String name){
        this.name = name;
    }

    @Override
    public String getPromptText(ConversationContext context) {
        return ReadLanguage.Steps_String.get(0); //给玩家的提示消息
    }

    @Override
    protected boolean isInputValid(ConversationContext context, String input) {

            return true;

    }

    //处理玩家的输入
    @Override
    protected Prompt acceptValidatedInput(ConversationContext context, String input) {

        if (input.equalsIgnoreCase("是")) {

            context.getForWhom().sendRawMessage(ReadLanguage.Steps_String.get(1));
            return new PromptB(name);
        } else {
            return Prompt.END_OF_CONVERSATION; //结束对话
        }

    }
}
