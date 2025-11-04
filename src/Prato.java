public class Prato {

    private String nome;
    private double preco;
    private int tempoPreparo;
    private String categoria;

    public Prato(String nome, double preco, int tempoPreparo, String categoria) {
        this.nome = nome;
        this.preco = preco;
        this.tempoPreparo = tempoPreparo;
        this.categoria = categoria;
    }

    public String getNome() {
        return nome;
    }

    public double getPreco() {
        return preco;
    }

    public int getTempoPreparo() {
        return tempoPreparo;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public void setTempoPreparo(int tempoPreparo) {
        this.tempoPreparo = tempoPreparo;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public Object[] toTableRow() {
        return new Object[]{nome, String.format("R$ %.2f", preco),
                tempoPreparo + " min", categoria};
    }

}