package BlogApp.payload;

import lombok.Data;

@Data
public class SignUp {
    private Long id;
    private String name;
    private String email;
    private String username;
    private String password;

}
