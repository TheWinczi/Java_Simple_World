package POProj2.Rosliny;

import POProj2.Organizm;
import POProj2.Punkt;
import POProj2.Roslina;
import POProj2.Swiat;
import POProj2.Zwierzeta.Wilk;

import java.awt.*;

public class Trawa extends Roslina {
    private final static int SILA_TRAWY = 0;

    public Trawa( Swiat swiat, int sila, int inicjatywa, int wspX, int wspY) {
        super(swiat, sila, inicjatywa, wspX, wspY);
    }
    public Trawa( Swiat swiat, int wspX, int wspY ){
        super(swiat, SILA_TRAWY, INICJATYWA_ROSLIN, wspX, wspY );
    }
    public Trawa( Swiat swiat, Punkt pkt ){
        super(swiat, SILA_TRAWY, INICJATYWA_ROSLIN, pkt.getWspX(), pkt.getWspY() );
    }

    //
    //  NADPISANE
    //
    @Override
    public String toString()
    {
        return "trawa";
    }

    @Override
    public Color getColor(){ return Color.green; }

    @Override
    public Organizm getKopia()
    {
        return new Trawa( this.getSwiat(), this.getSila(), this.getInicjatywa(), this.getWspX(), this.getWspY() );
    }
}
