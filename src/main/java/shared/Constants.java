package shared;

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
    public static final String MONGO_COLLECTION_NAME_PROD = "messages-PROD";
    public static final String MONGO_COLLECTION_NAME_TEST = "messages-TEST";

    // The types of date formats needed for analytics.
    public static final String MONTH_FORMAT = "MONTH";
    public static final String WEEKDAY_FORMAT = "WEEKDAY";
    public static final String HOUR_FORMAT = "HOUR";
    public static final String HOUR_SUFFIX = ":00";

    // Map that maps day of the week to its numerical representation (1-7).
    // The Calendar class starts the week with Sunday which gives it an index of 1.
    public static final Map<Integer, String> WEEKDAY_INDEX_TO_DAY = Map.of(
            2, "Monday",
            3, "Tuesday",
            4, "Wednesday",
            5, "Thursday",
            6, "Friday",
            7, "Saturday",
            1, "Sunday");

    // Exception message for InvalidConversationNameException.
    public static final String INVALID_CONV_NAME_EXCEPTION_MESSAGE = "Conversation does not exist: ";

    // Exception message for InvalidDateFormatException.
    public static final String INVALID_DATE_EXCEPTION_MESSAGE = "Invalid date format. Format must be of 'MONTH', 'WEEKDAY', or 'HOUR'";

    // Excel Sheet prefix.
    public static final String EXCEL_SHEET_PREFIX = "messagesPer";

    // Excel Sheet names/column headers.
    public static final String EXCEL_MESSAGES_PER_CONVERSATION = "Conversation";
    public static final String EXCEL_MESSAGES_PER_SENDER = "Sender";
    public static final String EXCEL_MESSAGES_PER_MONTH = "Month";
    public static final String EXCEL_MESSAGES_PER_WEEKDAY = "Weekday";
    public static final String EXCEL_MESSAGES_PER_HOUR = "Hour";
    public static final String EXCEL_TOTAL_MESSAGES = "TotalNumberOfMessages";
    public static final String EXCEL_NUM_MESSAGES_COLUMN_NAME = "NumberOfMessages";

    //
    public static final String EXCEL_ALL_CONVERSATIONS_FILE_PREFIX = "All-Conversations";

    // Excel column numbers.
    public static final int EXCEL_NAME_COLUMN_NUMBER = 0;
    public static final int EXCEL_DATA_COLUMN_NUMBER = 1;

    // The folder that Excel files are written to.
    public static final String EXCEL_OUTPUT_FOLDER = "/Users/matthew/Documents/programming/message-data-excel-output/";

    // Excel file extension
    public static final String EXCEL_FILE_EXTENSION = ".xlsx";

    // User input prompt
    public static final String USER_INPUT_PROMPT = "Enter person or conversation name, or leave blank for analytics aggregated across all conversations: ";

    // Loading status messages
    public static final String CONVERSATION_PARSER_LOADING_MESSAGE = "Parsing conversation files and writing to MongoDB...";
    public static final String ANALYTICS_LOADING_MESSAGE = "Generating analytics and writing to Excel output file...";
}
