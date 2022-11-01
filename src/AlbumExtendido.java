import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.print.attribute.standard.Copies;

public class AlbumExtendido extends AlbumTradicional {

  private HashMap<String, Seccion> paisesMundialistas;

  // ----------------------------------------------- Constructors
  // ---------------------------------------------------------


  public AlbumExtendido() {

    super("Extendido");
    super.setPremio("Pelota y Viaje");

    paisesMundialistas = new HashMap<>();
    establecerPaisesMundialistas();

  }

  private void establecerPaisesMundialistas() {

    Fabrica fb = new Fabrica();

    String[] paisesMundiales = fb.getListadoDeMundialesTop10();

    // Hay 10 paises mundialistas

    for (int i = 0; i < 10; i++) {

      String pais = paisesMundiales[i].substring(0, paisesMundiales[i].length() - 4);
      if (!pais.equals("Corea del Sur y Japón")) {
        paisesMundialistas.put(pais,
            new Seccion(pais, i));
      } else {
        paisesMundialistas.put("Japón",
            new Seccion("Japón", i));
      }

    }

  }

  @Override
  protected ArrayList<Figurita> pegarFiguritas(List<Figurita> figusPegar) {

    // Vamos a dividir las figuritas top 10 y comunes que quiere pegar el usuario
    ArrayList<Figurita> figtop10 = obtenerTOP10((ArrayList<Figurita>) figusPegar);
    ArrayList<Figurita> figComunes = obtenerComunes((ArrayList<Figurita>) figusPegar);

    // Aqui se almacenan las figuritas pegadas
    ArrayList<Figurita> figusPegadas = new ArrayList<>();

    // Utilizo super con las figuritas comunes
    // Notar que tuve que sacarlas de la lista que me pasaron por parametro
    
    for (Figurita fig : figtop10) {

      // Operacion de pegar las figuritas top 10
      
      paisesMundialistas.get(fig.get_pais()).pegarFiguritaTOP10(fig);

      if (fig != null) {

        figusPegadas.add(fig);
      }

    }

    ArrayList<Figurita> figusComunes = super.pegarFiguritas(figComunes);

    

    for (Figurita figurita : figusComunes) {

      figusPegadas.add(figurita);

    }

    return figusPegadas;
  }

  private ArrayList<Figurita> obtenerTOP10(ArrayList<Figurita> fig) {

    Iterator<Figurita> st = fig.iterator();
    ArrayList<Figurita> re = new ArrayList<>();
    while (st.hasNext()) {

      Figurita next = st.next();
      if (next != null) {

        if (next instanceof FiguritaTop10) {

          re.add(next);

        }

      }

    }

    return re;

  }

  private ArrayList<Figurita> obtenerComunes(ArrayList<Figurita> fig) {

    Iterator<Figurita> st = fig.iterator();
    ArrayList<Figurita> re = new ArrayList<>();
    while (st.hasNext()) {

      Figurita next = st.next();
      if (next != null) {

        if (!next.tipoFigurita) {

          re.add(next);

        }

      }

    }

    return re;

  }

  @Override
  public String secciones_Completas() {

    StringBuilder st = new StringBuilder();

    String[] paises = generarListadoDeMundiales();
    st.append(super.secciones_Completas());
    st.append("\n");
    for (String pais : paises) {

      
      if (paisesMundialistas.get(pais) != null) {

        boolean completa = paisesMundialistas.get(pais).get_Seccion_Completa();
        int size = paisesMundialistas.get(pais).getFigusPegadas().size();
       
        st.append("$T" + pais + ":" + completa + " $" + size + "\n");

      }

    }

    return st.toString();

  }

  
  public HashMap<String, Seccion> getPaisesMundialistas() {
    return paisesMundialistas;
  }

  
  


  private String[] generarListadoDeMundiales() {
    return new String[] {
        "España", "México", "Italia", "Estados Unidos",
        "Francia", "Japón", "Alemania",
        "Sudáfrica", "Brasil", "Rusia" };
  }

}
