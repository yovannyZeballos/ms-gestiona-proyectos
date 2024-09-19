package pe.com.yzm.model;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

/**
 * <b>Class</b>: Activity <br/>
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
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "actividades", schema = "public")
public class Activity {
    @Id
    Long id;

    @Column(value = "codigo")
    String code;

    @Column(value = "descripcion")
    String description;

    @Column(value = "proyecto_id")
    Long projectId;

    @Column(value = "horas_esfuerzo")
    Integer effortHours;

    @Column(value = "bolsa_hora_id")
    Long hourBagId;

    @Column(value = "usuario_actividad_id")
    Long userActivityId;
}