package org.jointheleague.features.student.third_feature;

import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.entity.channel.ServerTextChannel;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.MessageBuilder;
import org.javacord.api.entity.message.component.ActionRow;
import org.javacord.api.entity.message.component.Button;
import org.javacord.api.event.interaction.MessageComponentCreateEvent;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.interaction.MessageComponentInteraction;
import org.javacord.api.listener.interaction.MessageComponentCreateListener;
import org.jointheleague.discord_bot.DiscordBot;
import org.jointheleague.features.abstract_classes.Feature;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;
import org.jointheleague.features.templates.FeatureTemplate;

import java.util.Random;

public class FeatureThree extends FeatureTemplate {
    public final String COMMAND = "!fish";

    TextChannel channel;
    public double money = 0;
    public double luckModifier = 0.00;
    String location = "Sea";



    public FeatureThree(String channelName) {
        super(channelName);

        //Create a help embed to describe feature when !help command is sent
        helpEmbed = new HelpEmbed(
                COMMAND,
                "A luck based fishing game with lots of fish, sizes of fish, qualities of fish, upgrades, and more. If new to !\u200Efish, please start with !\u200Efish tutorial. REMEMBER TO ADD A SAVING FEATURE TO ADD A TEXT KEY TO RESTORE PROGRESS"
        );
    }

    @Override
    public void handle(MessageCreateEvent event) {
        channel = event.getChannel();
        String messageContent = event.getMessageContent();
        if (messageContent.equals(COMMAND) || messageContent.equalsIgnoreCase(COMMAND+" tutorial")) {
            //respond to message here
            event.getChannel().sendMessage("Welcome to !\u200Efish. In this game you will fish, like the name implies. You will continue to fish and gain money. With this money you can upgrade you rod, luck, hook, bait, et cetera. With all of this you should get new better fish. This journey begins the command !\u200Efish menu \nFish Size Value Multiplication Chart: PLACEHOLDER \nRarity Value Multiplication Chart: PLACEHOLDER \n*BETA TUTORIAL*");
        }
        else if (messageContent.equalsIgnoreCase(COMMAND + " menu")){
           // event.getChannel().sendMessage("Message to test whether TextChannel broke again.");
            new MessageBuilder().setContent("| Current Money: $" + money+" | Current Location: "+location+" | Luck Modifier: +" + luckModifier/5+" points |").addComponents(ActionRow.of(Button.success("fish", "Go Fishing"),Button.secondary("modify", "Modify Set-Up"),Button.secondary("save", "Save Game"))).send(channel);
            event.getApi().addMessageComponentCreateListener(event2 -> {
               MessageComponentInteraction mci = event2.getMessageComponentInteraction();
               String cID = mci.getCustomId();

               switch(cID){
                   case "fish":
                       event.getChannel().sendMessage("Going fishing...");
                       randomFish(location, event);
                       randomFish(location, event);
                       randomFish(location, event);
                       randomFish(location, event);
                       randomFish(location, event);
                       randomFish(location, event);
                       break;
                   case "modify":
                       setupModification(event);
                       break;
                   case "save" :
                       event.getChannel().sendMessage("SAVING");
                       break;
               }


            });
        }
        else if(messageContent.equals(COMMAND + " testMoney")){
            money = money + 10000000;
            luckModifier = luckModifier + 21354.5;
        }
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public void addMoney(double money) {
        this.money = this.money + money;
    }
    public void randomFish(String location, MessageCreateEvent event){
        Random random = new Random();
        lootTableA[] list = lootTableA.values();
        double totalWeight = 0.0;
        double ran;
        lootTableA lt = lootTableA.Sardine;
        if(location.equals("Sea")){
           for(int i =0; i < list.length; i++){
                   totalWeight = totalWeight + list[i].getChance();
           }
            ran = random.nextDouble(totalWeight);
           totalWeight =0;
           for(int i =0; i < list.length; i++){
               totalWeight = totalWeight + list[i].getChance();
               if(totalWeight>ran){ //test luck addition system
                   lt = list[i];
                   break;
               }

           }
                   addMoney(lt.getValue());
                   event.getChannel().sendMessage(" You caught a "+lt.name() + "! "+"| You sold this fish for $"+lt.getValue()+" | Your bank account is now at $" + money);



        }
    }

    public void setupModification(MessageCreateEvent event){
        new MessageBuilder().setContent("What would you like to modify in your set-up?").addComponents(ActionRow.of(Button.success("upgrade", "Buy Upgrades"),Button.success("location", "Change Location"), Button.danger("leave", "Cancel"))).send(channel);
        event.getApi().addMessageComponentCreateListener(event2 -> {
            MessageComponentInteraction mci = event2.getMessageComponentInteraction();
            String cID = mci.getCustomId();

        switch(cID){
            case "upgrade":
                upgradeMenu(event);
                break;
            case "location":
                break;
            case "leave":
                //clear old message here
                event.getChannel().sendMessage("Cancelling modification menu. Please go back to !fish menu.");
            break;
            default:
                break;
        }
        });
        }
        public void upgradeMenu(MessageCreateEvent event) {
            new MessageBuilder().setContent("What would you like to buy").addComponents(ActionRow.of(Button.success("0", "Carbon Fibre Rod | $125.25"), Button.success("1", "Deep-Dive Lure | $35.50"), Button.danger("2", "Better Boat Service | $1250.75"))).send(channel);
            event.getApi().addMessageComponentCreateListener(event2 -> {
            MessageComponentInteraction mci = event2.getMessageComponentInteraction();
            String cID = mci.getCustomId();
            //add system to stop purchasing multiple  plus a buy message.
            switch (cID) {
                case "0":
                    if(money>=125.25) {
                        money = money - 125.25;
                        luckModifier = luckModifier + 25;
                        event.getChannel().sendMessage("Congrats, you now own a carbon fibre fishing rod!");
                    }
                    else{
                        event.getChannel().sendMessage("You cannot afford this item.");
                    }
                    break;
                case "1":
                    if(money>=35.50) {
                        money = money - 35.50;
                        luckModifier = luckModifier + 5.75;
                        event.getChannel().sendMessage("Congrats, you now own a deep-dive lure!");
                    }
                    else{
                        event.getChannel().sendMessage("You cannot afford this item.");
                    }
                    break;
                case "2":
                    if(money>=1250.75) {
                        money = money - 1250.75;
                        luckModifier = luckModifier + 12.5;
                        event.getChannel().sendMessage("Congrats, you now have a better boat service!");
                        //new location available
                    }
                    else{
                        event.getChannel().sendMessage("You cannot afford this item.");
                    }
                    break;
            }
        });
        }
        }





