
/*
* Essa exceção personalizada é usada para propagar uma mensagem em caso de não não ser encntrada uma entidade.
*
*/

package br.com.douglas.turingbankh2.services.exceptions;

public class EntityDuplicatedException extends RuntimeException{
    public EntityDuplicatedException(String msg){
        super(msg);
    }
}
