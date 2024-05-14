package org.jointheleague.features.abstract_classes;

import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.aksingh.owmjapis.api.APIException;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;

public abstract class Feature extends ListenerAdapter {

    protected String channelName;

    public HelpEmbed helpEmbed;

    public Feature(String channelName) {
        this.channelName = channelName;
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
//        event.getChannel().ifPresent(e -> {
//            if (e.getName().equals(channelName)) {
//                try {
//                    handle(null);
//                } catch (APIException e1) {
//                    e1.printStackTrace();
//                }
//            }
//        });
    }

    public HelpEmbed getHelpEmbed() {
        return this.helpEmbed;
    }

    public abstract void handle(MessageCreateEvent event) throws APIException;

}