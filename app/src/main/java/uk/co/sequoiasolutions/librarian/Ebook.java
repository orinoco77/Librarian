package uk.co.sequoiasolutions.librarian;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Created by ajs on 10/08/2015.
 */
public class Ebook implements Comparable<Ebook> {
    public int Id;
    public String Title;
    public String Description;
    public String EbookUrl;
    public int ImageData;
    public List<Author> Authors=new ArrayList<>();

    @Override
    public int compareTo(Ebook other){
        if (this.Title != null && other.Title != null)
        {
            return this.Title.compareTo(other.Title);
        }
        return 0;
    }
}
