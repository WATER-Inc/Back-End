package dao.mongodb;

import com.mongodb.client.*;
import org.bson.Document;

public class MongoDBExample {
    public static void main(String[] args) {
        // Configure the connection string
        String connectionString = "mongodb://localhost:27017/WATER";

        // Create a MongoDB client
        try (MongoClient mongoClient = MongoClients.create(connectionString)) {

            // Connect to the database
// Connect to the "WATER" database
            MongoDatabase database = mongoClient.getDatabase("WATER");

            // Access the "messages" cluster
            MongoCollection<Document> messagesCollection = database.getCollection("messages");
            FindIterable<Document> documents = messagesCollection.find();

            // Print each document to the console
            for (Document document : documents) {
                System.out.println(document.toJson());
            }
            // Perform operations on the database
            // ...

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}