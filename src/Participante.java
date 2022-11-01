import java.util.ArrayList;
import java.util.List;

public class Participante {

    private final String _nombre;
    private final Integer _dni;

    private boolean _premioRecibido; // Si tiene el premio es true
    private Album _album;

    protected Fabrica _fb;
    private List<Figurita> _figuritasAdquiridas;
    private List<Figurita> _figuritasRepetidas;

    private String _tipoAlbum;

  

    // Constructor de Participante
    Participante(String nombre, Integer dni, String tipoAlbum) throws RuntimeException {

        if (!checkDni(dni)) { // El dni es invalido lanzo exception
            throw new RuntimeException("El dni es invalido");
        }

        if (!check_Tipo_Album(tipoAlbum)) { // Chequea si el tipo de album es valido
            throw new RuntimeException("El tipo de album no es valido.");
        }

        _fb = new Fabrica(); // Objeto de uso general

        _figuritasAdquiridas = new ArrayList<>();
        _figuritasRepetidas = new ArrayList<>();

        _nombre = nombre;
        _dni = dni;

        _album = establecerAlbum(tipoAlbum);
        _tipoAlbum = tipoAlbum;

    }

    // ------------------------------------- Chequeo de metodos
    // ---------------------------------------------------------
    private boolean checkDni(Integer dni) {

        if (String.valueOf(dni) != "") {
            return true;
        }
        return false;
    }

    private boolean check_Tipo_Album(String type) {

        return (type.equals("Web") || type.equals("Extendido") ||
                type.equals("Tradicional")) ? true : false;

    }

    private boolean detectarFigurita(Figurita figu) {

        for (int i = 0; i < _figuritasAdquiridas.size(); i++) {
        	
            if (_figuritasAdquiridas.get(i).equals(figu)) {
                return true;
            }
        }
        return false;

    }

    // ------------------------------------- Metodos de uso general
    // --------------------------------------------------------

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

        for (Figurita figurita : _figuritasAdquiridas) {

            st.append(figurita.toString() + "\n");

        }
        st.append("Repetidas: " + "\n");

        for (Figurita figurita : _figuritasRepetidas) {

            st.append(figurita.toString() + "\n");

        }

        return st.toString();

    }

    Album establecerAlbum(String tipo) {

        switch (tipo) {

            case "Web":
                return _fb.crearAlbumWeb();
            case "Extendido":
                return _fb.crearAlbumExtendido();
            default:
                return _fb.crearAlbumTradicional();

        }

    }

    void agregarFiguritas() {

        List<Figurita> figus = _fb.generarSobre(4);

        for (int i = 0; i < 4; i++) {

            if (detectarFigurita(figus.get(i))) { // Devuelve true si la figurita ya esta adquirida

                _figuritasRepetidas.add(figus.get(i));
            }

            else {
                _figuritasAdquiridas.add(figus.get(i));
            }

        }

    }

    void agregarFiguritasTOP10() {

        List<Figurita> figus = _fb.generarSobreTop10(4);
        for (int i = 0; i < 4; i++) {

            if (detectarFigurita(figus.get(i))) { // Devuelve true si la figurita ya esta adquirida

                _figuritasRepetidas.add(figus.get(i));
            }

            else {
                _figuritasAdquiridas.add(figus.get(i));
            }

        }

    }

    void AgregarFiguritaIndividual(Figurita figu){

        _figuritasAdquiridas.add(figu);


    }

    ArrayList<Figurita> pegarFiguritas() {

        //Por polimorfismo, si el album es extendido se invoca al pegar figuritas de extendido
        ArrayList<Figurita> figus = _album.pegarFiguritas(_figuritasAdquiridas);
        _figuritasAdquiridas.clear();
        return figus;

    }

    boolean poseeFigurita(Figurita fig){

        for (Figurita figurita : _figuritasAdquiridas) {
            
            if (figurita.equals(fig)) {
                return true;
            }
        }
        return false;

    }

    // ------------------------------------- Getters and Setters
    // --------------------------------------------------------

    Integer getDni() {
        return _dni;
    }

    Integer get_Codigo_Unico_Album() {

        return _album.codigoAlbum;
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

    public List<Figurita> get_figuritasRepetidas() {
        return _figuritasRepetidas;
    }

    Figurita obtenerFiguritaID(Integer codigo){


        for (Figurita figurita : _figuritasAdquiridas) {
            
            if (codigo.compareTo(figurita.getCodigo_ID()) == 0) {
                return figurita;
            }

        }

        throw new RuntimeException("Figurita no encontrada Error");
    }

    //Esta funcion se encarga de eliminar una figurita de la lista de adquiridas o repetidas
    void borrarFiguritaID(Integer CodigoFigurita){

       for (int i = 0; i < _figuritasAdquiridas.size(); i++) {
        
        if (_figuritasAdquiridas.get(i).getCodigo_ID() == CodigoFigurita) {
            
            _figuritasAdquiridas.remove(i);
        }

       }
       for (int i = 0; i < _figuritasRepetidas.size(); i++) {
        
        if (_figuritasRepetidas.get(i).getCodigo_ID() == CodigoFigurita) {
            
            _figuritasRepetidas.remove(i);
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
