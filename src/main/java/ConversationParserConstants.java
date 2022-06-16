public class ConversationParserConstants {
    // The folder holding all Facebook Messenger conversations.
    public static final String MESSAGES_FOLDER = "/Users/matthew/Documents/Message-Data-2022/messages-json/inbox/";

    // The filename extension of a conversation file.
    public static final String MESSAGES_EXTENSION = "json";

    // The field name for the field that messages are stored in in the JSON file.
    public static final String JSON_MESSAGES_FIELD_NAME = "messages";

    // The field names for data in the JSON file.
    public static final String JSON_CONVERSATION_FIELD_NAME = "title";
    public static final String JSON_GROUP_CHAT_FIELD_NAME = "thread_type";
    public static final String JSON_SENDER_FIELD_NAME = "sender_name";
    public static final String JSON_TIMESTAMP_FIELD = "timestamp_ms";
    public static final String JSON_CONTENT_FIELD_NAME = "content";

    // The field names for message data documents to be written to Elasticsearch.
    public static final String ES_CONVERSATION_FIELD_NAME = "conversation_name";
    public static final String ES_GROUP_CHAT_FIELD_NAME = "is_group_chat";
    public static final String ES_SENDER_FIELD_NAME = "sender_name";
    public static final String ES_DATE_FIELD_NAME = "date_sent";
    public static final String ES_CONTENT_FIELD_NAME = "content_name";

    // The conversation types for group chats and regular chats.
    public static final String GROUP_CHAT_TYPE_NAME = "RegularGroup";

    // The host name and port number to connect to the Elasticsearch cluster.
    public static final String ES_HOST_NAME = "localhost";
    public static final int ES_PORT = 9200;
}
