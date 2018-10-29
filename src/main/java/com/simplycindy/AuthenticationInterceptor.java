package com.simplycindy;

import com.simplycindy.controllers.UserController;
import com.simplycindy.models.UserData;
import com.simplycindy.models.data.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Created by LaunchCode
 */
public class AuthenticationInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    UserDao userDao;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {

        // Authentication white list; add all publicly visible pages here
        List<String> nonAuthPages = Arrays.asList("/login", "/register", "/product/display", "/home",
                                                    "/product/aProduct", "/email/contactForm");

        // Require sign-in for auth pages
        if ( nonAuthPages.contains(request.getRequestURI()) ) {
            return true;
        } else {

            String email = (String) request.getSession().getAttribute(UserController.userSessionKey);

            if (email != null) {
                UserData user = userDao.findByEmail(email);

                boolean isUserAnAdmin = user.isAdmin();
                if(isUserAnAdmin) {
                    return true;
                } else {
                    List <String> normalUserPages = Arrays.asList("/orders/viewCart", "/logout", "/orders/singleProduct", "/orders/payment");

                    if (normalUserPages.contains(request.getRequestURI())) {
                        return true;
                    } else {
                        //send unauthorized user ro previous page
                        String referer = request.getHeader("Referer");
                        response.sendRedirect(referer);
                        return false;
                    }
                }
            }
        }

        response.sendRedirect("/login");
        return false;
    }

}