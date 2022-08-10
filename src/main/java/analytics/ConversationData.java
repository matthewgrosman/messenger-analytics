package analytics;

import java.util.HashMap;

public interface ConversationData {
    HashMap<String, Integer> messagesPerPerson = new HashMap<>();
    HashMap<String, Integer> messagesPerMonth = new HashMap<>();
    HashMap<String, Integer> messagesPerWeekday = new HashMap<>();
    HashMap<String, Integer> messagesPerHour = new HashMap<>();

    void printConversationData();
}
