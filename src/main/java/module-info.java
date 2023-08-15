module ICTSkills {
    requires javafx.graphics;
    requires org.hibernate.orm.core;
    requires jakarta.persistence;
    requires java.naming;
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.databind;
    requires com.opencsv;

    opens ch.albin.ictskills.model;
    opens ch.albin.ictskills.controller to javafx.fxml;

    exports ch.albin.ictskills to javafx.graphics;
    exports ch.albin.ictskills.controller to javafx.fxml;

    exports ch.albin.ictskills.model.viewModel;
    exports ch.albin.ictskills.model;
    exports ch.albin.ictskills.util.files;
    exports ch.albin.ictskills.db;
}