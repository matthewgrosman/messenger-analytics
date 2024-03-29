import org.bson.Document;
import shared.Constants;

import java.util.Date;

public class UnitTestConstants {
    // Timestamp for use in test messages. This timestamp correlate to the date of:
    // Saturday, October 26th 2019, 12:12:42 pm
    public static final long TIMESTAMP_1 = 1572117162876L;
    public static final String TIMESTAMP_1_AS_DATE_STRING = "Sat Oct 26 12:12:42 PDT 2019";

    // Timestamp for use in test messages. This timestamp correlate to the date of:
    // Friday, May 7th 2021, 3:33:51 pm
    public static final long TIMESTAMP_2 = 1620426831850L;

    // Valid conversation name of an individual conversation.
    public static final String VALID_INDIVIDUAL_CONVERSATION = "Person1";

    // Valid conversation name of a group conversation.
    public static final String VALID_GROUP_CONVERSATION = "Steven and Bo";

    // Valid sender name
    public static final String VALID_SENDER_NAME = "Matthew Grosman";

    // These are two valid messages. They have different "_id" and "Content" fields,
    // but the other fields are the same. This is to make it easy to test methods
    // for adding the same value to a HashMap twice.
    public static final Document VALID_MESSAGE_1 = buildValidMessage1();
    public static final Document VALID_MESSAGE_2 = buildValidMessage2();
    public static final Document VALID_MESSAGE_3 = buildValidMessage3();

    private static Document buildValidMessage1() {
        Document document = new Document();

        document.append("_id", "62f045f3cb73d93703acf761");
        document.append(Constants.MONGO_CONVERSATION_FIELD_NAME, "Ashley Kil");
        document.append(Constants.MONGO_GROUP_CHAT_FIELD_NAME, false);
        document.append(Constants.MONGO_SENDER_FIELD_NAME, "Matthew Grosman");
        document.append(Constants.MONGO_DATE_FIELD_NAME, new Date(TIMESTAMP_1));
        document.append(Constants.MONGO_CONTENT_FIELD_NAME, "Hello okay test");

        return document;
    }

    private static Document buildValidMessage2() {
        Document document = new Document();

        document.append("_id", "62f045f3cb73dHSDFKS03acf761");
        document.append(Constants.MONGO_CONVERSATION_FIELD_NAME, "Ashley Kil");
        document.append(Constants.MONGO_GROUP_CHAT_FIELD_NAME, false);
        document.append(Constants.MONGO_SENDER_FIELD_NAME, "Matthew Grosman");
        document.append(Constants.MONGO_DATE_FIELD_NAME, new Date(TIMESTAMP_1));
        document.append(Constants.MONGO_CONTENT_FIELD_NAME, "Different content than the first message");

        return document;
    }

    private static Document buildValidMessage3() {
        Document document = new Document();

        document.append("_id", "62f045f3cb73d349cjfnkS03acf761");
        document.append(Constants.MONGO_CONVERSATION_FIELD_NAME, "BOMB-FIRE");
        document.append(Constants.MONGO_GROUP_CHAT_FIELD_NAME, true);
        document.append(Constants.MONGO_SENDER_FIELD_NAME, "Steven Juana");
        document.append(Constants.MONGO_DATE_FIELD_NAME, new Date(TIMESTAMP_2));
        document.append(Constants.MONGO_CONTENT_FIELD_NAME, "New content");

        return document;
    }
}
