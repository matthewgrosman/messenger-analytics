import analytics.ConversationDataMongoDB;
import analytics.InvalidDateFormatException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import shared.Constants;
import shared.MongoDBClient;

public class ConversationDataMongoDBTest {
    @BeforeAll
    static void establishMongoConnection() {
        MongoDBClient.getMongoDBConnection(Constants.MONGO_TEST_ENVIRONMENT);
    }

    @AfterAll
    static void closeMongoConnection() {
        MongoDBClient.closeMongoDBConnection();
    }

    @Test
    public void testIsConversationValidWithValidSingleConversation() {
        boolean result = ConversationDataMongoDB.isConversationValid(UnitTestConstants.VALID_INDIVIDUAL_CONVERSATION);
        Assertions.assertTrue(result);
    }

    @Test
    public void testIsConversationValidWithValidGroupConversation() {
        boolean result = ConversationDataMongoDB.isConversationValid(UnitTestConstants.VALID_GROUP_CONVERSATION);
        Assertions.assertTrue(result);
    }

    @Test
    public void testIsConversationValidWithInvalidConversation() {
        String invalidConversationName = "ksjj0948j322j9842hhji-r320rc2+?23344BC";
        boolean result = ConversationDataMongoDB.isConversationValid(invalidConversationName);
        Assertions.assertFalse(result);
    }

    @Test
    public void testGetConversationDataWithIndividualConversation() throws InvalidDateFormatException {
        // This tests single conversation level data.
        ConversationDataMongoDB.getConversationData(UnitTestConstants.VALID_INDIVIDUAL_CONVERSATION);
    }

    @Test
    public void testGetConversationDataWithGroupConversation() throws InvalidDateFormatException {
        // This tests single conversation level data.
        ConversationDataMongoDB.getConversationData(UnitTestConstants.VALID_GROUP_CONVERSATION);
    }

    @Test
    public void testGetConversationDataWithNullConversationName() {
        // This tests aggregated across all conversations data.
    }
}
