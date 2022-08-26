package parser;

import com.fasterxml.jackson.databind.JsonNode;

public class ConversationParserUtil {
    /**
     * Checks if a JsonNode is not null and contains a certain field name. If this
     * is the case, return the field value for the given field name- and if there
     * is some issue (node is null or field name does not exist), return null.
     *
     * @param node      A JsonNode that we are checking safety on
     * @param fieldName The field name we want to check exists in the node's fields.
     * @return          A JsonNode that either contains the field value for the field
     *                  name if it is valid, or a null value if otherwise.
     */
    public static JsonNode isSafeAndGet(JsonNode node, String fieldName) {
        if (node != null && node.has(fieldName)) {
            return node.get(fieldName);
        }
        return null;
    }
}
