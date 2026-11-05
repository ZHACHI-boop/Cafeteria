package Model;

import java.util.ArrayList;
import java.util.List;

public class CafeteriaModelo {
    private List<Producto> listaProductos;
    private List<Pedido> listaPedidos;
    private double gananciaTotal;
    private ArchivoGenerico<String> gestorProductos;
    private ArchivoGenerico<String> gestorPedidos;

    public CafeteriaModelo() {
        this.listaProductos = new ArrayList<>();
        this.listaPedidos = new ArrayList<>();
        this.gananciaTotal = 0.0;
        this.gestorProductos = new ArchivoGenerico<>("productos.txt");
        this.gestorPedidos = new ArchivoGenerico<>("pedidos.txt");
        cargarDesdeDisco();
    }

    public boolean registrarProducto(String codigo, String nombre, double precio, int stock) {
        for (Producto p : listaProductos) {
            if (p.obtenerCodigo().equals(codigo)) {
                return false;
            }
        }
        listaProductos.add(new Producto(codigo, nombre, precio, stock));
        sincronizarProductos();
        return true;
    }

    public int registrarPedido(String codigoProducto, int cantidad) {
        Producto productoEncontrado = null;
        for (Producto p : listaProductos) {
            if (p.obtenerCodigo().equals(codigoProducto)) {
                productoEncontrado = p;
                break;
            }
        }

        if (productoEncontrado == null) {
            return 1; // Producto no encontrado
        }

        if (productoEncontrado.obtenerStock() < cantidad) {
            return 2; // Stock insuficiente
        }

        // Descontar stock y actualizar archivo de productos
        productoEncontrado.modificarStock(productoEncontrado.obtenerStock() - cantidad);
        sincronizarProductos();

        String idPedido = "PED-" + (listaPedidos.size() + 1);
        double subtotal = productoEncontrado.obtenerPrecio() * cantidad;
        listaPedidos.add(new Pedido(idPedido, productoEncontrado.obtenerNombre(), cantidad, subtotal));
        sincronizarPedidos();
        return 0; // Exito
    }

    public Pedido despacharPedido() {
        if (listaPedidos.isEmpty()) {
            return null;
        }
        Pedido despachado = listaPedidos.remove(0);
        gananciaTotal += despachado.obtenerSubtotal();
        sincronizarPedidos();
        return despachado;
    }

    public List<Producto> obtenerProductos() {
        return listaProductos;
    }

    public List<Pedido> obtenerPedidos() {
        return listaPedidos;
    }

    public double obtenerGananciaTotal() {
        return gananciaTotal;
    }

    public void sincronizarProductos() {
        List<String> lineas = new ArrayList<>();
        for (Producto p : listaProductos) {
            lineas.add(p.aTextoPlano());
        }
        gestorProductos.guardarDatos(lineas);
    }

    public void sincronizarPedidos() {
        List<String> lineas = new ArrayList<>();
        for (Pedido ped : listaPedidos) {
            lineas.add(ped.aTextoPlano());
        }
        gestorPedidos.guardarDatos(lineas);
    }

    public void cargarDesdeDisco() {
        List<String> lineasP = gestorProductos.leerLineas();
        listaProductos.clear();
        for (String linea : lineasP) {
            String[] p = linea.split(";");
            if (p.length == 4) {
                listaProductos.add(new Producto(p[0], p[1], Double.parseDouble(p[2]), Integer.parseInt(p[3])));
            }
        }

        List<String> lineasPed = gestorPedidos.leerLineas();
        listaPedidos.clear();
        for (String linea : lineasPed) {
            String[] p = linea.split(";");
            if (p.length == 4) {
                listaPedidos.add(new Pedido(p[0], p[1], Integer.parseInt(p[2]), Double.parseDouble(p[3])));
            }
        }
    }
}