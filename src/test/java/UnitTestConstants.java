import org.bson.Document;
import shared.Constants;

import java.util.Date;

public class UnitTestConstants {
    public static final Document VALID_MESSAGE_1 = buildValidMessage1();
    public static final Document VALID_MESSAGE_2 = buildValidMessage2();

    private static Document buildValidMessage1() {
        Document document = new Document();
        long timestamp = 1572117162876L;

        document.append("_id", "62f045f3cb73d93703acf761");
        document.append(Constants.MONGO_CONVERSATION_FIELD_NAME, "Ashley Kil");
        document.append(Constants.MONGO_GROUP_CHAT_FIELD_NAME, false);
        document.append(Constants.MONGO_SENDER_FIELD_NAME, "Matthew Grosman");
        document.append(Constants.MONGO_DATE_FIELD_NAME, new Date(timestamp));
        document.append(Constants.MONGO_CONTENT_FIELD_NAME, "Hello okay test");

        return document;
    }

    private static Document buildValidMessage2() {
        Document document = new Document();
        long timestamp = 1572117162876L;

        document.append("_id", "62f045f3cb73dHSDFKS03acf761");
        document.append(Constants.MONGO_CONVERSATION_FIELD_NAME, "Ashley Kil");
        document.append(Constants.MONGO_GROUP_CHAT_FIELD_NAME, false);
        document.append(Constants.MONGO_SENDER_FIELD_NAME, "Matthew Grosman");
        document.append(Constants.MONGO_DATE_FIELD_NAME, new Date(timestamp));
        document.append(Constants.MONGO_CONTENT_FIELD_NAME, "Different content than the first message");

        return document;
    }
}
