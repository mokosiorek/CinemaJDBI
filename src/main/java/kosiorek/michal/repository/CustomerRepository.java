package kosiorek.michal.repository;

import kosiorek.michal.exceptions.ExceptionCode;
import kosiorek.michal.exceptions.MyException;
import kosiorek.michal.model.Customer;

import java.util.Optional;

public class CustomerRepository extends AbstractCrudRepository<Customer, Integer> {

    @Override
    public void add(Customer customer) {

        if (customer == null) {
            throw new MyException(ExceptionCode.OTHER, "CUSTOMER OBJECT IS NULL");
        }

        final String sql = "insert into customer ( name, surname, age, email ) values (:name,:surname,:age,:email)";

        jdbi.withHandle(handle -> handle
                .createUpdate(sql)
                .bind("name", customer.getName())
                .bind("surname", customer.getSurname())
                .bind("age", customer.getAge())
                .bind("email", customer.getEmail())
                .execute());


    }

    @Override
    public void update(Customer customer) {

        if (customer == null) {
            throw new MyException(ExceptionCode.OTHER, "CUSTOMER OBJECT IS NULL");
        }

        //final String sql = "update customer set name=?,surname=?,age=?, email=?,loyalty_card_id=? where id=?";
        //final String sql = "update customer set name=?,surname=?,age=?, email=? where id=?";
        final String sql = "update customer set name=:name,surname=:surname,age=:age, email=:email where id=:id";

        jdbi.withHandle(handle -> handle
                .createUpdate(sql)
                .bind("name", customer.getName())
                .bind("surname", customer.getSurname())
                .bind("age", customer.getAge())
                .bind("email", customer.getEmail())
                .bind("id", customer.getId())
                .execute());

    }

    public Optional<Customer> findByNameSurnameEmail(String name, String surname, String email) {

        //final String sql = "select id, name, surname, age, email, loyalty_card_id from customer where name=? AND surname=? AND email=?";
        final String sql = "select id, name, surname, age, email, loyalty_card_id from customer where name=:name AND surname=:surname AND email=:email";

        return jdbi.withHandle(handle -> handle
                .createQuery(sql)
                .bind("name", name)
                .bind("surname", surname)
                .bind("email", email)
                .mapToBean(Customer.class)
                .findFirst());

    }

    public void updateWithLoyaltyCard(Customer customer) {

        if (customer == null) {
            throw new MyException(ExceptionCode.OTHER, "CUSTOMER OBJECT IS NULL");
        }

        //final String sql = "update customer set name=?,surname=?,age=?, email=?,loyalty_card_id=? where id=?";
        final String sql = "update customer set name=:name,surname=:surname,age=:age, email=:email,loyalty_card_id=:loyalty_card_id where id=:id";

        jdbi.withHandle(handle -> handle
                .createUpdate(sql)
                .bind("name", customer.getName())
                .bind("surname", customer.getSurname())
                .bind("age", customer.getAge())
                .bind("email", customer.getEmail())
                .bind("loyalty_card_id", customer.getLoyaltyCardId())
                .bind("id", customer.getId())
                .execute());

    }
}
