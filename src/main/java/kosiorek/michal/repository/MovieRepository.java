package kosiorek.michal.repository;

import kosiorek.michal.exceptions.ExceptionCode;
import kosiorek.michal.exceptions.MyException;
import kosiorek.michal.model.Movie;

public class MovieRepository extends AbstractCrudRepository<Movie, Integer> {

    @Override
    public void add(Movie movie) {

        if (movie == null) {
            throw new MyException(ExceptionCode.OTHER, "MOVIE OBJECT IS NULL");
        }

        //final String sql = "insert into movie ( title, genre, price, duration, release_date ) values (?,?,?,?,?)";
        final String sql = "insert into movie ( title, genre, price, duration, release_date ) values (:title,:genre,:price,:duration,:release_date)";

        jdbi.withHandle(handle -> handle
                .createUpdate(sql)
                .bind("title", movie.getTitle())
                .bind("genre", movie.getGenre())
                .bind("price", movie.getPrice())
                .bind("duration", movie.getDuration())
                .bind("release_date", movie.getReleaseDate())
                .execute());

    }

    @Override
    public void update(Movie movie) {

        if (movie == null) {
            throw new MyException(ExceptionCode.OTHER, "MOVIE OBJECT IS NULL");
        }

        //final String sql = "update movie set title=?,genre=?,price=?, duration=?,release_date=? where id=?";
        final String sql = "update movie set title=:title,genre=:genre,price=:price, duration=:duration,release_date=:release_date where id=:id";

        jdbi.withHandle(handle -> handle
                .createUpdate(sql)
                .bind("title", movie.getTitle())
                .bind("genre", movie.getGenre())
                .bind("price", movie.getPrice())
                .bind("duration", movie.getDuration())
                .bind("release_date", movie.getReleaseDate())
                .bind("id",movie.getId())
                .execute());


    }

}
