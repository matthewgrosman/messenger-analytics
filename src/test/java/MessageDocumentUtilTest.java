import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.bson.Document;
import parser.MessageDocumentUtil;
import shared.Constants;

public class MessageDocumentUtilTest {
    @Test
    public void testGetBSONDocumentWithContent() {
        Document document = MessageDocumentUtil.getBSONDocument(
                UnitTestConstants.VALID_GROUP_CONVERSATION,
                Constants.GROUP_CHAT_TYPE_NAME,
                UnitTestConstants.VALID_SENDER_NAME,
                UnitTestConstants.TIMESTAMP_1,
                "This is my message"
        );

        String expectedConversationName = UnitTestConstants.VALID_GROUP_CONVERSATION;
        String expectedSenderName = UnitTestConstants.VALID_SENDER_NAME;
        String expectedDate = UnitTestConstants.TIMESTAMP_1_AS_DATE_STRING;
        String expectedContent = "This is my message";

        Assertions.assertEquals(expectedConversationName, document.get(Constants.MONGO_CONVERSATION_FIELD_NAME));
        Assertions.assertTrue((boolean)  document.get(Constants.MONGO_GROUP_CHAT_FIELD_NAME));
        Assertions.assertEquals(expectedSenderName, document.get(Constants.MONGO_SENDER_FIELD_NAME));
        Assertions.assertEquals(expectedDate, document.get(Constants.MONGO_DATE_FIELD_NAME).toString());
        Assertions.assertEquals(expectedContent, document.get(Constants.MONGO_CONTENT_FIELD_NAME));
    }

    @Test
    public void testGetBSONDocumentWithoutContent() {
        Document document = MessageDocumentUtil.getBSONDocument(
                UnitTestConstants.VALID_GROUP_CONVERSATION,
                Constants.GROUP_CHAT_TYPE_NAME,
                UnitTestConstants.VALID_SENDER_NAME,
                UnitTestConstants.TIMESTAMP_1,
                null
        );

        String expectedConversationName = UnitTestConstants.VALID_GROUP_CONVERSATION;
        String expectedSenderName = UnitTestConstants.VALID_SENDER_NAME;
        String expectedDate = UnitTestConstants.TIMESTAMP_1_AS_DATE_STRING;

        Assertions.assertEquals(expectedConversationName, document.get(Constants.MONGO_CONVERSATION_FIELD_NAME));
        Assertions.assertTrue((boolean) document.get(Constants.MONGO_GROUP_CHAT_FIELD_NAME));
        Assertions.assertEquals(expectedSenderName, document.get(Constants.MONGO_SENDER_FIELD_NAME));
        Assertions.assertEquals(expectedDate, document.get(Constants.MONGO_DATE_FIELD_NAME).toString());

        // There should be no content field, so trying to access it should give null (the
        // BSON document .get() doesn't throw a NullPointerException when accessing a key
        // that doesn't exist- it simply returns null).
        Assertions.assertNull(document.get(Constants.MONGO_CONTENT_FIELD_NAME));
    }

    @Test
    public void testGetBsonDocumentWithGroupChat() {
        Document document = MessageDocumentUtil.getBSONDocument(
                UnitTestConstants.VALID_GROUP_CONVERSATION,
                Constants.GROUP_CHAT_TYPE_NAME,
                UnitTestConstants.VALID_SENDER_NAME,
                UnitTestConstants.TIMESTAMP_1,
                null
        );

        Assertions.assertTrue((boolean) document.get(Constants.MONGO_GROUP_CHAT_FIELD_NAME));
    }

    @Test
    public void testGetBsonDocumentWithIndividualChat() {
        Document document = MessageDocumentUtil.getBSONDocument(
                UnitTestConstants.VALID_INDIVIDUAL_CONVERSATION,
                "RegularConversationNotAGroupChat",
                UnitTestConstants.VALID_SENDER_NAME,
                UnitTestConstants.TIMESTAMP_1,
                null
        );

        Assertions.assertFalse((boolean)  document.get(Constants.MONGO_GROUP_CHAT_FIELD_NAME));
    }

    @Test
    public void testGetBsonDocumentEncodesSingleQuoteCorrectly() {
        // Jackson parses single quotes (’) into "â". Check that the .getBSONDocument function
        // correctly reverts â back into ’ .
        String badParsedString = "Thatâ\u0080\u0099s doesnâ\u0080\u0099t hasnâ\u0080\u0099t " +
                "â\u0080\u0099â\u0080\u0099 This striâ\u0080\u0099ng contains some â\u0080\u0099 in it";

        String correctParsedString = "That’s doesn’t hasn’t ’’ This stri’ng contains some ’ in it";

        Document document = MessageDocumentUtil.getBSONDocument(
                UnitTestConstants.VALID_INDIVIDUAL_CONVERSATION,
                "RegularConversationNotAGroupChat",
                UnitTestConstants.VALID_SENDER_NAME,
                UnitTestConstants.TIMESTAMP_1,
                badParsedString
        );

        Assertions.assertEquals(correctParsedString, document.get(Constants.MONGO_CONTENT_FIELD_NAME));
    }
}
