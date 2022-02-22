package direction;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.InvalidPropertiesFormatException;
import java.util.List;
public class EcolageNomPayeEtudiant {
    int id;
    String etu;
    int idProm;
    String nomP;
    float reste;
    int mois;
    int annee;
    int idN;
    String niv;

    public ArrayList<EcolageNomPayeEtudiant> listeMoisActuel() throws ClassNotFoundException,SQLException,InstantiationException,IllegalAccessException

    {
        ArrayList list =  null;
        Connection connection = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            Class.forName("org.postgresql.Driver").newInstance();
            list =  new ArrayList<EcolageNomPayeEtudiant>();
            connection = DriverManager.getConnection( "jdbc:postgresql://localhost/"+"ecole","postgres","andrianjaka");
            stmt = connection.createStatement();
           
            String sql = "select etu,idecolage,idniveau,idpromotion,promotion,niveau_etudiant,date_part('month',now()) mois,date_part('year',now()) annee,(ecolage_aPaye-montant) reste from sommeEcolage_Paye";
            rs = stmt.executeQuery(sql);
            while(rs.next())
            {
                EcolageNomPayeEtudiant enp = new EcolageNomPayeEtudiant();

                enp.setAnnee(Integer.parseInt(rs.getString("annee")));
                enp.setEtu(rs.getString("etu"));
                enp.setId(rs.getInt("idecolage"));
                enp.setIdN(rs.getInt("idniveau"));
                enp.setIdProm(rs.getInt("idpromotion"));
                enp.setMois(Integer.parseInt(rs.getString("mois")));
                enp.setNiv(rs.getString("niveau_etudiant"));
                enp.setNomP(rs.getString("promotion"));

                //transformation
                // String reste = rs.getString("reste");
                // String [] split = reste.substring(1,reste.length()-1).split(",");
                // String value ="";
                // for(int i=0;i<split.length;i++)
                // {
                //     value = value + split[i];
                // }
                
                enp.setReste(rs.getFloat("reste"));

                list.add(enp);
            }

        } catch (Exception e) {
            //TODO: handle exception
            throw e;
        }
        finally{
            
            rs.close();
            stmt.close();
            connection.close();
        }
            
        return list;
    }


    
    // public ArrayList<EcolageNomPayeEtudiant> liste(String id,String annee,String mois,String prom) throws ClassNotFoundException,SQLException,InstantiationException,IllegalAccessException
    // {
    //     ArrayList list =  null;
    //     Connection connection = null;
    //     Statement stmt = null;
    //     ResultSet rs = null;
    //     try {
    //         Class.forName("org.postgresql.Driver").newInstance();
    //         list =  new ArrayList<EcolageNomPayeEtudiant>();
    //         connection = DriverManager.getConnection( "jdbc:postgresql://localhost/"+"ecole","postgres","andrianjaka");
    //         stmt = connection.createStatement();
    //         String sql = "select*from (select EcolageEtudiant.id id,Etudiant.etu etu,Etudiant.idpromotion idprom,Promotion.nom nomP,(niveau.ecolage - EcolageEtudiant.montant) reste,date_part('month',EcolageEtudiant.datee) mois,date_part('year',EcolageEtudiant.datee) annee,EcolageEtudiant.idNiveau idN,niveau.nom niv from EcolageEtudiant join Etudiant on EcolageEtudiant.idEtudiant = Etudiant.id join niveau on Etudiant.idNiveau = niveau.id join Promotion on Promotion.id = Etudiant.idpromotion) as liste where liste.idN ="+ id +" and liste.annee ="+annee+" and liste.mois="+mois+" and liste.nomP='"+prom+"'";
    //         rs = stmt.executeQuery(sql);
    //         while(rs.next())
    //         {
    //             EcolageNomPayeEtudiant enp = new EcolageNomPayeEtudiant();

    //             enp.setAnnee(Integer.parseInt(rs.getString("annee")));
    //             enp.setEtu(rs.getString("etu"));
    //             enp.setId(rs.getInt("id"));
    //             enp.setIdN(rs.getInt("idN"));
    //             enp.setIdProm(rs.getInt("idprom"));
    //             enp.setMois(Integer.parseInt(rs.getString("mois")));
    //             enp.setNiv(rs.getString("niv"));
    //             enp.setNomP(rs.getString("nomP"));
    //             enp.setReste(rs.getFloat("reste"));

    //             list.add(enp);
    //         }

    //     } catch (Exception e) {
    //         //TODO: handle exception
    //         throw e;
    //     }finally{
    //         rs.close();
    //         stmt.close();
    //         connection.close();
    //     }
            
    //     return list;
    // }



    public void setAnnee(int annee) {
        this.annee = annee;
    }
    public void setEtu(String etu) {
        this.etu = etu;
    }
    public void setId(int id) {
        this.id = id;
    }
    public void setIdN(int idN) {
        this.idN = idN;
    }
    public void setIdProm(int idProm) {
        this.idProm = idProm;
    }
    public void setMois(int mois) {
        this.mois = mois;
    }
    public void setNiv(String niv) {
        this.niv = niv;
    }
    public void setNomP(String nomP) {
        this.nomP = nomP;
    }
    public void setReste(float reste) {
        this.reste = reste;
    }
    public int getAnnee() {
        return annee;
    }
    public String getEtu() {
        return etu;
    }
    public int getId() {
        return id;
    }
    public int getIdN() {
        return idN;
    }
    public int getIdProm() {
        return idProm;
    }
    public int getMois() {
        return mois;
    }
    public String getNiv() {
        return niv;
    }
    public String getNomP() {
        return nomP;
    }
    public float getReste() {
        return reste;
    }
}


