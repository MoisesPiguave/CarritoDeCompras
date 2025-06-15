package ec.edu.ups.vista;

import ec.edu.ups.controlador.ProductoController;
import ec.edu.ups.dao.ProductoDAO;
import ec.edu.ups.dao.impl.ProductoDAOMemoria;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ProductoAnadirView productoAnadirView = new ProductoAnadirView();
            ProductoListaView productoListaView = new ProductoListaView();
            GestionDeProducto gestionDeProducto = new GestionDeProducto();
            ActualizarProducto actualizarProducto = new ActualizarProducto();

            productoAnadirView.setVisible(true);
            productoListaView.setVisible(true);
            gestionDeProducto.setVisible(true);
            actualizarProducto.setVisible(true);

            ProductoDAO productoDAO = new ProductoDAOMemoria();

            new ProductoController(productoDAO, productoAnadirView, productoListaView, gestionDeProducto, actualizarProducto);
        });
    }
}

