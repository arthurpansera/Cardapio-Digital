public class AlgoritmosOrdenacao {

    // ==================== BUBBLE SORT ====================

    public static void bubbleSortPorNome(Prato[] pratos) {
        int n = pratos.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (pratos[j].getNome().compareToIgnoreCase(pratos[j + 1].getNome()) > 0) {
                    trocar(pratos, j, j + 1);
                }
            }
        }
    }

    public static void bubbleSortPorPreco(Prato[] pratos) {
        int n = pratos.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (pratos[j].getPreco() > pratos[j + 1].getPreco()) {
                    trocar(pratos, j, j + 1);
                }
            }
        }
    }

    public static void bubbleSortPorTempo(Prato[] pratos) {
        int n = pratos.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (pratos[j].getTempoPreparo() > pratos[j + 1].getTempoPreparo()) {
                    trocar(pratos, j, j + 1);
                }
            }
        }
    }

    // ==================== INSERTION SORT ====================

    public static void insertionSortPorNome(Prato[] pratos) {
        int n = pratos.length;
        for (int i = 1; i < n; i++) {
            Prato chave = pratos[i];
            int j = i - 1;

            while (j >= 0 && pratos[j].getNome().compareToIgnoreCase(chave.getNome()) > 0) {
                pratos[j + 1] = pratos[j];
                j--;
            }
            pratos[j + 1] = chave;
        }
    }

    public static void insertionSortPorPreco(Prato[] pratos) {
        int n = pratos.length;
        for (int i = 1; i < n; i++) {
            Prato chave = pratos[i];
            int j = i - 1;

            while (j >= 0 && pratos[j].getPreco() > chave.getPreco()) {
                pratos[j + 1] = pratos[j];
                j--;
            }
            pratos[j + 1] = chave;
        }
    }

    public static void insertionSortPorTempo(Prato[] pratos) {
        int n = pratos.length;
        for (int i = 1; i < n; i++) {
            Prato chave = pratos[i];
            int j = i - 1;

            while (j >= 0 && pratos[j].getTempoPreparo() > chave.getTempoPreparo()) {
                pratos[j + 1] = pratos[j];
                j--;
            }
            pratos[j + 1] = chave;
        }
    }

    // ==================== QUICK SORT ====================

    public static void quickSortPorNome(Prato[] pratos) {
        quickSortNome(pratos, 0, pratos.length - 1);
    }

    private static void quickSortNome(Prato[] pratos, int inicio, int fim) {
        if (inicio < fim) {
            int pivo = particionarNome(pratos, inicio, fim);
            quickSortNome(pratos, inicio, pivo - 1);
            quickSortNome(pratos, pivo + 1, fim);
        }
    }

    private static int particionarNome(Prato[] pratos, int inicio, int fim) {
        Prato pivo = pratos[fim];
        int i = inicio - 1;

        for (int j = inicio; j < fim; j++) {
            if (pratos[j].getNome().compareToIgnoreCase(pivo.getNome()) <= 0) {
                i++;
                trocar(pratos, i, j);
            }
        }
        trocar(pratos, i + 1, fim);
        return i + 1;
    }

    public static void quickSortPorPreco(Prato[] pratos) {
        quickSortPreco(pratos, 0, pratos.length - 1);
    }

    private static void quickSortPreco(Prato[] pratos, int inicio, int fim) {
        if (inicio < fim) {
            int pivo = particionarPreco(pratos, inicio, fim);
            quickSortPreco(pratos, inicio, pivo - 1);
            quickSortPreco(pratos, pivo + 1, fim);
        }
    }

    private static int particionarPreco(Prato[] pratos, int inicio, int fim) {
        Prato pivo = pratos[fim];
        int i = inicio - 1;

        for (int j = inicio; j < fim; j++) {
            if (pratos[j].getPreco() <= pivo.getPreco()) {
                i++;
                trocar(pratos, i, j);
            }
        }
        trocar(pratos, i + 1, fim);
        return i + 1;
    }

    public static void quickSortPorTempo(Prato[] pratos) {
        quickSortTempo(pratos, 0, pratos.length - 1);
    }

    private static void quickSortTempo(Prato[] pratos, int inicio, int fim) {
        if (inicio < fim) {
            int pivo = particionarTempo(pratos, inicio, fim);
            quickSortTempo(pratos, inicio, pivo - 1);
            quickSortTempo(pratos, pivo + 1, fim);
        }
    }

    private static int particionarTempo(Prato[] pratos, int inicio, int fim) {
        Prato pivo = pratos[fim];
        int i = inicio - 1;

        for (int j = inicio; j < fim; j++) {
            if (pratos[j].getTempoPreparo() <= pivo.getTempoPreparo()) {
                i++;
                trocar(pratos, i, j);
            }
        }
        trocar(pratos, i + 1, fim);
        return i + 1;
    }

    // ==================== MÉTODO AUXILIAR ====================

    private static void trocar(Prato[] pratos, int i, int j) {
        Prato temp = pratos[i];
        pratos[i] = pratos[j];
        pratos[j] = temp;
    }

}