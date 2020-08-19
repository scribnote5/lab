package kr.ac.univ.handler;

import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        // 로그인 실패시 기능 구현
        String username = request.getParameter("username");
        String errormsg = null;

        if (exception instanceof BadCredentialsException) {
            errormsg = "The email or passwords do not match.";
        } else if (exception instanceof InternalAuthenticationServiceException) {
            errormsg = "The authentication is not processed due to system problem that occured internally.";
        } else if (exception instanceof AuthenticationCredentialsNotFoundException) {
            errormsg = "The authentication is not found.";
        }
        // 추후 잠금 및 비활성화 기능 구현
        else if (exception instanceof LockedException) {
            errormsg = "This user is locked.";
        } else if (exception instanceof DisabledException) {
            errormsg = "This user is disabled.";
        }

        request.setAttribute("username", username);
        request.setAttribute("errormsg", errormsg);

        request.getRequestDispatcher("/user/login/fail").forward(request, response);
    }

}