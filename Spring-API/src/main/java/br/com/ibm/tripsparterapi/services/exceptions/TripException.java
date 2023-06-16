package br.com.ibm.tripsparterapi.services.exceptions;

public class TripException extends RuntimeException{

    public TripException(String mensage){
        super(mensage);
    }
}
