import analytics.ConversationData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ConversationDataTest {
    @Test
    public void testHashMapsCreatedCorrectly() {
        ConversationData conversationData = new ConversationData();
        Assertions.assertNotNull(conversationData.messagesPerConversation);
        Assertions.assertNotNull(conversationData.messagesPerSender);
        Assertions.assertNotNull(conversationData.messagesPerMonth);
        Assertions.assertNotNull(conversationData.messagesPerWeekday);
        Assertions.assertNotNull(conversationData.messagesPerHour);
    }

    @Test
    public void testNumberOfMessagesVariableCreatedCorrectly() {
        ConversationData conversationData = new ConversationData();
        Assertions.assertNotNull(conversationData.numberOfMessages);
    }
}
