module com.github.JoseAngelGiron {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.xml.bind;
    requires java.sql;


    opens com.github.JoseAngelGiron.model.entity to java.xml.bind;
    opens com.github.JoseAngelGiron to javafx.fxml;
    exports com.github.JoseAngelGiron;
    exports com.github.JoseAngelGiron.view;
    opens com.github.JoseAngelGiron.view to javafx.fxml;
    opens com.github.JoseAngelGiron.persistance to java.xml.bind;

    exports com.github.JoseAngelGiron.persistance;
}
