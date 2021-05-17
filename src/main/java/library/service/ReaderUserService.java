package library.service;

import library.HibernateConfig;
import library.entity.ReaderUser;
import library.exception.EmailAlreadyExistsException;
import library.exception.InvalidEmailException;
import library.exception.ReaderUserNotFoundException;
import library.util.EmailValidator;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class ReaderUserService {

    public void save(ReaderUser user) throws EmailAlreadyExistsException, InvalidEmailException {
        emailValidator(user.getEmail());
        Session session = HibernateConfig.openSession();
        Transaction transaction = session.beginTransaction();

        try {
            session.saveOrUpdate(user);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            throw new InvalidEmailException();
        } finally {
            session.close();
        }
    }

    public ReaderUser getById(int id) {
        List<ReaderUser> readerUsers = getAll("id", id, true);
        return readerUsers.size() > 0 ? readerUsers.get(0) : null;
    }

    public void delete(ReaderUser readerUser){
        Session session = HibernateConfig.openSession();
        Transaction transaction = session.beginTransaction();

        try {
            session.delete(readerUser);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
        } finally {
            session.close();
        }
    }

    public ReaderUser get(String email, String password) throws ReaderUserNotFoundException {
        Session session = HibernateConfig.openSession();
        Transaction transaction = session.beginTransaction();
        ReaderUser readerUser = null;

        try {
            Query<ReaderUser> query = session.createQuery("FROM ReaderUser WHERE " +
                    "email=:email AND password=:password", ReaderUser.class);
            query.setParameter("email", email);
            query.setParameter("password", password);

            readerUser = query.getSingleResult();
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        } finally {
            session.close();
        }
        if (readerUser == null) {
            throw new ReaderUserNotFoundException();
        }

        return readerUser;
    }

    private void emailValidator(String userEmail) throws InvalidEmailException {
        EmailValidator.validEmail(userEmail);
    }


    private List<ReaderUser> getAll(String col, Object val, boolean limitOne) {
        Session session = HibernateConfig.openSession();
        Transaction transaction = session.beginTransaction();
        List<ReaderUser> readerUsers = new ArrayList<>();

        try {
            Query<ReaderUser> query = session.createQuery(String.format("FROM ReaderUser WHERE %s = :%s", col, col), ReaderUser.class);
            query.setParameter(col, val);

            if (limitOne) {
                query.setMaxResults(1);
            }

            readerUsers = query.getResultList();
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
        } finally {
            session.close();
        }

        return readerUsers;
    }
}
