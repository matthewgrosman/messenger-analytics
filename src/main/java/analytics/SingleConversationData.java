package analytics;

public class SingleConversationData implements ConversationData {
    public long numberOfMessages;

    /**
     * Prints out the conversation data.
     *
     * NOTE: Yes this is janky and ugly, but the final version will be presented in a GUI or
     * some other clean way of displaying data. This is mainly just here to test and validate that
     * the code is working.
     */
    public void printConversationData() {
        System.out.println("Total number of messages sent: " + numberOfMessages);

        System.out.println();

        for (String key : messagesPerPerson.keySet()) {
            System.out.printf("Name: " + key
                    + ", Number of Messages: " + messagesPerPerson.get(key));
            System.out.printf(" (%.2f%%)%n", AnalyticsUtil.getProportionAsPercentage(numberOfMessages, messagesPerPerson.get(key)));
        }

        System.out.println();

        for (String key : messagesPerMonth.keySet()) {
            System.out.printf("Date: " + key
                    + ", Number of Messages: " + messagesPerMonth.get(key));
            System.out.printf(" (%.2f%%)%n", AnalyticsUtil.getProportionAsPercentage(numberOfMessages, messagesPerMonth.get(key)));
        }

        System.out.println();

        for (String key : messagesPerWeekday.keySet()) {
            System.out.printf("Weekday: " + key
                    + ", Number of Messages: " + messagesPerWeekday.get(key));
            System.out.printf(" (%.2f%%)%n", AnalyticsUtil.getProportionAsPercentage(numberOfMessages, messagesPerWeekday.get(key)));
        }

        System.out.println();

        for (String key : messagesPerHour.keySet()) {
            System.out.printf("Hour: " + key
                    + ", Number of Messages: " + messagesPerHour.get(key));
            System.out.printf(" (%.2f%%)%n", AnalyticsUtil.getProportionAsPercentage(numberOfMessages, messagesPerHour.get(key)));
        }
    }

}
