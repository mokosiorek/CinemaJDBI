package kosiorek.michal.repository;

import java.util.List;
import java.util.Optional;

public interface CrudRepository<T, ID> {
    void add(T t);
    void update(T t);
    void delete(ID id);
    Optional<T> findOne(ID id);
    List<T> findAll();
}
