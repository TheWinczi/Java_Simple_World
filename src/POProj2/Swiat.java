package POProj2;

import POProj2.GUI.GraphicalUserInterface;
import POProj2.Rosliny.*;
import POProj2.Zwierzeta.*;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.util.*;
import java.util.List;

public class Swiat{

    private int wymiarX;
    private int wymiarY;
    private int ileZwierzat;
    private int ileRoslin;
    private Organizm[][] plansza;
    private String[] nazwyZwierzat;
    private String[] nazwyRoslin;

    private List<Zwierze> listaZwierzat = new ArrayList<Zwierze>();
    private List<Roslina> listaRoslin = new ArrayList<Roslina>();
    private List<Organizm> doDodania = new ArrayList<>();
    private List<Organizm> doUsuniecia = new ArrayList<>();
    private int licznikTur;

    private Czlowiek czlowiek;
    private Komentator komentator = new Komentator( true );

    private static final int ILE_RODZAJOW_ZWIERZAT = 6;
    private static final int ILE_RODZAJOW_ROSLIN = 5;
    private static final String PLIK_Z_ZAPISEM = "dane.txt";
    private Random random = new Random();

    private GraphicalUserInterface GUI;

    //
    //      KONSTRUKTOR
    //
    public Swiat( int _wymiarX, int _wymiarY )
    {
        this.wymiarX = _wymiarX;
        this.wymiarY = _wymiarY;
        this.plansza = new Organizm[wymiarY][wymiarX];

        //this.nazwyZwierzat = new String[ILE_RODZAJOW_ZWIERZAT];
        nazwyZwierzat = new String[]{"Wilk", "Owca", "Lis", "Zolw", "Antylopa", "Cyber owca"};
        nazwyRoslin = new String[]{"Trawa", "Mlecz", "Guarana", "Wilcze jagody", "Barszcz sosnowskiego"};
        this.licznikTur = 0;
    }


    //
    //      GETTERY
    //
    public Komentator getKomentator(){ return this.komentator; }
    public String[] getNazwyZwierzat(){ return this.nazwyZwierzat; }
    public String[] getNazwyRoslin(){ return this.nazwyRoslin; }
    public int getWymiarX() { return this.wymiarX; }
    public int getWymiarY() { return wymiarY; }
    public int getKtoraTura(){ return this.licznikTur; }
    public GraphicalUserInterface getGUI(){ return this.GUI; }

    List<Organizm> getListaOrganizm( Organizm organizm )
    {
        List<Organizm> listaOrganizmu = new ArrayList<>();

        switch( organizm.getRodzaj() )
        {
            case "zwierze":
                for( Zwierze zwierze: listaZwierzat )
                {
                    if( zwierze.toString().equals(organizm.toString()) )
                        listaOrganizmu.add( zwierze );
                }
                break;
            case "roslina":
                for( Roslina roslina: listaRoslin )
                {
                    if( roslina.toString().equals(organizm.toString()))
                        listaOrganizmu.add(roslina);
                }
                break;
        }

        return listaOrganizmu;
    }



    //
    //      PUBLICZNE
    //
    public void rozpocznijNowaGre( int _ileZwierzat, int _ileRoslin, boolean czyLosowe )
    {
        this.ileRoslin = _ileRoslin;
        this.ileZwierzat = _ileZwierzat;
        this.licznikTur = 0;
        this.listaZwierzat.clear();
        this.listaRoslin.clear();

        if( czyLosowe )
        {
            this.wyprodukujOrganizmy( ileZwierzat, ileRoslin );
            this.zaludnijSwiat();
        }

        this.zaladujPlansze();
        this.komentator.dodajWiadomosc("Rozpoczeto nowa gre");
        this.komentator.dodajWiadomosc("Swiat o wymiarach " + this.getWymiarX() + "x" + this.getWymiarY());
    }

