package ec.edu.ups.dao.impl;
import ec.edu.ups.dao.CarritoDAO;
import ec.edu.ups.modelo.Carrito;
import java.util.ArrayList;
import java.util.List;

public class CarritoDAOImpl implements CarritoDAO {

    private List<Carrito> carritoList = new ArrayList<>();

    @Override
    public void crear(Carrito carrito) {
        carritoList.add(carrito);
    }

    @Override
    public Carrito buscarPorCodigo(int codigo) {
        for (Carrito carrito : carritoList) {
            if (carrito.getCodigo() == codigo) {
                return carrito;
            }
        }
        return null;
    }

    @Override
    public void actualizar(Carrito carrito) {
        for (int i = 0; i < carritoList.size(); i++) {
            if (carritoList.get(i).getCodigo() == carrito.getCodigo()) {
                carritoList.set(i, carrito);
                break;
            }
        }
    }

    @Override
    public void eliminar(int codigo) {
        carritoList.removeIf(carrito -> carrito.getCodigo() == codigo);
    }

    @Override
    public List<Carrito> listarTodos() {
        return new ArrayList<>(carritoList);
    }
}
