package ru.kasatchikov.library.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.kasatchikov.library.models.Book;
import ru.kasatchikov.library.models.Person;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Component
public class BookDAO {
    private final JdbcTemplate jdbcTemplate;
    @Autowired
    public BookDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    public List<Book> index(){
        return jdbcTemplate.query("SELECT * FROM Book", new BeanPropertyRowMapper<>(Book.class));
    }
    public void save(Book book){
        jdbcTemplate.update("INSERT INTO Book(name, author, year) VALUES(?, ?, ?)", book.getName(), book.getAuthor(), book.getYear());
    }
    public Book show(int book_id){
        return jdbcTemplate.query("SELECT * FROM Book WHERE book_id=?", new Object[]{book_id},
                new BeanPropertyRowMapper<>(Book.class)).stream().findAny().orElse(null);
    }
    public Optional<Book> showName(String name){
        return jdbcTemplate.query("SELECT * FROM Book WHERE name=?", new Object[]{name},
                new BeanPropertyRowMapper<>(Book.class)).stream().findAny();
    }
    public void update(int book_id, Book updatedBook){
        jdbcTemplate.update("UPDATE Book SET name=?, author=?, year=? WHERE book_id=?",
                updatedBook.getName(), updatedBook.getAuthor(), updatedBook.getYear(), book_id);
    }
    public void delete(int book_id){
        jdbcTemplate.update("DELETE FROM Book WHERE book_id=?", book_id);
    }
    public Optional<Person> getBookOwner(int book_id){
        return jdbcTemplate.query("SELECT Person.* FROM Book JOIN Person ON book.person_id = person.id" +
                " WHERE Book.book_id=?", new Object[]{book_id}, new BeanPropertyRowMapper<>(Person.class)).stream().findAny();
    }
    public void assignBook(int id, Person selectedPerson){
        jdbcTemplate.update("UPDATE Book SET person_id=? WHERE book_id=?", selectedPerson.getId(), id);
    }
    public void releaseBook(int id){
        jdbcTemplate.update("UPDATE Book SET person_id=NULL WHERE book_id=?", id);
    }
}
