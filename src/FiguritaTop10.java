public class FiguritaTop10 extends Figurita{
    
    private String anio;
    private boolean balonOro; // si true es oro sino plata

    FiguritaTop10(String nombre, int valorbase, String pais, Integer num, String año){
        super(nombre, valorbase, pais, num); // Invocando al constructor del padre 
        anio = año;
        tipo_balon(); // Por diseño si es jugador 1 entonces gano balon oro, si es 2 el de plata
        
        //Calculos de si gano el balon oro o plata
        if (balonOro) { // Gano el balon de oro
            double porcentaje_valor_base = super.valor_base * 1.20;
            super.valor_base += (int) porcentaje_valor_base;
        }
        else {
            double porcentaje_valor_base = super.valor_base * 1.10;
            super.valor_base += (int) porcentaje_valor_base;
        }
        super.tipoFigurita = true; // Si es true es por que es top 10
        super.codigo_ID = codigo_ID * super.valor_base;
    }

    private void tipo_balon(){
       if (super.numeroJugador == 1) balonOro = true;      
    }

    @Override
    public String toString() {   
        //usamos super del toString de Figurita
        StringBuilder st = new StringBuilder();
        String balon;
        if(balonOro) balon = "oro"; else balon = "plata";
        
        st.append(super.toString());
        st.append("año: " + anio + "\n");
        st.append("balon ganado: " + balon + "\n");
        return st.toString();
    }
}
