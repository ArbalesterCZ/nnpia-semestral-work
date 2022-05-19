package st43189.upce.cz.controller;

import st43189.upce.cz.model.Employee;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin()
@RestController
@RequestMapping({ "/employees" })
public class TestController {

	private final List<Employee> employees = createList();

	@GetMapping(produces = "application/json")
	public List<Employee> firstPage() {
		return employees;
	}

	@DeleteMapping(path = { "/{id}" })
	public Employee delete(@PathVariable("id") Integer id) {
		Employee deletedEmp = null;
		for (Employee emp : employees) {
			if (emp.getEmpId().equals(id)) {
				employees.remove(emp);
				deletedEmp = emp;
				break;
			}
		}
		return deletedEmp;
	}

	@PostMapping
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public Employee create(@RequestBody Employee user) {
		user.setEmpId(employees.size()+1);
		user.setSalary(getRandomDouble());
		employees.add(user);
		return user;
	}

	private double getRandomDouble() {
		int rangeMin = 100;
		int rangeMax = 99999;
		Random r = new Random();
		return rangeMin + (rangeMax - rangeMin) * r.nextDouble();
	}

	private static List<Employee> createList() {
		List<Employee> tempEmployees = new ArrayList<>();
		Employee emp1 = new Employee();
		emp1.setName("emp1");
		emp1.setDesignation("manager");
		emp1.setEmpId(1);
		emp1.setSalary(3000);

		Employee emp2 = new Employee();
		emp2.setName("emp2");
		emp2.setDesignation("developer");
		emp2.setEmpId(2);
		emp2.setSalary(3000);

		Employee emp3 = new Employee();
		emp3.setName("emp3");
		emp3.setDesignation("designer");
		emp3.setEmpId(3);
		emp3.setSalary(3000);

		tempEmployees.add(emp1);
		tempEmployees.add(emp2);
		tempEmployees.add(emp3);
		return tempEmployees;
	}

}