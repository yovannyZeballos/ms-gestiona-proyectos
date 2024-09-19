package pe.com.yzm.expose.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

/**
 * <b>Class</b>: ActivityCreateRequest <br/>
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
public class ActivityCreateRequest {

    @NotBlank
    String code;

    @NotBlank
    String description;

    @NotNull
    @Min(1)
    Long projectId;

    @NotNull
    @Min(1)
    Integer effortHours;

    @NotNull
    @Min(1)
    Long hourBagId;

    @NotNull
    @Min(1)
    Long userActivityId;
}
