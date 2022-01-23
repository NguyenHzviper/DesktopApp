package services;
import java.util.ArrayList;

import model.Book;

public interface IRssService {
	
	public String getRssUrl();
	
	public ArrayList<Book> loadRss();
}
