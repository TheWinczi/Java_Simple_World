package POProj2.Zwierzeta;

import POProj2.Organizm;
import POProj2.Punkt;
import POProj2.Rosliny.BarszczSosnowskiego;
import POProj2.Swiat;
import POProj2.Zwierze;
import POProj2.Rosliny.BarszczSosnowskiego;

import java.awt.*;
import java.util.List;

import static java.lang.Math.abs;

public class CyberOwca extends Zwierze {
    private static final int SILA_CYBER_OWCY = 11;
    private static final int INICJATYWA_CYBER_OWCY = 4;

    //
    //  KONSTRUKTORY
    //
    public CyberOwca( Swiat swiat, int sila, int inicjatywa, int wspX, int wspY ){
        super( swiat, sila, inicjatywa, wspX, wspY );
    }
    public CyberOwca( Swiat swiat, int wspX, int wspY ){
        super( swiat, SILA_CYBER_OWCY, INICJATYWA_CYBER_OWCY, wspX, wspY );
    }
    public CyberOwca( Swiat swiat, Punkt pkt ){
        super( swiat, SILA_CYBER_OWCY, INICJATYWA_CYBER_OWCY, pkt.getWspX(), pkt.getWspY() );
    }

    //
    //  NADPISANE
    //
    @Override
    public void akcja()
    {
        List<Organizm> listaBarszczowSosnowskiego = this.getListaOrganizmu( new BarszczSosnowskiego(null, 0, 0) );
        if( listaBarszczowSosnowskiego.size() > 0 )
        {
            Punkt punktBarszczuSosnowskiego = listaBarszczowSosnowskiego.get(0).getPunkt();
            for( Organizm organizm : listaBarszczowSosnowskiego )
            {
                if(
                        ((this.getWspX() - organizm.getWspX())*(this.getWspX() - organizm.getWspX()) + (this.getWspY() - organizm.getWspY())*(this.getWspY() - organizm.getWspY()))
                                <
                        ((this.getWspX() - punktBarszczuSosnowskiego.getWspX())*(this.getWspX() - punktBarszczuSosnowskiego.getWspX()) + (this.getWspY() * punktBarszczuSosnowskiego.getWspY()) * (this.getWspY() * punktBarszczuSosnowskiego.getWspY()) )
                  ) {
                    punktBarszczuSosnowskiego = organizm.getPunkt();
                }
            }

            int roznicaX = this.getWspX() - punktBarszczuSosnowskiego.getWspX();
            int roznicaY = this.getWspY() - punktBarszczuSosnowskiego.getWspY();

            if( roznicaX != 0 && abs(roznicaX) >= abs(roznicaY) ) {
                if( roznicaX < 0 )
                    this.przemiscSie(this.getWspX()+1, this.getWspY());
                else if( roznicaX > 0 )
                    this.przemiscSie(this.getWspX()-1, this.getWspY());
            }
            else if( roznicaY != 0 && abs(roznicaY) >= abs(roznicaX) ) {
                if( roznicaY < 0 )
                    this.przemiscSie(this.getWspX(), this.getWspY()+1);
                else if( roznicaY > 0 )
                    this.przemiscSie(this.getWspX(), this.getWspY()-1);
            }
        }
        else
            super.akcja();
    }

    @Override
    public String toString()
    {
        return "cyberowca";
    }

    @Override
    public Color getColor(){ return Color.darkGray; }

    @Override
    public Organizm getKopia()
    {
        return new CyberOwca( this.getSwiat(), this.getSila(), this.getInicjatywa(), this.getWspX(), this.getWspY() );
    }
}
