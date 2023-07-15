
package validate;

public enum EstadoPedido {
    PENDIENTE("Pendiente"),
    ACEPTADO("Aceptado"),
    EN_CAMINO("En camino"),
    ENTREGADO("Entregado");
 
    private String value;

    public String getValue() {
        return value;
    }

    EstadoPedido(String value){
        this.value = value;
    }
}
