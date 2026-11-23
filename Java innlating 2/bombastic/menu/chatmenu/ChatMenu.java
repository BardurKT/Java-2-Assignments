
package bombastic.menu.chatmenu;

import java.util.function.Consumer;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.image.Image;

public class ChatMenu extends VBox {

    private final TextArea messagesArea;
    private final ListView<String> usersList;
    private final TextField messageInput;
    private final Button sendButton;
    private final Button mainMenuButton;

    private final StringProperty background =
            new SimpleStringProperty("bg1.png");

    public ChatMenu() {

        // Chat messages
        messagesArea = new TextArea();
        messagesArea.setEditable(false);
        messagesArea.setWrapText(true);
        messagesArea.setPromptText("Chat messages appear here...");
        messagesArea.setPrefHeight(280);

        // Online users
        usersList = new ListView<>();
        usersList.setPrefWidth(150);
        usersList.setPrefHeight(280);

        // Section titles
        Label messagesLabel = new Label("Messages");
        Label usersLabel = new Label("Online Players");

        VBox messagesBox = new VBox(8, messagesLabel, messagesArea);
        VBox usersBox = new VBox(8, usersLabel, usersList);

        HBox chatContent = new HBox(15, messagesBox, usersBox);

        HBox.setHgrow(messagesBox, Priority.ALWAYS);
        VBox.setVgrow(messagesArea, Priority.ALWAYS);
        VBox.setVgrow(usersList, Priority.ALWAYS);

        // Message input
        messageInput = new TextField();
        messageInput.setPromptText("Write a message...");

        sendButton = new Button("Send");
        sendButton.setPrefWidth(90);

        messageInput.setOnAction(event -> sendButton.fire());

        HBox inputArea = new HBox(10, messageInput, sendButton);
        inputArea.setAlignment(Pos.CENTER);

        HBox.setHgrow(messageInput, Priority.ALWAYS);

        // Main menu button
        mainMenuButton = new Button("Main Menu");
        mainMenuButton.setPrefWidth(180);

        setAlignment(Pos.CENTER);
        setSpacing(15);
        setPadding(new Insets(20));

        getChildren().addAll(
                chatContent,
                inputArea,
                mainMenuButton
        );

        VBox.setVgrow(chatContent, Priority.ALWAYS);

        // Background synchronization
        background.addListener((observable, oldValue, newValue) ->
                setBackgroundImage(newValue));

        setBackgroundImage(background.get());
    }

    public StringProperty selectedBackgroundProperty() {
        return background;
    }

    public void setBackgroundImage(String imageName) {

        Image image = new Image(
                "file:resources/images/" + imageName
        );

        BackgroundImage backgroundImage = new BackgroundImage(
                image,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(
                        1, 1, true, true, false, true
                )
        );

        setBackground(new Background(backgroundImage));
    }

    // Pass the send action to the controller.
    public void setOnSend(Consumer<String> action) {

        sendButton.setOnAction(event -> {
            String message = messageInput.getText().trim();

            if (!message.isEmpty()) {
                action.accept(message);
                messageInput.clear();
            }
        });
    }

    // Pass the navigation action to the controller.
    public void setOnMainMenu(Runnable action) {
        mainMenuButton.setOnAction(event -> action.run());
    }

    // Update messages without exposing the TextArea.
    public void setMessages(ObservableList<String> messages) {
        messagesArea.setText(String.join("\n", messages));
    }

    // Update online users without exposing the ListView.
    public void setOnlineUsers(ObservableList<String> users) {
        usersList.setItems(users);
    }

    public void addMessage(String message) {
        messagesArea.appendText(message + "\n");
    }
}
