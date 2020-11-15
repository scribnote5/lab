package kr.ac.univ.exception;

import kr.ac.univ.error.ErrorCode;

public class InvalidUsernameException extends BusinessException {
    public InvalidUsernameException() {
    	super(ErrorCode.INVALID_USERNAME);
    }
    
}