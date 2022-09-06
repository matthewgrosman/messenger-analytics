package analytics;

import java.util.HashMap;

public class ConversationData {
    public long numberOfMessages;
    public HashMap<String, Integer> messagesPerConversation;
    public HashMap<String, Integer> messagesPerSender;
    public HashMap<String, Integer> messagesPerMonth;
    public HashMap<String, Integer> messagesPerWeekday;
    public HashMap<String, Integer> messagesPerHour;

    /**
     * Constructor for the ConversationData class. Initialize all of the HashMaps so that
     * they can be used in the ConversationDataMongoDB class to store analytics data.
     */
    public ConversationData() {
        messagesPerConversation = new HashMap<>();
        messagesPerSender = new HashMap<>();
        messagesPerMonth = new HashMap<>();
        messagesPerWeekday = new HashMap<>();
        messagesPerHour = new HashMap<>();
    }
}
