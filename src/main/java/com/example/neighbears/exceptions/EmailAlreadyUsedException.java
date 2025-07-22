package com.example.neighbears.exceptions;



    public class EmailAlreadyUsedException extends RuntimeException {
        public EmailAlreadyUsedException(String message) {
            super(message);
        }
    }

