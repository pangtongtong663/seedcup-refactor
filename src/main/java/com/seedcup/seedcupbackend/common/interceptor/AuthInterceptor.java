package com.seedcup.seedcupbackend.common.interceptor;

import com.seedcup.seedcupbackend.common.annotation.LoginRequired;
import com.seedcup.seedcupbackend.common.exception.PermissionDeniedException;
import com.seedcup.seedcupbackend.common.exception.UnAuthException;
import com.seedcup.seedcupbackend.common.po.User;
import org.springframework.context.annotation.Bean;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;

public class AuthInterceptor implements HandlerInterceptor {

    private static final ThreadLocal<User> currentUser = new ThreadLocal<>();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler){
        if (!(handler instanceof HandlerMethod)) return true;
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        if (method.isAnnotationPresent(LoginRequired.class)) {
            LoginRequired loginRequired = method.getAnnotation(LoginRequired.class);
            User user = (User) request.getSession().getAttribute("userInfo");
            if (authLogIn(user)) {
                if (loginRequired.needAdmin()) return authAdmin(user);
                return true;
            }
            return authLogIn(user);
        }
        return true;
    }

    private boolean authLogIn(User user) throws UnAuthException {
        if (user == null) {
            throw new UnAuthException();
        } else {
            currentUser.set(user);
            return true;
        }
    }

    private boolean authAdmin(User user) throws PermissionDeniedException {
        if (user.getIsAdmin()) {
            return true;
        } else {
            throw new PermissionDeniedException();
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView){

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex){

    }

    public static User getCurrentUser() {
        /*
         * @Author holdice
         * @Description 获取当前登录的用户
         * @Date 2020/11/25 9:54 下午 
         * @Param []
         * @return com.seedcup.seedcupbackend.common.po.User
         */
        return currentUser.get();
    }
}
