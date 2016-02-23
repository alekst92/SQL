import java.sql.*;
import java.util.*;

public class Firma {

    private Scanner lukija = new Scanner(System.in);
    private Connection yhteys = null;

    private boolean avaaYhteys() {

	// alla KANTANIMI ja TUNNUS ovat samoja kuin Metropolian käyttäjätunnus.
	// SALASANA on MySQL:n salasana, jonka olet asettanut amme.metropolia.fi-sivulla.

        boolean ok = true;
        try {
            String kanta = "jdbc:mysql://localhost/firma";
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

    public Firma() {
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
            
            /*System.out.println("No. of columns : " + rsmd.getColumnCount());

              
            int i = 0;
            for(i=1 ; i <= rsmd.getColumnCount() ; i++){
                System.out.print("Field name: " + rsmd.getColumnName(i));
                System.out.println("      Type name : " + rsmd.getColumnTypeName(i));
                
            }*/
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
        System.out.print("Kirjoita taulun nimi: ");
        String merkkijono = lukija.nextLine();
        
        try {
            kysely = yhteys.createStatement();
            ResultSet tulos = kysely.executeQuery("SELECT * FROM " + merkkijono);
            ResultSetMetaData rsmd = tulos.getMetaData();
            
            System.out.println("No. of columns : " + rsmd.getColumnCount());

              
            int i = 0;
            for(i=1 ; i <= rsmd.getColumnCount() ; i++){
                System.out.print("Field name: " + rsmd.getColumnName(i));
                System.out.print("      Type name : " + rsmd.getColumnTypeName(i));
                System.out.println("      Type name : " + rsmd.getSchemaName(i));
                
            }
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
            System.out.print("Anna taulun nimi: ");
            taulunnimi = lukija.nextLine();
            System.out.print("Anna  muutettavan columnin nimi: ");
            columname = lukija.nextLine();
            System.out.print("Anna  muutettavan rivin id columnin nimi: ");
            columname2 = lukija.nextLine();
            System.out.print("Anna  muutettavan rivin indexi: ");
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
}
