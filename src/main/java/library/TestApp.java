package library;

import library.exception.BookNotFoundException;
import library.exception.ReaderUserNotFoundException;

public class TestApp {
    public static void main(String[] args) throws ReaderUserNotFoundException, BookNotFoundException {
        HibernateConfig.buildSessionFactory();
//        new ReaderUserService().listReaderUserBooks(new ReaderUserService().get("123", "123")).forEach(System.out::println);
//        Session session = HibernateConfig.openSession();
//        List<Book> bookList = null;
//        Transaction transaction = session.beginTransaction();
//        try {
//
//            ReaderUser user = new ReaderUserService().get("123", "123");
//            bookList = user.getBorrowedBooks();
//            bookList.forEach(System.out::println);
//            transaction.commit();
//
//        } catch (Exception e) {
//            transaction.rollback();
//        } finally {
//            session.close();
//        }
//        Session session = HibernateConfig.openSession();
//        Transaction transaction = session.beginTransaction();
//        List<Book> bookList = null;
//        try {
//
//            ReaderUser user = new ReaderUserService().get("123", "123");
//            user.getBorrowedBooks().forEach(System.out::println);
//            transaction.commit();
//
//        } catch (Exception e) {
//            transaction.rollback();
//        } finally {
//            session.close();
//        }
//        Session session = HibernateConfig.openSession();
//
//        try {
//            session.beginTransaction();
//            ReaderUser user = new ReaderUserService().get("123", "123");
//            user.getBorrowedBooks().forEach(System.out::println);
//            session.getTransaction().commit();
//
//        } finally {
//            session.close();
//        }

//        Book book = new BookService().findBookByTitle("title");
//        try {
//            session.beginTransaction();
//
//            book.setUserId(null);
//
//            new BookService().save(book);
//
//            session.getTransaction().commit();
//
//        } finally {
//            session.close();
//        }
//        ReaderUserService readerUserService = new ReaderUserService();
//        ReaderUser user = readerUserService.get("123", "123");
//
//        try {
//            session.beginTransaction();
//            Book book1 = new Book("1","1","1","1");
//            Book book2 = new Book("2","2","2","2");
//            Book book3 = new Book("3","3","3","3");
//
//            book1.setUserId(user);
//            book2.setUserId(user);
//            book3.setUserId(user);
//
//            session.save(book1);
//            session.save(book2);
//            session.save(book3);
//            session.getTransaction().commit();
//        } finally {
//            session.close();
//        }
    }
}
