package kosiorek.michal.repository;

import kosiorek.michal.exceptions.ExceptionCode;
import kosiorek.michal.exceptions.MyException;
import kosiorek.michal.model.SalesStand;

public class SalesStandRepository extends AbstractCrudRepository<SalesStand, Integer> {

    @Override
    public void add(SalesStand salesStand) {

        if (salesStand == null) {
            throw new MyException(ExceptionCode.OTHER, "SALES STAND OBJECT IS NULL");
        }

        //final String sql = "insert into sales_stand (start_date_time, movie_id, customer_id) values (?,?,?)";
        final String sql = "insert into sales_stand (start_date_time, movie_id, customer_id) values (:start_date_time, :movie_id, :customer_id)";

        jdbi.withHandle(handle -> handle
                .createUpdate(sql)
                .bind("start_date_time", salesStand.getStartDateTime())
                .bind("movie_id", salesStand.getMovieId())
                .bind("customer_id", salesStand.getCustomerId())
                .execute());
    }

    @Override
    public void update(SalesStand salesStand) {

        if (salesStand == null) {
            throw new MyException(ExceptionCode.OTHER, "SALES STAND OBJECT IS NULL");
        }

        //final String sql = "update sales_stand set customer_id=?, movie_id=?, start_date_time=? where id=?";
        final String sql = "update sales_stand set customer_id=:customer_id, movie_id=:movie_id, start_date_time=:start_date_time where id=:id";

        jdbi.withHandle(handle -> handle
                .createUpdate(sql)
                .bind("start_date_time", salesStand.getStartDateTime())
                .bind("movie_id", salesStand.getMovieId())
                .bind("customer_id", salesStand.getCustomerId())
                .bind("id", salesStand.getId())
                .execute());

    }

}
