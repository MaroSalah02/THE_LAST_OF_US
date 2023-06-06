package views;

import javafx.scene.control.ProgressBar;
import javafx.scene.paint.Color;

public class HealthBar extends ProgressBar {

    public HealthBar(double initialHealth) {
        setProgress(initialHealth);
        this.setStyle("-fx-accent: red; -fx-control-inner-background: black;-fx-pref-height: 8px;");
    }

    public void setValue(double newValue) {
        setProgress(newValue);
    }
}