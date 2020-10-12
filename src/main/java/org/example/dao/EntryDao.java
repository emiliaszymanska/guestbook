package org.example.dao;

import org.example.exceptions.ObjectNotFoundException;
import org.example.model.Entry;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class EntryDao {

    private final Connector CONNECTOR;
    private PreparedStatement preparedStatement;

    public EntryDao() {
        this.CONNECTOR = new Connector();
    }

    public void addEntry(Entry entry) throws ObjectNotFoundException {
        String insertStatement = "INSERT INTO entries (content, author, date_and_time) VALUES (?, ?, ?)";

        try {
            CONNECTOR.connect();
            preparedStatement = CONNECTOR.connection.prepareStatement(insertStatement);
            preparedStatement.setString(1, entry.getContent());
            preparedStatement.setString(2, entry.getAuthorName());
            preparedStatement.setObject(3, entry.getDateAndTime());
            preparedStatement.executeUpdate();

            preparedStatement.close();
            CONNECTOR.connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new ObjectNotFoundException();
        }
    }

    public List<Entry> getEntries() throws ObjectNotFoundException {
        List<Entry> entries = new ArrayList<>();
        String selectStatement = "SELECT * FROM entries";

        try {
            CONNECTOR.connect();
            preparedStatement = CONNECTOR.connection.prepareStatement(selectStatement);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Entry entry = getEntry(resultSet);
                entries.add(entry);
            }

            resultSet.close();
            CONNECTOR.connection.close();

            return entries;

        } catch (SQLException e) {
            e.printStackTrace();
            throw new ObjectNotFoundException();
        }
    }

    private Entry getEntry(ResultSet resultSet) throws SQLException {
        Entry entry = new Entry();
        entry.setContent(resultSet.getString("content"))
                .setAuthorName(resultSet.getString("author"))
                .setDateAndTime(resultSet.getObject("date_and_time", LocalDateTime.class));
        return entry;
    }
}
