package pe.com.yzm.business;

import pe.com.yzm.core.model.HeaderRequest;
import pe.com.yzm.expose.request.CompanyCreateRequest;
import pe.com.yzm.expose.response.CompanyResponse;
import pe.com.yzm.expose.request.CompanyUpdateRequest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * <b>Class</b>: CompanyService <br/>
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
public interface CompanyService {

    /**
     * Finds a company by its ID.
     *
     * @param headerRequest The request header containing necessary context information.
     * @param id            The ID of the company to be found.
     * @return A Mono of CompanyResponse containing the company details if found.
     */
    Mono<CompanyResponse> findByCompanyId(HeaderRequest headerRequest, Long id);

    /**
     * Finds all companies.
     *
     * @param headerRequest The request header containing necessary context information.
     * @return A Flux of CompanyResponse containing all the companies.
     */
    Flux<CompanyResponse> findAllCompany(HeaderRequest headerRequest);

    /**
     * Saves a new company.
     *
     * @param headerRequest The request header containing necessary context information.
     * @param companyRequest The request containing the company details to be saved.
     * @return A Mono of CompanyResponse containing the saved company details.
     */
    Mono<CompanyResponse> saveCompany(HeaderRequest headerRequest, CompanyCreateRequest companyRequest);

    /**
     * Updates a company.
     *
     * @param headerRequest The request header containing necessary context information.
     * @param companyRequest The request containing the company details to be updated.
     * @return A Mono of CompanyResponse containing the updated company details.
     */
    Mono<CompanyResponse> updateCompany(HeaderRequest headerRequest, CompanyUpdateRequest companyRequest);

    /**
     * Deletes a company.
     *
     * @param headerRequest The request header containing necessary context information.
     * @param id The ID of the company to be deleted.
     * @return A Mono of CompanyResponse containing the deleted company details.
     */
    Mono<Void> deleteCompany(HeaderRequest headerRequest, Long id);
}
