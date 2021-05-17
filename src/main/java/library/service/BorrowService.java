package library.service;

import library.HibernateConfig;
import library.entity.Book;
import library.entity.ReaderUser;
import library.exception.BookNotFoundException;
import library.exception.ReaderUserNotFoundException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class BorrowService {
    private BookService bookService = new BookService();

    public void borrowBook(Book book, ReaderUser user) {

        Session session = HibernateConfig.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            book.setUserId(user);
            session.saveOrUpdate(book);

            transaction.commit();

        } catch (Exception e) {
            transaction.rollback();
        } finally {
            session.close();
        }
    }

    public List<Book> listReaderUserBooks(int id) throws ReaderUserNotFoundException {
        Session session = HibernateConfig.openSession();
        List<Book> bookList = null;
        Transaction transaction = session.beginTransaction();
        ReaderUser readerUser1 = new ReaderUserService().getById(id);
        try {

            bookList = readerUser1.getBorrowedBooks();
            transaction.commit();

        } catch (Exception e) {
            transaction.rollback();
        } finally {
            session.close();
        }
        return bookList;
    }

    public void returnBook(Book book) throws BookNotFoundException {

        book.setUserId(null);

        new BookService().save(book);


//        Session session = HibernateConfig.openSession();
//        Transaction transaction = session.beginTransaction();
//        try {
//            session.beginTransaction();
//
//            book.setUserId(null);
//
//            session.saveOrUpdate(book);
//
//            session.getTransaction().commit();
//
//        } catch (Exception e) {
//            transaction.rollback();
//        } finally {
//            session.close();
//        }
    }

}
