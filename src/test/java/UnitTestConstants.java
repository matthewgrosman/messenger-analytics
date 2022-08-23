import org.bson.Document;
import shared.Constants;

public class UnitTestConstants {
    public static final Document VALID_MESSAGE = buildValidMessage();

    private static Document buildValidMessage() {
        Document document = new Document();
        document.append("_id", "62f045f3cb73d93703acf761");
        document.append(Constants.MONGO_CONVERSATION_FIELD_NAME, "Ashley Kil");
        document.append(Constants.MONGO_GROUP_CHAT_FIELD_NAME, false);
        document.append(Constants.MONGO_SENDER_FIELD_NAME, "Matthew Grosman");
        document.append(Constants.MONGO_DATE_FIELD_NAME, "Tue Jan 25 14:29:16 PST 2022");
        document.append(Constants.MONGO_CONTENT_FIELD_NAME, "Hello okay test");

        return document;
    }
}
