module ptit.tvnkhanh.musicplayerproject {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires javafx.media;
    requires java.sql;

    opens ptit.tvnkhanh.musicplayerproject to javafx.fxml;
    exports ptit.tvnkhanh.musicplayerproject;
    exports ptit.tvnkhanh.musicplayerproject.controller;
    opens ptit.tvnkhanh.musicplayerproject.controller to javafx.fxml;
    exports ptit.tvnkhanh.musicplayerproject.util;
    opens ptit.tvnkhanh.musicplayerproject.util to javafx.fxml;
}