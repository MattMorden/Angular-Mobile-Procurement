package dtos;

/**
 * EmployeeDTO - Container class that serializes employee information traveling
 * to and From EmployeeModel class
 */
import java.io.Serializable;

public class EmployeeDTO implements Serializable {

    public EmployeeDTO() {
    }
    private int id;
    private String title;
    private String firstname;
    private String lastname;
    private String phoneno;
    private String email;

    public int getId() {
        return this.id;
    }

    public void setId(int inValue) {
        this.id = inValue;
    }

    public String getFirstname() {
        return this.firstname;
    }

    public void setFirstname(String inValue) {
        this.firstname = inValue;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String inValue) {
        this.title = inValue;
    }

    public String getLastname() {
        return this.lastname;
    }

    public void setLastname(String inValue) {
        this.lastname = inValue;
    }

    public String getPhoneno() {
        return this.phoneno;
    }

    public void setPhoneno(String inValue) {
        this.phoneno = inValue;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String inValue) {
        this.email = inValue;
    }
}
