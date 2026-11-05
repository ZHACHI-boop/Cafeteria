package Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import View.VentanaCafeteria;
import Model.CafeteriaModelo;
import Model.Pedido;

public class CafeteriaControlador implements ActionListener {
    private CafeteriaModelo modelo;
    private VentanaCafeteria vista;

    public CafeteriaControlador(CafeteriaModelo modelo, VentanaCafeteria vista) {
        this.modelo = modelo;
        this.vista = vista;
        this.vista.asignarListeners(this);
        this.vista.setVisible(true);
        refrescarVista();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand();
        if (comando.equals("REG_PROD")) {
            procesarRegistroProducto();
        } else if (comando.equals("REG_PEDIDO")) {
            procesarRegistroPedido();
        } else if (comando.equals("DESPACHAR")) {
            procesarDespacho();
        }
    }

    public void procesarRegistroProducto() {
        String codigo = vista.obtenerCodProd();
        String nombre = vista.obtenerNombreProd();
        String precioStr = vista.obtenerPrecioProd();
        String stockStr = vista.obtenerStockProd();

        if (codigo.isEmpty() || nombre.isEmpty() || precioStr.isEmpty() || stockStr.isEmpty()) {
            vista.mostrarMensaje("Error: Todos los campos del producto son obligatorios.");
            return;
        }

        try {
            double precio = Double.parseDouble(precioStr);
            int stock = Integer.parseInt(stockStr);

            if (precio <= 0 || stock < 0) {
                vista.mostrarMensaje("Error: El precio debe ser mayor a cero y el stock no puede ser negativo.");
                return;
            }

            boolean exito = modelo.registrarProducto(codigo, nombre, precio, stock);
            if (exito) {
                vista.mostrarMensaje("Producto registrado exitosamente en el inventario.");
                vista.limpiarProd();
                refrescarVista();
            } else {
                vista.mostrarMensaje("Error: El codigo de producto ya existe.");
            }
        } catch (NumberFormatException e) {
            vista.mostrarMensaje("Error: El precio y el stock deben ser numeros validos.");
        }
    }

    public void procesarRegistroPedido() {
        String codigo = vista.obtenerCodPedido();
        String cantStr = vista.obtenerCantPedido();

        if (codigo.isEmpty() || cantStr.isEmpty()) {
            vista.mostrarMensaje("Error: Ingrese el codigo del producto y la cantidad.");
            return;
        }

        try {
            int cantidad = Integer.parseInt(cantStr);
            if (cantidad <= 0) {
                vista.mostrarMensaje("Error: La cantidad debe ser mayor a cero.");
                return;
            }

            int resultado = modelo.registrarPedido(codigo, cantidad);
            if (resultado == 0) {
                vista.mostrarMensaje("Pedido generado exitosamente y stock actualizado.");
                vista.limpiarPed();
                refrescarVista();
            } else if (resultado == 1) {
                vista.mostrarMensaje("Error: El codigo de producto no esta registrado.");
            } else if (resultado == 2) {
                vista.mostrarMensaje("Error: Stock insuficiente para procesar la cantidad solicitada.");
            }
        } catch (NumberFormatException e) {
            vista.mostrarMensaje("Error: La cantidad debe ser un numero entero.");
        }
    }

    public void procesarDespacho() {
        Pedido despachado = modelo.despacharPedido();
        if (despachado != null) {
            vista.mostrarMensaje("Pedido despachado: " + despachado.obtenerNombreProducto() + " (Subtotal: $" + despachado.obtenerSubtotal() + ")");
            refrescarVista();
        } else {
            vista.mostrarMensaje("No hay pedidos pendientes por despachar.");
        }
    }

    public void refrescarVista() {
        vista.actualizarTablaProductos(modelo.obtenerProductos());
        vista.actualizarTablaPedidos(modelo.obtenerPedidos());
        vista.actualizarGanancia(modelo.obtenerGananciaTotal());
    }
}