package server.domain.cinema;

import java.util.Date;
import java.util.HashMap;

import javax.persistence.*;

import server.domain.exception.SearchException;
import server.domain.movie.MovieShowing;
import server.domain.movie.TypeCategory;
import server.domain.theatre.Theatre;

/**
 * This class is referred to a movie
 * @see MovieShowing
 */
@Entity
@Table(name="movie")
public class Movie {
	@Id
	@Column(name="title")
	private String title;
	@Column(name="duration")
	private int duration; //in minutes
	@Column(name="category")
	private TypeCategory category;
	@Column(name="plot")
	private String plot;
	private String pathCover;
		
	public Movie() {}

	public Movie(String title, int duration, String plot, String pathCover, TypeCategory category) {
		this.title=title;
		this.duration=duration;
		this.category=category;
		this.plot=plot;
		this.pathCover=pathCover;
	}
	
	@Override
	public String toString() {
		String s = "Title: " + title + "\n";
		s += " Duration: " + duration + " minutes\n";
		s += " Category: " + (category == null ? "not set":category.toString().toLowerCase())+ "\n";
		s += " Plot: \n" + plot + "\n";
		return s;
	}
}
