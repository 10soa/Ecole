import java.sql.*;
import java.util.*;

public class Niveau {
    int id;
    String nom;
    float ecolage;

    ArrayList<Niveau> getNiv() throws ClassNotFoundException,SQLException,InstantiationException,IllegalAccessException
    {

        ArrayList list =  null;
        Connection connection = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            Class.forName("org.postgresql.Driver").newInstance();
            list =  new ArrayList<Niveau>();
            connection = DriverManager.getConnection( "jdbc:postgresql://localhost/"+"ecole","postgres"," ");
            stmt = connection.createStatement();
            rs = stmt.executeQuery("select*from Niveau"); 
            while(rs.next())
            {
                Niveau niv = new Niveau();

                niv.setEcolage(rs.getFloat("ecolage"));
                niv.setId(rs.getInt("id"));
                niv.setNom(rs.getString("nom"));

                list.add(niv);
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

    

    public void setEcolage(float ecolage) {
        this.ecolage = ecolage;
    }
    public void setId(int id) {
        this.id = id;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }
    public float getEcolage() {
        return ecolage;
    }
    public int getId() {
        return id;
    }
    public String getNom() {
        return nom;
    }
    
}
