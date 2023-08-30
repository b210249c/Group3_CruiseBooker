package com.group3.cruisebookingsystem;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import javafx.scene.image.Image;
import javafx.scene.text.TextAlignment;


public class Deck {
    private int selectedDeck;
    private String selectedRoomNumber;
    private ComboBox<Integer> deckCategory;
    private ComboBox<String> roomNumCategory;
    private String roomType;
    private int price;
    private Hyperlink btnBack;
    private ArrayList<RoomTypeDetails> roomTypes = new ArrayList<>();
    private ArrayList<Room> rooms = new ArrayList<>();
    private VBox mainVBox = new VBox();
    private Booking booking;
    private Book1 book1;
    private int index = 1;

    private String place;
    private String duration;
    private String route;
    private String date;
    private String country;
    private String viewpoint;
    private BorderPane leftBorderPane;
    private DatabaseManager database;
    private RoomType rmType;
    private Search search;
    private Login login;
    private CruiseBookingSystem cbs;
    private ArrayList<DeckDetails> deckDetails = new ArrayList<>();
    private Place1 place1;
    public Deck(CruiseBookingSystem cbs, Booking booking, Book1 book1, Search search, RoomType rmType, Place1 place1, Login login) {
        this.cbs = cbs;
        this.booking = booking;
        this.book1 = book1;
        this.search = search;
        this.rmType = rmType;
        this.place1 = place1;
        this.login = login;
    }


    public Scene deckScene() {

        BorderPane borderPane = new BorderPane();
        borderPane.setStyle("-fx-background-color: #E3CAB8;");

        borderPane.setTop(TopNavigationBar());
        borderPane.setLeft(LeftBorderPane());
        borderPane.setCenter(CenterScrollPane());

        return new Scene(borderPane, 1039, 694);
    }

    public BorderPane TopNavigationBar() {

        Button btnLogout = new Button("Logout");
        DropShadow dropShadow = new DropShadow();
        dropShadow.setColor(Color.DARKGRAY);
        dropShadow.setRadius(5);
        dropShadow.setOffsetX(2);
        dropShadow.setOffsetY(2);
        btnLogout.setEffect(dropShadow);

        btnLogout.setStyle("-fx-background-color: #cfd7d9; -fx-background-radius: 25px; -fx-padding: 5px 5px;-fx-text-fill: #0e3641; -fx-font-size: 15px;-fx-font-family: lato;-fx-font-weight: bold;");


        btnLogout.setPrefSize(80, 30);

        btnLogout.setOnMouseEntered(event -> {
            btnLogout.setStyle("-fx-background-color: rgba(0,85,255, 0.8); -fx-border-color: transparent; -fx-background-radius: 25px;-fx-text-fill: #cfd7d9; -fx-padding: 5px 5px; -fx-font-size: 15px;-fx-font-family: lato;-fx-font-weight: bold;");
        });

        btnLogout.setOnMouseExited(event -> {
            btnLogout.setStyle("-fx-background-color: #cfd7d9; -fx-border-color: transparent; -fx-background-radius: 25px;-fx-text-fill: #0e3641; -fx-padding: 5px 5px; -fx-font-size: 15px;-fx-font-family: lato;-fx-font-weight: bold;");
        });
        btnLogout.setOnAction(event -> {
            cbs.switchToMainScene();
        });

        Text welcome = new Text("Welcome, "+login.getUsername());
        welcome.setStyle("-fx-fill: white;");
        welcome.setFont(Font.font("Eras Demi ITC", 20));

        HBox hBox = new HBox(welcome, btnLogout);
        hBox.setSpacing(20);
        hBox.setPadding(new Insets(20));
        hBox.setAlignment(Pos.BASELINE_RIGHT);

        BorderPane borderPane = new BorderPane(hBox);
        borderPane.setStyle("-fx-background-color: rgba(0,0,0,0.3);");

        return borderPane;
    }

    public BorderPane CenterScrollPane(){


        BorderPane borderPane = new BorderPane(ControlVBox());
//        borderPane.setCenter(CenterVBox());
        borderPane.setStyle("-fx-background-color: transparent");

        return borderPane;
    }

