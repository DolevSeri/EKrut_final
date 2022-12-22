package server;
import javafx.application.Platform;
import javafx.scene.control.TextArea;

// a custom output stream that writes to a JavaFX text area
public class Console extends java.io.OutputStream {
    private final TextArea textArea;

    public Console(TextArea textArea) {
        this.textArea = textArea;
    }

    @Override
    public void write(int b) {
        // update the text area on the JavaFX application thread
        Platform.runLater(() -> textArea.appendText(String.valueOf((char) b)));
    }
}
