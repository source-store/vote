package ru.yakubov.vote.util.exception;

public class FailVoteException extends RuntimeException{
    public FailVoteException(String error) {
        super(error);
    }
}
