package br.com.douglas.turingbankh2.domain.enums;

public enum DirectDebitStatus {
    AGENDADO("Pagamento agendado"),
    PAGO("Pagamento realizado"),
    REAGENDADO("Pagamento reagendado por ausência de saldo")
    ;

    private String description;

    DirectDebitStatus(String description) {
        this.description = description;
    }
}
