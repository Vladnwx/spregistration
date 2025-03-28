package ru.savelevvn.model;

import java.util.Set;

public enum UserRole {
    ROLE_SYSTEM_ADMIN(Set.of(
            UserPrivilege.MANAGE_SYSTEM,
            UserPrivilege.MANAGE_USERS,
            UserPrivilege.VIEW_AUDIT_LOGS
    )),
    ROLE_SECURITY_ADMIN(Set.of(
            UserPrivilege.MANAGE_ROLES,
            UserPrivilege.MANAGE_PERMISSIONS,
            UserPrivilege.VIEW_AUDIT_LOGS
    )),
    ROLE_USER_ADMIN(Set.of(
            UserPrivilege.MANAGE_USERS,
            UserPrivilege.RESET_PASSWORDS
    )),
    ROLE_AUDITOR(Set.of(
            UserPrivilege.VIEW_AUDIT_LOGS
    )),
    ROLE_USER(Set.of()); // Стандартный зарегистрированный пользователь

    private final Set<UserPrivilege> privileges;

    UserRole(Set<UserPrivilege> privileges) {
        this.privileges = privileges;
    }

    public Set<UserPrivilege> getPrivileges() {
        return Set.copyOf(privileges); // Возвращаем неизменяемую копию
    }

    public boolean hasPrivilege(UserPrivilege privilege) {
        return privileges.contains(privilege);
    }


//    ROLE_ANONYMOUS,      // Анонимный пользователь (не зарегистрирован)
//    ROLE_STAFF,          // Персонал (общие права сотрудников)
//    ROLE_BOSS,           // Руководитель подразделения
//    ROLE_ACCOUNTANT,     // Бухгалтер
//    ROLE_HR,             // HR-менеджер (Human Resources)
//    ROLE_WAREHOUSE,      // Складской работник
//    ROLE_CONSTRUCTION,   // Инженер-строитель
//    ROLE_OFFICE_ENG,     // Офисный инженер
//    ROLE_FOREMAN,        // Бригадир
//    ROLE_ELECTRICIAN;    // Электрик


}
