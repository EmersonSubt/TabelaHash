class FuncaoHash1 extends TabelaHash {
    public FuncaoHash1(int tamanho) {
        super(tamanho);
    }

    @Override
    public int funcaoHash(String chave) {
        int hash = 0;
        for (char c : chave.toCharArray()) {
            hash = (hash * 11 + c) % tamanho;
        }
        return hash;
    }
}