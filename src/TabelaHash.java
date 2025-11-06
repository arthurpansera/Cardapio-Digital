import java.util.LinkedList;

public class TabelaHash {

    private static final int TAMANHO_INICIAL = 16;
    private static final double FATOR_CARGA = 0.75;

    private LinkedList<Prato>[] tabela;
    private int tamanho;
    private int numeroElementos;

    public TabelaHash() {
        this.tamanho = TAMANHO_INICIAL;
        this.tabela = new LinkedList[tamanho];
        this.numeroElementos = 0;
        
        // Inicializa cada posição do vetor tabela com uma nova lista vazia
        for (int i = 0; i < tamanho; i++) {
            tabela[i] = new LinkedList<>();
        }
    }

    public LinkedList<Prato>[] getTabela() {
        return tabela;
    }

    public void setTabela(LinkedList<Prato>[] tabela) {
        this.tabela = tabela;
    }

    public int getTamanho() {
        return tamanho;
    }

    public void setTamanho(int tamanho) {
        this.tamanho = tamanho;
    }

    public int getNumeroElementos() {
        return numeroElementos;
    }

    public void setNumeroElementos(int numeroElementos) {
        this.numeroElementos = numeroElementos;
    }

    // Gera um índice válido na tabela a partir da chave informada (entre 0 e tamanho-1)
    private int gerarHash(String chave) {
        return Math.abs(chave.toLowerCase().hashCode() % tamanho);
    }

    // Insere um prato na tabela hash
    public boolean inserir(Prato prato) {
        if (prato == null || prato.getNome() == null) {
            return false;
        }

        if (buscar(prato.getNome()) != null) {
            return false;
        }

        int indice = gerarHash(prato.getNome());
        tabela[indice].add(prato);
        numeroElementos++;

        if ((double) numeroElementos / tamanho > FATOR_CARGA) {
            redimensionarTabela();
        }

        return true;
    }

    // Busca um prato na tabela hash
    public Prato buscar(String nome) {
        if (nome == null) return null;

        int indice = gerarHash(nome);
        LinkedList<Prato> lista = tabela[indice];

        for (Prato prato : lista) {
            if (prato.getNome().equalsIgnoreCase(nome)) {
                return prato;
            }
        }

        return null;
    }

    // Remove um prato na tabela hash
    public boolean remover(String nome) {
        if (nome == null) return false;

        int indice = gerarHash(nome);
        LinkedList<Prato> lista = tabela[indice];

        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getNome().equalsIgnoreCase(nome)) {
                lista.remove(i);
                numeroElementos--;
                return true;
            }
        }

        return false;
    }

    // Exporta todos os pratos da tabela hash para um array
    public Prato[] exportarParaVetor() {
        Prato[] vetor = new Prato[numeroElementos];
        int posicao = 0;

        for (int i = 0; i < tamanho; i++) {
            for (Prato prato : tabela[i]) {
                vetor[posicao++] = prato;
            }
        }

        return vetor;
    }

    // Dobra o tamanho da tabela hash e reinsere todos os pratos
    private void redimensionarTabela() {
        int novoTamanho = tamanho * 2;
        LinkedList<Prato>[] novaTabela = new LinkedList[novoTamanho];

        for (int i = 0; i < novoTamanho; i++) {
            novaTabela[i] = new LinkedList<>();
        }

        LinkedList<Prato>[] tabelaAntiga = tabela;
        tabela = novaTabela;
        tamanho = novoTamanho;
        numeroElementos = 0;

        for (int i = 0; i < tabelaAntiga.length; i++) {
            for (Prato prato : tabelaAntiga[i]) {
                inserir(prato);
            }
        }
    }

    // Retorna true se a tabela hash estiver vazia
    public boolean estaVazia() {
        return numeroElementos == 0;
    }

    // Retorna o fator de carga da tabela hash
    public double getFatorCarga() {
        return (double) numeroElementos / tamanho;
    }

    // Retorna o número de colisões na tabela hashs
    public int getNumeroColisoes() {
        int colisoes = 0;

        for (int i = 0; i < tamanho; i++) {
            if (tabela[i].size() > 1) {
                colisoes += tabela[i].size() - 1;
            }
        }

        return colisoes;
    }

}