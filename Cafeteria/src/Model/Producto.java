package Model;

public class Producto {
    private String codigo;
    private String nombre;
    private double precio;
    private int stock;

    public Producto(String codigo, String nombre, double precio, int stock) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
    }

    public String obtenerCodigo() {
        return codigo;
    }

    public String obtenerNombre() {
        return nombre;
    }

    public double obtenerPrecio() {
        return precio;
    }

    public int obtenerStock() {
        return stock;
    }

    public void modificarStock(int nuevoStock) {
        this.stock = nuevoStock;
    }

    public String aTextoPlano() {
        return codigo + ";" + nombre + ";" + precio + ";" + stock;
    }
}