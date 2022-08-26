import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import parser.ConversationParserUtil;
import shared.Constants;

import java.io.File;
import java.io.IOException;

public class ConversationParserUtilTest {
    @Test
    public void testIsSafeAndGetWithSafeAndAccessibleFields() throws IOException {
        File testInputJsonFile = new File("src/test/test-input-files/ValidTestConversationFile.json");

        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(testInputJsonFile);
        JsonNode participants = ConversationParserUtil.isSafeAndGet(rootNode, "participants");

        Assertions.assertEquals(participants.get(0).get("name").asText(), "Person1");
        Assertions.assertEquals(participants.get(1).get("name").asText(), "Matthew Grosman");
    }

    @Test
    public void testIsSafeAndGetWithNestedFields() throws IOException {
        File testInputJsonFile = new File("src/test/test-input-files/ValidTestConversationFile.json");

        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(testInputJsonFile);
        JsonNode messages = ConversationParserUtil.isSafeAndGet(rootNode, "messages");
        JsonNode bumpedMessagesMetadata = ConversationParserUtil.isSafeAndGet(messages.get(0), "bumped_message_metadata");

        Assertions.assertEquals(bumpedMessagesMetadata.get("bumped_message").asText(), "Ok no worries, thank you though");
        Assertions.assertEquals(bumpedMessagesMetadata.get("is_bumped").asText(), "false");
    }

    @Test
    public void testIsSafeAndGetWithEmptyJsonNode() throws IOException {
        File testInputJsonFile = new File("src/test/test-input-files/EmptyTestConversationFile.json");
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(testInputJsonFile);

        Assertions.assertNull(ConversationParserUtil.isSafeAndGet(rootNode, "messages"));
    }

    @Test
    public void testIsSafeAndGetWithNullJsonNode() throws IOException {
        JsonNode rootNode = null;

        Assertions.assertNull(ConversationParserUtil.isSafeAndGet(rootNode, "messages"));
    }

    @Test
    public void testIsSafeAndGetWithInvalidField() throws IOException {
        File testInputJsonFile = new File("src/test/test-input-files/ValidTestConversationFile.json");

        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(testInputJsonFile);
        Assertions.assertNull(ConversationParserUtil.isSafeAndGet(rootNode, "someFieldThatDoesNotExist"));
    }
}
