package com.javainuse.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Objects;

public class Employee {
	private Integer empId;
	private String name;
	private String designation;
	@JsonIgnore
	private double salary;

	public Employee() {
	}

	public Integer getEmpId() {
		return empId;
	}

	public void setEmpId(Integer empId) {
		this.empId = empId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public double getSalary() {
		return salary;
	}

	public void setSalary(double salary) {
		this.salary = salary;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		Employee employee = (Employee) o;
		return Double.compare(employee.salary, salary) == 0 && Objects.equals(empId, employee.empId) && Objects.equals(name, employee.name) && Objects
				.equals(designation, employee.designation);
	}

	@Override
	public int hashCode() {
		return Objects.hash(empId, name, designation, salary);
	}

	@Override
	public String toString() {
		return "Employee{" +
				"empId=" + empId +
				", name='" + name + '\'' +
				", designation='" + designation + '\'' +
				", salary=" + salary +
				'}';
	}
}