import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class App {
    public static void main(String[] args) throws Exception {
        

        //Pruebas

        AlbumDelMundial ab = new AlbumDelMundial();

        ab.registrarParticipante(43729568, "Miguel Angel", "Extendido");
        ab.registrarParticipante(43729567, "xd", "Web");
       

        for (int i = 0; i < 500; i++) {
            
            ab.comprarFiguritas(43729568);
            ab.comprarFiguritas(43729567);
            ab.comprarFiguritasTop10(43729568);
        }


        //Figurita f = ab.agregarFiguritaIndividualPruebas(43729568);
        
       
        System.out.println("--------------------------------------------------------");
       
       //System.out.println(ab.mostrarParticipante(43729568));
       //System.out.println(ab.mostrarParticipante(43729567));
      
       
       System.out.println(ab.intercambiarUnaFiguritaRepetida(43729568));
       ab.pegarFiguritas(43729568);
       ab.pegarFiguritas(43729567);
       //System.out.println(ab.mostrarParticipante(43729568));
       ab.getSecciones(43729568);
       System.out.println(ab.llenoAlbum(43729568));
       System.out.println(ab.llenoAlbum(43729567));

       System.out.println(ab.Mostrar_Una_Seccion("Brasil", 43729568));
       String ganadores = ab.listadoDeGanadores();

        System.out.println(ganadores);
        List<String> list = ab.participantesQueCompletaronElPais("Argentina");

        Iterator<String> complete = list.iterator();

        while (complete.hasNext()) {
            
            System.out.println(complete.next());
        }
       
         /*
       System.out.println("IDRepetida: " + ab.buscarFiguritaRepetida(43729568));
       System.out.println(ab.darNombre(43729568));

        
        List<String> lt = ab.pegarFiguritas(43729568);
        Iterator<String> it = lt.iterator();


        
       
        System.out.println(ab.get_Codigo_Album(43729568));
        System.out.println(ab.get_Codigo_Album(43729567));
        System.out.println(ab.darPremio(43729568));
        
      

        
        System.out.println("Figus Pegadas: ");
        while (it.hasNext()) {
            
            System.out.println(it.next());

        }
*/
       
       

    }
}
