package com.employees.service.model;

public enum EmployeeRolesEnum {
    ADMIN_ROLE {
        @Override
        public String toString() {
            return "ADMIN_ROLE";
        }
    },
    INVENTORY_MANAGER_ROLE {
        @Override
        public String toString() {
            return "INVENTORY_MANAGER_ROLE";
        }
    },
    ORDER_MANAGER_ROLE {
        @Override
        public String toString() {
            return "ORDER_MANAGER_ROLE";
        }
    }
}
