package uk.co.sequoiasolutions.librarian;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ajs on 10/08/2015.
 */
public class Ebook {
    public int Id;
    public String Title;
    public String Description;
    public String EbookUrl;
    public int ImageData;
    public List<Author> Authors=new ArrayList<>();
}
