package kr.ac.univ.handler;

import kr.ac.univ.util.EmptyUtil;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
            errormsg = "The ID or password does not match.";
        } else if (exception instanceof InternalAuthenticationServiceException) {
            errormsg = "The authentication is not processed due to system problem that occurred internally.";
        } else if (exception instanceof AuthenticationCredentialsNotFoundException) {
            errormsg = "The authentication is not found.";
        } else if (exception instanceof DisabledException) {
            errormsg = "This user is inactive.";
        }  else if (exception instanceof LockedException) {
            errormsg = "This user is need to the admin's authentication.";
        }  else {
            errormsg = "Please contact admin.";
        }

        request.setAttribute("username", username);
        request.setAttribute("errormsg", errormsg);

        request.getRequestDispatcher("/user/login/fail").forward(request, response);
    }

}