package org.jointheleague.features.examples.second_features;

import net.aksingh.owmjapis.api.APIException;
import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.features.abstract_classes.Feature;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;

public class GuessAge extends Feature {
    public final String COMMAND = "!guessAge";
    int ageGuess;

    public GuessAge(String channelName) {
        super(channelName);
        helpEmbed = new HelpEmbed(COMMAND, "Give me your age, and I will determine if you're old enough to drive");
    }

    @Override
    public void handle(MessageCreateEvent event){
        String messageContent = event.getMessageContent();

        //start the game with the command
        if (messageContent.equals(COMMAND)) {
            event.getChannel().sendMessage("Give me your age and I will determine if you are old enough to drive. Guess by using e.g. !guessAge 5");
        }
        //check a guess
        else if (messageContent.contains(COMMAND)
                && !messageContent.contains("e.g.")
                && !messageContent.contains("this:")) {

            //check if the game has been started
            if(ageGuess == 0){
                //tell them to start the game first
                event.getChannel().sendMessage("Please start the game first using just the command");
                return;
            }

            //parse the guess from the message
            String guessMessage = messageContent.replaceAll(" ", "").replace(COMMAND, "");

            //change the guess to an int
            int guess = 0;
            try{
                guess = Integer.parseInt(guessMessage);
            }
            catch(NumberFormatException e){
                //tell them to format their guess properly
                event.getChannel().sendMessage("Please format your guess like this: " + COMMAND + " 5");
                return;
            }

            if (guess < ageGuess) {
                //tell them it's too low
                event.getChannel().sendMessage(guess + " is too low.  Guess again!");
            } else if (guess > ageGuess) {
                //tell them it's too high
                event.getChannel().sendMessage(guess + " is too high.  Guess again!");
            } else {
                //they got it correct
                event.getChannel().sendMessage("Correct!  The number I picked was " + ageGuess);
                //set numberToGuess back to 0
                ageGuess = 0;
            }

        }
    }
}