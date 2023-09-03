package br.com.douglas.turingbankh2.domain.enums;

public enum ContractType {
    PESSOAL("Empréstimo pessoal"),
    AUTO("Empréstimo automóveis"),
    IMOBILIARIO("Empréstimo imobiliário")
    ;

    private String description;

    ContractType(String description) {
        this.description = description;
    }
}
