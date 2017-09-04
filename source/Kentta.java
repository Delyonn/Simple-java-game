package source;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import apulaiset.Automaatti;
import static apulaiset.Suunnallinen.ETELA;
import static apulaiset.Suunnallinen.ITA;
import static apulaiset.Suunnallinen.LANSI;
import static apulaiset.Suunnallinen.POHJOINEN;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import sokkelo.*;
import sokkelo.sisalto.*;

/**
 *
 * @author lauri
 */
public class Kentta {
    
    /**
     * muuttujien ja tujlosteiden esittely
     */
    public Object[][] kentta;
    private int x;
    private int y;
    private int siemen;
    private final String KOPS = "Kops!";
    private final String VIRHE = "Virhe!";
    private final String VOITTO = "Voitto!";
    private final String TAPPIO = "Tappio!";
    
    private final String EROTIN ="|";
    Monkija mviite;
    OmaLista sokkeloLista; 
    OmaLista robot;
    OmaLista esineet;
    
    // tee tallenna siten, että sokkelolista tulee päivittyneenä. nyt se ei tule
    // Esineet löytyvät sokkelolistalta. tää on ongelma koska wincondition ei toimi ja sokkelo ei muutu
    // mönkijän tai robottien liikkuessa. eikä kun mönkijällä on esineitä -> tallenna ei toimi
    
    /*
     * OmaLista esineet; ?
     * Tallenna:
     * Sokkelolistaan seiniä ja käytäviä?
     * if seinä { seinä }
     * if käytävä print käytävä.inv if mönkijä print mönkijä.inv
     * vaatii esinelistan winconditionia varten.
    */
    
