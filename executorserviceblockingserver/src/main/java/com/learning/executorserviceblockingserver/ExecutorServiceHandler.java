package com.learning.executorserviceblockingserver;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.FutureTask;

public class ExecutorServiceHandler<S> extends DecoratedHandler<S> {
    private final ExecutorService pool;
    private final Thread.UncaughtExceptionHandler exceptionHandler;

    public ExecutorServiceHandler(Handler<S> other,
                                  ExecutorService pool,
                                  Thread.UncaughtExceptionHandler exceptionHandler) {
        super(other);
        this.pool = pool;
        this.exceptionHandler = exceptionHandler;
    }

    @Override
    public void handle(S s) {
        pool.submit(new FutureTask<>(() -> {
                        super.handle(s);
                        return null;
                    }) {
                        @Override
                        protected void done() {
                            super.done();
                        }

                        @Override
                        protected void setException(Throwable t) {
                            exceptionHandler.uncaughtException(Thread.currentThread(), t);
                        }
                    }
        );
    }

}
