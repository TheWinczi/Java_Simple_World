package POProj2.Zwierzeta;

import POProj2.Organizm;
import POProj2.Punkt;
import POProj2.Swiat;
import POProj2.Zwierze;

import java.awt.*;
import java.util.List;

public class Lis extends Zwierze {
    private static final int SILA_LISA = 3;
    private static final int INICJATYWA_LISA = 7;

    //
    //  KONSTRUKTORY
    //
    public Lis(Swiat swiat, int sila, int inicjatywa, int wspX, int wspY ){
        super( swiat, sila, inicjatywa, wspX, wspY );
    }
    public Lis( Swiat swiat,int wspX, int wspY ){
        super( swiat, SILA_LISA, INICJATYWA_LISA, wspX, wspY );
    }
    public Lis( Swiat swiat, Punkt pkt ){
        super( swiat, SILA_LISA, INICJATYWA_LISA, pkt.getWspX(), pkt.getWspY() );
    }

    //
    //  NADPISANE
    //
    @Override
    public void akcja()
    {
        List<Organizm> listaOrganizmow = this.getOrganizmyWokol(this.getPunkt(), this.skok);
        boolean czyZnalazlMiejsce = false;

        for( Organizm organizm : listaOrganizmow )
        {
            if( organizm.getRodzaj().equals("roslina") )
            {
                this.przemiscSie(organizm.getWspX(), organizm.getWspY());
                czyZnalazlMiejsce = true;
            }
            else if( organizm.getRodzaj().equals("zwierze") )
            {
                if( organizm.getSila() < 4 ) {
                    this.przemiscSie(organizm.getWspX(), organizm.getWspY());
                    czyZnalazlMiejsce = true;
                }
            }
        }
        if( !czyZnalazlMiejsce )
            super.akcja();
    }

    @Override
    public String toString()
    {
        return "lis";
    }

    @Override
    public Color getColor(){ return Color.ORANGE; }

    @Override
    public Organizm getKopia()
    {
        return new Lis( this.getSwiat(), this.getSila(), this.getInicjatywa(), this.getWspX(), this.getWspY() );
    }
}
