package com.group3.cruisebookingsystem;

import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class Place {
    private DatabaseManager database;
    private CruiseBookingSystem cbs;
    private VBox column1;
    private VBox column2;
    private VBox column3;
    private ArrayList<String> selectedRoomPlaces = new ArrayList<>();
    private String roomPlace;
    private Booking booking;
    private int count = 1;
    private String message ="";
    private Text txt1;
    private RoomType rmType;
    private Search search;
    private String place;
    private String duration;
    private String route;
    private String date;
    private String country;
    private String viewpoint;
    private BorderPane leftBorderPane;
    private Book1 book1;

    public Place(CruiseBookingSystem cbs, Booking booking, Book1 book1, Search search, RoomType rmType) {
        this.cbs = cbs;
        this.booking = booking;
        this.book1 = book1;
        this.search = search;
        this.rmType = rmType;
    }
    public Scene placeScene() {
        //        Image image = new Image(CruiseBookingSystem.class.getResource("").toString(), 1366,768,false,true);
//        BackgroundImage bI = new BackgroundImage(image,
//                BackgroundRepeat.NO_REPEAT,
//                BackgroundRepeat.NO_REPEAT,
//                BackgroundPosition.DEFAULT,
//                BackgroundSize.DEFAULT);

        BorderPane borderPane = new BorderPane();
        borderPane.setStyle("-fx-background-color: lightblue;");

//        borderPane.setBackground(new Background(bI));

        borderPane.setTop(TopNavigationBar());
        borderPane.setCenter(Center());

        return new Scene(borderPane, 1039, 694);
    }

    public BorderPane TopNavigationBar(){
        //        Image imgLeft = new Image(TextManipulation.class.getResource("left.png").toString());
//        ImageView ivLeft = new ImageView(imgLeft);
//        Button btnLeft = new Button("Left", ivLeft);
//        btnLeft.setOnAction(e -> {
//            System.out.println("Left button is clicked");
//        });

        //Facebook Icon
//        Image imageFB = new Image(Login.class.getResource("").toString());
//        ImageView imageViewFB = new ImageView(imageFB);
//        //Setting the height and the width of the Facebook Icon
//        imageViewFB.setFitHeight(30);
//        imageViewFB.setFitWidth(30);
//        //Setting image to button
        Button btnLogout = new Button("Logout");
        btnLogout.setOnAction(event -> {
            cbs.switchToMainScene();
        });
//        btnFB.setGraphic(imageViewFB);
//        btnFB.setStyle("-fx-background-color: transparent");

        Text welcome = new Text("Welcome, XXX");
        welcome.setFont(Font.font("Eras Demi ITC", 20));

        HBox hBox = new HBox(welcome, btnLogout);
        hBox.setSpacing(20);
        hBox.setPadding(new Insets(20));
        hBox.setAlignment(Pos.BASELINE_RIGHT);

        BorderPane borderPane = new BorderPane(hBox);
        borderPane.setStyle("-fx-background-color: white;");

        return borderPane;
    }
    public BorderPane LeftBorderPane(){
        viewpoint = "";

        database = new DatabaseManager();

        try {
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

            String selectedCountry = country;

            PreparedStatement preparedStatement2 = database.getConnection().prepareStatement("SELECT viewpoint FROM view_point WHERE country_from = ? ");

            preparedStatement2.setString(1, selectedCountry);

            ResultSet resultSet2 = preparedStatement2.executeQuery();
            while (resultSet2.next()){
                viewpoint += resultSet2.getString("viewpoint") + "\n";
            }

            Text txt1 = new Text(duration + "\n" + place);
            txt1.setWrappingWidth(260);
            txt1.setTextAlignment(TextAlignment.JUSTIFY);
            Text txt2 = new Text("Route: \n" + route + "\n\n" + "Date: " + date);
            txt2.setWrappingWidth(260);
            txt2.setTextAlignment(TextAlignment.JUSTIFY);

            Text txt3 = new Text("Viewpoint: \n\n" + viewpoint);
            txt3.setWrappingWidth(260);
            txt3.setTextAlignment(TextAlignment.JUSTIFY);



            Hyperlink backLink = new Hyperlink("Back to Choose Room Type " + (book1.getIndex()-1));
            backLink.setOnAction(event -> {
                rmType.setIndex(rmType.getIndex()-1);
                rmType.iterator(rmType.getIndex());
                roomPlace = null;
                count = 1;
                message ="";
                selectedRoomPlaces.clear();
                cbs.switchToRoomTypeScene();

            });

            Separator separator1 = new Separator(Orientation.HORIZONTAL);

            Separator separator2 = new Separator(Orientation.HORIZONTAL);


            VBox vBox = new VBox(txt1, backLink, separator1, txt2, separator2, txt3);
            vBox.setAlignment(Pos.TOP_LEFT);
            vBox.setSpacing(20);
            leftBorderPane = new BorderPane(vBox);
            BorderPane.setMargin(leftBorderPane, new Insets(30,10,40,10));
            leftBorderPane.setStyle("-fx-background-color: white; -fx-background-radius: 20; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.7), 10, 0, 0, 0);");
            BorderPane.setMargin(vBox, new Insets(40, 20, 0, 20));

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return leftBorderPane;
    }


    public BorderPane Center(){

        BorderPane borderPane = new BorderPane();
//        BorderPane.setMargin(borderPane, new Insets(30));

        borderPane.setLeft(LeftBorderPane());

        HBox take1 = CenterBorderPane();
        BorderPane take2 = CenterTop();
        VBox take3 = continueButton();
        VBox take4 = compileCenterTopAndCenterBorderPane(take1,take2,take3);
        borderPane.setCenter(take4);

        borderPane.setStyle("-fx-background-color: lightblue;");

//        ScrollPane scrollPane = new ScrollPane(borderPane);
//        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
//        scrollPane.setFitToWidth(true);

        return borderPane;
    }

    public BorderPane CenterTop(){

        txt1 = new Text("Room "+ count+ "  - Good choice, now where do you want your room?");
        txt1.setFont(Font.font("Eras Demi ITC", 40));
        txt1.setWrappingWidth(650);
        txt1.setTextAlignment(TextAlignment.LEFT);

        VBox vBox = new VBox(txt1);
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(20);
        BorderPane borderPane = new BorderPane();
        borderPane.setLeft(vBox);
        //        borderPane.setBackground(new Background(bI));
        BorderPane.setMargin(borderPane, new Insets(0,0,20,0));
//        borderPane.setStyle("-fx-background-color: white; -fx-background-radius: 20; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.7), 10, 0, 0, 0);");
        BorderPane.setMargin(vBox, new Insets(30,50,50,0));

        return borderPane;
    }

    public HBox CenterBorderPane(){

        BorderPane borderPane = new BorderPane();

        // Create UI elements
        column1 = createColumn("Column 1");
        column2 = createColumn("Column 2");
        column3 = createColumn("Column 3");

        // Create a layout pane and add columns
        HBox root = new HBox(column1, column2, column3);
        root.setSpacing(0); // No gap between columns
//        root.setBackground(new Background(new BackgroundFill(Color.LIGHTBLUE, CornerRadii.EMPTY, Insets.EMPTY)));

        borderPane.setStyle("-fx-background-color: lightblue;");
//        BorderPane borderPane = new BorderPane();
//        BorderPane.setMargin(borderPane, new Insets(30,30,30,0));
//        borderPane.setTop(CenterTop());
//        Text txt = new Text("Big Bold Savings");
//        txt.setFont(Font.font("Eras Demi ITC", 25));
//        HBox hBox = new HBox(txt);
//        hBox.setAlignment(Pos.BASELINE_LEFT);
//        BorderPane.setMargin(hBox, new Insets(0,0,10,0));
//        borderPane.setCenter(hBox);
//        borderPane.setBottom(CenterBottom());

        return root;
    }

    private VBox createColumn(String title) {

        VBox vbWhole = new VBox();
        vbWhole.setSpacing(70);
        vbWhole.setPadding(new Insets(35,10,20,20));
        vbWhole.setStyle("-fx-background-color: white; -fx-background-radius: 0; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.7), 10, 0, 0, 0);");
//        vbWhole.setStyle("-fx-background-color: white;");
//        VBox.setMargin(vbWhole,new Insets(200,200,200,200));
        vbWhole.getChildren().addAll(topVB(title),centerVB(),bottomVB(title));
        // Store references to the column VBoxes
        if (title.equals("Column 1")) {
            column1 = vbWhole;
        } else if (title.equals("Column 2")) {
            column2 = vbWhole;
        } else if (title.equals("Column 3")) {
            column3 = vbWhole;
        }
        return vbWhole;

    }

    public VBox topVB(String columnName){
        VBox column = new VBox();
        column.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        column.setSpacing(10);

        Text textAft,textHere;

        if(columnName.equals("Column 1")){
            textAft = new Text("Aft");
            textAft.setFont(Font.font("Times New Roman", 18));
            textAft.setTextAlignment(TextAlignment.RIGHT);

            textHere = new Text("The back of the ship, home to plenty of dining options and great sail-away views.");
            textHere.setWrappingWidth(200);
            textHere.setFont(Font.font("Times New Roman", 12));
            textHere.setTextAlignment(TextAlignment.CENTER);
        } else if (columnName.equals("Column 2")) {
            textAft = new Text("Mid-Ship");
            textAft.setFont(Font.font("Times New Roman", 18));
            textAft.setTextAlignment(TextAlignment.RIGHT);

            textHere = new Text("Mid-ship puts you in the center of all the action.");
            textHere.setWrappingWidth(200);
            textHere.setFont(Font.font("Times New Roman", 12));
            textHere.setTextAlignment(TextAlignment.CENTER);
        }else{
            textAft = new Text("Forward");
            textAft.setFont(Font.font("Times New Roman", 18));
            textAft.setTextAlignment(TextAlignment.RIGHT);

            textHere = new Text("The front of the ship, quicker and close to the spa and suite lounges.");
            textHere.setWrappingWidth(200);
            textHere.setFont(Font.font("Times New Roman", 12));
            textHere.setTextAlignment(TextAlignment.CENTER);
        }

        column.setAlignment(Pos.CENTER);
        column.getChildren().addAll(textAft, textHere);
        return column;
    }

    public VBox bottomVB(String columnName){
        VBox Vbutton = new VBox();
        Vbutton.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        Button selectButton = new Button("Select");
        Vbutton.setAlignment(Pos.CENTER);
        selectButton.setOnAction(e -> {

            column1.setStyle("-fx-background-color: " + (columnName.equals("Column 1") ? "blue;" : "white;-fx-background-radius: 0; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.7), 10, 0, 0, 0)"));
            column2.setStyle("-fx-background-color: " + (columnName.equals("Column 2") ? "blue;" : "white;-fx-background-radius: 0; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.7), 10, 0, 0, 0)"));
            column3.setStyle("-fx-background-color: " + (columnName.equals("Column 3") ? "blue;" : "white;-fx-background-radius: 0; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.7), 10, 0, 0, 0)"));


            updateVBoxBackgrounds(column1, columnName.equals("Column 1"));
            updateVBoxBackgrounds(column2, columnName.equals("Column 2"));
            updateVBoxBackgrounds(column3, columnName.equals("Column 3"));

            if (columnName.equals("Column 1")) {
                roomPlace = "Aft";
            } else if (columnName.equals("Column 2")) {
                roomPlace = "MidShip";
            } else if (columnName.equals("Column 3")) {
                roomPlace = "Forward";
            }

            System.out.println(roomPlace);

        });


        Vbutton.getChildren().addAll(selectButton);
        return Vbutton;
    }

    private void updateVBoxBackgrounds(VBox columnVBox, boolean isSelected) {
        for (Node node : columnVBox.getChildren()) {
            if (node instanceof VBox) {
                Color backgroundColor = isSelected ? Color.BLUE : Color.WHITE;
                ((VBox) node).setBackground(new Background(new BackgroundFill(backgroundColor, CornerRadii.EMPTY, Insets.EMPTY)));
            }
        }
    }

    public VBox centerVB(){
        VBox vbox = new VBox();
        vbox.setFillWidth(true);
        vbox.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        Image image = new Image(getClass().getResourceAsStream("cruiselogo.png"));

        // Create an ImageView to display the image
        ImageView imageView = new ImageView(image);

        // Add the ImageView to the VBox
        vbox.getChildren().add(imageView);
        vbox.setAlignment(Pos.CENTER);
        return vbox;
    }

    public VBox continueButton(){
        VBox vb = new VBox();
        vb.setBackground(new Background(new BackgroundFill(Color.LIGHTBLUE, CornerRadii.EMPTY, Insets.EMPTY)));
        Button continueButton = new Button("Continue");
        vb.setAlignment(Pos.CENTER_RIGHT);
        vb.setPadding(new Insets(20,30,0,0));
        continueButton.setOnAction(e -> {

            System.out.println(booking.getRoomNum());
            if(booking.getRoomNum()==1 && roomPlace!=null){
                // Add the selected room place to the list
                selectedRoomPlaces.add(roomPlace);

//                for (String place : selectedRoomPlaces) {
//                    System.out.println(place);
//                }

                // Show an alert for successful room selection
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Room Selection Successful");
                alert.setHeaderText(null);
                alert.setContentText("You have successfully chosen the room: " + roomPlace);
                alert.showAndWait();
                roomPlace = null;
                selectedRoomPlaces.clear();
                cbs.switchToDeckScene();
            }else if(booking.getRoomNum()!=1 && roomPlace!=null){
                message += "Room place " + count + ": " + roomPlace + "\n";
                selectedRoomPlaces.add(roomPlace);
                if (!(count == booking.getRoomNum())){
                    roomPlace = null;
                    count++;
                    txt1.setText("Room " + count + " - Keep choosing, you have more rooms to select.");
//                    txt1.setFill(getRandomColor());
                    column1.setStyle("-fx-background-color:white ;-fx-background-radius: 0; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.7), 10, 0, 0, 0)");
                    column2.setStyle("-fx-background-color:white ;-fx-background-radius: 0; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.7), 10, 0, 0, 0)");
                    column3.setStyle("-fx-background-color:white ;-fx-background-radius: 0; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.7), 10, 0, 0, 0)");
                    updateVBoxBackgrounds(column1, false);
                    updateVBoxBackgrounds(column2, false);
                    updateVBoxBackgrounds(column3, false);

                    System.out.println(roomPlace);
                    System.out.println(count);
                }else{
//                    for (String place : selectedRoomPlaces) {
//                    System.out.println(place);
//                    }
                    // Show an alert for successful room selection
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Room Selection Successful");
                    alert.setHeaderText(null);
                    alert.setContentText("You have successfully chosen the room: \n" + message);
                    alert.showAndWait();
                    roomPlace = null;
                    count = 1;
                    message ="";
                    selectedRoomPlaces.clear();
                    cbs.switchToDeckScene();
                }
            }
            else{
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Room Selection Required");
                alert.setHeaderText(null);
                alert.setContentText("Please select a room place before continuing.");
                alert.showAndWait();
            }
        });

        vb.getChildren().addAll(continueButton);
        return vb;
    }

    public VBox compileCenterTopAndCenterBorderPane(HBox centerBorderPane, BorderPane centerTop, VBox continueButton){
        VBox vb = new VBox();
        vb.getChildren().addAll(centerTop,centerBorderPane,continueButton);
        VBox.setMargin(vb, new Insets(0,0,0,10));
        vb.setSpacing(10);
        return vb;
    }

}


