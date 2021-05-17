package POProj2.Zwierzeta;

import POProj2.Organizm;
import POProj2.Punkt;
import POProj2.Swiat;
import POProj2.Zwierze;

import java.awt.*;

public class Wilk extends Zwierze {
    private static final int SILA_WILKA = 9;
    private static final int INICJATYWA_WILKA = 5;

    //
    //  KONSTRUKTORY
    //
    public Wilk( Swiat swiat, int sila, int inicjatywa, int wspX, int wspY ){
        super( swiat, sila, inicjatywa, wspX, wspY );
    }
    public Wilk ( Swiat swiat, int wspX, int wspY ){
        super( swiat, SILA_WILKA, INICJATYWA_WILKA, wspX, wspY );
    }
    public Wilk ( Swiat swiat, Punkt pkt ){
        super( swiat, SILA_WILKA, INICJATYWA_WILKA, pkt.getWspX(), pkt.getWspY() );
    }

    //
    //  NADPISANE
    //
    @Override
    public String toString()
    {
        return "wilk";
    }

    @Override
    public Color getColor(){ return Color.lightGray; }

    @Override
    public Organizm getKopia()
    {
        return new Wilk( this.getSwiat(), this.getSila(), this.getInicjatywa(), this.getWspX(), this.getWspY() );
    }

}
