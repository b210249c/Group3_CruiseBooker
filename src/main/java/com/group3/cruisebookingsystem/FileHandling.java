package com.group3.cruisebookingsystem;

import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class FileHandling {
    static DatabaseManager database;
    static String list="";
    static  String malaysialist ="";
    static String sgList="";
    static String payList="";


    public static void main(String[] args) {
        // Variables to store cruise data
        String country_from = "";
        String duration = "";
        String place = "";
        String cruise_ship = "";
        String route = "";
        String date = "";
        int price =0;
        String pricetext= "";
        String Malaysia ="Malaysia";
        database = new DatabaseManager();

        // Variables to store payment data
        String email ="";
        String room="";
        int adultNum =0;
        int kidNum = 0;
        String roomType = "";
        String roomPlace = "";
        int deckLevel = 0;
        int roomNum =0;
        int payPrice = 0;
        int tax=0;
        int totalPrice=0;
        String payPlace="";


        try {
            // Create a statement to execute a query on the database
            Statement sgstatement = database.getConnection().createStatement();

            // Execute a query to retrieve cruise data for Singapore
            ResultSet sgSet = sgstatement.executeQuery("SELECT country_from, duration, place, cruise_ship, route, price, date FROM cruise_destination WHERE country_from = 'Singapore';");

            // Loop through the result set to process each row of data
            while (sgSet.next()) {
                // Extract cruise data from the result set
                country_from = sgSet.getString("country_from").toString();
                duration = sgSet.getString("duration").toString();
                place = sgSet.getString("place").toString();
                cruise_ship = sgSet.getString("cruise_ship").toString();
                route = sgSet.getString("route").toString();
                price = sgSet.getInt("price");
                pricetext = String.valueOf(price);
                date = sgSet.getString("date");

                // Format the cruise data and add to the Singapore list
                sgList += "Country from , Duration , Place , Cruise ship , Route , Price ,date " + "\n"
                        + country_from + " , " + duration + " , " + place + " , " + cruise_ship + " , " + route + " , " + pricetext + " , " + date + "\n\n";

                try {
                    // Write the formatted data to a file
                    PrintWriter sgWriter = new PrintWriter("singaporeDestination.txt");
                    sgWriter.println(sgList);
                    sgWriter.close();

                    // Print a message indicating successful file writing
                    System.out.println("Singapore data separated and written to file.");
                } catch (Exception e) {
                    // Handle errors related to file writing
                    System.out.println("An error occurred: " + e.getMessage());
                }
            }
        } catch (Exception e) {
            // Handle exceptions related to database query and processing
            System.out.println(e.getMessage());
        }

        try {
            // Create a statement to execute a query on the database
            Statement statement = database.getConnection().createStatement();

            // Execute a query to retrieve payment details
            ResultSet resultSet = statement.executeQuery("SELECT email, room, adultNum, kidNum, roomType, roomPrice, deckLevel, roomNum, price, tax, totalPrice, place FROM user_payment_detail");

            // Loop through the result set to process each payment record
            while (resultSet.next()) {
                // Extract payment data from the result set
                email = resultSet.getString("email").toString();
                room = resultSet.getString("room").toString();
                place = resultSet.getString("place").toString();
                cruise_ship = resultSet.getString("cruise_ship").toString();
                route = resultSet.getString("route").toString();
                price = resultSet.getInt("price");
                pricetext = String.valueOf(price);
                date = resultSet.getString("date");

                // Format the payment data and add to the 'All Destination' list
                list += "Country from , Duration , Place , Cruise ship , Route , Price ,date " + "\n"
                        + country_from + " , " + duration + " , " + place + " , " + cruise_ship + " , " + route + " , " + pricetext + " , " + date + "\n\n";

                try {
                    // Write the formatted payment data to a file
                    PrintWriter allWriter = new PrintWriter("AllDestination.txt");
                    allWriter.println(list);
                    allWriter.close();

                    // Print a message indicating successful file writing
                    System.out.println("Payment data separated and written to file.");
                } catch (Exception e) {
                    // Handle errors related to file writing
                    System.out.println("An error occurred: " + e.getMessage());
                }
            }
        } catch (Exception e) {
            // Handle exceptions related to database query and processing
            System.out.println(e.getMessage());
        }


        try {
            // Create a statement to execute a query on the database
            Statement malaysiastatement = database.getConnection().createStatement();

            // Execute a query to retrieve cruise data for Malaysia
            ResultSet malaysiaSet = malaysiastatement.executeQuery("SELECT country_from, duration, place, cruise_ship, route, price, date FROM cruise_destination WHERE country_from = 'Malaysia'");

            // Loop through the result set to process each Malaysia cruise record
            while (malaysiaSet.next()) {
                // Extract Malaysia cruise data from the result set
                country_from = malaysiaSet.getString("country_from").toString();
                duration = malaysiaSet.getString("duration").toString();
                place = malaysiaSet.getString("place").toString();
                cruise_ship = malaysiaSet.getString("cruise_ship").toString();
                route = malaysiaSet.getString("route").toString();
                price = malaysiaSet.getInt("price");
                pricetext = String.valueOf(price);
                date = malaysiaSet.getString("date");

                // Format the Malaysia cruise data and add to the 'Malaysia Destination' list
                malaysialist += "Country from , Duration , Place , Cruise ship , Route , Price ,date " + "\n"
                        + country_from + " , " + duration + " , " + place + " , " + cruise_ship + " , " + route + " , " + pricetext + " , " + date + "\n\n";

                try {
                    // Write the formatted Malaysia cruise data to a file
                    PrintWriter malaysiaWriter = new PrintWriter("malaysiaDestination.txt");
                    malaysiaWriter.println(malaysialist);
                    malaysiaWriter.close();

                    // Print a message indicating successful file writing
                    System.out.println("Malaysia data separated and written to file.");
                } catch (Exception e) {
                    // Handle errors related to file writing
                    System.err.println("An error occurred: " + e.getMessage());
                }
            }
        } catch (Exception e) {
            // Handle exceptions related to database query and processing
            System.out.println(e.getMessage());
        }


        try {
            // Create a statement to execute a query on the database
            Statement paymentstatement = database.getConnection().createStatement();

            // Execute a query to retrieve payment details
            ResultSet paymentSet = paymentstatement.executeQuery("SELECT email, room, adultNum, kidNum, roomType, roomPlace, deckLevel, roomNum, price, tax, totalPrice, place FROM user_payment_detail");

            // Loop through the result set to process each payment record
            while (paymentSet.next()) {
                // Extract payment data from the result set
                email = paymentSet.getString("email").toString();
                room = paymentSet.getString("room").toString();
                adultNum = paymentSet.getInt("adultNum");
                kidNum = paymentSet.getInt("kidNum");
                roomType = paymentSet.getString("roomType").toString();
                roomPlace = paymentSet.getString("roomPlace").toString();
                deckLevel = paymentSet.getInt("deckLevel");
                roomNum = paymentSet.getInt("roomNum");
                payPrice = paymentSet.getInt("price");
                tax = paymentSet.getInt("tax");
                totalPrice = paymentSet.getInt("totalPrice");
                payPlace = paymentSet.getString("place").toString();

                // Format payment data and add to the 'Payment' list
                payList += "Payment : email,room,adultNum,kidNum,roomType,roomPlace,deckLevel,roomNum,payPrice,tax,totalPrice,payPlace\n"
                        + email + " , " + room + " , " + adultNum + " , " + kidNum + " , " + roomType + " , " + roomPlace + " , " + deckLevel + " , " + roomNum + " , " + payPrice + " , " + tax + " , " + totalPrice + " , " + payPlace + "\n\n";

                try {
                    // Write the formatted payment data to a file
                    PrintWriter payWriter = new PrintWriter("payment.txt");
                    payWriter.println(payList);
                    payWriter.close();

                    // Print a message indicating successful file writing
                    System.out.println("Payment data separated and written to file.");
                } catch (Exception e) {
                    // Handle errors related to file writing
                    System.err.println("An error occurred: " + e.getMessage());
                }
            }
        } catch (Exception e) {
            // Handle exceptions related to database query and processing
            System.out.println(e.getMessage());
        }

    }




}
