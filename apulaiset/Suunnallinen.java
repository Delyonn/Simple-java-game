package apulaiset;

/**
  * Rajapinta suunnallisille olioille. Olio tuntee pilmansuunnat, joiden symbolit
  * on annettu vakioina.
  * <p>
  * Harjoitusty, Olio-ohjelmoinnin perusteet, kevt 2016.
  * <p>
  * @author Jorma Laurikkala (jorma.laurikkala@uta.fi),
  * Informaatiotieteiden yksikk, Tampereen yliopisto.
  *
  */

public interface Suunnallinen {

   /** Pohjoista symboloiva merkki. */
   public static char POHJOINEN = 'p';

   /** It symboloiva merkki. */
   public static char ITA = 'i';

   /** Etel symboloiva merkki. */
   public static char ETELA = 'e';

   /** Lntt symboloiva merkki. */
   public static char LANSI = 'l';

   /** Olion suunnan palauttava metodi.
     *
     * @return jokin yll mritellyist neljst pilmansuunnan symbolista.
     */
   public abstract char suunta();

   /** Olion suunnan asettava metodi.
     *
     * @param ilmansuunta uusi suunta, joka on jokin neljst pilmansuunnasta.
     * @throws IllegalArgumentException jos parametri ei ollut jokin yll
     * mritellyist pilmansuunnan symboleista.
     */
   public abstract void suunta(char ilmansuunta) throws IllegalArgumentException;
}
