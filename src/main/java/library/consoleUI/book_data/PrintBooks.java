package library.consoleUI.book_data;

import library.consoleUI.text_formatter.Beautify;
import library.entity.book.Book;
import library.entity.user.User;
import library.util.file_managament.BookStorage;

import static library.consoleUI.text_formatter.Beautify.beautifyPrint;

public class PrintBooks {
    private BookStorage bookStorage = new BookStorage();

    public void printBooks(){
        bookStorage.read();
        beautifyPrint("all books");
        for (Book  book: bookStorage.getBookList()){
            System.out.println(book);
        }
    }
}
