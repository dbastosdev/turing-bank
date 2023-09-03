package br.com.douglas.turingbankh2.domain.enums;

public enum LoanContractStatus {
    EM_DIA("Contrato em dia"),
    FINALIZADO("Contrato finalizado"),
    ATRASADO("Contrato com parcelas em atraso")
    ;

    private String description;

    LoanContractStatus(String description) {
        this.description = description;
    }
}
