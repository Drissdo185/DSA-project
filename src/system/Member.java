package system;

import java.io.Serializable;

public abstract class Member implements Serializable {
    private String name;
    private String lastName;
    private String email;
    private String code;

    public Member(String name, String lastName, String email, String code) {
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
