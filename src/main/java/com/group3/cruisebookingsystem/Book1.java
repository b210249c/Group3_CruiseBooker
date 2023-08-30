package com.group3.cruisebookingsystem;

import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Iterator;

public class Book1 {
    private ArrayList<RoomTypeDetails> roomTypes = new ArrayList<>();
    private ArrayList<Room> rooms = new ArrayList<>();
    private VBox mainVBox = new VBox();
    private int adult;
    private int kid;
    private int total;
    private int index = 1;
    private Search search;
    private String place;
    private String duration;
    private String route;
    private String date;
    private String country;
    private String viewpoint;
    private BorderPane leftBorderPane;
    private DatabaseManager database;
    private Booking booking;
    private Login login;
    private CruiseBookingSystem cbs;
    public Book1(CruiseBookingSystem cbs, Booking booking, Search search, Login login) {
        this.cbs = cbs;
        this.booking = booking;
        this.search = search;
        this.login = login;
    }



    public Scene book1Scene() {

        BorderPane borderPane = new BorderPane();
        Image image = new Image(CruiseBookingSystem.class.getResource("room7.png").toString(), 1100,700,false,true);
        BackgroundImage bI = new BackgroundImage(image,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);

        borderPane.setBackground(new Background(bI));

        // Create a top navigation bar
        borderPane.setTop(TopNavigationBar());

        // Create a left pane with details
        borderPane.setLeft(LeftBorderPane());

        // Create the center content
        borderPane.setCenter(ControlVBox());

        return new Scene(borderPane, 1039, 694);
    }

