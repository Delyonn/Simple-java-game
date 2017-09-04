package apulaiset;

import java.util.Random;            // Random-luokka kyttn.
import fi.uta.csjola.oope.lista.*;  // ArrayList-luokka kyttn.

/**
  * Olio-ohjelmoinnin perusteet, syksy 2016, harjoitusty.
  * <p>
  * Apuluokka, jolla tuotetaan paikkoja roboteille. Luokan olettaa, ett Paikallinen-
  * ja Suunnallinen-rajapintojen metodien toteutukset ovat kytettviss.
  * <p>
  * VAIN KURSSIN VASTUUOPETTAJA SAA MUUTTAA TT LUOKKAA.
  * <p>
  * L KOPIOI METODEJA TST LUOKASTA OMAAN OHJELMAASI.
  * <p>
  * @author Jorma Laurikkala (jorma.laurikkala@uta.fi),
  * Informaatiotieteiden yksikk, Tampereen yliopisto.
  * <p>
  * @version 1.0, 2.3.2016.
  * <p>
  * 29.3.2016: muokattu paivitaPaikat-metodin kommenttia.
  * 31.3.2016: korjattu paikkaJaSuuntaOK-metodin lauseke
  * rivind
  */

public final class Automaatti {

   /*__________________________________________________________________________
    *
    * 1. Ktketyt attribuutit.
    *
    */

   /** Maailmalta ktketty pseudosatunnaislukugeneraattori. */
   private static Random generaattori;

   /** Tosi, jos on kutsuttu alusta-metodia. */
   private static boolean alustettu = false;

   /*__________________________________________________________________________
    *
    * 2. Luokan omaan kyttn tarkoitetut salaiset metodit.
    *
    */

   /** Tarkistaa paikan ja suunnan. Huomaa, ett paikan oltava sispaikka.
     *
     * @param rivind paikan rivi-indeksi.
     * @param sarind paikan sarakeindeksi.
     * @param suunta liikesuunta pilmansuuntana annettuna.
     * @param osat sokkelon osat sisltv taulukko.
     * @return true, jos paikka ja suunta olivat kunnossa.
     */
   private static final boolean paikkaJaSuuntaOK(int rivind, int sarind,
   char suunta, Object[][] osat) {
      // Paikan on oltava taulukossa.
      boolean paikkaOK = rivind > 0 && rivind < osat.length - 1 && sarind > 0
      && sarind < osat[0].length - 1;

      // Suunnan on oltava jokin neljst ilmansuunnasta.
      boolean suuntaOK = suunta == Suunnallinen.POHJOINEN
      || suunta == Suunnallinen.ITA || suunta == Suunnallinen.ETELA
      || suunta == Suunnallinen.LANSI;

      // Palautetaan kokonaistulos.
      return paikkaOK && suuntaOK;
   }

   /** Kertoo sijaisevatko sokkelon osan ja sen naapuri vastakkaisissa suunnissa.
     *
     * @param osa viite sokkelon osaan.
     * @param naapuri viite osan naapuriosaan.
     * @return true, jos suunnat ovat vastakkaiset, false muuten.
     */
   private static final boolean vastakkainenSuunta(Paikallinen osa,
   Paikallinen naapuri) {
      char suunta1 = ((Suunnallinen)osa).suunta();
      char suunta2 = annaNaapurinSuunta(osa, naapuri);
      if ((suunta1 == Suunnallinen.POHJOINEN && suunta2 == Suunnallinen.ETELA)
      || (suunta2 == Suunnallinen.POHJOINEN && suunta1 == Suunnallinen.ETELA)
      || (suunta1 == Suunnallinen.ITA && suunta2 == Suunnallinen.LANSI)
      || (suunta2 == Suunnallinen.ITA && suunta1 == Suunnallinen.LANSI))
         return true;
      else
         return false;
   }

   /** Antaa suunnan, jossa sokkelon osan naapuri sijaitsee.
     *
     * @param osa viite sokkelon osaan.
     * @param naapuri viite osan naapuriosaan.
     * @return naapurin suunta.
     */
   private static final char annaNaapurinSuunta(Paikallinen osa,
   Paikallinen naapuri) {
      if (osa.rivi() - 1 == naapuri.rivi())
         return Suunnallinen.POHJOINEN;
      else if (osa.sarake() + 1 == naapuri.sarake())
         return Suunnallinen.ITA;
      else if (osa.rivi() + 1 == naapuri.rivi())
         return Suunnallinen.ETELA;
      else
         return Suunnallinen.LANSI;
   }

   /*__________________________________________________________________________
    *
    * 3. Harjoitustyohjelmasta kutsuttavat julkiset metodit.
    *
    */

   /** Alustaa automaatin pseudosatunnaislukugeneraattorin siemenluvun avulla.
     * Metodia voidaan kutsua kerran tai useammin. Uusi kutsu samalla
     * siemenluvulla tuottaa samat paikat kuin aiempi kutsu.
     *
     * @param siemen siemenluku.
     */
   public static final void alusta(int siemen) {
      // Luodaan pseudosatunnaislukugeneraattori annetulla siemenluvulla.
      // Tietyll siemenluvulla saadaan tietty sarja pseudosatunnaislukuja.
      generaattori = new Random(siemen);

      // Automaatti on nyt alustettu.
      alustettu = true;
   }

