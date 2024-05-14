package org.jointheleague;

import org.jointheleague.discord_bot.DiscordBot;

import java.util.concurrent.ExecutionException;

public class Launcher {
    public static void main(String[] args) throws ExecutionException, InterruptedException {

        //Initialize variables
        String channelName = System.getenv("CHANNEL_NAME");
        String discordToken = System.getenv("DISCORD_TOKEN");
        boolean printDiscordInvite = true;

        //Instantiate DiscordBot and connect
        DiscordBot discordBot =  new DiscordBot(discordToken, "general");
        discordBot.connect(printDiscordInvite);

    }
}
