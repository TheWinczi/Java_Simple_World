package POProj2.Rosliny;

import POProj2.Organizm;
import POProj2.Roslina;
import POProj2.Swiat;

import java.awt.*;
import java.util.List;

public class BarszczSosnowskiego extends Roslina {

    private static final int SILA_BARSZCZU_SOSNOWSKIEGO = 10;

    public BarszczSosnowskiego( Swiat swiat, int sila, int inicjatywa, int wspX, int wspY) {
        super( swiat, sila, inicjatywa, wspX, wspY );
    }
    public BarszczSosnowskiego( Swiat swiat, int wspX, int wspY ) {
        super( swiat, SILA_BARSZCZU_SOSNOWSKIEGO, INICJATYWA_ROSLIN, wspX, wspY );
    }

    @Override
    public void akcja()
    {
        List<Organizm> listaOrganizmow = this.getOrganizmyWokol( this.getPunkt(), 1 );
        for( Organizm organizm: listaOrganizmow )
        {
            if( organizm.getRodzaj().equals("zwierze") )
            {
                if( organizm.toString().equals("cyberowca") )
                    ;
                else
                    organizm.zgin();
            }
        }
    }
    @Override
    public void kolizja( Organizm atakujacy )
    {
        if( atakujacy.getRodzaj().equals("zwierze") )
        {
            if( atakujacy.toString().equals("cyberowca") )
                this.zgin();
            else
                atakujacy.zgin();
        }
    }

    @Override
    public String toString()
    {
        return "barszczsosnowskiego";
    }
    @Override
    public Color getColor() { return new Color(100,96,0); } // ciemnozolty

    @Override
    public Organizm getKopia()
    {
        return new BarszczSosnowskiego( this.getSwiat(), this.getSila(), this.getInicjatywa(), this.getWspX(), this.getWspY() );
    }
}
