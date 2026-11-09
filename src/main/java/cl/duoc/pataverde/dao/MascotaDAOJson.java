package cl.duoc.pataverde.dao;

import cl.duoc.pataverde.model.Gato;
import cl.duoc.pataverde.model.Mascota;
import cl.duoc.pataverde.model.Perro;
import com.google.gson.Gson;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MascotaDAOJson implements MascotaDAO {

    private final Gson gson = new Gson();

    @Override
    public void guardar(List<Mascota> listaMascotas) {
        List<Perro> listaPerros = new ArrayList<>();
        List<Gato> listaGatos = new ArrayList<>();

        for (Mascota mascotaActual : listaMascotas) {
            if ("Perro".equals(mascotaActual.getTipo())) {
                listaPerros.add((Perro) mascotaActual);
            } else if ("Gato".equals(mascotaActual.getTipo())) {
                listaGatos.add((Gato) mascotaActual);
            }
        }

        try {
            PrintWriter escritorPerros = new PrintWriter("perros.json");
            escritorPerros.print(gson.toJson(listaPerros));
            escritorPerros.close();

            PrintWriter escritorGatos = new PrintWriter("gatos.json");
            escritorGatos.print(gson.toJson(listaGatos));
            escritorGatos.close();
        } catch (Exception e) { }
    }

    @Override
    public List<Mascota> obtenerTodas() {
        List<Mascota> listaCompleta = new ArrayList<>();

        File archivoPerros = new File("perros.json");
        if (archivoPerros.exists()) {
            try {
                Scanner lector = new Scanner(archivoPerros);
                String textoJson = "";
                while (lector.hasNextLine()) {
                    textoJson += lector.nextLine();
                }
                lector.close();

                Perro[] arregloPerros = gson.fromJson(textoJson, Perro[].class);
                if (arregloPerros != null) {
                    for (Perro perro : arregloPerros) {
                        listaCompleta.add(perro);
                    }
                }
            } catch (Exception e) { }
        }

        File archivoGatos = new File("gatos.json");
        if (archivoGatos.exists()) {
            try {
                Scanner lector = new Scanner(archivoGatos);
                String textoJson = "";
                while (lector.hasNextLine()) {
                    textoJson += lector.nextLine();
                }
                lector.close();

                Gato[] arregloGatos = gson.fromJson(textoJson, Gato[].class);
                if (arregloGatos != null) {
                    for (Gato gato : arregloGatos) {
                        listaCompleta.add(gato);
                    }
                }
            } catch (Exception e) { }
        }

        return listaCompleta;
    }
}
