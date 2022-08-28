import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.bson.Document;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import parser.ConversationParser;
import shared.Constants;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class ConversationParserTest {
    @Test
    public void testGetConversationFoldersWithValidFilePath() {
        String filePath = "src/test/test-input-files";
        File[] files = ConversationParser.getConversationFolders(filePath);

        Assertions.assertEquals(files.length, 5);
    }

    @Test
    public void testGetConversationFoldersWithInvalidFilePath() {
        String filePath = "someFilePathThatDoesntExist";
        File[] files = ConversationParser.getConversationFolders(filePath);

        Assertions.assertNull(files);
    }

    @Test
    public void testGetMessageDataWithMessageContainingContent() throws IOException {
        File testInputJsonFile = new File("src/test/test-input-files/ValidTestConversationFile.json");

        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(testInputJsonFile);
        JsonNode messages = rootNode.get(Constants.JSON_MESSAGES_FIELD_NAME);
        JsonNode conversationName = rootNode.get(Constants.JSON_CONVERSATION_FIELD_NAME);
        JsonNode groupType = rootNode.get(Constants.JSON_GROUP_CHAT_FIELD_NAME);
        JsonNode singleMessage = messages.get(0);

        Document document = ConversationParser.getMessageData(singleMessage, conversationName, groupType);

        String expectedConversationName = "Person1";
        String expectedSenderName = "Matthew Grosman";
        String expectedDate = new Date(1538281056531L).toString();
        String expectedContent = "Ok no worries, thank you though";

        Assertions.assertEquals(expectedConversationName, document.get(Constants.MONGO_CONVERSATION_FIELD_NAME));
        Assertions.assertFalse((boolean)  document.get(Constants.MONGO_GROUP_CHAT_FIELD_NAME));
        Assertions.assertEquals(expectedSenderName, document.get(Constants.MONGO_SENDER_FIELD_NAME));
        Assertions.assertEquals(expectedDate, document.get(Constants.MONGO_DATE_FIELD_NAME).toString());
        Assertions.assertEquals(expectedContent, document.get(Constants.MONGO_CONTENT_FIELD_NAME));
    }

    @Test
    public void testGetMessageDataWithMessageContainingNoContent() throws IOException {
        File testInputJsonFile = new File("src/test/test-input-files/ValidTestConversationNoContentMessage.json");

        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(testInputJsonFile);
        JsonNode messages = rootNode.get(Constants.JSON_MESSAGES_FIELD_NAME);
        JsonNode conversationName = rootNode.get(Constants.JSON_CONVERSATION_FIELD_NAME);
        JsonNode groupType = rootNode.get(Constants.JSON_GROUP_CHAT_FIELD_NAME);
        JsonNode messageWithNoContent = messages.get(0);

        Document document = ConversationParser.getMessageData(messageWithNoContent, conversationName, groupType);

        String expectedConversationName = "Person1";
        String expectedSenderName = "Matthew Grosman";
        String expectedDate = new Date(1512685138863L).toString();

        Assertions.assertEquals(expectedConversationName, document.get(Constants.MONGO_CONVERSATION_FIELD_NAME));
        Assertions.assertFalse((boolean)  document.get(Constants.MONGO_GROUP_CHAT_FIELD_NAME));
        Assertions.assertEquals(expectedSenderName, document.get(Constants.MONGO_SENDER_FIELD_NAME));
        Assertions.assertEquals(expectedDate, document.get(Constants.MONGO_DATE_FIELD_NAME).toString());

        // There should be no content field, so trying to access it should give null (the
        // BSON document .get() doesn't throw a NullPointerException when accessing a key
        // that doesn't exist- it simply returns null).
        Assertions.assertNull(document.get(Constants.MONGO_CONTENT_FIELD_NAME));
    }

    @Test
    public void testParseMessageJsonFileWithValidFile() throws IOException {
        File testInputJsonFile = new File("src/test/test-input-files/ValidTestConversationShort.json");
        ArrayList<Document> documents = ConversationParser.parseMessageJsonFile(testInputJsonFile);

        int expectedArraySize = 2;
        Assertions.assertEquals(documents.size(), expectedArraySize);

        // Check first message document is correct
        Document message1 = documents.get(0);

        String expectedConversationName1 = "Person1";
        String expectedSenderName1 = "Matthew Grosman";
        String expectedDate1 = new Date(1538281056531L).toString();
        String expectedContent1 = "Some content string";

        Assertions.assertEquals(expectedConversationName1, message1.get(Constants.MONGO_CONVERSATION_FIELD_NAME));
        Assertions.assertFalse((boolean)  message1.get(Constants.MONGO_GROUP_CHAT_FIELD_NAME));
        Assertions.assertEquals(expectedSenderName1, message1.get(Constants.MONGO_SENDER_FIELD_NAME));
        Assertions.assertEquals(expectedDate1, message1.get(Constants.MONGO_DATE_FIELD_NAME).toString());
        Assertions.assertEquals(expectedContent1, message1.get(Constants.MONGO_CONTENT_FIELD_NAME));

        // Check second message document is correct
        Document message2 = documents.get(1);

        String expectedConversationName2 = "Person1";
        String expectedSenderName2 = "Person1";
        String expectedDate2 = new Date(1538252862432L).toString();
        String expectedContent2 = "This is a message";

        Assertions.assertEquals(expectedConversationName2, message2.get(Constants.MONGO_CONVERSATION_FIELD_NAME));
        Assertions.assertFalse((boolean)  message2.get(Constants.MONGO_GROUP_CHAT_FIELD_NAME));
        Assertions.assertEquals(expectedSenderName2, message2.get(Constants.MONGO_SENDER_FIELD_NAME));
        Assertions.assertEquals(expectedDate2, message2.get(Constants.MONGO_DATE_FIELD_NAME).toString());
        Assertions.assertEquals(expectedContent2, message2.get(Constants.MONGO_CONTENT_FIELD_NAME));
    }

    @Test
    public void testParseMessageJsonFileWithInvalidFile() throws IOException {
        File testInputJsonFile = new File("src/test/test-input-files/EmptyTestConversationFile.json");
        ArrayList<Document> documents = ConversationParser.parseMessageJsonFile(testInputJsonFile);

        Assertions.assertEquals(documents.size(), 0);
    }

    @Test
    public void testParseConversationFolderFilesWithValidDirectory() throws IOException {
        File validConversationPath = new File("src/test/test-input-files/valid-conversation");
        ArrayList<Document> messages = ConversationParser.parseConversationFolderFiles(validConversationPath);

        // Check first message document is correct
        Document message1 = messages.get(0);

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
        Document message2 = messages.get(1);

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
        Document message3 = messages.get(2);

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
    public void testParseConversationFolderFilesWithInvalidDirectory() throws IOException {
        File validConversationPath = new File("src/test/93048242m2mc423mc20c/SOMEINVALIDPATHTHATDOESNTEXIST");
        Assertions.assertThrows(NullPointerException.class, () ->
                ConversationParser.parseConversationFolderFiles(validConversationPath));
    }
}
