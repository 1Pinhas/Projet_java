module com.ism {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires lombok;
    requires org.hibernate.orm.core;
    requires java.persistence;
    requires org.yaml.snakeyaml;
    requires javafx.base;
    requires javafx.graphics;

    opens com.ism to javafx.fxml, hibernate.entitymanager,
            java.persistence,
            org.hibernate.orm.core, 
            org.yaml.snakeyaml,
            java.sql,
            lombok;
    opens com.ism.entities to org.hibernate.orm.core, java.sql, javafx.base;        
    opens com.ism.controllers to javafx.fxml;
    opens com.ism.repositories.Impl to org.hibernate.orm.core, hibernate.entitymanager, java.sql;
    exports com.ism;
}
