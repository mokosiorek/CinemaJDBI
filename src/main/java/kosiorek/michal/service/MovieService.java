package kosiorek.michal.service;

import kosiorek.michal.exceptions.ExceptionCode;
import kosiorek.michal.exceptions.MyException;
import kosiorek.michal.model.Movie;
import kosiorek.michal.repository.MovieRepository;

import java.util.List;

public class MovieService {

    private MovieRepository movieRepository = new MovieRepository();

    public void addMovie(Movie movie) {

        movieRepository.add(movie);

    }

    public void addMovieList(List<Movie> movies) {
        movies.forEach(movie -> movieRepository.add(movie));
    }

    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    public void deleteMovie(Movie movie){
        movieRepository.delete(movie.getId());
    }

    public void editMovie(Movie movie){
        movieRepository.update(movie);
    }

    public Movie getMovieById(int id){
        return movieRepository.findOne(id).orElseThrow(() ->new MyException(ExceptionCode.OTHER,"Movie not found"));
    }
}
