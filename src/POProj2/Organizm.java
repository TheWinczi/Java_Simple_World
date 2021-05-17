package POProj2;

import java.awt.*;

public abstract class Organizm implements InterfaceOrganizm {

    //
    //  GETTERY
    //
    abstract public int getSila();
    abstract public int getInicjatywa();
    abstract public Swiat getSwiat();
    abstract public int getWspX();
    abstract public int getWspY();
    abstract public Punkt getPunkt();
    abstract public String getRodzaj();


    //
    //  SETTERY
    //
    abstract public void setSila( int _sila );
    abstract public void setInicjatywa( int _inicjatywa);
    abstract public void setWspX( int _wspX );
    abstract public void setWspY( int _wspY );
    abstract public void setCzyZywy( boolean _czyZywy );

    //
    //  DODATKOWE
    //
    public abstract void akcja();
    public abstract void kolizja( Organizm atakujacy );
    public abstract boolean czyOdbilAtak( Organizm atakujacy );
    public abstract void rysowanie();
    public abstract void zgin();
    public abstract Color getColor();
    public abstract Organizm getKopia();

}
