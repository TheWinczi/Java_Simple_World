package POProj2;

import java.awt.desktop.SystemEventListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Komentator {
    private StringBuilder wiadomosc = new StringBuilder();
    private StringBuilder informacjeDlaUzytkownika = new StringBuilder();
    private int iterator;
    private boolean czyAktywny;

    //
    //  KONSTRUKTOR
    //
    Komentator( boolean _czyAktywny ){
        this.czyAktywny = _czyAktywny;
        this.informacjeDlaUzytkownika.append("Witaj w Symulatorze zycia" + '\n');
        this.informacjeDlaUzytkownika.append("Stworzonej przez Macieja Winczewskiego" + '\n');
        this.informacjeDlaUzytkownika.append("na potrzeby projektu z przedmiotu" + '\n');
        this.informacjeDlaUzytkownika.append("Programowanie Obiektowe" + '\n' + '\n');
        this.informacjeDlaUzytkownika.append("Kilka podstawowych i przydatnych informacji:" + '\n');
        this.informacjeDlaUzytkownika.append("- Czlowiekiem mozesz poruszać" + '\n');
        this.informacjeDlaUzytkownika.append("  Uzywajac strzałek na klawiaturze" + '\n');
        this.informacjeDlaUzytkownika.append("- Umiejetnosc specjalna aktywujesz klawiszem 1" + '\n');
        this.informacjeDlaUzytkownika.append("- zapisywanie i odczytywanie rozgrywki " + '\n');
        this.informacjeDlaUzytkownika.append("- możesz kontrolować klawiszami l-load i s-save" + '\n' + '\n');
        this.informacjeDlaUzytkownika.append("Miłego Grania :)" + '\n');

        this.iterator = 0;
    }


    //
    //  SETTERY
    //
    public void setAktywny( boolean _czyAktywny ) {
        this.czyAktywny = _czyAktywny;
    }


    //
    //  GETTERY
    //
    public String getWiadomosc() {
        if( this.czyAktywny )
        {
            return this.wiadomosc.toString();
        }
        return null;
    }
    boolean getCzyAktywny(){
        return this.czyAktywny;
    }
    public String getInformacjeDlaUzytkownika(){
        return this.informacjeDlaUzytkownika.toString();
    }



    //
    //  DODATKOWE
    //
    public void dodajWiadomosc( String nowaWiadomosc ){
        if( this.czyAktywny )
        {
            this.iterator++;
            wiadomosc.append( iterator + ". " + nowaWiadomosc + "\n" );
        }

    }
    public void resetujWiadomosc(){
        if( this.czyAktywny ){
            this.wiadomosc.delete( 0, wiadomosc.length() );
            this.iterator = 0;
        }
    }


}
