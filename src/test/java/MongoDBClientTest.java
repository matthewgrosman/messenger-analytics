import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import shared.MongoDBClient;

public class MongoDBClientTest {
    @Test
    public void testCloseMongoDBConnectionBeforeOpening() {
        // Test to see if this throws an exception
        MongoDBClient.closeMongoDBConnection();
    }
}
