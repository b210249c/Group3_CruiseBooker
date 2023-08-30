package com.group3.cruisebookingsystem;

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
import java.util.ArrayList;
import java.util.Iterator;
import javafx.scene.image.Image;
import javafx.scene.text.TextAlignment;


public class RoomType {
    private String roomType;
    private int price;
    private Hyperlink btnBack;
    private ArrayList<RoomTypeDetails> roomTypes = new ArrayList<>();
    private ArrayList<Room> rooms = new ArrayList<>();
    private VBox mainVBox = new VBox();
    private Booking booking;
    private Book1 book1;
    private int index = 1;
    private Search search;
    private String place;
    private String duration;
    private String route;
    private String date;
    private String country;
    private String viewpoint;
    private BorderPane leftBorderPane;
    private Login login;
    private DatabaseManager database;
    private CruiseBookingSystem cbs;
    public RoomType(CruiseBookingSystem cbs, Booking booking, Book1 book1, Search search, Login login) {
        this.cbs = cbs;
        this.booking = booking;
        this.book1 = book1;
        this.search = search;
        this.login = login;
    }

    public Scene roomTypeScene() {

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


        ScrollPane scrollPane = new ScrollPane(ControlVBox());
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: #E3CAB8");

        BorderPane borderPane = new BorderPane(scrollPane);
        borderPane.setStyle("-fx-background-color: ##E3CAB8");

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
            Hyperlink backLink = new Hyperlink("Back to Room " + (book1.getIndex()-1));

            // Define the styles for normal and hover states
            String normalStyle = "-fx-text-fill: #0070E0; -fx-underline: false;-fx-font-family:Lato;-fx-font-size:12px;-fx-font-weight:bold;";
            String hoverStyle = "-fx-text-fill: #00ff05;-fx-underline: true;-fx-font-family:Lato; -fx-font-size:12px;-fx-font-weight:bold;";

            backLink.setStyle(normalStyle);

            // Apply hover styles on mouse enter and reset on mouse exit
            backLink.setOnMouseEntered(event -> backLink.setStyle(hoverStyle));
            backLink.setOnMouseExited(event -> backLink.setStyle(normalStyle));

            // Set action for the Hyperlink to navigate back
            backLink.setOnAction(event -> {
                mainVBox.getChildren().clear();
                book1.setIndex(book1.getIndex()-1);
                book1.iterator(book1.getIndex());
                index = 1;
                roomTypes.clear();
                cbs.switchToBook1Scene();

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

        // Check the current index
        if (index <= booking.getRoomNum()) {
            // If index is less than room number, clear and add the content of Center1 to mainVBox
            mainVBox.getChildren().clear();
            mainVBox.getChildren().add(CenterBorderPane());
        } else if (index == (booking.getRoomNum()+1)) {
            // If index is equal to the total number of rooms + 1
            // Switch to the place scene
            cbs.switchToPlace1Scene();
        }
        mainVBox.setStyle("-fx-background-color: #E3CAB8;");

        return mainVBox;
    }


    public BorderPane CenterBorderPane(){
        // Load custom fonts
        Font customFont = Font.loadFont(getClass().getResourceAsStream("EmilysCandy-Regular.ttf"), 18);
        Font customFont1 = Font.loadFont(getClass().getResourceAsStream("KaiseiOpti-Medium.ttf"), 18);

        // Create title text and set style
        Text txtTitle = new Text("Room " + index + " - Select your room type");

        txtTitle.setStyle("-fx-fill: black;-fx-font-size: 35px;-fx-font-family: '" + customFont.getName() + "';-fx-font-weight:bold;");

        // Create "Back" hyperlink
        btnBack = new Hyperlink("Back to Select Room Type");

        // Define the styles for normal and hover states
        String normalStyle = "-fx-text-fill: #0070E0; -fx-underline: false;-fx-font-family:Lato;-fx-font-size:16px;-fx-font-weight:bold;";
        String hoverStyle = "-fx-text-fill: #00ff05;-fx-underline: true;-fx-font-family:Lato; -fx-font-size:16px;-fx-font-weight:bold;";

        btnBack.setStyle(normalStyle);

        btnBack.setOnMouseEntered(event -> btnBack.setStyle(hoverStyle));
        btnBack.setOnMouseExited(event -> btnBack.setStyle(normalStyle));


        // Create a VBox to hold the title and "Back" hyperlink
        VBox titleVBox = new VBox(txtTitle, btnBack);
        titleVBox.setAlignment(Pos.TOP_CENTER);
        titleVBox.setSpacing(10);

        // Enable or disable the "Back" hyperlink based on index
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

        // Load images for room types
        Image interiorImage = new Image(CruiseBookingSystem.class.getResource("interior.png").toString());

        // Create an ImageView for the interior room image
        ImageView interiorView = new ImageView(interiorImage);
        interiorView.setFitWidth(650);

        // Create Text elements for interior room details
        Text txtInt1 = new Text("Our Interior rooms deliver all the comfort you need at a great value. And for a view, consider a room with a window overlooking Central Park or the Boardwalk.");
        Text txtInt2 = new Text("Our Interior rooms deliver all the comfort you");
        Text txtInt3 = new Text("\nRM592");

        // Apply styles and wrapping to the interior room details
        txtInt1.setStyle("-fx-fill: black;-fx-font-size: 16px;-fx-font-family: '" + customFont.getName() + "';");
        txtInt2.setStyle("-fx-fill: black;-fx-font-size: 16px;-fx-font-family: '" + customFont.getName() + "';");
        txtInt3.setStyle("-fx-fill: black;-fx-font-size: 16px;-fx-font-family: '" + customFont1.getName() + "';");
        txtInt1.setWrappingWidth(600);
        txtInt2.setWrappingWidth(600);
        txtInt3.setWrappingWidth(600);


        // Create "Select" button for interior room
        Button selectInterior = new Button("Select");

        // Apply styles to the "Select" button
        selectInterior.setPrefSize(100, 40);
        selectInterior.setStyle("-fx-background-color: #4F95DA; -fx-border-color: #91C9FF; -fx-border-width: 1;-fx-text-fill:white;-fx-font-family: Lato; -fx-font-weight:bold;-fx-font-size:18px;");
        selectInterior.setOnMouseEntered(e -> selectInterior.setStyle("-fx-background-color: transparent; -fx-border-color: #91C9FF; -fx-border-width: 1;-fx-text-fill:#4F95DA;-fx-font-family: Lato; -fx-font-weight:bold;-fx-font-size:18px;"));
        selectInterior.setOnMouseExited(e -> selectInterior.setStyle("-fx-background-color: #4F95DA; -fx-border-color: #91C9FF; -fx-border-width: 1;-fx-text-fill:white;-fx-font-family: Lato; -fx-font-weight:bold;-fx-font-size:18px;"));

        // Create an HBox to hold the "Select" button for interior room
        HBox btnIntHBox = new HBox(selectInterior);
        btnIntHBox.setAlignment(Pos.CENTER_RIGHT);
        selectInterior.setUserData("Interior");
        selectInterior.setOnAction(event -> {
            roomType = selectInterior.getUserData().toString();
            price = 592;
            RoomTypeDetails data = new RoomTypeDetails(index, roomType, price);
            roomTypes.add(data);

            for (RoomTypeDetails room : roomTypes){
                System.out.println(room);
            }
            index++;
            ControlVBox();
        });

        // Create a VBox to hold the interior room details and "Select" button
        VBox vBoxInt = new VBox(txtInt1, txtInt2, txtInt3, btnIntHBox);

        // Apply styles to the VBox
        vBoxInt.setStyle("-fx-background-color: white");
        vBoxInt.setPadding(new Insets(20));

        // Create a VBox to hold the interior room image and details
        VBox vBoxInterior = new VBox(interiorView, vBoxInt);


        // ... (Similar sections for ocean room type)
        Image oceanImage = new Image(CruiseBookingSystem.class.getResource("ocean.png").toString());
        ImageView oceanView = new ImageView(oceanImage);
        oceanView.setFitWidth(650);

        Text txtOcn1 = new Text("Soak in the scenes of sea and shore from the comfort of your own Ocean View room.");
        Text txtOcn2 = new Text("Our Ocean rooms deliver all the comfort you");
        Text txtOcn3 = new Text("\nRM899");

        txtOcn1.setStyle("-fx-fill: black;-fx-font-size: 16px;-fx-font-family: '" + customFont.getName() + "';");
        txtOcn2.setStyle("-fx-fill: black;-fx-font-size: 16px;-fx-font-family: '" + customFont.getName() + "';");
        txtOcn3.setStyle("-fx-fill: black;-fx-font-size: 16px;-fx-font-family: '" + customFont1.getName() + "';");
        txtInt1.setWrappingWidth(600);
        txtInt2.setWrappingWidth(600);
        txtInt3.setWrappingWidth(600);

        //Select Button
        Button selectOcean = new Button("Select");

        selectOcean.setPrefSize(100, 40);
        selectOcean.setStyle("-fx-background-color: #4F95DA; -fx-border-color: #91C9FF; -fx-border-width: 1;-fx-text-fill:white;-fx-font-family: Lato; -fx-font-weight:bold;-fx-font-size:18px;");
        selectOcean.setOnMouseEntered(e -> selectOcean.setStyle("-fx-background-color: transparent; -fx-border-color: #91C9FF; -fx-border-width: 1;-fx-text-fill:#4F95DA;-fx-font-family: Lato; -fx-font-weight:bold;-fx-font-size:18px;"));
        selectOcean.setOnMouseExited(e -> selectOcean.setStyle("-fx-background-color: #4F95DA; -fx-border-color: #91C9FF; -fx-border-width: 1;-fx-text-fill:white;-fx-font-family: Lato; -fx-font-weight:bold;-fx-font-size:18px;"));

        HBox btnOcnHBox = new HBox(selectOcean);
        btnOcnHBox.setAlignment(Pos.CENTER_RIGHT);
        selectOcean.setUserData("Ocean");
        selectOcean.setOnAction(event -> {
            roomType = selectOcean.getUserData().toString();
            price = 899;
            RoomTypeDetails data = new RoomTypeDetails(index, roomType, price);
            roomTypes.add(data);

            for (RoomTypeDetails room : roomTypes){
                System.out.println(room);
            }
            index++;
            ControlVBox();
        });


        VBox vBoxOcn = new VBox(txtOcn1, txtOcn2, txtOcn3, btnOcnHBox);
        vBoxOcn.setStyle("-fx-background-color: white");
        vBoxOcn.setPadding(new Insets(20));
        VBox vBoxOcean = new VBox(oceanView, vBoxOcn);

        // Create a VBox to hold all content
        VBox allVBox = new VBox(titleVBox, vBoxInterior, vBoxOcean);
        allVBox.setSpacing(40);

        // Create a BorderPane to hold all the content
        BorderPane borderPane = new BorderPane(allVBox);
        BorderPane.setMargin(allVBox, new Insets(30));

        return borderPane;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public void iterator(int index){
        // Create an iterator to traverse the roomTypes list
        Iterator<RoomTypeDetails> iterator = roomTypes.iterator();

        // Iterate through the roomTypes list
        while (iterator.hasNext()) {
            // Get the next RoomTypeDetails object from the iterator
            RoomTypeDetails room = iterator.next();

            // Check if the index of the current room matches the provided index or index + 1
            if (room.getIndex() == index || room.getIndex() == index + 1) {

                // If the condition is met, remove the current room from the list using the iterator's remove method
                iterator.remove();
            }
        }
    }

    public ArrayList<RoomTypeDetails> getRoomTypes() {
        return roomTypes;
    }
}