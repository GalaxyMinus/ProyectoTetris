package tetris;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

public class Imagenes_Juego { // Arreglado 11-27
    
    public void CargarFiguras (ArrayList<BufferedImage> ArrayImg) {
        ObtenerArchivos(ArrayImg, "Graficos/Figuras/", "png");
    }
    public void CargarListaFiguras (ArrayList<BufferedImage> ArrayImg) {
        ObtenerArchivos(ArrayImg, "Graficos/ListaFiguras/", "png");
    }
    public void CargarLetras (ArrayList<BufferedImage> ArrayImg) {
        ObtenerArchivos(ArrayImg, "Graficos/LetrasGra/", "png");
    }
    public void CargarNumeros (ArrayList<BufferedImage> ArrayImg) {
        ObtenerArchivos(ArrayImg, "Graficos/Numeros/", "png");
    }
    public void CargarFondos (ArrayList<BufferedImage> ArrayImg) {
        ObtenerArchivos(ArrayImg, "Graficos/Fondos/", "png");
    }
    
    public void ObtenerArchivos(ArrayList<BufferedImage> ArrayImg, String ruta, String extension) {
        
        File archivos = new File(ruta).getAbsoluteFile(); //.
        
        String lista_archivos[] = archivos.list(); //
        
        for (String arch:lista_archivos) { //
            int longi = arch.lastIndexOf('.'); //
            int long1 = arch.length(); //
            String ob_Ext = arch.substring(longi+1, long1); //
            
            if (ob_Ext.equals(extension)) { //
                
                try {
                    String rutt = archivos.getAbsoluteFile()+"/"+arch;
                    
                    BufferedImage Img = ImageIO.read(new File(rutt));
                    ArrayImg.add(Img);
                } catch (IOException ex) {
                    Logger.getLogger(Imagenes_Juego.class.getName()).log(Level.SEVERE, null, ex);
                
                }
                
            }
        }
        
    } //Arreglado
    
}
