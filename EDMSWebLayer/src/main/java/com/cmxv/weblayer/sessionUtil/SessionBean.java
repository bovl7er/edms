package com.cmxv.weblayer.sessionUtil;

import com.cmxv.modellayer.DBentities.UserWithRights;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;

public class SessionBean {

    private static final Logger log = Logger.getLogger(SessionBean.class);

//--------------------------------------------------------------------------------------------------------------------
    /**
     * Получение сессии
     *
     * @return сессию
     */
    public static HttpSession getSession() {
        return (HttpSession) FacesContext.getCurrentInstance()
                .getExternalContext().getSession(false);
    }
//--------------------------------------------------------------------------------------------------------------------

    /**
     * Получение запроса
     *
     * @return запрос типа HttpServletRequest
     */
    public static HttpServletRequest getRequest() {
        return (HttpServletRequest) FacesContext.getCurrentInstance()
                .getExternalContext().getRequest();
    }
//--------------------------------------------------------------------------------------------------------------------

    /**
     * Получение пользователя из сессии
     *
     * @return пользователя из аттрибута sessionUser
     */
    public static UserWithRights getUser() {
        HttpSession session = getSession();
        try {
            return (UserWithRights) session.getAttribute("sessionUser");
        } catch (Exception error) {
            log.error("Ошибка получения ползователя из сессии", error);
            return null;
        }
    }
//--------------------------------------------------------------------------------------------------------------------

    /**
     * Получение ид пользователя из сессии
     *
     * @return ид пользователя через аттрибута sessionUser
     */
    public static Integer getUserId() {
        HttpSession session = getSession();
        UserWithRights user;
        try {
            user = (UserWithRights) session.getAttribute("sessionUser");
        } catch (Exception error) {
            log.error("Ошибка получения ползователя из сессии", error);
            return null;
        }

        return user.getUserId();

    }
//--------------------------------------------------------------------------------------------------------------------
}
