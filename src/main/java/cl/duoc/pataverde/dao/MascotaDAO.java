package cl.duoc.pataverde.dao;

import cl.duoc.pataverde.model.Mascota;
import java.util.List;

public interface MascotaDAO {
    void guardar(List<Mascota> listaMascotas);
    List<Mascota> obtenerTodas();
}



