package org.jointheleague.features.help_embed;

import java.util.ArrayList;
import java.util.List;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;
import org.jointheleague.features.abstract_classes.Feature;

public class HelpListener extends Feature {

	public final String COMMAND = "!help";
	private List<HelpEmbed> helpEmbeds = new ArrayList<>();

	public HelpListener(String channelName) {
		super(channelName);
	}

	@Override
	public void handle(MessageReceivedEvent event) {
		String messageContent = event.getMessage().getContentStripped();
		if(messageContent.equalsIgnoreCase(COMMAND)) {
			for(HelpEmbed helpEmbed : helpEmbeds) {
				event.getChannel().sendMessage(helpEmbed.getEmbed());
			}
		}
	}
	
	public void addHelpEmbed(HelpEmbed helpEmbed) {
		helpEmbeds.add(helpEmbed);
	}
	
	
}
