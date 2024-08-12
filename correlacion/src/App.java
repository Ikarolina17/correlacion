import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class App {
    public static void main(String[] args) {
        
        String profitFile = "C:\\Users\\IrlaK\\Desktop\\startupprofit.txt";
        int[] columnas = {1, 2, 3, 5};

        // Listas para almacenar las columnas seleccionadas
        List<double[]> columnasSeleccionadas = new ArrayList<>();
        for (int i = 0; i < columnas.length; i++) {
            columnasSeleccionadas.add(new double[0]);
        }

        try (BufferedReader br = new BufferedReader(new FileReader(profitFile))) {
            String linea;
            boolean esPrimeraLinea = true;

            while ((linea = br.readLine()) != null) {
                if (esPrimeraLinea) {
                    esPrimeraLinea = false; 
                    continue;
                }

                String[] valores = linea.split(",");

                for (int i = 0; i < columnas.length; i++) {
                    int columna = columnas[i] - 1;
                    if (columna < valores.length && esNumerico(valores[columna])) {
                        double valor = Double.parseDouble(valores[columna]);
                        columnasSeleccionadas.set(i, appendValue(columnasSeleccionadas.get(i), valor));
                    }
                }
                System.out.println(linea);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (columnasSeleccionadas.size() >= 4) {
            double[] columna1 = columnasSeleccionadas.get(0);
            double[] columna2 = columnasSeleccionadas.get(1);
            double[] columna3 = columnasSeleccionadas.get(2);
            double[] columna5 = columnasSeleccionadas.get(3);

            double correlation1 = Correlation.pearsonCorrelation(columna1, columna5);
            double correlation2 = Correlation.pearsonCorrelation(columna2, columna5);
            double correlation3 = Correlation.pearsonCorrelation(columna3, columna5);

            System.out.println("Correlación entre columna 1 y 5: " + correlation1);
            System.out.println("Correlación entre columna 2 y 5: " + correlation2);
            System.out.println("Correlación entre columna 3 y 5: " + correlation3);
        } else {
            System.out.println("No hay suficientes columnas para calcular las correlaciones.");
        }
    }

    private static double[] appendValue(double[] array, double value) {
        double[] newArray = new double[array.length + 1];
        System.arraycopy(array, 0, newArray, 0, array.length);
        newArray[array.length] = value;
        return newArray;
    }

    private static boolean esNumerico(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static class Correlation {

        public static double mean(double[] data) {
            double sum = 0.0;
            for (double a : data) {
                sum += a;
            }
            return sum / data.length;
        }

        public static double pearsonCorrelation(double[] x, double[] y) {
            double meanX = mean(x);
            double meanY = mean(y);

            double sumXY = 0.0;
            double sumX2 = 0.0;
            double sumY2 = 0.0;

            for (int i = 0; i < x.length; i++) {
                double diffX = x[i] - meanX;
                double diffY = y[i] - meanY;

                sumXY += diffX * diffY;
                sumX2 += diffX * diffX;
                sumY2 += diffY * diffY;
            }

            return sumXY / Math.sqrt(sumX2 * sumY2);
        }
    }
}