    public void wykonajTure()
    {
        Zwierze tmpZwierze;

        this.czlowiek.akcja();
        tmpZwierze = this.czyNastapilaKolizjaZeZwierzeciem( this.czlowiek );
        if( tmpZwierze != null && tmpZwierze.getCzyZywy() ) {
            tmpZwierze.kolizja(this.czlowiek);
        }

        for ( Zwierze zwierze : listaZwierzat )
        {
            zwierze.akcja();

            tmpZwierze = this.czyNastapilaKolizjaZeZwierzeciem( zwierze );
            if( tmpZwierze != null && tmpZwierze.getCzyZywy() ) {
                tmpZwierze.kolizja(zwierze);
            }
        }

        for( Roslina roslina : listaRoslin )
        {
            roslina.akcja();

            tmpZwierze = this.czyNastapilaKolizjaZeZwierzeciem( roslina );
            if( tmpZwierze != null && tmpZwierze.getCzyZywy() )
                roslina.kolizja( tmpZwierze );
        }

        for( Organizm org : doUsuniecia )
            usunOrganizm(org);

        for( Organizm org : doDodania ){
            if( org.getRodzaj().equals("zwierze") )
                this.listaZwierzat.add( (Zwierze)org );
            else if( org.getRodzaj().equals("roslina") )
                this.listaRoslin.add( (Roslina)org );

            org.setCzyZywy(true);
        }

        sprawdzKoniecGry();

        doUsuniecia.clear();
        doDodania.clear();
        sorujListe(listaZwierzat);

        this.zaladujPlansze();
        this.odswierzEkran();
        this.licznikTur++;
    }



    //
    //      DODAWANIE I USUWANIE ORGANIZMOW
    //
    void usunOrganizm( Organizm organizm )
    {
        this.komentator.dodajWiadomosc("Umarł " + organizm.toString());

        switch (organizm.getRodzaj()) {
            case "zwierze":
                this.listaZwierzat.remove(organizm);
                break;
            case "roslina":
                this.listaRoslin.remove(organizm);
                break;
            case "czlowiek":
                this.czlowiek.setCzyZywy(false);
                sprawdzKoniecGry();
                break;
        }
    }

    void dodajDoUsuniecia( Organizm organizm )
    {
        organizm.setCzyZywy(false);
        this.doUsuniecia.add( organizm );
    }

    void dodajDoDodania( Organizm organizm )
    {
        organizm.setCzyZywy(false);
        this.doDodania.add( organizm );
    }

    private void wyprodukujOrganizmy( int ileZwierzat, int ileRoslin )
    {
        Zwierze zwierzeDoDodania = new Zwierze( this,0,0,0,0);
        Punkt pkt;

        for( int i=0; i<ileZwierzat; i++ )
        {
            pkt = wylosujOryginalnyPunkt();
            switch(i%ILE_RODZAJOW_ZWIERZAT)
            {
                case 0:
                    zwierzeDoDodania = new Wilk(this, pkt.getWspX(), pkt.getWspY());
                    break;
                case 1:
                    zwierzeDoDodania = new Owca(this, pkt.getWspX(), pkt.getWspY());
                    break;
                case 2:
                    zwierzeDoDodania = new Lis(this, pkt.getWspX(), pkt.getWspY());
                    break;
                case 3:
                    zwierzeDoDodania = new Zolw(this, pkt.getWspX(), pkt.getWspY());
                    break;
                case 4:
                    zwierzeDoDodania = new Antylopa(this, pkt.getWspX(), pkt.getWspY());
                    break;
                case 5:
                    zwierzeDoDodania = new CyberOwca( this, pkt.getWspX(), pkt.getWspY());
                    break;
            }

            //System.out.println( "Dodaje " + zwierzeDoDodania.toString() + " " + zwierzeDoDodania.getWspX() + "x" + zwierzeDoDodania.getWspY() );
            this.listaZwierzat.add( zwierzeDoDodania );
        }

        Roslina roslinaDoDodania = new Roslina(this, 0,0,0,0);
        for( int i=0; i<ileRoslin; i++ )
        {
            pkt = wylosujOryginalnyPunkt();
            switch(i%ILE_RODZAJOW_ROSLIN)
            {
                case 0:
                    roslinaDoDodania = new Trawa(this, pkt.getWspX(), pkt.getWspY());
                    break;
                case 1:
                    roslinaDoDodania = new Mlecz(this, pkt.getWspX(), pkt.getWspY());
                    break;
                case 2:
                    roslinaDoDodania = new Guarana(this, pkt.getWspX(), pkt.getWspY());
                    break;
                case 3:
                    roslinaDoDodania = new WilczeJagody(this, pkt.getWspX(), pkt.getWspY());
                    break;
                case 4:
                    roslinaDoDodania = new BarszczSosnowskiego(this, pkt.getWspX(), pkt.getWspY());
                    break;
            }
            this.listaRoslin.add( roslinaDoDodania );
        }
    }

