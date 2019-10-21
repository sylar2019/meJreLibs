package me.java.library.component.acl;

import com.google.common.base.Strings;
import me.java.library.common.model.dto.Result;
import me.java.library.component.acl.model.AclUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.http.HttpServletResponse;

/**
 * File Name             :  AclUtils
 *
 * @author :  sylar
 * @create :  2018/11/17
 * Description           :
 * Reviewed By           :
 * Reviewed On           :
 * Version History       :
 * Modified By           :
 * Modified Date         :
 * Comments              :
 * CopyRight             : COPYRIGHT(c) xxx.com   All Rights Reserved
 * *******************************************************************************************
 */
public class AclUtils {
    private static Logger log = LoggerFactory.getLogger(AclUtils.class);

    public static AclUser getLoginUser() {
        SecurityContext ctx = SecurityContextHolder.getContext();
        if (ctx != null) {
            Authentication auth = ctx.getAuthentication();
            if (auth != null && auth.getPrincipal() != null) {
                if (auth.getPrincipal() instanceof AclUser) {
                    return (AclUser) auth.getPrincipal();
                }
            }
        }
        return null;
    }

    public static String trimResourceUrl(String url) {
        if (Strings.isNullOrEmpty(url)) {
            return url;
        }
        String slash = "/";
        url = url.replace("*", "");
        while (url.endsWith(slash)) {
            int index = url.lastIndexOf(slash);
            if (index > 0) {
                url = url.substring(0, index);
            }
        }
        return url;
    }

    public static void output(HttpServletResponse response, Result<?> result) {
        output(response, result, HttpServletResponse.SC_OK);
    }

    public static void output(HttpServletResponse response, Result<?> result, int httpStatus) {
        try {
            response.setStatus(httpStatus);
            response.setContentType(AclConst.CONTENT_TYPE);
            response.getWriter().write(result.toString());
            response.getWriter().flush();
            response.getWriter().close();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
