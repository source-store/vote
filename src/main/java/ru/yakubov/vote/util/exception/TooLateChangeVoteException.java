package ru.yakubov.vote.util.exception;

public class TooLateChangeVoteException extends RuntimeException {
    public TooLateChangeVoteException(String msg) {
        super(msg);
    }
}
