package course.web.model;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.ScriptAssert;

@ScriptAssert(lang = "javascript", script = "_.password.localeCompare(_.verify) == 0", alias = "_", message = "Password must match")
public class SignupForm {
    @Size(min = 3, max = 20, message = "Invalid username. Length must be 3 to 20 characters")
    @Pattern(regexp = "^[a-zA-Z0-9_-]*$", message = "Invalid username. Try just letters and numbers")
    private String username;

    @Size(min = 3, max = 20, message = "Invalid password. Length must be 3 to 20 characters")
    private String password;

    private String verify;

    @Pattern(regexp = "^[\\S]+@[\\S]+\\.[\\S]+$", message = "Invalid email address. Must be in the format local@domain.com")
    private String email;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getVerify() {
        return verify;
    }

    public void setVerify(String verify) {
        this.verify = verify;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
