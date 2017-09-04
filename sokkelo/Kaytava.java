package sokkelo;

import sokkelo.sisalto.Esine;
import sokkelo.sisalto.Monkija;
import sokkelo.sisalto.Robotti;
import sokkelo.sisalto.REMluokka;
import source.OmaLista;

/**
 *
 * @author lauri
 */
public class Kaytava extends Sokkelo {
    private OmaLista inv = new OmaLista();
    private final char MERKKI = ' ';

    /**
     *
     * @param rivi
     * @param sarake
     */
    public Kaytava(int rivi, int sarake) {
        rivi(rivi);
        sarake(sarake);
    }
    /**
     *
     * @return kaytavaobjektin inventorio lista
     */
    public OmaLista inventorio() {
        return inv;
    } 
    /**
     *
     * @param e käytävällä liikkuva luokka
     */
    public void lisaaKaytavalle(REMluokka e) {
        inv.lisaaTarkasti(e);
    }

    /**
     *
     * @param e käytävällä liikkuva luokka
     */
    public void poistaKaytavalta(REMluokka e) {
        inv.poista(e);
    }

    /**
     * 
     * @return palauttaa käytävällä olevan mönkijän
     */
    public Monkija monkija(){
        return (Monkija)inv.alkio(0);
    }

    /**
     *
     * @return Merkki riippuu käytävällä olevista objekteista
     */
    @Override
    public String ui() {
        if(inv.sisaltaa("Monkija")) {
            return Monkija.MERKKI+"";
        } else if(inv.sisaltaa("Robotti")) {
            return Robotti.MERKKI+"";
        } else if (inv.sisaltaa("Esine")) {
            return Esine.MERKKI+"";
        } else {
            return MERKKI+"";
        }
    }

    @Override
    public String toString(){ 
        return this.getClass().getSimpleName()+"  "+EROTIN+super.toString()+EROTIN;
    }
}