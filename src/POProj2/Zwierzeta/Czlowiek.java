package POProj2.Zwierzeta;

import POProj2.Punkt;
import POProj2.Swiat;
import POProj2.Zwierze;

import javax.swing.*;
import java.awt.*;

public class Czlowiek extends Zwierze {
    private static final int SILA_CZLOWIEKA = 5;
    private static final int INICJATYWA_CZLOWIEKA = 4;
    private static final int CZAS_ODNAWIANIA_MAGICZNEGO__ELIKSIRU = 5;
    private static final int CZAS_TRWANIA_MAGICZNEGO_ELIKSIRU = 5;

    private boolean czyMagicznyEliksirAktywowany = false;
    private int czasOdnawianiaMagicznegoEliksiru = 5;
    private int czasTrwaniaMagicznegoEliksiru = 5;

    //
    //  KONSTRUKTORY
    //
    public Czlowiek( Swiat swiat, int sila, int inicjatywa, int wspX, int wspY ){
        super( swiat, sila, inicjatywa, wspX, wspY );
    }
    public Czlowiek( Swiat swiat, int wspX, int wspY ){
        super( swiat, SILA_CZLOWIEKA, INICJATYWA_CZLOWIEKA, wspX, wspY );
    }
    public Czlowiek( Swiat swiat, Punkt pkt ){
        super( swiat, SILA_CZLOWIEKA, INICJATYWA_CZLOWIEKA, pkt.getWspX(), pkt.getWspY() );
    }

    public boolean sprobojAktywowacMagicznyEliksir() {
        if( this.czasOdnawianiaMagicznegoEliksiru <= 0 )
        {
            this.getSwiat().getGUI().dodajDokomentarzy("Aktywowano Magiczny Eleksir, sila zwiekszyla sie do 10");
            this.czyMagicznyEliksirAktywowany = true;
            return true;
        }
        return false;
    }

    //
    //  NADPISANE
    //
    @Override
    public void akcja()
    {
        if( czyMagicznyEliksirAktywowany )
        {
            this.getSwiat().getKomentator().dodajWiadomosc("Pozostalo " + czasTrwaniaMagicznegoEliksiru + " tur do konca dzialania eliksiru" );
            this.setSila( this.sila + (10 - (CZAS_TRWANIA_MAGICZNEGO_ELIKSIRU - this.czasTrwaniaMagicznegoEliksiru)) );
            System.out.println( this.sila );

            this.czasTrwaniaMagicznegoEliksiru--;

            if( this.czasTrwaniaMagicznegoEliksiru <= 0 )
            {
                this.czyMagicznyEliksirAktywowany = false;
                this.czasOdnawianiaMagicznegoEliksiru = CZAS_ODNAWIANIA_MAGICZNEGO__ELIKSIRU;
                this.czasTrwaniaMagicznegoEliksiru = CZAS_TRWANIA_MAGICZNEGO_ELIKSIRU;
            }
        }

        if(czasOdnawianiaMagicznegoEliksiru > 0)
            czasOdnawianiaMagicznegoEliksiru--;
    }

    public boolean przemiescSie( int kierunek )
    {
        switch( kierunek )
        {
            case 38:
                if ( this.getWspY() > 0 ) {
                    super.przemiscSie(this.getWspX(), this.getWspY() - 1);
                    return true;
                }
                break;
            case 40:
                if ( this.getWspY() < this.getSwiat().getWymiarY()-1 ) {
                    super.przemiscSie(this.getWspX(), this.getWspY() + 1);
                    return true;
                }
            break;
            case 37:
                if ( this.getWspX() > 0 ) {
                    super.przemiscSie(this.getWspX()-1, this.getWspY());
                    return true;
                }
            break;
            case 39:
                if ( this.getWspX() < this.getSwiat().getWymiarX()-1 ) {
                    super.przemiscSie(this.getWspX()+1, this.getWspY());
                    return true;
                }
            break;
        }
        return false;
    }

    @Override
    public String toString()
    {
        return "czlowiek";
    }

    @Override
    public Color getColor(){ return Color.BLACK; }
}
