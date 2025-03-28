package ru.savelevvn.model;

public enum UserPrivilege {
    // Системные привилегии
    MANAGE_SYSTEM,

    // Управление пользователями
    MANAGE_USERS,
    RESET_PASSWORDS,
    LOCK_USERS,

    // Управление безопасностью
    MANAGE_ROLES,
    MANAGE_PERMISSIONS,

    // Аудит
    VIEW_AUDIT_LOGS,
    EXPORT_AUDIT_LOGS,

    // Специфичные привилегии
    APPROVE_REQUESTS,    // Для ROLE_BOSS, ROLE_HR
    ACCESS_FINANCIAL,    // Для ROLE_ACCOUNTANT
    MANAGE_INVENTORY,    // Для ROLE_WAREHOUSE

}
