/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package autovarasto;

import java.sql.*;
import java.util.*;

public class Kayttoliittyma {
    private Scanner lukija = new Scanner(System.in);
    private Autovarasto autovarasto = new Autovarasto();
    
    public void aloita(){
        int numero, numero2;
        while(true){
            System.out.print("Anna komento, komentolista - 0: ");
            numero = lukija.nextInt();
            if (numero == 0){
                System.out.println("SELECT DATABASE - 1");
                System.out.println("SELECT          - 2");
                System.out.println("UPDATE          - 3");
                System.out.println("DELETE          - 4");
                System.out.println("INSERT          - 5");
                System.out.println("EXIT            - 6");
            }            
            if (numero == 1){
                autovarasto.valitseTietokanta();
            }
            else if (numero == 2){
                while(true){
                    System.out.println("Nayta taulut               - 1");
                    System.out.println("Describe taulu             - 2");
                    System.out.println("Anna vapaamuotoinen kysely - 3");
                    System.out.println("Tietojen selaus            - 4");
                    System.out.println("Lopeta                     - 5");
                    numero2 = lukija.nextInt();
                    if(numero2 == 1){
                        autovarasto.naytaTaulut();
                    }
                    else if(numero2 == 2){
                        
                        autovarasto.describeTaulu();
                    }
                    else if(numero2 == 3){
                        autovarasto.vapaaKysely();
                    }
                    else if(numero2 == 4){
                        autovarasto.Select2();
                    }
                    else if(numero2 == 5){
                        break;
                    }
                }
 
            }
            else if (numero == 3){
                autovarasto.naytaTaulut();
                autovarasto.describeTaulu();
                autovarasto.paivitaTaulu();
            }
            else if (numero == 4){
                while(true){
                    System.out.println("Poista database          - 1");
                    System.out.println("Poista taulu             - 2");
                    System.out.println("Poista taulun tietoja    - 3");
                    System.out.println("Lopeta                   - 5");
                    numero2 = lukija.nextInt();
                    if(numero2 == 1){
                        autovarasto.poistaDatabase();
                    }
                    else if(numero2 == 2){
                        autovarasto.poistaTaulu();
                    }
                    else if(numero2 == 3){
                        autovarasto.poistaTaulunTietoja();
                    }
                    else if(numero2 == 5){
                        break;
                    }
                    
                }
            }
            else if (numero == 5){
                autovarasto.describeTaulu();
                autovarasto.lisaaTauluun();
            }
            else if (numero == 6){
                autovarasto.suljeYhteys();
                break;
            }
        }


    }
    public boolean testaaYhteys(){
        if(autovarasto.avaaYhteys() == true){
            return true;
        }
        else{
            return false;
        }
    }



}



