package sokkelo.sisalto;

import source.OmaLista;

/**
 *
 * @author lauri
 */
public class Monkija extends Liikkuvat {

    private OmaLista inv = new OmaLista();

    /**
     * Sisältää mönkijän ominaisuudet sekä tiedot
     */
    public static final char MERKKI = 'M';
    
    /**
     *
     * @param e Energia
     * @param s Suunta
     * @param rivi Mönkijän rivi index
     * @param sarake Mönkijän sarake index
     */
    public Monkija(int e, char s, int rivi, int sarake) {
        suunta(s);
        energia(e);
        rivi(rivi);
        sarake(sarake);
    }
    
    /**
     *
     * @return palauttaa mönkijän inventorion (OmaLista)
     */
    public OmaLista inv() {
        return inv;
    }
    
    /**
     *
     * @param e lisättävä Esine objekti
     */
    public void lisaaEsine(Esine e) {
        inv.lisaaTarkasti(e);
    }
    
    /**
     *
     * @param n muunnettava määrä
     */
    public void muunna(int n) {
        if(n >= 0 && n <= inv.koko()) {
            for(int i = 0; i < n; i++) {
                Esine e = (Esine)inv.poistaAlusta();
                this.energia(energia()+e.energia());
            }
        }
    }
    
    /**
     * tulostaa inventorion sisällön
     */
    public void inventoi() {
        System.out.println(toString());
        for(int i = 0; i < inv.koko(); i++) {
            System.out.println(inv.alkio(i).toString());
        }
    }
    
    /**
     *
     * @return Mönkijän merkki
     */
    @Override
    public String ui() {
        return MERKKI+"";
    }
    
    @Override
    public String toString(){
        return this.getClass().getSimpleName()+"  "+EROTIN+ super.toString()+EROTIN;
    }

}