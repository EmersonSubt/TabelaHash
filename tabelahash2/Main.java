import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        int tamanho = 5001;
        TabelaHash tabela1 = new FuncaoHash1(tamanho);
        TabelaHash tabela2 = new FuncaoHash2(tamanho);

        List<String> nomes = carregarNomes("female_names.txt");

        if (nomes.isEmpty()) {
            System.out.println("Nenhum nome foi carregado.");
            return;
        }

        System.out.println("Arquivo lido com sucesso. Total de nomes: " + nomes.size());

        long tempoInsercao1 = medirTempoInsercao(tabela1, nomes);
        long tempoBusca1 = medirTempoBusca(tabela1, nomes);
        long tempoInsercao2 = medirTempoInsercao(tabela2, nomes);
        long tempoBusca2 = medirTempoBusca(tabela2, nomes);

        gerarRelatorio(tabela1, tabela2, tempoInsercao1, tempoBusca1, tempoInsercao2, tempoBusca2);
    }

    private static List<String> carregarNomes(String caminhoArquivo) {
        List<String> nomes = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(caminhoArquivo))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                nomes.add(linha);
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo: " + e.getMessage());
        }
        return nomes;
    }

    private static long medirTempoInsercao(TabelaHash tabela, List<String> nomes) {
        long inicio = System.nanoTime();
        for (String nome : nomes) {
            tabela.inserir(nome);
        }
        return System.nanoTime() - inicio;
    }

    private static long medirTempoBusca(TabelaHash tabela, List<String> nomes) {
        long inicio = System.nanoTime();
        for (String nome : nomes) {
            tabela.buscar(nome);
        }
        return System.nanoTime() - inicio;
    }

    private static void gerarRelatorio(TabelaHash tabela1, TabelaHash tabela2, long tempoInsercao1, long tempoBusca1, long tempoInsercao2, long tempoBusca2) {
        System.out.println(" - Análise de Desempenho das Tabelas Hash -");

        System.out.printf("Total de colisões da Tabela 1: %d\n", tabela1.getNumeroDeColisoes());
        System.out.printf("Total de colisões da Tabela 2: %d\n", tabela2.getNumeroDeColisoes());

        System.out.printf("Tempo total para inserir elementos na Tabela 1: %d ns\n", tempoInsercao1);
        System.out.printf("Tempo total para buscar elementos na Tabela 1: %d ns\n", tempoBusca1);

        System.out.printf("Tempo total para inserir elementos na Tabela 2: %d ns\n", tempoInsercao2);
        System.out.printf("Tempo total para buscar elementos na Tabela 2: %d ns\n", tempoBusca2);

        imprimirDistribuicaoColisoes(tabela1, 1);
        imprimirDistribuicaoColisoes(tabela2, 2);
    }

    private static void imprimirDistribuicaoColisoes(TabelaHash tabela, int numeroTabela) {
        System.out.printf("Tabela %d - Distribuição de chaves e colisões em intervalos de 200 posições:\n", numeroTabela);
        int[] distribuicao = tabela.getDistribuicaoChaves();
        int[] colisoesPorIntervalo = tabela.getColisoesPorIntervalo(200);
        for (int i = 0; i < distribuicao.length; i += 200) {
            int totalChaves = 0;
            for (int j = i; j < i + 200 && j < distribuicao.length; j++) {
                totalChaves += distribuicao[j];
            }
            System.out.printf("Intervalo %d-%d: %d chaves, %d colisões\n", i, Math.min(i + 199, distribuicao.length - 1), totalChaves, colisoesPorIntervalo[i / 200]);
        }
    }
}
