package shared;

import com.mongodb.client.*;

import org.bson.Document;

public class MongoDBClient {
    private static MongoClient mongoClient;
    public static MongoCollection<Document> messagesCollection;

    /**
     * Gets a connection to the local MongoDB instance. We need to access the database "messenger-data",
     * which will hold all of the messages from the conversations we parse. Going deeper, we access the
     * collection "messages" within the database. We write directly to this collection.
     */
    public static void getMongoDBConnection(String environment) {
        mongoClient = MongoClients.create();
        MongoDatabase messengerDataDatabase = mongoClient.getDatabase(Constants.MONGO_DATABASE_NAME);
        messagesCollection = messengerDataDatabase.getCollection(environment);

    }

    /**
     * Closes the connection to the local MongoDB instance.
     */
    public static void closeMongoDBConnection() {
        mongoClient.close();
    }
}
