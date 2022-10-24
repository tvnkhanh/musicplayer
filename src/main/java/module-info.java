module ptit.tvnkhanh.musicplayerproject {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;

    opens ptit.tvnkhanh.musicplayerproject to javafx.fxml;
    exports ptit.tvnkhanh.musicplayerproject;
}