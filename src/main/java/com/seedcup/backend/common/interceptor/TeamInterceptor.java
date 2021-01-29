package com.seedcup.backend.common.interceptor;

import com.seedcup.backend.common.annotation.TeamRequired;
import com.seedcup.backend.common.dao.TeamMapper;
import com.seedcup.backend.common.exception.NoTeamException;
import com.seedcup.backend.common.exception.PermissionDeniedException;
import com.seedcup.backend.common.po.Team;
import com.seedcup.backend.common.po.User;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

public class TeamInterceptor implements HandlerInterceptor {

    private static final ThreadLocal<Team> currentTeam = new ThreadLocal<>();

    private static TeamMapper teamMapper;

    public TeamInterceptor(TeamMapper teamMapper) {
        TeamInterceptor.teamMapper = teamMapper;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)) return true;
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        if (method.isAnnotationPresent(TeamRequired.class)) {
            TeamRequired teamRequired = method.getAnnotation(TeamRequired.class);
            return checkTeam(teamRequired.needLeader());
        }
        return true;
    }

    private boolean checkTeam(boolean needLeader) throws NoTeamException, PermissionDeniedException {
        User user = AuthInterceptor.getCurrentUser();
        if (user.getTeamId() == -1) throw new NoTeamException();
        Team team = teamMapper.selectById(user.getTeamId());
        currentTeam.set(team);
        if (needLeader && !team.getLeaderId().equals(user.getId())) {
            throw new PermissionDeniedException();
        } else {
            return true;
        }
    }

    public static Team getCurrentTeam() {
        return currentTeam.get();
    }


    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
