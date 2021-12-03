package tetris;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ConcurrentModificationException;
import javax.swing.JComponent;
import javax.swing.JFrame;


public class TetrisJuego extends JFrame { //Arreglado 21-60
    
    public static void main(String[] args) {
        
        TetrisJuego tetrisJuego; //Inicia TetrisJuego
        tetrisJuego = new TetrisJuego();
        
        
    }
    
    private String titulo = "Tetris";
    private int ancho = 600;
    private int alto = 720;
    private final int increment = 20;
    private final int decrement = -20;
    private int Velocidad = 500;
    private int VelocidadCercania = 400;
    private int VelocidadMemoria;
    private int IndiceFigura;
    private int Premio;
    private int Bonus;
    private int ContadorLineas;
    private int Level_Map;
    private int PuntosJuego;
    private int TotalFiguras;
    
    private final int Min_Izq = 200;
    private final int Max_Der = 400;
    private final int Max_Aba = 660;
    
    private boolean Pausar;
    private boolean BloqueaTeclado;
    private boolean BloqueaAbajo;
    private boolean BloqueaTecla_Rotar;
    private boolean Final_Carrera;
    private boolean NuevoCiclo;
    private boolean FindelJuego;
    
    private ArrayList<BufferedImage> Figura2D = new ArrayList<>();
    private ArrayList<BufferedImage> ListaFiguras = new ArrayList<>();
    private ArrayList<BufferedImage> Letras = new ArrayList<>();
    private ArrayList<BufferedImage> Numeros = new ArrayList<>();
    private ArrayList<BufferedImage> FondoGrafico = new ArrayList<>();
    
    private ArrayList<BufferedImage> Figura_2D = new ArrayList<>(); //Arreglado 62-67
    private ArrayList<Integer> Figura_PosiX = new ArrayList<>();
    private ArrayList<Integer> Figura_PosiY = new ArrayList<>();
    private ArrayList<String> Figura_Tipo = new ArrayList<>();
    private ArrayList<Integer> Figura_Estado = new ArrayList<>();
    private ArrayList<Integer> Figura_Ronda = new ArrayList<>();
    
    private ReproduceAudio ReproAudio = new ReproduceAudio();
    private Rotar_Figuras Rot_Fig = new Rotar_Figuras(this, Figura_PosiX, Figura_PosiY, Figura_Tipo, Figura_Estado);
    
