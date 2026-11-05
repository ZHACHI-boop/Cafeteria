package Model;

public class Pedido {
    private String idPedido;
    private String nombreProducto;
    private int cantidad;
    private double subtotal;

    public Pedido(String idPedido, String nombreProducto, int cantidad, double subtotal) {
        this.idPedido = idPedido;
        this.nombreProducto = nombreProducto;
        this.cantidad = cantidad;
        this.subtotal = subtotal;
    }

    public String obtenerIdPedido() {
        return idPedido;
    }

    public String obtenerNombreProducto() {
        return nombreProducto;
    }

    public int obtenerCantidad() {
        return cantidad;
    }

    public double obtenerSubtotal() {
        return subtotal;
    }

    public String aTextoPlano() {
        return idPedido + ";" + nombreProducto + ";" + cantidad + ";" + subtotal;
    }
}