    public void dodajOrganizm( String nazwa, int wspX, int wspY )
    {
        switch( nazwa )
        {
            case "Wilk":
                this.listaZwierzat.add( new Wilk(this, wspX, wspY ) );
                break;
            case "Owca":
                this.listaZwierzat.add( new Owca(this, wspX, wspY ) );
                break;
            case "Lis":
                this.listaZwierzat.add( new Lis(this, wspX, wspY ) );
                break;
            case "Zolw":
                this.listaZwierzat.add( new Zolw(this, wspX, wspY ) );
                break;
            case "Antylopa":
                this.listaZwierzat.add( new Antylopa(this, wspX, wspY ) );
                break;
            case "Cyber owca":
                this.listaZwierzat.add( new CyberOwca( this, wspX, wspY ) );
                break;
            case "Trawa":
                this.listaRoslin.add( new Trawa(this, wspX, wspY ) );
                break;
            case "Mlecz":
                this.listaRoslin.add( new Mlecz(this, wspX, wspY ) );
                break;
            case "Guarana":
                this.listaRoslin.add( new Guarana(this, wspX, wspY ) );
                break;
            case "Wilcze jagody":
                this.listaRoslin.add( new WilczeJagody(this, wspX, wspY ) );
                break;
            case "Barszcz sosnowskiego":
                this.listaRoslin.add( new BarszczSosnowskiego(this, wspX, wspY ) );
                break;
        }
        zaladujPlansze();
    }



    //
    //      KOLIZJE
    //
    Zwierze czyNastapilaKolizjaZeZwierzeciem( Organizm organizmDoSprawdzenia )
    {
        if( this.czlowiek != null && this.czlowiek.getWspX() == organizmDoSprawdzenia.getWspX() && this.czlowiek.getWspY() == organizmDoSprawdzenia.getWspY() && this.czlowiek != organizmDoSprawdzenia )
            return this.czlowiek;

        for( Zwierze zwierze : listaZwierzat )
        {
            if( zwierze != organizmDoSprawdzenia && zwierze.getWspX() == organizmDoSprawdzenia.getWspX() && zwierze.getWspY() == organizmDoSprawdzenia.getWspY() )
                return zwierze;
        }

        return null;
    }
    Zwierze czyNastapilaKolizjaZeZwierzeciem( int wspX, int wspY )
    {
        if( this.czlowiek != null && this.czlowiek.getWspX() == wspX && this.czlowiek.getWspY() == wspY )
            return czlowiek;

        for( Zwierze zwierze : listaZwierzat )
        {
            if( zwierze.getWspX() == wspX && zwierze.getWspY() == wspY )
                return zwierze;
        }

        return null;
    }

    private Roslina czyNastapilaKolizjaZRoslina( int wspX, int wspY )
    {
        for( Roslina roslina : this.listaRoslin )
        {
            if( roslina.getWspX() == wspX && roslina.getWspY() == wspY )
                return roslina;
        }

        return null;
    }

    private Roslina czyNastapilaKolizjaZRoslina( Roslina roslinaDoSprawdzenia )
    {
        for( Roslina roslina : this.listaRoslin )
        {
            if( roslina.getWspX() == roslinaDoSprawdzenia.getWspX() && roslina.getWspY() == roslinaDoSprawdzenia.getWspY() && roslina != roslinaDoSprawdzenia )
                return roslina;
        }

        return null;
    }



