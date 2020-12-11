package com.seedcup.seedcupbackend.common.interceptor;

import com.seedcup.seedcupbackend.common.annotation.LoginRequired;
import com.seedcup.seedcupbackend.common.dao.UserMapper;
import com.seedcup.seedcupbackend.common.dto.UserBasicInfo;
import com.seedcup.seedcupbackend.common.exception.PermissionDeniedException;
import com.seedcup.seedcupbackend.common.exception.UnAuthException;
import com.seedcup.seedcupbackend.common.po.User;
import org.springframework.beans.factory.annotation.Autowired;
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
        /*
         * @Author holdice
         * @Description 拦截请求前置处理器，检查所请求路由是否需要登录，是否需要管理员，并作相应认证
         * @Date 2020/12/11 3:21 下午
         * @Param [request, response, handler]
         * @return boolean
         */
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
        /*
         * @Author holdice
         * @Description 验证登录
         * @Date 2020/12/11 3:22 下午
         * @Param [userBasicInfo]
         * @return boolean
         */
        if (userBasicInfo == null) {
            throw new UnAuthException();
        } else {
            currentUserBasicInfo.set(userBasicInfo);
            return true;
        }
    }

    private boolean authAdmin(UserBasicInfo userBasicInfo) throws PermissionDeniedException {
        /*
         * @Author holdice
         * @Description 验证管理员身份
         * @Date 2020/12/11 3:22 下午
         * @Param [userBasicInfo]
         * @return boolean
         */
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
        /*
         * @Author holdice
         * @Description 获取当前登录的用户
         * @Date 2020/11/25 9:54 下午 
         * @Param []
         * @return com.seedcup.seedcupbackend.common.po.User
         */
        return userMapper.selectById(currentUserBasicInfo.get().getUserId());
    }
}
