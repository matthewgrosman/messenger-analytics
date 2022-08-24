package analytics;

import shared.Constants;
import shared.MongoDBClient;

import java.util.Scanner;

public class Analytics {
    /**
     * Generates analytics on messages either on single conversation level or aggregated across
     * all conversations level. This is determined by the parameter conversationName. If
     * conversationName is null, then we serve the user aggregated conversations analytics. If the
     * user specifies a specific conversation for the conversationName parameter, we serve
     * them analytics specific to just that conversation.
     *
     * Single conversation analytics include:
     *  - Number of messages sent.
     *  - Number of messages sent each month (number and percentages).
     *  - Who has sent how many messages (number and percentages).
     *  - Most active hours (number and percentages).
     *
     * Aggregated conversation analytics include:
     *  - Number of messages sent.
     *  - Number of messages sent each month (number and percentages).
     *  - Who has sent how many messages (number and percentages).
     *  - Most active hours (number and percentages).
     *  - How many messages have been sent in each conversation (number and percentages).
     *
     * @param conversationName  A String denoting the current conversation name if the user would like
     *                          to filter results to a single conversation level. This parameter is left
     *                          as null if the user would like to view aggregated data across all conversations.
     * @return                  A ConversationData object that contains all relevant analytics data collected
     *                          in this function.
     *
     * @throws InvalidConversationNameException Can throw an InvalidConversationNameException if the conversation
     *                                          name provided by the user does not exist.
     * @throws InvalidDateFormatException       ConversationDataMongoDB.getConversationData() can throw an
     *                                          InvalidDateFormatException.
     */
    public static ConversationData getConversationData(String conversationName) throws InvalidConversationNameException, InvalidDateFormatException {
        // If the user wants to filter down analytics to a specific conversation, we need to ensure
        // that they have entered a valid conversation name. This statement checks that either the
        // user does not want to filter down to a specific conversation (meaning conversationName is null
        // and they want aggregated conversation data), or if a user does want to filter down analytics,
        // then the conversation is valid.
        if (conversationName == null || ConversationDataMongoDB.isConversationValid(conversationName)) {
            return ConversationDataMongoDB.getConversationData(conversationName);
        }
        else {
            throw new InvalidConversationNameException(Constants.INVALID_CONV_EXCEPTION_MESSAGE + conversationName);
        }
    }

    public static void main(String[] args) throws InvalidConversationNameException, InvalidDateFormatException {
        // Open MongoDB client.
        MongoDBClient.getMongoDBConnection();

        // Gets user input.
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter person or conversation name, or leave blank for analytics aggregated across all conversations: ");
        String input = scanner.nextLine();
        String conversationName = (input.equals("")) ? null : input;
        scanner.close();

        // Get and print conversation data.
        ConversationData conversationData = getConversationData(conversationName);
        conversationData.printConversationData();

        // Close MongoDB client.
        MongoDBClient.closeMongoDBConnection();
    }
}
