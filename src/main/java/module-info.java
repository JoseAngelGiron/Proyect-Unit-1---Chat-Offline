module com.github.JoseAngelGiron {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.xml.bind;

    opens com.github.JoseAngelGiron.model.entity to java.xml.bind;
    opens com.github.JoseAngelGiron to javafx.fxml;
    exports com.github.JoseAngelGiron;
    exports com.github.JoseAngelGiron.controller;
    opens com.github.JoseAngelGiron.controller to javafx.fxml;
}
