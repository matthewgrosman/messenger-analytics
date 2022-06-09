import com.fasterxml.jackson.databind.JsonNode;

public class ConversationParserUtil {
    /**
     * Checks if a JsonNode is not null and contains a certain field name. This gives
     * assurance that we can call someNode.get(fieldName) without a NullPointerException.
     *
     * @param node      A JsonNode that we are checking safety on
     * @param fieldName The field name we want to check exists in the node's fields.
     * @return          A boolean that determines if the field name exists and it is safe
     *                  to access that field.
     */
    public static boolean isSafeToAccess(JsonNode node, String fieldName) {
        return !node.isNull() && node.has(fieldName);
    }
}
