package source;


import apulaiset.*;
import java.io.IOException;

/**
 *
 * @author lauri
 */
public class PeliMoottori {   
    //Ohjelman tulosteet sekä ymmärtämät komennot:
    private static final String ALKUTXT = "***********\n* SOKKELO *\n***********";
    private static final String HEIHEI = "Ohjelma lopetettu.";
    private static final String KOMENTOTXT = "Kirjoita komento:";

    private static final String LIIKU = "liiku";
    private static final String KARTTA = "kartta";
    private static final String KATSO = "katso";
    private static final String ODOTA = "odota";
    private static final String INVENTOI = "inventoi";
    private static final String LATAA = "lataa";
    private static final String MUUNNA = "muunna";
    private static final String LOPETA = "lopeta";
    private static final String TALLENNA = "tallenna";
    private static final String VIRHE = "Virhe!";

    private static Kentta kentta;
    private static boolean kaynnissa;

    /**
     * käynnistää moottorin ja alustaa kaiken
     * @return palauttaa true
     */
    public static boolean kaynnista() {
        kaynnissa = true;
        System.out.println(ALKUTXT);
        kentta = new Kentta();
        try {
            kentta.lataa();
        } catch (IOException e) {
            System.out.println(VIRHE);
        }
        moottori();
        return kaynnissa;
    }

    /**
     * Lopettaa pelin
     */
    protected static void lopeta() {
        kaynnissa = false;
    }

    /**
     * lukee ja pilkkoo komennon
     * @return pilkottu komento
     */
    protected static String[] komento() {
        System.out.println(KOMENTOTXT);
        String k = In.readString();
        String[] komento = k.split("\\s+");
        return komento;
    }
    
    /**
     * Pelin pääsilmukka
     */
    protected static void moottori() {
        boolean vuoro = false;
        while(kaynnissa == true) {

            String[] komento = komento();
            if(komento[0].equals(LOPETA) && komento.length == 1) {
                kentta.kartta();
                kaynnissa = false;
            } else if(komento[0].equals(LATAA) && komento.length == 1) {
                try {
                    kentta.lataa();
                } catch (IOException e) {
                    System.out.println(VIRHE);
                }
            } else if(komento[0].equals(INVENTOI) && komento.length == 1) {
                kentta.inventoi();
            } else if(komento[0].equals(LIIKU) && komento.length == 2) {
                boolean b = kentta.liiku(komento[1]);
                if (b) {
                   vuoro = true; 
                }
            } else if(komento[0].equals(KARTTA)) {
                kentta.kartta();
            } else if(komento[0].equals(MUUNNA) && komento.length == 2) {
                kentta.muunna(komento[1]);
            } else if(komento[0].equals(KATSO) && komento.length == 2) {
                kentta.katso(komento[1]);
            } else if(komento[0].equals(TALLENNA) && komento.length == 1) {
                kentta.tallenna();
            } else if(komento[0].equals(ODOTA) && komento.length == 1) {
                vuoro = true;
            } else {
                System.out.println(VIRHE);
            }
            if(kentta.winCondition()) {
                kaynnissa = false;
                kentta.kartta();
                vuoro = false;
            }
            
            if(vuoro) {
                kentta.vihulainen();
                kentta.kartta();
                vuoro = false;
            }
        }
        System.out.println(HEIHEI);
    }
}