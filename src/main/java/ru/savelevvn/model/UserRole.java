package ru.savelevvn.model;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public enum UserRole {
    ROLE_ADMIN,          // Полный доступ ко всем функциям
    ROLE_SUPERADMIN,     // Расширенные права (например, управление администраторами)
    ROLE_USER,           // Стандартный зарегистрированный пользователь
    ROLE_ANONYMOUS,      // Анонимный пользователь (не зарегистрирован)
    ROLE_STAFF,          // Персонал (общие права сотрудников)
    ROLE_BOSS,           // Руководитель подразделения
    ROLE_DEV,            // Разработчик (доступ к API/инструментам)
    ROLE_ACCOUNTANT,     // Бухгалтер
    ROLE_HR,             // HR-менеджер (Human Resources)
    ROLE_WAREHOUSE,      // Складской работник
    ROLE_CONSTRUCTION,   // Инженер-строитель
    ROLE_OFFICE_ENG,     // Офисный инженер
    ROLE_FOREMAN,        // Бригадир
    ROLE_ELECTRICIAN;    // Электрик

    // Метод для получения привилегий, связанных с ролью
    public Set<UserPrivilege> getPrivileges() {
        Set<UserPrivilege> privileges = new HashSet<>();
        switch (this) {
            case ROLE_SUPERADMIN:
                privileges.addAll(Arrays.asList(UserPrivilege.values())); // Все привилегии
                break;
            case ROLE_ADMIN:
                privileges.addAll(Arrays.asList(UserPrivilege.values())); // Все привилегии
                break;
            case ROLE_USER:
                privileges.add(UserPrivilege.READ_DATA);
                break;
            case ROLE_HR:
                privileges.add(UserPrivilege.MANAGE_USERS);
                privileges.add(UserPrivilege.MANAGE_ROLES);
                privileges.add(UserPrivilege.APPROVE_REQUESTS);
                break;
            case ROLE_ACCOUNTANT:
                privileges.add(UserPrivilege.ACCESS_FINANCIAL);
                break;
            case ROLE_DEV:
                privileges.add(UserPrivilege.CONFIGURE_SYSTEM);
                break;
            case ROLE_BOSS:
                privileges.add(UserPrivilege.APPROVE_REQUESTS);
                privileges.add(UserPrivilege.MANAGE_USERS);
                break;
            case ROLE_WAREHOUSE:
                privileges.add(UserPrivilege.MANAGE_INVENTORY);
                break;
            case ROLE_CONSTRUCTION:
                privileges.add(UserPrivilege.READ_DATA);
                privileges.add(UserPrivilege.WRITE_DATA);
                break;
            case ROLE_OFFICE_ENG:
                privileges.add(UserPrivilege.READ_DATA);
                privileges.add(UserPrivilege.WRITE_DATA);
                break;
            case ROLE_STAFF:
                privileges.add(UserPrivilege.READ_DATA);
                privileges.add(UserPrivilege.WRITE_DATA);
                break;
            case ROLE_FOREMAN:
                privileges.add(UserPrivilege.READ_DATA);
                privileges.add(UserPrivilege.WRITE_DATA);
                break;
            case ROLE_ELECTRICIAN:
                privileges.add(UserPrivilege.READ_DATA);
                break;
        }
        return privileges;
    }
}
