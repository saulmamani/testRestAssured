import Constants.EmployeeEndpoint;
import Entities.Employee;
import Utils.EmployeeRequest;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class RestAssuredTest {
    @Test
    public void getEmployeesTest(){
        Response response = EmployeeRequest.get(EmployeeEndpoint.GET_EMPLOYEES);
        response.then().assertThat().statusCode(200);
        response.then().assertThat().body("size()", Matchers.not(0));
    }

    @Test
    public void getEmployeeWithIdTest(){
        Response response = EmployeeRequest.getWithId(EmployeeEndpoint.GET_EMPLOYEE,"2");
        response.then().assertThat().statusCode(200);
        response.then().assertThat().body("size()", Matchers.equalTo(3));
        response.then().assertThat().body("data.id", Matchers.equalTo(2));
        response.then().assertThat().body("data.employee_name", Matchers.equalTo("Garrett Winters"));
        response.then().assertThat().body("data.employee_salary", Matchers.equalTo(170750));

        response.then().assertThat().body("message", Matchers.equalTo("Successfully! Record has been fetched."));
    }

    @Test
    public void postEmployeeTest() throws JsonProcessingException {
        Employee employee = new Employee();
        employee.setName("Diego");
        employee.setAge("28");
        employee.setSalary("13000");

        ObjectMapper mapper = new ObjectMapper();
        String payload = mapper.writeValueAsString(employee);

        Response response = EmployeeRequest.post(EmployeeEndpoint.POST_EMPLOYEE,payload);

        response.then().assertThat().statusCode(200);
        response.then().assertThat().body("data.name", Matchers.equalTo(employee.getName()));
        response.then().assertThat().body("data.salary", Matchers.equalTo(employee.getSalary()));
        response.then().assertThat().body("data.age", Matchers.equalTo(employee.getAge()));
        response.then().assertThat().body("data.id", Matchers.not(""));
        response.then().assertThat().body("message", Matchers.equalTo("Successfully! Record has been added."));
    }

    @Test
    public void putEmployeeTest() throws JsonProcessingException {
        Employee employee = new Employee();
        employee.setName("Carlos");
        employee.setAge("20");
        employee.setSalary("5000");

        ObjectMapper mapper = new ObjectMapper();
        String payload = mapper.writeValueAsString(employee);

        Response response = EmployeeRequest.put(EmployeeEndpoint.PUT_EMPLOYEE, "2", payload);
        response.then().assertThat().statusCode(200);
        response.then().assertThat().body("status", Matchers.equalTo("success"));
        response.then().assertThat().body("data.name", Matchers.equalTo(employee.getName()));
        response.then().assertThat().body("data.salary", Matchers.equalTo(employee.getSalary()));
        response.then().assertThat().body("data.age", Matchers.equalTo(employee.getAge()));
        response.then().assertThat().body("message", Matchers.equalTo("Successfully! Record has been updated."));
    }

    @Test
    public void deleteEmployeeTest(){
        Response response = EmployeeRequest.delete(EmployeeEndpoint.DELETE_EMPLOYEE, "10");
        response.then().assertThat().statusCode(200);
        response.then().assertThat().body("status", Matchers.equalTo("success"));
        response.then().assertThat().body("data", Matchers.equalTo("10"));
        response.then().assertThat().body("message", Matchers.equalTo("Successfully! Record has been deleted"));
    }
}
