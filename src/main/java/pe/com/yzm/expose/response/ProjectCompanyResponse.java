package pe.com.yzm.expose.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

/**
 * <b>Class</b>: ProjectCompanyResponse <br/>
 * <b>Copyright</b>: 2024 Yovanny Zeballos Medina<br/>.
 *
 * @author 2024  Yovanny Zeballos Medina <br/>
 * <u>Developed by</u>: Yovanny Zeballos <br/>
 * <u>Changes:</u><br/>
 * <ul>
 *   <li>
 *     setiembre 06, 2024 Creación de Clase.
 *   </li>
 * </ul>
 */
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProjectCompanyResponse {
    Long id;
    String projectName;
    String companyName;
    Long companyId;
}
