class Figurita{

    
    private String _nombre;
    private String _pais;
    protected Integer numeroJugador; //El numero de este jugador
  

    private static Integer valor_Codigo = 0;
    protected Integer codigo_ID;
   
    protected boolean tipoFigurita; //false si es tradicional y true si es top10
    protected Integer valor_base; // El valor base de esta figurita
    

    



    public Figurita(String nombre, int valorbase, String pais, Integer num){

        _nombre = nombre;
        _pais = pais;
        valor_base = valorbase;
        valor_Codigo++;
        numeroJugador = num;
        tipoFigurita = false;
        codigo_ID = valor_base * numeroJugador; // valor base * numero jugador es el ID
        
    }

    


    @Override
    public boolean equals(Object obj) {
        
        if (obj instanceof Figurita) {
            
            Figurita other = (Figurita) obj;

                //IREP: si coinciden en pais, no puede suceder que dos del mismo pais posean el mismo valor 
                // base ya que el numero de jugador + valor del pais siempre dara distinto
                //Si Coinciden en pais y valor base son iguales
                return (codigo_ID.compareTo(other.codigo_ID) == 0  && _pais.equals(other._pais) && numeroJugador.equals(other.numeroJugador))? true: false;

        }
        return false;
    }    


    @Override
    public String toString() {
        
        StringBuilder st = new StringBuilder();

        st.append("Nombre: " + _nombre + "\n");
        st.append("Pais: " + _pais + "\n");
        st.append("Valor_Base: " + valor_base + "\n");
        st.append("CodigoID: " + codigo_ID + "\n");

        return st.toString();

    }


    public String get_pais() {
        return _pais;
    }

    public Integer getValor_base() {
        return valor_base;
    }

    public String get_nombre() {
        return _nombre;
    }

    public Integer getNumeroJugador() {
        return numeroJugador;
    }

    public Integer getCodigo_ID() {
        return codigo_ID;
    }

    protected void setID(Integer other){

        this.codigo_ID = other;
    }

}