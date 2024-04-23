import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GeneradorDeArchivo {
    private static final String JSON_FILE_PATH = "transacciones.json";

    public void guardarConversion(ConversorMoneda conversorMoneda) {
        List<ConversorMoneda> transacciones = leerTransacciones();

        transacciones.add(conversorMoneda);

        try (Writer writer = new FileWriter(JSON_FILE_PATH)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(transacciones, writer);
        } catch (IOException e) {
            System.err.println("Error al guardar la conversión en el archivo JSON: " + e.getMessage());
        }
    }

    private List<ConversorMoneda> leerTransacciones() {
        List<ConversorMoneda> transacciones = new ArrayList<>();
        try (Reader reader = new FileReader(JSON_FILE_PATH)) {
            Gson gson = new Gson();
            ConversorMoneda[] transaccionesArray = gson.fromJson(reader, ConversorMoneda[].class);
            if (transaccionesArray != null) {
                for (ConversorMoneda transaccion : transaccionesArray) {
                    transacciones.add(transaccion);
                }
            }
        } catch (IOException e) {
            System.err.println("Error al leer las transacciones del archivo JSON: " + e.getMessage());
        }
        return transacciones;
    }
}
