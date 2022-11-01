public class AlbumWeb extends Album {
    
    private Integer _codigoPromocional;
    private boolean canjeoCodigoPromocional;

   
     //----------------------------------------------- Constructors --------------------------------------------------------- 

    AlbumWeb(){

        super("Web");
        super.setPremio("Camiseta Oficial");
        _codigoPromocional = generar_Codigo_Promocional();

    }

    void canjearCodigo(){

        canjeoCodigoPromocional = true;
    }

     //------------------------------------- Chequeo de metodos --------------------------------------------------------- 

     private Integer generar_Codigo_Promocional(){

        return (int) (Math.random() * 100);
     }


        //------------------------------------- Getters y Setters ---------------------------------------------------------
    
    
    public Integer get_codigoPromocional() {
        return _codigoPromocional;
    }

    public boolean getCanjeoCodigo() {
        return canjeoCodigoPromocional;
    }

  
}
