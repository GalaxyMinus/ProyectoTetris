package tetris;

import java.awt.Rectangle;
import java.util.ArrayList;

public class Rotar_Figuras {
    
    private ArrayList<Integer> Figura_PosiX = new ArrayList<>();
    private ArrayList<Integer> Figura_PosiY = new ArrayList<>();
    private ArrayList<String> Figura_Tipo = new ArrayList<>();
    private ArrayList<Integer> Figura_Estado = new ArrayList<>();
    
    private ArrayList<Rectangle> Almacenar = new ArrayList<>();
    private TetrisJuego Juego_T;
    
    
    
    public Rotar_Figuras(TetrisJuego Juego_T, ArrayList<Integer> Figura_PosiX, ArrayList<Integer> Figura_PosiY,
            ArrayList<String> Figura_Tipo, ArrayList<Integer> Figura_Estado) {
        
        
        this.Juego_T = Juego_T;
        this.Figura_PosiX = Figura_PosiX;
        this.Figura_PosiY = Figura_PosiY;
        this.Figura_Tipo = Figura_Tipo;
        this.Figura_Estado = Figura_Estado;
    } //Arreglado
    
    public void Rotar_Figuras() {
        
        int elem=0;
        int indx=0;
        for (int Estado: Figura_Estado) {
            
            if (Estado==2){
                
                if (get_Compare("A", indx)){
                    
                    if(elem==0){Mueve(1, 1, indx);}
                    if(elem==2){Mueve(-1, -1, indx);}
                    if(elem==3){Mueve(-2, -2, indx);}
                    Figura_Tipo.set(indx, "A1");
                }else if (get_Compare("A1", indx)){
                    
                    if(elem==0){Mueve(-1, -1, indx);}
                    if(elem==2){Mueve(1, 1, indx);}
                    if(elem==3){Mueve(2, 2, indx);}
                    Figura_Tipo.set(indx, "A");
                    
                }
                
                // - - - - - - - - - - - - - - - - - - - - -
                
                if (get_Compare("B", indx)){
                    
                    if (elem==0){Mueve(0, 2, indx);}
                    if (elem==3){Mueve(-2, 2, indx);}
                    Figura_Tipo.set(indx, "B1");
                }else if (get_Compare("B1", indx)){
                    
                    if (elem==2){Mueve(-2, 0, indx);}
                    if (elem==3){Mueve(-2, -2, indx);}
                    Figura_Tipo.set(indx, "B2");
                }else if (get_Compare("B2", indx)){
                    if (elem==0){Mueve(0, -2, indx);}
                    if (elem==3){Mueve(2, -2, indx);}
                    Figura_Tipo.set(indx, "B3");
                    
                }else if(get_Compare("B3", indx)) {
                    if (elem==2){Mueve(2, 0, indx);}
                    if (elem==3){Mueve(2, 2, indx);}
                    Figura_Tipo.set(indx, "B");
                }
                
                // - - - - - - - - - - - - - - - - - - - - -
                
                if (get_Compare("C", indx)){
                
                    if(elem==0){Mueve(2, 0, indx);}
                    if(elem==3){Mueve(0, 2, indx);}
                    Figura_Tipo.set(indx, "C1");
                }else if (get_Compare("C1", indx)) {
                    
                    if (elem==0){Mueve(-2, 0, indx);}
                    if (elem==3){Mueve(0, -2, indx);}
                    Figura_Tipo.set(indx, "C");
            }
            
                // - - - - - - - - - - - - - - - - - - - - -
                
                if (get_Compare("D", indx)){
                    if (elem==0){Mueve(1, 1, indx);}
                    Figura_Tipo.set(indx, "D1");
                } else if (get_Compare("D1", indx)){
                    if (elem==2){Mueve(-1, 1, indx);}
                    Figura_Tipo.set(indx, "D2");
                } else if (get_Compare("D2", indx)){
                    if (elem==3){Mueve(-1, -1, indx);}
                    Figura_Tipo.set(indx, "D3");
                } else if (get_Compare("D3", indx)){
                    if (elem==0){Mueve(-1, -1, indx);}
                    if (elem==2){Mueve(1, -1, indx);}
                    if (elem==3){Mueve(1, 1, indx);}
                    Figura_Tipo.set(indx, "D");
                
                }
                // - - - - - - - - - - - - - - - - - - - - -
                
                if (get_Compare("E", indx)){
                    if (elem==0){Mueve(2, -2, indx);}
                    if (elem==1){Mueve(2, 0, indx);}
                    Figura_Tipo.set(indx, "E1");
                } else if (get_Compare("E1", indx)){
                    if (elem==0){Mueve(2, 1, indx);}
                    if (elem==1){Mueve(0, -1, indx);}
                    Figura_Tipo.set(indx, "E2");
                } else if (get_Compare("E2", indx)) {
                    if (elem==0){Mueve(-1, 3, indx);}
                    if (elem==1){Mueve(0, 1, indx);}
                    if (elem==3){Mueve(1, 2, indx);}
                    Figura_Tipo.set(indx, "E3");
                } else if (get_Compare("E3", indx)) {
                    if (elem==0){Mueve(-3, -2, indx);}
                    if (elem==1){Mueve(-2, 0, indx);}
                    if (elem==3){Mueve(-1, -2, indx);}
                    Figura_Tipo.set(indx, "E");
                }
                
                // - - - - - - - - - - - - - - - - - - - - -
                
                if (get_Compare("F", indx)){
                    if (elem==0){Mueve(0, 1, indx);}
                    if (elem==3){Mueve(-2, 1, indx);}
                    
                    Figura_Tipo.set(indx, "F1");
                } else if (get_Compare("F1", indx)) {
                    if (elem==0){Mueve(0, -1, indx);}
                    if (elem==3){Mueve(2, -1, indx);}
                    Figura_Tipo.set(indx, "F");
                }
                elem++;
            }
            
            indx++;
        }
                
                
                
    } // completado (?)
    
