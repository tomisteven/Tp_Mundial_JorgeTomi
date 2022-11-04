public class AlbumTradicional extends Album {
    
    protected boolean _premio_inst_entregado; // true si ya se le sorteó
//    protected Integer _numero_Sorteo;
    protected String _premioInstantaneo;
     //----------------------------------------------- Constructors --------------------------------------------------------- 

    AlbumTradicional(String premioInstantaneo)  {

        super("Tradicional");
        super.setPremio("Pelota");
        _premioInstantaneo = premioInstantaneo;
        _premio_inst_entregado = false;
    }

	public String sortear() {
		if(_premio_inst_entregado) throw new RuntimeException("Sorteo ya realizado");
		_premio_inst_entregado = true;
		return _premioInstantaneo;
	}
}
