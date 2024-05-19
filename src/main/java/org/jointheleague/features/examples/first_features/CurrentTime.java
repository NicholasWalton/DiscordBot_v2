package org.jointheleague.features.examples.first_features;


import java.text.SimpleDateFormat;
import java.util.Date;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jointheleague.features.abstract_classes.Feature;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;

public class CurrentTime extends Feature {

	public final String COMMAND = "!time";

	public CurrentTime(String channelName) {
		super(channelName);
		helpEmbed = new HelpEmbed(COMMAND, "Displays the current time");
	}

	@Override
	public void handle(MessageReceivedEvent event) {
		String messageContent = event.getMessage().getContentStripped();
		if (messageContent.startsWith(COMMAND)) {
			Date date = new Date(System.currentTimeMillis());
			SimpleDateFormat formatter = new SimpleDateFormat("'The time is 'HH:mm:ss z' on 'MM-dd-yyyy'.'");
			event.getChannel().sendMessage(formatter.format(date));
		}
	}

}
