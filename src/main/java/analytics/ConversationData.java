/**
 * The reason we are using an interface here is to cut down on excess
 * duplicate code. SingleConversationData and AggregatedConversationData
 * were originally just their own classes with no implementing an interface.
 * The only real difference between the two classes is that the Aggregated
 * version contains an extra HashMap to hold an additional analytic. This
 * meant that the SingleConversationMongoDB  and AggregatedConversationMongoDB
 * classes had a lot of duplicate code as they did nearly the same thing
 * except an extra metric.
 *
 * Using an interface allowed me to condense a lot of the functions into the
 * AnalyticsUtil class, where the functions now take in an implementation of the
 * ConversationData, and perform the task regardless of if the argument is
 * a SingleConversationData object or a AggregatedConversationData object.
 */

package analytics;

import java.util.HashMap;

public interface ConversationData {
    HashMap<String, Integer> messagesPerPerson = new HashMap<>();
    HashMap<String, Integer> messagesPerMonth = new HashMap<>();
    HashMap<String, Integer> messagesPerWeekday = new HashMap<>();
    HashMap<String, Integer> messagesPerHour = new HashMap<>();

    void printConversationData();
}
