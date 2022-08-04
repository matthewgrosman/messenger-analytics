import org.elasticsearch.xcontent.XContentBuilder;
import org.elasticsearch.xcontent.XContentFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Date;

public class MessageDataDocument {
    private String conversationName;
    private Boolean isGroupChat;
    private String senderName;
    private Date date;
    private String content;

    /**
     * Public constructor for the MessageDataDocument class.
     *
     * @param conversationName  String representing the name of the current Facebook Messenger conversation.
     * @param groupType         Boolean representing if the current conversation is a group chat or not.
     * @param senderName        String representing the person who sent the current message.
     * @param timestamp         String representing the time when the current message was sent.
     * @param content           String that is the literal current message.
     */
    public MessageDataDocument(String conversationName, String groupType, String senderName, long timestamp,
                               String content) {
        this.conversationName = ensureSingleQuoteUTF8(conversationName);
        this.isGroupChat = isGroupTypeGroupChat(groupType);
        this.senderName = senderName;
        this.date = getTimestampAsDate(timestamp);
        this.content = ensureSingleQuoteUTF8(content);
    }

    public String getSenderName() {
        return this.senderName;
    }

    public String getConversationName() {
        return this.conversationName;
    }

    public String getContent() {
        return this.content;
    }

    public Date getDate() {
        return this.date;
    }

    public Boolean getGroupChat() {
        return this.isGroupChat;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setConversationName(String conversationName) {
        this.conversationName = conversationName;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setGroupChat(Boolean groupChat) {
        this.isGroupChat = groupChat;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    /**
     * Creates an XContentBuilder object that contains all of the information for the document
     * we want to send to Elasticsearch. This object can be directly written to Elasticsearch
     * without any further modification.
     *
     * @return              An XContentBuilder object containing all the message data to send to Elasticsearch.
     * @throws IOException  Can throw an IOException when calling "XContentFactory.jsonBuilder()".
     */
//    public XContentBuilder getMessageDataDocument() throws IOException {
//        XContentBuilder builder = XContentFactory.jsonBuilder();
//        builder.startObject();
//
//        builder.field(ConversationParserConstants.ES_CONVERSATION_FIELD_NAME, this.conversationName);
//        builder.field(ConversationParserConstants.ES_GROUP_CHAT_FIELD_NAME, this.isGroupChat);
//        builder.field(ConversationParserConstants.ES_SENDER_FIELD_NAME, this.senderName);
//        builder.timeField(ConversationParserConstants.ES_DATE_FIELD_NAME, this.date);
//
//        // A message may have no content- in this case we can put it as a null value.
//        if (this.content != null) {
//            builder.field(ConversationParserConstants.ES_CONTENT_FIELD_NAME, this.content);
//        }
//        else {
//            builder.nullField(ConversationParserConstants.ES_CONTENT_FIELD_NAME);
//        }
//
//        builder.endObject();
//        return builder;
//    }

    /**
     * Given a timestamp, return the date as a standard Java Date object.
     *
     * @param timestamp A long representing the timestamp of a message.
     * @return          A Date that represents the date a message was sent.
     */
    private Date getTimestampAsDate(long timestamp) {
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
    private Boolean isGroupTypeGroupChat(String groupType) {
        return groupType.equals(ConversationParserConstants.GROUP_CHAT_TYPE_NAME);
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
     * @param currentString A String that needs to be checked for UTF-8 compliance
     * @return
     */
    private String ensureSingleQuoteUTF8(String currentString) {
        if (currentString != null && currentString.contains("â\u0080\u0099")) {
            byte[] stringBytes = currentString.getBytes(StandardCharsets.ISO_8859_1);
            return new String(stringBytes, StandardCharsets.UTF_8);
        }
        return currentString;
    }
}
