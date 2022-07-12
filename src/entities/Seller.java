package entities;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class Seller implements Serializable {

	private static final long serialVersionUID = 1L;

	private int id;
	private String name;
	private String email;
	private Date dateBirthdate;
	private Double baseSalary;
	private Department department;

	public Seller(int id, String name, String email, Date dateBirthdate, Double baseSalary, Department department) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.dateBirthdate = dateBirthdate;
		this.baseSalary = baseSalary;
		this.department = department;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getDateBirthdate() {
		return dateBirthdate;
	}

	public void setDateBirthdate(Date dateBirthdate) {
		this.dateBirthdate = dateBirthdate;
	}

	public Double getBaseSalary() {
		return baseSalary;
	}

	public void setBaseSalary(Double baseSalary) {
		this.baseSalary = baseSalary;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Seller other = (Seller) obj;
		return id == other.id;
	}

	@Override
	public String toString() {
		return "Seller [" + name + "], Department [" + department.getName() + "]";
	}

}
