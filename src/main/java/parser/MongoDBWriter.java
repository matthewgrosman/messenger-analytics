package parser;

import org.bson.Document;
import shared.MongoDBClient;

import java.util.ArrayList;

public class MongoDBWriter {
    /**
     * Writes messageDataDocuments to MongoDB.
     *
     * @param messageDataDocuments  An ArrayList of MessageDataDocuments that need to be written to MongoDB.
     */
    public static void writeMessageDataDocuments(ArrayList<Document> messageDataDocuments) {
        if (messageDataDocuments.size() > 0) {
            MongoDBClient.messagesCollection.insertMany(messageDataDocuments);
        }
    }
}
