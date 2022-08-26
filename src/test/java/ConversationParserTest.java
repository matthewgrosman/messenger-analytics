import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import parser.ConversationParser;

import java.io.File;

public class ConversationParserTest {
    @Test
    public void testGetConversationsWithValidFilePath() {
        String filePath = "src/test/test-input-files";
        File[] files = ConversationParser.getConversations(filePath);

        String expectedFileName1 = "ValidTestConversationFile.json";
        String expectedFileName2 = "EmptyTestConversationFile.json";

        Assertions.assertEquals(files[0].getName(), expectedFileName1);
        Assertions.assertEquals(files[1].getName(), expectedFileName2);
    }

    @Test
    public void testGetConversationsWithInvalidFilePath() {
        String filePath = "someFilePathThatDoesntExist";
        File[] files = ConversationParser.getConversations(filePath);

        Assertions.assertNull(files);
    }
}