    //
    //      LOSOWANIE I POBIERANIE PUNKTOW
    //
    private Punkt wylosujOryginalnyPunkt(){
        int wspX, wspY;
        wspX = this.random.nextInt(this.wymiarX-1);
        wspY = this.random.nextInt(this.wymiarY-1);

        Punkt nowyPunkt = new Punkt( wspX, wspY );

        while( czyNastapilaKolizjaZeZwierzeciem(nowyPunkt.getWspX(), nowyPunkt.getWspY()) != null
                && czyNastapilaKolizjaZRoslina(nowyPunkt.getWspX(), nowyPunkt.getWspY()) != null )
        {
            wspX = this.random.nextInt(this.wymiarX-1);
            wspY = this.random.nextInt(this.wymiarY-1);

            nowyPunkt.setWspX(wspX);
            nowyPunkt.setWspY(wspY);
        }

        return nowyPunkt;
    }

    Organizm getOrganizmWPunkcie( Punkt pkt ){

        if( this.czlowiek.getWspX() == pkt.getWspX() && this.czlowiek.getWspY() == pkt.getWspY() )
            return this.czlowiek;

        for( Zwierze zwierze: this.listaZwierzat ){
            if( zwierze.getWspX() == pkt.getWspX() && zwierze.getWspY() == pkt.getWspY() )
                return zwierze;
        }
        for( Roslina roslina: this.listaRoslin ){
            if( roslina.getWspX() == pkt.getWspX() && roslina.getWspY() == pkt.getWspY() )
                return roslina;
        }
        return null;
    }

    List<Organizm> getOrganizmyWokol( Punkt pkt, int skok ){
        List<Organizm> listaOrganizmow = new ArrayList<>();
        List<Punkt> listaPunktow = this.getMiejscaWokol( pkt, skok );
        Organizm organizm;

        for( Punkt punkt : listaPunktow )
        {
            organizm = this.getOrganizmWPunkcie( punkt );
            if( organizm != null )
                listaOrganizmow.add( organizm );
        }

        return listaOrganizmow;
    }
    List<Punkt> getMiejscaWokol( Punkt pkt, int skok )
    {
        List<Punkt> listaPunktow = new ArrayList<>();

        int prawaStrona = pkt.getWspX() + skok;
        int lewaStrona = pkt.getWspX() - skok;

        // Przesun sie po Y
        for( int i=pkt.getWspY()-skok;  i<=pkt.getWspY()+skok; i++ ) {
            if( i>0 && i < wymiarY )
            {
                if( lewaStrona > 0 )
                    listaPunktow.add( new Punkt(lewaStrona, i) );

                if( prawaStrona < wymiarX )
                    listaPunktow.add( new Punkt(prawaStrona, i) );
            }
        }

        // Przesun sie po X
        int gornaLinia = pkt.getWspY() - skok;
        int dolnaLinia = pkt.getWspY() + skok;

        for( int i=pkt.getWspX()-(skok-1);  i<=pkt.getWspX() + (skok-1); i++ ) {
            if( i>0 && i < this.wymiarX )
            {
                if( gornaLinia > 0 )
                    listaPunktow.add( new Punkt(i, gornaLinia) );

                if( dolnaLinia < wymiarY )
                    listaPunktow.add( new Punkt(i,dolnaLinia) );
            }
        }

        return listaPunktow;
    }



    //
    //      DODATKOWE
    //
    private void zaladujPlansze()
    {
        for( int i=0; i<this.wymiarY; i++ )
            for( int j=0; j<this.wymiarX; j++ )
                this.plansza[i][j] = null;

        for( Zwierze zwierze : this.listaZwierzat )
            this.plansza[zwierze.getWspY()][zwierze.getWspX()] = zwierze;

        for( Roslina roslina : this.listaRoslin )
            this.plansza[roslina.getWspY()][roslina.getWspX()] = roslina;

        if( this.czlowiek != null )
            this.plansza[this.czlowiek.getWspY()][this.czlowiek.getWspX()] = this.czlowiek;
    }

