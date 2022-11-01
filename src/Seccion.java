import java.util.ArrayList;
import java.util.List;

public class Seccion {
    
    String pais;
    private Integer numero_Seccion;
   
    private boolean seccion_Completa;
    private ArrayList<Figurita> figusPegadas;
    

    public Seccion(String pais, Integer numero_Seccion) {
        
        this(pais);
        this.numero_Seccion = numero_Seccion;
        figusPegadas = new ArrayList<>();
        seccion_Completa = false;
    }

    Seccion(String pais){

        this.pais = pais;
    }

    protected Figurita pegarFiguritaTOP10(Figurita other){

         //Usamos un booleano para detectar si la figurita ya fue pegada
         boolean ret = true;
         for (Figurita figurita : figusPegadas) {
             
             ret = ret && !figurita.equals(other); // mientras false la figurita no esta pegada
             
             if (!ret) { 
             return null; // Devolvemos null si la figurita a pegar ya fue pegada
             }    
         }
 
         if (figusPegadas.size() != 2) {
             
             figusPegadas.add(other);
         }
 
         if (figusPegadas.size() == 2) {
             seccion_Completa = true;
         }
 
 
         return other; // Si fue pegada con exito devolvemos las figurita


    }

    @Override
    public String toString() {
        
        StringBuilder st = new StringBuilder();

       for (Figurita figurita : figusPegadas) {
        
            st.append("$" + figurita.numeroJugador + "\n");
       }
       return st.toString();
    }

    protected Figurita pegarFiguritas(Figurita other){

        for (Figurita figurita : figusPegadas) {
            
            // Si son iguales devuelvo null inmediatamente
            if (figurita.equals(other)) { 
                return null; // la figurita a pegar ya fue pegada
            }    
        }

        if (figusPegadas.size() != 11) {
            
            figusPegadas.add(other);
        }

        if (figusPegadas.size() == 11) {
            seccion_Completa = true;
        }


        return other; // Si fue pegada con exito devolvemos las figurita
    }

    public boolean get_Seccion_Completa() {
        return seccion_Completa;
    }

    public Integer getNumero_Seccion() {
        return numero_Seccion;
    }

    public ArrayList<Figurita> getFigusPegadas() {
        return figusPegadas;
    }

    
}
