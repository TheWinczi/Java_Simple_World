package POProj2;

import java.awt.*;
import java.util.List;
import java.util.Random;

public class Zwierze extends Organizm {

    private Random random = new Random();

    protected int sila;
    protected int inicjatywa;
    protected int czasIstnienia;
    protected Swiat swiat;
    protected Punkt punkt;
    protected int skok;
    protected boolean czyZywy;
    protected static final int MINIMALNY_CZAS_ISTNIENIA_POTREBYN_DO_ROZMNOZENIA = 5;

    public Zwierze( Swiat _swiat, int _sila, int _inicjatywa, int _wspX, int _wspY )
    {
        this.swiat = _swiat;
        this.sila = _sila;
        this.inicjatywa = _inicjatywa;
        this.czasIstnienia = 0;
        this.punkt = new Punkt( _wspX, _wspY );
        this.skok = 1;
        this.czyZywy = true;
    }
    public Zwierze( Swiat _swiat, int _sila, int _inicjatywa, int _wspX, int _wspY, int _skok )
    {
        this.skok = _skok;
        this.swiat = _swiat;
        this.sila = _sila;
        this.inicjatywa = _inicjatywa;
        this.czasIstnienia = 0;
        this.punkt = new Punkt( _wspX, _wspY );
        this.czyZywy = true;
    }

    //
    //  GETTERY
    //
    @Override
    public int getSila() {
        return this.sila;
    }
    @Override
    public int getInicjatywa() {
        return this.inicjatywa;
    }
    @Override
    public int getWspX() {
        return this.punkt.getWspX();
    }
    @Override
    public int getWspY() {
        return this.punkt.getWspY();
    }
    @Override
    public Swiat getSwiat() { return this.swiat; }
    @Override
    public Punkt getPunkt() { return this.punkt; }
    public int getCzasIstnienia(){ return this.czasIstnienia; }
    public boolean getCzyZywy(){ return this.czyZywy; }

    //
    //  SETTERY
    //
    @Override
    public void setSila( int _sila ) {
        if( sila >= 0 )
            this.sila = _sila;
    }
    @Override
    public void setInicjatywa( int _inicjatywa) {
        if( inicjatywa >= 0 )
            this.inicjatywa = _inicjatywa;
    }
    @Override
    public void setWspX( int _wspX ) {
        if( _wspX >= 0 )
            this.punkt.setWspX(_wspX);
    }
    @Override
    public void setWspY( int _wspY ) {
        if( _wspY >= 0 )
            this.punkt.setWspY(_wspY);
    }
    @Override
    public void setCzyZywy( boolean _czyZywy )
    {
        this.czyZywy = _czyZywy;
    }



    //
    //  NADPISANE
    //
    @Override
    public boolean czyOdbilAtak( Organizm atakujacy ) {
        return false;
    }

    @Override
    public void zgin() {
        this.getSwiat().dodajDoUsuniecia( this );
    }

    @Override
    public void akcja()
    {
        this.wylosujNowaPozycje( this.getPunkt() );
        this.czasIstnienia++;
    }

    @Override
    public void kolizja(Organizm atakujacy) {

        this.getSwiat().getKomentator().dodajWiadomosc("Kolizja " + this.toString() + " z " + atakujacy.toString() + " na pozycji " + this.getWspX() + "x" + this.getWspY() );

        if( atakujacy.toString().equals(this.toString()) ) {
            this.getSwiat().getKomentator().dodajWiadomosc(this.toString() + " chca sie rozmnozyc");
            this.rozmnozSie( (Zwierze)atakujacy );
        }
        else {

            if (this.getSila() > atakujacy.getSila()) {
                this.swiat.dodajDoUsuniecia(atakujacy);
            } else if (this.getSila() <= atakujacy.getSila()) {
                this.swiat.dodajDoUsuniecia(this);
            }
        }
    }

    //@Override
    public void rysowanie() {
        //this.swiat.dodajDoKolejkiRysowania(this );
    }

    @Override
    public String getRodzaj()
    {
        return "zwierze";
    }

    @Override
    public Color getColor(){ return Color.BLACK; }


    //
    //  DODATKOWE
    //
    public void przemiscSie(int _wspX, int _wspY) {

        if( _wspX >= 0 && _wspX < this.swiat.getWymiarX() ){
            this.setWspX( _wspX );
        }

        if( _wspY >= 0 && _wspY < this.swiat.getWymiarY() ) {
            this.setWspY(_wspY);
        }

        this.swiat.getKomentator().dodajWiadomosc( this.toString() + " przemiscil sie na " + _wspX + "x" + _wspY );
    }

    public void przemiscSie(Punkt nowyPunkt) {
        this.punkt.setWspX( nowyPunkt.getWspX() );
        this.punkt.setWspY( nowyPunkt.getWspY() );

        this.swiat.getKomentator().dodajWiadomosc( this.toString() + " przemiscil sie na " + nowyPunkt.getWspX() + "x" + nowyPunkt.getWspY() );
    }

    protected List<Organizm> getOrganizmyWokol( Punkt pkt, int skok )
    {
        return this.getSwiat().getOrganizmyWokol(pkt, skok);
    }

    protected List<Organizm> getListaOrganizmu( Organizm organizm )
    {
        return this.getSwiat().getListaOrganizm(organizm);
    }

    protected void wylosujNowaPozycje( Punkt pkt )
    {
        List<Punkt> listaPunktow = this.swiat.getMiejscaWokol( pkt, this.skok );
        Punkt nowyPunkt = listaPunktow.get(random.nextInt(listaPunktow.size()));

        Zwierze tmpZwierze = this.getSwiat().czyNastapilaKolizjaZeZwierzeciem( nowyPunkt.getWspX(), nowyPunkt.getWspY() );
        if( tmpZwierze == null || ( tmpZwierze != null && !tmpZwierze.czyOdbilAtak( this )) )
        {
            if( tmpZwierze != null && tmpZwierze.toString().equals(this.toString()) )
                this.kolizja(tmpZwierze);
            else{
                this.przemiscSie( nowyPunkt );
            }
        }
    }

    protected void rozmnozSie( Zwierze zwierze ) {

        if( this.czasIstnienia >= MINIMALNY_CZAS_ISTNIENIA_POTREBYN_DO_ROZMNOZENIA && zwierze.getCzasIstnienia() >= MINIMALNY_CZAS_ISTNIENIA_POTREBYN_DO_ROZMNOZENIA ){
            List<Punkt> lista = this.swiat.getMiejscaWokol(zwierze.getPunkt(), 1 );
            if( lista.size() > 0 )
            {
                Punkt pkt = lista.get(random.nextInt(lista.size()));
                Zwierze noweZwierze = (Zwierze)zwierze.getKopia();
                noweZwierze.przemiscSie(pkt);
                this.swiat.dodajDoDodania( noweZwierze );
                this.swiat.getKomentator().dodajWiadomosc(zwierze.toString() + " rozmnozyl sie");
            }
        }
    }

    @Override
    public Organizm getKopia()
    {
        return new Zwierze( this.swiat, this.sila, this.inicjatywa, this.punkt.getWspX(), this.punkt.getWspY(), this.skok );
    }
}
