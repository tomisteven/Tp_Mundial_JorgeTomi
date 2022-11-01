public class FiguritaTop10 extends Figurita{
    
    private String anio;
    private boolean balonGanado; // si true es oro sino plata
   


    FiguritaTop10(String nombre, int valorbase, String pais, Integer num, String año){

        super(nombre, valorbase, pais, num); // Invocando al constructor del padre
       
        anio = año;
        balonganado(); // Por IREP si es jugador 1 entonces gano balon oro, si es 2 el de plata
        
        //Calculos de si gano el balon oro o plata
        if (balonGanado) { // Gano el balon de oro
            double porcentaje_valor_base = super.valor_base * 1.20;
            super.valor_base += (int) porcentaje_valor_base;
        }
        else{

            double porcentaje_valor_base = super.valor_base * 1.10;
            super.valor_base += (int) porcentaje_valor_base;

        }

        super.tipoFigurita = true; // Si es true es por que es top 10
        super.codigo_ID = codigo_ID * super.valor_base;


    }

    private void balonganado(){

       if (super.numeroJugador == 1) {
            balonGanado = true;
       }
      
    }

    @Override
    public String toString() {
        
        //Implementacion que usa super del to string de Figurita
        StringBuilder st = new StringBuilder();

        st.append(super.toString());
        st.append("año: " + anio + "\n");
        if (balonGanado) {
            st.append("balon ganado: " + "Oro" + "\n");
        }
        else{
            st.append("balon ganado: " + "Plata" + "\n");
        }
        
        
        return st.toString();
    }

}
