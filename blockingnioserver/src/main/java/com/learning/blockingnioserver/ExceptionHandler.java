package com.learning.blockingnioserver;

import java.util.function.BiConsumer;

public class ExceptionHandler<S, X extends Throwable> extends DecoratedHandler<S, X> {
    private final BiConsumer<S, Throwable> exceptionConsumer;

    public ExceptionHandler(Handler<S, X> other, BiConsumer<S, Throwable> exceptionConsumer) {
        super(other);
        this.exceptionConsumer = exceptionConsumer;
    }

    public ExceptionHandler(Handler<S, X> other) {
        this(other, (s, x) -> System.err.println("Issue with " + s + " error " + x));
    }

    public void handle(S s) {
        try {
            super.handle(s);
        } catch (Throwable x) {
            exceptionConsumer.accept(s, x);
        }
    }
}