package org.example.bo.custom.impl;

import org.example.bo.custom.BookBO;
import org.example.dao.DAOFactory;
import org.example.dao.custom.BookDAO;
import org.example.dto.BookDto;
import org.example.entity.Book;

import java.util.ArrayList;
import java.util.List;

public class BookBOImpl implements BookBO {

    BookDAO bookDAO= (BookDAO) DAOFactory.getDaoFactory().getDao(DAOFactory.DAOType.BOOK);

    @Override
    public boolean addBook(BookDto dto) {
        return bookDAO.add(new Book(dto.getBookId(),dto.getTitle(),dto.getAuthor(),dto.getGenre(),
                dto.isAvailability()));
    }

    @Override
    public List<BookDto> getAllBooks() {
        List<Book> all = bookDAO.getAll();

        List<BookDto> allBooks = new ArrayList<>();

        for (Book book : all) {
            allBooks.add(new BookDto(book.getBookId(),book.getTitle(),book.getAuthor(),book.getGenre(),
            book.isAvailability()));
        }
        return allBooks;
    }

    @Override
    public boolean updateBook(BookDto dto) {
        return bookDAO.update(new Book(dto.getBookId(),dto.getTitle(),dto.getAuthor(),dto.getGenre(),
                dto.isAvailability()));
    }

    @Override
    public boolean isExistBook(String id) {
        return bookDAO.isExists(id);
    }

    @Override
    public BookDto searchBook(String id) {
        Book search = bookDAO.search(id);
        BookDto bookDto = new BookDto(search.getBookId(),search.getTitle(),search.getAuthor(),search.getGenre(),search.isAvailability());
        return bookDto;
    }

    @Override
    public boolean deleteBook(String id) {
        return bookDAO.delete(id);
    }

    @Override
    public boolean borrowBook(String id) {
        return bookDAO.borrowBook(id);
    }

    public int generateNextBookId() {
        return bookDAO.generateNextBookId();
    }
}
