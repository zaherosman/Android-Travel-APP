package br.com.ibm.tripsparterapi.services.exceptions;

public class EmailException extends RuntimeException{

    public EmailException(String mensage){
        super(mensage);
    }
}
