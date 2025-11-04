package fag;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import fag.objetos.Loja;

public class Principal {

	static Scanner scanner = new Scanner(System.in);
	static List<Loja> estoque = new ArrayList<>();
	static List<Loja> carrinho = new ArrayList<>();
	static int qnt50 = 5, qnt20 = 5, qnt10 = 5;
	static double saldo = 400;
	static double total = 0;

	public static void main(String[] args) {
		popularEstoque();
		mostrarMenu();
	}

	public static void mostrarMenu() {
		int opcao = 10000;

		do {
			System.out.printf("\n----------MENU----------\n");
			System.out.println("1-> Cadastrar produto");//
			System.out.println("2-> Listar produtos");//
			System.out.println("3-> Realizar venda");
			System.out.println("4-> Exibir saldo");//
			System.out.println("0-> Sair");
			opcao = scanner.nextInt();
			scanner.nextLine();

			validarOpcao(opcao);

		} while (opcao != 0);
	}

	public static void validarOpcao(int opcao) {

		switch (opcao) {

		case 1:
			cadastrarProduto();
			break;

		case 2:
			listarProdutos();
			break;

		case 3:
			realizarVenda();
			break;

		case 4:
			exibirSaldo();

			break;

		case 0:
			break;
		default:
			System.out.println("Escolha uma opção válida!");
			break;

		}

	}

	public static void popularEstoque() {
		Loja produtoUm = new Loja("bicicleta", 300, 5, 0);
		Loja produtoDois = new Loja("borracha", 10, 6, 0);
		Loja produtoTres = new Loja("Caderno", 25, 9, 0);

		estoque.add(produtoUm);
		estoque.add(produtoDois);
		estoque.add(produtoTres);

	}

	public static void cadastrarProduto() {

		System.out.println("Informe o nome do produto:");
		String nome = scanner.nextLine();

		for (Loja produto : estoque) {
			if (produto.getNome().equalsIgnoreCase(nome)) {
				System.out.println("Produto já cadastrado! Deseja adicionar ele no estoque?(sim = 1, não = 2)");
				int opcao = scanner.nextInt();
				scanner.nextLine();

				if (opcao == 1) {
					System.out.print("Informe a quantidade que deseja adicionar: ");
					int qtd = scanner.nextInt();
					scanner.nextLine();
					produto.setQuantidadeEstoque(produto.getQuantidadeEstoque() + qtd);

					System.out.println("Estoque atualizado! Novo total: " + produto.getQuantidadeEstoque());
				} else {
					mostrarMenu();
				}

				return;
			}
		}

		System.out.println("Informe o preço do produto:");
		double preco = scanner.nextDouble();
		scanner.nextLine();

		System.out.println("Informe a quantidade inicial:");
		int quantidade = scanner.nextInt();
		scanner.nextLine();

		Loja novoProduto = new Loja(nome, preco, quantidade, quantidade);
		estoque.add(novoProduto);
		novoProduto.setQuantidadeEstoque(novoProduto.getQuantidadeEstoque() + quantidade);
		System.out.println("Produto cadastrado!!");

	}

	public static void listarProdutos() {
		System.out.printf("\n-----PRODUTOS NO ESTOQUE-----\n");
		for (int i = 0; i < estoque.size(); i++) {
			System.out.printf("%d | ", i + 1);
			estoque.get(i).mostrarProduto();
		}

	}

	public static void listarCarrinho() {
		System.out.println("-----CARRINHO-----");
		for (int i = 0; i < carrinho.size(); i++) {
			System.out.printf("%d | ", i + 1);
			carrinho.get(i).mostrarCarrinho();
		}
	}

	public static void realizarVenda() {
		int opcao;
		total = 0;

		do {
			listarProdutos();
			System.out.printf("\nQual a posição do produto que deseja comprar(Digite 0 para finalizar compra):\n");
			opcao = scanner.nextInt();
			scanner.nextLine();

			if (opcao == 0) {
				break;
			}

			if (opcao < 0 || opcao > estoque.size()) {
				System.out.printf("\nO produto não foi encontrado!\n");
				continue;
			}
			Loja venda = estoque.get(opcao - 1);

			System.out.println("Qual a quantidade do produto:");
			int quantidade = scanner.nextInt();

			if (quantidade > venda.getQuantidadeEstoque()) {
				System.out.println("Não temos essa quantidade no estoque");
				continue;

			}
			venda.setQuantidadeEstoque(venda.getQuantidadeEstoque() - quantidade);

			carrinho.add(venda);
			venda.setQuantidadeCarrinho(venda.getQuantidadeCarrinho() + quantidade);
			System.out.println("Produto adicionado ao carrinho!");

			total += venda.getPreco() * venda.getQuantidadeCarrinho();

		} while (true);

		if (carrinho.isEmpty()) {
			System.out.println("Carrinho vazio. Nenhuma venda realizada.");
			return;
		}

		finalizarCompra();

	}

