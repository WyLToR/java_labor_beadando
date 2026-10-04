package org.example.demo.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class KapcsolatForm {

    @NotBlank(message = "A név megadása kötelező.")
    @Size(min = 2, max = 100, message = "A név 2 és 100 karakter között legyen.")
    private String nev;

    @NotBlank(message = "Az e-mail cím megadása kötelező.")
    @Email(message = "Érvényes e-mail címet adj meg.")
    private String email;

    @NotBlank(message = "Az üzenet megadása kötelező.")
    @Size(min = 5, max = 2000, message = "Az üzenet 5 és 2000 karakter között legyen.")
    private String uzenet;

    public String getNev() {
        return nev;
    }

    public void setNev(String nev) {
        this.nev = nev;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUzenet() {
        return uzenet;
    }

    public void setUzenet(String uzenet) {
        this.uzenet = uzenet;
    }
}