package br.com.fiap.javacare.user.util;

import br.com.fiap.javacare.user.exception.AccessDeniedException;
import br.com.fiap.javacare.user.model.User;
import br.com.fiap.javacare.user.model.UserType;

import java.util.UUID;

public class AccessControlUtil {
    public static boolean canViewHistory(User requester, UUID targetUserId) {
        return switch (requester.getType()) {
            case MEDIC, NURSE -> true;
            case PATIENT -> requester.getId().equals(targetUserId);
        };
    }
}

