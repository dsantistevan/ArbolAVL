/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package colores;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.paint.Color;

/**
 *
 * @author David Santistevan
 */
public abstract class GamaColores {
    private final Color borde;
    private final Color relleno;
    
    public GamaColores(Color b,Color r){
        borde=b;
        relleno=r;
    }

    public Color getBorde() {
        return borde;
    }

    public Color getRelleno() {
        return relleno;
    }
    
    public static List<GamaColores> getGamas(){
        List<GamaColores> lista=new ArrayList<>();
        lista.add(new GamaAzul());
        lista.add(new GamaRoja());
        lista.add(new GamaMorada());
        lista.add(new GamaVerde());
        lista.add(new GamaNaranja());
        lista.add(new GamaAmarilla());
        return lista;
    }
}
