package cl.duoc.pataverde.model;

public class Gato extends Mascota {
    private boolean exterior;

    public Gato(String nombre, int edad, double peso, boolean exterior) {
        super(nombre, edad, peso);
        this.exterior = exterior;
    }

    public boolean isExterior() {
        return exterior;
    }

    public void setExterior(boolean exterior) {
        this.exterior = exterior;
    }

    @Override
    public String getTipo() {
        return "Gato";
    }

    @Override
    public String getDatosEspecificos() {
        return String.format("Exterior: %s", exterior ? "sí" : "no");
    }

    @Override
    public double calcularCostoConsulta() {
        double costoBase = 12000;
        if (exterior) {
            costoBase *= 1.15;
        }
        return costoBase;
    }
}