	public static void finalizarCompra() {
		listarCarrinho();
		System.out.printf("\n0 -> Remover produto do carrinho\n");
		System.out.println("1 -> Ir para o pagamento");
		int opcao = scanner.nextInt();
		scanner.nextLine();

		if (opcao == 0) {
			removerProduto();

		}

		if (opcao == 1) {
			if (carrinho.isEmpty()) {
				System.out.println("Carrinho vazio. Nenhuma venda realizada.");
				return;
			}
			pagarProduto(total);
		}

	}

	public static void removerProduto() {
		listarCarrinho();
		System.out.println("Qual produto deseja remover?(Posição)");
		int escolha = scanner.nextInt();

		if (escolha > carrinho.size()) {
			System.out.println("Produto não encontrado");
			removerProduto();
		}

		Loja produtoExcluidoCarrinho = carrinho.get(escolha - 1);
		for (Loja produtoEstoque : estoque) {
			if (produtoEstoque.getNome().equalsIgnoreCase(produtoExcluidoCarrinho.getNome())) {
				produtoEstoque.setQuantidadeEstoque(
						produtoEstoque.getQuantidadeEstoque() + produtoExcluidoCarrinho.getQuantidadeCarrinho());
				break;
			}
		}
		carrinho.remove(escolha - 1);
		produtoExcluidoCarrinho.setQuantidadeCarrinho(0);

		produtoExcluidoCarrinho.setQuantidadeEstoque(produtoExcluidoCarrinho.getQuantidadeEstoque());

		System.out.printf("%s foi removido do carrinho!\n", produtoExcluidoCarrinho.getNome());
		atualizarTotal();
		finalizarCompra();

	}

	public static void atualizarTotal() {
		total = 0;
		for (Loja produto : carrinho) {
			total += produto.getPreco() * produto.getQuantidadeCarrinho();
		}
	}

	public static void pagarProduto(double total) {

		double valorPago = 0;

		while (valorPago < total) {
			System.out.printf("\nTotal da compra: R$ %.2f\n", total);

			System.out.println("\nInforme o pagamento (quantas notas de cada):");

			System.out.print("Notas de 50: ");
			int pag50 = scanner.nextInt();

			System.out.print("Notas de 20: ");
			int pag20 = scanner.nextInt();

			System.out.print("Notas de 10: ");
			int pag10 = scanner.nextInt();

			scanner.nextLine();
			valorPago = (pag50 * 50) + (pag20 * 20) + (pag10 * 10);

			System.out.printf("\nValor total pago: R$ %.2f\n", valorPago);
			if (valorPago < total) {
				System.out.println("Valor insuficiente, insira um novo pagamento");
			} else {
				qnt50 += pag50;
				qnt20 += pag20;
				qnt10 += pag10;
				saldo += valorPago;
			}
			double troco = valorPago - total;

			if (valorPago > total) {

				System.out.printf("\nTROCO TOTAL: R$ %.2f\n", troco);
				calcularNotas(troco);
			}

		}
		System.out.println("\nCompra finalizada com sucesso!");
		carrinho.clear();
		total = 0;
	}

	public static void exibirSaldo() {

		System.out.println("---SALDO---");
		System.out.printf("R$ %.2f\n", saldo);
		System.out.printf("\n---QUANTIDADE DE NOTAS---\n");
		System.out.printf("NOTAS DE 50: %d\n", qnt50);
		System.out.printf("NOTAS DE 20: %d\n", qnt20);
		System.out.printf("NOTAS DE 10: %d\n", qnt10);

	}

	public static boolean calcularNotas(double troco) {
		double trocoInicio = troco;
		int notas50 = 0, notas20 = 0, notas10 = 0;

		System.out.println("----NOTAS ENTREGUES----");

		while (troco >= 50 && qnt50 > 0) {
			troco -= 50;
			qnt50--;
			notas50++;
		}

		while (troco >= 20 && qnt20 > 0) {
			troco -= 20;
			qnt20--;
			notas20++;

		}

		while (troco >= 10 && qnt10 > 0) {
			troco -= 10;
			qnt10--;
			notas10++;
		}

		if (troco >= 1) {
			System.out.printf("Ainda falta R$ %.2f (não há notas suficientes)\n", troco);
			return false;
		}

		saldo -= trocoInicio;

		if (notas50 > 0)
			System.out.println(notas50 + " nota(s) de R$50");
		if (notas20 > 0)
			System.out.println(notas20 + " nota(s) de R$20");
		if (notas10 > 0)
			System.out.println(notas10 + " nota(s) de R$10");

		return true;

	}
}
