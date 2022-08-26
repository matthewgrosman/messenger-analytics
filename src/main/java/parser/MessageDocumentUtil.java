package parser;

import org.bson.Document;
import shared.Constants;

import java.nio.charset.StandardCharsets;
import java.util.Date;

public class MessageDocumentUtil {
    /**
     * Creates a BSON document that contains all of the information for the document we want to send to MongoDB.
     *
     * @param conversationName  String representing the name of the current Facebook Messenger conversation.
     * @param groupType         Boolean representing if the current conversation is a group chat or not.
     * @param senderName        String representing the person who sent the current message.
     * @param timestamp         String representing the time when the current message was sent.
     * @param content           String that is the literal current message.
     *
     * @return  A BSON document containing all the message data to send to MongoDB.
     */
    public static Document getBSONDocument(String conversationName, String groupType, String senderName, long timestamp,
                                    String content) {
        Document document = new Document();
        document.append(Constants.MONGO_CONVERSATION_FIELD_NAME, ensureSingleQuoteUTF8(conversationName));
        document.append(Constants.MONGO_GROUP_CHAT_FIELD_NAME, isGroupTypeGroupChat(groupType));
        document.append(Constants.MONGO_SENDER_FIELD_NAME, senderName);
        document.append(Constants.MONGO_DATE_FIELD_NAME, getTimestampAsDate(timestamp));

        // A message may or may not have content. If it does, we add an appropriate field,
        // but if the message is empty, we do not add a field for it (MongoDB treats a non-existent
        // field as null for our purposes).
        if (content != null) {
            document.append(Constants.MONGO_CONTENT_FIELD_NAME, ensureSingleQuoteUTF8(content));
        }

        return document;
    }

    /**
     * Given a timestamp, return the date as a standard Java Date object.
     *
     * @param timestamp A long representing the timestamp of a message.
     * @return          A Date that represents the date a message was sent.
     */
    private static Date getTimestampAsDate(long timestamp) {
        return new Date(timestamp);
    }

    /**
     * Given the group type of a message JSON document, determine if this is a
     * group chat or not.
     *
     * @param groupType A String representing the group type of a conversation.
     *                  The string "RegularGroup" denotes a group chat, while
     *                  the string "Regular" denotes a regular chat between two people.
     * @return          A Boolean denoting if the conversation is a group chat.
     *                  A result of "true" means the conversation is a group chat,
     *                  while "false" means the conversation is not a group chat.
     */
    private static Boolean isGroupTypeGroupChat(String groupType) {
        return groupType.equals(Constants.GROUP_CHAT_TYPE_NAME);
    }

    /**
     * Ensures that the character "'" is encoded correctly. I ran into an issue where Jackson
     * was not parsing "'" correctly and would produce "â" instead of "'". This function
     * checks if the string contains "â", and if it does, encodes the string into proper
     * UTF-8. This also handles correctly encoding emojis- as previously they would show up
     * as similar odd characters like "'" did.
     *
     * NOTE: I checked online and couldn't seem to find a way to change this in a Jackson
     * setting- so this is my best solution for the moment. Ideally I would like to remove
     * this function and find some kind of parameter I can set when using Jackson to decode
     * the JSON that handles this. But, oh well.
     *
     * @param currentString A String that needs to be checked for UTF-8 compliance.
     * @return              A valid UTF-8 string.
     */
    private static String ensureSingleQuoteUTF8(String currentString) {
        if (currentString != null && currentString.contains("â\u0080\u0099")) {
            byte[] stringBytes = currentString.getBytes(StandardCharsets.ISO_8859_1);
            return new String(stringBytes, StandardCharsets.UTF_8);
        }
        return currentString;
    }
}
