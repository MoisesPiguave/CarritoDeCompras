package ec.edu.ups.controlador;

import ec.edu.ups.dao.CarritoDAO;
import ec.edu.ups.modelo.Carrito;
import ec.edu.ups.modelo.ItemCarrito;
import ec.edu.ups.modelo.Producto;
import ec.edu.ups.vista.CarritoAnadirView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CarritoController {

    private final CarritoDAO carritoDAO;
    private final CarritoAnadirView carritoAnadirView;
    private final Carrito carritoActual;

    private DefaultTableModel modelo;

    public CarritoController(CarritoDAO carritoDAO, CarritoAnadirView carritoAnadirView) {
        this.carritoDAO = carritoDAO;
        this.carritoAnadirView = carritoAnadirView;
        this.carritoActual = new Carrito();
        inicializar();
        configurarEventos();
    }

    private void inicializar() {
        modelo = new DefaultTableModel();
        modelo.addColumn("Código");
        modelo.addColumn("Nombre");
        modelo.addColumn("Precio");
        modelo.addColumn("Cantidad");
        modelo.addColumn("Subtotal");
        carritoAnadirView.getTblProductos().setModel(modelo);

        cargarCantidadCombo();
    }

    private void cargarCantidadCombo() {
        carritoAnadirView.getCbxCantidad().removeAllItems();
        for (int i = 1; i <= 20; i++) {
            carritoAnadirView.getCbxCantidad().addItem(String.valueOf(i));
        }
    }

    private void configurarEventos() {
        carritoAnadirView.getBtnAnadir().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarItemAlCarrito();
            }
        });

        carritoAnadirView.getBtnGuardar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarCarrito();
            }
        });

        carritoAnadirView.getBtnLimpiar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limpiarCarrito();
            }
        });
    }

    private void agregarItemAlCarrito() {
        try {
            int codigo = Integer.parseInt(carritoAnadirView.getTxtCodigo().getText());
            String nombre = carritoAnadirView.getTxtNombre().getText();
            double precio = Double.parseDouble(carritoAnadirView.getTxtPrecio().getText().replace(",", "."));
            int cantidad = Integer.parseInt(carritoAnadirView.getCbxCantidad().getSelectedItem().toString());

            if (nombre.isEmpty()) {
                carritoAnadirView.mostrarMensaje("El nombre del producto no puede estar vacío.");
                return;
            }
            if (precio <= 0) {
                carritoAnadirView.mostrarMensaje("El precio debe ser mayor a 0.");
                return;
            }
            if (cantidad <= 0) {
                carritoAnadirView.mostrarMensaje("La cantidad debe ser mayor a 0.");
                return;
            }

            Producto producto = new Producto(codigo, nombre, precio);
            carritoActual.agregarProducto(producto, cantidad);

            double subtotal = precio * cantidad;

            modelo.addRow(new Object[]{
                    codigo,
                    nombre,
                    String.format("%.2f", precio),
                    cantidad,
                    String.format("%.2f", subtotal)
            });

            calcularTotales();

            limpiarCampos();

        } catch (NumberFormatException ex) {
            carritoAnadirView.mostrarMensaje("Datos inválidos. Verifique los campos.");
        }
    }

    private void calcularTotales() {
        double subtotal = 0;
        for (int i = 0; i < modelo.getRowCount(); i++) {
            subtotal += Double.parseDouble(modelo.getValueAt(i, 4).toString());
        }
        double iva = subtotal * 0.12;
        double total = subtotal + iva;

        carritoAnadirView.getTxtSubtotal().setText(String.format("%.2f", subtotal));
        carritoAnadirView.getTxtIva().setText(String.format("%.2f", iva));
        carritoAnadirView.getTxtTotal().setText(String.format("%.2f", total));
    }

    private void limpiarCampos() {
        carritoAnadirView.getTxtCodigo().setText("");
        carritoAnadirView.getTxtNombre().setText("");
        carritoAnadirView.getTxtPrecio().setText("");
        carritoAnadirView.getCbxCantidad().setSelectedIndex(0);
    }

    private void limpiarCarrito() {
        modelo.setRowCount(0);
        carritoActual.vaciarCarrito();
        carritoAnadirView.getTxtSubtotal().setText("");
        carritoAnadirView.getTxtIva().setText("");
        carritoAnadirView.getTxtTotal().setText("");
        limpiarCampos();
    }

    private void guardarCarrito() {
        if (modelo.getRowCount() == 0) {
            carritoAnadirView.mostrarMensaje("No hay productos en el carrito para guardar.");
            return;
        }
        carritoDAO.crear(carritoActual);
        carritoAnadirView.mostrarMensaje("Carrito guardado exitosamente.");
        limpiarCarrito();
    }
}
