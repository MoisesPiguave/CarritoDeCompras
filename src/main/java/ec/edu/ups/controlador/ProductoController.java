package ec.edu.ups.controlador;

import ec.edu.ups.dao.ProductoDAO;
import ec.edu.ups.modelo.Producto;
import ec.edu.ups.vista.ProductoAnadirView;
import ec.edu.ups.vista.ProductoListaView;
import ec.edu.ups.vista.GestionDeProducto;
import ec.edu.ups.vista.ActualizarProducto;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ProductoController {

    private final ProductoAnadirView productoAnadirView;
    private final ProductoListaView productoListaView;
    private final GestionDeProducto gestionDeProducto;
    private final ActualizarProducto actualizarProducto;
    private final ProductoDAO productoDAO;

    public ProductoController(ProductoDAO productoDAO,
                              ProductoAnadirView productoAnadirView,
                              ProductoListaView productoListaView,
                              GestionDeProducto gestionDeProducto,
                              ActualizarProducto actualizarProducto) {
        this.productoDAO = productoDAO;
        this.productoAnadirView = productoAnadirView;
        this.productoListaView = productoListaView;
        this.gestionDeProducto = gestionDeProducto;
        this.actualizarProducto = actualizarProducto;
        configurarEventos();
    }

    private void configurarEventos() {
        productoAnadirView.getBtnAceptar().addActionListener(e -> guardarProducto());

        productoListaView.getBtnBuscar().addActionListener(e -> buscarProductoPorNombre());

        productoListaView.getBtnListar().addActionListener(e -> listarProductos());

        gestionDeProducto.getBtnBuscar().addActionListener(e -> buscarProductoPorCodigoGestion());

        gestionDeProducto.getBtnEliminar().addActionListener(e -> eliminarProducto());

        actualizarProducto.getBtnBuscar().addActionListener(e -> buscarProductoPorCodigoActualizar());

        actualizarProducto.getBtnActualizar().addActionListener(e -> actualizarProducto());
    }

    private void guardarProducto() {
        int codigo = Integer.parseInt(productoAnadirView.getTxtCodigo().getText());
        String nombre = productoAnadirView.getTxtNombre().getText();
        double precio = Double.parseDouble(productoAnadirView.getTxtPrecio().getText());

        productoDAO.crear(new Producto(codigo, nombre, precio));
        productoAnadirView.mostrarMensaje("Producto guardado correctamente");
        productoAnadirView.limpiarCampos();
        productoAnadirView.mostrarProductos(productoDAO.listarTodos());
    }

    private void buscarProductoPorNombre() {
        String nombre = productoListaView.getTxtBuscar().getText();
        List<Producto> productosEncontrados = productoDAO.buscarPorNombre(nombre);
        productoListaView.cargarDatos(productosEncontrados);
    }

    private void listarProductos() {
        List<Producto> productos = productoDAO.listarTodos();
        productoListaView.cargarDatos(productos);
    }

    private void buscarProductoPorCodigoGestion() {
        int codigo = Integer.parseInt(gestionDeProducto.getTxtBuscar().getText());
        Producto producto = productoDAO.buscarPorCodigo(codigo);
        gestionDeProducto.mostrarProducto(producto);
    }

    private void eliminarProducto() {
        int filaSeleccionada = gestionDeProducto.getTblProductos().getSelectedRow();
        if (filaSeleccionada != -1) {
            int codigo = (int) gestionDeProducto.getModelo().getValueAt(filaSeleccionada, 0);
            productoDAO.eliminar(codigo);
            JOptionPane.showMessageDialog(null, "Producto eliminado");
            gestionDeProducto.mostrarProducto(null);
        } else {
            JOptionPane.showMessageDialog(null, "Seleccione un producto para eliminar");
        }
    }

    private void buscarProductoPorCodigoActualizar() {
        int codigo = Integer.parseInt(actualizarProducto.getTxtBuscar().getText());
        Producto producto = productoDAO.buscarPorCodigo(codigo);
        if (producto != null) {
            actualizarProducto.getTxtNombre().setText(producto.getNombre());
            actualizarProducto.getTxtPrecio().setText(String.valueOf(producto.getPrecio()));
        } else {
            JOptionPane.showMessageDialog(null, "Producto no encontrado");
        }
    }

    private void actualizarProducto() {
        int codigo = Integer.parseInt(actualizarProducto.getTxtBuscar().getText());
        String nuevoNombre = actualizarProducto.getTxtNombre().getText();
        double nuevoPrecio = Double.parseDouble(actualizarProducto.getTxtPrecio().getText());

        Producto producto = productoDAO.buscarPorCodigo(codigo);
        if (producto != null) {
            producto.setNombre(nuevoNombre);
            producto.setPrecio(nuevoPrecio);
            productoDAO.actualizar(producto);
            JOptionPane.showMessageDialog(null, "Producto actualizado");
        } else {
            JOptionPane.showMessageDialog(null, "Producto no encontrado para actualizar");
        }
    }
}
