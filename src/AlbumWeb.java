public class AlbumWeb extends Album {
    
    private boolean _codigoPromoDisponible;
   
     //------------------- Constructor --------------------------- 

    AlbumWeb() {
        super("Web");
        super.setPremio("Camiseta Oficial");
        _codigoPromoDisponible = true;
    }
      //------------------ Getters y Setters ----------------------
  
    public boolean getCodigoPromoDisponible() {
        return _codigoPromoDisponible;
    }

    public void setCanjeoCodigo() {
        _codigoPromoDisponible = false;
    }
  
}
