package POProj2.Rosliny;

import POProj2.Organizm;
import POProj2.Punkt;
import POProj2.Roslina;
import POProj2.Swiat;
import POProj2.Zwierzeta.Wilk;

import java.awt.*;

public class Mlecz extends Roslina {
    private static final int SILA_MLECZA = 0;
    private static final int ILE_PROB_ROZMNAZANIA = 3;

    //
    //  KONTRUKTORY
    //
    public Mlecz( Swiat swiat, int sila, int inicjatywa, int wspX, int wspY ){
        super( swiat, sila, inicjatywa, wspX, wspY );
    }
    public Mlecz( Swiat swiat, int wspX, int wspY ){
        super( swiat, SILA_MLECZA, INICJATYWA_ROSLIN, wspX, wspY );
    }
    public Mlecz( Swiat swiat, Punkt pkt ){
        super( swiat, SILA_MLECZA, INICJATYWA_ROSLIN, pkt.getWspX(), pkt.getWspY() );
    }


    //
    //  NADPISANE
    //
    @Override
    public void akcja() {
        for( int i=0; i<ILE_PROB_ROZMNAZANIA; i++ )
            super.akcja();
    }
    @Override
    public String toString()
    {
        return "mlecz";
    }

    @Override
    public Color getColor(){ return Color.YELLOW; }

    @Override
    public Organizm getKopia()
    {
        return new Mlecz( this.getSwiat(), this.getSila(), this.getInicjatywa(), this.getWspX(), this.getWspY() );
    }
}