    /**
     * Latausmetodi, jolla ladataan kenttä heti pelin alussa.
     * Voidaan ladata myös kesken pelin
     * @throws FileNotFoundException jos tiedostoa ei löydy
     * @throws IOException
     */
    public void lataa() throws FileNotFoundException, IOException {
        robot = new OmaLista();
        esineet = new OmaLista();
        int lippu=0;
        boolean kaytava = false;
        boolean monkija = false;
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("sokkelo.txt")))){
            String strLine;
            
            //Read File Line By Line
            while ((strLine = br.readLine()) != null)   {
                String separated[]= strLine.split("[|]");
                
                if(lippu == 0) {
                    siemen = Integer.parseInt(separated[0].trim());
                    x = Integer.parseInt(separated[1].trim());
                    y = Integer.parseInt(separated[2].trim());
                    kentta = new Object[x][y];
                }
                int rivi = Integer.parseInt(separated[1].trim());
                int sarake = Integer.parseInt(separated[2].trim());
                switch (separated[0].trim()) {
                    case "Seina":
                        kaytava = false;
                        monkija = false;
                        Seina s = new Seina(rivi,sarake);
                        kentta[rivi][sarake] = s;
                        break;
                    case "Kaytava":
                        monkija = false;
                        Kaytava k = new Kaytava(rivi,sarake);
                        kentta[rivi][sarake] = k;
                        kaytava = true;
                        break;
                    case "Monkija":
                        if(kaytava == true) {
                            Kaytava tmp = (Kaytava)kentta[rivi][sarake];
                            mviite = new Monkija(Integer.parseInt(separated[3].trim()), separated[4].trim().charAt(0),rivi, sarake);
                            tmp.lisaaKaytavalle(mviite);
                            monkija = true;
                        }
                        break;
                    case "Robotti":
                        if(kaytava == true){
                            Kaytava tmp = (Kaytava)kentta[rivi][sarake];
                            Robotti r = new Robotti(Integer.parseInt(separated[3].trim()), separated[4].trim().charAt(0),rivi, sarake);
                            tmp.lisaaKaytavalle(r);
                            robot.lisaaLoppuun(r);
                        }
                        break;
                    case "Esine":
                        if(monkija == true) {
                            Kaytava tmp = (Kaytava)kentta[rivi][sarake];
                            Monkija m = (Monkija)tmp.inventorio().alkio(0);
                            m.lisaaEsine(new Esine(Integer.parseInt(separated[3].trim()), rivi, sarake));
                        }else {
                            Kaytava tmp = (Kaytava)kentta[rivi][sarake];
                            Esine e = new Esine(Integer.parseInt(separated[3].trim()),rivi, sarake);
                            tmp.lisaaKaytavalle(e);
                            esineet.lisaaTarkasti(e);
                        }   
                    
                        break;
                    default:
                        break;
                }
                lippu++;
            }
            br.close();
        }
        Automaatti.alusta(siemen);
    }

    /**
     * Tallentaa pelin tekstitiedostoon
     * Peli tallennetaan OmaLista tyyppisestä listasta
     */
    public void tallenna() {
        paivitaSokkelo();
        try {
            File file = new File("sokkelo.txt");
            FileOutputStream fileOut = new FileOutputStream(file);
            PrintWriter pw = new PrintWriter(fileOut);
            String a = x+"";
            String b = y+"";
            String c = siemen+"";
            while(a.length()<=3) {
                a+=" ";
            }
            while(b.length()<=3) {
                b+=" ";
            }
            while(c.length()<=3) {
                c+=" ";
            }
            
            pw.println(c+EROTIN+a+EROTIN+b+EROTIN);
            for(int i = 0; i < sokkeloLista.koko(); i++) {
                pw.println(sokkeloLista.alkio(i).toString());
            }
            pw.flush();
            pw.close();
        } catch (IOException e) {
            System.out.println(e);
        }
    }
    
    public void paivitaSokkelo() {
        sokkeloLista = new OmaLista();
        for(int i = 0; i < x; i++) {
            for(int j = 0; j < y; j++) {
                Sokkelo s = (Sokkelo)kentta[i][j];
                if(kentta[i][j] instanceof Kaytava) {
                    Kaytava ktmp = (Kaytava)s;
                    sokkeloLista.lisaaLoppuun(s);
                    if(ktmp.inventorio().alkio(0) instanceof Monkija) {
                        sokkeloLista.lisaaLoppuun(mviite);
                        for(int l = 0; l < mviite.inv().koko(); l++) {
                            sokkeloLista.lisaaLoppuun(mviite.inv().alkio(l));
                        }
                    } else {
                        for(int k = 0; k < ktmp.inventorio().koko(); k++) {
                            sokkeloLista.lisaaLoppuun(ktmp.inventorio().alkio(k));
                        }
                    }
                } else {
                    sokkeloLista.lisaaLoppuun(s);
                }
                
            }
        }
    }

    /**
     * Katsoo mönkijän näkökulmasta johonkin neljästä ilmansuunnasta
     * @param suunta suunta, johon katsotaan
     */
    public void katso(String suunta) {
        int r = 0;
        int s = 0;
        if(null != suunta)
            switch (suunta) {
            case POHJOINEN+"":
                r = -1;
                break;
            case ETELA+"":
                r = 1;
                break;
            case ITA+"":
                s = 1;
                break;
            case LANSI+"":
                s = -1;
                break;
            default:
                System.out.println(VIRHE);
                return;
        }
        
        if(kentta[mviite.rivi()+r][mviite.sarake()+s] instanceof Kaytava) {
            Kaytava tmp = (Kaytava)kentta[mviite.rivi()+r][mviite.sarake()+s];
            System.out.println(tmp);
            if(tmp.inventorio().koko() > 0) {
                for(int i = 0; i < tmp.inventorio().koko(); i++) {
                    System.out.println(tmp.inventorio().alkio(i));
                }
            }
        } else {
            System.out.println(kentta[mviite.rivi()+r][mviite.sarake()+s]);
        }
    }

    /**
     * Liikkuttaa mönkijää johonkin neljästä ilmansuunnasta.
     * Päivittää mönkijän ja kohdattujen esineiden koordinaatit sekä sijainnit
     * Jos kohdataan robotti, taistellaan.
     * @param s suunta johon liikutaan
     */
    public boolean liiku(String s) {
        int rtmp,stmp;
        
        //Tästä switch casesta ei pääse läpi, jos annettu parametri ei ole oikein.
        switch (s) {
            case POHJOINEN+"":
                rtmp = -1;
                stmp = 0;
                break;
            case ETELA+"":
                rtmp = 1;
                stmp = 0;
                break;
            case ITA+"":
                rtmp = 0;
                stmp = 1;
                break;
            case LANSI+"":
                rtmp = 0;
                stmp = -1;
                break;
            default:
                System.out.println(VIRHE);
                return false;
        }
        

        
        if(kentta[mviite.rivi()+rtmp][mviite.sarake()+stmp] instanceof Kaytava) {
            Kaytava ktmp = (Kaytava)kentta[mviite.rivi()][mviite.sarake()];
            ktmp.poistaKaytavalta(mviite);
            mviite.rivi(mviite.rivi()+rtmp);
            mviite.sarake(mviite.sarake()+stmp);
            Kaytava k = (Kaytava)kentta[mviite.rivi()][mviite.sarake()];
            if(k.inventorio().koko() > 0) {
                while(k.inventorio().koko() > 0) {
                    if(k.inventorio().sisaltaa("Robotti")) {
                        int i=0;
                        while(k.inventorio().sisaltaa("Robotti")) {
                            if(k.inventorio().alkio(i) instanceof Robotti) {
                                boolean b = taistelu((Robotti)k.inventorio().alkio(i));
                                if(!b) {
                                    kartta();
                                    return true;
                                }
                            }
                            i++;
                        }
                        
                    }
                    if(k.inventorio().sisaltaa("Esine")){
                        int i=0;
                        while(k.inventorio().sisaltaa("Esine")) {
                            Esine e = (Esine)k.inventorio().alkio(i);
                            mviite.lisaaEsine(e);
                            esineet.poista(e);
                            k.poistaKaytavalta(e);
                        }
                    }
                } 
            }
            
            //lisätään mönkijä käytävälle, kun tarvittavat työt on suoritettu
            k.lisaaKaytavalle(mviite);
            mviite.suunta(s.charAt(0));
            
            //päivitetään myös esineiden koordinaatit
            for(int i = 0; i < mviite.inv().koko(); i++) {
                Esine e = (Esine)mviite.inv().alkio(i);
                e.rivi(mviite.rivi());
                e.sarake(mviite.sarake());
            }
            return true;
        } else {
            System.out.println(KOPS);
            return true;
        }
        
    }

    /**
     * Taistelumetodi. Pienemmän energiamäärnä omaava häviää.
     * @param robotti
     */
    public boolean taistelu(Robotti robotti) {
        Kaytava k = (Kaytava)kentta[mviite.rivi()][mviite.sarake()];
        if(mviite.compareTo(robotti) <= 0) {
            mviite.energia(mviite.energia()-robotti.energia());
            robot.poista(robotti);
            k.poistaKaytavalta(robotti);
            System.out.println(VOITTO);
            return true;
        } else {
            k.poistaKaytavalta(mviite);
            System.out.println(TAPPIO);
            PeliMoottori.lopeta();
            return false;
        }
    }

    /**
     * Toimii robottien liikuttajana
     * Kohdatessa mönkijä, taistellaan
     */
    public void vihulainen() {
        if(robot.koko() > 0) {
            for(int i = 0; i < robot.koko(); i++) {
                Robotti r = (Robotti)robot.alkio(i);
                Kaytava k = (Kaytava)kentta[r.rivi()][r.sarake()];
                k.poistaKaytavalta(r);
            }
            Automaatti.paivitaPaikat(robot, kentta);

            for(int i = 0; i < robot.koko(); i++) {
                Robotti r = (Robotti)robot.alkio(i);
                Kaytava k = (Kaytava)kentta[r.rivi()][r.sarake()];
                if(k.inventorio().sisaltaa("Monkija")) {
                    k.lisaaKaytavalle(r);
                    boolean b = taistelu(r);
                    if(b) {
                        i--;
                    }
                } else {
                    k.lisaaKaytavalle(r);
                }
            }
        }
    }
    
    /**
     * Muiuntaa n määrän esineitä energiaksi
     * @param n muunnettavien esineiden lukumäärä
     */
    public void muunna(String n) {
        try {
            int i = Integer.parseInt(n);
            if(i >= 0 && i <= mviite.inv().koko())
                mviite.muunna(i);
            else
                System.out.println(VIRHE);
        } catch (NumberFormatException e) {
            System.out.println(VIRHE);
        }
    }
    
    /**
     * Tulostaa mönkijän sisällön
     */
    public void inventoi() {
        mviite.inventoi();
    }
    
    /**
     * Tarkastaa, onko kaikki esineet poimittu
     * @return true jos peli on voitettu
     */
    public boolean winCondition() {
        return !esineet.sisaltaa("Esine");
    }
    
    /**
     * Tulostaa kartan taulukkomuodossa
     */
    public void kartta() {
        for(int i = 0; i < x; i++) {
            for(int j = 0; j < y; j++) {
                Sokkelo s = (Sokkelo)kentta[i][j];
                System.out.print(s.ui());
            }
            System.out.println();
        }
    }
}
