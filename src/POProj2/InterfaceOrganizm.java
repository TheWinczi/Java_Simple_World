package POProj2;

public interface InterfaceOrganizm {
    void akcja();
    void kolizja( Organizm atakujacy );
    void rysowanie();
    boolean czyOdbilAtak(Organizm atakujacy);
}