    public ArrayList<Rectangle> ProximaRotacion(String Tipo) {
        
        Almacenar = new ArrayList<>();
        
        if (Tipo.equals("A")) {
            Intro(1, -1);
            Intro(0, 0);
            Intro(-1, -2);
            Intro(-2, -3);
            return Almacenar; // check
        }
        
        if (Tipo.equals("A1")) {
            Intro(-1, 0);
            Intro(0, 1);
            Intro(1, 2);
            Intro(2, 3);
            return Almacenar; // check
        }
        
        if (Tipo.equals("B")) {
            Intro(0, 2);
            Intro(0, 0);
            Intro(0, 0);
            Intro(-2, 2);
            return Almacenar; // check
        } 
                
        if (Tipo.equals("B1")) {
            Intro(0, 0);
            Intro(0, 0);
            Intro(-2, 0);
            Intro(-2, -2);
            return Almacenar; // check
        }
                        
        if (Tipo.equals("B2")) {
            Intro(0, -2);
            Intro(0, 0);
            Intro(0, 0);
            Intro(2, -2);
            return Almacenar; // check
        }
        
        if (Tipo.equals("B3")) {
            Intro(0, 0);
            Intro(0, 0);
            Intro(2, 0);
            Intro(2, 2);
            return Almacenar;
        }
        
        if (Tipo.equals("C")) {
            Intro(2, 0);
            Intro(0, 0);
            Intro(0, 0);
            Intro(0, 2);
            return Almacenar;
        }
        
        if (Tipo.equals("C1")) {
            Intro(-2, 0);
            Intro(0, 0);
            Intro(0, 0);
            Intro(0, -2);
            return Almacenar;
        }
        
        if (Tipo.equals("D")) {
            Intro(1, 1);
            Intro(0, 0);
            Intro(0, 0);
            Intro(0, 2);
            return Almacenar;
        }
        
        if (Tipo.equals("D1")) {
            Intro(0, 0);
            Intro(0, 0);
            Intro(-1, 1);
            Intro(0, 0);
            return Almacenar;
        }
        
        if (Tipo.equals("D2")) {
            Intro(0, 0);
            Intro(0, 0);
            Intro(0, 0);
            Intro(-1, -1);
            return Almacenar;
        } // check
        
        if (Tipo.equals("D3")) {
            Intro(-1, -1);
            Intro(0, 0);
            Intro(1, -1);
            Intro(1, 1);
            return Almacenar;
        }
        
        if (Tipo.equals("E")) {
            Intro(2, -2);
            Intro(2, 0);
            Intro(0, 0);
            Intro(0, 0);
            return Almacenar;
        }
        
        if (Tipo.equals("E1")) {
            Intro(2, 1);
            Intro(0, -1);
            Intro(0, 0);
            Intro(0, 0);
            return Almacenar;
        }
        
        if (Tipo.equals("E2")) {
            Intro(-1, 3);
            Intro(0, 1);
            Intro(0, 0);
            Intro(1, 2);
            return Almacenar;
        }
        
        if (Tipo.equals("E3")) {
            Intro(-3, -2);
            Intro(-2, 0);
            Intro(0, 0);
            Intro(-1, -2);
            return Almacenar;
        } // check
        
        if (Tipo.equals("F")) {
            Intro(0, 1);
            Intro(0, 0);
            Intro(0, 0);
            Intro(-2, -1);
            return Almacenar;
        }
        
        if (Tipo.equals("F1")) {
            Intro(0, -1);
            Intro(0, 0);
            Intro(0, 0);
            Intro(2, -1);
            return Almacenar;
        }
        
        
        return null;
    } // completado y verificado
    
    public void Intro(int x, int y) {
        Almacenar.add(new Rectangle(x, y, 20, 20));
    } //Arreglado
    
    public boolean get_Compare(String T, int indx) {
        return Figura_Tipo.get(indx).equals(T);
    } //Arreglado
    
    public void Mueve(int x, int y, int indx) {
        
        Figura_PosiX.set(indx, Juego_T.getFx(indx) + (x*20));
        
        Figura_PosiY.set(indx, Juego_T.getFy(indx) + (y*20));
    } //Arreglado
    
}
