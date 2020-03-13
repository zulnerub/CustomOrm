import entities.Address;
import entities.Employee;
import entities.Project;
import entities.Town;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;

public class Engine implements Runnable {

    private final EntityManager entityManager;
    private final BufferedReader reader;

    public Engine(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.reader = new BufferedReader(new InputStreamReader(System.in));
    }

    @Override
    public void run() {
        //Ex 2
        // this.removeObjectsEx();
        //ex3

        //      try {
        //         this.containsEmployee();
        //     } catch (IOException e) {
        //         e.printStackTrace();
        //     }

        //ex 4
        //this.employeeSalaryOver50000();

        // ex5
        //this.employeesFromDepartments();
        // ex6
        //   try {
        //       this.addingNewAddressAndAddItToEmployee();
        //   } catch (IOException e) {
        //       e.printStackTrace();
        //   }

        //ex 7
        //this.addressesWithEmployeeCount();
        //ex8
        //   try {
        //       this.getEmployeeWithProject();
        //   } catch (IOException e) {
        //        e.printStackTrace();
        //   }
        //ex9
        //  this.getTenLatestProjects();
        //ex 10
        // this.increaseSalary();
        //ex 11
        //try {
       //     this.townsDelete();
       // } catch (IOException e) {
       //     e.printStackTrace();
      //  }
        //ex 12
      //  try {
      //      this.findEmployeeByFirstName();
      //  } catch (IOException e) {
     //       e.printStackTrace();
     //   }
    // ex 13
        this.maxSalaryByDepartment();
    }

    private void maxSalaryByDepartment() {
        this.entityManager
                .createQuery("SELECT e FROM Employee AS e " +
                        "GROUP BY e.department HAVING e.salary NOT BETWEEN 30000 AND 70000 " +
                        "ORDER BY e.salary", Employee.class)
                .getResultStream()
                .forEach(e -> {
                    System.out.printf("%s - %.2f\n", e.getDepartment().getName(), e.getSalary());
                });
    }

    private void findEmployeeByFirstName() throws IOException {
        System.out.println("Please enter search pattern:");
        String input = reader.readLine();
        input += "%";
        this.entityManager
                .createQuery("SELECT e FROM Employee AS e " +
                        "WHERE e.firstName LIKE :pattern ", Employee.class)
                .setParameter("pattern", input)
                .getResultStream()
                .forEach(e -> {
                    System.out.printf("%s %s - %s - ($%.2f)\n", e.getFirstName(), e.getLastName(), e.getJobTitle(), e.getSalary());
                });
    }

    private void townsDelete() throws IOException {
        System.out.println("Please enter town to delete:");
        String input = reader.readLine();

        List<Employee> employees = this.entityManager
                .createQuery("SELECT e FROM Employee AS e " +
                        "WHERE e.address.town.name = :tName", Employee.class)
                .setParameter("tName", input)
                .getResultList();

        this.entityManager.getTransaction().begin();
        employees.forEach(e -> {
            e.setAddress(null);

        });

        this.entityManager.flush();
        this.entityManager.getTransaction().commit();

        this.entityManager.getTransaction().begin();
        List<Address> addresses = this.entityManager
                .createQuery("SELECT a FROM Address AS a " +
                        "WHERE a.town.name = :tName", Address.class)
                .setParameter("tName", input)
                .getResultList();
        int count = addresses.size();
        addresses.forEach(a -> {
            a.setTown(null);
        });

        this.entityManager.flush();
        this.entityManager.getTransaction().commit();

        this.entityManager.getTransaction().begin();
        this.entityManager
                .createQuery("DELETE FROM Town " +
                        "WHERE name = :tName")
                .setParameter("tName", input)
                .executeUpdate();

        this.entityManager.flush();
        this.entityManager.getTransaction().commit();
        System.out.printf("%d %s in %s deleted\n", count, count > 1 ? "addresses" : "address", input);

    }

    private void increaseSalary() {
        this.entityManager
                .createQuery("SELECT e FROM Employee AS e " +
                        "WHERE e.department.id IN (1,2,4,11)", Employee.class)
                .getResultStream()
                .forEach(e -> {
                    e.setSalary(e.getSalary().multiply(new BigDecimal("1.12")));
                    System.out.printf("%s %s ($%.2f)\n", e.getFirstName(), e.getLastName(), e.getSalary());
                });
    }

