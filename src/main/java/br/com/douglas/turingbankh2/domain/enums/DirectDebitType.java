package br.com.douglas.turingbankh2.domain.enums;

public enum DirectDebitType {
    EMPRESTIMO("Pagamento de parcela de empréstimo"),
    BOLETO("Pagamento de boleto")
    ;

    private String description;

    DirectDebitType(String description) {
        this.description = description;
    }
}
