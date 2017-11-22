/*
* Ez az osztály felelős az adatbázis műveletekért. 
*/

package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DBModel {

    private Connection conn; //az adatbázis kapcsolat
    private PreparedStatement getAllKerdesPstmt; //a kérdések lekérdezése
    private PreparedStatement updateKerdesPstmt; //módosítás
    private PreparedStatement addKerdesPstmt; //új kérdés felvétele
    private PreparedStatement removeKerdesPstmt; //törlés

    /*
    * A konstruktorban történnek az SQL műveletek PreparedStatement segítségével.
    */
    public DBModel(Connection conn) throws SQLException {
        this.conn = conn;
        getAllKerdesPstmt = conn.prepareStatement("SELECT * FROM kerdes");
        updateKerdesPstmt = conn.prepareStatement("UPDATE kerdes SET kerdes=?, valasz0=?,"
                + " valasz1=?, valasz2=?, valasz3=?, helyesValasz=? WHERE idKerdesek=?");
        addKerdesPstmt = conn.prepareStatement("INSERT INTO kerdes (kerdes, valasz0, valasz1, "
                + "valasz2, valasz3, helyesValasz) VALUES (?, ?, ?, ?, ?, ?)");
        removeKerdesPstmt = conn.prepareStatement("DELETE FROM kerdes WHERE idKerdesek = ?");
    }

    public void close() throws SQLException { // ez a metódus zárja le az adatbázis kapcsolatot
        conn.close();
    }

    public List<Kerdes> getAllKerdes() throws SQLException {
        List<Kerdes> kerdesek = new ArrayList<>();
        ResultSet rs = getAllKerdesPstmt.executeQuery();
        while (rs.next()) {
            Kerdes kerdes = new Kerdes(
                    rs.getInt("idKerdesek"), 
                    rs.getString("kerdes"), 
                    rs.getString("valasz0"),
                    rs.getString("valasz1"),
                    rs.getString("valasz2"),
                    rs.getString("valasz3"),
                    rs.getInt("helyesValasz"));
            kerdesek.add(kerdes);
        }
        rs.close();
        return kerdesek;
    }
    
    public void updateKerdes(Kerdes kerdes) throws SQLException {
        updateKerdesPstmt.setString(1, kerdes.getKerdes());
        updateKerdesPstmt.setString(2, kerdes.getValasz0());
        updateKerdesPstmt.setString(3, kerdes.getValasz1());
        updateKerdesPstmt.setString(4, kerdes.getValasz2());
        updateKerdesPstmt.setString(5, kerdes.getValasz3());
        updateKerdesPstmt.setInt(6, kerdes.getHelyesValasz());
        updateKerdesPstmt.setInt(7, kerdes.getIdKerdesek());
        updateKerdesPstmt.executeUpdate();
    }
    
    public void addKerdes(Kerdes kerdes) throws SQLException {
        addKerdesPstmt.setString(1, kerdes.getKerdes());
        addKerdesPstmt.setString(2, kerdes.getValasz0());
        addKerdesPstmt.setString(3, kerdes.getValasz1());
        addKerdesPstmt.setString(4, kerdes.getValasz2());
        addKerdesPstmt.setString(5, kerdes.getValasz3());
        addKerdesPstmt.setInt(6, kerdes.getHelyesValasz());
        addKerdesPstmt.executeUpdate();
    }
    
    public void removeKerdes(Kerdes kerdes) throws SQLException {
        removeKerdesPstmt.setInt(1, kerdes.getIdKerdesek());
        removeKerdesPstmt.executeUpdate();
    }
    
}
