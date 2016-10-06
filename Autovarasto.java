/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package autovarasto;
import java.sql.*;
import java.util.*;
/**
 *
 * @author AnonymousX
 */


public class Autovarasto {
    private Scanner lukija = new Scanner(System.in);
    private Connection yhteys = null;

    public boolean avaaYhteys() {

	// alla KANTANIMI ja TUNNUS ovat samoja kuin Metropolian käyttäjätunnus.
	// SALASANA on MySQL:n salasana, jonka olet asettanut amme.metropolia.fi-sivulla.

        boolean ok = true;
        try {
            String kanta = "jdbc:mysql://localhost/autovarasto";
            Class.forName("com.mysql.jdbc.Driver");
            try {
                yhteys = DriverManager.getConnection(kanta, "root",
                        "aleksi");
            } catch (SQLException e) {
                System.err.println("Yhteyden avaus epäonnistui.");
                ok = false;
            }
        } catch (Exception e) {
            System.err.println("Ajurin lataus epäonnistui.");
            ok = false;
        } finally {
            return ok;
        }
    }

    public Autovarasto() {
        boolean ok = avaaYhteys();
        if (ok) {
            //operoi();
        }
        //suljeYhteys();
        //lukija.close();
    }

    public void suljeYhteys() {
        if (yhteys != null) {
            try {
                yhteys.close();
            } catch (Exception e) {
            }
        }
    }



