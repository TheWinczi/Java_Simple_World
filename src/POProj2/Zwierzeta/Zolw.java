package POProj2.Zwierzeta;

import POProj2.Organizm;
import POProj2.Punkt;
import POProj2.Swiat;
import POProj2.Zwierze;

import java.awt.*;
import java.util.Random;

public class Zolw extends Zwierze {
    private static final int SILA_ZOLWIA = 2;
    private static final int INICJATYWA_ZOLWIA = 1;
    Random random = new Random();

    //
    //  KONSTRUKTORY
    //
    public Zolw(Swiat swiat, int sila, int inicjatywa, int wspX, int wspY ){
        super(swiat, sila, inicjatywa, wspX, wspY );
    }
    public Zolw( Swiat swiat,int wspX, int wspY ){
        super(swiat,  SILA_ZOLWIA, INICJATYWA_ZOLWIA, wspX, wspY );
    }
    public Zolw(Swiat swiat, Punkt pkt){
        super(swiat,  SILA_ZOLWIA, INICJATYWA_ZOLWIA, pkt.getWspX(), pkt.getWspY() );
    }

    //
    //  NADPISANE
    //
    @Override
    public void akcja()
    {
        if( random.nextInt(100) <= 75 )
            super.akcja();
    }
    @Override
    public boolean czyOdbilAtak( Organizm atakujacy )
    {
        if( atakujacy.getSila() < 5 )
            return true;
        return false;
    }

    @Override
    public String toString()
    {
        return "zolw";
    }

    @Override
    public Color getColor(){ return new Color(0,90,0); } // Ciemnozielony

    @Override
    public Organizm getKopia()
    {
        return new Zolw( this.getSwiat(), this.getSila(), this.getInicjatywa(), this.getPunkt().getWspX(), this.getPunkt().getWspY() );
    }
}
