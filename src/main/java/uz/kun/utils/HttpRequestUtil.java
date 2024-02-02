package uz.kun.utils;

import jakarta.servlet.http.HttpServletRequest;
import uz.kun.dto.JWTDTO;
import uz.kun.enums.ProfileRole;
import uz.kun.exception.ForbiddenException;

public class HttpRequestUtil {
    public static Integer getProfileId(HttpServletRequest request, ProfileRole... requiredRoleList) {
        Integer id = (Integer) request.getAttribute("id");
        ProfileRole role = (ProfileRole) request.getAttribute("role");

        if(requiredRoleList.length==0){
            return id;
            
        }
        for (ProfileRole requiredRole : requiredRoleList ){
            if (role.equals(requiredRole)) {
                return id;

            }
        }
        throw new ForbiddenException("Method not allowed");
    }

    public static JWTDTO getJWTDTO(HttpServletRequest request, ProfileRole... requiredRoleList) {
        Integer id = (Integer) request.getAttribute("id");
        ProfileRole role = (ProfileRole) request.getAttribute("role");


        JWTDTO dto = new JWTDTO(id,role);
        for (ProfileRole requiredRole : requiredRoleList) {
            if (role.equals(requiredRole)) {
                return dto;
            }
        }
        throw new ForbiddenException("Method not allowed");
    }



}
