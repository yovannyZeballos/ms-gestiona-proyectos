package pe.com.yzm.model;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <b>Class</b>: HourBag <br/>
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
@Table(name = "bolsas_horas", schema = "public")
public class HourBag {
    @Id
    Long id;

    @Column(value = "empresa_id")
    Long companyId;

    @Column(value = "horas")
    Integer hours;

    @Column(value = "horas_consumidas")
    Integer consumedHours;

    @Column(value = "costo_hora")
    BigDecimal hourCost;

    @Column(value = "fecha_registro")
    LocalDateTime registrationDate;

    @Column(value = "estado")
    Boolean status;

}
