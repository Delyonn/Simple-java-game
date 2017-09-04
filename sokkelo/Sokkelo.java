package sokkelo;

import apulaiset.*;

/**
 *
 * @author lauri
 */
public abstract class Sokkelo implements Paikallinen{

    private int rivi;
    private int sarake;

    /**
     *
     */
    protected final String EROTIN ="|";

    @Override
    public int rivi() {
        return rivi;
    }

    @Override
    public void rivi(int ind) throws IllegalArgumentException {
        if (ind >= 0) {
            rivi = ind;
        } else {
            throw new IllegalArgumentException("Virheellinen rivi-indeksi!");
        }
    }

    @Override
    public int sarake() {
        return sarake;
    }

    @Override
    public void sarake(int ind) throws IllegalArgumentException {
        if (ind >= 0) {
            sarake = ind;
        } else {
            throw new IllegalArgumentException("Virheellinen sarake-indeksi!");
        }
    }

    @Override
    public boolean sallittu() {    
        return this instanceof Kaytava;
    }
    
    /**
     *
     * @return
     */
    public abstract String ui();
    
    @Override
    public String toString() {
        String r =rivi+"";
        String s =sarake+"";
        while(r.length() < 4) {
            r=r+" ";
        }
        while(s.length() < 4) {
            s=s+" ";
        }
        
        return r+EROTIN+s;
    }
}