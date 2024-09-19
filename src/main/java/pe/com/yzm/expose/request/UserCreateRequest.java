package pe.com.yzm.expose.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

/**
 * <b>Class</b>: UserCreateRequest <br/>
 * <b>Copyright</b>: 2024 Yovanny Zeballos Medina<br/>.
 *
 * @author 2024  Yovanny Zeballos Medina <br/>
 * <u>Developed by</u>: Yovanny Zeballos <br/>
 * <u>Changes:</u><br/>
 * <ul>
 *   <li>
 *     setiembre 04, 2024 Creación de Clase.
 *   </li>
 * </ul>
 */
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserCreateRequest {
  @NotBlank
  String name;

  @NotBlank
  @Email
  String email;

  @ToString.Exclude
  @NotBlank
  String password;

  @NotNull
  Long roleId;

  Long userId;
}
