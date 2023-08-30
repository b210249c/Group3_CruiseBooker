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
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Register{
    private CruiseBookingSystem cbs;
    private DatabaseManager database;

    public Register(CruiseBookingSystem cbs) {
        this.cbs = cbs;
    }

    // Method to create the registration scene
    public Scene registerScene()  {


        BorderPane borderPane = new BorderPane();

        // ... Background setup and image loading ...
        Image image = new Image(CruiseBookingSystem.class.getResource("bgforlogin.png").toString(), 1366,768,false,true);
        BackgroundImage bI = new BackgroundImage(image,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);

        borderPane.setBackground(new Background(bI));

        // Center and top content setup
        borderPane.setCenter(CenterRegister());
        borderPane.setTop(TopBackButton());

        return new Scene(borderPane, 1039, 694);
    }

    // Method to create the top back button
    public HBox TopBackButton(){

        Button btnBack = new Button("<");

        // ... Button styling ...
        btnBack.setFont(Font.font("Arial", 35));
        DropShadow dropShadow = new DropShadow();
        dropShadow.setColor(Color.DARKGRAY);
        dropShadow.setRadius(5);
        dropShadow.setOffsetX(2);
        dropShadow.setOffsetY(2);
        btnBack.setEffect(dropShadow);

        btnBack.setStyle(
                "-fx-text-fill: white;" +
                        "-fx-background-color: transparent;" +
                        "-fx-border-radius: 15%;" +
                        "-fx-background-radius: 150%;" +
                        "-fx-padding: 8px 16px;"
        );

        btnBack.setOnMouseEntered(event -> btnBack.setStyle(
                "-fx-text-fill: white;" +
                        "-fx-background-color: #1b2b36;" +
                        "-fx-border-radius: 50%;" +
                        "-fx-background-radius: 50%;" +
                        "-fx-padding: 8px 16px;"
        ));
        btnBack.setOnMouseExited(event -> btnBack.setStyle(
                "-fx-text-fill: white;" +
                        "-fx-background-color: transparent;" +
                        "-fx-border-radius: 50%;" +
                        "-fx-background-radius: 50%;" +
                        "-fx-padding: 8px 16px;"
        ));

        // Back button action
        btnBack.setOnAction(event -> {
            cbs.switchToMainScene();
        });

        HBox hBox = new HBox(btnBack);
        hBox.setAlignment(Pos.BASELINE_LEFT);

        return hBox;
    }

    // Method to create the center content of the registration scene
    public BorderPane CenterRegister(){

        // ... Font and label setup ...
        Font customFont = Font.loadFont(getClass().getResourceAsStream("LilitaOne-Regular.ttf"), 18);
        Font customFont1 = Font.loadFont(getClass().getResourceAsStream("Lato-Regular.ttf"), 18);

        // Sign In label
        Label signInLabel = new Label("Register");
        signInLabel.setStyle("-fx-font-size: 28px; -fx-font-weight: bold;-fx-font-family: '" + customFont.getName() + "';-fx-text-fill: #6699ff;");

        // Username
        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");

        //Email
        TextField emailField = new TextField();
        emailField.setPromptText("Email");

        //Password
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");

        // Sign Up button
        Button signUpButton = new Button("Register");

        // ... Button styling ...
        DropShadow dropShadow = new DropShadow();
        dropShadow.setColor(Color.DARKGRAY);
        dropShadow.setRadius(5);
        dropShadow.setOffsetX(2);
        dropShadow.setOffsetY(2);
        signUpButton.setEffect(dropShadow);

        signUpButton.setStyle("-fx-background-color: #cfd7d9; -fx-background-radius: 25px; -fx-padding: 5px 10px;-fx-text-fill: #0e3641; -fx-font-size: 15px;-fx-font-family: '" + customFont1.getName() + "';-fx-font-weight: bold;");

        signUpButton.setPrefSize(100, 40);

        signUpButton.setOnMouseEntered(event -> {
            signUpButton.setStyle("-fx-background-color: rgba(14,54,65, 0.8); -fx-border-color: transparent; -fx-background-radius: 25px;-fx-text-fill: #cfd7d9; -fx-padding: 5px 10px; -fx-font-size: 15px;-fx-font-family: '" + customFont1.getName() + "';-fx-font-weight: bold;");
        });

        signUpButton.setOnMouseExited(event -> {
            signUpButton.setStyle("-fx-background-color: #cfd7d9; -fx-border-color: transparent; -fx-background-radius: 25px;-fx-text-fill: #0e3641; -fx-padding: 5px 10px; -fx-font-size: 15px;-fx-font-family: '" + customFont1.getName() + "';-fx-font-weight: bold;");
        });
        signUpButton.setOnAction(event -> RegisterAccount(usernameField, emailField, passwordField, database));

        // Sign In text button
        Hyperlink signInLink = new Hyperlink("Have Account? Sign In");

        // ... Link styling ...
        // Define the styles for normal and hover states
        String normalStyle = "-fx-text-fill: #00ffca; -fx-underline: true;";
        String hoverStyle = "-fx-text-fill: #00ff05;-fx-underline: false;";

        signInLink.setStyle(normalStyle);

        signInLink.setOnMouseEntered(event -> signInLink.setStyle(hoverStyle));
        signInLink.setOnMouseExited(event -> signInLink.setStyle(normalStyle));
        signInLink.setOnAction(event -> {
            cbs.switchToLoginScene();
        });

        // VBox setup
        VBox vBox = new VBox(signInLabel, usernameField, emailField, passwordField, signUpButton, signInLink);
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(20);

        // ... BorderPane styling ...
        BorderPane borderPane = new BorderPane(vBox);
        BorderPane.setMargin(borderPane, new Insets(100,200,200,200));
        borderPane.setStyle("-fx-background-color: rgba(255,255,255,0.3); -fx-background-radius: 20; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.7), 10, 0, 0, 0);");
        BorderPane.setMargin(vBox, new Insets(50));

        return borderPane;
    }

    // Perform registration if input is valid
    public void RegisterAccount(TextField usernameField, TextField emailField, PasswordField passwordField, DatabaseManager database){


        try {
            //get all information
            String username = usernameField.getText();
            String email = emailField.getText();
            String userpass = passwordField.getText();

            //create database
            database = new DatabaseManager();

            if (!username.isEmpty() && !email.isEmpty() && !userpass.isEmpty() && !validateEmailRegistered(email,database) && !containsNumbers(username) && validEmailFormat(email) && userpass.length() >= 5) {

                int success = registerUser(username,email, userpass);

                // ... Registration successful handling ...
                if (success>0) {
                    System.out.println("Successful");
                    usernameField.clear();
                    emailField.clear();
                    passwordField.clear();
                    usernameField.setStyle(" ");
                    emailField.setStyle(" ");
                    passwordField.setStyle(" ");
                    System.out.println("Registration failed.");
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Registration successful.");
                    alert.setHeaderText(null);
                    alert.setContentText("Redirect to login page");
                    alert.showAndWait();
                    cbs.switchToLoginScene();

                } else {
                    // Handle registration failure
                    System.out.println("Registration failed.");
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Registration failed.");
                    alert.setHeaderText(null);
                    alert.setContentText("Something went wrong. Please try again later.");
                    alert.showAndWait();
                }
            } else {

                if (containsNumbers(username)) {
                    // ... Invalid username handling ...
                    usernameField.setStyle("-fx-border-color: red;");
                    usernameField.clear();
                    usernameField.setPromptText("Username cannot contain numbers");

                } else if (username.isEmpty()) {

                    usernameField.setStyle("-fx-border-color: red;");
                    usernameField.setPromptText("Please fill in your username.");

                }else{
                    usernameField.setStyle(" ");
                }

                if (validateEmailRegistered(email,database)) {
                    // ... Email already registered handling ...
                    emailField.clear();
                    emailField.setPromptText("Email is already registered");
                    emailField.setStyle("-fx-border-color: red;");

                }else if (!validEmailFormat(email)) {
                    // ... Invalid email format handling ...
                    emailField.setStyle("-fx-border-color: red;");
                    emailField.clear();
                    emailField.setPromptText("Invalid email format");

                }else if (email.isEmpty()) {

                    emailField.setStyle("-fx-border-color: red;");
                    emailField.setPromptText("Please fill in your email.");

                }else{
                    emailField.setStyle(" ");
                }

                if (userpass.length() < 5) {
                    // ... Invalid password length handling ...
                    passwordField.setStyle("-fx-border-color: red;");
                    passwordField.clear();
                    passwordField.setPromptText("Invalid password!");

                }else if (userpass.isEmpty()) {
                    passwordField.setStyle("-fx-border-color: red;");
                    passwordField.setPromptText("Please fill in your password.");
                } else{
                    passwordField.setStyle(" ");
                }
            }

        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    // Method to register a user in the database
    private int registerUser(String username, String email, String password) {
        database = new DatabaseManager();
        int successful=0;

        try {
            PreparedStatement preparedStatement = database.getConnection().prepareStatement("INSERT INTO REGISTRATION (username, email, password) VALUES (?,?,?)");

            preparedStatement.setString(1, username);
            preparedStatement.setString(2, email);
            preparedStatement.setString(3, password);

            successful = preparedStatement.executeUpdate();

            preparedStatement.close();


        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        return successful;
    }

    private boolean validateEmailRegistered(String email, DatabaseManager database) {
        boolean validateRegistered = false;

        try {
            PreparedStatement statement = database.getConnection().prepareStatement("SELECT email FROM REGISTRATION WHERE email = ?");
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                validateRegistered = true;
            }
            statement.close();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return validateRegistered;
    }

    // Method to validate email format
    private boolean validEmailFormat(String email) {
        String regex = "^[A-Za-z0-9+_.-]+@(.+)$";
        return email.matches(regex) && email.contains("@") &&
                email.indexOf('@') > 3 && email.endsWith(".com");
    }

    // Method to check if a string contains numbers
    private boolean containsNumbers(String str) {
        return str.matches(".*\\d.*");
    }
}
