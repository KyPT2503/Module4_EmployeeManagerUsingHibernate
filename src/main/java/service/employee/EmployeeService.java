package service.employee;

import model.Employee;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

public class EmployeeService implements IEmployeeService {
    @Autowired
    private EntityManager entityManager;

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<Employee> getAll() {
        TypedQuery<Employee> query = entityManager.createQuery("select e from Employee as e", Employee.class);
        return query.getResultList();
    }

    @Override
    public boolean remove(int id) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            Query query = session.createQuery("delete from Employee where id=:id");
            query.setParameter("id", id);
            query.executeUpdate();
            transaction.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return false;
    }

    @Override
    public boolean add(Employee employee) {
        Session session=null;
        Transaction transaction = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.saveOrUpdate(employee);
            transaction.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        }finally {
            if (session != null) {
                session.close();
            }
        }
        return false;
    }

    @Override
    public boolean update(int id, Employee employee) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            Query query = session.createQuery("update Employee set name=:name,email=:email,address=:address where id=:id");
            query.setParameter("name", employee.getName());
            query.setParameter("email", employee.getEmail());
            query.setParameter("address", employee.getAddress());
            query.setParameter("id", id);
            query.executeUpdate();
            /*session.saveOrUpdate(employee);*/
            transaction.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        }finally {
            if (session != null) {
                session.close();
            }
        }
        return false;
    }

    @Override
    public Employee getById(int id) {
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("select e from Employee as e where e.id=:id");
        query.setParameter("id", id);
        List employees = query.getResultList();
        if (employees.size() > 0) {
            return (Employee) employees.get(0);
        }
        return null;
    }
}
