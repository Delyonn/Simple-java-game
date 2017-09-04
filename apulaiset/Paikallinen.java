package apulaiset;

/**
  * Rajapinta paikkansa tunteville olioille. Paikka mritelln rivi-
  * ja sarakeindeksien avulla. Indeksiarvon tulee olla positiivinen
  * (suurempi tai yht suuri kuin nolla).
  * <p>
  * Harjoitusty, Olio-ohjelmoinnin perusteet, kevt 2016.
  * <p>
  * @author Jorma Laurikkala (jorma.laurikkala@uta.fi),
  * Informaatiotieteiden yksikk, Tampereen yliopisto.
  *
  */

public interface Paikallinen {

   /** Paikan rivi-indeksin palauttava metodi.
     *
     * @return paikan rivi-indeksi.
     */
   public abstract int rivi();

   /** Paikan rivi-indeksin asettava metodi.
     *
     * @param ind rivi-indeksi.
     * @throws IllegalArgumentException jos indeksi on negatiivinen.
     */
   public abstract void rivi(int ind);

   /** Paikan sarakeindeksin palauttava metodi.
     *
     * @return paikan sarakeindeksi.
     */
   public abstract int sarake();

   /** Paikan sarakeindeksin asettava metodi.
     *
     * @param ind sarakeindeksi.
     * @throws IllegalArgumentException jos indeksi on negatiivinen.
     */
   public abstract void sarake(int ind);   
   
   /** Kertoo onko paikkaan sallittua asettaa sislt (mnkij, robotti tai esine).
     * Paikka on kytettviss, jos siin on kytv.
     *
     * @return true, jos paikka on kytettviss.
     */   
   public abstract boolean sallittu();
}
