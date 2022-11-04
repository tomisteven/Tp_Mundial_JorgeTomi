import java.util.ArrayList;
import java.util.List;

public class Participante {

    private final String _nombre;
    private final Integer _dni;

    private boolean _premioRecibido; // Si tiene el premio es true
    private Album _album;

    protected Fabrica _fb;
    private List<Figurita> _figusCompradas;
    private List<Figurita> _figusRepetidas;

    private String _tipoAlbum;

  

    // Constructor de Participante
    Participante(String nombre, Integer dni, String tipoAlbum) {//throws RuntimeException {

        _fb = new Fabrica(); // Objeto de uso general

        _figusCompradas = new ArrayList<>();
        _figusRepetidas = new ArrayList<>();

        _nombre = nombre;
        _dni = dni;

        _album = generarAlbum(tipoAlbum);
        _tipoAlbum = tipoAlbum;
    }

    // ---------------------------- Chequeo de metodos -----------------

    private boolean estaComprada(Figurita figu) {	
    	return _figusCompradas.contains(figu) ? true : false;
    }

    // ------------------- Metodos de uso general -----------------------

    @Override
    public boolean equals(Object obj) {

        if (obj instanceof Participante) {

            // Si tienen el mismo dni son la misma persona
            Participante participante = (Participante) obj;
            return _dni == participante._dni;

        }
        return false;
    }

    // Muestra la informacion completa de este participante
    @Override
    public String toString() {
        StringBuilder st = new StringBuilder();

        st.append("Nombre: " + _nombre + "\n");
        st.append("Tipo Album: " + _tipoAlbum + "\n");
        st.append("Completo El album: " + _premioRecibido + "\n");
        st.append("Adquiridas: " + "\n");

        for (Figurita figurita : _figusCompradas) {
            st.append(figurita.toString() + "\n");

        }
        st.append("Repetidas: " + "\n");
        for (Figurita figurita : _figusRepetidas) {
            st.append(figurita.toString() + "\n");

        }

        return st.toString();

    }

    Album generarAlbum(String tipo) {
    	switch (tipo) {
            case "Web": return _fb.crearAlbumWeb();
            case "Extendido": return _fb.crearAlbumExtendido();
            default: return _fb.crearAlbumTradicional();
        }
    }

    void agregarFiguritas() {
        List<Figurita> figus = _fb.generarSobre(4);

        for (int i=0; i<4; i++) {
            if (estaComprada(figus.get(i)) ? _figusRepetidas.add(figus.get(i))
            	: _figusCompradas.add(figus.get(i)));        
        }
    }

    void agregarFiguritasTOP10() {

        List<Figurita> figus = _fb.generarSobreTop10(4);
        for (int i = 0; i < 4; i++) {

            if (estaComprada(figus.get(i))) { // Devuelve true si la figurita ya esta adquirida

                _figusRepetidas.add(figus.get(i));
            }

            else {
                _figusCompradas.add(figus.get(i));
            }
        }
    }

    void AgregarFiguritaIndividual(Figurita figu){
        _figusCompradas.add(figu);
    }

    ArrayList<Figurita> pegarFiguritas() {

        //Por polimorfismo, si el album es extendido se invoca al pegar figuritas de extendido
        ArrayList<Figurita> figus = _album.pegarFiguritas(_figusCompradas);
        _figusCompradas.clear();
        return figus;
    }

    boolean poseeFigurita(Figurita fig){

        for (Figurita figurita : _figusCompradas) {
            
            if (figurita.equals(fig)) {
                return true;
            }
        }
        return false;

    }

    // ------------------ Getters and Setters -------------------

    Integer getDni() {
        return _dni;
    }

    Integer get_Codigo_Unico_Album() {
        return _album._codigoAlbum;
    }

    String getNombre() {
        return _nombre;
    }

    Album getAlbum() {
        return _album;
    }

    boolean premioRecibido() {
        return _album.canjeo_Premio_Album;
    }


    public String get_tipoAlbum() {
        return _tipoAlbum;
    }
    
    public List<Figurita> getFigusCompradas() {
    	return _figusCompradas;
    }

    public List<Figurita> get_figuritasRepetidas() {
        return _figusRepetidas;
    }

    Figurita obtenerFiguritaID(Integer codigo){


        for (Figurita figurita : _figusCompradas) {
            
            if (codigo.compareTo(figurita.getCodigo_ID()) == 0) {
                return figurita;
            }

        }
        System.out.println("figus compradas: " + _figusCompradas.size());
        throw new RuntimeException("Figurita no encontrada!");
    }

    //Esta funcion se encarga de eliminar una figurita de la lista de adquiridas o repetidas
    void borrarFiguritaID(Integer CodigoFigurita){

       for (int i = 0; i < _figusCompradas.size(); i++) {
        
        if (_figusCompradas.get(i).getCodigo_ID() == CodigoFigurita) {
            
            _figusCompradas.remove(i);
            }
       }
       for (int i = 0; i < _figusRepetidas.size(); i++) {
        
        if (_figusRepetidas.get(i).getCodigo_ID() == CodigoFigurita) {
            
            _figusRepetidas.remove(i);
            }
       }
    }

    //Devuelve un listado de todas las secciones
    String secciones_Completas(){
        return _album.secciones_Completas();       
    }

	public void reclamarPremio() {
		_premioRecibido = _album.canjeo_Premio_Album;		
	}
}
