public class AlgoritmosOrdenacao {

    // ==================== BUBBLE SORT ====================
    // Compara os elementos um a um e, se estiverem fora de ordem, troca de posição

    public static void bubbleSortPorNome(Prato[] pratos) {
        // Pega o tamanho do vetor de pratos
        int n = pratos.length;

        // Repete o processo várias vezes
        for (int i = 0; i < n - 1; i++) {
            // Compara os pratos que estão lado a lado
            for (int j = 0; j < n - i - 1; j++) {
                // Verifica se o nome do prato atual é maior (alfabeticamente) do que o próximo
                if (pratos[j].getNome().compareToIgnoreCase(pratos[j + 1].getNome()) > 0) {
                    // Troca os pratos de posição
                    trocarPratos(pratos, j, j + 1);
                }
            }
        }
    }

    public static void bubbleSortPorPreco(Prato[] pratos) {
        int n = pratos.length;

        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (pratos[j].getPreco() > pratos[j + 1].getPreco()) {
                    trocarPratos(pratos, j, j + 1);
                }
            }
        }
    }

    public static void bubbleSortPorTempo(Prato[] pratos) {
        int n = pratos.length;

        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (pratos[j].getTempoPreparo() > pratos[j + 1].getTempoPreparo()) {
                    trocarPratos(pratos, j, j + 1);
                }
            }
        }
    }

    // ==================== INSERTION SORT ====================
    // Insere cada elemento no lugar certo

    public static void insertionSortPorNome(Prato[] pratos) {
        // Pega o tamanho do vetor de pratos
        int n = pratos.length;

        // Começa do segundo prato, pois o primeiro já é considerado "ordenado"
        for (int i = 1; i < n; i++) {
            // Prato que vai ser inserido no lugar certo
            Prato chave = pratos[i];
            // Compara com o elemento anterior
            int j = i - 1;

            // Verifica enquanto não chegar no início e o nome anterior for maior (alfabeticamente)
            while (j >= 0 && pratos[j].getNome().compareToIgnoreCase(chave.getNome()) > 0) {
                // Move o prato anterior uma posição pra frente
                pratos[j + 1] = pratos[j];
                // Volta uma posição pra continuar comparando
                j--;
            }

            // Coloca o prato na posição certa quando achá-la
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
    // Escolhe um pivô e divide o array em duas partes: esquerda (elementos menores) e direita (elementos maiores)
    // Repete o processo em cada parte

    public static void quickSortPorNome(Prato[] pratos) {
        // Inicia o Quick Sort para todo o vetor, do primeiro ao último elemento
        quickSortNome(pratos, 0, pratos.length - 1);
    }

    private static void quickSortNome(Prato[] pratos, int inicio, int fim) {
        // Verifica se há mais de um elemento
        if (inicio < fim) {
            // Escolhe um pivô e organiza o vetor em torno dele
            int pivo = particionarNome(pratos, inicio, fim);
            // Ordena parte esquerda
            quickSortNome(pratos, inicio, pivo - 1);
            // Ordena parte direita
            quickSortNome(pratos, pivo + 1, fim);
        }
    }

    private static int particionarNome(Prato[] pratos, int inicio, int fim) {
        // Pivô é o último elemento
        Prato pivo = pratos[fim];
        // Marca a posição para trocar elementos menores que o pivô
        int i = inicio - 1;

        // Percorre os elementos do início até antes do pivô
        for (int j = inicio; j < fim; j++) {
            // Verifica se o prato for menor ou igual ao pivô
            if (pratos[j].getNome().compareToIgnoreCase(pivo.getNome()) <= 0) {
                // Avança a posição
                i++;
                // Coloca o elemento menor à esquerda do pivô
                trocarPratos(pratos, i, j);
            }
        }

        // Coloca o pivô no lugar certo
        trocarPratos(pratos, i + 1, fim);
        // Retorna a posição do pivô
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
                trocarPratos(pratos, i, j);
            }
        }

        trocarPratos(pratos, i + 1, fim);
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
                trocarPratos(pratos, i, j);
            }
        }

        trocarPratos(pratos, i + 1, fim);
        return i + 1;
    }

    // ==================== MÉTODO AUXILIAR ====================

    // Troca dois pratos de posição no array
    private static void trocarPratos(Prato[] pratos, int i, int j) {
        // Guarda temporariamente o prato que está na posição i
        Prato temp = pratos[i];
        // Coloca o prato da posição j no lugar do i
        pratos[i] = pratos[j];
        // Coloca o prato que estava guardado (temp) na posição j
        pratos[j] = temp;
    }

}