package org.example.exceptions;

import java.sql.SQLException;

public class ObjectNotFoundException extends SQLException {

    public ObjectNotFoundException() {
        super("Object not found");
    }
}
