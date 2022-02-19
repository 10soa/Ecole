import java.util.*;
import java.sql.*;
public class Promotion {
    int id;
    String nom;
    int statut;

    ArrayList<Promotion> gepostgresrom() throws ClassNotFoundException,SQLException,InstantiationException,IllegalAccessException
    {

        ArrayList list =  null;
        Connection connection = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            Class.forName("org.postgresql.Driver").newInstance();
            list =  new ArrayList<Promotion>();
            connection = DriverManager.getConnection( "jdbc:postgresql://localhost/"+"ecole","postgres"," ");
            stmt = connection.createStatement();
            rs = stmt.executeQuery("select*from promotion where statut=0"); 
            while(rs.next())
            {
                Promotion prom = new Promotion();

                prom.setStatut(rs.getInt("statut"));
                prom.setId(rs.getInt("id"));
                prom.setNom(rs.getString("nom"));
                list.add(prom);
            }

        } catch (Exception e) {
            //TODO: handle exception
            throw e;
        } finally{
            
            rs.close();
            stmt.close();
            connection.close();
        }
            
        return list;
    }

    
    public int getId() {
        return id;
    }
    public String getNom() {
        return nom;
    }
    public int getStatut() {
        return statut;
    }
    public void setId(int id) {
        this.id = id;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }
    public void setStatut(int statut) {
        this.statut = statut;
    }
    
}
