import analytics.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import shared.Constants;
import shared.MongoDBClient;

public class AnalyticsTest {
    @BeforeAll
    static void establishMongoConnection() {
        MongoDBClient.getMongoDBConnection(Constants.MONGO_TEST_ENVIRONMENT);
    }

    @AfterAll
    static void closeMongoConnection() {
        MongoDBClient.closeMongoDBConnection();
    }

    @Test
    public void testInvalidConversationNameThrowsException() {
        String invalidConversationName = "fdsjkfskfjslkfjdslf2342";
        Assertions.assertThrows(InvalidConversationNameException.class, () ->
                Analytics.getConversationData(invalidConversationName));
    }

    @Test
    public void testGetConversationDataWithIndividualConversation() throws InvalidDateFormatException, InvalidConversationNameException {
        // This tests single conversation level data.
        ConversationData conversationData = Analytics.getConversationData(
                UnitTestConstants.VALID_INDIVIDUAL_CONVERSATION);

        String sender1 = "Person1";
        String sender2 = "Matthew Grosman";

        // Check number of messages is correct
        long actualNumMessages = conversationData.numberOfMessages;
        long expectedNumMessages = 4;
        Assertions.assertEquals(actualNumMessages, expectedNumMessages);

        // Check number of messages in conversation
        int actualConvNumMessages = conversationData.messagesPerConversation.get(UnitTestConstants.VALID_INDIVIDUAL_CONVERSATION);
        int expectedConvNumMessages = 4;
        Assertions.assertEquals(actualConvNumMessages, expectedConvNumMessages);

        // Check number of messages per sender
        int actualSender1NumMessages = conversationData.messagesPerSender.get(sender1);
        int actualSender2NumMessages = conversationData.messagesPerSender.get(sender2);
        int expectedSender1NumMessages = 2;
        int expectedSender2NumMessages = 2;
        Assertions.assertEquals(actualSender1NumMessages, expectedSender1NumMessages);
        Assertions.assertEquals(actualSender2NumMessages, expectedSender2NumMessages);

        // Check number of messages per month
        int actualMessagesPerMonth = conversationData.messagesPerMonth.get("9-2018");
        int expectedMessagesPerMonth = 4;
        Assertions.assertEquals(actualMessagesPerMonth, expectedMessagesPerMonth);

        // Check number of messages per weekday
        int actualMessagesPerWeekday = conversationData.messagesPerWeekday.get("Saturday");
        int expectedMessagesPerWeekday = 4;
        Assertions.assertEquals(actualMessagesPerWeekday, expectedMessagesPerWeekday);

        // Check number of messages per hour
        int actualMessagesPerHour1 = conversationData.messagesPerHour.get("11:00");
        int actualMessagesPerHour2 = conversationData.messagesPerHour.get("13:00");
        int actualMessagesPerHour3 = conversationData.messagesPerHour.get("21:00");
        int expectedMessagesPerHour1 = 1;
        int expectedMessagesPerHour2 = 2;
        int expectedMessagesPerHour3 = 1;
        Assertions.assertEquals(actualMessagesPerHour1, expectedMessagesPerHour1);
        Assertions.assertEquals(actualMessagesPerHour2, expectedMessagesPerHour2);
        Assertions.assertEquals(actualMessagesPerHour3, expectedMessagesPerHour3);
    }

    @Test
    public void testGetConversationDataWithGroupConversation() throws InvalidDateFormatException, InvalidConversationNameException {
        // This tests single conversation level data.
        ConversationData conversationData = Analytics.getConversationData(
                UnitTestConstants.VALID_GROUP_CONVERSATION);

        String sender1 = "Steven Juana";
        String sender2 = "Matthew Grosman";
        String sender3 = "Bo Bramer";

        // Check number of messages is correct
        long actualNumMessages = conversationData.numberOfMessages;
        long expectedNumMessages = 10;
        Assertions.assertEquals(actualNumMessages, expectedNumMessages);

        // Check number of messages in conversation
        int actualConvNumMessages = conversationData.messagesPerConversation.get(UnitTestConstants.VALID_GROUP_CONVERSATION);
        int expectedConvNumMessages = 10;
        Assertions.assertEquals(actualConvNumMessages, expectedConvNumMessages);

        // Check number of messages per sender
        int actualSender1NumMessages = conversationData.messagesPerSender.get(sender1);
        int actualSender2NumMessages = conversationData.messagesPerSender.get(sender2);
        int actualSender3NumMessages = conversationData.messagesPerSender.get(sender3);
        int expectedSender1NumMessages = 6;
        int expectedSender2NumMessages = 2;
        int expectedSender3NumMessages = 2;
        Assertions.assertEquals(actualSender1NumMessages, expectedSender1NumMessages);
        Assertions.assertEquals(actualSender2NumMessages, expectedSender2NumMessages);
        Assertions.assertEquals(actualSender3NumMessages, expectedSender3NumMessages);

        // Check number of messages per month
        int actualMessagesPerMonth = conversationData.messagesPerMonth.get("6-2019");
        int expectedMessagesPerMonth = 10;
        Assertions.assertEquals(actualMessagesPerMonth, expectedMessagesPerMonth);

        // Check number of messages per weekday
        int actualMessagesPerWeekday = conversationData.messagesPerWeekday.get("Friday");
        int expectedMessagesPerWeekday = 10;
        Assertions.assertEquals(actualMessagesPerWeekday, expectedMessagesPerWeekday);

        // Check number of messages per hour
        int actualMessagesPerHour1 = conversationData.messagesPerHour.get("20:00");
        int actualMessagesPerHour2 = conversationData.messagesPerHour.get("21:00");
        int expectedMessagesPerHour1 = 7;
        int expectedMessagesPerHour2 = 3;
        Assertions.assertEquals(actualMessagesPerHour1, expectedMessagesPerHour1);
        Assertions.assertEquals(actualMessagesPerHour2, expectedMessagesPerHour2);
    }