    public TetrisJuego() { //Arreglado 74-105
        
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle(titulo);
        setBounds(0, 0, ancho, alto);
        
        Imagenes_Juego Img_Juego = new Imagenes_Juego();
        
        Img_Juego.CargarFiguras(Figura2D);
        Img_Juego.CargarListaFiguras(ListaFiguras);
        Img_Juego.CargarLetras(Letras);
        Img_Juego.CargarNumeros(Numeros);
        Img_Juego.CargarFondos(FondoGrafico);
        
        add(new PlantillaGrafica());
        
        setVisible(true);
        
        EventosAccionEscucha();
        
        for (int x=0;x<1000;x++){
            Figura_Ronda.add(GenerarNumero()); // Verificar Video 1 13:04
        }
        
        CrearBucle();
        
        CrearFigura(Figura_Ronda.get(TotalFiguras));
        
        ReproAudio.Fx(7);
        
        
        
    } //Arreglado
    
    
    public class PlantillaGrafica extends JComponent {      
        
        
        @Override
        public void paint(Graphics g) {
            
            
            Graphics2D g2 = (Graphics2D) g;
            
            try {
                g2.drawImage(FondoGrafico.get(Level_Map), 0, 0, rootPane);
            } catch (IndexOutOfBoundsException e) {
                g2.drawImage(FondoGrafico.get(17), 0, 0, rootPane); 
            }
            
            /*
            Dibujar_Texto_Grafico(g2, 10, 40, 25, 25, 22, "Puntos");
            Dibujar_Texto_Grafico(g2, 10, 80, 20, 20, 18, ""+PuntosJuego);
            
            Dibujar_Texto_Grafico(g2, 10, 140, 25, 25, 22, "Lineas");
            Dibujar_Texto_Grafico(g2, 10, 180, 20, 20, 20, ""+ContadorLineas);
            
            Dibujar_Texto_Grafico(g2, 10, 220, 25, 25, 21, "Figuras");
            Dibujar_Texto_Grafico(g2, 10, 260, 15, 15, 18, ""+TotalFiguras);
            
            Dibujar_Texto_Grafico(g2, 10, 300, 25, 25, 21, "Nivel");
            Dibujar_Texto_Grafico(g2, 10, 340, 22, 22, 22, ""+Level_Map);
            */
            
            g2.setFont(new Font("Verdana", Font.BOLD, 24));
            g2.setColor(Color.WHITE);
            g2.drawString("Puntos ", 10, 40);
            g2.drawString(""+PuntosJuego, 10, 80);
            
            g2.drawString("Lineas ", 10, 140);
            g2.drawString(""+ContadorLineas, 10, 180);
            
            g2.drawString("Figuras ", 10, 220);
            g2.drawString(""+TotalFiguras, 10, 260);
            
            g2.drawString("Nivel ", 10, 300);
            g2.drawString(""+Level_Map, 10, 340);
            
            try {
                if (!Pausar) {
                    int Index = 0;
                    for (int Estado:Figura_Estado) {
                        if (Estado>=1){
                            g2.drawImage(Figura_2D.get(Index), getFx(Index), getFy(Index), rootPane);
                        }
                        Index++;
                    }
                }
            }catch(ConcurrentModificationException e) {
                e.getStackTrace();
            }
                
            int incrementa=0;
            for (int c=0;c<6;c++){
                g2.drawImage(ListaFiguras.get(Figura_Ronda.get(TotalFiguras+c)), 470, 50+incrementa, rootPane);
                incrementa=incrementa+50;
            }
            
            
                
            for (int Tex=0;Tex<680;Tex+=20){
                    
                    g2.drawImage(Figura2D.get(8), 160, 0+Tex, rootPane);
                    g2.drawImage(Figura2D.get(8), 180, 0+Tex, rootPane);
                    g2.drawImage(Figura2D.get(8), 400, 0+Tex, rootPane);
                    g2.drawImage(Figura2D.get(8), 420, 0+Tex, rootPane);
                    
                } //Arreglado
                
                for (int Text=0;Text<240;Text+=20){
                    g2.drawImage(Figura2D.get(8), 180+Text, 660, rootPane);
                } //Arreglado
                
                for (int Text=0;Text<600;Text+=20){
                    g2.drawImage(Figura2D.get(8), Text, 680, rootPane);
                } //Arreglado
                
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f)); //Arreglado
                
                for (int x = Min_Izq;x<Max_Der;x=x+20){
                    g2.setColor(Color.DARK_GRAY);
                    g2.drawLine(x, 0, x, Max_Aba);
                }
                
                for (int y=20;y<Max_Aba+20;y=y+20){
                    g.drawLine(Min_Izq, y, Max_Der, y);
                }
                
                if (FindelJuego) {
                    Dibujar_Texto_Grafico(g2, 30, 500, 40, 40, 40, "Fin del Juego");
                }
                
                if (Pausar) {
                    Dibujar_Texto_Grafico(g2, 70, 300, 40, 40, 30, "JUEGO PAUSADO");
                }
                
