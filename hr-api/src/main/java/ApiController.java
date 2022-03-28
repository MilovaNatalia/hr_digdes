import models.Department;
import models.Employee;
import models.Position;

import java.util.Optional;

public class ApiController {

    // employee methods
    public Optional<Employee> createEmployee(String employeeInfo){
        return Optional.empty();
    }

    public Optional<Employee> updateEmployee(String employeeId, String employeeInfo){
        return Optional.empty();
    }

    public boolean deleteEmployee(String employeeId){
        return false;
    }

    public Optional<Employee> searchEmployee(String searchRequest){
        return Optional.empty();
    }

    public Optional<Employee> viewEmployee (String employeeId){
        return Optional.empty();
    }

    //TODO load employees from file
    public boolean loadEmployeesFromFile(String fileName){
        return false;
    }

    //department methods
    public Optional<Department> createDepartment(String departmentInfo){
        return Optional.empty();
    }

    public Optional<Department> updateDepartment(String departmentId, String departmentInfo){
        return Optional.empty();
    }

    public boolean deleteDepartment(String departmentId){
        return false;
    }

    public Optional<Department> searchDepartment(String searchRequest){
        return Optional.empty();
    }

    public Optional<Department> viewDepartment (String departmentId){
        return Optional.empty();
    }

    public boolean loadDepartmentsFromFile(String fileName){
        return false;
    }

    //TODO load departments from file

    //positions methods
    public Optional<Position> addPosition(String positionInfo){
        return Optional.empty();
    }

    public Optional<Position> updatePosition(String positionId, String positionInfo){
        return Optional.empty();
    }

    public boolean deletePosition(String positionId){
        return false;
    }

    public boolean loadPositionsFromFile(String fileName){
        return false;
    }
    //TODO load positions from file
    //notifier settings method
    public boolean updateNotifierSettings (String settings){
        return false;
    }
    //TODO load settings from file
    public boolean loadNotifierSettingsFromFile(String fileName){
        return false;
    }
}