    @Test
    public void testGetConversationDataWithNullConversationName() throws InvalidDateFormatException, InvalidConversationNameException {
        // This tests aggregated across all conversations data.
        ConversationData conversationData = Analytics.getConversationData(null);

        String sender1 = "Steven Juana";
        String sender2 = "Matthew Grosman";
        String sender3 = "Bo Bramer";
        String sender4 = "Person1";

        // Check number of messages is correct
        long actualNumMessages = conversationData.numberOfMessages;
        long expectedNumMessages = 14;
        Assertions.assertEquals(actualNumMessages, expectedNumMessages);

        // Check number of messages in conversation
        int actualConvNumMessages1 = conversationData.messagesPerConversation.get(UnitTestConstants.VALID_GROUP_CONVERSATION);
        int actualConvNumMessages2 = conversationData.messagesPerConversation.get(UnitTestConstants.VALID_INDIVIDUAL_CONVERSATION);
        int expectedConvNumMessages1 = 10;
        int expectedConvNumMessages2 = 4;
        Assertions.assertEquals(actualConvNumMessages1, expectedConvNumMessages1);
        Assertions.assertEquals(actualConvNumMessages2, expectedConvNumMessages2);

        // Check number of messages per sender
        int actualSender1NumMessages = conversationData.messagesPerSender.get(sender1);
        int actualSender2NumMessages = conversationData.messagesPerSender.get(sender2);
        int actualSender3NumMessages = conversationData.messagesPerSender.get(sender3);
        int actualSender4NumMessages = conversationData.messagesPerSender.get(sender4);
        int expectedSender1NumMessages = 6;
        int expectedSender2NumMessages = 4;
        int expectedSender3NumMessages = 2;
        int expectedSender4NumMessages = 2;
        Assertions.assertEquals(actualSender1NumMessages, expectedSender1NumMessages);
        Assertions.assertEquals(actualSender2NumMessages, expectedSender2NumMessages);
        Assertions.assertEquals(actualSender3NumMessages, expectedSender3NumMessages);
        Assertions.assertEquals(actualSender4NumMessages, expectedSender4NumMessages);

        // Check number of messages per month
        int actualMessagesPerMonth1 = conversationData.messagesPerMonth.get("6-2019");
        int actualMessagesPerMonth2 = conversationData.messagesPerMonth.get("9-2018");
        int expectedMessagesPerMonth1 = 10;
        int expectedMessagesPerMonth2 = 4;
        Assertions.assertEquals(actualMessagesPerMonth1, expectedMessagesPerMonth1);
        Assertions.assertEquals(actualMessagesPerMonth2, expectedMessagesPerMonth2);

        // Check number of messages per weekday
        int actualMessagesPerWeekday1 = conversationData.messagesPerWeekday.get("Friday");
        int actualMessagesPerWeekday2 = conversationData.messagesPerWeekday.get("Saturday");
        int expectedMessagesPerWeekday1 = 10;
        int expectedMessagesPerWeekday2 = 4;
        Assertions.assertEquals(actualMessagesPerWeekday1, expectedMessagesPerWeekday1);
        Assertions.assertEquals(actualMessagesPerWeekday2, expectedMessagesPerWeekday2);

        // Check number of messages per hour
        int actualMessagesPerHour1 = conversationData.messagesPerHour.get("20:00");
        int actualMessagesPerHour2 = conversationData.messagesPerHour.get("21:00");
        int actualMessagesPerHour3 = conversationData.messagesPerHour.get("11:00");
        int actualMessagesPerHour4 = conversationData.messagesPerHour.get("13:00");
        int expectedMessagesPerHour1 = 7;
        int expectedMessagesPerHour2 = 4;
        int expectedMessagesPerHour3 = 1;
        int expectedMessagesPerHour4 = 2;
        Assertions.assertEquals(actualMessagesPerHour1, expectedMessagesPerHour1);
        Assertions.assertEquals(actualMessagesPerHour2, expectedMessagesPerHour2);
        Assertions.assertEquals(actualMessagesPerHour3, expectedMessagesPerHour3);
        Assertions.assertEquals(actualMessagesPerHour4, expectedMessagesPerHour4);
    }
}
