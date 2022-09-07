package excel;

import shared.Constants;

import analytics.ConversationData;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ExcelWriter {
    private static Workbook workbook;

    /**
     * Writes conversation data from user inputted conversation and writes it to Excel. The excel file is
     * in the format of separate sheets for each analytic type (i.e. there is a separate sheet for messages
     * per conversation, messages per sender, etc.)
     *
     * @param conversationName  A String denoting the name of the conversation.
     * @param conversationData  A ConversationData object containing all of the data from a given conversation.
     * @throws IOException      Writing to Excel may throw an IOException
     */
    public static void writeToExcel(String conversationName, ConversationData conversationData) throws IOException {
        // Create a new Excel Workbook to hold all of the analytics sheets
        workbook = new XSSFWorkbook();

        // For each analytic, create a new sheet and write the data.
        writeAnalyticToSheet(conversationData.numberOfMessages, Constants.EXCEL_TOTAL_MESSAGES);
        writeAnalyticToSheet(conversationData.messagesPerConversation, Constants.EXCEL_MESSAGES_PER_CONVERSATION);
        writeAnalyticToSheet(conversationData.messagesPerSender, Constants.EXCEL_MESSAGES_PER_SENDER);
        writeAnalyticToSheet(conversationData.messagesPerMonth, Constants.EXCEL_MESSAGES_PER_MONTH);
        writeAnalyticToSheet(conversationData.messagesPerWeekday, Constants.EXCEL_MESSAGES_PER_WEEKDAY);
        writeAnalyticToSheet(conversationData.messagesPerHour, Constants.EXCEL_MESSAGES_PER_HOUR);

        // After all data has been formatted in sheets, write the workbook to an output file.
        writeToOutputFile(conversationName);

        // Close the workbook.
        workbook.close();
    }

    /**
     * Given a single analytic HashMap (i.e. HashMap for messages per conversation or messages per hour, etc.),
     * write this data to a new Excel sheet in the workbook.
     *
     * @param dataHashMap   A HashMap<String, Integer> containing data for the current analytic type. The
     *                      String is a name of the conversation, sender, month, etc. depending on what
     *                      analytic we are dealing with and the Integer is the value associated with that name
     *                      for the given analytic.
     * @param analyticName  A String representing the current analytic name.
     */
    private static void writeAnalyticToSheet(HashMap<String, Integer> dataHashMap, String analyticName) {
        // Create a new sheet for each analytic type we deal with.
        Sheet sheet = workbook.createSheet(Constants.EXCEL_SHEET_PREFIX + analyticName);

        // Keeps track of the current row we are writing to in the sheet.
        int rowIndexNumber = 0;

        // Create the header row. This is two columns- the analytic name in the first columns (i.e.
        // "Sender" or "Conversation"), and the value in the second column (i.e "NumberOfMessages").
        Row header = sheet.createRow(rowIndexNumber);
        header.createCell(Constants.EXCEL_NAME_COLUMN_NUMBER).setCellValue(analyticName);
        header.createCell(Constants.EXCEL_DATA_COLUMN_NUMBER).setCellValue(Constants.EXCEL_NUM_MESSAGES_COLUMN_NAME);

        // Add the data from the HashMap to the sheet. Each HashMap has the the format of
        // <String, Integer>, where the String is a name of the conversation, sender, month, etc. depending
        // on what analytic we are dealing with and the Integer is the value associated with that name
        // for the given analytic. We put the name in the first column and the value in the second column.
        for (Map.Entry<String,Integer> entry : dataHashMap.entrySet()) {
            Row row = sheet.createRow(++rowIndexNumber);
            row.createCell(Constants.EXCEL_NAME_COLUMN_NUMBER).setCellValue(entry.getKey());
            row.createCell(Constants.EXCEL_DATA_COLUMN_NUMBER).setCellValue(entry.getValue());
        }
    }

    /**
     * Given the analytic number of messages, write this data to a new Excel sheet in the workbook.
     *
     * @param numberOfMessages  A long representing the total number of messages for a conversation.
     * @param analyticName      A String representing the current analytic name.
     */
    private static void writeAnalyticToSheet(long numberOfMessages, String analyticName) {
        // Create a new sheet for each analytic type we deal with. Unlike the other metrics,
        // simply using the analyticName for the sheet name (in this case it is
        // "TotalNumberOfMessages") is good enough, and works better without prepending
        // the prefix (to make it "messagesPerTotalNumberOfMessages").
        Sheet sheet = workbook.createSheet(analyticName);

        // Keeps track of the current row we are writing to in the sheet.
        int rowIndexNumber = 0;

        // Create the header row. This is just a single column
        Row header = sheet.createRow(rowIndexNumber);
        header.createCell(Constants.EXCEL_NAME_COLUMN_NUMBER).setCellValue(analyticName);

        // Write the data to the sheet. For this analytic, we are just writing a single value, the
        // number of messages for the conversation.
        Row row = sheet.createRow(++rowIndexNumber);
        row.createCell(Constants.EXCEL_NAME_COLUMN_NUMBER).setCellValue(numberOfMessages);
    }

    /**
     * Writes the completed workbook to an output file.
     *
     * @param conversationName  A String representing the conversation name.
     * @throws IOException      Can throw an IOException from .write() method.
     */
    private static void writeToOutputFile(String conversationName) throws IOException {
        // A null conversation name means the user has requested to see aggregated conversation data.
        // If we leave this as null, the output filename will be "null-2022-08-07...", so we change it
        // to "All-Conversations" for a better filename.
        if (conversationName == null) {
            conversationName = Constants.EXCEL_ALL_CONVERSATIONS_FILE_PREFIX;
        }

        // Create a unique file name in the format of conversationName + the current date and time.
        String outputFile = Constants.EXCEL_OUTPUT_FOLDER + conversationName + "-" + java.time.LocalDate.now()
                + Constants.EXCEL_FILE_EXTENSION;

        FileOutputStream outputStream = new FileOutputStream(outputFile);
        workbook.write(outputStream);
    }
}
