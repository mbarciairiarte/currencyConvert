import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class ConversorMonedas {
    private static final List<String> MONEDAS_PERMITIDAS = Arrays.asList("USD", "ARS", "BRL", "COP");

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        GeneradorDeArchivo generador = new GeneradorDeArchivo();

        System.out.println("Bienvenido al conversor de monedas");

        boolean continuar = true;
        while (continuar) {
            try {
                System.out.println("Monedas permitidas: USD, ARS, BRL, COP");
                System.out.println("Por favor ingrese el monto a convertir:");
                double monto = scanner.nextDouble();
                scanner.nextLine(); // Limpiar el buffer

                System.out.println("Ingrese la moneda de origen (ej: USD, ARS, BRL, COP):");
                String monedaOrigen = scanner.nextLine().toUpperCase();

                System.out.println("Ingrese la moneda de destino (ej: USD, ARS, BRL, COP):");
                String monedaDestino = scanner.nextLine().toUpperCase();

                if (!MONEDAS_PERMITIDAS.contains(monedaOrigen) || !MONEDAS_PERMITIDAS.contains(monedaDestino)) {
                    System.out.println("Moneda no permitida. Las monedas permitidas son: USD, ARS, BRL, COP");
                    continue; // Vuelve al inicio del bucle
                }

                Moneda resultado = ConsultorMonedasService.consultarTasaDeCambio(monedaOrigen);
                if (resultado != null) {
                    double tasaCambio = resultado.getConversion_rates().getOrDefault(monedaDestino, -1.0);
                    if (tasaCambio != -1.0) {
                        double montoConvertido = monto * tasaCambio;
                        System.out.printf("%.2f %s equivale a %.2f %s%n", monto, monedaOrigen, montoConvertido, monedaDestino);

                        // Crear objeto ConversorMoneda y guardarlo en archivo JSON
                        String resultadoString = String.format("%.2f %s equivale a %.2f %s", monto, monedaOrigen, montoConvertido, monedaDestino);
                        ConversorMoneda conversorMoneda = new ConversorMoneda(monto, monedaOrigen, monedaDestino, montoConvertido);
                        generador.guardarConversion(conversorMoneda);

                        System.out.println("La conversión ha sido guardada en el archivo JSON.");
                    } else {
                        System.out.println("No se encontró la tasa de cambio para la moneda de destino.");
                        // Guardar error en archivo JSON
                        String errorString = "No se encontró la tasa de cambio para " + monedaDestino;
                        ConversorMoneda errorConversorMoneda = new ConversorMoneda(monto, monedaOrigen, monedaDestino, 0.0);
                        generador.guardarConversion(errorConversorMoneda);
                    }
                } else {
                    System.out.println("No se pudo obtener la tasa de cambio.");
                    // Guardar error en archivo JSON
                    String errorString = "No se pudo obtener la tasa de cambio para " + monedaOrigen;
                    ConversorMoneda errorConversorMoneda = new ConversorMoneda(monto, monedaOrigen, monedaDestino, 0.0);
                    generador.guardarConversion(errorConversorMoneda);
                }

            } catch (InputMismatchException e) {
                System.out.println("Error: Ingrese un monto válido.");
                scanner.nextLine(); // Limpiar el buffer después del error
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }

            System.out.println("¿Desea hacer otra conversión? (S/N)");
            String respuesta = scanner.nextLine();
            if (!respuesta.equalsIgnoreCase("S")) {
                continuar = false;
            }
        }

        System.out.println("Gracias por usar el conversor de monedas. ¡Hasta luego!");
        scanner.close();
    }
}
