package br.com.fiap.javacare.user.model;

public enum UserType {
    MEDIC,
    NURSE,
    PATIENT;

    public boolean canViewAllHistory() {
        return this == MEDIC || this == NURSE;
    }

    public boolean canEditHistory() {
        return this == MEDIC;
    }

    public boolean canRegisterConsultation() {
        return this == NURSE;
    }

    public boolean canViewOwnHistoryOnly() {
        return this == PATIENT;
    }
}
