package fag.objetos;

public class Loja {

	private String nome;
	private double preco;
	private int quantidadeEstoque;
	private int quantidadeCarrinho;

	public Loja(String nome, double preco, int quantidadeEstoque, int quantidadeCarrinho) {
		setNome(nome);
		setPreco(preco);
		setQuantidadeEstoque(quantidadeEstoque);
		setQuantidadeCarrinho(quantidadeCarrinho);

	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		if (nome != null && !nome.isBlank()) {
			this.nome = nome;
		}

	}

	public double getPreco() {
		return preco;
	}

	public void setPreco(double preco) {
		if (preco > 0) {
			this.preco = preco;
		}
	}

	public int getQuantidadeEstoque() {
		return quantidadeEstoque;
	}

	public void setQuantidadeEstoque(int quantidadeEstoque) {
		if (quantidadeEstoque > 0) {
			this.quantidadeEstoque = quantidadeEstoque;
		}
	}

	public int getQuantidadeCarrinho() {
		return quantidadeCarrinho;
	}

	public void setQuantidadeCarrinho(int quantidadeCarrinho) {
		if (quantidadeCarrinho > 0) {
			this.quantidadeCarrinho = quantidadeCarrinho;
		}
	}

	public void mostrarProduto() {
		System.out.printf("Produto: %s - Preço: R$ %.2f - Quantidade no estoque: %d\n", nome, preco, quantidadeEstoque);
	}

	public void mostrarCarrinho() {
		System.out.printf("Produto: %s - Preço: R$ %.2f - Quantidade: %d \n", nome, preco, quantidadeCarrinho);
	}

}
