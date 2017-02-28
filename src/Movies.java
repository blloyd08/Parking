/**
 * Represents a movie with a title, year, length, genre, and studio name.
 * @author mmuppa
 *
 */
public class Movies {
	private String title;
	private int year;
	private int length;
	private String genre, studioName;
	
	
	/**
	 * Initialize the movie parameters.
	 * @param title
	 * @param year
	 * @param length
	 * @param genre
	 * @param studioName
	 * @throws IllegalArgumentException if title or genre or studio name are null or empty,
	 * length <= 0, year < 1920.
	 */
	public Movies(String title, int year, int length, String genre,
			String studioName) {
		setTitle(title);
		setYear(year);
		setLength(length);
		setGenre(genre);
		setStudioName(studioName);
	}
	
	@Override
	public String toString()
	{
		return "Movie [title=" + title + ", year=" + year + ", length="
				+ length + ", genre=" + genre + ", studioName=" + studioName
				+ "]";
	}

	/**
	 * Returns the title of the movie.
	 * @return movie title
	 */
	public String getTitle()
	{
		return title;
	}
	
	/**
	 * Modifies the title of the movie.
	 * @param title
	 * @throws IllegalArgumentException if title is null or empty.
	 */
	public void setTitle(String title)
	{
		if (title == null || title.length() == 0 )
			throw new IllegalArgumentException("Please supply a valid title.");
		this.title = title;
	}
	
	/**
	 * Returns the year the movie was made.
	 * @return year
	 */
	public int getYear()
	{
		return year;
	}
	
	/**
	 * Sets the movie year.
	 * @param year
	 * @throws IllegalArgumentException if year is before 1920. 
	 */
	public void setYear(int year)
	{
		if (year < 1920)
			throw new IllegalArgumentException("Movie year cannot be before 1920.");
		this.year = year;
	}
	
	/**
	 * Returns the length of the movie.
	 * @return length
	 */
	public int getLength()
	{
		return length;
	}
	
	/**
	 * Modifies the length of the movie.
	 * @param length
	 * @throws IllegalArgumentException if length is negative or 0.
	 */
	public void setLength(int length)
	{
		if (length <= 0)
			throw new IllegalArgumentException("Movie length cannot be negative or 0.");
		
		this.length = length;
	}
	
	/**
	 * Returns the genre of the movie.
	 * @return genre
	 */
	public String getGenre()
	{
		return genre;
	}
	
	/**
	 * Sets the genre of the movie.
	 * @param genre
	 * @throws IllegalArgumentException if genre is null or empty.
	 */
	public void setGenre(String genre)
	{
		if (genre == null || genre.length() == 0)
			throw new IllegalArgumentException("Please supply a valid genre.");
		
		this.genre = genre;
	}
	
	/**
	 * Returns the name of the studio.
	 * @return studioName
	 */
	public String getStudioName()
	{
		return studioName;
	}
	
	/**
	 * Sets the name of the studio. 
	 * @param studioName
	 * @throws IllegalArgumentException if studio name is null or empty.
	 */
	public void setStudioName(String studioName)
	{
		if (studioName == null || studioName.length() == 0)
			throw new IllegalArgumentException("Please supply a valid studio name.");
		
		this.studioName = studioName;
	}
	
	
}
