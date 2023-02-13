package estg.ipp.pt.Utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.orgcom.District;

public final class Utils {
    /**
     * Construtor utilizado para privar a instanciação desta classe
     */
    private Utils() {
        throw new UnsupportedOperationException();
    }

    /**
     * Passa os objetos do iterador passado por parâmetro para um Array List
     *
     * @param <T>      Tipo dos objetos do iterador
     * @param iterator Iterador com os objetos
     * @return ArrayList do tipo indicado
     */
    public static <T> List<T> iteratorToArray(final Iterator<T> iterator) {
        final List<T> arrayList = new ArrayList<>();
        while (iterator.hasNext()) {
            final T item = iterator.next();
            if (item != null) {
                arrayList.add(item);
            }
        }
        return arrayList;
    }

    /**
     * Retorna um valor decimal e arredonda-o a 2 casas decimais
     *
     * @param value Valor a arrendondar
     * @return Valor decimal com 2 casas decimais
     */
    public static double TwoCasesDecimals(final double value) {
        return Math.round(value * 100.0) / 100.0;
    }

    /**
     * Converte um distrito recebido como String para um objeto do tipo District
     * (ENUM)
     *
     * @param label Distrito para ser convertido
     * @return Distrito convertido para um objeto do tipo District (ENUM)
     * @throws IllegalArgumentException Se o distrito não existir
     */
    public static District stringLabelToDistrict(final String label) throws IllegalArgumentException {
        for (int i = 0; i < District.values().length; i++) {
            if (District.values()[i].toString().equals(label)) {
                return District.values()[i];
            }
        }
        throw new IllegalArgumentException("Value not exist");
    }

    /**
     * Converte determinado objeto para um objeto do tipo Integer
     *
     * @param number Objeto a converter
     * @return Objeto convertido para Integer
     */
    public static Integer integerParseWithDefault(final Object number) {
        try {
            return Integer.parseInt(number.toString());
        } catch (NumberFormatException | NullPointerException e) {
            return null;
        }
    }

    /**
     * Converte determinado objeto para um objeto do tipo Double
     *
     * @param number Objeto a converter
     * @return Objeto convertido para Double
     */
    public static Double doubleParseWithDefault(final Object number) {
        try {
            return Double.parseDouble(number.toString());
        } catch (NumberFormatException | NullPointerException e) {
            return null;
        }
    }

    /**
     * Converte uma String para um objeto do tipo LocalDate
     *
     * @param date Data para ser convertida
     * @return Data convertida para um objeto do tipo LocalDate
     */
    public static LocalDate stringToLocalDate(final String date) {
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try {
            return LocalDate.parse(date, formatter);
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    /**
     * Valida o NIF
     * @param nif NIF a verificar
     * @return se o NIF é valido
     */
    public static boolean validaNif(String nif) {
        try {
            final int max = 9;
            if (!nif.matches("[0-9]+") || nif.length() != max) return false;
            int checkSum = 0;
            //calcula a soma de controlo
            for (int i = 0; i < max - 1; i++) {
                checkSum += (nif.charAt(i) - '0') * (max - i);
            }
            int checkDigit = 11 - (checkSum % 11);
            if (checkDigit >= 10) checkDigit = 0;
            return checkDigit == nif.charAt(max - 1) - '0';
        } catch (Exception e) {
            return false;
        }
    }
}
