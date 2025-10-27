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

    @Override
    public String toString() {
        return String.format("%s - R$ %.2f - %d min - %s",
                nome, preco, tempoPreparo, categoria);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Prato prato = (Prato) obj;
        return nome.equalsIgnoreCase(prato.nome);
    }

    @Override
    public int hashCode() {
        return nome.toLowerCase().hashCode();
    }

    public Object[] toTableRow() {
        return new Object[]{nome, String.format("R$ %.2f", preco),
                tempoPreparo + " min", categoria};
    }

}