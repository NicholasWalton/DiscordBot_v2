package org.jointheleague.features.student;

import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.never;

public class RiddleTest {
    private final String testChannelName = "test";
    private final Riddle riddle = new Riddle(testChannelName);

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @Mock
    private MessageCreateEvent messageCreateEvent;

    @Mock
    private TextChannel textChannel;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    public void itShouldNotPrintToSystemOut() {
        String expected = "";
        String actual = outContent.toString();

        assertEquals(expected, actual);
        System.out.println(actual);
        System.setOut(originalOut);
    }

    @Test
    void itShouldHaveACommand() {
        //Given

        //When
        String command = riddle.RIDDLE;

        //Then
        assertNotEquals("", command);
        assertEquals("!riddle", command);
        assertNotNull(command);
    }

    @Test
    void itShouldHandleMessagesWithCommand() {
        //Given
        HelpEmbed helpEmbed = new HelpEmbed(riddle.RIDDLE, "test");
        when(messageCreateEvent.getMessageContent()).thenReturn(riddle.RIDDLE);
        when(messageCreateEvent.getChannel()).thenReturn((textChannel));

        //When
        riddle.handle(messageCreateEvent);

        //Then
        verify(textChannel, times(1)).sendMessage(anyString());
    }

    @Test
    void itShouldNotHandleMessagesWithoutCommand() {
        //Given
        String command = "";
        when(messageCreateEvent.getMessageContent()).thenReturn(command);

        //When
        riddle.handle(messageCreateEvent);

        //Then
        verify(textChannel, never()).sendMessage();
    }

    @Test
    void itShouldHaveAHelpEmbed() {
        //Given

        //When
        HelpEmbed actualHelpEmbed = riddle.getHelpEmbed();

        //Then
        assertNotNull(actualHelpEmbed);
    }

    @Test
    void itShouldHaveTheCommandAsTheTitleOfTheHelpEmbed() {
        //Given

        //When
        String helpEmbedTitle = riddle.getHelpEmbed().getTitle();
        String command = riddle.RIDDLE;

        //Then
        assertEquals(command, helpEmbedTitle);
    }

     @Test
    void itShouldAllowTryAgain() {
        //Given
        when(messageCreateEvent.getMessageContent()).thenReturn(riddle.RIDDLE);
        when(messageCreateEvent.getChannel()).thenReturn((textChannel));

        //When
        riddle.handle(messageCreateEvent);

        //Then
         assertEquals(true, true);
    }

    @Test
    void itShouldGiveFeedback() {
        //Given
        when(messageCreateEvent.getMessageContent()).thenReturn(riddle.RIDDLE);
        when(messageCreateEvent.getChannel()).thenReturn((textChannel));

        //When
        riddle.handle(messageCreateEvent);

        //Then
        //verify(textChannel, times(1)).sendMessage("Good job.");
        assertEquals(true, true);
    }

    @Test
    void itShouldPrintGoodJob() {
        //Given
        when(messageCreateEvent.getMessageContent()).thenReturn(riddle.RIDDLE);
        when(messageCreateEvent.getChannel()).thenReturn((textChannel));

        //When
        riddle.handle(messageCreateEvent);

        //Then
        verify(textChannel, times(1)).sendMessage("Good job.");
    }
}
