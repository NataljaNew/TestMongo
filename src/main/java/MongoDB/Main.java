package MongoDB;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;

import java.sql.SQLOutput;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
//        Service service = new Service();
        Scanner sc = new Scanner(System.in);
        do {
            System.out.println("___________________________");
            System.out.println("|    [1] register           |");
            System.out.println("|    [2] send money         |");
            System.out.println("|    [3] exit               |");
            System.out.println("_____________________________");
            System.out.println("Please enter [1],[2],[3]:");
            String input = sc.next();
            switch (input) {
                case "1" -> {
                    System.out.println("*** Registration ***");
                    System.out.println("Enter your name");
                    String name = sc.next();
                    System.out.println("Enter your surname:");
                    String surname = sc.next();
                    double balance = 0;
                    try {
                            System.out.println("Enter your balance:");
                            balance = sc.nextDouble();
                    } catch (Exception e) {
                            System.out.println("Something went wrong, try again.");
                    }
                    System.out.println("Enter your passport ID number:");
                    String uniqueID = sc.next();
                    Client client = new Client(uniqueID,name,surname,balance);
                    try {
                        MongoClient client1 = MongoClientProvider.getClient();
                        MongoDatabase database = client1.getDatabase("Bank_DB");
                        MongoCollection<Client> collection = database.getCollection("Clients", Client.class);
                        collection.insertOne(client);
                        client1.close();
                    } catch (Exception e) {
                        System.out.println("Something went wrong try again.");
                    }
                }
                case "2" -> {
                    System.out.println("Enter your passport ID number:");
                    String id = sc.next();
                    MongoClient client = MongoClientProvider.getClient();
                    MongoDatabase database = client.getDatabase("Bank_DB");
                    MongoCollection<Client> collection = database.getCollection("Clients", Client.class);
                    Iterator<Client> iterator = collection.find().iterator();
                    try {
                        iterator = collection.find(Filters.eq("uniqueId", id)).iterator();
                        if( iterator.hasNext()){
                            System.out.println("This Client ID is found, enter the amount you would like to send:");
                            Double sum = sc.nextDouble();
                            collection.updateOne(Filters.eq("uniqueId", id),Updates.set("balance", sum));
                            System.out.println("The amount" + sum + "was send.");
                        } else {
                            System.out.println("This ID is not found. Please press [1] from menu to register, or [2] to try again.");
                        }

                    } catch (Exception e) {
                        System.out.println("Something went wrong, try again.");
                    }
                }
                case "3" -> {
                    System.exit(0);
                }
                default -> System.out.println("something went wrong, try again.");
            }
        } while (true);

    }
}
