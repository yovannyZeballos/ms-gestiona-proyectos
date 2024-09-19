package pe.com.yzm.business.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import pe.com.yzm.business.CompanyService;
import pe.com.yzm.core.exception.BusinessException;
import pe.com.yzm.core.logger.LoggerUtil;
import pe.com.yzm.core.model.HeaderRequest;
import pe.com.yzm.expose.request.CompanyCreateRequest;
import pe.com.yzm.expose.response.CompanyResponse;
import pe.com.yzm.expose.request.CompanyUpdateRequest;
import pe.com.yzm.mapper.CompanyMapper;
import pe.com.yzm.repository.CompanyRepository;
import pe.com.yzm.util.ConstantMessage;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * <b>Class</b>: CompanyServiceImpl <br/>
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
@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {

    // The CompanyRepository instance used for database operations
    private final CompanyRepository companyRepository;
    private final CompanyMapper companyMapper;

    /**
     * This method is used to find a company by its ID.
     *
     * @param headerRequest The request header containing transaction details
     * @param id            The ID of the company to be found
     * @return A Mono of CompanyResponse if the company is found, else throws a BusinessException
     */
    @Override
    public Mono<CompanyResponse> findByCompanyId(HeaderRequest headerRequest, Long id) {
        LoggerUtil.logInput(headerRequest.getTransactionId(), headerRequest.toString(), id);
        return companyRepository.findById(id)
                .switchIfEmpty(Mono.error(BusinessException.createException(ConstantMessage.COMPANY_GET_ERROR,
                        List.of(ConstantMessage.COMPANY_NOT_FOUND), HttpStatus.NOT_FOUND)))
                .map(companyMapper::companytoCompanyResponse)
                .doOnNext(response ->
                        LoggerUtil.logOutput(headerRequest.getTransactionId(), headerRequest.toString(), response.toString()))
                .doOnError(error -> LoggerUtil.logError(headerRequest.getTransactionId(), error));

    }

    /**
     * This method is used to find all companies.
     *
     * @param headerRequest The request header containing transaction details
     * @return A Flux of CompanyResponse containing all companies
     */
    @Override
    public Flux<CompanyResponse> findAllCompany(HeaderRequest headerRequest) {
        LoggerUtil.logInput(headerRequest.getTransactionId(), headerRequest.toString(), null);
        return companyRepository.findAll()
                .map(companyMapper::companytoCompanyResponse)
                .doOnNext(response ->
                        LoggerUtil.logOutput(headerRequest.getTransactionId(), headerRequest.toString(), response.toString()))
                .doOnError(error -> LoggerUtil.logError(headerRequest.getTransactionId(), error));
    }

    /**
     * This method is used to save a new company.
     *
     * @param headerRequest  The request header containing transaction details
     * @param companyRequest The request containing the details of the company to be saved
     * @return A Mono of CompanyResponse containing the saved company details
     */
    @Override
    public Mono<CompanyResponse> saveCompany(HeaderRequest headerRequest, CompanyCreateRequest companyRequest) {
        LoggerUtil.logInput(headerRequest.getTransactionId(), headerRequest.toString(), companyRequest);
        return companyRepository.save(companyMapper.companyCreateRequestToCompany(companyRequest))
                .map(companyMapper::companytoCompanyResponse)
                .doOnNext(response ->
                        LoggerUtil.logOutput(headerRequest.getTransactionId(), headerRequest.toString(), response.toString()))
                .doOnError(error -> LoggerUtil.logError(headerRequest.getTransactionId(), error));
    }

    /**
     * This method is used to update an existing company.
     *
     * @param headerRequest  The request header containing transaction details
     * @param companyRequest The request containing the updated details of the company
     * @return A Mono of CompanyResponse containing the updated company details
     */
    @Override
    public Mono<CompanyResponse> updateCompany(HeaderRequest headerRequest, CompanyUpdateRequest companyRequest) {
        LoggerUtil.logInput(headerRequest.getTransactionId(), headerRequest.toString(), companyRequest);
        return companyRepository.findById(companyRequest.getId())
                .switchIfEmpty(Mono.error(BusinessException.createException(ConstantMessage.COMPANY_UPDATE_ERROR,
                        List.of(ConstantMessage.COMPANY_NOT_FOUND), HttpStatus.NOT_FOUND)))
                .map(company -> companyMapper.companyUpdateRequestToCompany(company, companyRequest))
                .flatMap(companyRepository::save)
                .map(companyMapper::companytoCompanyResponse)
                .doOnNext(response ->
                        LoggerUtil.logOutput(headerRequest.getTransactionId(), headerRequest.toString(), response.toString()))
                .doOnError(error -> LoggerUtil.logError(headerRequest.getTransactionId(), error));
    }

    /**
     * This method is used to delete a company by its ID.
     *
     * @param headerRequest The request header containing transaction details
     * @param id            The ID of the company to be deleted
     * @return A Mono of Void if the company is successfully deleted, else throws a BusinessException
     */
    @Override
    public Mono<Void> deleteCompany(HeaderRequest headerRequest, Long id) {
        LoggerUtil.logInput(headerRequest.getTransactionId(), headerRequest.toString(), id);
        return companyRepository.findById(id)
                .switchIfEmpty(Mono.error(BusinessException.createException(ConstantMessage.COMPANY_DELETE_ERROR,
                        List.of(ConstantMessage.COMPANY_NOT_FOUND), HttpStatus.NOT_FOUND)))
                .flatMap(companyRepository::delete)
                .doOnSuccess(response ->
                        LoggerUtil.logOutput(headerRequest.getTransactionId(), headerRequest.toString(), null))
                .doOnError(error -> LoggerUtil.logError(headerRequest.getTransactionId(), error));
    }
}
