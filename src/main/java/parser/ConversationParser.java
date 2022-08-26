package parser;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FilenameUtils;
import org.bson.Document;
import shared.Constants;
import shared.MongoDBClient;

public class ConversationParser {
    /**
     * Gets relevant information from a message and returns it in the form of a parser.MessageDocumentUtil
     * object. This includes the conversation name, group type, and message content.
     *
     * @param message           A JsonNode containing the content of a message. This either contains a string
     *                          or is null.
     * @param conversationName  A JsonNode containing the name of the conversation.
     * @param groupType         A JsonNode containing the group type of the conversation.
     * @return                  A Document that contains all the relevant data from this message.
     */
    public static Document getMessageData(JsonNode message, JsonNode conversationName, JsonNode groupType) {
        // We need to get the sender name, timestamp, and content from each message.
        String senderName = message.get(Constants.JSON_SENDER_FIELD_NAME).asText();
        long timestamp = message.get(Constants.JSON_TIMESTAMP_FIELD).asLong();
        JsonNode content = ConversationParserUtil.isSafeAndGet(message,
                Constants.JSON_CONTENT_FIELD_NAME);

        // Sender name and timestamp will always have a value, but content may be null- so
        // we take an extra step to ensure it is handled without a null pointer exception.
        String contentString = (content == null) ? null : content.asText();

        return MessageDocumentUtil.getBSONDocument(
                conversationName.asText(),
                groupType.asText(),
                senderName,
                timestamp,
                contentString
        );
    }

    /**
     * Takes an individual JSON file that contains messages and parses this file for data.
     *
     * @param messageJson   A JSON file containing messages from a Facebook Messenger conversation.
     * @return              An ArrayList<parser.MessageDocumentUtil> that contains parser.MessageDocumentUtil objects
     *                      that contain the data from each message from the JSON file
     */
    public static ArrayList<Document> getFileMessagesData(File messageJson) throws IOException {
        ArrayList<Document> messageDataDocuments = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(messageJson);

        // Grab data from necessary fields. We need the conversation name, group type, and the array of messages.
        JsonNode conversationName = ConversationParserUtil.isSafeAndGet(rootNode, Constants.JSON_CONVERSATION_FIELD_NAME);
        JsonNode groupType = ConversationParserUtil.isSafeAndGet(rootNode, Constants.JSON_GROUP_CHAT_FIELD_NAME);
        JsonNode messages = ConversationParserUtil.isSafeAndGet(rootNode, Constants.JSON_MESSAGES_FIELD_NAME);

        if (conversationName != null && groupType != null && messages != null) {
            for (JsonNode message : messages) {
                messageDataDocuments.add(getMessageData(message, conversationName, groupType));
            }
        }

        return messageDataDocuments;
    }

    /**
     * Takes in a conversation and calls necessary functions to parse conversation for data to
     * send to MongoDB.
     *
     * @param conversation  A File that contains all the conversation data for a specific chat
     *                      on Facebook Messenger.
     */
    public static void parseConversation(File conversation) throws IOException {
        // Each conversation folder can hold several things- namely sub-folders for photos,
        // gifs and other information. We just want the actual conversations- these are
        // stored in JSON files (they are the only file stored as a JSON, so we can use
        // this filename extension to identify them). Once we have identified the message
        // JSON, we send it to a function that parses that file and send data to MongoDB.
        for (File file : conversation.listFiles()) {
            if (FilenameUtils.getExtension(file.getName()).equals(Constants.MESSAGES_EXTENSION)) {
                ArrayList<Document> messageDataDocuments = getFileMessagesData(file);
                MongoDBWriter.writeMessageDataDocuments(messageDataDocuments);
            }
        }
    }

    /**
     * This function gets the Facebook Messenger conversations folder, iterates through each
     * conversation, and calls necessary functions to gather data.
     */
    public static File[] getConversations() {
        File messagesDirectory = new File(Constants.MESSAGES_FOLDER);

        // This line removes the '.DS_Store' file from being considered a conversation while
        // returning the valid conversations. This is, to my knowledge, a Mac OS feature where
        // the OS will create a file (.DS_Store) in a directory to store certain metadata about
        // the directory.
        return messagesDirectory.listFiles((dir, name) -> !name.equals(".DS_Store"));
    }

    public static void main(String[] args) throws IOException {
        File[] conversations = getConversations();

        MongoDBClient.getMongoDBConnection(Constants.MONGO_PROD_ENVIRONMENT);

        for (File conversation : conversations) {
            parseConversation(conversation);
        }

        MongoDBClient.closeMongoDBConnection();
    }
}