                g2.setFont(new Font("Verdana",Font.BOLD, 15));
                g2.setColor(Color.WHITE);
                g2.drawString(""+Velocidad, 400, 675);
            
        }
        
    
    } //Verificado
    
    public void Dibujar_Texto_Grafico(Graphics2D g2, int X, int Y, int Ancho, int Alto, int separacion, String texto) {
        
        int longitud = texto.length();
        
        int vueltas=0;
        String LE[]={"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z"};
        String NU[]={"0","1","2","3","4","5","6","7","8","9"};
        
        for (int p=0;p<longitud;p++){
            
            String letra =""+texto.charAt(p);
            BufferedImage img=null;
            
            for (int indx = 0;indx<LE.length;indx++) {      
                if(img==null) {
                    img = Selec(Letras, letra, LE[indx], indx);
                }
            } //Arreglado
            
            for (int indx = 0;indx<NU.length;indx++) {      
                if(img==null) {
                    img = Selec(Numeros, letra, NU[indx], indx);
                }
            }
            
            if (Ancho!=0) {
                g2.drawImage(img, X+vueltas, Y, Ancho, Alto, rootPane);
            }
            vueltas+=separacion;
        }
    } //Terminado
    
    public BufferedImage Selec(ArrayList<BufferedImage> Buff, String letra, String text, int indx){
        
        BufferedImage img=null;
        if (letra.equalsIgnoreCase(text)){
            img=Buff.get(indx);
                 
        }
        
        
        return img;
    } //Verificado
    
    
    public void CrearFigura(int Figura) {
        
        TotalFiguras++;
        
        setDificultad();
        // Linea Recta (forma de "I")
        if (Figura==0){
            FiguraPosixy(260, 40);
            FiguraPosixy(280, 40);
            FiguraPosixy(300, 40);
            FiguraPosixy(320, 40);
            CreaDatos("A", 2, Figura);
        }
        // Crea una "L" Azul
        if (Figura==1){
            FiguraPosixy(260, 20);
            FiguraPosixy(260, 40);
            FiguraPosixy(280, 40);
            FiguraPosixy(300, 40);
            CreaDatos("B", 2, Figura);
        }
        // Crea un cuadrado 
        if (Figura==2){
            FiguraPosixy(280, 40);
            FiguraPosixy(280, 20);
            FiguraPosixy(300, 40);
            FiguraPosixy(300, 20);
            CreaDatos("G", 2, Figura);
            
        }
        //Cuatro verde
        if (Figura==3){
            FiguraPosixy(260, 40);
            FiguraPosixy(280, 40);
            FiguraPosixy(280, 20);
            FiguraPosixy(300, 20);
            CreaDatos("C", 2, Figura);
        }
        // L naranja 
        if (Figura==4){
            FiguraPosixy(280, 40);
            FiguraPosixy(300, 40);
            FiguraPosixy(320, 40);
            FiguraPosixy(320, 20);
            CreaDatos("E", 2, Figura);
        }
        //Cuatro Rojo
        if (Figura==5){
            FiguraPosixy(280, 20);
            FiguraPosixy(300, 20);
            FiguraPosixy(300, 40);
            FiguraPosixy(320, 20);
            CreaDatos("F", 2, Figura);
        }
        //Figura T 
        if (Figura==6) {
            FiguraPosixy(260, 40);
            FiguraPosixy(280, 40);
            FiguraPosixy(280, 20);
            FiguraPosixy(300, 40);
            CreaDatos("D", 2, Figura);
        }
        
        
        
        
    } //Verificado
    
    public void CreaDatos(String tipo, int estado, int Figura){
        for (int x=0;x<4;x++) {
            Figura_Estado.add(estado);
            Figura_Tipo.add(tipo);
            Figura_2D.add(Figura2D.get(Figura));
        }
        Genera_Movimiento();
    } //Verificado
    
    public void Genera_Movimiento() {
        
        Thread moverFigura = new Thread(() -> {
            
            VelocidadMemoria = Velocidad;
            
            while(!Final_Carrera && !FindelJuego){
                
                Evaluar_Figuras("A");
                
                NuevoCiclo=false;
                try{Thread.sleep(Velocidad);} catch(InterruptedException e){}
                
                NuevoCiclo=true;
                Velocidad=VelocidadMemoria;
            }
            
            if (!FindelJuego) {
                
                Velocidad =VelocidadMemoria;
                Final_Carrera=false;
                CambiaEstado();
                EvaluarLineas();
                ReproAudio.Fx(2);
                CrearFigura(Figura_Ronda.get(TotalFiguras));
                BloqueaTeclado=false;
                IndiceFigura=IndiceFigura+4;
                BloqueaTecla_Rotar=false;
                BloqueaAbajo=false;
                
                Premio = Premio * Bonus;
                PuntosJuego = PuntosJuego + Premio;
                Bonus = -1;
                Premio = 0;                
            }
            
        });
        
        moverFigura.start();
        
    } //Verificado
        
    
    
    public void setDificultad(){
        
        for (int figuras=0;figuras<1000;figuras=figuras+40){
            if (TotalFiguras==figuras){
                Velocidad=Velocidad-10;
                Level_Map++;
            }
            
        }
        
    } //Verificado
    
    
    public void EventosAccionEscucha() {
        
        addKeyListener(new KeyAdapter() {
           @Override
           public void keyReleased(KeyEvent e) {
               
               int TeclaCode = e.getKeyCode();
               
               if (TeclaCode==32) {
                    if (Evaluar_Figuras("R") && !Pausar && !BloqueaTecla_Rotar) {
                        ReproAudio.Fx(4);
                        Rot_Fig.Rotar_Figuras();
                    }else{
                        ReproAudio.Fx(5);
                    }
                }
               
               
               // Arreglado 1/?
               if (TeclaCode==27){
                   Pausar=!Pausar;
               }
               
           }
           
           @Override
           public void keyPressed(KeyEvent e) {
               int TeclaCode = e.getKeyCode();
               if (TeclaCode == 39 && !BloqueaTeclado && !Pausar){
                   ReproAudio.Fx(3);
                   Desplazamiento_Lateral(increment, "D");
              // Verificado
           }
           //Cursor izq
           if (TeclaCode==37 && !BloqueaTeclado && !Pausar) {
            Desplazamiento_Lateral(decrement, "I");
        }
           // Cursor abajo
           if (TeclaCode==40 && !BloqueaAbajo && !Pausar) {
               Desplazamiento_Abajo(increment);
           
           }
        }   
            
    });        
        
        
} //Verificado
    
    public void Desplazamiento_Lateral(int accion, String lados){
        
        if (Evaluar_Figuras(lados)){
            int index=0;
            for (int Estado:Figura_Estado){
                if (Estado==2){
                    Figura_PosiX.set(index, getFx(index)+accion);
                }
                index++;
            }
        }
    } //Verificado
    
    public void Desplazamiento_Abajo(int accion){
        if (Evaluar_Figuras("B")){
            int index=0;
            for (int Estado:Figura_Estado){
                if(Estado==2){
                    Figura_PosiY.set(index, getFy(index)+accion);
                }
                index++;
            }
        }     
    } //Verificado
    
    public boolean Evaluar_Figuras(String Direccion){
     
        Rectangle Figuras_Estaticas=null;
        Rectangle N_F_abajo=null;
        Rectangle N_F_derecha=null;
        Rectangle N_F_izquierda=null;
        Rectangle N_F_CercaAbajo=null;
        boolean EsNull=true;
        int index=0;
        for (int Estado:Figura_Estado) {
            
            if (Estado==1) {
                Figuras_Estaticas = new Rectangle(getFx(index), getFy(index),20,20);
            }
        
            int Proxi_PosX;
            int Proxi_PosY;
            if (Rot_Fig.ProximaRotacion(getFtipo())!=null && Direccion.equals("R")){
                ArrayList<Rectangle> ProximaRotacion = Rot_Fig.ProximaRotacion(getFtipo());
                for (int ind=0;ind<4;ind++){
                    try{
                    Rectangle rec = ProximaRotacion.get(ind);
                    Proxi_PosX = (int) rec.getX()*20+getFx(ind+IndiceFigura);
                    Proxi_PosY = (int) rec.getY()*20+getFy(ind+IndiceFigura);
                    
                    Rectangle ProximaR = new Rectangle(Proxi_PosX, Proxi_PosY, 20,20);
                    
                    
                    if (ProximaR.intersects(new Rectangle(180, 0, 20, getHeight()))){
                        return false;
                    }
                    
                    if (ProximaR.intersects(new Rectangle(400, 0, 20, getHeight()))){
                        return false;
                    }
                    
                    if (ProximaR.intersects(new Rectangle(200, 660, 200, 60))){
                        return false;
                    }
                    
                    
                    if (Figuras_Estaticas!=null){
                        if (ProximaR.intersects(new Rectangle(Figuras_Estaticas))){
                            return false;
                        }
                    }
                            
                            
                }catch (NullPointerException e){System.out.println("Error "+Arrays.toString(e.getStackTrace()));} //Arreglado
            }
        }
            
        if (Estado==2) {
                EsNull=false;
                N_F_abajo = new Rectangle(getFx(index), getFy(index)+20, 20, 20);
                N_F_CercaAbajo = new Rectangle(getFx(index), getFy(index)+40, 20, 20);
                N_F_derecha = new Rectangle(getFx(index)+20, getFy(index), 20, 20);
                N_F_izquierda = new Rectangle(getFx(index)-20, getFy(index), 20, 20);
            }
            
            
            for (int x=0;x<getFsice();x++) {
                Rectangle ParteInferior = new Rectangle(200, 660, 200, 60);
                Rectangle ParteIzquierda = new Rectangle(180, 0, 30, getHeight());
                Rectangle ParteDerecha = new Rectangle(380, 0, 20, getHeight());
                
                if (!EsNull){
                    if (N_F_abajo.intersects(ParteInferior)){
                        Velocidad=VelocidadCercania;
                    }
                    if (N_F_abajo.intersects(ParteIzquierda) && Direccion.equals("I") && !Final_Carrera){
                        return false;
                    }
                    
                    if (N_F_abajo.intersects(ParteDerecha) && Direccion.equals("D") && !Final_Carrera){
                        return false;
                    }
                    
                    if (N_F_abajo.intersects(ParteInferior) && Direccion.equals("B") && !Final_Carrera){
                        return false;
                    }
                    
                    if (N_F_abajo.intersects(ParteInferior) && !Final_Carrera && !NuevoCiclo){
                        Final_Carrera=true;
                        BloqueaAbajo=true;
                        BloqueaTecla_Rotar=true;
                        BloqueaTeclado=true;
                        return false;
                    } //Arreglado
                    
                    
                    
                }
                
                
                if (getEstado(x)==1){
                    Rectangle Figura = new Rectangle(getFx(x), getFy(x), 20 ,20);
                    if (!EsNull){
                        if (N_F_CercaAbajo.intersects(Figura)){
                            Velocidad=VelocidadCercania;
                        }
                        
                        if (N_F_izquierda.intersects(Figura) && Direccion.equals ("I") && !Final_Carrera){
                            return false;
                        }
                        
                        if (N_F_derecha.intersects(Figura) && Direccion.equals("D") && !Final_Carrera){
                            return false;
                        }
                        
                        if (N_F_abajo.intersects(Figura) && Direccion.equals("B") && !Final_Carrera){
                            return false;
                        }
                        
                        if (N_F_abajo.intersects(Figura) && !Final_Carrera && NuevoCiclo){
                            Final_Carrera=true;
                            BloqueaAbajo=true;
                            BloqueaTecla_Rotar=true;
                            BloqueaTeclado=true;
                            return false;
                        }
                    }
                }
                
            }
            index++;
        }
        
        if (!Pausar && !Final_Carrera && Direccion.equals("A")){
            int indx=0;
            for (int Status:Figura_Estado){
                if (Status==2){
                    Figura_PosiY.set(indx, getFy(indx)+20);
                }
                indx++;
            }
        }
        return true;
    } //Verificado
    
    public void EvaluarLineas(){
        Bonus++;
        for (int Posicion_Y=640;Posicion_Y>60;Posicion_Y-=20){
            int indx=0;
            int Cantidad=0;
            ArrayList<Integer> FiguraIndice = new ArrayList<>();
            try {
                for (int Estado:Figura_Estado) {
                    
                    if (Estado==1) {
                        int Ultima_PosicionY = Posicion_Y;
                        if (Posicion_Y==getFy(indx)){
                        
                            Cantidad++;
                            FiguraIndice.add(indx);
                            
                    }
                    if (getFy(indx)==40 && FindelJuego){
                        FindelJuego=true;
                        ReproAudio.Fx(0);
                        ReproAudio.Fx(6);
                    }
                    
                    if (Cantidad==10) {
                        Limpiar_Linea(FiguraIndice, Ultima_PosicionY);
                        Cantidad=0;
                        FiguraIndice.clear();
                        EvaluarLineas();
                    }
                }
                indx++; //Arreglado
            }
                
            
            
            } catch (ConcurrentModificationException e) {
                System.out.println("Error"+e.getStackTrace());
        }
    }
        
    int longitud = Figura_Estado.size();
    
    for (int x=0;x<longitud;x++){
        try{
            
            int Estado = Figura_Estado.get(x);
            if (Estado==0){
                Figura_2D.remove(x);
                Figura_Estado.remove(x);
                Figura_PosiX.remove(x);
                Figura_PosiY.remove(x);
                Figura_Tipo.remove(x);
                IndiceFigura = IndiceFigura-1;
            }
            
        } catch(IndexOutOfBoundsException e){}
    }
        
} //Verificado
    
    public void Limpiar_Linea(ArrayList<Integer> figura, int Ultima_PosicionY) {
        
        for (int fig:figura){
            Figura_Estado.set(fig, 0);
        }
        int indx=0;
        for (int Est:Figura_Estado){
            if(Est==1 && Ultima_PosicionY>=getFy(indx)){
                Figura_PosiY.set(indx, getFy(indx)+20);
            }
            indx++;
        }
        ContadorLineas++;
        ReproAudio.Fx(1);
        Premio=Premio+100;     
} //Verificado
    
    public void FiguraPosixy(int PosiX, int PosiY){
        
        Figura_PosiX.add(PosiX);
        Figura_PosiY.add(PosiY);
        
    } //Verificado
    
    public int GenerarNumero(){
        int Figura = (int) (Math.random()*7);
        // Crea una figura al azar
        return Figura;
    } // Arreglado
    
    public void CambiaEstado(){
        for (int x=0;x<getFsice();x++){
            if(getEstado(x)==2){
                Figura_Estado.set(x, 1);
            }
        }
    } // Arreglado
    
    public String getFtipo(){
        
        String tipo="";
        int index=0;
        for(String T:Figura_Tipo){
            if (Figura_Estado.get(index)==2){
                tipo=T;
            }
            index++;
        }
        
        return tipo;
    } // Arreglado
    
    public int getFsice(){
        return Figura_2D.size();
    } //Arreglado
    
    public int getFx(int indx) {
        
        return Figura_PosiX.get(indx);
    } //Arreglado
    
    public int getFy(int indx) {
        
        return Figura_PosiY.get(indx);
    } //Arreglado
    
    public int getEstado(int indx){
        
        return Figura_Estado.get(indx);
    } // Arreglado
        
    public void CrearBucle(){
        Thread hilo = new Thread(() -> {
            
            while (!FindelJuego){
                
                try {Thread.sleep(33);} catch (InterruptedException ex) {}
                repaint();
                
                
            }
        });
        
        hilo.start();
    } // Arreglado
    
    
}
    

