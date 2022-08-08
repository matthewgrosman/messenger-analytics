package shared;

import java.util.HashMap;
import java.util.Map;

public final class Constants {
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

    // The field names for message data documents to be written to MongoDB.
    public static final String MONGO_CONVERSATION_FIELD_NAME = "conversation_name";
    public static final String MONGO_GROUP_CHAT_FIELD_NAME = "is_group_chat";
    public static final String MONGO_SENDER_FIELD_NAME = "sender_name";
    public static final String MONGO_DATE_FIELD_NAME = "date_sent";
    public static final String MONGO_CONTENT_FIELD_NAME = "content_name";

    // The conversation types for group chats and regular chats.
    public static final String GROUP_CHAT_TYPE_NAME = "RegularGroup";

    // The database and collection names for MongoDB.
    public static final String MONGO_DATABASE_NAME = "messenger-data";
    public static final String MONGO_COLLECTION_NAME = "messages-test-3";

    // The types of date formats needed for analytics.
    public static final String MONTH_FORMAT = "MONTH";
    public static final String WEEKDAY_FORMAT = "WEEKDAY";
    public static final String HOUR_FORMAT = "HOUR";
    public static final String HOUR_SUFFIX = ":00";

    public static final Map<Integer, String> WEEKDAY_INDEX_TO_DAY = Map.of(
            1, "Monday",
            2, "Tuesday",
            3, "Wednesday",
            4, "Thursday",
            5, "Friday",
            6, "Saturday",
            7, "Sunday");
}
