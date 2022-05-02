package com.king.NewShopClass;

/*
*
* 创建玩家店铺的类
*
* */

import com.king.AroundMain;
import com.king.ReadFiles.ReadLanguage;
import org.bukkit.conversations.Conversation;
import org.bukkit.conversations.ConversationAbandonedEvent;
import org.bukkit.conversations.ConversationAbandonedListener;
import org.bukkit.conversations.ConversationFactory;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashSet;

public class NewShop {

    public static HashSet<String> PromptInPlayer = new HashSet<>(); //正在执行对话的玩家

    public static HashSet<String> PlayerName = new HashSet<>(); //是否需要被监听 放置方块事件?

    public static void NewShop(Player player){

        if(PromptInPlayer.contains(player.getName())){
            return;
        }

        PromptInPlayer.add(player.getName());

        Conversation conversation = new ConversationFactory(AroundMain.getPlugin(AroundMain.class))
                .withFirstPrompt(new PromptA(player.getName()))
                .addConversationAbandonedListener(new ConversationAbandonedListener() {
                    @Override
                    public void conversationAbandoned(ConversationAbandonedEvent abandonedEvent) {
                        if (abandonedEvent.gracefulExit()) {
                            abandonedEvent.getContext().getForWhom().sendRawMessage("对话结束");
                            PromptInPlayer.remove(player.getName());
                        }
                    }
                })
                .buildConversation(player);

        conversation.begin();
    }

    public static void put_CK(Player player){

        if(PromptInPlayer.contains(player.getName())){

            return;
        }

        PromptInPlayer.add(player.getName());

        Conversation conversation = new ConversationFactory(AroundMain.getPlugin(AroundMain.class))
                .withFirstPrompt(new Prompt_putCK(player.getName()))
                .addConversationAbandonedListener(new ConversationAbandonedListener() {
                    @Override
                    public void conversationAbandoned(ConversationAbandonedEvent abandonedEvent) {
                        if (abandonedEvent.gracefulExit()) {
                            abandonedEvent.getContext().getForWhom().sendRawMessage("对话结束");
                            PromptInPlayer.remove(player.getName());
                        }
                    }
                })
                .buildConversation(player);

        conversation.begin();
    }

    //上架物品
    public static void Up_Item(Player player, ItemStack itemStack){

        if(PromptInPlayer.contains(player.getName())){

            return;
        }

        PromptInPlayer.add(player.getName());

        Conversation conversation = new ConversationFactory(AroundMain.getPlugin(AroundMain.class))

                .withFirstPrompt(new PromptUpItem(player.getName(),itemStack))
                .addConversationAbandonedListener(new ConversationAbandonedListener() {
                    @Override
                    public void conversationAbandoned(ConversationAbandonedEvent abandonedEvent) {
                        if (abandonedEvent.gracefulExit()) {
                            abandonedEvent.getContext().getForWhom().sendRawMessage("对话结束");
                            PromptInPlayer.remove(player.getName());
                        }
                    }
                })
                .buildConversation(player);

        conversation.begin();

    }

    //上架物品
    public static void NewName(Player player){

        if(PromptInPlayer.contains(player.getName())){

            return;
        }

        PromptInPlayer.add(player.getName());

        Conversation conversation = new ConversationFactory(AroundMain.getPlugin(AroundMain.class))

                .withFirstPrompt(new Prompt_NewName(player.getName()))
                .addConversationAbandonedListener(new ConversationAbandonedListener() {
                    @Override
                    public void conversationAbandoned(ConversationAbandonedEvent abandonedEvent) {
                        if (abandonedEvent.gracefulExit()) {
                            abandonedEvent.getContext().getForWhom().sendRawMessage(ReadLanguage.New_shop_name);
                            PromptInPlayer.remove(player.getName());
                        }
                    }
                })
                .buildConversation(player);

        conversation.begin();

    }

}
