package mpianatra;
import java.sql.Date;
import java.sql.*;
import java.util.*;

public class Etudiant {
    int id;
    int idNiveau;
    int idPromotion;
    String etu;
    String nom;
    String prenom;
    String sexe;
    String adresse;
    Date dateNaissance;


    public ArrayList<Etudiant> getEtudiant() throws ClassNotFoundException,SQLException,InstantiationException,IllegalAccessException
    {

        ArrayList list =  null;
        Connection connection = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            Class.forName("org.postgresql.Driver").newInstance();
            list =  new ArrayList<Etudiant>();
            connection = DriverManager.getConnection( "jdbc:postgresql://localhost/"+"ecole","postgres","andrianjaka");
            stmt = connection.createStatement();
            rs = stmt.executeQuery("select*from Etudiant"); 
            while(rs.next())
            {
                Etudiant etudiant = new Etudiant();
                etudiant.setAdresse(rs.getString("adresse"));
                etudiant.setDateNaissance(rs.getDate("datenaissance"));
                etudiant.setEtu(rs.getString("etu"));
                etudiant.setId(rs.getInt("id"));
                etudiant.setidPromotion(rs.getInt("idpromotion"));
                etudiant.setIdNiveau(rs.getInt("idniveau"));
                etudiant.setNom(rs.getString("nom"));
                etudiant.setPrenom(rs.getString("prenom"));
                etudiant.setSexe(rs.getString("sexe"));
                
                list.add(etudiant);
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


    
    // public void insertEcolage(String annee, String mois,String date,String montant,String niv,String id) throws ClassNotFoundException,SQLException,InstantiationException,IllegalAccessException
    // {

    //     ArrayList list =  null;
    //     Connection connection = null;
    //     Statement stmt = null;
    //     String sql = null;
    //     try {
    //         Class.forName("org.postgresql.Driver").newInstance();
    //         list =  new ArrayList<Etudiant>();
    //         connection = DriverManager.getConnection( "jdbc:postgresql://localhost/"+"ecole","tp","secret");
    //         stmt = connection.createStatement();
    //         sql = "insert into Ecolage (id,idetudiant,montant,datee,mois,annee,idniveau) values (nextval('eco'),"+ id +","+montant+",'"+date+"',"+mois+","+annee+","+niv+")";
            
    //         stmt.execute(sql); 

    //     } catch (Exception e) {
    //         //TODO: handle exception
    //         throw e;
    //     } finally{
            
    //         stmt.close();
    //         connection.close();
    //     }
    // }


    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }
    public void setDateNaissance(Date dateNaissance) {
        this.dateNaissance = dateNaissance;
    }
    public void setEtu(String etu) {
        this.etu = etu;
    }
    public void setId(int id) {
        this.id = id;
    }
    public void setIdNiveau(int idNiveau) {
        this.idNiveau = idNiveau;
    }
    public void setidPromotion(int idPromotion) {
        this.idPromotion = idPromotion;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }
    public void setSexe(String sexe) {
        this.sexe = sexe;
    }
    public String getAdresse() {
        return adresse;
    }
    public Date getDateNaissance() {
        return dateNaissance;
    }
    public String getEtu() {
        return etu;
    }
    public int getId() {
        return id;
    }
    public int getIdNiveau() {
        return idNiveau;
    }
    public int getidPromotion() {
        return idPromotion;
    }
    public String getNom() {
        return nom;
    }
    public String getPrenom() {
        return prenom;
    }
    public String getSexe() {
        return sexe;
    }

}
