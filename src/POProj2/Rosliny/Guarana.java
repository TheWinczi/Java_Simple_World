package POProj2.Rosliny;

import POProj2.Organizm;
import POProj2.Punkt;
import POProj2.Roslina;
import POProj2.Swiat;
import POProj2.Zwierzeta.Wilk;

import java.awt.*;

public class Guarana extends Roslina {

    private static final int SILA_GUARANY = 10;

    //
    //  KONTRUKTORY
    //
    public Guarana(Swiat swiat, int sila, int inicjatywa, int wspX, int wspY ){
        super( swiat, sila, inicjatywa, wspX, wspY );
    }
    public Guarana( Swiat swiat,int wspX, int wspY ){
        super( swiat, SILA_GUARANY, INICJATYWA_ROSLIN, wspX, wspY );
    }
    public Guarana(Swiat swiat, Punkt pkt ){
        super( swiat, SILA_GUARANY, INICJATYWA_ROSLIN, pkt.getWspX(), pkt.getWspY() );
    }


    //
    //  NADPISANE
    //
    @Override
    public void kolizja( Organizm atakujacy ) {

        if( atakujacy.getRodzaj().equals("zwierze") ) {
            atakujacy.setSila( atakujacy.getSila() + 3 );
            this.zgin();
        }
    }
    @Override
    public String toString()
    {
        return "guarana";
    }

    @Override
    public Color getColor(){ return Color.MAGENTA; }

    @Override
    public Organizm getKopia()
    {
        return new Guarana( this.getSwiat(), this.getSila(), this.getInicjatywa(), this.getWspX(), this.getWspY() );
    }
}
