package com.dictionary;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MainService {
    public static void main(String[] args){
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/customerDB", "root", "root");
        }
        catch(SQLException excep) {
            excep.printStackTrace(); // Print the exception
        }
    }


}
