package com.dev.babak.cinemapardy.queue.handlers

public abstract class MessageHandler<E> {

    public abstract void processMessage(E message)
}