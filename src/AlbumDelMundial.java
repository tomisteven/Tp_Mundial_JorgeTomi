import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class AlbumDelMundial implements IAlbumDelMundial {

	Fabrica fb; // Objeto fabrica de uso general
	Set<Participante> participantes; // La lista de participantes
//	Set<Participante> participantesAlbumCompleto;

	AlbumDelMundial() {

		participantes = new HashSet<>();
	//	participantesAlbumCompleto = new HashSet<>();
		fb = new Fabrica();

	}

	// --------------------------------- Metodos Principales
	// ----------------------------------------------
	
	//METODO IMPLEMENTADO
	@Override
	public int registrarParticipante(int dni, String nombre, String tipoAlbum) {

		//si esta registrado lanza excepcion sino...
		if (estaRegistrado(dni)) {
			throw new RuntimeException("El participante ya esta registrado");
		}

		participantes.add(new Participante(nombre, dni, tipoAlbum)); // Añadimos al nuevo participante
		return Detectar_Codigo_Unico(dni);

		/*
		 * Participante p1 = new Participante(nombre, dni, tipoAlbum); for (Participante
		 * participante : participantes) { // Vamos a recorrer la lista de participantes
		 * 
		 * if (participante.equals(p1)) { // equals de participante throw new
		 * RuntimeException("El participante ya esta registrado"); }
		 * 
		 * }
		 */

	}

	//METODO IMPLEMENTADO
	@Override
	public void comprarFiguritas(int dni) {

		if (!estaRegistrado(dni)) {
			throw new RuntimeException("Participante no registrado");
		}

		Participante p = DetectarParticipante(dni);
		p.agregarFiguritas();

	}

	//METODO IMPLEMENTADO
	@Override
	public void comprarFiguritasTop10(int dni) {

		Participante p = DetectarParticipante(dni);
		if (p.getAlbum()._tipo.equals("Extendido")) {
			p.agregarFiguritasTOP10();
		} else {

			throw new RuntimeException("El usuario no tiene album extendido");
		}

	}

		
	
	//METODO IMPLEMENTADO
	@Override
	public void comprarFiguritasConCodigoPromocional(int dni) {
		Participante p = DetectarParticipante(dni);
		AlbumWeb albumWeb = (AlbumWeb) p.getAlbum();
		if (p.get_tipoAlbum().equals("Web") && !albumWeb.getCanjeoCodigo()) {
			p.agregarFiguritas();
		} else {
			throw new RuntimeException("Accion invalida para el dni");

		}
	}
	
	//METODO IMPLEMENTADO
	@Override
	public List<String> pegarFiguritas(int dni) {

		if (!estaRegistrado(dni)) {
			throw new RuntimeException("Participante no encontrado");
		}

		Participante p = DetectarParticipante(dni);
		ArrayList<Figurita> figus = (ArrayList<Figurita>) p.pegarFiguritas();

		List<String> lista = new LinkedList<>();
		for (Figurita f : figus) {

			StringBuilder st = new StringBuilder();
			st.append("$" + f.get_pais() + "-");
			st.append("$" + f.getNumeroJugador());
			lista.add(st.toString());

		}

		return lista;
	}
	
	
	//
	// Devuelve true si el participante lleno el album
	@Override
	public boolean llenoAlbum(int dni) {

		if (!estaRegistrado(dni)) {
			throw new RuntimeException("Participante no encontrado");
		}

		Participante p = DetectarParticipante(dni);

		return p.getAlbum().getAlbumCompleto();
	}

	//METODO IMPLEMENTADO
	@Override
	public String aplicarSorteoInstantaneo(int dni) {
		Participante p = DetectarParticipante(dni);
		if (p == null || !(p.getAlbum() instanceof AlbumTradicional)) {
			throw new RuntimeException(" El dni no aplica para el sorteo");
		}
		AlbumTradicional albumTradicional = (AlbumTradicional) p.getAlbum();

		return albumTradicional.aplicarSorteoInstantaneo();
	}

	//METODO IMPLEMENTADO
	@Override
	public int buscarFiguritaRepetida(int dni) {
		if (!estaRegistrado(dni)) {
			throw new RuntimeException("Participante no encontrado");
		}

		Participante p = DetectarParticipante(dni);

		return (p.get_figuritasRepetidas().size() != 0) ? p.get_figuritasRepetidas().get(0).getCodigo_ID() : -1;

	}



	//METODO IMPLEMENTADO
	@Override
	public boolean intercambiarUnaFiguritaRepetida(int dni) {
		if (!estaRegistrado(dni)) {
			throw new RuntimeException("Participante no encontrado");
		}

		// Vamos a intercambiar una figurita repetida
		Iterator<Participante> it = participantes.iterator(); // Iterator de participantes
		Participante p = DetectarParticipante(dni); // Localizamos a nuestro participante
		Figurita figurita_Cambio;
		boolean ret = false;

		if (p.get_figuritasRepetidas().size() > 0) { // si tiene repetidas...
			figurita_Cambio = p.get_figuritasRepetidas().get(0); //agarramos la primera figurita
			
		} else {
			System.out.println("entro a if false");
				return false;		
		}

		// Recorremos la lista de participantes
		// Por IREP no nos importa el tipo de album en este caso
		while (it.hasNext()) {
			//System.out.println("entro al while");
			Participante other = it.next();

			// Veamos las figuritas repetidas de este participante
			for (Figurita figurita : other.get_figuritasRepetidas()) {

				// deteccion de valor base y que no sean la misma
				if (figurita.valor_base <= figurita_Cambio.valor_base && !figurita.equals(figurita_Cambio)) {
					// Detectamos si p tiene la figurita other de menor valor en su stack
					ret = TieneFigurita(dni, figurita);

					if (ret) {

						// Intercambiamos las figuritas de los participantes
						p.AgregarFiguritaIndividual(figurita);
						other.AgregarFiguritaIndividual(figurita_Cambio);
						
						//borramos de p y other la figurita que cambiamos
						p.borrarFiguritaID(figurita_Cambio.getCodigo_ID());
						other.borrarFiguritaID(figurita.getCodigo_ID());
						System.out.println("llego adentro");
						return true;

					}

				}
			}
		}
		System.out.println("llego afuera");
		return false;
		
	}

	//METODO IMPLEMENTADO
	@Override
	public String darNombre(int dni) {

		if (!estaRegistrado(dni)) {
			throw new RuntimeException("Participante invalido");
		}
		//devuelve el nombre del participante si esta registrado
		return DetectarParticipante(dni).getNombre();

	}

	//METODO IMPLEMENTADO
	@Override
	public String darPremio(int dni) {

		if (!estaRegistrado(dni)) {
			throw new RuntimeException("El participante no esta registrado");
		}
		
		Participante participante = this.DetectarParticipante(dni);
		StringBuilder st = new StringBuilder();

		if (participante != null && participante.getAlbum().getAlbumCompleto() && participante.premioRecibido() 
			|| participante != null && !participante.getAlbum().getAlbumCompleto() && !participante.premioRecibido() ) {
			throw new RuntimeException("El participante no puede recibir el premio");

		}else {
			participante.reclamarPremio();
			st.append(participante.getAlbum().getPremio()).append(" : \n");

			// "- ($dni) $nombre: $premio"
			st.append("- ($" + participante.getDni() + ") ");
			st.append("$nombre: " + participante.getNombre());
			st.append(" $premio: " + participante.getAlbum().getPremio());
		}
		return st.toString();
  
//       
	}
	
	//METODO IMPLEMENTADO
	@Override
	public String listadoDeGanadores() {

		Iterator<Participante> st = participantes.iterator();
		StringBuilder bl = new StringBuilder();

		while (st.hasNext()) {

			Participante p = st.next();
			// && p.get_premio()
			if (p.getAlbum().getAlbumCompleto()) {

				bl.append("- ($" + p.getDni() + ") ");
				bl.append("$" + p.getNombre() + ": ");
				bl.append("$" + p.premioRecibido() + "\n");

			}

		}

		return bl.toString();
	}

	@Override
	public List<String> participantesQueCompletaronElPais(String nombrePais) {
		// ($dni) $nombre: $tipoAlbum
		List<String> completaron_Pais = new ArrayList<>();

		for (Participante participante : participantes) {

			if (participante.getAlbum().getSeccion(nombrePais).get_Seccion_Completa()) {

				StringBuilder st = new StringBuilder();

				st.append("($" + participante.getDni() + ") ");
				st.append("$" + participante.getNombre() + ": ");
				st.append("$" + participante.get_tipoAlbum());

				completaron_Pais.add(st.toString());
			}

		}

		return completaron_Pais;
	}

	// ------------------------------------- Metodos de uso
	// general--------------------------------------------------------

	// Devuelve pasado un dni el codigo unico
	private Integer Detectar_Codigo_Unico(Integer dni) {

		Iterator<Participante> st = participantes.iterator();
		//mientras que haya otro participante..
		while (st.hasNext()) {
			//crea el participante..
			Participante p = (Participante) st.next();
			//verifica si el dni coincide con un dni ya registrado
			if (p.getDni().compareTo(dni) == 0) {

				return p.get_Codigo_Unico_Album();
			}
		}

		return -1;

	}

	// --------------------------------------------- Localizacion
	// -------------------------------------------
	private Participante DetectarParticipante(Integer dni) {

		// Vamos a recorrer la lista de participantes
		Iterator<Participante> st = participantes.iterator();
		while (st.hasNext()) {

			Participante p = (Participante) st.next();
			if (p.getDni().compareTo(dni) == 0) { // si son iguales compareto devuelve 0

				return p;
			}
		}

		return null; // retorna null si no se lo encuentra

	}

	boolean TieneFigurita(Integer dni, Figurita figu) {

		Participante p = DetectarParticipante(dni);

		return p.poseeFigurita(figu);

	}

	// Retorna true si el participante esta en la lista de participantes
	private boolean estaRegistrado(Integer dni) {

		Iterator<Participante> st = participantes.iterator();

		while (st.hasNext()) {

			Participante p = (Participante) st.next();

			if (p.getDni().compareTo(dni) == 0) {

				return true;
			}
		}

		return false;

	}

	// Obtener el codigo unico del album de determinado participante
	public Integer get_Codigo_Album(Integer dni) {

		Iterator<Participante> st = participantes.iterator();

		while (st.hasNext()) {

			Participante p = (Participante) st.next();

			if (p.getDni().compareTo(dni) == 0) {

				return p.get_Codigo_Unico_Album();
			}
		}
		throw new RuntimeException("Participante no encontrado");
	}

	// Muestra las figuritas adquiridas que posee este participante y sus datos
	public String mostrarParticipante(int dni) throws Exception {

		if (!estaRegistrado(dni)) {
			throw new RuntimeException("El participante no esta registrado");
		}

		for (Participante p : participantes) {

			if (p.getDni() == dni) {

				return p.toString();
			}

		}

		return "";

	}

	public Figurita agregarFiguritaIndividualPruebas(Integer dni) {

		Participante p = DetectarParticipante(dni);
		Figurita nueva = new Figurita("Jugador" + 0, 1000, "Argentina", 1);
		p.AgregarFiguritaIndividual(nueva);
		return nueva;
	}

	public void getSecciones(Integer dni) {

		if (!estaRegistrado(dni)) {
			throw new RuntimeException("El participante no esta registrado");
		}

		Participante p = DetectarParticipante(dni);

		System.out.println(p.getAlbum().secciones_Completas());

	}

	// Metodo auxiliar que permite mostrar la seccion de cualquier pais
	String Mostrar_Una_Seccion(String pais, Integer dni) {

		Fabrica fb = new Fabrica();
		String[] st = fb.getListadoDeMundialesTop10();
		Participante p = DetectarParticipante(dni);

		if (p.get_tipoAlbum().equals("Extendido")) {

			for (String _pais : st) {
				StringBuilder builder = new StringBuilder();
				_pais = String.valueOf(_pais.charAt(pais.length() - 2))
						+ String.valueOf(_pais.charAt(pais.length() - 1));

				if (_pais.equals(pais) || pais.equals("Japón")) {

					builder.append(p.getAlbum().paises.get(pais).toString());
					AlbumExtendido album = (AlbumExtendido) p.getAlbum();
					builder.append(album.getPaisesMundialistas().get(_pais).toString());
					return builder.toString();

				}
			}
		}

		return p.getAlbum().paises.get(pais).toString();
	}

	@Override
	public boolean intercambiar(int dni, int codFigurita) {
		// TODO Auto-generated method stub
		return false;
	}

}
