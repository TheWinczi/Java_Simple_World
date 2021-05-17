package POProj2.Zwierzeta;

import POProj2.Organizm;
import POProj2.Punkt;
import POProj2.Swiat;
import POProj2.Zwierze;

import java.awt.*;
import java.util.Random;

public class Antylopa extends Zwierze {
    private static final int SILA_ANTYLOPY = 4;
    private static final int INICJATYWA_ANTYLOPY = 4;
    private static final int SZANSA_NA_UCIECZKE = 50;
    private static final int SKOK_ANTYLOPY = 2;

    Random rand = new Random();

    //
    //  KONSTRUKTORY
    //
    public Antylopa(Swiat swiat, int sila, int inicjatywa, int wspX, int wspY ){
        super( swiat, sila, inicjatywa, wspX, wspY, SKOK_ANTYLOPY );
    }
    public Antylopa( Swiat swiat,int wspX, int wspY ){
        super( swiat, SILA_ANTYLOPY, INICJATYWA_ANTYLOPY, wspX, wspY, SKOK_ANTYLOPY );
    }
    public Antylopa( Swiat swiat, Punkt pkt ){
        super( swiat, SILA_ANTYLOPY, INICJATYWA_ANTYLOPY, pkt.getWspX(), pkt.getWspY(), SKOK_ANTYLOPY );
    }

    //
    //  NADPISANE
    //
    @Override
    public void kolizja( Organizm atakujacy )
    {
        if( rand.nextInt(100) <= SZANSA_NA_UCIECZKE )
        {
            this.getSwiat().getKomentator().dodajWiadomosc("Antylopa odbiła atak napastnika");
            this.wylosujNowaPozycje( this.getPunkt() );
        }
    }
    @Override
    public String toString()
    {
        return "antylopa";
    }

    @Override
    public Color getColor(){ return new Color(200,120,0); } // Jasnobrazowy

    @Override
    public Organizm getKopia()
    {
        return new Antylopa( this.getSwiat(), this.getSila(), this.getInicjatywa(), this.getWspX(), this.getWspY() );
    }


}
