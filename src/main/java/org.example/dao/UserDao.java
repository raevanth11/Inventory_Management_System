package org.example.dao;
import org.example.model.User;

import java.sql.SQLException;

public interface UserDao {
    boolean addUser(User user) throws SQLException ;
    User getUserByUsername(String username) throws SQLException;
    boolean deleteUser(String username) throws SQLException;

}