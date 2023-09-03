package br.com.douglas.turingbankh2.grpcClient.enums;

public enum InvestmentAccountType {
    TESOURO_DIRETO("Investimento no tesouro direto"),
    ACOES("Investimento em ações"),
    FUNDO_IMOBILIARIO("Investimento em fundo imobiliário")
    ;

    private String description;

    InvestmentAccountType(String description) {
        this.description = description;
    }

}
