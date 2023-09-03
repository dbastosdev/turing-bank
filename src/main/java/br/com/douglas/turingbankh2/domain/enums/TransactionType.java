package br.com.douglas.turingbankh2.domain.enums;

public enum TransactionType {
    SAQUE("Operação de saque"),
    DEPOSITO("Operação de depósito"),
    PAGAMENTO_PARCELA("Operação de pagamento de parcela")
    ;

    private String description;

    TransactionType(String description) {
        this.description = description;
    }
}
