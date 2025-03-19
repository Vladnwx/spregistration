package ru.savelevvn.model;

public enum UserPrivilege {
    // Общие привилегии
    READ_DATA,
    WRITE_DATA,
    DELETE_DATA,

    // Администрирование
    MANAGE_USERS,
    MANAGE_ROLES,
    AUDIT_LOGS,

    // Специфичные привилегии
    APPROVE_REQUESTS,    // Для ROLE_BOSS, ROLE_HR
    ACCESS_FINANCIAL,    // Для ROLE_ACCOUNTANT
    MANAGE_INVENTORY,    // Для ROLE_WAREHOUSE
    CONFIGURE_SYSTEM     // Для ROLE_DEV
}
