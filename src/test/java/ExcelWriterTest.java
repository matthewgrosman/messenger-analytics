import analytics.Analytics;
import analytics.ConversationData;
import analytics.InvalidConversationNameException;
import analytics.InvalidDateFormatException;
import excel.ExcelWriter;
import org.junit.jupiter.api.Test;
import shared.Constants;

import java.io.IOException;

public class ExcelWriterTest {
    @Test
    public void testWriteToExcelWritesCorrectly() throws InvalidConversationNameException, InvalidDateFormatException, IOException {
        ConversationData conversationData = Analytics.getConversationData(
                UnitTestConstants.VALID_INDIVIDUAL_CONVERSATION);

        ExcelWriter.writeToExcel(UnitTestConstants.VALID_INDIVIDUAL_CONVERSATION, conversationData);

        String outputFile = Constants.EXCEL_OUTPUT_FOLDER + UnitTestConstants.VALID_INDIVIDUAL_CONVERSATION
                + "-" + java.time.LocalDateTime.now() + Constants.EXCEL_FILE_EXTENSION;
    }
}
