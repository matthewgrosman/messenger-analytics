import analytics.Analytics;
import analytics.ConversationData;
import analytics.InvalidConversationNameException;
import analytics.InvalidDateFormatException;
import excel.ExcelWriter;
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

    @BeforeAll
    static void writeToExcel() throws InvalidConversationNameException, InvalidDateFormatException, IOException {
        // Individual conversation
        ConversationData conversationData = Analytics.getConversationData(
                UnitTestConstants.VALID_INDIVIDUAL_CONVERSATION);
        ExcelWriter.writeToExcel(UnitTestConstants.VALID_INDIVIDUAL_CONVERSATION, conversationData);

        // Group conversation
        ConversationData conversationData2 = Analytics.getConversationData(
                UnitTestConstants.VALID_GROUP_CONVERSATION);
        ExcelWriter.writeToExcel(UnitTestConstants.VALID_GROUP_CONVERSATION, conversationData2);

        // All conversations
        ConversationData conversationData3 = Analytics.getConversationData(null);
        ExcelWriter.writeToExcel(null, conversationData3);
    }

    @AfterAll
    static void closeMongoConnection() {
        MongoDBClient.closeMongoDBConnection();
    }

    @Test
    public void testWriteToExcelWritesIndividualConversationTotalNumberOfMessagesCorrectly() throws IOException {
        double expectedNumberOfMessages = 4.0;

        Sheet sheet = getWorkbookSheet(UnitTestConstants.VALID_INDIVIDUAL_CONVERSATION, Constants.EXCEL_TOTAL_MESSAGES);
        Row row = sheet.getRow(0);
        Assertions.assertEquals(row.getCell(0).toString(), Constants.EXCEL_TOTAL_MESSAGES);
        Row row2 = sheet.getRow(1);
        Assertions.assertEquals(row2.getCell(0).getNumericCellValue(), expectedNumberOfMessages);
    }

    @Test
    public void testWriteToExcelWritesIndividualConversationMessagesPerConversationCorrectly() throws IOException {
        double expectedNumberOfMessages = 4.0;

        Sheet sheet = getWorkbookSheet(UnitTestConstants.VALID_INDIVIDUAL_CONVERSATION,
                Constants.EXCEL_SHEET_PREFIX + Constants.EXCEL_MESSAGES_PER_CONVERSATION);

        Row row = sheet.getRow(0);
        Assertions.assertEquals(row.getCell(0).toString(), Constants.EXCEL_MESSAGES_PER_CONVERSATION);
        Assertions.assertEquals(row.getCell(1).toString(), Constants.EXCEL_NUM_MESSAGES_COLUMN_NAME);

        Row row2 = sheet.getRow(1);
        Assertions.assertEquals(row2.getCell(0).toString(), UnitTestConstants.VALID_INDIVIDUAL_CONVERSATION);
        Assertions.assertEquals(row2.getCell(1).getNumericCellValue(), expectedNumberOfMessages);
    }

    @Test
    public void testWriteToExcelWritesIndividualConversationMessagesPerSenderCorrectly() throws IOException {
        double expectedNumberOfMessagesSender1 = 2.0;
        double expectedNumberOfMessagesSender2 = 2.0;

        Sheet sheet = getWorkbookSheet(UnitTestConstants.VALID_INDIVIDUAL_CONVERSATION,
                Constants.EXCEL_SHEET_PREFIX + Constants.EXCEL_MESSAGES_PER_SENDER);

        Row row = sheet.getRow(0);
        Assertions.assertEquals(row.getCell(0).toString(), Constants.EXCEL_MESSAGES_PER_SENDER);
        Assertions.assertEquals(row.getCell(1).toString(), Constants.EXCEL_NUM_MESSAGES_COLUMN_NAME);

        Row row2 = sheet.getRow(1);
        Assertions.assertEquals(row2.getCell(0).toString(), "Matthew Grosman");
        Assertions.assertEquals(row2.getCell(1).getNumericCellValue(), expectedNumberOfMessagesSender1);

        Row row3 = sheet.getRow(2);
        Assertions.assertEquals(row3.getCell(0).toString(), UnitTestConstants.VALID_INDIVIDUAL_CONVERSATION);
        Assertions.assertEquals(row3.getCell(1).getNumericCellValue(), expectedNumberOfMessagesSender2);
    }

    @Test
    public void testWriteToExcelWritesIndividualConversationMessagesPerMonthCorrectly() throws IOException {
        double expectedNumberOfMessages = 4.0;

        Sheet sheet = getWorkbookSheet(UnitTestConstants.VALID_INDIVIDUAL_CONVERSATION,
                Constants.EXCEL_SHEET_PREFIX + Constants.EXCEL_MESSAGES_PER_MONTH);

        Row row = sheet.getRow(0);
        Assertions.assertEquals(row.getCell(0).toString(), Constants.EXCEL_MESSAGES_PER_MONTH);
        Assertions.assertEquals(row.getCell(1).toString(), Constants.EXCEL_NUM_MESSAGES_COLUMN_NAME);

        Row row2 = sheet.getRow(1);
        Assertions.assertEquals(row2.getCell(0).toString(), "9-2018");
        Assertions.assertEquals(row2.getCell(1).getNumericCellValue(), expectedNumberOfMessages);
    }

    @Test
    public void testWriteToExcelWritesIndividualConversationMessagesPerWeekdayCorrectly() throws IOException {
        double expectedNumberOfMessages = 4.0;

        Sheet sheet = getWorkbookSheet(UnitTestConstants.VALID_INDIVIDUAL_CONVERSATION,
                Constants.EXCEL_SHEET_PREFIX + Constants.EXCEL_MESSAGES_PER_WEEKDAY);

        Row row = sheet.getRow(0);
        Assertions.assertEquals(row.getCell(0).toString(), Constants.EXCEL_MESSAGES_PER_WEEKDAY);
        Assertions.assertEquals(row.getCell(1).toString(), Constants.EXCEL_NUM_MESSAGES_COLUMN_NAME);

        Row row2 = sheet.getRow(1);
        Assertions.assertEquals(row2.getCell(0).toString(), "Saturday");
        Assertions.assertEquals(row2.getCell(1).getNumericCellValue(), expectedNumberOfMessages);
    }

    @Test
    public void testWriteToExcelWritesIndividualConversationMessagesPerHourCorrectly() throws IOException {
        double expectedNumberOfMessagesHour1 = 1.0;
        double expectedNumberOfMessagesHour2 = 2.0;

        Sheet sheet = getWorkbookSheet(UnitTestConstants.VALID_INDIVIDUAL_CONVERSATION,
                Constants.EXCEL_SHEET_PREFIX + Constants.EXCEL_MESSAGES_PER_HOUR);

        Row row = sheet.getRow(0);
        Assertions.assertEquals(row.getCell(0).toString(), Constants.EXCEL_MESSAGES_PER_HOUR);
        Assertions.assertEquals(row.getCell(1).toString(), Constants.EXCEL_NUM_MESSAGES_COLUMN_NAME);

        Row row2 = sheet.getRow(1);
        Assertions.assertEquals(row2.getCell(0).toString(), "13:00");
        Assertions.assertEquals(row2.getCell(1).getNumericCellValue(), expectedNumberOfMessagesHour2);

        Row row3 = sheet.getRow(2);
        Assertions.assertEquals(row3.getCell(0).toString(), "11:00");
        Assertions.assertEquals(row3.getCell(1).getNumericCellValue(), expectedNumberOfMessagesHour1);

        Row row4 = sheet.getRow(3);
        Assertions.assertEquals(row4.getCell(0).toString(), "21:00");
        Assertions.assertEquals(row4.getCell(1).getNumericCellValue(), expectedNumberOfMessagesHour1);
    }

    @Test
    public void testWriteToExcelWritesGroupConversationTotalNumberOfMessagesCorrectly() throws IOException {
        double expectedNumberOfMessages = 10.0;

        Sheet sheet = getWorkbookSheet(UnitTestConstants.VALID_GROUP_CONVERSATION, Constants.EXCEL_TOTAL_MESSAGES);
        Row row = sheet.getRow(0);
        Assertions.assertEquals(row.getCell(0).toString(), Constants.EXCEL_TOTAL_MESSAGES);
        Row row2 = sheet.getRow(1);
        Assertions.assertEquals(row2.getCell(0).getNumericCellValue(), expectedNumberOfMessages);
    }

    @Test
    public void testWriteToExcelWritesGroupConversationMessagesPerConversationCorrectly() throws IOException {
        double expectedNumberOfMessages = 10.0;

        Sheet sheet = getWorkbookSheet(UnitTestConstants.VALID_GROUP_CONVERSATION,
                Constants.EXCEL_SHEET_PREFIX + Constants.EXCEL_MESSAGES_PER_CONVERSATION);

        Row row = sheet.getRow(0);
        Assertions.assertEquals(row.getCell(0).toString(), Constants.EXCEL_MESSAGES_PER_CONVERSATION);
        Assertions.assertEquals(row.getCell(1).toString(), Constants.EXCEL_NUM_MESSAGES_COLUMN_NAME);

        Row row2 = sheet.getRow(1);
        Assertions.assertEquals(row2.getCell(0).toString(), UnitTestConstants.VALID_GROUP_CONVERSATION);
        Assertions.assertEquals(row2.getCell(1).getNumericCellValue(), expectedNumberOfMessages);
    }

    @Test
    public void testWriteToExcelWritesGroupConversationMessagesPerSenderCorrectly() throws IOException {
        double expectedNumberOfMessagesSender1 = 2.0;
        double expectedNumberOfMessagesSender2 = 6.0;

        Sheet sheet = getWorkbookSheet(UnitTestConstants.VALID_GROUP_CONVERSATION,
                Constants.EXCEL_SHEET_PREFIX + Constants.EXCEL_MESSAGES_PER_SENDER);

        Row row = sheet.getRow(0);
        Assertions.assertEquals(row.getCell(0).toString(), Constants.EXCEL_MESSAGES_PER_SENDER);
        Assertions.assertEquals(row.getCell(1).toString(), Constants.EXCEL_NUM_MESSAGES_COLUMN_NAME);

        Row row2 = sheet.getRow(1);
        Assertions.assertEquals(row2.getCell(0).toString(), "Matthew Grosman");
        Assertions.assertEquals(row2.getCell(1).getNumericCellValue(), expectedNumberOfMessagesSender1);

        Row row3 = sheet.getRow(2);
        Assertions.assertEquals(row3.getCell(0).toString(), "Steven Juana");
        Assertions.assertEquals(row3.getCell(1).getNumericCellValue(), expectedNumberOfMessagesSender2);

        Row row4 = sheet.getRow(3);
        Assertions.assertEquals(row4.getCell(0).toString(), "Bo Bramer");
        Assertions.assertEquals(row4.getCell(1).getNumericCellValue(), expectedNumberOfMessagesSender1);
    }

    @Test
    public void testWriteToExcelWritesGroupConversationMessagesPerMonthCorrectly() throws IOException {
        double expectedNumberOfMessages = 10.0;

        Sheet sheet = getWorkbookSheet(UnitTestConstants.VALID_GROUP_CONVERSATION,
                Constants.EXCEL_SHEET_PREFIX + Constants.EXCEL_MESSAGES_PER_MONTH);

        Row row = sheet.getRow(0);
        Assertions.assertEquals(row.getCell(0).toString(), Constants.EXCEL_MESSAGES_PER_MONTH);
        Assertions.assertEquals(row.getCell(1).toString(), Constants.EXCEL_NUM_MESSAGES_COLUMN_NAME);

        Row row2 = sheet.getRow(1);
        Assertions.assertEquals(row2.getCell(0).toString(), "6-2019");
        Assertions.assertEquals(row2.getCell(1).getNumericCellValue(), expectedNumberOfMessages);
    }

    @Test
    public void testWriteToExcelWritesGroupConversationMessagesPerWeekdayCorrectly() throws IOException {
        double expectedNumberOfMessages = 10.0;

        Sheet sheet = getWorkbookSheet(UnitTestConstants.VALID_GROUP_CONVERSATION,
                Constants.EXCEL_SHEET_PREFIX + Constants.EXCEL_MESSAGES_PER_WEEKDAY);

        Row row = sheet.getRow(0);
        Assertions.assertEquals(row.getCell(0).toString(), Constants.EXCEL_MESSAGES_PER_WEEKDAY);
        Assertions.assertEquals(row.getCell(1).toString(), Constants.EXCEL_NUM_MESSAGES_COLUMN_NAME);

        Row row2 = sheet.getRow(1);
        Assertions.assertEquals(row2.getCell(0).toString(), "Friday");
        Assertions.assertEquals(row2.getCell(1).getNumericCellValue(), expectedNumberOfMessages);
    }

    @Test
    public void testWriteToExcelWritesGroupConversationMessagesPerHourCorrectly() throws IOException {
        double expectedNumberOfMessagesHour1 = 7.0;
        double expectedNumberOfMessagesHour2 = 3.0;

        Sheet sheet = getWorkbookSheet(UnitTestConstants.VALID_GROUP_CONVERSATION,
                Constants.EXCEL_SHEET_PREFIX + Constants.EXCEL_MESSAGES_PER_HOUR);

        Row row = sheet.getRow(0);
        Assertions.assertEquals(row.getCell(0).toString(), Constants.EXCEL_MESSAGES_PER_HOUR);
        Assertions.assertEquals(row.getCell(1).toString(), Constants.EXCEL_NUM_MESSAGES_COLUMN_NAME);

        Row row2 = sheet.getRow(1);
        Assertions.assertEquals(row2.getCell(0).toString(), "20:00");
        Assertions.assertEquals(row2.getCell(1).getNumericCellValue(), expectedNumberOfMessagesHour1);

        Row row3 = sheet.getRow(2);
        Assertions.assertEquals(row3.getCell(0).toString(), "21:00");
        Assertions.assertEquals(row3.getCell(1).getNumericCellValue(), expectedNumberOfMessagesHour2);
    }

    @Test
    public void testWriteToExcelWritesAllConversationsTotalNumberOfMessagesCorrectly() throws IOException {
        double expectedNumberOfMessages = 14.0;

        Sheet sheet = getWorkbookSheet(Constants.EXCEL_ALL_CONVERSATIONS_FILE_PREFIX, Constants.EXCEL_TOTAL_MESSAGES);
        Row row = sheet.getRow(0);
        Assertions.assertEquals(row.getCell(0).toString(), Constants.EXCEL_TOTAL_MESSAGES);
        Row row2 = sheet.getRow(1);
        Assertions.assertEquals(row2.getCell(0).getNumericCellValue(), expectedNumberOfMessages);
    }

    @Test
    public void testWriteToExcelWritesAllConversationsMessagesPerConversationCorrectly() throws IOException {
        double expectedNumberOfMessages1 = 10.0;
        double expectedNumberOfMessages2 = 4.0;

        Sheet sheet = getWorkbookSheet(Constants.EXCEL_ALL_CONVERSATIONS_FILE_PREFIX,
                Constants.EXCEL_SHEET_PREFIX + Constants.EXCEL_MESSAGES_PER_CONVERSATION);

        Row row = sheet.getRow(0);
        Assertions.assertEquals(row.getCell(0).toString(), Constants.EXCEL_MESSAGES_PER_CONVERSATION);
        Assertions.assertEquals(row.getCell(1).toString(), Constants.EXCEL_NUM_MESSAGES_COLUMN_NAME);

        Row row2 = sheet.getRow(1);
        Assertions.assertEquals(row2.getCell(0).toString(), UnitTestConstants.VALID_GROUP_CONVERSATION);
        Assertions.assertEquals(row2.getCell(1).getNumericCellValue(), expectedNumberOfMessages1);

        Row row3 = sheet.getRow(2);
        Assertions.assertEquals(row3.getCell(0).toString(), UnitTestConstants.VALID_INDIVIDUAL_CONVERSATION);
        Assertions.assertEquals(row3.getCell(1).getNumericCellValue(), expectedNumberOfMessages2);
    }

    @Test
    public void testWriteToExcelWritesAllConversationsMessagesPerSenderCorrectly() throws IOException {
        double expectedNumberOfMessagesSender1 = 2.0;
        double expectedNumberOfMessagesSender2 = 4.0;
        double expectedNumberOfMessagesSender3 = 6.0;


        Sheet sheet = getWorkbookSheet(Constants.EXCEL_ALL_CONVERSATIONS_FILE_PREFIX,
                Constants.EXCEL_SHEET_PREFIX + Constants.EXCEL_MESSAGES_PER_SENDER);

        Row row = sheet.getRow(0);
        Assertions.assertEquals(row.getCell(0).toString(), Constants.EXCEL_MESSAGES_PER_SENDER);
        Assertions.assertEquals(row.getCell(1).toString(), Constants.EXCEL_NUM_MESSAGES_COLUMN_NAME);

        Row row2 = sheet.getRow(1);
        Assertions.assertEquals(row2.getCell(0).toString(), "Matthew Grosman");
        Assertions.assertEquals(row2.getCell(1).getNumericCellValue(), expectedNumberOfMessagesSender2);

        Row row3 = sheet.getRow(2);
        Assertions.assertEquals(row3.getCell(0).toString(), "Steven Juana");
        Assertions.assertEquals(row3.getCell(1).getNumericCellValue(), expectedNumberOfMessagesSender3);

        Row row4 = sheet.getRow(3);
        Assertions.assertEquals(row4.getCell(0).toString(), "Person1");
        Assertions.assertEquals(row4.getCell(1).getNumericCellValue(), expectedNumberOfMessagesSender1);

        Row row5 = sheet.getRow(4);
        Assertions.assertEquals(row5.getCell(0).toString(), "Bo Bramer");
        Assertions.assertEquals(row5.getCell(1).getNumericCellValue(), expectedNumberOfMessagesSender1);
    }

    @Test
    public void testWriteToExcelWritesAllConversationsMessagesPerMonthCorrectly() throws IOException {
        double expectedNumberOfMessages1 = 10.0;
        double expectedNumberOfMessages2 = 4.0;

        Sheet sheet = getWorkbookSheet(Constants.EXCEL_ALL_CONVERSATIONS_FILE_PREFIX,
                Constants.EXCEL_SHEET_PREFIX + Constants.EXCEL_MESSAGES_PER_MONTH);

        Row row = sheet.getRow(0);
        Assertions.assertEquals(row.getCell(0).toString(), Constants.EXCEL_MESSAGES_PER_MONTH);
        Assertions.assertEquals(row.getCell(1).toString(), Constants.EXCEL_NUM_MESSAGES_COLUMN_NAME);

        Row row2 = sheet.getRow(1);
        Assertions.assertEquals(row2.getCell(0).toString(), "6-2019");
        Assertions.assertEquals(row2.getCell(1).getNumericCellValue(), expectedNumberOfMessages1);

        Row row3 = sheet.getRow(2);
        Assertions.assertEquals(row3.getCell(0).toString(), "9-2018");
        Assertions.assertEquals(row3.getCell(1).getNumericCellValue(), expectedNumberOfMessages2);
    }

    @Test
    public void testWriteToExcelWritesAllConversationsMessagesPerWeekdayCorrectly() throws IOException {
        double expectedNumberOfMessages1 = 10.0;
        double expectedNumberOfMessages2 = 4.0;

        Sheet sheet = getWorkbookSheet(Constants.EXCEL_ALL_CONVERSATIONS_FILE_PREFIX,
                Constants.EXCEL_SHEET_PREFIX + Constants.EXCEL_MESSAGES_PER_WEEKDAY);

        Row row = sheet.getRow(0);
        Assertions.assertEquals(row.getCell(0).toString(), Constants.EXCEL_MESSAGES_PER_WEEKDAY);
        Assertions.assertEquals(row.getCell(1).toString(), Constants.EXCEL_NUM_MESSAGES_COLUMN_NAME);

        Row row2 = sheet.getRow(1);
        Assertions.assertEquals(row2.getCell(0).toString(), "Friday");
        Assertions.assertEquals(row2.getCell(1).getNumericCellValue(), expectedNumberOfMessages1);

        Row row3 = sheet.getRow(2);
        Assertions.assertEquals(row3.getCell(0).toString(), "Saturday");
        Assertions.assertEquals(row3.getCell(1).getNumericCellValue(), expectedNumberOfMessages2);
    }

    @Test
    public void testWriteToExcelWritesAllConversationsMessagesPerHourCorrectly() throws IOException {
        double expectedNumberOfMessagesHour1 = 2.0;
        double expectedNumberOfMessagesHour2 = 1.0;
        double expectedNumberOfMessagesHour3 = 7.0;
        double expectedNumberOfMessagesHour4 = 4.0;

        Sheet sheet = getWorkbookSheet(Constants.EXCEL_ALL_CONVERSATIONS_FILE_PREFIX,
                Constants.EXCEL_SHEET_PREFIX + Constants.EXCEL_MESSAGES_PER_HOUR);

        Row row = sheet.getRow(0);
        Assertions.assertEquals(row.getCell(0).toString(), Constants.EXCEL_MESSAGES_PER_HOUR);
        Assertions.assertEquals(row.getCell(1).toString(), Constants.EXCEL_NUM_MESSAGES_COLUMN_NAME);

        Row row2 = sheet.getRow(1);
        Assertions.assertEquals(row2.getCell(0).toString(), "13:00");
        Assertions.assertEquals(row2.getCell(1).getNumericCellValue(), expectedNumberOfMessagesHour1);

        Row row3 = sheet.getRow(2);
        Assertions.assertEquals(row3.getCell(0).toString(), "11:00");
        Assertions.assertEquals(row3.getCell(1).getNumericCellValue(), expectedNumberOfMessagesHour2);

        Row row4 = sheet.getRow(3);
        Assertions.assertEquals(row4.getCell(0).toString(), "20:00");
        Assertions.assertEquals(row4.getCell(1).getNumericCellValue(), expectedNumberOfMessagesHour3);

        Row row5 = sheet.getRow(4);
        Assertions.assertEquals(row5.getCell(0).toString(), "21:00");
        Assertions.assertEquals(row5.getCell(1).getNumericCellValue(), expectedNumberOfMessagesHour4);
    }


    private Sheet getWorkbookSheet(String conversationName, String sheetName) throws IOException {
        String outputFile = Constants.EXCEL_OUTPUT_FOLDER + conversationName + "-" + java.time.LocalDate.now()
                + Constants.EXCEL_FILE_EXTENSION;
        FileInputStream file = new FileInputStream(outputFile);
        Workbook workbook = new XSSFWorkbook(file);
        return workbook.getSheet(sheetName);
    }
}
