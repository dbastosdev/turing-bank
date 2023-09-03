package br.com.douglas.turingbankh2.domain.enums;

public enum InstallmentStatus {
    AGUARDANDO_PAGAMENTO("Aguardando pagamento até a data de vencimento"),
    ATRASADO("Pagamento atrasado"),
    PAGO("Pagamento realizado")
    ;

    private String description;

    InstallmentStatus(String description) {
        this.description = description;
    }
}
