package br.com.fiap.javacare.user.util;

import br.com.fiap.javacare.user.model.User;

import java.util.UUID;

public class AccessControlUtil {
    public static boolean canViewHistory(User requester, UUID targetUserId) {
        return switch (requester.getType()) {
            case MEDIC, NURSE -> true;
            case PATIENT -> requester.getId()
                    .equals(targetUserId);
        };
    }
}