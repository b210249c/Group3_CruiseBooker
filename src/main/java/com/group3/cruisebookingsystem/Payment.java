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


public class Payment {
    private Login login;
    private Button btnConfirm;
    private int price;
    private ArrayList<RoomTypeDetails> roomTypes = new ArrayList<>();
    private ArrayList<Room> rooms = new ArrayList<>();
    private VBox mainVBox = new VBox();
    private Booking booking;
    private Book1 book1;
    private int index = 1;
    private Deck deck;
    private String place;
    private String duration;
    private String route;
    private String date;
    private String country;
    private DatabaseManager database;
    private RoomType rmType;
    private Search search;
    private Place1 place1;
    private CruiseBookingSystem cbs;
    private ArrayList<DeckDetails> deckDetails = new ArrayList<>();
    private ArrayList<PlaceDetails> placeDetails = new ArrayList<>();
    public Payment(CruiseBookingSystem cbs, Booking booking, Book1 book1, Search search, RoomType rmType, Place1 place1, Deck deck, Login login) {
        this.cbs = cbs;
        this.booking = booking;
        this.book1 = book1;
        this.search = search;
        this.rmType = rmType;
        this.place1 = place1;
        this.deck = deck;
        this.login = login;
    }


    public Scene paymentScene() {

        BorderPane borderPane = new BorderPane();
        Image image = new Image(CruiseBookingSystem.class.getResource("payment1.png").toString(), 1068,728,false,true);
        BackgroundImage bI = new BackgroundImage(image,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        borderPane.setBackground(new Background(bI));


        borderPane.setTop(TopNavigationBar());
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


        ScrollPane scrollPane = new ScrollPane(ControlVBox());
//        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: transparent");

        BorderPane borderPane = new BorderPane(scrollPane);
        borderPane.setCenter(scrollPane);

        btnConfirm = new Button("Confirm");

        btnConfirm.setStyle("-fx-background-color: transparent; -fx-border-color: #91C9FF; -fx-border-width: 1;-fx-text-fill:#4F95DA;-fx-font-family: Lato; -fx-font-weight:bold;-fx-font-size:13px;");
        btnConfirm.setOnMouseEntered(e -> btnConfirm.setStyle("-fx-background-color: #4F95DA; -fx-border-color: #91C9FF; -fx-border-width: 1;-fx-text-fill:white;-fx-font-family: Lato; -fx-font-weight:bold;-fx-font-size:13px;"));
        btnConfirm.setOnMouseExited(e -> btnConfirm.setStyle("-fx-background-color: transparent; -fx-border-color: #91C9FF; -fx-border-width: 1;-fx-text-fill:#4F95DA;-fx-font-family: Lato; -fx-font-weight:bold;-fx-font-size:13px;"));

        btnConfirm.setOnAction(event -> {
            addAll();
            rooms.clear();
            roomTypes.clear();
            deckDetails.clear();
            placeDetails.clear();
            cbs.switchToHomeScene();
        });

        HBox hBoxBtnConfirm = new HBox(btnConfirm);
        hBoxBtnConfirm.setAlignment(Pos.CENTER_RIGHT);
        BorderPane.setMargin(hBoxBtnConfirm, new Insets(10));

        borderPane.setBottom(hBoxBtnConfirm);
        borderPane.setStyle("-fx-background-color: white; -fx-background-radius: 20; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.7), 10, 0, 0, 0);");
        BorderPane.setMargin(borderPane, new Insets(30,100,30,100));
        BorderPane.setMargin(scrollPane, new Insets(10,0,10,0));

        return borderPane;
    }



    public VBox ControlVBox(){

        if (index <= booking.getRoomNum()) {
            mainVBox.getChildren().clear();
            mainVBox.getChildren().add(CenterBorderPane());
        } else if (index == (booking.getRoomNum()+1)) {

            cbs.switchToHomeScene();
        }
        mainVBox.setStyle("-fx-background-color: transparent;");

        return mainVBox;
    }


    public BorderPane CenterBorderPane(){

        // Load fonts
        Font customFont = Font.loadFont(getClass().getResourceAsStream("EmilysCandy-Regular.ttf"), 18);
        Font customFont1 = Font.loadFont(getClass().getResourceAsStream("KaiseiOpti-Medium.ttf"), 18);
        Font customFont2 = Font.loadFont(getClass().getResourceAsStream("Righteous-Regular.ttf"), 18);

        database = new DatabaseManager();

        // Create the title text
        Text txtTitle = new Text("Payment Details");
        txtTitle.setStyle("-fx-font-family: '" + customFont2.getName() + "';-fx-font-size: 35px;");

        txtTitle.setTextAlignment(TextAlignment.CENTER);
        VBox titleVBox = new VBox(txtTitle);
        titleVBox.setAlignment(Pos.TOP_CENTER);


        try {
            String selectedBtnDetails = search.getSelectedBtnDetails();

            // Fetch selected experience details from the database
            PreparedStatement preparedStatement1 = database.getConnection().prepareStatement("SELECT duration, place, route, date, country_from, price FROM cruise_destination WHERE place = ? ");

            preparedStatement1.setString(1, selectedBtnDetails);

            ResultSet resultSet1 = preparedStatement1.executeQuery();
            while (resultSet1.next()){
                duration = resultSet1.getString("duration");
                place = resultSet1.getString("place");
                route = resultSet1.getString("route");
                date = resultSet1.getString("date");
                country = resultSet1.getString("country_from");
                price = resultSet1.getInt("price");
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        // Create text elements to display selected experience details
        Text selectedExperience = new Text("Place: " + place + "\nDuration: " + duration + "\nDate: " + date + "\nDeparture Country: " + country + "\nRoute: " + route);

        selectedExperience.setStyle("-fx-fill: white;-fx-font-size: 20px;-fx-font-family: '" + customFont.getName() + "';-fx-font-weight:bold;");

        VBox vBox = new VBox(selectedExperience);
        vBox.setStyle("-fx-background-color: #3c362a");
        vBox.setPadding(new Insets(20));

        // Initialize variables to store pricing and details
        int totalAdultPrice = 0;
        int totalRoomPrice = 0;
        String roomDetails = "";
        String roomPlaceDetails = "";
        String deckExpDetails = "";
        String peopleRoomDetails = "";

        // Create text elements to display pricing and details
        Text details1 = new Text("You have chosen " + booking.getRoomNum() + " room.");

        details1.setStyle("-fx-fill: black;-fx-font-size: 16px;-fx-font-family: '" + customFont1.getName() + "';");

        ArrayList<Room> rooms1 = book1.getRooms();
        ArrayList<RoomTypeDetails> roomTypes = rmType.getRoomTypes();
        ArrayList<PlaceDetails> placeDTs = place1.getPlaceDetails();
        ArrayList<DeckDetails> deckDTs = deck.getDeckDetails();

        for (int i = 0; i < rooms1.size(); i++) {
            Room rmDetail = rooms1.get(i);
            peopleRoomDetails += "Room " + (i + 1) + " : " + rmDetail.getAdultNum() + " Adult/Adults and " + rmDetail.getKidNum() + " Kid/Kids.\nTotal price for Room " + (i+1) + " is RM " + (rmDetail.getAdultNum() * price) + ".\n";
            totalAdultPrice += rmDetail.getAdultNum() * price;

            RoomTypeDetails roomTypeDetail = roomTypes.get(i);
            roomDetails += "Room " + (i + 1) + " : \n" +
                    "Room Type: " + roomTypeDetail.getRoomType() +
                    "\nPrice for " + roomTypeDetail.getRoomType() + " : RM " + roomTypeDetail.getPrice() + "\n";
            if(roomTypeDetail.getRoomType() == "Interior"){
                totalRoomPrice += roomTypeDetail.getPrice();
            }else {
                totalRoomPrice += roomTypeDetail.getPrice();
            }

            PlaceDetails placeDT = placeDTs.get(i);
            roomPlaceDetails += "Room " + (i + 1) + " : You have chosen the " + placeDT.getSelectedPlace() + "\n";

            DeckDetails deckDetail = deckDTs.get(i);
            deckExpDetails += "Room " + (i + 1) + " : You have chosen level " + deckDetail.getDeckLevel() + " deck and room number " + deckDetail.getRoomNumber() + "\n";

        }

        Text roomPlaceExp = new Text(roomPlaceDetails);

        Text totalPeoplePerRoom = new Text(peopleRoomDetails);


        Text details2 = new Text("Total Price (Not Included Room Type Price): RM " + totalAdultPrice);




        Text details3 = new Text(roomDetails);

        Text details4 = new Text("Total Price (Not Included Total People Price): RM " + totalRoomPrice);





        Text deckExperience = new Text(deckExpDetails);

        Text details5 = new Text("Total Price (Not Included Taxes and Fees): RM " + (totalAdultPrice + totalRoomPrice));

        Text details6 = new Text("Taxes and Fees: RM " + (String.format("%.2f", (totalAdultPrice + totalRoomPrice) * 0.06)));

        Text details7 = new Text("Total Price (Include Taxes and Fees): RM " + (String.format("%.2f", (totalAdultPrice + totalRoomPrice) + (totalAdultPrice + totalRoomPrice) * 0.06)));

        roomPlaceExp.setStyle("-fx-fill: black;-fx-font-size: 16px;-fx-font-family: '" + customFont1.getName() + "';");
        totalPeoplePerRoom.setStyle("-fx-fill: black;-fx-font-size: 16px;-fx-font-family: '" + customFont1.getName() + "';");
        details2.setStyle("-fx-fill: black;-fx-font-size: 16px;-fx-font-family: '" + customFont1.getName() + "';");
        details3.setStyle("-fx-fill: black;-fx-font-size: 16px;-fx-font-family: '" + customFont1.getName() + "';");
        details4.setStyle("-fx-fill: black;-fx-font-size: 16px;-fx-font-family: '" + customFont1.getName() + "';");
        deckExperience.setStyle("-fx-fill: black;-fx-font-size: 16px;-fx-font-family: '" + customFont1.getName() + "';");
        details5.setStyle("-fx-fill: black;-fx-font-size: 16px;-fx-font-family: '" + customFont1.getName() + "';");
        details6.setStyle("-fx-fill: black;-fx-font-size: 16px;-fx-font-family: '" + customFont1.getName() + "';");
        details7.setStyle("-fx-fill: black;-fx-font-size: 16px;-fx-font-family: '" + customFont1.getName() + "';");

        // Create VBox to contain all the text elements
        VBox allDetailsinOneVBox = new VBox(txtTitle, vBox, details1, totalPeoplePerRoom, details3, roomPlaceExp, details2, details4, deckExperience, details5, details6, details7);
        allDetailsinOneVBox.setAlignment(Pos.CENTER_LEFT);
        allDetailsinOneVBox.setPadding(new Insets(0));



        allDetailsinOneVBox.setSpacing(10);

        BorderPane borderPane = new BorderPane(allDetailsinOneVBox);
        BorderPane.setMargin(allDetailsinOneVBox, new Insets(10));
        borderPane.setStyle("-fx-background-color: white;");


        return borderPane;
    }

    public void addAll(){

        // Get necessary data from various sources
        ArrayList<Room> rooms1 = book1.getRooms();
        ArrayList<RoomTypeDetails> roomTypes = rmType.getRoomTypes();
        ArrayList<PlaceDetails> placeDTs = place1.getPlaceDetails();
        ArrayList<DeckDetails> deckDTs = deck.getDeckDetails();
        String email = login.getEmail();

        // Initialize total price
        double price1 = 0;

        // Loop through each room and add details to the database
        for (int i = 0; i < rooms1.size(); i++) {
            Room rmDetail = rooms1.get(i);

            int room = rmDetail.getRoomNum();
            int adultNum = rmDetail.getAdultNum();
            int kidNum = rmDetail.getKidNum();

            // Calculate total price based on adult count and room type
            price1 += rmDetail.getAdultNum() * price;

            RoomTypeDetails roomTypeDetail = roomTypes.get(i);

            String roomType = roomTypeDetail.getRoomType();
            price1 += roomTypeDetail.getPrice();
            double tax = price1 * 0.06;

            // Calculate total price including tax
            double totalPrice = tax + price1;

            PlaceDetails placeDT = placeDTs.get(i);
            String roomPlace = placeDT.getSelectedPlace();

            DeckDetails deckDetail = deckDTs.get(i);

            int deckLevel = deckDetail.getDeckLevel();
            int roomNum = Integer.parseInt(deckDetail.getRoomNumber());

            try {
                // Insert payment details into the database
                PreparedStatement preparedStatement = database.getConnection().prepareStatement("INSERT INTO user_payment_detail(email, room, adultNum, kidNum, roomType, roomPlace, deckLevel, roomNum, price, tax, totalPrice, place) VALUES (?,?,?,?,?,?,?,?,?,?,?,?);");

                preparedStatement.setString(1, email);
                preparedStatement.setInt(2, room);
                preparedStatement.setInt(3, adultNum);
                preparedStatement.setInt(4, kidNum);
                preparedStatement.setString(5, roomType);
                preparedStatement.setString(6, roomPlace);
                preparedStatement.setInt(7, deckLevel);
                preparedStatement.setInt(8, roomNum);
                preparedStatement.setDouble(9, price1);
                preparedStatement.setDouble(10, tax);
                preparedStatement.setDouble(11, totalPrice);
                preparedStatement.setString(12, place);

                preparedStatement.executeUpdate();
            }catch(SQLException e){
                System.out.println(e.getMessage());
            }
        }



    }
}