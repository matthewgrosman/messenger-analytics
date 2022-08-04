import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.IndexResponse;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;

import java.io.IOException;
import java.util.ArrayList;

public class ElasticsearchWriter {
    private static ElasticsearchTransport transport;
    private static ElasticsearchClient client;

    public static void writeMessageDataDocuments(ArrayList<MessageDataDocument> messageDataDocuments) throws IOException {
        for (MessageDataDocument document : messageDataDocuments) {
            // Write the document.getMessageDataDocument() to Elasticsearch.
        }
    }

    /**
     * Creates a ElasticsearchClient and stores it as a static class variable. This client is used to interact
     * with the Elasticsearch API and send documents to our Elasticsearch cluster.
     */
    public static void getElasticsearchConnection() throws IOException {
        // Create a low-level client.
//        RestClient restClient = RestClient.builder(
//                new HttpHost(ConversationParserConstants.ES_HOST_NAME, ConversationParserConstants.ES_PORT)).build();

        final CredentialsProvider credentialsProvider =
                new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY,
                new UsernamePasswordCredentials("elastic", "none"));

        RestClientBuilder builder = RestClient.builder(
                new HttpHost("localhost", 9200))
                .setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback() {
                    @Override
                    public HttpAsyncClientBuilder customizeHttpClient(
                            HttpAsyncClientBuilder httpClientBuilder) {
                        return httpClientBuilder
                                .setDefaultCredentialsProvider(credentialsProvider);
                    }
                });

        // Create an ElasticsearchTransport and ElasticsearchClient to connect to the
        // Elasticsearch cluster. The transport holds the network resources responsible for
        // connecting to the cluster, while the client is a wrapper that wraps the transport
        // and provides us with an API to use to interact with our ES cluster. We save both of
        // these as we need to use the client to interact with the API and we need to close the
        // transport object to close our connection to the ES cluster- so we store both of these
        // as private static class variables to use later.
        transport = new RestClientTransport(builder.build(), new JacksonJsonpMapper());
        client = new ElasticsearchClient(transport);
        System.out.println("Connection established to: " + client);

        System.out.println(client.cluster().health());
    }

    /**
     * Closes the connection to the Elasticsearch cluster.
     * @throws IOException  Calling "transport.close()" may throw an IOException.
     */
    public static void closeElasticsearchConnection() throws IOException {
        // In order to close the connection to the Elasticsearch cluster we must close the transport.
        // The transport (not the client, which is simply a wrapper for the transport) holds all the
        // network resources, so we close that object to end the connection.
        transport.close();
    }

    public static void testWrite() throws IOException {
        IndexResponse response = client.index(i -> i
        .index("newindex")
        .document(new MessageDataDocument("hi","hello","yo",42432434, "howdy")));

        System.out.println(response);
    }
}
