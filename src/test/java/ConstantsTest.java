import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import shared.Constants;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ConstantsTest {
    @Test
    public void testMessagesDirectoryIsValid() {
        Path path = Paths.get(Constants.MESSAGES_FOLDER);
        Assertions.assertTrue(Files.isDirectory(path));
    }
}
