package com.simplycindy;

import com.simplycindy.controllers.UserController;
import com.simplycindy.models.User;
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
        List<String> nonAuthPages = Arrays.asList("/login", "/register", "/product/display", "/product/adminIndex", "/home", "/product/aProduct", "/orders/viewCart");

        // Require sign-in for auth pages
        if ( nonAuthPages.contains(request.getRequestURI()) ) {
            return true;
        } else {

            String email = (String) request.getSession().getAttribute(UserController.userSessionKey);

            if (email != null) {
                User user = userDao.findByEmail(email);

                boolean isUserAnAdmin = user.isAdmin();
                if(isUserAnAdmin) {
                    return true;
                } else {
                    // TODO
                    return true; // TODO Fix
                    // Create list of admin pages
                    // See if normal user is trying to access an admin page, and return false if that is the case
                    // If normal user is trying to access non-admin page return true
                }
            }
        }

        response.sendRedirect("/login");
        return false;
    }

}