   /** Siirt jokaista robottia satunnaisesti yhden paikan verran sokkelossa.
     * Robotin nykyinen suunta saattaa vaihtua. Siirron onnistuminen edellytt
     * Paikallinen- ja Suunnallinen-rajapintojen toteutusta.
     *
     * @param robotit lista, jolla viitteet robottiolioihin, jotka paikallistuvat
     * ja joilla on suunta. Listan tietoalkioilla tulee tosin sanoen olla tavalla
     * tai toisella Paikallinen- ja Suunnallinen-rajapintojen metodien toteutukset.
     * ROBOTTIEN KESKINISEN JRJESTYKSEN TULEE OLLA SAMA KUIN TEKSITIEDOSTOSSA,
     * jotta robotit liikkuisivat samoin kuin malliratkaisussa.
     * Olioiden paikat ja mahdollisesti suunnat ovat pivittyneet metodin
     * suorituksen jlkeen. Taulukko ei muutu. Huomaa, ett parametri voi olla
     * hyvin OmaLista-tyyppinen, koska OmaLista-luokka periytyy
     * LinkitettyLista-luokasta. Samoin esimerkiksi Osat-tyyppisist alkioista
     * koostuva taulukko kelpaa, koska jokainen luokka on yhteensopiva
     * Object-tyypin kanssa.
     * @param osat sokkelon osat sisltv kaksiulotteinen taulukko. Osilla tulee olla
     * tavalla tai toisella Paikallinen-rajapinnan metodien toteutukset.
     * @throws IllegalArgumentException jos automaattia ei ole alustettu,
     * lydetn null-arvoinen viite, paikka tai suunta on virheellinen,
     * taulukon alkioiden luokka ei ole toteuttanut itse tai saanut periytymisen
     * kautta Paikallinen-rajapinnan toteutusta tai robottien luokka ei ole
     * toteuttanut itse tai saanut periytymisen kautta Paikallinen-
     * ja Suunnallinen-rajapintoja.
     */
   public static final void paivitaPaikat(LinkitettyLista robotit, Object[][] osat)
   throws IllegalArgumentException {
      // Heitetn poikkeus, jos automaattia ei ole kynnistetty.
      if (!alustettu)
         throw new IllegalArgumentException("Automaattia ei ole alustettu!");

      // Heitetn poikkeus, jos muistia ei ole varattu.
      if (robotit == null || osat == null)
         throw new IllegalArgumentException("Muistia ei ole varattu!");

      try {
         // Siirretn yht robottia kerrallaan.
         for (int roboind = 0; roboind < robotit.koko(); roboind++) {
            // Asetetaan robottiin apuviitteet, joiden kautta voidaan kutsua
            // helpommin rajapintojen metodien toteutuksia.
            Paikallinen paikallinen = (Paikallinen)robotit.alkio(roboind);
            Suunnallinen suunnallinen = (Suunnallinen)robotit.alkio(roboind);

            // Rivi- ja sarakeindeksit sek nykyinen suunta apumuuttujiin.
            int rivind = paikallinen.rivi();
            int sarind = paikallinen.sarake();
            char suunta = suunnallinen.suunta();

            // Tarkistetaan tiedot.
            if (!paikkaJaSuuntaOK(rivind, sarind, suunta, osat))
               throw new IllegalArgumentException("Paikka (" + rivind + ", "
               + sarind + ") tai suunta (" + suunta + ") virheellinen!");

            // Tunnistetaan liikkumiseen kytettviss olevat naapuripaikat.
            LinkitettyLista sallitutNaapurit = new LinkitettyLista();
            int[][] naapuriPaikat = { { rivind - 1, sarind }, { rivind, sarind + 1 },
            { rivind + 1, sarind }, { rivind, sarind - 1} };
            for (int[] naapuriPaikka : naapuriPaikat) {
               // Asetaan apuviite naapuriin.
               Paikallinen paikallinenNaapuri =
               (Paikallinen)osat[naapuriPaikka[0]][naapuriPaikka[1]];
               if (paikallinenNaapuri.sallittu())
                  sallitutNaapurit.lisaaLoppuun(paikallinenNaapuri);
            }

            // Valitaan suoraan vastakkaisessa suunnassa oleva naapuri, kun ollaan
            // umpikujassa. Muuten valitaan satunnaisesti jokin naapureista siten,
            // ett vastakkainen suunta valitaan pienemmll todennkisyydell.
            boolean vastakkaisetSuunnat = true;
            Paikallinen valittuNaapuri = null;
            int i = 0;
            do {
               int arpa = generaattori.nextInt(sallitutNaapurit.koko());
               valittuNaapuri = (Paikallinen)sallitutNaapurit.alkio(arpa);
               vastakkaisetSuunnat = vastakkainenSuunta(paikallinen, valittuNaapuri);
               i++;
            }
            while (i < 2 && vastakkaisetSuunnat);

            // Pivitetn robotin tiedot.
            suunnallinen.suunta(annaNaapurinSuunta(paikallinen, valittuNaapuri));
            paikallinen.rivi(valittuNaapuri.rivi());
            paikallinen.sarake(valittuNaapuri.sarake());
         }
      }
      // Todennkisimpi (tieto)alkioihin liittyvi ongelmia.
      catch (NullPointerException e) {
         e.printStackTrace();
         throw new IllegalArgumentException("Null-arvoinen (tieto)alkio!");
      }
      // Todennkisimpi (tieto)alkioihin liittyvi ongelmia.
      catch (ArrayIndexOutOfBoundsException e) {
         e.printStackTrace();
         throw new IllegalArgumentException("Virheellinen paikka!");
      }
      // Tarvitaan, jotta seuraava sieppari ei nappaisi heitetty poikkeusta.
      catch (IllegalArgumentException e) {
         e.printStackTrace();
         throw e;
      }
      // Tt ei pitisi tapahtua.
      catch (Exception e) {
         e.printStackTrace();
      }
   }
}
