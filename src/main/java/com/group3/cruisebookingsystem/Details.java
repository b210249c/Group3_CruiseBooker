package com.group3.cruisebookingsystem;

import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.awt.*;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Details {
    private CruiseBookingSystem cbs;
    private Search search;
    private String place;
    private String duration;
    private String route;
    private String date;
    private String country;
    private String viewpoint;
    private String cruise_ship;
    private Blob image;
    private int price;
    private Login login;
    private BorderPane leftBorderPane;
    private BorderPane bottomBorderPane;
    private BorderPane centerBorderPane;
    private DatabaseManager database;
    public Details(CruiseBookingSystem cbs, Search search, Login login) {
        this.cbs = cbs;
        this.search = search;
        this.login = login;
    }

    // Create the scene for displaying cruise details
    public Scene detailScene() {

        BorderPane mainBorderPane = new BorderPane();
        mainBorderPane.setStyle("-fx-background-color: lightblue;");

        mainBorderPane.setTop(TopNavigationBar());
        mainBorderPane.setCenter(Center());

        return new Scene(mainBorderPane, 1039, 694);
    }

    // Create the top navigation bar with welcome message and logout button
    public BorderPane TopNavigationBar(){

        Button btnLogout = new Button("Logout");
        btnLogout.setOnAction(event -> {
            cbs.switchToMainScene();
        });

        Text welcome = new Text("Welcome, "+login.getUsername());
        welcome.setFont(Font.font("Eras Demi ITC", 20));

        HBox hBox = new HBox(welcome, btnLogout);
        hBox.setSpacing(20);
        hBox.setPadding(new Insets(20));
        hBox.setAlignment(Pos.BASELINE_RIGHT);

        BorderPane topBorderPane = new BorderPane(hBox);
        topBorderPane.setStyle("-fx-background-color: white;");

        return topBorderPane;
    }

    public BorderPane LeftBorderPane(){
        viewpoint = "";

        database = new DatabaseManager();

        try {

            // Retrieve selected cruise details
            String selectedBtnDetails = search.getSelectedBtnDetails();

            PreparedStatement preparedStatement1 = database.getConnection().prepareStatement("SELECT duration, place, route, date, country_from FROM cruise_destination WHERE place = ? ");

            preparedStatement1.setString(1, selectedBtnDetails);

            ResultSet resultSet1 = preparedStatement1.executeQuery();
            while (resultSet1.next()){
                duration = resultSet1.getString("duration");
                place = resultSet1.getString("place");
                route = resultSet1.getString("route");
                date = resultSet1.getString("date");
                country = resultSet1.getString("country_from");
            }

            // Retrieve viewpoint information for the selected country
            String selectedCountry = country;

            PreparedStatement preparedStatement2 = database.getConnection().prepareStatement("SELECT viewpoint FROM view_point WHERE country_from = ? ");

            preparedStatement2.setString(1, selectedCountry);

            ResultSet resultSet2 = preparedStatement2.executeQuery();
            while (resultSet2.next()){
                // Concatenate viewpoint information
                viewpoint += resultSet2.getString("viewpoint") + "\n";
            }

            // Create and configure text elements for display
            Text txt1 = new Text(duration + "\n" + place);
            txt1.setWrappingWidth(260);
            txt1.setTextAlignment(TextAlignment.JUSTIFY);
            Text txt2 = new Text("Route: \n" + route + "\n\n" + "Date: " + date);
            txt2.setWrappingWidth(260);
            txt2.setTextAlignment(TextAlignment.JUSTIFY);

            Text txt3 = new Text("Viewpoint: \n\n" + viewpoint);
            txt3.setWrappingWidth(260);
            txt3.setTextAlignment(TextAlignment.JUSTIFY);

            // Create a hyperlink to return to search result
            Hyperlink backLink = new Hyperlink("Back to Search Result");
            backLink.setOnAction(event -> {
                cbs.switchToSearchScene();
            });

            // Create separators for layout
            Separator separator1 = new Separator(Orientation.HORIZONTAL);
            Separator separator2 = new Separator(Orientation.HORIZONTAL);

            // Create a VBox to contain text elements
            VBox vBox = new VBox(txt1, backLink, separator1, txt2, separator2, txt3);
            vBox.setAlignment(Pos.TOP_LEFT);
            vBox.setSpacing(20);

            // Create and configure the left BorderPane
            leftBorderPane = new BorderPane(vBox);
            BorderPane.setMargin(leftBorderPane, new Insets(30));
            leftBorderPane.setStyle("-fx-background-color: white; -fx-background-radius: 20; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.7), 10, 0, 0, 0);");

            // Set additional margin for the VBox
            BorderPane.setMargin(vBox, new Insets(40, 20, 0, 20));

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return leftBorderPane;
    }

    public ScrollPane Center(){

        BorderPane borderPane = new BorderPane();

        borderPane.setLeft(LeftBorderPane());
        borderPane.setCenter(CenterBorderPane());
        borderPane.setStyle("-fx-background-color: #E3CAB8;");

        // Create a ScrollPane to contain the BorderPane
        ScrollPane scrollPane = new ScrollPane(borderPane);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);

        return scrollPane;
    }

    public BorderPane CenterBorderPane(){

            BorderPane borderPane = new BorderPane();
            BorderPane.setMargin(borderPane, new Insets(30,30,30,0));
            borderPane.setTop(CenterTop());
            Text txt = new Text(place);
            txt.setFont(Font.font("Eras Demi ITC", 18));
            HBox hBox = new HBox(txt);
            hBox.setAlignment(Pos.BASELINE_LEFT);
            BorderPane.setMargin(hBox, new Insets(0,0,10,0));
            borderPane.setCenter(hBox);
            borderPane.setBottom(CenterBottom());


        return borderPane;
    }

    public BorderPane CenterTop(){

        database = new DatabaseManager();

        try {
            String selectedBtnDetails = search.getSelectedBtnDetails();

            PreparedStatement preparedStatement1 = database.getConnection().prepareStatement("SELECT cruise_ship FROM cruise_destination WHERE place = ? ");

            preparedStatement1.setString(1, selectedBtnDetails);

            ResultSet resultSet1 = preparedStatement1.executeQuery();
            while (resultSet1.next()){
                cruise_ship = resultSet1.getString("cruise_ship");
            }


            Text txt1 = new Text("YOU\'RE FEELING ADVENTUROUS");
            txt1.setFont(Font.font("Eras Demi ITC", 40));
            txt1.setWrappingWidth(350);
            txt1.setTextAlignment(TextAlignment.LEFT);

            Text txt2 = new Text("We like that! Let's get you onboard\n" + cruise_ship);
            txt2.setTextAlignment(TextAlignment.LEFT);

            VBox vBox = new VBox(txt1, txt2);
            vBox.setAlignment(Pos.CENTER_LEFT);
            vBox.setSpacing(20);
            centerBorderPane = new BorderPane();
            centerBorderPane.setLeft(vBox);
            BorderPane.setMargin(centerBorderPane, new Insets(0,0,30,0));
            centerBorderPane.setStyle("-fx-background-color: white; -fx-background-radius: 20; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.7), 10, 0, 0, 0);");
            BorderPane.setMargin(vBox, new Insets(50));

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }



        return centerBorderPane;
    }

    // Create the center bottom section with pricing information and a Continue button
    public BorderPane CenterBottom(){
        database = new DatabaseManager();

        try {
            String selectedBtnDetails = search.getSelectedBtnDetails();

            PreparedStatement preparedStatement1 = database.getConnection().prepareStatement("SELECT price FROM cruise_destination WHERE place = ? ");

            preparedStatement1.setString(1, selectedBtnDetails);

            ResultSet resultSet1 = preparedStatement1.executeQuery();
            while (resultSet1.next()){
                price = resultSet1.getInt("price");
            }

            // Create and configure text elements
            Text txt1 = new Text("Good Choice! Proceed to Choose Your Room");
            Text txt2 = new Text("Taxes 6% per total charge");
            Text txt3 = new Text("Kids free!");


            VBox vBox = new VBox(txt1, txt2, txt3);
            vBox.setAlignment(Pos.CENTER_LEFT);
            vBox.setSpacing(20);

            Text txt4 = new Text("From RM* /Person");
            Text txt5 = new Text("RM" + price);

            VBox vBox1 = new VBox(txt4, txt5);
            vBox1.setAlignment(Pos.CENTER_RIGHT);
            vBox1.setSpacing(10);

            // Create a separator and a Continue button
            Separator separator = new Separator(Orientation.HORIZONTAL);

            // Create the button
            Button btnContinue = new Button("Continue");
            btnContinue.setPrefSize(140, 40);
            btnContinue.setStyle("-fx-background-color: transparent; -fx-border-color: #60442a; -fx-border-width: 1;-fx-text-fill:#60442a;-fx-font-family: Lato; -fx-font-weight:bold;-fx-font-size:18px;");
            btnContinue.setOnMouseEntered(e -> btnContinue.setStyle("-fx-background-color: #60442a; -fx-border-color: #60442a; -fx-border-width: 1;-fx-text-fill:white;-fx-font-family: Lato; -fx-font-weight:bold;-fx-font-size:18px;"));
            btnContinue.setOnMouseExited(e -> btnContinue.setStyle("-fx-background-color: transparent; -fx-border-color: #60442a; -fx-border-width: 1;-fx-text-fill:#60442a;-fx-font-family: Lato; -fx-font-weight:bold;-fx-font-size:18px;"));

            btnContinue.setOnAction(event -> {
                cbs.switchToBookingScene();
            });

            // Create a VBox to hold the separator and the button
            VBox vBox2 = new VBox(separator, btnContinue);
            vBox2.setAlignment(Pos.CENTER_RIGHT);
            vBox2.setSpacing(30);
            vBox2.setPadding(new Insets(0, 50, 50, 50));

            // Create and configure the bottom BorderPane
            bottomBorderPane = new BorderPane();
            bottomBorderPane.setLeft(vBox);
            bottomBorderPane.setRight(vBox1);
            bottomBorderPane.setBottom(vBox2);
            bottomBorderPane.setStyle("-fx-background-color: white; -fx-background-radius: 20; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.7), 10, 0, 0, 0);");
            BorderPane.setMargin(vBox, new Insets(50, 50, 30, 50));
            BorderPane.setMargin(vBox1, new Insets(50, 50, 30, 50));

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return bottomBorderPane;
    }
}


