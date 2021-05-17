package POProj2.GUI;

import POProj2.Organizm;
import POProj2.Swiat;

import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;

import static java.awt.Color.WHITE;

public class GraphicalUserInterface extends JFrame implements ActionListener, KeyListener, MouseListener {

    private int szerokosc, wysokosc;

    private Swiat swiat;
    private Organizm[][] tablicaOrganizmow;
    private int szerokoscPlanszy;
    private int offSetX, offSetY;
    private int wymiarOkna;

    private Plansza plansza;
    private JFrame oknoKomentarzy;
    private JTextPane tekstKomentarzy;

    private JButton przyciskDodaniaOrganizmu;
    private JButton przyciskKolejnejTury;
    private JFrame oknoDodawaniaOrganizmu;
    private String pomocnicza;
    private int wspXNowegoOrganizmu, wspYNowegoOrganizmu;

    Toolkit kit = Toolkit.getDefaultToolkit();
    Dimension ekran = kit.getScreenSize();

    public GraphicalUserInterface( Swiat _swiat, Organizm[][] _plansza )
    {
        this.swiat = _swiat;
        this.tablicaOrganizmow = _plansza;
    }

    public void init()
    {
        this.szerokosc = (int)ekran.getWidth()/2;
        this.wysokosc = (int)ekran.getHeight()/2;
        this.wymiarOkna = Math.min(szerokosc, wysokosc);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Projekt Programowanie Obiektowa Maciej Winczewski 183211");
        this.setResizable(false);
        this.setLocation( ((int)ekran.getWidth()/2 - wymiarOkna/2), ((int)ekran.getHeight()/2 - wymiarOkna/2) );
        this.setLayout(new GridLayout(1,1) );

        wyswietlKomentarze();
        wyswietlInformacje();
        this.plansza = new Plansza();
        this.add( plansza );

        this.setFocusable(true);
        this.addKeyListener(this);
        this.addMouseListener(this);
        this.setSize(wymiarOkna,wymiarOkna);
        this.setFocusable(true);
        this.repaint();
        this.setVisible(true);
        this.toFront();


    }

    public void wyswietlInformacje()
    {
        JFrame oknoInformacji = new JFrame();

        oknoInformacji.setLayout( new GridLayout(0,1) );
        oknoInformacji.setSize(300,this.wysokosc);
        oknoInformacji.setLocation(((int)ekran.getWidth()/2 - wymiarOkna/2) - 300, ((int)ekran.getHeight()/2 - wysokosc/2));
        oknoInformacji.setLayout(null);
        JTextPane tekstInformacji = new JTextPane();

        JPanel jp = new JPanel( new GridLayout(0,1) );
        jp.setLayout( new GridLayout(0,1) );
        jp.setSize(300,(int)(this.wysokosc * 0.8));

        StyledDocument doc = tekstInformacji.getStyledDocument();
        SimpleAttributeSet center = new SimpleAttributeSet();
        StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
        doc.setParagraphAttributes(10, doc.getLength(), center, true);

        tekstInformacji.setText( ":INFORMACJE:" + "\n\n" + this.swiat.getKomentator().getInformacjeDlaUzytkownika() );
        jp.add(tekstInformacji);
        oknoInformacji.add(jp);

        przyciskKolejnejTury = new JButton("Wykonaj ture");
        przyciskKolejnejTury.setBounds(70,(int)(this.wysokosc*0.8)+20, 150,30);
        przyciskKolejnejTury.addActionListener(this);
        oknoInformacji.add( przyciskKolejnejTury );

        tekstInformacji.setEditable(false);
        oknoInformacji.setResizable(false);
        oknoInformacji.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        oknoInformacji.setVisible(true);
        oknoInformacji.repaint();
    }

    public void wyswietlKomentarze()
    {
        oknoKomentarzy = new JFrame();
        oknoKomentarzy.setSize(300,this.wysokosc);
        oknoKomentarzy.setLocation(((int)ekran.getWidth()/2 - wymiarOkna/2) + this.wymiarOkna, ((int)ekran.getHeight()/2 - wysokosc/2));
        tekstKomentarzy = new JTextPane();

        StyledDocument doc = tekstKomentarzy.getStyledDocument();
        SimpleAttributeSet center = new SimpleAttributeSet();
        StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
        doc.setParagraphAttributes(10, doc.getLength(), center, true);

        tekstKomentarzy.setText( "Tura " + this.swiat.getKtoraTura() + "\n\n" + this.swiat.getKomentator().getWiadomosc() );
        this.swiat.getKomentator().resetujWiadomosc();

        oknoKomentarzy.add(tekstKomentarzy);
        tekstKomentarzy.setEditable(false);
        oknoKomentarzy.setResizable(false);
        oknoKomentarzy.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        oknoKomentarzy.setVisible(true);
        oknoKomentarzy.repaint();
    }
    public void odswierzKomentarze()
    {
        tekstKomentarzy.setText( "Tura " + this.swiat.getKtoraTura() + "\n\n" + this.swiat.getKomentator().getWiadomosc() );
        this.swiat.getKomentator().resetujWiadomosc();
    }
    public void dodajDokomentarzy( String doDodania )
    {
        tekstKomentarzy.setText( tekstKomentarzy.getText() + '\n' + doDodania );
    }
    public void resetujKomentarze()
    {
        this.tekstKomentarzy.setText("");
        this.swiat.getKomentator().resetujWiadomosc();
    }

