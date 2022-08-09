package analytics;

import java.util.HashMap;

public class AggregatedConversationData {
    public long numberOfMessages;
    public HashMap<String, Integer> messagesPerConversation;
    public HashMap<String, Integer> messagesPerMonth;
    public HashMap<String, Integer> messagesPerWeekday;
    public HashMap<String, Integer> messagesPerHour;
    public HashMap<String, Integer> messagesPerSender;

    /**
     * Constructor for the AggregatedConversationData class.
     */
    public AggregatedConversationData() {
        messagesPerConversation = new HashMap<>();
        messagesPerMonth = new HashMap<>();
        messagesPerWeekday = new HashMap<>();
        messagesPerHour = new HashMap<>();
        messagesPerSender = new HashMap<>();
    }

    public void printConversationData() {
    }
}
