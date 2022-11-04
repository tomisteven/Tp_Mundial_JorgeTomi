import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Album {
   protected String _tipo; // Tipo de album 
   // String = nombre del pais, seccion posee los jugadores de cada uno
   protected HashMap<String, Seccion> _paises; 
   protected Integer _codigoAlbum; // Codigo unico de este album
   protected boolean _albumCompleto; // true si las secciones estan completas y false en caso contrario

   private static int _codigo; // Lo usamos para los codigos de cada album
   private String _premio; // metodo usado en la herencia
   protected boolean canjeo_Premio_Album; // false por default
   protected Fabrica fb = new Fabrica();

   // ----------------------------------------------- Constructors -------------------------------------------
   Album(String type) {

      generar_Codigo_Album(); // Generamos un codigo para el album
      _tipo = type;
      _paises = new HashMap<>();
      _albumCompleto = false;
      establecerPaises();
   }

   // ------------------------------------- Generadores -------------------------------------------------------

   private void generar_Codigo_Album() {
      _codigo += 1;
      _codigoAlbum = _codigo;
   }

   private void establecerPaises() {
      String[] paisesParticipantes = fb.getPaisesParticipantes();
      for (int i = 0; i < 32; i++) { // Son 32 secciones de 0 - 31
         _paises.put(paisesParticipantes[i], new Seccion(paisesParticipantes[i], i));
      }
   }

   protected ArrayList<Figurita> pegarFiguritas(List<Figurita> figusPegar) {
      // Pegar Figuritas de la clase album
      ArrayList<Figurita> figusPegadas = new ArrayList<>(); // Las figus que se van a pegar
     if (_albumCompleto) return figusPegadas; // no sigue pegando
     
      // Recorremos la lista de figuritas a pegar
      for (Figurita figurita : figusPegar) {

         //Selecciono el pais y pego las figuritas...
         //System.out.println(figurita.get_pais());
         Figurita fig = _paises.get(figurita.get_pais()).pegarFiguritas(figurita);

         // Fig es null si y solo si el usuario me paso una figurita que ya pego.
         if (fig != null) { 
            figusPegadas.add(fig);
         }
      }

      if (album_Completo()) _albumCompleto = true;
 
      return figusPegadas; // Devolvemos las figuritas que pego el usuario

   }

   private boolean album_Completo(){
      String[] st = fb.getPaisesParticipantes();
      boolean ret = true;
      if(!_tipo.equals("Extendido")) {
    	  for (int i = 0; i < 32; i++) {
    		  ret = ret && _paises.get(st[i]).get_Seccion_Completa();
    	  }
      }
      return ret;
   }

   // --------------------------------Setters & Getters ---------------------------------------------------------

   public Integer getCodigoAlbum() {
      return _codigoAlbum;
   }

   public boolean getAlbumCompleto() {
      return _albumCompleto;
   }
   
   public void setPremio(String premio) {
	   _premio = premio;
   }
   
   public String getPremio() {
		return _premio;
   }
   
   public String getTipo() {
	   return _tipo;
   }

   Seccion getSeccion(String pais){
      return _paises.get(pais);
   }

   public String secciones_Completas() {
      
      //Muestra las secciones completas de la lista de paises
      String[] st = fb.getPaisesParticipantes();
      StringBuilder bl = new StringBuilder();

      for (String pais: st) {   
         boolean completa = _paises.get(pais).get_Seccion_Completa();
         int size = _paises.get(pais).getFigusPegadas().size();
         bl.append("$" + pais + ":" + completa + " $" + size + "\n" );
      }
      return bl.toString();
   }
}
