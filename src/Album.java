import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


// Clase Album
public class Album {

   protected String _tipo; // Tipo de album 
   // String = nombre del pais, seccion posee los jugadores de cada uno
   protected HashMap<String, Seccion> paises; 
   

   protected Integer codigoAlbum; // Codigo unico de este album
   protected boolean albumCompleto; // true si las secciones estan completas y false en caso contrario

   private static int codigos; // Lo usamos para los codigos de cada album
   private String _premio; // metodo usado en la herencia
   protected boolean canjeo_Premio_Album; // false por default

   // ----------------------------------------------- Constructors -------------------------------------------
   Album(String type) {

      generar_Codigo_Album(); // Generamos un codigo para el album
      _tipo = type;
      paises = new HashMap<>();
      albumCompleto = false;
      establecerPaises();

   }

   // ------------------------------------- Generadores -------------------------------------------------------

   private void generar_Codigo_Album() {

      codigos += 1;
      codigoAlbum = codigos;

   }

   private void establecerPaises() {

      Fabrica fb = new Fabrica(); // Usamos la clase Fabrica para obtener los paises
      String[] paisesParticipantes = fb.getPaisesParticipantes();

      for (int i = 0; i < 32; i++) { // Son 32 secciones de 0 - 31

         paises.put(paisesParticipantes[i], new Seccion(paisesParticipantes[i], i));

      }

   }

   protected ArrayList<Figurita> pegarFiguritas(List<Figurita> figusPegar) {

      // Pegar Figuritas de la clase album
      ArrayList<Figurita> figusPegadas = new ArrayList<>(); // Las figus que se van a pegar
      
      // Recorremos la lista de figuritas a pegar
      for (Figurita figurita : figusPegar) {

         //String key = figurita.get_pais();

         //Selecciono el pais y pego las figuritas...
         //System.out.println(figurita.get_pais());
         Figurita fig = paises.get(figurita.get_pais()).pegarFiguritas(figurita);

         // Fig es null si y solo si el usuario me paso una figurita que ya pego.
         if (fig != null) { 
            figusPegadas.add(fig);
         }

      }

      if (!albumCompleto && detectar_Album_Completo()) {
         albumCompleto = !albumCompleto;
      }
     

      return figusPegadas; // Devolvemos las figuritas que pego el usuario

   }

   private boolean detectar_Album_Completo(){

      Fabrica fb = new Fabrica();

      String[] st = fb.getPaisesParticipantes();
      boolean ret = true;
      if(!_tipo.equals("Extendido")) {
    	  for (int i = 0; i < 32; i++) {
    	         ret = ret && paises.get(st[i]).get_Seccion_Completa();

    	      }
      }
      
      
      return ret;


   }

   

   // ------------------------------------- Getters ---------------------------------------------------------

   public Integer getCodigoAlbum() {

      return codigoAlbum;

   }

   public boolean getAlbumCompleto() {

      return albumCompleto;
   }
   public void setPremio(String premio) {
	   this._premio = premio;
   }
   public String getPremio() {
	if(canjeo_Premio_Album ) {
		throw new RuntimeException("premio ya otorgado ");
	}else {
		canjeo_Premio_Album = true;
	      return _premio;

	}

   }

   Seccion getSeccion(String pais){

      return paises.get(pais);

   }

   public String secciones_Completas() {
      
      //Muestra las secciones completas de la lista de paises
      Fabrica fb = new Fabrica();
      String[] st = fb.getPaisesParticipantes();
      StringBuilder bl = new StringBuilder();

      for (String pais : st) {
         
         boolean completa = paises.get(pais).get_Seccion_Completa();
         int size = paises.get(pais).getFigusPegadas().size();
         bl.append("$" + pais + ":" + completa + " $" + size + "\n" );

      }

      
      
      
      return bl.toString();
   }

   
}
