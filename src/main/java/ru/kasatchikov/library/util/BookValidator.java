package ru.kasatchikov.library.util;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.kasatchikov.library.dao.BookDAO;
import ru.kasatchikov.library.models.Book;

@Component
public class BookValidator implements Validator {
    private final BookDAO bookDAO;
    public BookValidator(BookDAO bookDAO) {
        this.bookDAO = bookDAO;
    }
    @Override
    public boolean supports(Class<?> clazz) {
        return Book.class.equals(clazz);
    }
    @Override
    public void validate(Object target, Errors errors) {
        Book book = (Book) target;

        if(bookDAO.showName(book.getName()).isPresent())
            errors.rejectValue("name", "", "Книга с таким названием уже существует");
    }
}
