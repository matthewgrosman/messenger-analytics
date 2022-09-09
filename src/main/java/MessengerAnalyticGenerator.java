import analytics.Analytics;
import analytics.InvalidConversationNameException;
import analytics.InvalidDateFormatException;
import parser.ConversationParser;
import shared.Constants;
import shared.MongoDBClient;

import java.io.IOException;

public class MessengerAnalyticGenerator {
    public static void main(String[] args) throws IOException, InvalidConversationNameException, InvalidDateFormatException {
        MongoDBClient.getMongoDBConnection(Constants.MONGO_COLLECTION_NAME_PROD);

        if (MongoDBClient.isMongoDBCollectionEmpty()) {
            ConversationParser.populateMongoDBCollection();
        }

        Analytics.generateAnalyticExcelFiles();

        MongoDBClient.closeMongoDBConnection();
    }
}
