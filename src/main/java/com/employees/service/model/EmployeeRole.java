package com.employees.service.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "tbl_employee_role")
public class EmployeeRole {
    @Id
    @GeneratedValue
    private Long id;
    @Column(nullable = false, unique = true, updatable = false)
    private String name;

    @OneToMany(targetEntity = Employee.class, mappedBy = "role", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Employee> employees;

    public EmployeeRole() {
    }

    public EmployeeRole(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof EmployeeRole)) {
            return false;
        }
        EmployeeRole paramObj = (EmployeeRole) obj;
        EqualsBuilder eb = new EqualsBuilder();
        eb.append(getName(),paramObj.getName());
        return eb.isEquals();
    }

    @Override
    public int hashCode() {
        HashCodeBuilder hcb = new HashCodeBuilder();
        hcb.append(getName());
        return hcb.toHashCode();
    }
}