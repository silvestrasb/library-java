package library.service;

import library.HibernateConfig;
import library.entity.Book;
import library.exception.AuthorDoesNotExistsException;
import library.exception.BookNotFoundException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class BookService {

    public void save(Book book) {
        Session session = HibernateConfig.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.saveOrUpdate(book);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
        } finally {
            session.close();
        }
    }

    public Book getById(int id) {
        List<Book> books = getAll("id", id, true);
        return books.size() > 0 ? books.get(0) : null;
    }

    public void delete(Book book) {
        Session session = HibernateConfig.openSession();
        Transaction transaction = session.beginTransaction();

        try {
            session.delete(book);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
        } finally {
            session.close();
        }
    }

    public Book findBookByTitle(String title) throws BookNotFoundException {
        List<Book> books = getAll("title", title, true);
        Book book = books.size() > 0 ? books.get(0) : null;
        if (book != null) {
            return book;
        }
        throw new BookNotFoundException();
    }

    public List<Book> findBooksByGenre(String genre) throws BookNotFoundException {
        List<Book> books = getAll("genre", genre, false);
        if (books.isEmpty()) {
            throw new BookNotFoundException();
        }
        return books;
    }

    public List<Book> findBookByAuthor(String authorsName, String authorsSurname) throws AuthorDoesNotExistsException {
        Session session = HibernateConfig.openSession();
        Transaction transaction = session.beginTransaction();
        List<Book> books = new ArrayList<>();

        try {
            Query<Book> query = session.createQuery("FROM Book WHERE authorsName = :authorsName" +
                    " AND authorsSurname=:authorsSurname AND userId=null", Book.class);
            query.setParameter("authorsName", authorsName);
            query.setParameter("authorsSurname", authorsSurname);
            books = query.getResultList();
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        } finally {
            session.close();
        }

        if (books.isEmpty() || books == null) {
            throw new AuthorDoesNotExistsException();
        }
        return books;
    }

    public List<Book> getBookList() {
        Session session = HibernateConfig.openSession();
        Transaction transaction = session.beginTransaction();
        List<Book> books = new ArrayList<>();

        try {
            Query<Book> query = session.createQuery("FROM Book where userId=null", Book.class);
            books = query.getResultList();
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
            books = null;
        } finally {
            session.close();
        }

        return books;
    }

    public void remove(Book book) {
        Session session = HibernateConfig.openSession();
        Transaction transaction = session.beginTransaction();

        try {
            session.delete(book);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
        } finally {
            session.close();
        }
    }

    private List<Book> getAll(String col, Object val, boolean limitOne) {
        Session session = HibernateConfig.openSession();
        Transaction transaction = session.beginTransaction();
        List<Book> books = new ArrayList<>();

        try {
            Query<Book> query = session.createQuery(String.format("FROM Book WHERE %s = :%s AND userId=null", col, col), Book.class);
            query.setParameter(col, val);

            if (limitOne) {
                query.setMaxResults(1);
            }

            books = query.getResultList();
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
        } finally {
            session.close();
        }

        return books;
    }

}
