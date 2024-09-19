package pe.com.yzm.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import pe.com.yzm.model.Company;

/**
 * <b>Class</b>: CompanyRepository <br/>
 * <b>Copyright</b>: 2024 Yovanny Zeballos Medina<br/>.
 *
 * @author 2024  Yovanny Zeballos Medina <br/>
 * <u>Developed by</u>: Yovanny Zeballos <br/>
 * <u>Changes:</u><br/>
 * <ul>
 *   <li>
 *     setiembre 05, 2024 Creación de Clase.
 *   </li>
 * </ul>
 */
public interface CompanyRepository extends ReactiveCrudRepository<Company, Long> {
}
