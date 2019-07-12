package me.util.acl;

import com.google.common.base.Strings;
import me.util.acl.model.AclUser;
import me.util.pojo.dto.Result;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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

    public static void output(HttpServletResponse response, Result<?> result) throws IOException {
        output(response, result, HttpServletResponse.SC_OK);
    }

    public static void output(HttpServletResponse response, Result<?> result, int httpStatus) throws IOException {
        response.setStatus(httpStatus);
        response.setContentType(AclConst.CONTENT_TYPE);
        response.getWriter().write(result.toString());
        response.getWriter().flush();
        response.getWriter().close();
    }
}
