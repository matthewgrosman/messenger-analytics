import org.elasticsearch.xcontent.XContentBuilder;
import org.elasticsearch.xcontent.XContentFactory;

import java.io.IOException;
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
        this.conversationName = conversationName;
        this.isGroupChat = isGroupTypeGroupChat(groupType);
        this.senderName = senderName;
        this.date = getTimestampAsDate(timestamp);
        this.content = content;
    }

    /**
     * Creates an XContentBuilder object that contains all of the information for the document
     * we want to send to Elasticsearch. This object can be directly written to Elasticsearch
     * without any further modification.
     *
     * @return              An XContentBuilder object containing all the message data to send to Elasticsearch.
     * @throws IOException  Can throw an IOException when calling "XContentFactory.jsonBuilder()".
     */
    public XContentBuilder getMessageDataDocument() throws IOException {
        XContentBuilder builder = XContentFactory.jsonBuilder();
        builder.startObject();

        builder.field(ConversationParserConstants.ES_CONVERSATION_FIELD_NAME, this.conversationName);
        builder.field(ConversationParserConstants.ES_GROUP_CHAT_FIELD_NAME, this.isGroupChat);
        builder.field(ConversationParserConstants.ES_SENDER_FIELD_NAME, this.senderName);
        builder.timeField(ConversationParserConstants.ES_DATE_FIELD_NAME, this.date);

        // A message may have no content- in this case we can put it as a null value.
        if (this.content != null) {
            builder.field(ConversationParserConstants.ES_CONTENT_FIELD_NAME, this.content);
        }
        else {
            builder.nullField(ConversationParserConstants.ES_CONTENT_FIELD_NAME);
        }

        builder.endObject();
        return builder;
    }

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
}
