package POProj2.Rosliny;

import POProj2.Organizm;
import POProj2.Punkt;
import POProj2.Roslina;
import POProj2.Swiat;
import POProj2.Zwierzeta.Wilk;

import java.awt.*;

public class WilczeJagody extends Roslina {
    private static final int SILA_WILCZYCH_JAGOD = 99;

    //
    //  KONTRUKTORY
    //
    public WilczeJagody(Swiat swiat, int sila, int inicjatywa, int wspX, int wspY ){
        super( swiat, sila, inicjatywa, wspX, wspY );
    }
    public WilczeJagody( Swiat swiat, int wspX, int wspY ){
        super( swiat, SILA_WILCZYCH_JAGOD, INICJATYWA_ROSLIN, wspX, wspY );
    }
    public WilczeJagody( Swiat swiat, Punkt pkt ){
        super( swiat, SILA_WILCZYCH_JAGOD, INICJATYWA_ROSLIN, pkt.getWspX(), pkt.getWspY() );
    }


    //
    //  NADPISANE
    //
    @Override
    public void kolizja( Organizm atakujacy ) {
        if( atakujacy.getRodzaj().equals("zwierze") ) {
            atakujacy.zgin();
            this.zgin();
        }
    }
    @Override
    public String toString()
    {
        return "wilczejagody";
    }

    @Override
    public Color getColor(){ return Color.blue; }

    @Override
    public Organizm getKopia()
    {
        return new WilczeJagody( this.getSwiat(), this.getSila(), this.getInicjatywa(), this.getWspX(), this.getWspY() );
    }
}