    private void getTenLatestProjects() {
        this.entityManager
                .createQuery("SELECT p FROM Project AS p " +
                        "ORDER BY p.startDate DESC ", Project.class)
                .setFirstResult(0)
                .setMaxResults(10)
                .getResultStream()
                .sorted(Comparator.comparing(Project::getName))
                .forEach(p -> {
                    System.out.printf("Project name: %s\n" +
                                    "        Project Description: %s\n" +
                                    "        Project Start Date: %s\n" +
                                    "        Project End Date: %s\n",
                            p.getName(), p.getDescription(), p.getStartDate(), p.getEndDate());
                });

    }

    private void getEmployeeWithProject() throws IOException {
        System.out.println("Please enter employee id:");
        int employeeId = Integer.parseInt(reader.readLine());

        this.entityManager
                .createQuery("SELECT e FROM Employee AS e " +
                        "WHERE e.id = :eId", Employee.class)
                .setParameter("eId", employeeId)
                .getResultStream()
                .forEach(e -> {
                    System.out.printf("%s %s - %s\n", e.getFirstName(), e.getLastName(), e.getJobTitle());
                    e.getProjects().forEach(p -> {
                        System.out.printf("      %s\n", p.getName());
                    });
                });
    }

    private void addressesWithEmployeeCount() {
        this.entityManager
                .createQuery("SELECT a FROM Address AS a " +
                        "GROUP BY a.employees.size " +
                        "ORDER BY a.employees.size DESC", Address.class)
                .setFirstResult(0)
                .setMaxResults(10)
                .getResultStream()
                .forEach(a -> {
                    System.out.printf("%s - %d %s\n", a.getText(), a.getEmployees().size(), a.getEmployees().size() > 1 ? "employees" : "employee");
                });

    }

    private void addingNewAddressAndAddItToEmployee() throws IOException {
        System.out.println("Enter emp last name: ");
        String lastName = reader.readLine();

        Employee employee = this.entityManager
                .createQuery("SELECT e FROM Employee AS e " +
                        "WHERE e.lastName = :name", Employee.class)
                .setParameter("name", lastName)
                .getSingleResult();

        Address address = this.createNewAddress("Vitoshka 15");

        this.entityManager.getTransaction().begin();
        this.entityManager.detach(employee);
        employee.setAddress(address);
        this.entityManager.merge(employee);

        this.entityManager.flush();
        this.entityManager.getTransaction().commit();
    }

    private Address createNewAddress(String textContent) {
        Address address = new Address();
        address.setText(textContent);

        this.entityManager.getTransaction().begin();
        this.entityManager.persist(address);
        this.entityManager.getTransaction().commit();

        return address;
    }

    private void employeesFromDepartments() {
        this.entityManager
                .createQuery("SELECT e FROM Employee AS e " +
                        "WHERE e.department.name = 'Research and Development' " +
                        "ORDER BY e.salary, e.id ", Employee.class)
                .getResultStream()
                .forEach(e -> {
                    System.out.printf("%s %s from %s - $%.2f\n",
                            e.getFirstName(), e.getLastName(), e.getDepartment(), e.getSalary());
                });
    }

    private void containsEmployee() throws IOException {
        System.out.println("Enter employee full name: ");
        String input = reader.readLine();

        try {
            Employee employee = this.entityManager.createQuery("SELECT e FROM Employee AS e " +
                    "WHERE concat(e.firstName, ' ', e.lastName) = :name", Employee.class)
                    .setParameter("name", input)
                    .getSingleResult();
            System.out.println("Yes");
        } catch (NoResultException nre) {
            System.out.println("No");
        }

    }

    private void removeObjectsEx() {
        List<Town> towns = this.entityManager
                .createQuery("SELECT t FROM Town AS t " +
                        "WHERE length(t.name) > 5", Town.class)
                .getResultList();

        this.entityManager.getTransaction().begin();
        towns.forEach(this.entityManager::detach);

        for (Town t : towns) {
            t.setName(t.getName().toLowerCase());
        }

        towns.forEach(this.entityManager::merge);

        this.entityManager.flush();
        this.entityManager.getTransaction().commit();
    }

    private void employeeSalaryOver50000() {
        this.entityManager
                .createQuery("SELECT e FROM Employee AS e " +
                        "WHERE e.salary > 50000", Employee.class)
                .getResultStream()
                .forEach(e -> System.out.printf("%s%n", e.getFirstName()));
    }

}
