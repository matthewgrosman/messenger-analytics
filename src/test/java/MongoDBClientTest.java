import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import parser.ConversationParser;
import shared.Constants;
import shared.MongoDBClient;

import java.io.IOException;

public class MongoDBClientTest {
    @Test
    public void testCloseMongoDBConnectionBeforeOpening() {
        // Test to see if this throws an exception
        MongoDBClient.closeMongoDBConnection();
    }

    @Test
    public void testIsMongoDBCollectionEmptyReturnsTrueBeforePopulating() {
        MongoDBClient.getMongoDBConnection(Constants.MONGO_COLLECTION_NAME_TEST + java.time.LocalDateTime.now());
        Assertions.assertTrue(MongoDBClient.isMongoDBCollectionEmpty());
        MongoDBClient.closeMongoDBConnection();
    }

    @Test
    public void testIsMongoDBCollectionEmptyReturnsFalseAfterPopulating() throws IOException {
        MongoDBClient.getMongoDBConnection(Constants.MONGO_COLLECTION_NAME_TEST + java.time.LocalDateTime.now());
        ConversationParser.populateMongoDBCollection();
        Assertions.assertFalse(MongoDBClient.isMongoDBCollectionEmpty());
        MongoDBClient.closeMongoDBConnection();
    }
}
