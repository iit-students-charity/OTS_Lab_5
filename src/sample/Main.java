package sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Group root = new Group();
        final Scene scene = new Scene(root, 800, 400);
        stage.setTitle("Laboratory work 5");
        final VBox vBox = new VBox();
        vBox.setPadding(new Insets(20));

        Integer altCount = 5;
        Integer crCount = 5;
        Integer defaultTextFieldWeight = 3;
        GridPane grid = new GridPane();
        grid.setHgap(7);
        grid.setVgap(7);

        for (int i = 0; i < crCount; i++) {
            TextField addedTextField1 = new TextField();
            TextField addedTextField2 = new TextField();

            addedTextField1.setId("sCoefficient" + String.valueOf(i+1));
            addedTextField1.setPrefColumnCount(defaultTextFieldWeight);
            grid.add(addedTextField1, altCount + 1, i + 1);

            addedTextField2.setId("coefficient" + String.valueOf(i+1));
            addedTextField2.setPrefColumnCount(defaultTextFieldWeight);
            grid.add(addedTextField2, altCount + 2, i + 1);
        }

        grid.add(new Label("Простота ремонта"), 0, 1);
        grid.add(new Label("Цена"), 0, 2);
        grid.add(new Label("Доступность комплектующих"), 0, 3);
        grid.add(new Label("Длинна провода"), 0, 4);
        grid.add(new Label("Вес"), 0, 5);

        grid.add(new Label("Значение Si"), altCount + 1, 0);
        grid.add(new Label("Коэффициент"), altCount + 2, 0);

        for (int i = 0; i < altCount; i++) {
            grid.add(new Label("Тостер " + String.valueOf(i + 1)), i + 1, 0);
            for (int j = 0; j < crCount; j++) {
                TextField addedTextField = new TextField();
                addedTextField.setPrefColumnCount(defaultTextFieldWeight);
                addedTextField.setId("textField" + String.valueOf(i) + String.valueOf(j));
                grid.add(addedTextField, i + 1, j + 1);
            }
        }
        Button button = new Button("Рассчитать");
        button.setId("submit button");
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                double[] sCoefficients = new double[crCount];
                double[][] criteries = new double[altCount][crCount];
                double[] coefficients = new double[crCount];
                double[] results = new double[altCount];

                for (int i = 0; i < crCount; i++) {
                    TextField findedTField1 = (TextField) scene.lookup("#sCoefficient" + String.valueOf(i + 1));
                    sCoefficients[i] = Double.valueOf(findedTField1.getText());
                    TextField findedTField2 = (TextField) scene.lookup("#coefficient" + String.valueOf(i + 1));
                    coefficients[i] = Double.valueOf(findedTField2.getText());
                }

                for (int i = 0; i < altCount; i++) {
                    results[i] = 1;
                    for (int j = 0; j < crCount; j++) {
                        TextField findedTField = (TextField) scene.lookup("#textField" + String.valueOf(i) + String.valueOf(j));
                        criteries[i][j] = (Double.valueOf(findedTField.getText()));
                    }
                }

                for (int i = 0; i < altCount; i++) {
                    for (int j = 0; j < crCount; j++) {
                        results[i] *= 1 - criteries[i][j] * coefficients[j] / sCoefficients[j];
                    }
                }

                int max = 0;
                for (int i = 0; i < altCount; i++) {
                    Text text = new Text("Тостер " + String.valueOf(i + 1) + " : " + "1 - q0(" + (i+1) + ") = " + String.valueOf(results[i]));
                    vBox.getChildren().add(text);
                    if (results[i] < results[max])
                        max = i;
                }


                Text text = new Text("Лучшая альтернатива: Тостер " + String.valueOf(max + 1));
                vBox.getChildren().add(text);

            }
        });
        grid.add(button, altCount + 2, crCount + 2);
        vBox.getChildren().add(grid);

        root.getChildren().add(vBox);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}