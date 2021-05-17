package POProj2;

import java.awt.*;
import java.util.List;
import java.util.Random;

public class Roslina extends Organizm {

    protected int sila;
    protected int inicjatywa;
    protected int czasIstnienia;
    protected Punkt punkt;
    protected Swiat swiat;
    protected boolean czyZywy;

    protected static final int INICJATYWA_ROSLIN = 0;
    private static final int PRAWDOPODOBIENSTWO_ROZPRZESTRZENIENIA_SIE = 15;
    protected static final int MINIMALNY_CZAS_ISTNIENIA_POTREBNY_DO_ROZMNAZANIA = 3;
    Random random = new Random();

    //
    //  KONSTRUKTORY
    //
    public Roslina( Swiat _swiat, int _sila, int _inicjatywa, int _wspX, int _wspY ) {
        this.swiat = _swiat;
        this.sila = _sila;
        this.inicjatywa = _inicjatywa;
        this.czasIstnienia = 0;
        this.punkt = new Punkt( _wspX, _wspY );
        this.czyZywy = true;
    }

    public Roslina( Swiat _swiat, int _sila, int _wspX, int _wspY ) {
        this.swiat = _swiat;
        this.sila = _sila;
        this.inicjatywa = INICJATYWA_ROSLIN;
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
    public Punkt getPunkt(){ return this.punkt; }
    public int getCzasIstnienia(){ return this.czasIstnienia; }


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
        if( _wspX > 0 )
            this.punkt.setWspX(_wspX);
    }
    @Override
    public void setWspY( int _wspY ) {
        if( _wspY > 0 )
            this.punkt.setWspX(_wspY);
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
    public void akcja() {

        if( this.czasIstnienia >= MINIMALNY_CZAS_ISTNIENIA_POTREBNY_DO_ROZMNAZANIA && random.nextInt(100) <= PRAWDOPODOBIENSTWO_ROZPRZESTRZENIENIA_SIE )
            this.sprobujSieRozmnozyc();

        this.czasIstnienia++;
    }
    @Override
    public void zgin() {
        this.getSwiat().dodajDoUsuniecia( this );
    }

    @Override
    public void kolizja(Organizm atakujacy) {

        if( atakujacy.getRodzaj().equals("zwierze") )
            this.swiat.dodajDoUsuniecia(this);
    }

    @Override
    public boolean czyOdbilAtak( Organizm atakujacy ) {
        return false;
    }
    @Override
    public String getRodzaj()
    {
        return "roslina";
    }

    //
    //  DODATKOWE
    //
    protected List<Organizm> getOrganizmyWokol(Punkt pkt, int skok )
    {
        return this.getSwiat().getOrganizmyWokol(pkt, skok);
    }

    @Override
    public void rysowanie() {
        //this.swiat.dodajDoKolejkiRysowania(this);
    }

    @Override
    public Color getColor(){ return Color.GREEN; }

    @Override
    public Organizm getKopia()
    {
        return new Roslina( this.swiat, this.sila, this.inicjatywa, this.punkt.getWspX(), this.punkt.getWspY() );
    }


    //
    //  DODATKOWE
    //
    void sprobujSieRozmnozyc(){

        if( this.czyZywy && this.czasIstnienia > MINIMALNY_CZAS_ISTNIENIA_POTREBNY_DO_ROZMNAZANIA && random.nextInt(100) <= PRAWDOPODOBIENSTWO_ROZPRZESTRZENIENIA_SIE ) {
            List<Punkt> lista = this.swiat.getMiejscaWokol(this.getPunkt(), 1 );
            if( lista.size() > 0 )
            {
                Punkt pkt = lista.get(random.nextInt(lista.size()));
                Roslina nowaRoslina = (Roslina)this.getKopia();
                nowaRoslina.setWspX( pkt.getWspX() );
                nowaRoslina.setWspY( pkt.getWspY() );
                this.swiat.dodajDoDodania( nowaRoslina );
                this.swiat.getKomentator().dodajWiadomosc(this.toString() + " rozprzestrzenil sie");
            }
        }
    }
}
