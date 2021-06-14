package tbs.models;

import java.util.List;

public class EmployeeUsers {

    private List<HrMaster> employees;
    private List<Users> users;

    public EmployeeUsers(List<HrMaster> employees, List<Users> users) {
        this.employees = employees;
        this.users = users;
    }

    public List<HrMaster> getEmployees() {
        return employees;
    }

    public void setEmployees(List<HrMaster> employees) {
        this.employees = employees;
    }

    public List<Users> getUsers() {
        return users;
    }

    public void setUsers(List<Users> users) {
        this.users = users;
    }
}
