package org.jointheleague.discord_bot;

import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.requests.GatewayIntent;

import net.dv8tion.jda.api.requests.restaction.MessageCreateAction;
import net.dv8tion.jda.api.utils.messages.MessageCreateBuilder;
import org.jointheleague.features.abstract_classes.Feature;
import org.jointheleague.features.examples.second_features.HighLowGame;
import org.jointheleague.features.examples.third_features.CatFactsApi;
import org.jointheleague.features.examples.third_features.NewsApi;
import org.jointheleague.features.examples.first_features.CurrentTime;
import org.jointheleague.features.help_embed.HelpListener;
import org.jointheleague.features.student.first_feature.FeatureOne;

import java.util.concurrent.ExecutionException;

public class DiscordBot {

	private String token;

	private String channelName;

	JDA api;

	HelpListener helpListener;

	public DiscordBot(String token, String channelName) {
		this.token = token;
		this.channelName = channelName;
		helpListener = new HelpListener(channelName);
	}

	public void connect(boolean printInvite) throws ExecutionException, InterruptedException {
		api = JDABuilder.createDefault(token)
				.enableIntents(GatewayIntent.MESSAGE_CONTENT) // enables explicit access to message.getContentDisplay()
				.build();
		api.awaitReady();

		//Print the URL to invite the bot
		if (printInvite) {
			System.out.println("To authorize your bot, send your teacher this link: " + api.getInviteUrl()
					+"\n\tThis message can be disabled in org.jointheleague.Launcher.java");
		}

		//Send bot connected message in channel
		api.getTextChannelsByName(channelName, true).forEach(e -> {
			MessageCreateBuilder builder = new MessageCreateBuilder();
			builder.addContent(api.getSelfUser().getName() + " has connected");
			MessageCreateAction message = e.sendMessage(builder.build());
			System.out.println(message);
        });

		//add help listener to bot
		api.addEventListener(helpListener);

		//add features
		addFeature(new FeatureOne(channelName));
		addFeature(new CurrentTime(channelName));
		addFeature(new HighLowGame(channelName));
		addFeature(new NewsApi(channelName));
		addFeature(new CatFactsApi(channelName));
	}

	private void addFeature(Feature feature){
		api.addEventListener(feature);
		helpListener.addHelpEmbed(feature.getHelpEmbed());
	}
}
