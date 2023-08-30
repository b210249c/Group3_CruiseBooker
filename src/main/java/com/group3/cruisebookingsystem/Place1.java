package com.group3.cruisebookingsystem;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
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


public class Place1 {
    private String selectedButton;
    private Button selectButton;
    private String roomPlace;
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
    private VBox column1;
    private VBox column2;
    private VBox column3;
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
    private ArrayList<PlaceDetails> placeDetails = new ArrayList<>();
    public Place1(CruiseBookingSystem cbs, Booking booking, Book1 book1, Search search, RoomType rmType, Login login) {
        this.cbs = cbs;
        this.booking = booking;
        this.book1 = book1;
        this.search = search;
        this.rmType = rmType;
        this.login = login;
    }


    public Scene place1Scene() {
        BorderPane borderPane = new BorderPane();
        Image image = new Image(CruiseBookingSystem.class.getResource("place1.png").toString(), 1366,768,false,true);
        BackgroundImage bI = new BackgroundImage(image,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        borderPane.setBackground(new Background(bI));


        borderPane.setTop(TopNavigationBar());
        borderPane.setLeft(LeftBorderPane());
        borderPane.setCenter(CenterScrollPane());

        return new Scene(borderPane, 1039, 694);
    }

    public BorderPane TopNavigationBar(){

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
            Hyperlink backLink = new Hyperlink("Back to Choose Room Type " + (book1.getIndex()-1));

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
                rmType.setIndex(rmType.getIndex()-1);
                rmType.iterator(rmType.getIndex());
                index = 1;
                placeDetails.clear();
                cbs.switchToRoomTypeScene();

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
            mainVBox.getChildren().clear();
            mainVBox.getChildren().add(CenterBorderPane());
        } else if (index == (booking.getRoomNum()+1)) {

            cbs.switchToDeckScene();
        }
        mainVBox.setStyle("-fx-background-color: transparent;");

        return mainVBox;
    }

    private VBox createColumn(String title) {

        // Create a new VBox to hold the entire column's content
        VBox vbWhole = new VBox();

        // Set spacing between elements and padding around the VBox
        vbWhole.setSpacing(70);
        vbWhole.setPadding(new Insets(35,20,20,10));

        // Apply styling to the VBox to give it a drop shadow effect
        vbWhole.setStyle("-fx-background-color: white; -fx-background-radius: 0; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.7), 10, 0, 0, 0);");

        // Add child VBoxes (topVB, centerVB, bottomVB) to the main VBox
        vbWhole.getChildren().addAll(topVB(title),centerVB(title),bottomVB(title));

        // Store references to the column VBoxes based on the title
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

        // Create a new VBox to hold the top section content for each column
        VBox column = new VBox();

        // Set background color, spacing, and alignment for the VBox
        column.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        column.setSpacing(10);

        Text textAft,textHere;

        // Load a custom font
        Font customFont = Font.loadFont(getClass().getResourceAsStream("KaiseiOpti-Medium.ttf"), 18);

        // Based on the columnName, set text for the title and description
        if(columnName.equals("Column 1")){
            textAft = new Text("Aft");
            textAft.setFont(Font.font("Times New Roman", 20));
            textAft.setTextAlignment(TextAlignment.RIGHT);
            textAft.setStyle("-fx-font-weight:bold");

            textHere = new Text("The back of the ship, home to plenty of dining options and great sail-away views.");
            textHere.setWrappingWidth(200);
            textHere.setStyle("-fx-fill: black;-fx-font-size: 10px;-fx-font-family: '" + customFont.getName() + "';");
            textHere.setTextAlignment(TextAlignment.CENTER);

        } else if (columnName.equals("Column 2")) {
            textAft = new Text("Mid-Ship");
            textAft.setFont(Font.font("Times New Roman", 20));
            textAft.setTextAlignment(TextAlignment.RIGHT);
            textAft.setStyle("-fx-font-weight:bold");

            textHere = new Text("Mid-ship puts you in the center of all the action.");
            textHere.setWrappingWidth(200);
            textHere.setStyle("-fx-fill: black;-fx-font-size: 10px;-fx-font-family: '" + customFont.getName() + "';");
            textHere.setTextAlignment(TextAlignment.CENTER);

        }else{
            textAft = new Text("Forward");
            textAft.setFont(Font.font("Times New Roman", 20));
            textAft.setTextAlignment(TextAlignment.RIGHT);
            textAft.setStyle("-fx-font-weight:bold");

            textHere = new Text("The front of the ship, quicker and close to the spa and suite lounges.");
            textHere.setWrappingWidth(200);
            textHere.setStyle("-fx-fill: black;-fx-font-size: 10px;-fx-font-family: '" + customFont.getName() + "';");
            textHere.setTextAlignment(TextAlignment.CENTER);
        }

        column.setAlignment(Pos.CENTER);
        column.getChildren().addAll(textAft, textHere);
        return column;
    }

