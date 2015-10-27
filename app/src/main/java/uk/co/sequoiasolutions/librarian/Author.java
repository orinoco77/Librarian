package uk.co.sequoiasolutions.librarian;

/**
 * Created by ajs on 25/10/2015.
 */
public class Author {
    private int _id;
    private String _name;

    public int get_id(){
        return _id;
    }

    public void set_id(int id) {
        _id=id;
    }

    public String get_name() {
        return _name;
    }

    public void set_name(String name) {
        _name=name;
    }
}
