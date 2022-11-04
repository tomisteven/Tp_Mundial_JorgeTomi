import java.util.ArrayList;

public class Seccion {
    
    String _pais;
    private Integer _id_seccion;
    private boolean seccion_completa;
    private ArrayList<Figurita> figusPegadas;
    private Fabrica fb;
    

    public Seccion(String pais, Integer id_Seccion) {
        
        _pais = pais;
        _id_seccion = id_Seccion;
        figusPegadas = new ArrayList<>();
        seccion_completa = false;
        fb = new Fabrica();
    }

    public Seccion(String pais){
        _pais = pais;
    }

    protected Figurita pegarFiguritaTOP10(Figurita other){
    	if (seccion_completa) return null; // ya no hay nada para pegar aquí;
    	
         //Usamos un acumulador booleano para ver si la figurita ya fue pegada
         boolean ret = false;
         for (Figurita figurita : figusPegadas) {
        	 ret = ret || figurita.equals(other); // mantiene false si la figurita no esta pegada
             }
         if (ret) return null; // Devolvemos null si la figurita a pegar ya fue pegada
         
         figusPegadas.add(other);
         if (figusPegadas.size() == 2) seccion_completa = true;

         return other; // devolvemos la figurita pegada
    }

    @Override
    public String toString() {
        
        StringBuilder st = new StringBuilder();

       for (Figurita figurita : figusPegadas) {
        
            st.append("$" + figurita.numeroJugador + "\n");
       }
       return st.toString();
    }

    protected Figurita pegarFiguritas(Figurita figu){

        for (Figurita figurita : figusPegadas) {
            
            // Si son iguales devuelvo null inmediatamente
            if (figurita.equals(figu)) { 
                return null; // la figurita a pegar ya fue pegada
            }    
        }

        if (figusPegadas.size() != fb.getLugaresPorPais()) {
            
            figusPegadas.add(figu);
        }

        if (figusPegadas.size() == fb.getLugaresPorPais()) {
            seccion_completa = true;
        }


        return figu; // Si fue pegada con exito devolvemos las figurita
    }

    public boolean get_Seccion_Completa() {
        return seccion_completa;
    }

    public Integer getNumero_Seccion() {
        return _id_seccion;
    }

    public ArrayList<Figurita> getFigusPegadas() {
        return figusPegadas;
    }

    
}
