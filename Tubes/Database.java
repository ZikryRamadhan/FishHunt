/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tubes;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author user
 */
public class Database {
    
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/fishhunt";
    static final String USER = "root";
    static final String PASS = "";    
    
    static Connection conn;
    static Statement stmt;
    static ResultSet rs;
    
        // Melakukan koneksi ke database
        // harus dibungkus dalam blok try/catch  
    static Connection connect(){
        conn = null;
        try {
            // register driver yang akan dipakai
            Class.forName(JDBC_DRIVER);
            
            // buat koneksi ke database
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            
            // buat objek statement
            stmt = conn.createStatement();
            
            // buat query ke database
            String sql = "";
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }
    
    static void insert(String name, int score){
        if(conn == null){conn = connect();}
        try {
            String sql = "INSERT INTO highscore (username, score) VALUES ('"+name+"','"+score+"')";
            stmt.executeUpdate(sql);
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    static ResultSet show(){
        if(conn == null){conn = connect();}
        try {
            // buat objek statement
            stmt = conn.createStatement();
            
            // buat query ke database
            String sql = "SELECT * FROM highscore ORDER by score DESC";
            
            // eksekusi query dan simpan hasilnya di obj ResultSet
            rs = stmt.executeQuery(sql);
            
            // tampilkan hasil query
            
//            while(rs.next()){
//                //modul7.model.addRow(new Object[]{rs.getInt("bayar") , rs.getDouble("liter")});
//                //System.out.println("bayar: " + rs.getInt("bayar"));
//                //System.out.println("liter: " + rs.getDouble("liter"));
//            }
            
        } catch (Exception e) {
            e.printStackTrace();
        } return rs;   
    }    
}
