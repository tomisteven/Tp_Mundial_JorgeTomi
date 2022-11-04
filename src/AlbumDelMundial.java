import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class AlbumDelMundial implements IAlbumDelMundial {

	Fabrica fb;
	Set<Participante> participantes;
	static int _codigos_promo_canjeados;
	static int _sorteos_instantaneos_realizados;

	AlbumDelMundial() {
		participantes = new HashSet<>();
		fb = new Fabrica();
		_codigos_promo_canjeados = 0;
		_sorteos_instantaneos_realizados = 0;
	}

	// ------------------------- Métodos Principales --------------------------
	
	//METODO IMPLEMENTADO
	@Override
	public int registrarParticipante(int dni, String nombre, String tipoAlbum) {
		
		if (dni > 99999999 || dni < 1000) {
			throw new RuntimeException("El dni no puede ser mayor a "
										+ "99999999 ni menor a 1000");
		}
		if (nombre.length() > 15) {
			throw new RuntimeException("el nombre no puede tener más de 15 caracteres");
		}		

		//si ya esta registrado lanza excepcion
		if (estaRegistrado(dni)) {
			throw new RuntimeException("El participante ya esta registrado");
		}
		
		if (!(tipoAlbum.equals("Web") || tipoAlbum.equals("Extendido") ||
        tipoAlbum.equals("Tradicional"))) {
			throw new RuntimeException("Tipo de álbum no válido");
		}
		
		Participante p = new Participante(nombre, dni, tipoAlbum);
		participantes.add(p); // agregamos participante

		return buscar_codigo_album(dni);
	}

	//METODO IMPLEMENTADO
	@Override
	public void comprarFiguritas(int dni) {
		if (!estaRegistrado(dni)) 
			throw new RuntimeException("El participante no está registrado!");

		Participante p = buscarParticipante(dni);
		p.agregarFiguritas();

	}

	//METODO IMPLEMENTADO
	@Override
	public void comprarFiguritasTop10(int dni) {
		Participante p = buscarParticipante(dni);
		if (p.getAlbum().getTipo().equals("Extendido"))	p.agregarFiguritasTOP10();
		else throw new RuntimeException("El usuario no tiene album extendido");
	}

	//METODO IMPLEMENTADO
	@Override
	public void comprarFiguritasConCodigoPromocional(int dni) {
		Participante p = buscarParticipante(dni);

		if (!(p.get_tipoAlbum().equals("Web"))) // no tipa el álbum con ese participante
				throw new RuntimeException("Sin álbum web no se puede usar código promo!");
		
		AlbumWeb a = (AlbumWeb) p.getAlbum();	
		if (!(a.getCodigoPromoDisponible()))
			throw new RuntimeException("Código promo ya canjeado");
			
			p.agregarFiguritas();
			_codigos_promo_canjeados++;
			a.setCanjeoCodigo();
	}
	
	//METODO IMPLEMENTADO
	@Override
	public List<String> pegarFiguritas(int dni) {
		if (!estaRegistrado(dni)) throw new RuntimeException("dni inexistente!");

		Participante p = buscarParticipante(dni);
		ArrayList<Figurita> figus = (ArrayList<Figurita>) p.pegarFiguritas();

		List<String> lista = new LinkedList<>();
		for (Figurita f : figus) {

			StringBuilder st = new StringBuilder();
			st.append("$" + f.get_pais() + "-"
					+ "$" + f.getNumeroJugador());
			lista.add(st.toString());
//			System.out.println("pegó:\n" + f.toString());
		}
		return lista;
	}
	
	// true: album lleno
	@Override
	public boolean llenoAlbum(int dni) {
		if (!estaRegistrado(dni)) throw new RuntimeException("dni inexistente!");
		Participante p = buscarParticipante(dni);
		return p.getAlbum().getAlbumCompleto();
	}

	//METODO IMPLEMENTADO
	@Override
	public String aplicarSorteoInstantaneo(int dni) {
		Participante p = buscarParticipante(dni);
		if (p == null) throw new RuntimeException("dni inexistente!");
		if (!(p.getAlbum().getTipo().equals("Tradicional")))
			throw new RuntimeException("El participante no aplica para el sorteo instantáneo");	
		AlbumTradicional album = (AlbumTradicional) p.getAlbum();
		_sorteos_instantaneos_realizados++;
		return album.sortear();
	}

	@Override
	/**
	 * Busca si el participante tiene alguna figurita repetida y devuelve 
	 * el codigo de la primera que encuentre.
	 * Si no encuentra ninguna, devuelve el codigo -1. 
	 */
	public int buscarFiguritaRepetida(int dni) {
		if (!estaRegistrado(dni)) throw new RuntimeException("dni inexistente!");
		Participante p = buscarParticipante(dni);
		System.out.println("REPETIDA: " + p.get_figuritasRepetidas().get(0).getCodigo_ID());
		return (p.get_figuritasRepetidas().size() != 0) ? p.get_figuritasRepetidas().get(0).getCodigo_ID() : -1;
	}
	
	@Override
	public boolean intercambiar(int dni, int codFigurita) {
		//participante
		Participante participante_cambiador = buscarParticipante(dni);
		//LA FIGURITA QUE SE VA A CAMBIAR
		Figurita Figurita_Cambio = participante_cambiador.obtenerFiguritaID(codFigurita);

		boolean ret = false;
		Iterator<Participante> it = participantes.iterator();

		while (it.hasNext()) {

			Participante participate_otro = it.next();

			// si Tienen el mismo tipo de album
			if (participate_otro.get_tipoAlbum().equals(participante_cambiador.get_tipoAlbum())) {

				// Vamos a ver las figus repetidas de other
				for (int i = 0; i < participate_otro.get_figuritasRepetidas().size(); i++) {

					Figurita figuritaDeParticipanteACambiar = participate_otro.get_figuritasRepetidas().get(i);
					// Comparamos valor base de figurita de other con la de p
					if (figuritaDeParticipanteACambiar.getValor_base() == Figurita_Cambio.getValor_base()
							&& !figuritaDeParticipanteACambiar.equals(Figurita_Cambio)) {

						// Buscamos si p tiene la figurita other de menor valor en su stock
						ret = tieneFigurita(dni, figuritaDeParticipanteACambiar);

						if (!ret) {
							System.out.println(
									"Figurita Intercambiada ID: " + participate_otro.get_figuritasRepetidas().get(i).getCodigo_ID()
											+ "\n" + "Figurita Intercambiada Pais: "
											+ participate_otro.get_figuritasRepetidas().get(i).get_pais());

							// Intercambiamos las figuritas de los participantes
							participante_cambiador.AgregarFiguritaIndividual(participate_otro.get_figuritasRepetidas().get(i));
							participate_otro.AgregarFiguritaIndividual(participante_cambiador.obtenerFiguritaID(codFigurita));
							// Eliminamos las intercambiadas
							participante_cambiador.borrarFiguritaID(codFigurita);
							participate_otro.borrarFiguritaID(participate_otro.get_figuritasRepetidas().get(i).getCodigo_ID());
							return true;
						}

					}

				}

			}

		}
		return false;
	}

	@Override
	public boolean intercambiarUnaFiguritaRepetida(int dni) {
		if (!estaRegistrado(dni)) {
			throw new RuntimeException("Participante no encontrado");
		}
		// Vamos a intercambiar una figurita repetida
		Iterator<Participante> it = participantes.iterator(); // Iterator de participantes
		Participante p = buscarParticipante(dni); // Localizamos a nuestro participante
		Figurita figurita_Cambio;
		boolean ret = false;

		if (p.get_figuritasRepetidas().size() != 0) // si tiene repetidas...
			figurita_Cambio = p.get_figuritasRepetidas().get(0); //agarramos la primera figurita
		else return false;		

		// Recorremos la lista de participantes
		// Por diseño no nos importa el tipo de album en este caso
		while (it.hasNext()) {
			Participante other = it.next();

			// Veamos las figuritas repetidas de este participante
			for (Figurita figurita : other.get_figuritasRepetidas()) {

				// getting el valor base y que no sean la misma
				if (figurita.valor_base == figurita_Cambio.valor_base && !figurita.equals(figurita_Cambio)) {
					// Detectamos si p tiene la figurita other de menor valor en stock
					ret = tieneFigurita(dni, figurita);

					if (ret) {

						// Intercambiamos las figuritas de los participantes
						p.AgregarFiguritaIndividual(figurita);
						other.AgregarFiguritaIndividual(figurita_Cambio);
						
						//borramos de p y other la figurita que cambiamos
						p.borrarFiguritaID(figurita_Cambio.getCodigo_ID());
						other.borrarFiguritaID(figurita.getCodigo_ID());
						return true;
					}
				}
			}
		}
		return false;
	}

	//METODO IMPLEMENTADO
	@Override
	public String darNombre(int dni) {

		if (!estaRegistrado(dni)) {
			throw new RuntimeException("Participante invalido");
		}
		//devuelve el nombre del participante si esta registrado
		return buscarParticipante(dni).getNombre();
	}

	//METODO IMPLEMENTADO
	@Override
	public String darPremio(int dni) {

		if (!estaRegistrado(dni)) {
			throw new RuntimeException("El participante no esta registrado");
		}
		
		Participante participante = this.buscarParticipante(dni);
		StringBuilder st = new StringBuilder();

		if (participante != null && participante.getAlbum().getAlbumCompleto() && participante.premioRecibido() 
			|| participante != null && !participante.getAlbum().getAlbumCompleto() && !participante.premioRecibido() ) {
			throw new RuntimeException("El participante no puede recibir el premio");
		}
		else {
			participante.reclamarPremio();
			st.append(participante.getAlbum().getPremio()).append(" : \n");

			//
			st.append("($" + participante.getDni() + ") "
					  + "$" + participante.getNombre() + ": "
					  + "$" + participante.getAlbum().getPremio());
		}
		
//		System.out.println(st.toString());
		return st.toString();
  
//       
	}
	
	//METODO IMPLEMENTADO
	@Override
	public String listadoDeGanadores() {
		Iterator<Participante> i = participantes.iterator();
		StringBuilder lista = new StringBuilder();
		
		while (i.hasNext()) {
			Participante p = i.next();
			if (p.getAlbum().getAlbumCompleto()) {
				lista.append("($" + p.getDni().toString() + ")"
							 + " $" + p.getNombre()
							 + " $" + p.getAlbum().getPremio() + "\n");
			}
		}
		return lista.toString();
	}

	@Override
	public List<String> participantesQueCompletaronElPais(String nombrePais) {
		List<String> completaronPais = new ArrayList<>();
		
		for (Participante participante : participantes) {
			if (participante.getAlbum().getSeccion(nombrePais).get_Seccion_Completa()) {
				completaronPais.add("($" + participante.getDni().toString() + ")"
									+ " $" + participante.getNombre()
									+ " $" +participante.get_tipoAlbum());
			}
		}
		return completaronPais;
	}	

	// ----------------- Metodos de uso general -----------------

	// Devuelve pasado un dni el codigo unico
	private Integer buscar_codigo_album(Integer dni) {

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

	// -------------------------- Localizacion -------------------
	private Participante buscarParticipante(Integer dni) {
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

	boolean tieneFigurita(Integer dni, Figurita figu) {

		Participante p = buscarParticipante(dni);
	//	System.out.println("Tiene la figu??" +  p.poseeFigurita(figu));
		return p.poseeFigurita(figu);
	}

	// Retorna true si el participante esta en la lista de participantes
	private boolean estaRegistrado(Integer dni) {
		boolean resultado = false;
		for (Participante p: participantes) {
			resultado = resultado || p.getDni().equals(dni);
		}
		return resultado;
	}

	// Obtener el codigo unico del album de determinado participante
	public Integer get_Codigo_Album(Integer dni) {
		
		for (Participante p: participantes) {
			if (p.getDni().equals(dni)) return p.get_Codigo_Unico_Album();
		}
		throw new RuntimeException("No existe ese participante");
	}
	
	// Muestra las figuritas adquiridas que posee este participante y sus datos
	public String mostrarParticipante(int dni) {
		if (!estaRegistrado(dni)) {
			throw new RuntimeException("El participante no esta registrado");
		}
		
		String participante = "";
		for (Participante p : participantes) {
			if (p.getDni() == dni) {
				participante =  p.toString();
			}
		}
		return participante;
	}

	public Figurita agregarFiguritaIndividualPruebas(Integer dni) {

		Participante p = buscarParticipante(dni);
		Figurita nueva = new Figurita("Jugador" + 0, 1000, "Argentina", 1);
		p.AgregarFiguritaIndividual(nueva);
		return nueva;
	}

	// Metodo auxiliar que permite mostrar la seccion de cualquier pais
	String Mostrar_Una_Seccion(String pais, Integer dni) {

		Fabrica fb = new Fabrica();
		String[] st = fb.getListadoDeMundialesTop10();
		Participante p = buscarParticipante(dni);

		if (p.get_tipoAlbum().equals("Extendido")) {

			for (String _pais : st) {
				StringBuilder builder = new StringBuilder();
				_pais = String.valueOf(_pais.charAt(pais.length() - 2))
						+ String.valueOf(_pais.charAt(pais.length() - 1));

				if (_pais.equals(pais) || pais.equals("Japón")) {

					builder.append(p.getAlbum()._paises.get(pais).toString());
					AlbumExtendido album = (AlbumExtendido) p.getAlbum();
					builder.append(album.getPaisesMundialistas().get(_pais).toString());
					return builder.toString();

				}
			}
		}

		return p.getAlbum()._paises.get(pais).toString();
	}
	
	public int getCodigosCanjeados() {
		return _codigos_promo_canjeados;
	}
	
	public int getSorteosInstRealizados() {
		return _sorteos_instantaneos_realizados;
	}

	@Override
	public String toString() {
		int cantParticipantes = participantes.size();
		int cantAlbumesTradicionales = 0, cantAlbumesWeb = 0, cantAlbumesExtendidos = 0;
		StringBuilder sis = new StringBuilder();
		
		for (Participante p: participantes) {
			if (p.get_tipoAlbum().equals("Tradicional")) cantAlbumesTradicionales++;
			else if (p.get_tipoAlbum().equals("Web")) cantAlbumesWeb++;
			else cantAlbumesExtendidos++;
		}	
		
		sis.append("*** Estadísticas generales - Sistema Álbum del Mundial ***\n\n");
		sis.append("Cantidad de participantes registrados: " + cantParticipantes + "\n\n");
		sis.append("Cantidad de figuritas fabricadas: " + Figurita.getCantFigusFabricadas() + "\n\n");
		sis.append("Cantidad de álbumes vendidos según tipo:\n");
		sis.append("Tradicionales: " + cantAlbumesTradicionales + "\n" +
				   "Extendidos: " + cantAlbumesExtendidos    + "\n" +
				   "Web: " + cantAlbumesWeb + "\n\n");
		
		sis.append("Códigos promocionales canjeados: " + getCodigosCanjeados()+"\n");
		sis.append("Sorteos Instantáneos Realizados: " + getSorteosInstRealizados());
		
	return sis.toString();
	}
}