    private void sprawdzKoniecGry()
    {
        int wybor = -1;


        if( this.listaZwierzat.size() == 0 && this.czlowiek.getCzyZywy() ) {
            wybor = JOptionPane.showConfirmDialog(this.GUI, "BRAWO!\nZwyciestwo Czlowieka!\nChcesz zaczac od nowa?");
        }
        else if( !this.czlowiek.getCzyZywy() ){
            wybor = JOptionPane.showConfirmDialog(this.GUI, "PREGRANA :(\nZginal czlowiek\nChcesz zaczac od nowa?");
        }

        if( wybor == 0 ) {
            this.GUI.resetujKomentarze();
            rozpocznijNowaGre(this.ileZwierzat, this.ileRoslin, true);
        }
        else if ( wybor == 1 || wybor == 2 )
            System.exit(0);
    }

    private void zaludnijSwiat()
    {
        Punkt pkt = this.wylosujOryginalnyPunkt();
        this.czlowiek = new Czlowiek( this, pkt );

        this.komentator.dodajWiadomosc("Dodano czlowieka do swiata");
    }
    public void poruszCzlowiekiem( int wKtoraStrone )
    {
        if( this.czlowiek.przemiescSie( wKtoraStrone ) ){
            this.wykonajTure();
        }

    }
    public void sprobujAktywowacEliksir()
    {
        if( this.czlowiek.sprobojAktywowacMagicznyEliksir() )
            this.odswierzEkran();
    }

    private void sorujListe( List<Zwierze> lista )
    {
        lista.sort(new Comparator<Zwierze>() {
            @Override
            public int compare(Zwierze z1, Zwierze z2) {
                return Integer.compare(z2.getInicjatywa(), z1.getInicjatywa());
            }
        });
    }



    //
    //      GRAPHICS USER INTERFACE
    //
    public void rysujSwiat()
    {
        this.GUI = new GraphicalUserInterface( this, this.plansza );
        GUI.init();
        GUI.repaint();
    }
    private void odswierzEkran()
    {
        this.GUI.repaint();
        this.GUI.odswierzKomentarze();
        this.GUI.toFront();
    }


    //
    //      OBSLUGA ZDARZEN
    //
    public void przechwycZdarzenie( KeyEvent keyEvent )
    {
        switch( keyEvent.getKeyCode() )
        {
            case 49:
                this.czlowiek.sprobojAktywowacMagicznyEliksir();
                break;
            case 37:
            case 38:
            case 39:
            case 40:
                if( this.czlowiek.przemiescSie(keyEvent.getKeyCode()) ) {
                    this.wykonajTure();
                }
                break;
            case 76:
                wczytajZPliku();
                break;
            case 83:
                zapiszDoPliku();
                break;
            default:
                System.out.println( keyEvent.getKeyCode() );
        }
    }




