package uk.co.sequoiasolutions.librarian;

import java.util.Comparator;

/**
 * Created by ajs on 25/10/2015.
 */
public class Author implements Comparable<Author> {
    public int Id;
    public String Forename;
    public String Surname;

    @Override
    public int compareTo(Author other)
    {
        int output=0;
        if (this.Surname != null && other.Surname !=null)
        {
            output = this.Surname.compareTo(other.Surname);
        }
        if (output == 0 && this.Forename != null && other.Forename != null)
        {
            output = this.Forename.compareTo(other.Forename);
        }
        return output;
    }

}
