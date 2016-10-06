/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package autovarasto;

/**
 *
 * @author AnonymousX
 */
public class Main {
    public static void main(String[] args) {
        Kayttoliittyma kayttis = new Kayttoliittyma();
        if(kayttis.testaaYhteys()){
            kayttis.aloita();
        }
    }
    
}