    //
    //      WSPOLPRACA Z PLIKAMI
    //
    public void zapiszDoPliku()
    {
        try{
            PrintWriter writer = new PrintWriter(PLIK_Z_ZAPISEM);
            writer.println( this.getWymiarX() + " " + this.getWymiarY() );
            writer.println( this.listaZwierzat.size() + " " + this.listaRoslin.size() );
            writer.println( this.licznikTur );
            writer.println( this.czlowiek.toString() + " " + this.czlowiek.getSila() + " " + this.czlowiek.getInicjatywa() + " " + this.czlowiek.getWspX() + " " + this.czlowiek.getWspY() );

            for( Zwierze zwierze : this.listaZwierzat )
                writer.println( zwierze.toString()  + " " + zwierze.getSila() + " " + zwierze.getInicjatywa() + " " + zwierze.getWspX() + " " + zwierze.getWspY() );

            for( Roslina roslina : this.listaRoslin )
                writer.println( roslina.toString()  + " " + roslina.getSila() + " " + roslina.getInicjatywa() + " " + roslina.getWspX() + " " + roslina.getWspY() );

            writer.close();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        JOptionPane.showMessageDialog(this.GUI, "Zapisano stan gry");
    }

    public void wczytajZPliku()
    {
        try{
            Scanner scanner = new Scanner(Paths.get(PLIK_Z_ZAPISEM));

            this.listaZwierzat.clear();
            this.listaRoslin.clear();

            this.wymiarX = scanner.nextInt();
            this.wymiarY = scanner.nextInt();
            this.ileZwierzat = scanner.nextInt();
            this.ileRoslin = scanner.nextInt();
            this.licznikTur = scanner.nextInt();

            //System.out.println( wymiarX + " "+ wymiarY + " " + ileZwierzat + " " + ileRoslin);
            int sila, inicjatywa, wspX, wspY;

            String zwierze;
            for( int i=0; i<this.ileZwierzat+1; i++ )
            {
                zwierze = scanner.next();
                sila = scanner.nextInt();
                inicjatywa = scanner.nextInt();
                wspX = scanner.nextInt();
                wspY = scanner.nextInt();

                //System.out.println( zwierze + " " + sila + " " + inicjatywa + " " + wspX + " " + wspY );

                Zwierze zwierzeDoDodania = new Zwierze(null, 0,0,0,0);
                switch( zwierze )
                {
                    case "wilk":
                        zwierzeDoDodania = new Wilk(this, sila, inicjatywa, wspX, wspY );
                        break;
                    case "owca":
                        zwierzeDoDodania = new Owca(this, sila, inicjatywa, wspX, wspY );
                        break;
                    case "lis":
                        zwierzeDoDodania = new Lis(this, sila, inicjatywa, wspX, wspY );
                        break;
                    case "zolw":
                        zwierzeDoDodania = new Zolw(this, sila, inicjatywa, wspX, wspY );
                        break;
                    case "antylopa":
                        zwierzeDoDodania = new Antylopa(this, sila, inicjatywa, wspX, wspY );
                        break;
                    case "cyberowca":
                        zwierzeDoDodania = new CyberOwca( this, sila, inicjatywa, wspX, wspY );
                        break;
                    case "czlowiek":
                        zwierzeDoDodania = null;
                        this.czlowiek = new Czlowiek( this, sila, inicjatywa, wspX, wspY );
                        break;
                }
                if( zwierzeDoDodania != null )
                    this.listaZwierzat.add( zwierzeDoDodania );
            }

            String roslina;
            Roslina roslinaDoDodania = new Roslina(this,0,0,0,0);
            for( int i=0; i<this.ileRoslin; i++ )
            {
                roslina = scanner.next();
                sila = scanner.nextInt();
                inicjatywa = scanner.nextInt();
                wspX = scanner.nextInt();
                wspY = scanner.nextInt();

                //System.out.println(roslina + " " + sila + " " + inicjatywa + " " + wspX + " " + wspY );

                switch( roslina )
                {
                    case "trawa":
                        roslinaDoDodania = new Trawa(this, sila, inicjatywa, wspX, wspY );
                        break;
                    case "mlecz":
                        roslinaDoDodania = new Mlecz(this, sila, inicjatywa, wspX, wspY );
                        break;
                    case "guarana":
                        roslinaDoDodania = new Guarana(this, sila, inicjatywa, wspX, wspY );
                        break;
                    case "wilczejagody":
                        roslinaDoDodania = new WilczeJagody(this, sila, inicjatywa, wspX, wspY );
                        break;
                    case "barszczsosnowskiego":
                        roslinaDoDodania = new BarszczSosnowskiego(this, sila, inicjatywa, wspX, wspY );
                        break;
                }
                this.listaRoslin.add( roslinaDoDodania );
            }
            scanner.close();
            JOptionPane.showMessageDialog( this.GUI,"Zaladowano z Pliku" );
            this.zaladujPlansze();
            this.odswierzEkran();
        }
        catch (IOException ignored) {
            JOptionPane.showMessageDialog( this.GUI,"Nie udało się zaladowac z pliku" );
        }
    }
}
