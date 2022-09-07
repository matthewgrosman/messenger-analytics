import analytics.Analytics;
import analytics.ConversationData;
import analytics.InvalidConversationNameException;
import analytics.InvalidDateFormatException;
import excel.ExcelWriter;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import shared.Constants;
import shared.MongoDBClient;

import java.io.FileInputStream;
import java.io.IOException;

public class ExcelWriterTest {
    @BeforeAll
    static void establishMongoConnection() {
        MongoDBClient.getMongoDBConnection(Constants.MONGO_COLLECTION_NAME_TEST);
    }

    @AfterAll
    static void closeMongoConnection() {
        MongoDBClient.closeMongoDBConnection();
    }

    @Test
    public void testWriteToExcelWritesIndividualConversationTotalNumberOfMessagesCorrectly() throws InvalidConversationNameException, InvalidDateFormatException, IOException {
        ConversationData conversationData = Analytics.getConversationData(
                UnitTestConstants.VALID_INDIVIDUAL_CONVERSATION);
        ExcelWriter.writeToExcel(UnitTestConstants.VALID_INDIVIDUAL_CONVERSATION, conversationData);

        double expectedNumberOfMessages = 4.0;

        Sheet sheet = getWorkbookSheet(UnitTestConstants.VALID_INDIVIDUAL_CONVERSATION, Constants.EXCEL_TOTAL_MESSAGES);
        Row row = sheet.getRow(0);
        Assertions.assertEquals(row.getCell(0).toString(), Constants.EXCEL_TOTAL_MESSAGES);
        Row row2 = sheet.getRow(1);
        Assertions.assertEquals(row2.getCell(0).getNumericCellValue(), expectedNumberOfMessages);
    }

    private Sheet getWorkbookSheet(String conversationName, String sheetName) throws IOException {
        String outputFile = Constants.EXCEL_OUTPUT_FOLDER + conversationName + "-" + java.time.LocalDate.now()
                + Constants.EXCEL_FILE_EXTENSION;

        FileInputStream file = new FileInputStream(outputFile);
        Workbook workbook = new XSSFWorkbook(file);
        return workbook.getSheet(sheetName);
    }
}
