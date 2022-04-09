package com.notes.util;

public class Constant {

    // Can be used for others more specific errors;
    public class ErrorCode {
        public static final String SUCCESS                 = "ER-000";

        // Error codes from HTTP Status
        public static final String ERROR_INTERNAL_SERVER   = "ER-500";
        public static final String ERROR_UNAUTHORIZED      = "ER-401";
        public static final String ERROR_BAD_REQUEST       = "ER-400";
        public static final String ERROR_NOT_FOUND         = "ER-404";

        // Custom Error codes
    }

}
