package org.jointheleague.features.abstract_classes;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.aksingh.owmjapis.api.APIException;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public abstract class Feature
        extends ListenerAdapter
{

    protected String channelName;

    public HelpEmbed helpEmbed;

    public Feature(String channelName) {
        this.channelName = channelName;
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.getChannel().getName().equals(channelName)) {
            handle(event);
        }
    }

    public HelpEmbed getHelpEmbed() {
        return this.helpEmbed;
    }

    public abstract void handle(MessageReceivedEvent event);

}