package com.eds.orders.domain.exception;

public class DomainRuleViolationException extends  RuntimeException {
        private final String code;
        public DomainRuleViolationException(String code, String message) {
            super(message);
            this.code = code;
        }

        public String getCode() {
            return code;
        }
}
