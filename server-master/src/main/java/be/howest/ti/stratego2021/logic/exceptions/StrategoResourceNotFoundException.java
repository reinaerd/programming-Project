package be.howest.ti.stratego2021.logic.exceptions;

public class StrategoResourceNotFoundException extends RuntimeException {

    public static final long serialVersionUID = 10000;

    public StrategoResourceNotFoundException(String msg) {
        super(msg);
    }

}
