package View;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import Model.Pedido;
import Model.Producto;

public class VentanaCafeteria extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField txtCodProd, txtNombreProd, txtPrecioProd, txtStockProd;
    private JTextField txtCodPedido, txtCantPedido;
    private JLabel lblGanancia;
    private JButton btnRegProd, btnRegPedido, btnDespachar;
    private JTable tablaProductos;
    private DefaultTableModel modeloTablaProductos;
    private JTable tablaPedidos;
    private DefaultTableModel modeloTablaPedidos;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    VentanaCafeteria frame = new VentanaCafeteria();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public VentanaCafeteria() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 850, 650);
        setLocationRelativeTo(null);
        setTitle("Sistema de Gestion de Cafeteria - Inventario y Pedidos");

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane);

        // Panel superior para formularios
        JPanel panelFormularios = new JPanel(new GridLayout(1, 2, 5, 5));

        JPanel panelProd = new JPanel(new GridLayout(5, 2, 3, 3));
        panelProd.setBorder(BorderFactory.createTitledBorder("Registrar Producto"));
        panelProd.add(new JLabel("Codigo:"));
        txtCodProd = new JTextField();
        panelProd.add(txtCodProd);
        panelProd.add(new JLabel("Nombre:"));
        txtNombreProd = new JTextField();
        panelProd.add(txtNombreProd);
        panelProd.add(new JLabel("Precio:"));
        txtPrecioProd = new JTextField();
        panelProd.add(txtPrecioProd);
        panelProd.add(new JLabel("Stock:"));
        txtStockProd = new JTextField();
        panelProd.add(txtStockProd);
        btnRegProd = new JButton("Guardar Producto");
        panelProd.add(btnRegProd);

        JPanel panelPed = new JPanel(new GridLayout(4, 2, 3, 3));
        panelPed.setBorder(BorderFactory.createTitledBorder("Generar Pedido"));
        panelPed.add(new JLabel("Cod Producto:"));
        txtCodPedido = new JTextField();
        panelPed.add(txtCodPedido);
        panelPed.add(new JLabel("Cantidad:"));
        txtCantPedido = new JTextField();
        panelPed.add(txtCantPedido);
        btnRegPedido = new JButton("Crear Pedido");
        panelPed.add(btnRegPedido);
        btnDespachar = new JButton("Despachar Siguiente");
        panelPed.add(btnDespachar);

        panelFormularios.add(panelProd);
        panelFormularios.add(panelPed);
        contentPane.add(panelFormularios, BorderLayout.NORTH);

        // Panel central con tablas de Productos y Pedidos
        JPanel panelTablas = new JPanel(new GridLayout(2, 1, 5, 5));

        modeloTablaProductos = new DefaultTableModel(new String[]{"Codigo", "Nombre", "Precio ($)", "Stock Disponible"}, 0);
        tablaProductos = new JTable(modeloTablaProductos);
        JScrollPane scrollProd = new JScrollPane(tablaProductos);
        scrollProd.setBorder(BorderFactory.createTitledBorder("Catalogo de Productos y Stock"));
        panelTablas.add(scrollProd);

        modeloTablaPedidos = new DefaultTableModel(new String[]{"ID Pedido", "Producto", "Cantidad", "Subtotal ($)"}, 0);
        tablaPedidos = new JTable(modeloTablaPedidos);
        JScrollPane scrollPed = new JScrollPane(tablaPedidos);
        scrollPed.setBorder(BorderFactory.createTitledBorder("Cola de Pedidos Pendientes"));
        panelTablas.add(scrollPed);

        contentPane.add(panelTablas, BorderLayout.CENTER);

        // Panel inferior para ganancias
        JPanel panelInferior = new JPanel();
        lblGanancia = new JLabel("Ganancia Total: $0.00");
        panelInferior.add(lblGanancia);
        contentPane.add(panelInferior, BorderLayout.SOUTH);
    }

    public String obtenerCodProd() { return txtCodProd.getText().trim(); }
    public String obtenerNombreProd() { return txtNombreProd.getText().trim(); }
    public String obtenerPrecioProd() { return txtPrecioProd.getText().trim(); }
    public String obtenerStockProd() { return txtStockProd.getText().trim(); }
    public String obtenerCodPedido() { return txtCodPedido.getText().trim(); }
    public String obtenerCantPedido() { return txtCantPedido.getText().trim(); }

    public void limpiarProd() {
        txtCodProd.setText("");
        txtNombreProd.setText("");
        txtPrecioProd.setText("");
        txtStockProd.setText("");
    }

    public void limpiarPed() {
        txtCodPedido.setText("");
        txtCantPedido.setText("");
    }

    public void mostrarMensaje(String m) {
        JOptionPane.showMessageDialog(this, m);
    }

    public void actualizarGanancia(double total) {
        lblGanancia.setText(String.format("Ganancia Total: $%.2f", total));
    }

    public void asignarListeners(ActionListener listener) {
        btnRegProd.addActionListener(listener);
        btnRegPedido.addActionListener(listener);
        btnDespachar.addActionListener(listener);
        btnRegProd.setActionCommand("REG_PROD");
        btnRegPedido.setActionCommand("REG_PEDIDO");
        btnDespachar.setActionCommand("DESPACHAR");
    }

    public void actualizarTablaProductos(List<Producto> lista) {
        modeloTablaProductos.setRowCount(0);
        for (Producto p : lista) {
            modeloTablaProductos.addRow(new Object[]{
                p.obtenerCodigo(),
                p.obtenerNombre(),
                p.obtenerPrecio(),
                p.obtenerStock()
            });
        }
    }

    public void actualizarTablaPedidos(List<Pedido> lista) {
        modeloTablaPedidos.setRowCount(0);
        for (Pedido p : lista) {
            modeloTablaPedidos.addRow(new Object[]{
                p.obtenerIdPedido(),
                p.obtenerNombreProducto(),
                p.obtenerCantidad(),
                p.obtenerSubtotal()
            });
        }
    }
}