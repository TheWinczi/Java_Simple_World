package POProj2.Zwierzeta;

import POProj2.Organizm;
import POProj2.Punkt;
import POProj2.Swiat;
import POProj2.Zwierze;

import java.awt.*;

public class Owca extends Zwierze {
    private static final int SILA_OWCY = 4;
    private static final int INICJATYWA_OWCY = 4;

    //
    //  KONSTRUKTORY
    //
    public Owca( Swiat swiat, int sila, int inicjatywa, int wspX, int wspY ){
        super( swiat, sila, inicjatywa, wspX, wspY );
    }
    public Owca( Swiat swiat, int wspX, int wspY ){
        super( swiat, SILA_OWCY, INICJATYWA_OWCY, wspX, wspY );
    }
    public Owca( Swiat swiat, Punkt pkt ){
        super( swiat, SILA_OWCY, INICJATYWA_OWCY, pkt.getWspX(), pkt.getWspY() );
    }

    //
    //  NADPISANE
    //
    @Override
    public String toString()
    {
        return "owca";
    }

    @Override
    public Color getColor(){ return Color.RED; }

    @Override
    public Organizm getKopia()
    {
        return new Owca( this.getSwiat(), this.getSila(), this.getInicjatywa(), this.getWspX(), this.getWspY() );
    }
}
