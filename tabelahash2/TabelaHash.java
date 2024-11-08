abstract class TabelaHash {
    protected int tamanho;
    protected String[] tabela;
    protected int numeroDeColisoes;

    public TabelaHash(int tamanho) {
        this.tamanho = tamanho;
        this.tabela = new String[tamanho];
        this.numeroDeColisoes = 0;
    }

    public abstract int funcaoHash(String chave);

    public void inserir(String chave) {
        int indice = funcaoHash(chave);
        boolean colidiu = false;
        while (tabela[indice] != null) {
            if (!tabela[indice].equals(chave) && !colidiu) {
                numeroDeColisoes++;
                colidiu = true;
            }
            indice = (indice + 1) % tamanho;
        }
        tabela[indice] = chave;
    }

    public boolean buscar(String chave) {
        int indice = funcaoHash(chave);
        while (tabela[indice] != null) {
            if (tabela[indice].equals(chave)) {
                return true;
            }
            indice = (indice + 1) % tamanho;
        }
        return false;
    }

    public int getNumeroDeColisoes() {
        return numeroDeColisoes;
    }

    public int[] getDistribuicaoChaves() {
        int[] distribuicao = new int[tamanho];
        for (String chave : tabela) {
            if (chave != null) {
                int indice = funcaoHash(chave);
                distribuicao[indice]++;
            }
        }
        return distribuicao;
    }

    public int[] getColisoesPorIntervalo(int intervalo) {
        int[] colisoesPorIntervalo = new int[(tamanho + intervalo - 1) / intervalo];
        for (int i = 0; i < tamanho; i++) {
            if (tabela[i] != null) {
                int hashIndice = funcaoHash(tabela[i]);
                if (hashIndice != i) {
                    colisoesPorIntervalo[hashIndice / intervalo]++;
                }
            }
        }
        return colisoesPorIntervalo;
    }
}
