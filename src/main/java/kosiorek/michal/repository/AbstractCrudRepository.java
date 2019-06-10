package kosiorek.michal.repository;

import kosiorek.michal.connection.DbJdbiConnection;
import org.jdbi.v3.core.Jdbi;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public abstract class AbstractCrudRepository<T, ID> implements CrudRepository<T, ID> {

    private Class<T> entityType = (Class<T>)((ParameterizedType)this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    private Class<T> idType = (Class<T>)((ParameterizedType)this.getClass().getGenericSuperclass()).getActualTypeArguments()[1];

    protected final Jdbi jdbi = DbJdbiConnection.getInstance().getJdbi();

    @Override
    public List<T> findAll() {

        return jdbi.withHandle(handle -> handle
                .createQuery("select * from " + getTableName())
                .mapToBean(entityType)
                .list()
        );
    }

    @Override
    public Optional<T> findOne(ID id) {
        return jdbi.withHandle(handle -> handle
                .createQuery("select * from " + getTableName() + " where id = :id")
                .bind("id", id)
                .mapToBean(entityType)
                .findFirst()
        );
    }

    @Override
    public void delete(ID id) {
        jdbi.useHandle(handle -> handle
                .createUpdate("delete from " + getTableName() + " where id = :id")
                .bind("id", id)
                .execute()
        );
    }

    private String getTableName() {
        // SalesStand -> sales_stands
        List<String> elements = new ArrayList<>();

        StringBuilder sb = new StringBuilder(entityType.getSimpleName().charAt(0));
        for (char x : entityType.getSimpleName().substring(1).toCharArray()) {
            if (Character.isUpperCase(x)) {
                elements.add(sb.toString());
                sb = new StringBuilder(x);
            }
            else {
                sb.append(x);
            }
        }
        return elements.stream().collect(Collectors.joining("_")) /* + "s" */;
    }
}
