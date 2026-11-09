package cl.duoc.pataverde.model;

public class Perro extends Mascota implements Adoptable {
    private String raza;
    private boolean vacunado;
    private boolean disponibleAdopcion;

    public Perro(String nombre, int edad, double peso, String raza, boolean vacunado) {
        super(nombre, edad, peso);
        setRaza(raza);
        this.vacunado = vacunado;
        this.disponibleAdopcion = false;
    }

    public String getRaza() {
        return raza;
    }

    public void setRaza(String raza) {
        if (raza == null || raza.trim().isEmpty()) {
            throw new IllegalArgumentException("La raza no puede estar vacía.");
        }
        this.raza = raza;
    }

    public boolean isVacunado() {
        return vacunado;
    }

    public void setVacunado(boolean vacunado) {
        this.vacunado = vacunado;
    }

    @Override
    public void disponibleAdopcion() {
        this.disponibleAdopcion = true;
    }

    @Override
    public boolean isDisponibleAdopcion() {
        return disponibleAdopcion;
    }

    @Override
    public String getTipo() {
        return "Perro";
    }

    @Override
    public String getDatosEspecificos() {
        return String.format("Raza: %s | Vacunado: %s | Adopción: %s",
                raza,
                vacunado ? "sí" : "no",
                disponibleAdopcion ? "sí" : "no");
    }

    @Override
    public double calcularCostoConsulta() {
        double costoBase = 15000;
        if (!vacunado) {
            costoBase *= 1.30;
        }
        return costoBase;
    }
}

