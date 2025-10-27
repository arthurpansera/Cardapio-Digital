import java.util.LinkedList;

public class TabelaHash {

    private static final int TAMANHO_INICIAL = 16;
    private static final double FATOR_CARGA = 0.75;

    private LinkedList<Prato>[] tabela;
    private int tamanho;
    private int numeroElementos;

    @SuppressWarnings("unchecked")
    public TabelaHash() {
        this.tamanho = TAMANHO_INICIAL;
        this.tabela = new LinkedList[tamanho];
        this.numeroElementos = 0;

        for (int i = 0; i < tamanho; i++) {
            tabela[i] = new LinkedList<>();
        }
    }

    private int hash(String chave) {
        return Math.abs(chave.toLowerCase().hashCode() % tamanho);
    }

    public boolean inserir(Prato prato) {
        if (prato == null || prato.getNome() == null) {
            return false;
        }

        if (buscar(prato.getNome()) != null) {
            return false;
        }

        int indice = hash(prato.getNome());
        tabela[indice].add(prato);
        numeroElementos++;

        if ((double) numeroElementos / tamanho > FATOR_CARGA) {
            redimensionar();
        }

        return true;
    }

    public Prato buscar(String nome) {
        if (nome == null) return null;

        int indice = hash(nome);
        LinkedList<Prato> lista = tabela[indice];

        for (Prato prato : lista) {
            if (prato.getNome().equalsIgnoreCase(nome)) {
                return prato;
            }
        }
        return null;
    }

    public boolean remover(String nome) {
        if (nome == null) return false;

        int indice = hash(nome);
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

    @SuppressWarnings("unchecked")
    private void redimensionar() {
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

    public int getNumeroElementos() {
        return numeroElementos;
    }

    public int getTamanho() {
        return tamanho;
    }

    public boolean estaVazia() {
        return numeroElementos == 0;
    }

    public double getFatorCarga() {
        return (double) numeroElementos / tamanho;
    }

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