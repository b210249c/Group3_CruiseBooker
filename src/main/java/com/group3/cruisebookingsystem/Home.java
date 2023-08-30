package com.group3.cruisebookingsystem;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import javafx.scene.paint.Color;
import javafx.scene.text.FontWeight;


public class Home{
    private CruiseBookingSystem cbs;
    private Login login;
    public Home(CruiseBookingSystem cbs,Login login) {
        this.cbs = cbs;
        this.login = login;
    }
    public Scene homeScene() {

        BorderPane borderPane = new BorderPane();

        // Set background image
        Image image = new Image(CruiseBookingSystem.class.getResource("homebg.png").toString(), 1366,768,false,true);
        BackgroundImage bI = new BackgroundImage(image,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);

        borderPane.setBackground(new Background(bI));

        // Set top navigation bar and center information
        borderPane.setTop(TopNavigationBar());
        borderPane.setCenter(CenterInformation());


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

    public BorderPane CenterInformation(){

        VBox textVB = new VBox();
        Font customFont = Font.loadFont(getClass().getResourceAsStream("YesevaOne-Regular.ttf"), 18);

        //Center text Home
        Text description = new Text("Set Sail for Unforgettable Adventures.");
        Text description1 = new Text("Your Journey Begins Here");
        description.setWrappingWidth(1000);
        description.setTextAlignment(TextAlignment.CENTER);
        description.setStyle("-fx-fill: white;-fx-font-size: 45px;-fx-font-family: '" + customFont.getName() + "';");
        description1.setWrappingWidth(1000);
        description1.setTextAlignment(TextAlignment.CENTER);
        description1.setStyle("-fx-fill: white;-fx-font-size: 35px;");


        // Create the button
        Button btn = new Button();
        btn.setPrefSize(180, 60);
        btn.setStyle("-fx-background-color: transparent; -fx-border-color: #60442a; -fx-border-width: 1;");
        btn.setOnMouseEntered(e -> btn.setStyle("-fx-background-color: #60442a; -fx-border-color: #60442a; -fx-border-width: 1;"));
        btn.setOnMouseExited(e -> btn.setStyle("-fx-background-color: transparent; -fx-border-color: #60442a; -fx-border-width: 1;"));

        Text buttonText = new Text("Book Now");
        buttonText.setFill(Color.WHITE);
        buttonText.setFont(Font.font("Lato", FontWeight.BOLD, 18));

        btn.setGraphic(buttonText);

        btn.setOnAction(event -> {
            cbs.switchToSearchScene();
        });

        textVB.getChildren().addAll(description,description1);
        textVB.setAlignment(Pos.CENTER);

        VBox.setMargin(textVB,new Insets(60,0,295,0));
        VBox vBox = new VBox(textVB, btn);
        vBox.setAlignment(Pos.TOP_CENTER);
        vBox.setSpacing(20);

        BorderPane borderPane = new BorderPane(vBox);

        return borderPane;
    }

}
