import javafx.application.Application;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.Text;

public class Olympic extends Application
{
    public void start(Stage primaryStage)
    {
        Circle blue = new Circle(100, 140, 60);
        blue.setFill(null);
        blue.setStroke(Color.BLUE);
        blue.setStrokeWidth(10);

        Circle black = new Circle(250, 140, 60);
        black.setFill(null);
        black.setStroke(Color.BLACK);
        black.setStrokeWidth(10);

        Circle red = new Circle(400, 140, 60);
        red.setFill(null);
        red.setStroke(Color.RED);
        red.setStrokeWidth(10);

        Circle orange = new Circle(175, 200, 60);
        orange.setFill(null);
        orange.setStroke(Color.ORANGE);
        orange.setStrokeWidth(10);

        Circle green = new Circle(325, 200, 60);
        green.setFill(null);
        green.setStroke(Color.GREEN);
        green.setStrokeWidth(10);

        Group root = new Group(blue, black, red, orange, green);
        Scene scene = new Scene(root, 500, 350, Color.WHITE);

        primaryStage.setTitle("Olympic");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}