import java.sql.*;
import java.util.*;

public class Kayttoliittyma {
    private Scanner lukija = new Scanner(System.in);
    private Firma firma = new Firma();
    
    public void aloita(){
        int numero;
        int numero2;
        while(true){
            System.out.print("Anna komento, komentolista - 0: ");
            numero = lukija.nextInt();
            if (numero == 0){
                System.out.println("SELECT DATABASE - 1");
                System.out.println("SELECT          - 2");
                System.out.println("UPDATE          - 3");
                System.out.println("DELETE          - 4");
                System.out.println("EXIT            - 5");
            }            
            if (numero == 1){
                firma.valitseTietokanta();
            }
            else if (numero == 2){
                while(true){
                    System.out.println("Anna vapaamuotoinen kysely - 1");
                    System.out.println("Describe taulu             - 2");
                    System.out.println("Lopeta                     - 3");
                    numero2 = lukija.nextInt();
                    if(numero2 == 1){
                        firma.vapaaKysely();
                        break;
                    }
                    else if(numero2 == 2){
                        firma.describeTaulu();
                    }
                    else if(numero2 == 3){
                        break;
                    }
                }
 
            }
            else if (numero == 3){
                firma.paivitaTaulu();
            }
            else if (numero == 4){
                firma.poistaDatabase();
            }
            else if (numero == 5){
                firma.suljeYhteys();
                break;
            }
        }


    }



}


