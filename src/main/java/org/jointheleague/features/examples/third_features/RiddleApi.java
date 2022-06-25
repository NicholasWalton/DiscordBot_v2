package org.jointheleague.features.examples.third_features;



import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.features.abstract_classes.Feature;
import org.jointheleague.features.examples.third_features.plain_old_java_objects.cat_facts_api.RiddleWrapper;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;
import org.springframework.web.reactive.function.client.WebClient;


import net.aksingh.owmjapis.api.APIException;
import reactor.core.publisher.Mono;

public class RiddleApi extends Feature{

	 public final String COMMAND = "!age";

	    private WebClient webClient;
	    private static final String baseUrl = "https://api.agify.io";
 
	public RiddleApi(String channelName) {
		super(channelName);
		helpEmbed = new HelpEmbed(COMMAND, "Use the syntax '!riddle NAME' to start");

        //build the WebClient
        this.webClient = WebClient
                .builder()
                .baseUrl(baseUrl)
                .build();
    
	}
    @Override
    public void handle(MessageCreateEvent event) {
        String messageContent = event.getMessageContent();
        if (messageContent.startsWith(COMMAND)) {
        	String name=messageContent.substring(8);
        	Result riddle = getRiddle(name);
        	int age=riddle.getAge()-15;
        	event.getChannel().sendMessage(name+"'s predicted age is: "+age);
        }
        

    	
    }

	
	
	 public Result getRiddle(String reqName) {
		 

	        //Make the request, accepting the response as a plain old java object you created
	        Mono<Result> riddleMono = webClient.get()
	        		.uri(uriBuilder-> 
	        			uriBuilder.queryParam("name", reqName).build()
	        		)
	                .retrieve()
	                .bodyToMono(Result.class);

	        //collect the response into a plain old java object
	        Result r = riddleMono.block();

	        //get the cat fact from the response


	        //send the message
	        return r;

	    }
	 
	 public void setWebClient(WebClient webClient) {
	        this.webClient = webClient;
	    }
	

	

}
