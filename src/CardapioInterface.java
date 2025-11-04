import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class CardapioInterface extends JFrame {

    private TabelaHash cardapio;
    private DefaultTableModel modeloTabela;
    private JTable tabela;

    private JTextField txtNome, txtPreco, txtTempo, txtCategoria, txtBuscar;
    private JComboBox<String> cbCriterio, cbAlgoritmo;
    private JLabel lblTempo, lblElementos, lblColisoes, lblFatorCarga;

    public CardapioInterface() {
        cardapio = new TabelaHash();
        carregarDadosIniciais();
        inicializarInterface();
    }

    private void inicializarInterface() {
        setTitle("Cardápio Digital - Gestão de Pratos");
        setSize(1200, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JPanel painelTitulo = criarPainelTitulo();
        add(painelTitulo, BorderLayout.NORTH);

        JPanel painelCentral = criarPainelTabela();
        add(painelCentral, BorderLayout.CENTER);

        JPanel painelControles = criarPainelControles();
        add(painelControles, BorderLayout.EAST);

        JPanel painelEstatisticas = criarPainelEstatisticas();
        add(painelEstatisticas, BorderLayout.SOUTH);

        atualizarTabela();
        atualizarEstatisticas();
    }

    private JPanel criarPainelTitulo() {
        JPanel painel = new JPanel();
        painel.setBackground(new Color(41, 128, 185));
        painel.setPreferredSize(new Dimension(0, 70));

        JLabel titulo = new JLabel("CARDÁPIO DIGITAL");
        titulo.setFont(new Font("Arial", Font.BOLD, 32));
        titulo.setForeground(Color.WHITE);

        painel.add(titulo);
        return painel;
    }

    private JPanel criarPainelTabela() {
        JPanel painel = new JPanel(new BorderLayout(5, 5));
        painel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        String[] colunas = {"Nome", "Preço", "Tempo", "Categoria"};
        modeloTabela = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabela = new JTable(modeloTabela);
        tabela.setFont(new Font("Arial", Font.PLAIN, 14));
        tabela.setRowHeight(30);
        tabela.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        tabela.getTableHeader().setBackground(new Color(52, 152, 219));
        tabela.getTableHeader().setForeground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(tabela);
        scrollPane.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(52, 152, 219), 2),
                "Pratos Cadastrados",
                0, 0, new Font("Arial", Font.BOLD, 14)));

        painel.add(scrollPane, BorderLayout.CENTER);
        return painel;
    }

    private JPanel criarPainelControles() {
        JPanel painel = new JPanel();
        painel.setLayout(new BoxLayout(painel, BoxLayout.Y_AXIS));
        painel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        painel.setPreferredSize(new Dimension(350, 0));

        painel.add(criarSecaoInserir());
        painel.add(Box.createVerticalStrut(15));

        painel.add(criarSecaoBuscarRemover());
        painel.add(Box.createVerticalStrut(15));

        painel.add(criarSecaoOrdenar());

        return painel;
    }

    private JPanel criarSecaoInserir() {
        JPanel painel = new JPanel();
        painel.setLayout(new BoxLayout(painel, BoxLayout.Y_AXIS));
        painel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(46, 204, 113), 2),
                "Inserir Prato",
                0, 0, new Font("Arial", Font.BOLD, 14)));

        txtNome = new JTextField(20);
        txtPreco = new JTextField(20);
        txtTempo = new JTextField(20);
        txtCategoria = new JTextField(20);

        painel.add(criarCampo("Nome:", txtNome));
        painel.add(Box.createVerticalStrut(8));
        painel.add(criarCampo("Preço (R$):", txtPreco));
        painel.add(Box.createVerticalStrut(8));
        painel.add(criarCampo("Tempo (min):", txtTempo));
        painel.add(Box.createVerticalStrut(8));
        painel.add(criarCampo("Categoria:", txtCategoria));
        painel.add(Box.createVerticalStrut(12));

        JButton btnInserir = criarBotao("Inserir", new Color(46, 204, 113));
        btnInserir.addActionListener(e -> inserirPrato());
        painel.add(btnInserir);

        return painel;
    }

    private JPanel criarSecaoBuscarRemover() {
        JPanel painel = new JPanel();
        painel.setLayout(new BoxLayout(painel, BoxLayout.Y_AXIS));
        painel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(230, 126, 34), 2),
                "Buscar/Remover Prato",
                0, 0, new Font("Arial", Font.BOLD, 14)));

        txtBuscar = new JTextField(20);
        painel.add(criarCampo("Nome:", txtBuscar));
        painel.add(Box.createVerticalStrut(12));

        JPanel painelBotoes = new JPanel(new FlowLayout());

        JButton btnBuscar = criarBotao("Buscar", new Color(52, 152, 219));
        btnBuscar.addActionListener(e -> buscarPrato());
        painelBotoes.add(btnBuscar);

        JButton btnRemover = criarBotao("Remover", new Color(231, 76, 60));
        btnRemover.addActionListener(e -> removerPrato());
        painelBotoes.add(btnRemover);

        painel.add(painelBotoes);
        return painel;
    }

    private JPanel criarSecaoOrdenar() {
        JPanel painel = new JPanel();
        painel.setLayout(new BoxLayout(painel, BoxLayout.Y_AXIS));
        painel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(155, 89, 182), 2),
                "Ordenar Cardápio",
                0, 0, new Font("Arial", Font.BOLD, 14)));

        cbCriterio = new JComboBox<>(new String[]{"Nome", "Preço", "Tempo"});
        cbAlgoritmo = new JComboBox<>(new String[]{"BubbleSort", "InsertionSort", "QuickSort"});

        painel.add(criarCampoCombo("Critério:", cbCriterio));
        painel.add(Box.createVerticalStrut(8));
        painel.add(criarCampoCombo("Algoritmo:", cbAlgoritmo));
        painel.add(Box.createVerticalStrut(12));

        JButton btnOrdenar = criarBotao("Ordenar", new Color(155, 89, 182));
        btnOrdenar.addActionListener(e -> ordenarCardapio());
        painel.add(btnOrdenar);

        painel.add(Box.createVerticalStrut(10));
        lblTempo = new JLabel("Tempo: -- ms");
        lblTempo.setFont(new Font("Arial", Font.BOLD, 12));
        lblTempo.setAlignmentX(Component.CENTER_ALIGNMENT);
        painel.add(lblTempo);

        return painel;
    }

    private JPanel criarPainelEstatisticas() {
        JPanel painel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 10));
        painel.setBackground(new Color(236, 240, 241));
        painel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(189, 195, 199), 2),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)));

        lblElementos = criarLabelEstatistica("Pratos: 0");
        lblColisoes = criarLabelEstatistica("Colisões: 0");
        lblFatorCarga = criarLabelEstatistica("Fator: 0.00");

        painel.add(lblElementos);
        painel.add(lblColisoes);
        painel.add(lblFatorCarga);

        return painel;
    }

    private JPanel criarCampo(String label, JTextField campo) {
        JPanel painel = new JPanel(new BorderLayout(5, 0));
        painel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));

        JLabel lbl = new JLabel(label);
        lbl.setPreferredSize(new Dimension(100, 25));
        lbl.setFont(new Font("Arial", Font.PLAIN, 12));

        campo.setFont(new Font("Arial", Font.PLAIN, 12));

        painel.add(lbl, BorderLayout.WEST);
        painel.add(campo, BorderLayout.CENTER);

        return painel;
    }

    private JPanel criarCampoCombo(String label, JComboBox<String> combo) {
        JPanel painel = new JPanel(new BorderLayout(5, 0));
        painel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));

        JLabel lbl = new JLabel(label);
        lbl.setPreferredSize(new Dimension(100, 25));
        lbl.setFont(new Font("Arial", Font.PLAIN, 12));

        combo.setFont(new Font("Arial", Font.PLAIN, 12));

        painel.add(lbl, BorderLayout.WEST);
        painel.add(combo, BorderLayout.CENTER);

        return painel;
    }

    private JButton criarBotao(String texto, Color cor) {
        JButton botao = new JButton(texto);
        botao.setFont(new Font("Arial", Font.BOLD, 14));
        botao.setBackground(cor);
        botao.setForeground(Color.WHITE);
        botao.setFocusPainted(false);
        botao.setBorderPainted(false);
        botao.setPreferredSize(new Dimension(150, 35));
        botao.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        botao.setAlignmentX(Component.CENTER_ALIGNMENT);
        botao.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return botao;
    }

    private JLabel criarLabelEstatistica(String texto) {
        JLabel label = new JLabel(texto);
        label.setFont(new Font("Arial", Font.BOLD, 14));
        label.setForeground(new Color(44, 62, 80));
        return label;
    }

    private void inserirPrato() {
        try {
            String nome = txtNome.getText().trim();
            double preco = Double.parseDouble(txtPreco.getText().trim());
            int tempo = Integer.parseInt(txtTempo.getText().trim());
            String categoria = txtCategoria.getText().trim();

            if (nome.isEmpty() || categoria.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Nome e categoria são obrigatórios!",
                        "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Prato prato = new Prato(nome, preco, tempo, categoria);

            if (cardapio.inserir(prato)) {
                JOptionPane.showMessageDialog(this,
                        "Prato inserido com sucesso!",
                        "Sucesso", JOptionPane.INFORMATION_MESSAGE);

                limparCamposInserir();
                atualizarTabela();
                atualizarEstatisticas();
            } else {
                JOptionPane.showMessageDialog(this,
                        "Prato já existe no cardápio!",
                        "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "Preço e tempo devem ser números válidos!",
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void buscarPrato() {
        String nome = txtBuscar.getText().trim();

        if (nome.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Digite o nome do prato!",
                    "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Prato prato = cardapio.buscar(nome);

        if (prato != null) {
            JOptionPane.showMessageDialog(this,
                    String.format("Prato encontrado!\n\n" +
                                    "Nome: %s\n" +
                                    "Preço: R$ %.2f\n" +
                                    "Tempo: %d min\n" +
                                    "Categoria: %s",
                            prato.getNome(), prato.getPreco(),
                            prato.getTempoPreparo(), prato.getCategoria()),
                    "Busca", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this,
                    "Prato não encontrado!",
                    "Busca", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void removerPrato() {
        String nome = txtBuscar.getText().trim();

        if (nome.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Digite o nome do prato!",
                    "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int confirmacao = JOptionPane.showConfirmDialog(this,
                "Deseja realmente remover o prato '" + nome + "'?",
                "Confirmar Remoção", JOptionPane.YES_NO_OPTION);

        if (confirmacao == JOptionPane.YES_OPTION) {
            if (cardapio.remover(nome)) {
                JOptionPane.showMessageDialog(this,
                        "Prato removido com sucesso!",
                        "Sucesso", JOptionPane.INFORMATION_MESSAGE);

                txtBuscar.setText("");
                atualizarTabela();
                atualizarEstatisticas();
            } else {
                JOptionPane.showMessageDialog(this,
                        "Prato não encontrado!",
                        "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void ordenarCardapio() {
        if (cardapio.estaVazia()) {
            JOptionPane.showMessageDialog(this,
                    "Cardápio vazio! Insira pratos primeiro.",
                    "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String criterio = (String) cbCriterio.getSelectedItem();
        String algoritmo = (String) cbAlgoritmo.getSelectedItem();

        Prato[] pratos = cardapio.exportarParaVetor();
        long inicio = System.nanoTime();

        executarOrdenacao(pratos, criterio, algoritmo);

        long fim = System.nanoTime();
        double tempoMs = (fim - inicio) / 1_000_000.0;

        atualizarTabelaComPratos(pratos);

        lblTempo.setText(String.format("Tempo: %.4f ms", tempoMs));

        JOptionPane.showMessageDialog(this,
                String.format("Ordenação concluída!\n\n" +
                                "Algoritmo: %s\n" +
                                "Critério: %s\n" +
                                "Tempo: %.4f ms\n" +
                                "Pratos: %d",
                        algoritmo, criterio, tempoMs, pratos.length),
                "Sucesso", JOptionPane.INFORMATION_MESSAGE);
    }

    private void executarOrdenacao(Prato[] pratos, String criterio, String algoritmo) {
        switch (algoritmo) {
            case "BubbleSort":
                executarBubbleSort(pratos, criterio);
                break;
            case "InsertionSort":
                executarInsertionSort(pratos, criterio);
                break;
            case "QuickSort":
                executarQuickSort(pratos, criterio);
                break;
        }
    }

    private void executarBubbleSort(Prato[] pratos, String criterio) {
        switch (criterio) {
            case "Nome":
                AlgoritmosOrdenacao.bubbleSortPorNome(pratos);
                break;
            case "Preço":
                AlgoritmosOrdenacao.bubbleSortPorPreco(pratos);
                break;
            case "Tempo":
                AlgoritmosOrdenacao.bubbleSortPorTempo(pratos);
                break;
        }
    }

    private void executarInsertionSort(Prato[] pratos, String criterio) {
        switch (criterio) {
            case "Nome":
                AlgoritmosOrdenacao.insertionSortPorNome(pratos);
                break;
            case "Preço":
                AlgoritmosOrdenacao.insertionSortPorPreco(pratos);
                break;
            case "Tempo":
                AlgoritmosOrdenacao.insertionSortPorTempo(pratos);
                break;
        }
    }

    private void executarQuickSort(Prato[] pratos, String criterio) {
        switch (criterio) {
            case "Nome":
                AlgoritmosOrdenacao.quickSortPorNome(pratos);
                break;
            case "Preço":
                AlgoritmosOrdenacao.quickSortPorPreco(pratos);
                break;
            case "Tempo":
                AlgoritmosOrdenacao.quickSortPorTempo(pratos);
                break;
        }
    }

    private void atualizarTabela() {
        modeloTabela.setRowCount(0);
        Prato[] pratos = cardapio.exportarParaVetor();

        for (Prato prato : pratos) {
            modeloTabela.addRow(prato.toTableRow());
        }
    }

    private void atualizarTabelaComPratos(Prato[] pratos) {
        modeloTabela.setRowCount(0);

        for (Prato prato : pratos) {
            modeloTabela.addRow(prato.toTableRow());
        }
    }

    private void atualizarEstatisticas() {
        lblElementos.setText("Pratos: " + cardapio.getNumeroElementos());
        lblColisoes.setText("Colisões: " + cardapio.getNumeroColisoes());
        lblFatorCarga.setText(String.format("Fator: %.2f", cardapio.getFatorCarga()));
    }

    private void limparCamposInserir() {
        txtNome.setText("");
        txtPreco.setText("");
        txtTempo.setText("");
        txtCategoria.setText("");
    }

    private void carregarDadosIniciais() {
        cardapio.inserir(new Prato("Feijoada Completa", 45.90, 120, "Prato Principal"));
        cardapio.inserir(new Prato("Picanha na Brasa", 89.90, 40, "Carnes"));
        cardapio.inserir(new Prato("Lasanha Bolonhesa", 35.00, 50, "Massas"));
        cardapio.inserir(new Prato("Salmão Grelhado", 65.00, 25, "Peixes"));
        cardapio.inserir(new Prato("Risoto de Funghi", 42.00, 35, "Massas"));
        cardapio.inserir(new Prato("Tilápia Assada", 38.00, 30, "Peixes"));
        cardapio.inserir(new Prato("Strogonoff de Frango", 32.00, 60, "Prato Principal"));
        cardapio.inserir(new Prato("Hambúrguer Artesanal", 28.00, 20, "Lanches"));
        cardapio.inserir(new Prato("Pizza Margherita", 45.00, 28, "Pizzas"));
        cardapio.inserir(new Prato("Salada Caesar", 22.00, 10, "Saladas"));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            CardapioInterface frame = new CardapioInterface();
            frame.setVisible(true);
        });
    }

}