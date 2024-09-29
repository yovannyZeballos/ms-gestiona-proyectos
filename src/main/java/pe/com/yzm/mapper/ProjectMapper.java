package pe.com.yzm.mapper;

import org.mapstruct.Mapper;
import pe.com.yzm.expose.request.ProjectCreateRequest;
import pe.com.yzm.expose.request.ProjectUpdateRequest;
import pe.com.yzm.expose.response.ProjectCompanyResponse;
import pe.com.yzm.expose.response.ProjectResponse;
import pe.com.yzm.model.Company;
import pe.com.yzm.model.Project;

/**
 * <b>Class</b>: ProjectMapper <br/>
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
@Mapper(componentModel = "spring")
public abstract class ProjectMapper {

    /**
     * Converts a Project entity to a ProjectResponse DTO.
     *
     * @param project The Project entity to be converted.
     * @return A ProjectResponse DTO representing the given Project entity.
     */
    public abstract ProjectResponse projectToProjectResponse(Project project);

    /**
     * Converts a ProjectCreateRequest DTO to a Project entity.
     *
     * @param projectRequest The ProjectCreateRequest DTO to be converted.
     * @return A Project entity representing the given ProjectCreateRequest DTO.
     */
    public abstract Project projectCreateRequestToProject(ProjectCreateRequest projectRequest);

    /**
     * Updates a Project entity with the details from a ProjectUpdateRequest DTO.
     *
     * @param projectUpdate The existing Project entity to be updated.
     * @param projectRequest The ProjectUpdateRequest DTO containing the updated details.
     * @return The updated Project entity.
     */
    public Project projectUpdateRequestToProject(Project projectUpdate, ProjectUpdateRequest projectRequest) {
        projectUpdate.setId(projectRequest.getId());
        projectUpdate.setName(projectRequest.getName());
        projectUpdate.setCompanyId(projectRequest.getCompanyId());
        return projectUpdate;
    }

    /**
     * Converts a Project entity to a ProjectCompanyResponse DTO.
     *
     * @param project The Project entity to be converted.
     * @param companyName The Company entity to be converted.
     * @return A ProjectCompanyResponse DTO representing the given Project entity and Company entity.
     */
    public ProjectCompanyResponse projectToProjectCompanyResponse(Project project, String companyName) {
        return ProjectCompanyResponse.builder()
                .id(project.getId())
                .projectName(project.getName())
                .companyName(companyName)
                .companyId(project.getCompanyId())
                .build();
    }
}
