import java.io.IOException;
import java.util.ArrayList;

public class ElasticsearchWriter {
    public static void writeMessageDataDocuments(ArrayList<MessageDataDocument> messageDataDocuments) throws IOException {
        for (MessageDataDocument document : messageDataDocuments) {
            System.out.println(document.getConversationName());
            System.out.println(document.getSenderName());
            System.out.println(document.getDate());
            System.out.println(document.getGroupChat());
            System.out.println(document.getContent());
            System.out.println("------------------------------\n");
        }
    }
}
