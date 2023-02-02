package org.jointheleague.features.examples.custom_features;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.features.abstract_classes.Feature;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;

public class CreateShapes extends Feature {
    public final String COMMAND = "!shapes";
    private HelpEmbed helpEmbed;

    public CreateShapes(String channelName) {
        super(channelName);
        HelpEmbed helpEmbed = new HelpEmbed(COMMAND, "Greets you");
    }

    @Override
    public void handle(MessageCreateEvent event) {
        String messageContent = event.getMessageContent();
        if (messageContent.startsWith(COMMAND)) {
            event.getChannel().sendMessage("Hello " + event.getMessageAuthor().getName() + ".");
        }
    }
}
