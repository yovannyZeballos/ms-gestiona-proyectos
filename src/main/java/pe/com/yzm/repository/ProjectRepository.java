package pe.com.yzm.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import pe.com.yzm.model.Project;
import reactor.core.publisher.Flux;

/**
 * <b>Class</b>: ProjectRpository <br/>
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
public interface ProjectRepository extends ReactiveCrudRepository<Project, Long> {
    Flux<Project> findAllByCompanyId(Long companyId);
}
