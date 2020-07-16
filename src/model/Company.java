package model;

public class Company {

    private Long id;
    private String name;
    private String email;
    private String cnpj;

    public Company() {

    }

    public Company(String name, String email, String cnpj) {
        this.name = name;
        this.email = email;
        this.cnpj = cnpj;
    }

    public Company(Long id, String name, String email, String cnpj) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.cnpj = cnpj;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

}
