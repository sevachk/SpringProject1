package ru.kasatchikov.library.util;

import org.hibernate.validator.internal.engine.groups.ValidationOrder;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.kasatchikov.library.dao.PersonDAO;
import ru.kasatchikov.library.models.Person;

@Component
public class PersonValidator implements Validator {
    private final PersonDAO personDAO;
    public PersonValidator(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }
    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }
    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;
        if(personDAO.showName(person.getName()).isPresent())
            errors.rejectValue("name", "", "Данное имя уже занято");
    }
}
