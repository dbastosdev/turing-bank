/*
* Somente um padrão para a mensagem de erro
* */

package br.com.douglas.turingbankh2.controllers.exceptions;

import java.time.Instant;

public class StandartError {

    private Instant timestamp; // instante da ocorrência da exceção
    private Integer status; // Status do erro HTTP
    private String error; // tipo de erro
    private String message; // mensagem associada
    private String path; // caminho do recurso em que houve erro

    public StandartError(){

    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
