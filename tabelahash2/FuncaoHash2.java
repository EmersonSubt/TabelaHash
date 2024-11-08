class FuncaoHash2 extends TabelaHash {
    public FuncaoHash2(int tamanho) {
        super(tamanho);
    }

    @Override
    public int funcaoHash(String chave) {
        int hash = 0;
        for (int i = 0; i < chave.length(); i++) {
            hash = (hash * 13 + chave.charAt(i)) % tamanho;
        }
        return hash;
    }
}