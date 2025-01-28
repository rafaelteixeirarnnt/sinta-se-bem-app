package br.com.leaf.sintasebemapp.domain.enums;

public enum EstadosEnum {

    AC("AC", "Acre"),
    AL("AL", "Alagoas"),
    AP("AP", "Amapá"),
    AM("AM", "Amazonas"),
    BA("BA", "Bahia"),
    CE("CE", "Ceará"),
    DF("DF", "Distrito Federal"),
    ES("ES", "Espírito Santo"),
    GO("GO", "Goiás"),
    MA("MA", "Maranhão"),
    MT("MT", "Mato Grosso"),
    MS("MS", "Mato Grosso do Sul"),
    MG("MG", "Minas Gerais"),
    PA("PA", "Pará"),
    PB("PB", "Paraíba"),
    PR("PR", "Paraná"),
    PE("PE", "Pernambuco"),
    PI("PI", "Piauí"),
    RJ("RJ", "Rio de Janeiro"),
    RN("RN", "Rio Grande do Norte"),
    RS("RS", "Rio Grande do Sul"),
    RO("RO", "Rondônia"),
    RR("RR", "Roraima"),
    SC("SC", "Santa Catarina"),
    SP("SP", "São Paulo"),
    SE("SE", "Sergipe"),
    TO("TO", "Tocantins");

    private final String sigla;
    private final String nome;

    EstadosEnum(String sigla, String nome) {
        this.sigla = sigla;
        this.nome = nome;
    }

    public String getSigla() {
        return sigla;
    }

    public String getNome() {
        return nome;
    }

    public static EstadosEnum getByNome(String nome) {
        for (EstadosEnum estado : values()) {
            if (estado.getNome().equalsIgnoreCase(nome)) {
                return estado;
            }
        }
        throw new IllegalArgumentException("Estado não encontrado: " + nome);
    }

    public static EstadosEnum getBySigla(String sigla) {
        for (EstadosEnum estado : values()) {
            if (estado.getSigla().equalsIgnoreCase(sigla)) {
                return estado;
            }
        }
        throw new IllegalArgumentException("Estado não encontrado para a sigla: " + sigla);
    }

    @Override
    public String toString() {
        return sigla + " - " + nome;
    }
}