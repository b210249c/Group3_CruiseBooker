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

public class Booking {
    private int roomNum;
//    private int index;
    private VBox mainVBox;
    private CruiseBookingSystem cbs;
    private Search search;
    private String place;
    private String duration;
    private String route;
    private String date;
    private String country;
    private String viewpoint;
    private Login login;
    private BorderPane leftBorderPane;
    private DatabaseManager database;
    public Booking(CruiseBookingSystem cbs, Search search, Login login) {
        this.cbs = cbs;
        this.search = search;
        this.login = login;
    }


    public Scene bookingScene() {

        BorderPane borderPane = new BorderPane();
        borderPane.setStyle("-fx-background-color: #524F56;");

        borderPane.setTop(TopNavigationBar());
        borderPane.setCenter(Center());

        return new Scene(borderPane, 1039, 694);
    }

    // Create the top navigation bar with welcome message and logout button
    public BorderPane TopNavigationBar() {
        // ... Button styling and event handling ...
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


    public ScrollPane Center(){

        BorderPane borderPane = new BorderPane();

        Image image = new Image(CruiseBookingSystem.class.getResource("room4.png").toString(), 1100,650,false,true);
        BackgroundImage bI = new BackgroundImage(image,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);

        borderPane.setBackground(new Background(bI));
        borderPane.setLeft(LeftBorderPane());
        borderPane.setCenter(CenterBorderPane());

        ScrollPane scrollPane = new ScrollPane(borderPane);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        scrollPane.setStyle("-fx-background-color:transparent");

        return scrollPane;
    }

    public BorderPane LeftBorderPane(){
        viewpoint = "";

        database = new DatabaseManager();

        try {
            // ... Database queries and result processing ...
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

            // Load download Font
            Font customFont = Font.loadFont(getClass().getResourceAsStream("KaiseiOpti-Medium.ttf"), 18);

            // Create text elements for details
            Text txt1 = new Text(duration + "\n" + place);
            txt1.setWrappingWidth(260);
            txt1.setTextAlignment(TextAlignment.JUSTIFY);
            Text txt2 = new Text("Route: \n" + route + "\n\n" + "Date: " + date);
            txt2.setWrappingWidth(260);
            txt2.setTextAlignment(TextAlignment.JUSTIFY);

            Text txt3 = new Text("Viewpoint: \n\n" + viewpoint);
            txt3.setWrappingWidth(260);
            txt3.setTextAlignment(TextAlignment.JUSTIFY);

            // ... Set text properties ...
            txt1.setStyle("-fx-fill: black;-fx-font-size: 12px;-fx-font-family: '" + customFont.getName() + "';");

            txt2.setStyle("-fx-fill: black;-fx-font-size: 12px;-fx-font-family: '" + customFont.getName() + "';");

            txt3.setStyle("-fx-fill: black;-fx-font-size: 12px;-fx-font-family: '" + customFont.getName() + "';");


            Hyperlink backLink = new Hyperlink("Back to Details");

            // ... Hyperlink styling and event handling ...
            // Define the styles for normal and hover states
            String normalStyle = "-fx-text-fill: #0070E0; -fx-underline: false;-fx-font-family:Lato;-fx-font-size:12px;-fx-font-weight:bold;";
            String hoverStyle = "-fx-text-fill: #00ff05;-fx-underline: true;-fx-font-family:Lato; -fx-font-size:12px;-fx-font-weight:bold;";

            backLink.setStyle(normalStyle);

            backLink.setOnMouseEntered(event -> backLink.setStyle(hoverStyle));
            backLink.setOnMouseExited(event -> backLink.setStyle(normalStyle));

            backLink.setOnAction(event -> {
                cbs.switchToDetailScene();
            });

            Separator separator1 = new Separator(Orientation.HORIZONTAL);

            Separator separator2 = new Separator(Orientation.HORIZONTAL);


            VBox vBox = new VBox(txt1, backLink, separator1, txt2, separator2, txt3);
            vBox.setAlignment(Pos.TOP_LEFT);
            vBox.setSpacing(20);
            leftBorderPane = new BorderPane(vBox);
            BorderPane.setMargin(leftBorderPane, new Insets(30,10,60,10));
            leftBorderPane.setStyle("-fx-background-color: white; -fx-background-radius: 20; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.7), 10, 0, 0, 0);");
            BorderPane.setMargin(vBox, new Insets(40, 20, 0, 20));

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return leftBorderPane;
    }



    public BorderPane CenterBorderPane() {

        Font customFont = Font.loadFont(getClass().getResourceAsStream("EmilysCandy-Regular.ttf"), 18);
        Font customFont1 = Font.loadFont(getClass().getResourceAsStream("KaiseiOpti-Medium.ttf"), 18);

        Text txt1 = new Text("How many rooms do you need?");
        txt1.setStyle("-fx-fill: black;-fx-font-size: 25px;-fx-font-family: '" + customFont.getName() + "';-fx-font-weight:bold;");

        Text txt2 = new Text("Most rooms sleep up to 4 guests");
        txt2.setStyle("-fx-fill: black;-fx-font-size: 15px;-fx-font-family: '" + customFont1.getName() + "';");

        roomNum = 1;

        Text txtRoomNum = new Text(roomNum + "");
        txtRoomNum.setStyle("-fx-font-size: 28");

        //Button "-"
        Button btnDelete = new Button("-");

        btnDelete.setFont(Font.font("Arial", 28));

        DropShadow dropShadow = new DropShadow();
        dropShadow.setColor(Color.DARKGRAY);
        dropShadow.setRadius(5);
        dropShadow.setOffsetX(2);
        dropShadow.setOffsetY(2);
        btnDelete.setEffect(dropShadow);

        btnDelete.setStyle(
                "-fx-text-fill: black;" +
                        "-fx-background-color: transparent;" +
                        "-fx-border-radius: 15%;" +
                        "-fx-background-radius: 150%;" +
                        "-fx-padding: 8px 20px;"
        );

        btnDelete.setOnMouseEntered(event -> btnDelete.setStyle(
                "-fx-text-fill: white;" +
                        "-fx-background-color: #1b2b36;" +
                        "-fx-border-radius: 50%;" +
                        "-fx-background-radius: 50%;" +
                        "-fx-padding: 8px 20px;"
        ));
        btnDelete.setOnMouseExited(event -> btnDelete.setStyle(
                "-fx-text-fill: black;" +
                        "-fx-background-color: transparent;" +
                        "-fx-border-radius: 50%;" +
                        "-fx-background-radius: 50%;" +
                        "-fx-padding: 8px 20px;"
        ));

        //Button "+"
        Button btnAdd = new Button("+");

        btnAdd.setFont(Font.font("Arial", 28));

        btnAdd.setStyle(
                "-fx-text-fill: black;" +
                        "-fx-background-color: transparent;" +
                        "-fx-border-radius: 15%;" +
                        "-fx-background-radius: 150%;" +
                        "-fx-padding: 8px 16px;"
        );

        btnAdd.setOnMouseEntered(event -> btnAdd.setStyle(
                "-fx-text-fill: white;" +
                        "-fx-background-color: #1b2b36;" +
                        "-fx-border-radius: 50%;" +
                        "-fx-background-radius: 50%;" +
                        "-fx-padding: 8px 16px;"
        ));
        btnAdd.setOnMouseExited(event -> btnAdd.setStyle(
                "-fx-text-fill: black;" +
                        "-fx-background-color: transparent;" +
                        "-fx-border-radius: 50%;" +
                        "-fx-background-radius: 50%;" +
                        "-fx-padding: 8px 16px;"
        ));

        HBox hBox = new HBox(btnDelete, txtRoomNum, btnAdd);
        hBox.setSpacing(20);
        hBox.setAlignment(Pos.CENTER_RIGHT);
        hBox.setStyle("-fx-background-color: white;");
        hBox.setPadding(new Insets(10));

        //Create a button
        Button btnContinue = new Button("Continue");
        btnContinue.setPrefSize(140, 40);
        btnContinue.setStyle("-fx-background-color: #4F95DA; -fx-border-color: #91C9FF; -fx-border-width: 1;-fx-text-fill:white;-fx-font-family: Lato; -fx-font-weight:bold;-fx-font-size:18px;");
        btnContinue.setOnMouseEntered(e -> btnContinue.setStyle("-fx-background-color: transparent; -fx-border-color: #91C9FF; -fx-border-width: 1;-fx-text-fill:#4F95DA;-fx-font-family: Lato; -fx-font-weight:bold;-fx-font-size:18px;"));
        btnContinue.setOnMouseExited(e -> btnContinue.setStyle("-fx-background-color: #4F95DA; -fx-border-color: #91C9FF; -fx-border-width: 1;-fx-text-fill:white;-fx-font-family: Lato; -fx-font-weight:bold;-fx-font-size:18px;"));

        HBox hBox1 = new HBox(btnContinue);
        hBox1.setAlignment(Pos.CENTER_RIGHT);
        HBox.setMargin(btnContinue, new Insets(0,15,0,0));
        VBox vBox = new VBox(txt1, txt2, hBox, hBox1);
        vBox.setSpacing(20);
        vBox.setPadding(new Insets(40));
        VBox.setMargin(hBox, new Insets(0,0,0,430));
        VBox.setMargin(hBox1, new Insets(20,0,0,0));

        BorderPane borderPane = new BorderPane(vBox);
        BorderPane.setMargin(borderPane, new Insets(30,30,300,0));
        borderPane.setStyle("-fx-background-color: white; -fx-background-radius: 20; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.7), 10, 0, 0, 0);");

        VBox.setMargin(borderPane, new Insets(30,10,30,10));

            btnDelete.setOnAction(event -> {
                if (roomNum == 1) {
                    btnDelete.setDisable(false);
                }
                if (roomNum > 1) {
                    roomNum -= 1;
                    txtRoomNum.setText(String.valueOf(roomNum));
                }
            });

            btnAdd.setOnAction(event -> {
                if (roomNum > 4) {
                    btnAdd.setDisable(false);
                }
                if (roomNum < 4) {
                    roomNum += 1;
                    txtRoomNum.setText(String.valueOf(roomNum));
                }
            });



            btnContinue.setOnAction(event -> {
                cbs.switchToBook1Scene();
                    }
            );


        return borderPane;
    }

    public int getRoomNum() {
        return roomNum;
    }


}