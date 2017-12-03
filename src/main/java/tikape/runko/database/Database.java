package tikape.runko.database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Database {

    private String databaseAddress;

    public Database(String databaseAddress) throws ClassNotFoundException {
        this.databaseAddress = databaseAddress;
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(databaseAddress);
    }

    public void init() {
        List<String> lauseet = sqliteLauseet();

        // "try with resources" sulkee resurssin automaattisesti lopuksi
        try (Connection conn = getConnection()) {
            Statement st = conn.createStatement();

            // suoritetaan komennot
            for (String lause : lauseet) {
                System.out.println("Running command >> " + lause);
                st.executeUpdate(lause);
            }

        } catch (Throwable t) {
            // jos tietokantataulu on jo olemassa, ei komentoja suoriteta
            System.out.println("Error >> " + t.getMessage());
        }
    }

    private List<String> sqliteLauseet() {
        ArrayList<String> lista = new ArrayList<>();

        // tietokantataulujen luomiseen tarvittavat komennot suoritusjärjestyksessä
        lista.add("CREATE TABLE Annos (id integer PRIMARY KEY, nimi varchar(255));");
        lista.add("INSERT INTO Annos (nimi) VALUES ('Vihersmoothie');");
        lista.add("INSERT INTO Annos (nimi) VALUES ('Kaakaosmoothie');");
        
        lista.add("CREATE TABLE RaakaAine (id integer PRIMARY KEY, nimi varchar(255));");
        lista.add("INSERT INTO RaakaAine (nimi) VALUES ('Banaani');");
        lista.add("INSERT INTO RaakaAine (nimi) VALUES ('Chlorella');");
        lista.add("INSERT INTO RaakaAine (nimi) VALUES ('Vihersmoothiejauhe');");
        lista.add("INSERT INTO RaakaAine (nimi) VALUES ('Raakakaakaojauhe');");
        lista.add("INSERT INTO RaakaAine (nimi) VALUES ('Maca-jauhe');");
        lista.add("INSERT INTO RaakaAine (nimi) VALUES ('Omena');");
        lista.add("INSERT INTO RaakaAine (nimi) VALUES ('Vesi');");
        lista.add("CREATE TABLE AnnosRaakaAine (id integer PRIMARY KEY, annos_id integer, "
                + "annos_nimi varchar(255), raaka_aine_id integer, raaka_aine_nimi varchar(255), "
                + "jarjestys integer, maara varchar(20), ohje varchar(255),"
                + "FOREIGN KEY (annos_id) REFERENCES Annos(id), FOREIGN KEY (raaka_aine_id) "
                + "REFERENCES RaakaAine(id));");
        
        lista.add("INSERT INTO AnnosRaakaAine (annos_id, annos_nimi, raaka_aine_id, raaka_aine_nimi, jarjestys, maara, ohje) VALUES (1, 'Vihersmoothie', 1, 'Banaani', 1, '1 kpl', 'Pilko banaani blenderiin.');");
        lista.add("INSERT INTO AnnosRaakaAine (annos_id, annos_nimi, raaka_aine_id, raaka_aine_nimi, jarjestys, maara, ohje) VALUES (1, 'Vihersmoothie', 2, 'Chlorella', 2, '2 teelusikallista', 'Lisää blenderiin chlorella.');");
        lista.add("INSERT INTO AnnosRaakaAine (annos_id, annos_nimi, raaka_aine_id, raaka_aine_nimi, jarjestys, maara, ohje) VALUES (1, 'Vihersmoothie', 3, 'Vihersmoothiejauhe', 3, '1 ruokalusikallinen', 'Lisää blenderiin "
                + "vihersmoothiejauhe.');");
        lista.add("INSERT INTO AnnosRaakaAine (annos_id, annos_nimi, raaka_aine_id, raaka_aine_nimi, jarjestys, maara, ohje) VALUES (1, 'Vihersmoothie', 7, 'Vesi', 4, 'sopivasti', 'Lisää lopuksi sopivasti vettä "
                + "ja sekoita aineksia blenderissä noin 10 sekunnin ajan.');");
        

        return lista;
    }
}
