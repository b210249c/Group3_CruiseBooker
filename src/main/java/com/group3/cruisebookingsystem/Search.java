package com.group3.cruisebookingsystem;

import javafx.geometry.Insets;
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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Search {
    private String selectedCountry = "All";
    private String selectedDuration = "All";
    private String selectedBtnDetails;
    private ResultSet resultSet;
    private ComboBox<String> category;
    private ComboBox<String> duration;
    private VBox mainVBox = new VBox();
    private Login login;
    private DatabaseManager database;
    private CruiseBookingSystem cbs;
    public Search(CruiseBookingSystem cbs, Login login) {
        this.cbs = cbs;
        this.login = login;
    }
    public Scene searchScene() {

        BorderPane intBorderPane = new BorderPane();
        intBorderPane.setTop(titleVBox());

        //Create Scroll for page
        ScrollPane scrollPane = new ScrollPane(CruiseBorderPane());
        scrollPane.setFitToHeight(true);
        scrollPane.setStyle("-fx-background-color: transparent;");
        scrollPane.setFitToWidth(true);

        intBorderPane.setCenter(scrollPane);

        BorderPane mainBorderPane = new BorderPane();

        //Set background
        Image image = new Image(CruiseBookingSystem.class.getResource("search1.png").toString(), 1366,768,false,true);
        BackgroundImage bI = new BackgroundImage(image,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        mainBorderPane.setBackground(new Background(bI));

        mainBorderPane.setTop(TopNavigationBar());
        mainBorderPane.setCenter(intBorderPane);

        return new Scene(mainBorderPane, 1039, 694);
    }

    public BorderPane TopNavigationBar(){

        //Logout button
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

        //Hover, Exit, Click button
        btnLogout.setOnMouseEntered(event -> {
            btnLogout.setStyle("-fx-background-color: rgba(0,85,255, 0.8); -fx-border-color: transparent; -fx-background-radius: 25px;-fx-text-fill: #cfd7d9; -fx-padding: 5px 5px; -fx-font-size: 15px;-fx-font-family: lato;-fx-font-weight: bold;");
        });

        btnLogout.setOnMouseExited(event -> {
            btnLogout.setStyle("-fx-background-color: #cfd7d9; -fx-border-color: transparent; -fx-background-radius: 25px;-fx-text-fill: #0e3641; -fx-padding: 5px 5px; -fx-font-size: 15px;-fx-font-family: lato;-fx-font-weight: bold;");
        });
        btnLogout.setOnAction(event -> {
            cbs.switchToMainScene();
        });

        //Navigation bar (Welcome Text + Logout button)
        Text welcome = new Text("Welcome, "+login.getUsername());
        welcome.setStyle("-fx-fill: white;");
        welcome.setFont(Font.font("Eras Demi ITC", 20));

        HBox hBox = new HBox(welcome, btnLogout);
        hBox.setSpacing(20);
        hBox.setPadding(new Insets(20));
        hBox.setAlignment(Pos.BASELINE_RIGHT);

        //Top Navigation Bar color
        BorderPane borderPane = new BorderPane(hBox);
        borderPane.setStyle("-fx-background-color: rgba(0,0,0,0.3);");

        return borderPane;
    }

    //Contruct search info
    public VBox titleVBox(){
        Font customFont = Font.loadFont(getClass().getResourceAsStream("Righteous-Regular.ttf"), 18);

        //Title
        Text title = new Text("Find your Cruise");
        title.setStyle("-fx-font-family: '" + customFont.getName() + "';-fx-font-size: 38px;");
        title.setTextAlignment(TextAlignment.CENTER);

        //Back hyperlink
        Hyperlink backLink = new Hyperlink("Back to Home Page");
        // Define the styles for normal and hover states
        String normalStyle = "-fx-text-fill: #0070E0; -fx-underline: false;-fx-font-family:Lato;-fx-font-size:14px;-fx-font-weight:bold;";
        String hoverStyle = "-fx-text-fill: white;-fx-underline: true;-fx-font-family:Lato; -fx-font-size:14px;-fx-font-weight:bold;";

        backLink.setStyle(normalStyle);

        backLink.setOnMouseEntered(event -> backLink.setStyle(hoverStyle));
        backLink.setOnMouseExited(event -> backLink.setStyle(normalStyle));

        //back hyperlink clicked
        backLink.setOnAction(event -> {
            mainVBox.getChildren().clear();
            selectedCountry = "All";
            selectedDuration = "All";
            category.setPromptText("Select Departure Country");
            duration.setPromptText("Select Duration");
            cbs.switchToHomeScene();

        });

        //List of Country
        category = new ComboBox<>();
        category.getItems().addAll("All", "Malaysia", "Singapore");
        category.setPromptText("Select Departure Country");
        category.setPrefWidth(300);
        category.setOnAction(event -> {
            mainVBox.getChildren().clear();
            selectedCountry = category.getValue();
            category.setPromptText(selectedCountry);
            CruiseBorderPane();
        });

        //List for night
        duration = new ComboBox<>();
        duration.getItems().addAll("All", "1 Night", "2 Nights", "3 Nights", "4 Nights");
        duration.setPromptText("Select Duration");
        duration.setPrefWidth(300);
        duration.setOnAction(event -> {
            mainVBox.getChildren().clear();
            selectedDuration = duration.getValue();
            duration.setPromptText(selectedDuration);
            CruiseBorderPane();
        });

        //Keep combo box
        HBox filterHBox = new HBox(category, duration);
        filterHBox.setSpacing(30);
        filterHBox.setAlignment(Pos.CENTER);

        VBox titleVBox = new VBox(title, backLink, filterHBox);
        titleVBox.setSpacing(30);
        titleVBox.setAlignment(Pos.CENTER);
        titleVBox.setPadding(new Insets(30));

        return titleVBox;
    }

    public VBox CruiseBorderPane(){
        // Initialize database manager
        database = new DatabaseManager();

        try {
            // Check different filter conditions and execute corresponding queries
            if (selectedCountry.equals("All") && selectedDuration.equals("All")) {
                Statement statement = database.getConnection().createStatement();

                resultSet = statement.executeQuery("SELECT country_from, duration, place, cruise_ship, route, price, date FROM cruise_destination");

            } else if ((selectedCountry.equals("Malaysia") || selectedCountry.equals("Singapore")) && selectedDuration.equals("All")) {
                PreparedStatement preparedStatement = database.getConnection().prepareStatement("SELECT country_from, duration, place, cruise_ship, route, price, date FROM cruise_destination WHERE country_from = ?");

                preparedStatement.setString(1, selectedCountry);

                resultSet = preparedStatement.executeQuery();

            } else if (selectedCountry.equals("All") && (selectedDuration.equals("1 Night") || selectedDuration.equals("2 Nights") || selectedDuration.equals("3 Nights") || selectedDuration.equals("4 Nights"))) {
                PreparedStatement preparedStatement = database.getConnection().prepareStatement("SELECT country_from, duration, place, cruise_ship, route, price, date FROM cruise_destination WHERE duration = ?");

                preparedStatement.setString(1, selectedDuration);

                resultSet = preparedStatement.executeQuery();

            } else if ((selectedCountry.equals("Malaysia") || selectedCountry.equals("Singapore")) && (selectedDuration.equals("1 Night") || selectedDuration.equals("2 Nights") || selectedDuration.equals("3 Nights") || selectedDuration.equals("4 Nights"))) {
                PreparedStatement preparedStatement = database.getConnection().prepareStatement("SELECT country_from, duration, place, cruise_ship, route, price, date FROM cruise_destination WHERE country_from = ? AND duration = ?");

                preparedStatement.setString(1, selectedCountry);
                preparedStatement.setString(2, selectedDuration);

                resultSet = preparedStatement.executeQuery();
            }

            // Loop through the result set and create UI elements for each cruise
            while (resultSet.next()){
                Label lblDuration = new Label("Duration: ");
                Label lblDate = new Label("Date: ");
                Label lblCountry = new Label("Departure From: ");
                Label lblCruise_ship = new Label("Cruise Ship: ");
                Label lblRoute = new Label("Route: ");

                // Retrieve data from result set
                Text duration = new Text(resultSet.getString("duration"));
                Text place = new Text(resultSet.getString("place"));
                place.setFont(Font.font("Eras Demi ITC", 15));
                Text date = new Text(resultSet.getString("date"));
                Text country = new Text(resultSet.getString("country_from"));
                Text cruise_ship = new Text(resultSet.getString("cruise_ship"));
                Text route = new Text(resultSet.getString("route"));
                Text price = new Text("From RM* /Person\n" + resultSet.getString("price"));
                price.setTextAlignment(TextAlignment.CENTER);
                price.setFill(Color.WHITE);

                // Create "Select" button for each cruise
                Button btnSelect = new Button("Select");
                btnSelect.setUserData(resultSet.getString("place"));

                btnSelect.setStyle("-fx-background-color: transparent; -fx-border-color: #FFFFFF; -fx-border-width: 1;-fx-text-fill:#FFFFFF;-fx-font-family: Lato; -fx-font-weight:bold;-fx-font-size:13px;");
                btnSelect.setOnMouseEntered(e -> btnSelect.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: #91C9FF; -fx-border-width: 1;-fx-text-fill:#4F95DA;-fx-font-family: Lato; -fx-font-weight:bold;-fx-font-size:13px;"));
                btnSelect.setOnMouseExited(e -> btnSelect.setStyle("-fx-background-color: transparent; -fx-border-color: #FFFFFF; -fx-border-width: 1;-fx-text-fill:#FFFFFF;-fx-font-family: Lato; -fx-font-weight:bold;-fx-font-size:13px;"));

                // Create layout for displaying cruise details
                GridPane gridPane = new GridPane();
                gridPane.addColumn(0, lblDuration, lblDate, lblCountry, lblCruise_ship, lblRoute);
                gridPane.addColumn(1, duration, date, country, cruise_ship, route);
                gridPane.setHgap(10);
                gridPane.setVgap(5);

                // Create left and right VBox for UI layout
                VBox vBoxLeft = new VBox(place, gridPane);
                VBox vBoxRight = new VBox(price, btnSelect);

                vBoxRight.setStyle("-fx-background-color: #3c362a;");
                vBoxRight.setSpacing(30);
                vBoxRight.setPadding(new Insets(0,60,0,60));
                vBoxRight.setAlignment(Pos.CENTER);

                // Handle button click to switch to detail scene
                btnSelect.setOnAction(event -> {
                    selectedBtnDetails = btnSelect.getUserData().toString();
                    cbs.switchToDetailScene();
                });

                vBoxRight.setAlignment(Pos.CENTER);
                vBoxLeft.setSpacing(20);
                BorderPane borderPane = new BorderPane();
                borderPane.setLeft(vBoxLeft);
                borderPane.setRight(vBoxRight);
                BorderPane.setMargin(vBoxLeft, new Insets(30));
                borderPane.setStyle("-fx-background-color: white; -fx-background-radius: 20; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.7), 10, 0, 0, 0);");
                mainVBox.getChildren().add(borderPane);
                mainVBox.setSpacing(20);
                mainVBox.setAlignment(Pos.CENTER);
                mainVBox.setStyle("-fx-background-color: #E3CAB8;");
                mainVBox.setPadding(new Insets(30));
            }

            // Display message if no results found
            if (mainVBox.getChildren().isEmpty()) {
                BorderPane noResultBorderPane = new BorderPane();
                Text txtNoResult = new Text("No results found.");
                txtNoResult.setFont(Font.font("Eras Demi ITC", 60));
                txtNoResult.setFill(Color.RED);
                txtNoResult.setTextAlignment(TextAlignment.CENTER);
                noResultBorderPane.setCenter(txtNoResult);
                noResultBorderPane.setStyle("-fx-background-color: transparent;");

                mainVBox.getChildren().add(noResultBorderPane);
                mainVBox.setAlignment(Pos.CENTER);
                mainVBox.setStyle("-fx-background-color: #E3CAB8;");
                mainVBox.setPadding(new Insets(30));
            }


        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return mainVBox;
    }

    //Get all stored details that a button has clicked
    public String getSelectedBtnDetails() {
        return selectedBtnDetails;
    }

}