    private class Plansza extends JComponent
    {
        int wymiarKwadratu;
        int offSetX;
        int offSetY;

        public Plansza()
        {
            this.wymiarKwadratu = (wymiarOkna) / (Math.max(swiat.getWymiarX(), swiat.getWymiarY())+2);
            this.offSetX = wymiarKwadratu*2;
            this.offSetY = wymiarKwadratu;
        }

        public void paintComponent( Graphics g )
        {
            for( int i=0; i < swiat.getWymiarY(); i++ ) {
                for( int j=0; j < swiat.getWymiarX(); j++ )
                {
                    Graphics2D g2D = (Graphics2D)g ;
                    g2D.setColor(WHITE);
                    Rectangle2D rect = new Rectangle2D.Double( j*wymiarKwadratu + offSetX/2, i*wymiarKwadratu+offSetY/2, wymiarKwadratu-2, wymiarKwadratu-2 );

                    if( tablicaOrganizmow[i][j] != null ){
                        g2D.setColor(tablicaOrganizmow[i][j].getColor());
                    }

                    this.setCursor( Cursor.getPredefinedCursor( Cursor.CROSSHAIR_CURSOR ) );

                    g2D.fill(rect);
                    g2D.draw(rect);
                }
            }
        }

        void sprobojDodacOrganizm( int wspX, int wspY )
        {
            if( czyPusty(wspX,wspY) )
                panelDodawaniaOrganizmu();
            else
                dodajDokomentarzy("Nie mozna dodac organizmu na zajete pole");
        }

        boolean czyPusty( int _wspX, int _wspY )
        {
            wspXNowegoOrganizmu = (_wspX - offSetX/2 - 5 )/wymiarKwadratu;
            wspYNowegoOrganizmu = (_wspY - offSetY/2 - 30 )/wymiarKwadratu;

            if( tablicaOrganizmow[wspYNowegoOrganizmu][wspXNowegoOrganizmu] == null )
                return true;
            else
                return false;
        }

    }

    public void panelDodawaniaOrganizmu()
    {
        oknoDodawaniaOrganizmu = new JFrame("Panel dodawania organizmu");
        oknoDodawaniaOrganizmu.setSize(300,this.wysokosc);
        oknoDodawaniaOrganizmu.setResizable(false);
        oknoDodawaniaOrganizmu.setLayout( null );
        oknoDodawaniaOrganizmu.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        oknoDodawaniaOrganizmu.setLocation( ((int)ekran.getWidth()/2 - wymiarOkna/2)-300 , ((int)ekran.getHeight()/2 - wysokosc/2));

        ButtonGroup BG = new ButtonGroup();
        JPanel jrp = new JPanel( new GridLayout(0,1) );
        jrp.setSize(300,(int)(this.wysokosc * 0.8));
        String[] tab = this.swiat.getNazwyZwierzat();

        for( String str : tab )
        {
            JRadioButton radioButton = new JRadioButton(str, false);
            radioButton.addActionListener(this);
            BG.add(radioButton);
            jrp.add( radioButton );
        }

        tab = this.swiat.getNazwyRoslin();
        for( String str : tab )
        {
            JRadioButton radioButton = new JRadioButton(str,false);
            BG.add(radioButton);
            jrp.add( radioButton );
            radioButton.addActionListener(this);
        }

        przyciskDodaniaOrganizmu = new JButton("Dodaj");
        przyciskDodaniaOrganizmu.setBounds(70,(int)(this.wysokosc * 0.8) + 10, 150,50);
        przyciskDodaniaOrganizmu.addActionListener(this);

        oknoDodawaniaOrganizmu.add(jrp);
        oknoDodawaniaOrganizmu.add(przyciskDodaniaOrganizmu);
        oknoDodawaniaOrganizmu.setVisible(true);

    }


    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        this.repaint();
        if( actionEvent.getSource() == przyciskDodaniaOrganizmu )
        {
            if( pomocnicza != null )
            {
                swiat.dodajOrganizm( pomocnicza, wspXNowegoOrganizmu, wspYNowegoOrganizmu );
                oknoDodawaniaOrganizmu.dispose();
                dodajDokomentarzy("Dodano " + pomocnicza + " na pozycji " + wspXNowegoOrganizmu + "x" + wspYNowegoOrganizmu );
                this.repaint();
            }

        }
        else if( actionEvent.getSource() instanceof JRadioButton ) {
            pomocnicza = actionEvent.getActionCommand();
        }
        else if( actionEvent.getSource() == przyciskKolejnejTury )
            swiat.wykonajTure();
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {
        // nie implementowac
    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        this.swiat.przechwycZdarzenie(keyEvent);
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
        // nie implementowac
    }
    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        this.plansza.sprobojDodacOrganizm( mouseEvent.getX(), mouseEvent.getY() );
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        // nie implementowac
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
        // nie implementowac
    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {
        // nie implementowac
    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {
        // nie implementowac
    }
}
