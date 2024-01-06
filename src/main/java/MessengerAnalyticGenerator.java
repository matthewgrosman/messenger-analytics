import analytics.Analytics;
import exceptions.InvalidConversationNameException;
import exceptions.InvalidDateFormatException;
import parser.ConversationParser;
import shared.Constants;
import mongodb.MongoDBClient;

import java.io.IOException;

public class MessengerAnalyticGenerator {
    public static void main(String[] args) throws IOException, InvalidConversationNameException, InvalidDateFormatException {
        MongoDBClient.getMongoDBConnection(Constants.MONGO_COLLECTION_NAME_PROD);

        if (MongoDBClient.isMongoDBCollectionEmpty()) {
            ConversationParser.populateMongoDBCollection();
        }

        Analytics.generateAnalyticExcelFile();

        MongoDBClient.closeMongoDBConnection();
    }
}
