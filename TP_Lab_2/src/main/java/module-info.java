module org.JavaCatamaran {
    requires javafx.controls;
    requires javafx.fxml;
    requires log4j;

    opens org.JavaCatamaran to javafx.fxml;
    exports org.JavaCatamaran;
}