    public VBox bottomVB(String columnName){
        // Create a new VBox to hold the bottom section content of each column
        VBox Vbutton = new VBox();

        // Set background color for the VBox
        Vbutton.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));

        // Create a "Select" button
        selectButton = new Button("Select");

        // Set preferred size and styling for the button
        selectButton.setPrefSize(60, 30);
        selectButton.setStyle("-fx-background-color: transparent; -fx-border-color: #91C9FF; -fx-border-width: 1;-fx-text-fill:#4F95DA;-fx-font-family: Lato; -fx-font-weight:bold;-fx-font-size:13px;");
        selectButton.setOnMouseEntered(e -> selectButton.setStyle("-fx-background-color: #4F95DA; -fx-border-color: #91C9FF; -fx-border-width: 1;-fx-text-fill:white;-fx-font-family: Lato; -fx-font-weight:bold;-fx-font-size:13px;"));
        selectButton.setOnMouseExited(e -> selectButton.setStyle("-fx-background-color: transparent; -fx-border-color: #91C9FF; -fx-border-width: 1;-fx-text-fill:#4F95DA;-fx-font-family: Lato; -fx-font-weight:bold;-fx-font-size:13px;"));

        // Set alignment for the VBox
        Vbutton.setAlignment(Pos.CENTER);

        // Handle the button's action
        selectButton.setOnAction(e -> {

            // Define a custom semi-transparent color
            Color customColor = new Color(211.0 / 255, 234.0 / 255, 242.0 / 255, 0.4);

            // Determine background color based on the column name
            String backgroundColor = columnName.equals("Column 1") ? toRGBCode(customColor) : "white";
            String backgroundColor1 = columnName.equals("Column 2") ? toRGBCode(customColor) : "white";
            String backgroundColor2 = columnName.equals("Column 3") ? toRGBCode(customColor) : "white";

            // Apply background colors and styles to column VBoxes
            column1.setStyle("-fx-background-color: " + backgroundColor + "; -fx-background-radius: 0; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.7), 10, 0, 0, 0)");
            column2.setStyle("-fx-background-color: " + backgroundColor1 + "; -fx-background-radius: 0; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.7), 10, 0, 0, 0)");
            column3.setStyle("-fx-background-color: " + backgroundColor2 + "; -fx-background-radius: 0; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.7), 10, 0, 0, 0)");


            // Update VBox background colors based on selection
            updateVBoxBackgrounds(column1, columnName.equals("Column 1"));
            updateVBoxBackgrounds(column2, columnName.equals("Column 2"));
            updateVBoxBackgrounds(column3, columnName.equals("Column 3"));

            // Set the selected room place
            if (columnName.equals("Column 1")) {
                selectButton.setUserData("Aft");
            } else if (columnName.equals("Column 2")) {
                selectButton.setUserData("MidShip");
            } else if (columnName.equals("Column 3")) {
                selectButton.setUserData("Forward");
            }

            System.out.println(selectButton);

            selectedButton = selectButton.getUserData().toString();

        });

        // Add the "Select" button to the VBox
        Vbutton.getChildren().addAll(selectButton);
        return Vbutton;
    }

    // Helper method to convert Color to RGB hex code
    public static String toRGBCode(Color color) {
        return String.format("#%02X%02X%02X",
                (int) (color.getRed() * 255),
                (int) (color.getGreen() * 255),
                (int) (color.getBlue() * 255));
    }

    private void updateVBoxBackgrounds(VBox columnVBox, boolean isSelected) {

        // Iterate through the child nodes of the column VBox
        for (Node node : columnVBox.getChildren()) {
            if (node instanceof VBox) {

                // Define the background color based on whether the column is selected
                Color backgroundColor = isSelected ? new Color(211.0/255, 234.0/255, 242.0/255, 0.4) : Color.WHITE;

                // Set the background color of the inner VBox
                ((VBox) node).setBackground(new Background(new BackgroundFill(backgroundColor, CornerRadii.EMPTY, Insets.EMPTY)));
            }
        }
    }

    public VBox centerVB(String columnName){
        VBox vbox = new VBox();
        vbox.setFillWidth(true);
        vbox.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        if(columnName.equals("Column 1")){

            // Load the image for column 1
            Image image = new Image(getClass().getResourceAsStream("ship3.png"));

            // Create an ImageView to display the image
            ImageView imageView = new ImageView(image);
            // Add the ImageView to the VBox
            imageView.setFitHeight(62);
            imageView.setFitWidth(168);
            vbox.getChildren().add(imageView);

        } else if (columnName.equals("Column 2")) {
            Image image = new Image(getClass().getResourceAsStream("ship2.png"));

            // Create an ImageView to display the image
            ImageView imageView = new ImageView(image);
            imageView.setFitHeight(62);
            imageView.setFitWidth(168);
            // Add the ImageView to the VBox
            vbox.getChildren().add(imageView);

        }else{
            Image image = new Image(getClass().getResourceAsStream("ship1.png"));

            // Create an ImageView to display the image
            ImageView imageView = new ImageView(image);
            imageView.setFitHeight(62);
            imageView.setFitWidth(168);
            // Add the ImageView to the VBox
            vbox.getChildren().add(imageView);
        }

        vbox.setAlignment(Pos.CENTER);
        return vbox;
    }
    public BorderPane CenterBorderPane(){

        // Load custom fonts
        Font customFont = Font.loadFont(getClass().getResourceAsStream("EmilysCandy-Regular.ttf"), 18);

        Text txtTitle = new Text("Room "+ index+ " \n Good choice, now where do you want your room?");
        txtTitle.setFont(Font.font("Eras Demi ITC", 25));
        txtTitle.setTextAlignment(TextAlignment.CENTER);
        txtTitle.setStyle("-fx-fill: white;-fx-font-size: 25px;-fx-font-family: '" + customFont.getName() + "';");

        btnBack = new Hyperlink("Back to Select Place");

        // Define the styles for normal and hover states
        String normalStyle = "-fx-text-fill: #0070E0; -fx-underline: false;-fx-font-family:Lato;-fx-font-size:16px;-fx-font-weight:bold;";
        String hoverStyle = "-fx-text-fill: #00ff05;-fx-underline: true;-fx-font-family:Lato; -fx-font-size:16px;-fx-font-weight:bold;";

        btnBack.setStyle(normalStyle);

        btnBack.setOnMouseEntered(event -> btnBack.setStyle(hoverStyle));
        btnBack.setOnMouseExited(event -> btnBack.setStyle(normalStyle));

        VBox titleVBox = new VBox(txtTitle, btnBack);
        titleVBox.setAlignment(Pos.TOP_CENTER);
        titleVBox.setSpacing(20);

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

        /// No gap between columns/ Create UI elements
        column1 = createColumn("Column 1");
        column2 = createColumn("Column 2");
        column3 = createColumn("Column 3");

        // Create a layout pane and add columns
        HBox root = new HBox(column1, column2, column3);
        root.setSpacing(0);


        Button btnConfirm = new Button("Confirm");

        btnConfirm.setPrefSize(100, 40);
        btnConfirm.setStyle("-fx-background-color: #4F95DA; -fx-border-color: #91C9FF; -fx-border-width: 1;-fx-text-fill:white;-fx-font-family: Lato; -fx-font-weight:bold;-fx-font-size:18px;");
        btnConfirm.setOnMouseEntered(e -> btnConfirm.setStyle("-fx-background-color: transparent; -fx-border-color: #91C9FF; -fx-border-width: 1;-fx-text-fill:#4F95DA;-fx-font-family: Lato; -fx-font-weight:bold;-fx-font-size:18px;"));
        btnConfirm.setOnMouseExited(e -> btnConfirm.setStyle("-fx-background-color: #4F95DA; -fx-border-color: #91C9FF; -fx-border-width: 1;-fx-text-fill:white;-fx-font-family: Lato; -fx-font-weight:bold;-fx-font-size:18px;"));

        btnConfirm.setOnAction(event -> {
            selectedButton = selectButton.getUserData().toString();

            if(selectedButton == null){
                btnConfirm.setDisable(true);
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Room Selection Required");
                alert.setHeaderText(null);
                alert.setContentText("Please select a room place before continuing.");
                alert.showAndWait();
            }else {
                PlaceDetails data = new PlaceDetails(index, selectedButton);
                placeDetails.add(data);
                for (PlaceDetails placeDT : placeDetails) {
                    System.out.println(placeDT);
                }
                index++;
                ControlVBox();
            }
            btnConfirm.setDisable(false);

        });
        HBox hBoxBtnConfirm = new HBox(btnConfirm);
        hBoxBtnConfirm.setAlignment(Pos.CENTER_RIGHT);


        VBox allVBox = new VBox(titleVBox, root, hBoxBtnConfirm);
        allVBox.setSpacing(20);

        BorderPane borderPane = new BorderPane(allVBox);
        BorderPane.setMargin(allVBox, new Insets(30,10,30,10));


        return borderPane;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public ArrayList<PlaceDetails> getPlaceDetails() {
        return placeDetails;
    }

    public void iterator(int index){
        Iterator<PlaceDetails> iterator = placeDetails.iterator();
        while (iterator.hasNext()) {
            PlaceDetails place = iterator.next();
            if (place.getIndex() == index || place.getIndex() == index + 1) {
                iterator.remove();
            }
        }
    }


}