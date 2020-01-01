package sample;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.text.Text;
import javafx.scene.control.Button;

public class NumButton extends Application {
    private Text randomText;
    public void start(Stage primaryStage) {
        randomText = new Text("🎲Number: 0\n");
        randomText.setFill(Color.GRAY);
        randomText.setFont(Font.font (30));
        Button shake = new Button("Shake🤘Shake");

        shake.setOnAction(this::processButtonPress);

        FlowPane pane = new FlowPane(randomText, shake);
        pane.setAlignment(Pos.CENTER);
        pane.setHgap(20);
        pane.setStyle("-fx-background-color: white");

        Scene scene = new Scene(pane, 300, 300);

        primaryStage.setTitle("ShakeShake");
        primaryStage.setScene(scene);
        primaryStage.show();

    }
    public void processButtonPress(ActionEvent event) {
        randomText.setText("🎲Number: " + (int)(1+Math.random()*(100-1+1)) + "\n");
}

}
