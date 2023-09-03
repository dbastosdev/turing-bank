
/*
* Essa exceção personalizada é usada para propagar uma mensagem em caso de não não ser encntrada uma entidade.
*
*/

package br.com.douglas.turingbankh2.services.exceptions;

public class EntityNotFoundException extends RuntimeException{
    public EntityNotFoundException(String msg){
        super(msg);
    }
}