    public BorderPane LeftBorderPane(){
        viewpoint = "";

        database = new DatabaseManager();

        try {
            // Get selected button details from the search
            String selectedBtnDetails = search.getSelectedBtnDetails();

            // Prepare and execute the first SQL query
            PreparedStatement preparedStatement1 = database.getConnection().prepareStatement("SELECT duration, place, route, date, country_from FROM cruise_destination WHERE place = ? ");

            preparedStatement1.setString(1, selectedBtnDetails);

            ResultSet resultSet1 = preparedStatement1.executeQuery();

            // Extract data from the result set
            while (resultSet1.next()){
                duration = resultSet1.getString("duration");
                place = resultSet1.getString("place");
                route = resultSet1.getString("route");
                date = resultSet1.getString("date");
                country = resultSet1.getString("country_from");
            }

            String selectedCountry = country;

            // Prepare and execute the second SQL query
            PreparedStatement preparedStatement2 = database.getConnection().prepareStatement("SELECT viewpoint FROM view_point WHERE country_from = ? ");

            preparedStatement2.setString(1, selectedCountry);

            // Extract viewpoint data from the result set
            ResultSet resultSet2 = preparedStatement2.executeQuery();
            while (resultSet2.next()){
                viewpoint += resultSet2.getString("viewpoint") + "\n";
            }

            // Load custom font
            Font customFont = Font.loadFont(getClass().getResourceAsStream("KaiseiOpti-Medium.ttf"), 18);

            // Create Text elements for displaying cruise details
            Text txt1 = new Text(duration + "\n" + place);
            txt1.setWrappingWidth(260);
            txt1.setTextAlignment(TextAlignment.JUSTIFY);
            Text txt2 = new Text("Route: \n" + route + "\n\n" + "Date: " + date);
            txt2.setWrappingWidth(260);
            txt2.setTextAlignment(TextAlignment.JUSTIFY);
            Text txt3 = new Text("Viewpoint: \n\n" + viewpoint);
            txt3.setWrappingWidth(260);
            txt3.setTextAlignment(TextAlignment.JUSTIFY);


            // Apply styling to the Text elements
            txt1.setStyle("-fx-fill: black;-fx-font-size: 12px;-fx-font-family: '" + customFont.getName() + "';");

            txt2.setStyle("-fx-fill: black;-fx-font-size: 12px;-fx-font-family: '" + customFont.getName() + "';");

            txt3.setStyle("-fx-fill: black;-fx-font-size: 12px;-fx-font-family: '" + customFont.getName() + "';");

            // Create a Hyperlink for navigating back to the previous screen
            Hyperlink backLink = new Hyperlink("Back to Choose Room Place" + (book1.getIndex()-1));

            // Define the styles for normal and hover states
            String normalStyle = "-fx-text-fill: #0070E0; -fx-underline: false;-fx-font-family:Lato;-fx-font-size:14px;-fx-font-weight:bold;";
            String hoverStyle = "-fx-text-fill: #00ff05;-fx-underline: true;-fx-font-family:Lato; -fx-font-size:14px;-fx-font-weight:bold;";

            backLink.setStyle(normalStyle);

            // Apply hover styles on mouse enter and reset on mouse exit
            backLink.setOnMouseEntered(event -> backLink.setStyle(hoverStyle));
            backLink.setOnMouseExited(event -> backLink.setStyle(normalStyle));

            // Set action for the Hyperlink to navigate back
            backLink.setOnAction(event -> {
                mainVBox.getChildren().clear();
                place1.setIndex(booking.getRoomNum());
                place1.iterator(place1.getIndex());
                index = 1;
                deckDetails.clear();
                cbs.switchToPlace1Scene();

            });

            // Create separators
            Separator separator1 = new Separator(Orientation.HORIZONTAL);
            Separator separator2 = new Separator(Orientation.HORIZONTAL);

            // Create a VBox to hold the Text elements, Hyperlink, and separators
            VBox vBox = new VBox(txt1, backLink, separator1, txt2, separator2, txt3);
            vBox.setAlignment(Pos.TOP_LEFT);
            vBox.setSpacing(20);

            // Create the BorderPane to hold the VBox
            leftBorderPane = new BorderPane(vBox);
            BorderPane.setMargin(leftBorderPane, new Insets(30,10,40,10));
            leftBorderPane.setStyle("-fx-background-color: white; -fx-background-radius: 20; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.7), 10, 0, 0, 0);");
            BorderPane.setMargin(vBox, new Insets(40, 20, 0, 20));

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return leftBorderPane;
    }

    public VBox ControlVBox(){

        if (index <= booking.getRoomNum()) {

            // Clear the existing content in the main VBox
            mainVBox.getChildren().clear();

            // Add the content of CenterBorderPane to the main VBox
            mainVBox.getChildren().add(CenterBorderPane());
        } else if (index == (booking.getRoomNum()+1)) {

            // Once all rooms are selected, switch to the payment scene
            cbs.switchToPaymentScene();
        }

        // Set the background color of the main VBox to transparent
        mainVBox.setStyle("-fx-background-color: transparent;");

        return mainVBox;
    }


    public BorderPane CenterBorderPane(){
        // Load custom fonts
        Font customFont = Font.loadFont(getClass().getResourceAsStream("EmilysCandy-Regular.ttf"), 18);
        Font customFont1 = Font.loadFont(getClass().getResourceAsStream("KaiseiOpti-Medium.ttf"), 18);

        // Create title text and hyperlink for navigation
        Text txtTitle = new Text("Room " + index + " \n Choose your deck and room number");

        txtTitle.setStyle("-fx-fill: black;-fx-font-size: 25px;-fx-font-family: '" + customFont.getName() + "';-fx-font-weight:bold;");
        txtTitle.setTextAlignment(TextAlignment.CENTER);

        btnBack = new Hyperlink("Back to Select Deck and Room Number");

        // Define the styles for normal and hover states
        String normalStyle = "-fx-text-fill: #0070E0; -fx-underline: false;-fx-font-family:Lato;-fx-font-size:12px;-fx-font-weight:bold;";
        String hoverStyle = "-fx-text-fill: #00ff05;-fx-underline: true;-fx-font-family:Lato; -fx-font-size:12px;-fx-font-weight:bold;";

        btnBack.setStyle(normalStyle);

        btnBack.setOnMouseEntered(event -> btnBack.setStyle(hoverStyle));
        btnBack.setOnMouseExited(event -> btnBack.setStyle(normalStyle));

        VBox titleVBox = new VBox(txtTitle, btnBack);
        titleVBox.setAlignment(Pos.TOP_CENTER);
        titleVBox.setSpacing(10);

        // Handle navigation and disable the back button if on the first room
        if(index == 1) {
            btnBack.setDisable(true);
        }else {
            btnBack.setDisable(false);
            btnBack.setOnAction(event -> {
                index--;
                ControlVBox();
                iterator(index);
            });
        }

        // Create an image view for deck selection
        Image interiorImage = new Image(CruiseBookingSystem.class.getResource("deck.png").toString());
        ImageView interiorView = new ImageView(interiorImage);
        interiorView.setFitWidth(650);
        interiorView.setFitHeight(247);


        // Create and configure UI elements for deck and room selection
        Text txtDeckTitle = new Text("What deck do you prefer?");

        Text txtLevel = new Text("Deck Level: ");
        deckCategory = new ComboBox<>(FXCollections.observableArrayList(
                1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14
        ));
        deckCategory.setPromptText("Select Deck Level");
        HBox hBoxDeck = new HBox(txtLevel, deckCategory);
        hBoxDeck.setSpacing(20);
        hBoxDeck.setAlignment(Pos.CENTER_LEFT);
        deckCategory.setOnAction(event -> {
            selectedDeck = deckCategory.getValue();
            updateRoomComboBoxItems();
        });
        VBox combine1 = new VBox(txtDeckTitle, hBoxDeck);

        Text txtRoomTitle = new Text("Select your room number");
        Text txtRoom = new Text("Room Number: ");
        roomNumCategory = new ComboBox<>();
        roomNumCategory.setPromptText("Select Room Number");
        roomNumCategory.setOnAction(event -> {
            selectedRoomNumber = roomNumCategory.getValue();
        });

        // Set text and styles
        txtDeckTitle.setStyle("-fx-fill: black;-fx-font-size: 16px;-fx-font-family: '" + customFont1.getName() + "';");
        txtLevel.setStyle("-fx-fill: black;-fx-font-size: 14px;-fx-font-family: '" + customFont1.getName() + "';");
        txtRoomTitle.setStyle("-fx-fill: black;-fx-font-size: 16px;-fx-font-family: '" + customFont1.getName() + "';");
        txtRoom.setStyle("-fx-fill: black;-fx-font-size: 14px;-fx-font-family: '" + customFont1.getName() + "';");

        HBox hBoxRoom = new HBox(txtRoom, roomNumCategory);
        hBoxRoom.setSpacing(20);
        hBoxRoom.setAlignment(Pos.CENTER_LEFT);

        //Confirmation button
        Button btnConfirm = new Button("Confirm");
        btnConfirm.setPrefSize(120, 50);
        btnConfirm.setStyle("-fx-background-color: #4F95DA; -fx-border-color: #91C9FF; -fx-border-width: 1;-fx-text-fill:white;-fx-font-family: Lato; -fx-font-weight:bold;-fx-font-size:18px;");
        btnConfirm.setOnMouseEntered(e -> btnConfirm.setStyle("-fx-background-color: transparent; -fx-border-color: #91C9FF; -fx-border-width: 1;-fx-text-fill:#4F95DA;-fx-font-family: Lato; -fx-font-weight:bold;-fx-font-size:18px;"));
        btnConfirm.setOnMouseExited(e -> btnConfirm.setStyle("-fx-background-color: #4F95DA; -fx-border-color: #91C9FF; -fx-border-width: 1;-fx-text-fill:white;-fx-font-family: Lato; -fx-font-weight:bold;-fx-font-size:18px;"));
        btnConfirm.setOnAction(event -> {

            if(deckCategory.getSelectionModel().isEmpty() && roomNumCategory.getSelectionModel().isEmpty()){
                btnConfirm.setDisable(true);
            }else if (deckCategory.getSelectionModel().isEmpty() || roomNumCategory.getSelectionModel().isEmpty()){
                btnConfirm.setDisable(true);
            }else {
                DeckDetails data = new DeckDetails(index, selectedDeck, selectedRoomNumber);
                deckDetails.add(data);
                for (DeckDetails deckDT : deckDetails) {
                    System.out.println(deckDT);
                }
                index++;
                ControlVBox();
            }
            btnConfirm.setDisable(false);
        });
        HBox hBoxBtnConfirm = new HBox(btnConfirm);
        hBoxBtnConfirm.setAlignment(Pos.CENTER_RIGHT);

        // Combine all UI elements into a single VBox
        VBox combine2 = new VBox(txtRoomTitle, hBoxRoom);
        VBox vBoxChoice = new VBox(combine1, combine2);
        vBoxChoice.setSpacing(20);
        vBoxChoice.setStyle("-fx-background-color: white");
        vBoxChoice.setPadding(new Insets(20));
        VBox vBoxImgAndChoice = new VBox(interiorView, vBoxChoice);


        VBox allVBox = new VBox(titleVBox, vBoxImgAndChoice, hBoxBtnConfirm);
        allVBox.setSpacing(20);

        // Set up the final BorderPane
        BorderPane borderPane = new BorderPane(allVBox);
        BorderPane.setMargin(allVBox, new Insets(10,30,10,30));

        return borderPane;
    }

    private void updateRoomComboBoxItems() {
        // Clear existing items in the roomNumCategory ComboBox
        roomNumCategory.getItems().clear();

        // Get the selected deck from the deckCategory ComboBox
        int selectedDeck = deckCategory.getValue();

        // Populate roomNumCategory ComboBox with room numbers
        for (int i = 1; i <= 50; i++) {

            // Use String.format to ensure leading zeroes in the room number
            roomNumCategory.getItems().add(String.format("%02d%02d", selectedDeck, i));
            for(DeckDetails deckDetails1: deckDetails){
                if (deckDetails1.getRoomNumber() == selectedRoomNumber){
                    roomNumCategory.getItems().remove(selectedRoomNumber);
                }

                database = new DatabaseManager();
                try {
                    PreparedStatement preparedStatement1 = database.getConnection().prepareStatement("SELECT roomNum FROM user_payment_detail");

                    ResultSet resultSet1 = preparedStatement1.executeQuery();

                    while (resultSet1.next()){
                        String roomNum = resultSet1.getString("roomNum");
                        roomNumCategory.getItems().remove(roomNum);
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }

            }

        }
    }

    public void iterator(int index){
        Iterator<DeckDetails> iterator = deckDetails.iterator();
        while (iterator.hasNext()) {
            DeckDetails deck = iterator.next();
            if (deck.getIndex() == index || deck.getIndex() == index + 1) {
                iterator.remove();
            }
        }
    }

    public ArrayList<DeckDetails> getDeckDetails() {
        return deckDetails;
    }
}