/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tikape.runko.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import tikape.runko.domain.Annos;
import tikape.runko.domain.AnnosRaakaAine;

/**
 *
 * @author antti
 */
public class AnnosRaakaAineDao {
    
    private Database database;

    public AnnosRaakaAineDao(Database database) {
        this.database = database;
    }
    
    
    public List<AnnosRaakaAine> findAll() throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM AnnosRaakaAine");

        ResultSet rs = stmt.executeQuery();
        List<AnnosRaakaAine> annosRaakaAineet = new ArrayList<>();
        while (rs.next()) {
            Integer id = rs.getInt("id");
            Integer annosId = rs.getInt("annos_id");
            Integer raakaAineId = rs.getInt("raaka_aine_id");
            Integer jarjestys = rs.getInt("jarjestys");
            String maara = rs.getString("maara");
            String ohje = rs.getString("ohje");

            annosRaakaAineet.add(new AnnosRaakaAine(id, annosId, raakaAineId, jarjestys, maara, ohje));
        }

        rs.close();
        stmt.close();
        connection.close();

        
        Collections.sort(annosRaakaAineet);
        
        return annosRaakaAineet;
    }
    
    
    public AnnosRaakaAine saveOrUpdate(AnnosRaakaAine object) throws SQLException {
        // simply support saving -- disallow saving if task with 
        // same name exists
        AnnosRaakaAine byCombination = findByCombination(object.getAnnosId(), object.getRaakaAineId());

        if (byCombination != null) {
            return byCombination;
        }

        try (Connection conn = database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO AnnosRaakaAine (annos_id, raaka_aine_id, jarjestys, maara, ohje) VALUES (?, ?, ?, ?, ?)");
            stmt.setInt(1, object.getAnnosId());
            stmt.setInt(2, object.getRaakaAineId());
            stmt.setInt(3, object.getJarjestys());
            stmt.setString(4, object.getMaara());
            stmt.setString(5, object.getOhje());
            stmt.executeUpdate();
        }

        return findByCombination(object.getAnnosId(), object.getRaakaAineId());

    }
    
    private AnnosRaakaAine findByCombination(Integer annosId, Integer raakaAineId) throws SQLException {
        try (Connection conn = database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("SELECT id, annos_id, raaka_aine_id, jarjestys, maara, ohje FROM AnnosRaakaAine WHERE annosId = ? AND raakaAineId = ?");
            stmt.setInt(1, annosId);
            stmt.setInt(2, raakaAineId);

            try (ResultSet result = stmt.executeQuery()) {
                if (!result.next()) {
                    return null;
                }

                return createFromRow(result);
            }
        }
    }
    
    public List<AnnosRaakaAine> findByAnnosId(Integer annosId) throws SQLException {
        List<AnnosRaakaAine> annosRaakaAineet = new ArrayList<>();
        
        try (Connection conn = database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("SELECT id, annos_id, raaka_aine_id, jarjestys, maara, ohje FROM AnnosRaakaAine WHERE annosId = ?");
            stmt.setInt(1, annosId);

            try (ResultSet result = stmt.executeQuery()) {
                while (result.next()) {
                    annosRaakaAineet.add(createFromRow(result));
                }
            }
        }
        
        Collections.sort(annosRaakaAineet);
        
        return annosRaakaAineet;
    }
    
    // public AnnosRaakaAine(Integer id, Integer annosId, Integer raakaAineId, Integer jarjestys, String maara, String ohje)
    
    public AnnosRaakaAine createFromRow(ResultSet resultSet) throws SQLException {
        return new AnnosRaakaAine(resultSet.getInt("id"), resultSet.getInt("annosId"), resultSet.getInt("raakaAineId"), resultSet.getInt("jarjestys"), resultSet.getString("maara"), resultSet.getString("ohje"));
    }
    
}
