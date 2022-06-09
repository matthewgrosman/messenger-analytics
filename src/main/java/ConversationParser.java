import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FilenameUtils;

public class ConversationParser {
    /**
     * Takes an individual JSON file that contains messages and parses this file for data.
     *
     * @param messageJson   A JSON file containing messages from a Facebook Messenger conversation.
     */
    public static void parseMessageJson(File messageJson) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(messageJson);

        if (ConversationParserUtil.isSafeToAccess(rootNode, ConversationParserConstants.MESSAGES_JSON_FIELD_NAME)) {
            JsonNode messages = rootNode.get(ConversationParserConstants.MESSAGES_JSON_FIELD_NAME);
            for (JsonNode message : messages) {
                System.out.println(message.get("content").asText());
            }
        }
    }

    /**
     * Takes in a conversation and calls necessary functions to parse conversation for data to
     * send to Elasticsearch.
     *
     * @param conversation  A File that contains all the conversation data for a specific chat
     *                      on Facebook Messenger.
     */
    public static void parseConversation(File conversation) throws IOException {
        // Each conversation folder can hold several things- namely sub-folders for photos,
        // gifs and other information. We just want the actual conversations- these are
        // stored in JSON files (they are the only file stored as a JSON, so we can use
        // this filename extension to identify them). Once we have identified the message
        // JSON, we send it to a function that parses that file and send data to Elasticsearch.
        for (File file : conversation.listFiles()) {
            if (FilenameUtils.getExtension(file.getName()).equals(ConversationParserConstants.MESSAGES_EXTENSION)) {
                parseMessageJson(file);
            }
        }
    }

    /**
     * This function gets the Facebook Messenger conversations folder, iterates through each
     * conversation, and calls necessary functions to gather data.
     */
    public static File[] getConversations() {
        File messagesDirectory = new File(ConversationParserConstants.MESSAGES_FOLDER);

        // This line removes the '.DS_Store' file from being considered a conversation while
        // returning the valid conversations. This is, to my knowledge, a Mac OS feature where
        // the OS will create a file (.DS_Store) in a directory to store certain metadata about
        // the directory.
        return messagesDirectory.listFiles((dir, name) -> !name.equals(".DS_Store"));
    }

    public static void main(String[] args) throws IOException {
        File[] conversations = getConversations();

        for (File conversation : conversations) {
            parseConversation(conversation);
        }
    }

}
