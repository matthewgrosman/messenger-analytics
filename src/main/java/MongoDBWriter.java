import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;

import org.bson.Document;

import java.util.ArrayList;

public class MongoDBWriter {
    private static MongoClient mongoClient;
    private static MongoCollection<Document> messagesCollection;

    /**
     * Gets a connection to the local MongoDB instance. We need to access the database "messenger-data",
     * which will hold all of the messages from the conversations we parse. Going deeper, we access the
     * collection "messages" within the database. We write directly to this collection.
     */
    public static void getMongoDBConnection() {
        mongoClient = MongoClients.create();
        MongoDatabase messengerDataDatabase = mongoClient.getDatabase(Constants.MONGO_DATABASE_NAME);
        messagesCollection = messengerDataDatabase.getCollection(Constants.MONGO_COLLECTION_NAME);
    }

    /**
     * Writes messageDataDocuments to MongoDB.
     *
     * @param messageDataDocuments  An ArrayList of MessageDataDocuments that need to be written to MongoDB.
     */
    public static void writeMessageDataDocuments(ArrayList<MessageDataDocument> messageDataDocuments) {
        for (MessageDataDocument document : messageDataDocuments) {
            messagesCollection.insertOne(document.getBSONDocument());
        }
    }

    /**
     * Closes the connection to the local MongoDB instance.
     */
    public static void closeMongoDBConnection() {
        mongoClient.close();
    }
}
