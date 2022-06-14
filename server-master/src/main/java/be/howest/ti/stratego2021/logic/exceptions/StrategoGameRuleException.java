package be.howest.ti.stratego2021.logic.exceptions;

public class StrategoGameRuleException extends RuntimeException {

    public static final long serialVersionUID = 10000;

    public StrategoGameRuleException(String msg) {
        super(msg);
    }
}
