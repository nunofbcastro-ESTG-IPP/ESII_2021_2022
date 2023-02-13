package estg.ipp.pt.Utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.InvalidPathException;
import java.nio.file.Paths;
import java.util.StringJoiner;
import java.nio.charset.StandardCharsets;

public final class Files {
    /**
     * Construtor utilizado para privar a instanciação desta classe
     */
    private Files() {
        throw new UnsupportedOperationException();
    }

    /**
     * Verifica se o caminho enviado é valido
     * 
     * @param path Caminho a enviar
     * @return True se o caminho é valido, Falso caso contrário
     */
    public static boolean isValidPath(final String path) {
        try {
            Paths.get(path);
        } catch (InvalidPathException | NullPointerException ex) {
            return false;
        }
        return true;
    }

    /**
     * Método responsável por ler um ficheiro
     *
     * @param nameFile Nome do ficheiro
     * @return As linhas do ficheiro
     */
    public static String readFile(final String nameFile) {
        final StringJoiner stringJoiner = new StringJoiner("\n");

        try {
            final BufferedReader br = new BufferedReader(new FileReader(nameFile, StandardCharsets.UTF_8));
            while (br.ready()) {
                stringJoiner.add(br.readLine());
            }
            br.close();
        } catch (IOException ignored) {
        }

        return stringJoiner.toString();
    }

    /**
     * Método responsável por ler um ficheiro
     *
     * @param nameFile Nome do ficheiro
     * @param Linha    Linha a adicionar no ficheiro
     * @return True caso seja adicionada a linha com sucesso, False caso
     *         contrário
     */
    public static boolean writeFile(final String nameFile, final String Linha) {
        try {
            final FileWriter fw = new FileWriter(nameFile, StandardCharsets.UTF_8, false);
            final BufferedWriter bw = new BufferedWriter(fw);

            bw.write(Linha);
            bw.newLine();
            bw.close();
            fw.close();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
        return true;
    }
}
