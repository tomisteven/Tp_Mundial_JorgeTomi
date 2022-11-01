public class AlbumTradicional extends Album {
    
    protected boolean premioEntregado;
    protected Integer numero_Sorteo;
    protected String _premioInstantaneo;
     //----------------------------------------------- Constructors --------------------------------------------------------- 

    AlbumTradicional(String premioInstantaneo)  {

        super("Tradicional");
        super.setPremio("Pelota");

        _premioInstantaneo = premioInstantaneo;
    }

	public String aplicarSorteoInstantaneo() {
		if(premioEntregado) {
			throw new RuntimeException("Sorteo ya realizado");
		}else {
			premioEntregado = true;
			return _premioInstantaneo;

		}

	}

//    AlbumTradicional(String tipo) {
//
//        super(tipo);
//    }
}