    public BorderPane TopNavigationBar() {
        //Create button
        Button btnLogout = new Button("Logout");

        //Styling button
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

        //Welcome text and logout button
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

    public BorderPane LeftBorderPane(){
        viewpoint = "";

        // Create a database manager instance
        database = new DatabaseManager();

        try {
            // Get the selected button details from the search
            String selectedBtnDetails = search.getSelectedBtnDetails();

            // Query the cruise_destination table to get details
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

            // Get the selected country
            String selectedCountry = country;

            // Query the view_point table to get viewpoints
            PreparedStatement preparedStatement2 = database.getConnection().prepareStatement("SELECT viewpoint FROM view_point WHERE country_from = ? ");

            preparedStatement2.setString(1, selectedCountry);

            ResultSet resultSet2 = preparedStatement2.executeQuery();
            while (resultSet2.next()){
                viewpoint += resultSet2.getString("viewpoint") + "\n";
            }

            // Load a custom font
            Font customFont = Font.loadFont(getClass().getResourceAsStream("KaiseiOpti-Medium.ttf"), 18);

            // Create text elements with details
            Text txt1 = new Text(duration + "\n" + place);
            txt1.setWrappingWidth(260);
            txt1.setTextAlignment(TextAlignment.JUSTIFY);
            Text txt2 = new Text("Route: \n" + route + "\n\n" + "Date: " + date);
            txt2.setWrappingWidth(260);
            txt2.setTextAlignment(TextAlignment.JUSTIFY);

            Text txt3 = new Text("Viewpoint: \n\n" + viewpoint);
            txt3.setWrappingWidth(260);
            txt3.setTextAlignment(TextAlignment.JUSTIFY);

            // Apply styles to text elements
            txt1.setStyle("-fx-fill: black;-fx-font-size: 12px;-fx-font-family: '" + customFont.getName() + "';");

            txt2.setStyle("-fx-fill: black;-fx-font-size: 12px;-fx-font-family: '" + customFont.getName() + "';");

            txt3.setStyle("-fx-fill: black;-fx-font-size: 12px;-fx-font-family: '" + customFont.getName() + "';");

            // Create a hyperlink "Back to Room Number"
            Hyperlink backLink = new Hyperlink("Back to Room Number");

            // Define the styles for normal and hover states
            String normalStyle = "-fx-text-fill: #0070E0; -fx-underline: false;-fx-font-family:Lato;-fx-font-size:12px;-fx-font-weight:bold;";
            String hoverStyle = "-fx-text-fill: #00ff05;-fx-underline: true;-fx-font-family:Lato; -fx-font-size:12px;-fx-font-weight:bold;";

            backLink.setStyle(normalStyle);

            backLink.setOnMouseEntered(event -> backLink.setStyle(hoverStyle));
            backLink.setOnMouseExited(event -> backLink.setStyle(normalStyle));

            backLink.setOnAction(event -> {
                // Handle the back action
                index = 1;
                mainVBox.getChildren().clear();
                rooms.clear();
                cbs.switchToBookingScene();

            });

            // Create separators
            Separator separator1 = new Separator(Orientation.HORIZONTAL);
            Separator separator2 = new Separator(Orientation.HORIZONTAL);

            // Create a VBox to hold the elements
            VBox vBox = new VBox(txt1, backLink, separator1, txt2, separator2, txt3);
            vBox.setAlignment(Pos.TOP_LEFT);
            vBox.setSpacing(20);

            // Create and style the left BorderPane
            leftBorderPane = new BorderPane(vBox);
            BorderPane.setMargin(leftBorderPane, new Insets(30));
            leftBorderPane.setStyle("-fx-background-color: white; -fx-background-radius: 20; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.7), 10, 0, 0, 0);");
            BorderPane.setMargin(vBox, new Insets(40, 20, 0, 20));

        } catch (Exception e) {
            // Handle exceptions
            System.out.println(e.getMessage());
        }

        return leftBorderPane;
    }

    public VBox ControlVBox(){

        // Check the current index
        if (index <= booking.getRoomNum()) {
            // If index is less than room number, clear and add the content of Center1 to mainVBox
            mainVBox.getChildren().clear();
            mainVBox.getChildren().add(Center1());
        }else if(index == (booking.getRoomNum()+1)) {
            // If index is equal to the total number of rooms + 1
            // Switch to the room type scene
            roomTypes.clear();
            cbs.switchToRoomTypeScene();

        }
        mainVBox.setStyle("-fx-background-color: transparent;");

        return mainVBox;
    }

    public BorderPane Center1() {

        // Load custom fonts
        Font customFont = Font.loadFont(getClass().getResourceAsStream("EmilysCandy-Regular.ttf"), 18);
        Font customFont1 = Font.loadFont(getClass().getResourceAsStream("KaiseiOpti-Medium.ttf"), 18);

        // Initialize adult and kid counts
         adult = 1;
         kid = 0;

        // Create Text elements
        Text txt1 = new Text("Room " + index);
        txt1.setStyle("-fx-fill: black;-fx-font-size: 30px;-fx-font-family: '" + customFont.getName() + "';-fx-font-weight:bold;");txt1.setFont(Font.font("Eras Demi ITC", 25));

        Text txt2 = new Text("Most rooms sleep up to 4 guests");
        txt2.setStyle("-fx-fill: black;-fx-font-size: 18px;-fx-font-family: '" + customFont1.getName() + "';");

        // Create Text elements for Adult
        Text txtAdult = new Text("Adult: ");

        txtAdult.setStyle("-fx-fill: black;-fx-font-size: 15px;-fx-font-family: '" + customFont1.getName() + "';");

        Text txtAdultNum = new Text(adult + "");

        txtAdultNum.setStyle("-fx-fill: black;-fx-font-size: 15px;-fx-font-family: '" + customFont1.getName() + "';");

        // Create "-" Button for Adults
        //Button "-"
        Button btnDeleteA = new Button("-");

        // ... (rest of the styling and event handling for the "-" button)
        btnDeleteA.setFont(Font.font("Arial", 15));

        DropShadow dropShadow = new DropShadow();
        dropShadow.setColor(Color.DARKGRAY);
        dropShadow.setRadius(5);
        dropShadow.setOffsetX(2);
        dropShadow.setOffsetY(2);
        btnDeleteA.setEffect(dropShadow);

        btnDeleteA.setStyle(
                "-fx-text-fill: black;" +
                        "-fx-background-color: transparent;" +
                        "-fx-border-radius: 15%;" +
                        "-fx-background-radius: 150%;" +
                        "-fx-padding: 8px 16px;"
        );

        btnDeleteA.setOnMouseEntered(event -> btnDeleteA.setStyle(
                "-fx-text-fill: white;" +
                        "-fx-background-color: #1b2b36;" +
                        "-fx-border-radius: 50%;" +
                        "-fx-background-radius: 50%;" +
                        "-fx-padding: 8px 16px;"
        ));
        btnDeleteA.setOnMouseExited(event -> btnDeleteA.setStyle(
                "-fx-text-fill: black;" +
                        "-fx-background-color: transparent;" +
                        "-fx-border-radius: 50%;" +
                        "-fx-background-radius: 50%;" +
                        "-fx-padding: 8px 16px;"
        ));

        // Create "+" Button for Adults
        //Button "+"
        Button btnAddA = new Button("+");

        // ... (rest of the styling and event handling for the "+" button)
        btnAddA.setFont(Font.font("Arial", 15));

        btnAddA.setStyle(
                "-fx-text-fill: black;" +
                        "-fx-background-color: transparent;" +
                        "-fx-border-radius: 15%;" +
                        "-fx-background-radius: 150%;" +
                        "-fx-padding: 8px 14px;"
        );

        btnAddA.setOnMouseEntered(event -> btnAddA.setStyle(
                "-fx-text-fill: white;" +
                        "-fx-background-color: #1b2b36;" +
                        "-fx-border-radius: 50%;" +
                        "-fx-background-radius: 50%;" +
                        "-fx-padding: 8px 14px;"
        ));
        btnAddA.setOnMouseExited(event -> btnAddA.setStyle(
                "-fx-text-fill: black;" +
                        "-fx-background-color: transparent;" +
                        "-fx-border-radius: 50%;" +
                        "-fx-background-radius: 50%;" +
                        "-fx-padding: 8px 14px;"
        ));

        // Create HBox for Adult controls
        HBox hBox1 = new HBox(txtAdult, btnDeleteA, txtAdultNum, btnAddA);
        hBox1.setSpacing(20);
        hBox1.setAlignment(Pos.CENTER_LEFT);

        total = adult + kid;

        btnDeleteA.setOnAction(event -> {
                if (adult == 1) {
                    btnDeleteA.setDisable(false);
                }
                if (adult > 1) {
                    adult--;
                    total--;
                    txtAdultNum.setText(String.valueOf(adult));
                }

        });

        btnAddA.setOnAction(event -> {
                if (adult >=4) {
                    btnAddA.setDisable(false);
                }
                if (total < 4) {
                    adult++;
                    total++;
                    txtAdultNum.setText(String.valueOf(adult));
                }

        });

        // ... (similar sections for Kid controls)
        Text txtKid = new Text("Kid: ");

        txtKid.setStyle("-fx-fill: black;-fx-font-size: 15px;-fx-font-family: '" + customFont1.getName() + "';");

        VBox textKidVB = new VBox(txtKid);
        textKidVB.setPadding(new Insets(0,17,0,0));
        Text txtKidNum = new Text(kid + "");

        txtKidNum.setStyle("-fx-fill: black;-fx-font-size: 15px;-fx-font-family: '" + customFont1.getName() + "';");

        //button "-"
        Button btnDeleteK = new Button("-");
        btnDeleteK.setFont(Font.font("Arial", 15));

        btnDeleteK.setStyle(
                "-fx-text-fill: black;" +
                        "-fx-background-color: transparent;" +
                        "-fx-border-radius: 15%;" +
                        "-fx-background-radius: 150%;" +
                        "-fx-padding: 8px 16px;"
        );

        btnDeleteK.setOnMouseEntered(event -> btnDeleteK.setStyle(
                "-fx-text-fill: white;" +
                        "-fx-background-color: #1b2b36;" +
                        "-fx-border-radius: 50%;" +
                        "-fx-background-radius: 50%;" +
                        "-fx-padding: 8px 16px;"
        ));
        btnDeleteK.setOnMouseExited(event -> btnDeleteK.setStyle(
                "-fx-text-fill: black;" +
                        "-fx-background-color: transparent;" +
                        "-fx-border-radius: 50%;" +
                        "-fx-background-radius: 50%;" +
                        "-fx-padding: 8px 16px;"
        ));

        //Button "+"
        Button btnAddK = new Button("+");

        btnAddK.setFont(Font.font("Arial", 15));

        btnAddK.setStyle(
                "-fx-text-fill: black;" +
                        "-fx-background-color: transparent;" +
                        "-fx-border-radius: 15%;" +
                        "-fx-background-radius: 150%;" +
                        "-fx-padding: 8px 14px;"
        );

        btnAddK.setOnMouseEntered(event -> btnAddK.setStyle(
                "-fx-text-fill: white;" +
                        "-fx-background-color: #1b2b36;" +
                        "-fx-border-radius: 50%;" +
                        "-fx-background-radius: 50%;" +
                        "-fx-padding: 8px 14px;"
        ));
        btnAddK.setOnMouseExited(event -> btnAddK.setStyle(
                "-fx-text-fill: black;" +
                        "-fx-background-color: transparent;" +
                        "-fx-border-radius: 50%;" +
                        "-fx-background-radius: 50%;" +
                        "-fx-padding: 8px 14px;"
        ));

        HBox hBox2 = new HBox(txtKid, btnDeleteK, txtKidNum, btnAddK);
        hBox2.setSpacing(20);
        hBox2.setAlignment(Pos.CENTER_LEFT);

        btnDeleteK.setOnAction(event -> {
                if (kid == 0) {
                    btnDeleteK.setDisable(false);
                }
                if (kid > 0) {
                    kid--;
                    total--;
                    txtKidNum.setText(String.valueOf(kid));
                }

        });

        btnAddK.setOnAction(event -> {
                if (kid >= 4) {
                    btnAddK.setDisable(false);
                }
                if (total < 4) {
                    kid++;
                    total++;
                    txtKidNum.setText(String.valueOf(kid));
                }

        });



        Hyperlink btnBack = new Hyperlink("Back");
        if(index == 1) {
            btnBack.setDisable(true);
        }else {
            btnBack.setDisable(false);
            btnBack.setOnAction(event -> {
                index--;
                iterator(index);
                mainVBox.getChildren().clear();
                ControlVBox();
            });
        }

        // Create "Confirm" Button
        Button btnContinue = new Button("Confirm");

        // ... (styling and event handling for the "Confirm" button)
        btnContinue.setPrefSize(140, 40);
        btnContinue.setStyle("-fx-background-color: #4F95DA; -fx-border-color: #91C9FF; -fx-border-width: 1;-fx-text-fill:white;-fx-font-family: Lato; -fx-font-weight:bold;-fx-font-size:18px;");
        btnContinue.setOnMouseEntered(e -> btnContinue.setStyle("-fx-background-color: transparent; -fx-border-color: #91C9FF; -fx-border-width: 1;-fx-text-fill:#4F95DA;-fx-font-family: Lato; -fx-font-weight:bold;-fx-font-size:18px;"));
        btnContinue.setOnMouseExited(e -> btnContinue.setStyle("-fx-background-color: #4F95DA; -fx-border-color: #91C9FF; -fx-border-width: 1;-fx-text-fill:white;-fx-font-family: Lato; -fx-font-weight:bold;-fx-font-size:18px;"));

        // Create HBox for the "Confirm" button
        HBox hBox3 = new HBox(btnContinue);
        hBox3.setSpacing(20);
        hBox3.setAlignment(Pos.CENTER_RIGHT);

        btnContinue.setOnAction(event -> {

            Room data = new Room(index, adult, kid);
            rooms.add(data);

            for (Room room : rooms){
                System.out.println(room);
            }

            index++;
            ControlVBox();
        });

        // Set up the VBox to hold all the elements
        VBox vBox = new VBox(txt1, txt2, hBox1, hBox2, hBox3);
        vBox.setSpacing(20);
        vBox.setPadding(new Insets(40));

        // Create the BorderPane to hold the VBox
        BorderPane borderPane = new BorderPane(vBox);
        BorderPane.setMargin(borderPane, new Insets(30,30,300,0));
        borderPane.setStyle("-fx-background-color: white; -fx-background-radius: 20; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.7), 10, 0, 0, 0);");

        VBox.setMargin(borderPane, new Insets(30,30,10,0));

        return borderPane;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public void iterator(int index){
        Iterator<Room> iterator = rooms.iterator();
        while (iterator.hasNext()) {
            Room room = iterator.next();
            if (room.getRoomNum() == index || room.getRoomNum() == index + 1) {
                iterator.remove();
            }
        }
    }

    public ArrayList<Room> getRooms() {
        return rooms;
    }
}