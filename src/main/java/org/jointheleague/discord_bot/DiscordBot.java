package org.jointheleague.discord_bot;
import java.util.*;
import org.javacord.api.*;
import org.javacord.api.entity.message.*;
import org.javacord.api.listener.message.*;
import org.javacord.api.util.event.*;
import org.javacord.api.entity.channel.*;
import org.javacord.api.entity.emoji.*;
public class DiscordBot {
	DiscordApi API;
	public DiscordBot(String Token) {
		this.API = new DiscordApiBuilder().setToken(Token).login().join();
	}
	HashMap<Long, ListenerManager<MessageCreateListener>> AnnoyedChannels = new HashMap<Long, ListenerManager<MessageCreateListener>>();
	HashMap<Long, String> AnnoyedChannelStarters = new HashMap<Long, String>();
	public void Connect() {
		long ClientID = API.getClientId();
		API.addMessageCreateListener(E -> {
			String Content = E.getMessageContent();
			String[] Data = Content.substring(6).split(" ");
			TextChannel Channel = E.getChannel();
			Long ChannelID = Channel.getId();
			String AuthorName = E.getMessageAuthor().asUser().get().getName();
			if (Content.startsWith("BBot: ")) {
				switch (Data[0]) {
					case "Annoy":
						if (!AnnoyedChannels.containsKey(ChannelID)) {
							Channel.sendMessage("Done");
							AnnoyedChannels.put(ChannelID, Channel.addMessageCreateListener(M -> {
								if (M.getMessageAuthor().getId() != ClientID) {
									Channel.sendMessage("\"" + M.getMessageContent() + "\"");
								}
							}));
							AnnoyedChannelStarters.put(ChannelID, AuthorName);
						} else {
							Channel.sendMessage("Already active");
						}
						break;
					case "StopAnnoy":
						if (!AnnoyedChannels.containsKey(ChannelID)) {
							Channel.sendMessage("Not active");
						} else if (!AnnoyedChannelStarters.get(ChannelID).equals(AuthorName)) {
							Channel.sendMessage("Nice try...");
						} else {
							Channel.sendMessage("Done");
							AnnoyedChannels.get(ChannelID).remove();
							AnnoyedChannels.remove(ChannelID);
							AnnoyedChannelStarters.remove(ChannelID);
						}
						break;
					case "PollCreate":
						if (Channel.getType() == ChannelType.PRIVATE_CHANNEL) {
							try {
								Message ReactionMessage = Channel.sendMessage("React with options (" + Emojis.CheckBox + " when done)").get();
								ReactionMessage.addReactionAddListener(M -> {
									Emoji TheEmoji = M.getEmoji();
									if (TheEmoji.equalsEmoji(Emojis.CheckBox)) {
										ReactionMessage.delete();
										Channel.sendMessage("Yes");
									} else {
										ReactionMessage.addReaction(TheEmoji);
									}
								});
								ReactionMessage.addReactionRemoveListener(M -> {
									
								});
							} catch(Exception B) {
								B.printStackTrace();
							}
							
						} else {
							Channel.sendMessage("Only for DMs");
						}
						break;
					case "Poll":
						break;
					default:
						Channel.sendMessage("\"BBot: Help\" for help");
						break;
				}
			}
		});
	}
}