    public void poistaDatabase() {
        Statement kysely = null;
        
        try {
            kysely = yhteys.createStatement();
            String merkkijono;
            System.out.print("Anna databasen nimi: ");
            merkkijono = lukija.nextLine();
            int tulos = kysely.executeUpdate("DROP DATABASE " + merkkijono);
            
                
            } catch (SQLException e) {
                System.err.println("Tulosjoukon käsittely "
                        + "epäonnistui.");
         
        } finally {
            try {
                kysely.close();
            } catch (Exception e) {
            }
        }
    }
    public void valitseTietokanta() {
        Statement kysely = null;
        
        try {
            kysely = yhteys.createStatement();
            String merkkijono;
            System.out.print("Anna databasen nimi: ");
            merkkijono = lukija.nextLine();
            int tulos = kysely.executeUpdate("USE " + merkkijono);
            
                
            } catch (SQLException e) {
                System.err.println("Tulosjoukon käsittely "
                        + "epäonnistui.");
         
        } finally {
            try {
                kysely.close();
            } catch (Exception e) {
            }
        }
    }
    public void vapaaKysely() {
        Statement kysely = null;
        System.out.println("Kirjoita kysely: ");
        String merkkijono = lukija.nextLine();
        try {
            kysely = yhteys.createStatement();
            ResultSet tulos = kysely.executeQuery(merkkijono);
            ResultSetMetaData rsmd = tulos.getMetaData();
            
            int i = 0;
            System.out.println("");
            int x = 1;
            try {
                while (tulos.next()) {
                    for ( i = 1; i <= rsmd.getColumnCount(); i++) {
                        if (i > 1) {
                        System.out.print(", ");
                        }

                        int type = rsmd.getColumnType(i);
                        if (type == Types.VARCHAR || type == Types.CHAR) {
                            System.out.print(tulos.getString(i));
                        } else {
                            System.out.print(tulos.getLong(i));
                        }
                    }

                    System.out.println();
                }
                
            } catch (SQLException e) {
                System.err.println("Tulosjoukon käsittely "
                        + "epäonnistui.");
                     
            } finally {
                tulos.close();
            }
        } catch (SQLException e) {
            System.err.println("Kyselyä ei voida suorittaa.");
        } finally {
            try {
                kysely.close();
            } catch (Exception e) {
            }
        }
    }
    public void describeTaulu() {
        Statement kysely = null;
        Statement kysely2 = null;
        System.out.print("Kirjoita halutun taulun nimi taulun tietueiden tyyppien tunnistamiseksi: ");
        String merkkijono = lukija.nextLine();
        System.out.println();
        
        try {
            kysely = yhteys.createStatement();
            ResultSet tulos = kysely.executeQuery("SELECT * FROM " + merkkijono);
            ResultSetMetaData rsmd = tulos.getMetaData();
            
            DatabaseMetaData meta = yhteys.getMetaData();
            ResultSet tablesRs = meta.getPrimaryKeys(null, null, merkkijono);
            meta.getPrimaryKeys(null, null, merkkijono);
            
            while(tablesRs.next()){
                System.out.println("Taulun Primary key :"+tablesRs.getString(4));
            }
 
            System.out.println("No. of columns : " + rsmd.getColumnCount());

              
            int i = 0;
            for(i=1 ; i <= rsmd.getColumnCount() ; i++){
                System.out.print("Field name: " + rsmd.getColumnName(i));
                System.out.println("      Type name : " + rsmd.getColumnTypeName(i));
            }
            System.out.println();
            try {
                while (tulos.next()) {
                   // System.out.println(tulos.getInt(1));
                }
                
            } catch (SQLException e) {
                System.err.println("Tulosjoukon käsittely "
                        + "epäonnistui.");
            } finally {
                tulos.close();
            }
        } catch (SQLException e) {
            System.err.println("Kyselyä ei voida suorittaa.");
        } finally {
            try {
                kysely.close();
            } catch (Exception e) {
            }
        }
    }
    public void paivitaTaulu() {
        Statement kysely = null;
        
        try {
            kysely = yhteys.createStatement();
            String taulunnimi;
            String columname;
            String uusiarvo;
            String indexi;
            String columname2;
            System.out.print("Anna paivitettavan taulun nimi: ");
            taulunnimi = lukija.nextLine();
            System.out.print("Anna  muutettavan columnin nimi: ");
            columname = lukija.nextLine();
            System.out.print("Anna  muutettavan rivin id columnin nimi (eli primary key columnin NIMI): ");
            columname2 = lukija.nextLine();
            System.out.print("Anna  muutettavan rivin indexi (eli primary keyn arvo): ");
            indexi = lukija.nextLine();
            System.out.print("Anna  columnin uusi arvo: ");
            uusiarvo = lukija.nextLine();
           int tulos = kysely.executeUpdate("UPDATE " + taulunnimi + " SET " + columname + " = " +"'" + uusiarvo + "'" + " WHERE " + columname2 + " = " + "'"+ indexi+"'" );
            //int tulos = kysely.executeUpdate("UPDATE tyontekija SET etunimi = 'jokuvaa' WHERE numero = '3'");
                
            } catch (SQLException e) {
                System.err.println("Tulosjoukon käsittely "
                        + "epäonnistui.");
         
        } finally {
            try {
                kysely.close();
            } catch (Exception e) {
            }
        }
    }
    public void Select2() {
        Statement kysely = null;
        try {
            kysely = yhteys.createStatement();
            String taulunnimi;
            String columname;
            String joku = "";
            int maara = 0;
            System.out.print("Anna taulun nimi: ");
            taulunnimi = lukija.nextLine();
            System.out.print("Anna etsittavat columnit pilkulla eritellen: ");
            int s = 0;
            joku = lukija.nextLine();

            System.out.println(joku);
            ResultSet tulos = kysely.executeQuery("SELECT " + joku + " FROM " + taulunnimi);
            ResultSetMetaData rsmd = tulos.getMetaData();
            int i = 0;
            int x = 1;
            try {
                while (tulos.next()) {
                    for ( i = 1; i <= rsmd.getColumnCount(); i++) {
                        if (i > 1) {
                        System.out.print(", ");
                        }

                        int type = rsmd.getColumnType(i);
                        if (type == Types.VARCHAR || type == Types.CHAR) {
                            System.out.print(tulos.getString(i));
                        } else {
                            System.out.print(tulos.getLong(i));
                        }
                    }

                    System.out.println();
                }
                
            } catch (SQLException e) {
                System.err.println("Tulosjoukon käsittely "
                        + "epäonnistui.");
                     
            } finally {
                tulos.close();
            }
        } catch (SQLException e) {
            System.err.println("Kyselyä ei voida suorittaa.");
        } finally {
            try {
                kysely.close();
            } catch (Exception e) {
            }
        }
    }
    public void naytaTaulut() {
        Statement kysely = null;
        
        try {
            
            kysely = yhteys.createStatement();
            String[] types = {"TABLE"};
            DatabaseMetaData md = yhteys.getMetaData();
            ResultSet tulos = md.getTables(null, null, "%", types);

            try {
                while (tulos.next()) {
                    String tableName = tulos.getString(3);
                      System.out.println("Table : " + tableName);
                }
                
            } catch (SQLException e) {
                System.err.println("Tulosjoukon käsittely "
                        + "epäonnistui.");
            } finally {
                tulos.close();
            }
        } catch (SQLException e) {
            System.err.println("Kyselyä ei voida suorittaa.");
        } finally {
            try {
                kysely.close();
            } catch (Exception e) {
            }
        }
    }
        public void poistaTaulu() {
        Statement kysely = null;
        
        try {
            kysely = yhteys.createStatement();
            String merkkijono;
            System.out.print("Anna taulun nimi: ");
            merkkijono = lukija.nextLine();
            int tulos = kysely.executeUpdate("DROP TABLE " + merkkijono);
            
                
            } catch (SQLException e) {
                System.err.println("Tulosjoukon käsittely "
                        + "epäonnistui.");
         
        } finally {
            try {
                kysely.close();
            } catch (Exception e) {
            }
        }
    }
    public void lisaaTauluun() {
        Statement kysely = null;
        String taulunnimi = "";
        ArrayList lista = new ArrayList();
        String arvo = "";
        int maara = 0;
        int s = 0;
        try {

            kysely = yhteys.createStatement();
            System.out.print("Anna taulun nimi johon haluat lisata tietoa: ");
            taulunnimi = lukija.nextLine();
            ResultSet tulos = kysely.executeQuery("SELECT * FROM " + taulunnimi);
            ResultSetMetaData rsmd = tulos.getMetaData();
            System.out.println("Anna uudet arvot, aloita ylimmasta columnista(entterillä eritellen): ");
            maara = rsmd.getColumnCount();
            while(true){
                if(s < maara){
                    arvo = lukija.nextLine();
                    lista.add(arvo);
                   // System.out.println("moivaaa");
                }
                
                else{
                    break;
                }
              s++; 
            }
            System.out.println(maara);
            
            //System.out.println(lista.get(0));
            //System.out.println(lista.get(1));
            
            String sql = "INSERT INTO " + taulunnimi + " VALUES(";
            s = 0;
            while(true){
                if(s == (rsmd.getColumnCount()-1)){
                    sql = sql + "'" + lista.get(s) + "')";
                    break;        
                }
                
                else if(s < rsmd.getColumnCount()){
                    sql = sql + "'" + lista.get(s)+ "'" + ", ";
                }

              s++;               
            }
            System.out.println(sql);

             kysely.executeUpdate(sql);
                
            } catch (SQLException e) {
                System.err.println("Tulosjoukon käsittely "
                        + "epäonnistui.");
         
        } finally {
            try {
                kysely.close();
            } catch (Exception e) {
            }
        }
    }
        public void poistaTaulunTietoja() {
        Statement kysely = null;
        ArrayList lista = new ArrayList();
        String arvo = "";
        String sql = "";
        int s = 0;
        try {
            
            kysely = yhteys.createStatement();
            System.out.print("Anna taulun nimi: ");
            arvo = lukija.nextLine();
            lista.add(arvo);
            
            System.out.print("Anna poistettavan rivin jonkun columnin nimi(esim. primary key-columnin nimi): ");
            arvo = lukija.nextLine();
            lista.add(arvo);
            System.out.print("Anna asken asettamasi columnin nimen arvo(esim. primary key arvo): ");
            arvo = lukija.nextLine();
            lista.add(arvo);
            sql = "DELETE FROM " + lista.get(0) + " WHERE ";
            sql = sql + " " + lista.get(1) + " = '" + lista.get(2) + "'" ;
            System.out.println(sql);
             kysely.executeUpdate(sql);
                
            } catch (SQLException e) {
                System.err.println("Tulosjoukon käsittely "
                        + "epäonnistui.");
         
        } finally {
            try {
                kysely.close();
            } catch (Exception e) {
            }
        }
    }
}
