package com.seedcup.backend.common.interceptor;

import com.seedcup.backend.common.annotation.LoginRequired;
import com.seedcup.backend.common.dao.UserMapper;
import com.seedcup.backend.common.dto.UserBasicInfo;
import com.seedcup.backend.common.exception.PermissionDeniedException;
import com.seedcup.backend.common.exception.UnAuthException;
import com.seedcup.backend.common.po.User;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

public class AuthInterceptor implements HandlerInterceptor {

    private static final ThreadLocal<UserBasicInfo> currentUserBasicInfo = new ThreadLocal<>();

    private static UserMapper userMapper;

    public AuthInterceptor (UserMapper userMapper) {
        AuthInterceptor.userMapper = userMapper;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler){
        if (!(handler instanceof HandlerMethod)) return true;
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        if (method.isAnnotationPresent(LoginRequired.class)) {
            LoginRequired loginRequired = method.getAnnotation(LoginRequired.class);
            UserBasicInfo userBasicInfo = (UserBasicInfo) request.getSession().getAttribute("userBasicInfo");
            if (authLogIn(userBasicInfo)) {
                if (loginRequired.needAdmin()) return authAdmin(userBasicInfo);
                return true;
            }
            return authLogIn(userBasicInfo);
        }
        return true;
    }

    private boolean authLogIn(UserBasicInfo userBasicInfo) throws UnAuthException {
        if (userBasicInfo == null) {
            throw new UnAuthException();
        } else {
            currentUserBasicInfo.set(userBasicInfo);
            return true;
        }
    }

    private boolean authAdmin(UserBasicInfo userBasicInfo) throws PermissionDeniedException {
        if (userBasicInfo.getIsAdmin()) {
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
        return userMapper.selectById(currentUserBasicInfo.get().getUserId());
    }

    public static Integer getCurrentUserId() {
        return currentUserBasicInfo.get().getUserId();
    }
}
