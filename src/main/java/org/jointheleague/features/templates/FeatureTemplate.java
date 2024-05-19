package org.jointheleague.features.templates;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jointheleague.features.abstract_classes.Feature;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;

public class FeatureTemplate extends Feature {

    public final String COMMAND = "!command";

    public FeatureTemplate(String channelName) {
        super(channelName);

        //Create a help embed to describe feature when !help command is sent
        helpEmbed = new HelpEmbed(
                COMMAND,
                "Give a brief description of your feature here, including how the user interacts with it"
        );
    }

    @Override
    public void handle(MessageReceivedEvent event) {
        String messageContent = event.getMessage().getContentStripped();
        if (messageContent.startsWith(COMMAND)) {
            //respond to message here
            event.getChannel().sendMessage("Sending a message to the channel");
        }
    }

}
