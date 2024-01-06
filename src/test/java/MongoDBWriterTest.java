import com.mongodb.client.FindIterable;
import org.bson.Document;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import parser.ConversationParser;
import mongodb.MongoDBWriter;
import shared.Constants;
import mongodb.MongoDBClient;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class MongoDBWriterTest {
    @BeforeAll
    static void establishMongoConnection() {
        MongoDBClient.getMongoDBConnection(
                Constants.MONGO_COLLECTION_NAME_TEST + java.time.LocalDateTime.now());
    }

    @AfterAll
    static void closeMongoConnection() {
        MongoDBClient.closeMongoDBConnection();
    }

    @Test
    public void testWritingToMongoDB() throws IOException {
        File validConversationPath = new File("src/test/test-input-files/valid-conversation");
        ArrayList<Document> messages = ConversationParser.parseConversationFolderFiles(validConversationPath);

        MongoDBWriter.writeMessageDataDocuments(messages);

        // This grabs the messages from MongoDB in the form of an iterator
        FindIterable<Document> conversationMessages = MongoDBClient.messagesCollection.find();

        // Iterate through the messages iterator and add them to an ArrayList to make it easier to validate
        // (we can't do iterator.get(index) like we can do with an ArrayList).
        ArrayList<Document> conversationMessagesAsArrayList = new ArrayList<>();
        for (Document message : conversationMessages) {
            conversationMessagesAsArrayList.add(message);
        }

        // Check first message document is correct
        Document message1 = conversationMessagesAsArrayList.get(0);

        String expectedConversationName1 = "Person1";
        String expectedSenderName1 = "Matthew Grosman";
        String expectedDate1 = new Date(1538281056549L).toString();
        String expectedContent1 = "New content";

        Assertions.assertEquals(expectedConversationName1, message1.get(Constants.MONGO_CONVERSATION_FIELD_NAME));
        Assertions.assertFalse((boolean)  message1.get(Constants.MONGO_GROUP_CHAT_FIELD_NAME));
        Assertions.assertEquals(expectedSenderName1, message1.get(Constants.MONGO_SENDER_FIELD_NAME));
        Assertions.assertEquals(expectedDate1, message1.get(Constants.MONGO_DATE_FIELD_NAME).toString());
        Assertions.assertEquals(expectedContent1, message1.get(Constants.MONGO_CONTENT_FIELD_NAME));

        // Check second message document is correct
        Document message2 = conversationMessagesAsArrayList.get(1);

        String expectedConversationName2 = "Person1";
        String expectedSenderName2 = "Matthew Grosman";
        String expectedDate2 = new Date(1538281056531L).toString();
        String expectedContent2 = "Some content string";

        Assertions.assertEquals(expectedConversationName2, message2.get(Constants.MONGO_CONVERSATION_FIELD_NAME));
        Assertions.assertFalse((boolean)  message2.get(Constants.MONGO_GROUP_CHAT_FIELD_NAME));
        Assertions.assertEquals(expectedSenderName2, message2.get(Constants.MONGO_SENDER_FIELD_NAME));
        Assertions.assertEquals(expectedDate2, message2.get(Constants.MONGO_DATE_FIELD_NAME).toString());
        Assertions.assertEquals(expectedContent2, message2.get(Constants.MONGO_CONTENT_FIELD_NAME));

        // Check third message document is correct
        Document message3 = conversationMessagesAsArrayList.get(2);

        String expectedConversationName3 = "Person1";
        String expectedSenderName3 = "Person1";
        String expectedDate3 = new Date(1538252862432L).toString();
        String expectedContent3 = "This is a message";

        Assertions.assertEquals(expectedConversationName3, message3.get(Constants.MONGO_CONVERSATION_FIELD_NAME));
        Assertions.assertFalse((boolean)  message3.get(Constants.MONGO_GROUP_CHAT_FIELD_NAME));
        Assertions.assertEquals(expectedSenderName3, message3.get(Constants.MONGO_SENDER_FIELD_NAME));
        Assertions.assertEquals(expectedDate3, message3.get(Constants.MONGO_DATE_FIELD_NAME).toString());
        Assertions.assertEquals(expectedContent3, message3.get(Constants.MONGO_CONTENT_FIELD_NAME));
    }

    @Test
    public void testWritingToMongoDBWithEmptyDocumentsArray() {
        ArrayList<Document> messages = new ArrayList<>();
        MongoDBWriter.writeMessageDataDocuments(messages);

        // This grabs the messages from MongoDB in the form of an iterator
        long messageCount = MongoDBClient.messagesCollection.countDocuments();

        Assertions.assertEquals(messageCount, 0);
    }
}
