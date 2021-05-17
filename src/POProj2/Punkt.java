package POProj2;

public class Punkt {
    private int wspX;
    private int wspY;

    //
    //  KONSTRUKTORY
    //
    public Punkt()
    {
        this.wspX = 0;
        this.wspY = 0;
    }
    public Punkt( int _wspX, int _wspY )
    {
        this.wspX = _wspX;
        this.wspY = _wspY;
    }

    //
    //  GETTERY
    //
    public Punkt getPunkt() {
        return this;
    }
    public int getWspX() {
        return this.wspX;
    }
    public int getWspY() {
        return this.wspY;
    }

    //
    //  SETTERY
    //
    public void setWspX( int _wspX ){
        this.wspX = _wspX;
    }
    public void setWspY( int _wspY ){
        this.wspY = _wspY;
    }

}
