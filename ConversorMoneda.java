public class ConversorMoneda {
    private double monto;
    private String monedaOrigen;
    private String monedaDestino;
    private double montoConvertido;

    public ConversorMoneda(double monto, String monedaOrigen, String monedaDestino, double montoConvertido) {
        this.monto = monto;
        this.monedaOrigen = monedaOrigen;
        this.monedaDestino = monedaDestino;
        this.montoConvertido = montoConvertido;
    }

    public double getMonto() {
        return monto;
    }

    public String getMonedaOrigen() {
        return monedaOrigen;
    }

    public String getMonedaDestino() {
        return monedaDestino;
    }

    public double getMontoConvertido() {
        return montoConvertido;
    }
}
