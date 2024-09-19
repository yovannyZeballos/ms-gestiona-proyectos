package pe.com.yzm.business.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import pe.com.yzm.business.HourBagService;
import pe.com.yzm.core.exception.BusinessException;
import pe.com.yzm.core.logger.LoggerUtil;
import pe.com.yzm.core.model.HeaderRequest;
import pe.com.yzm.expose.request.HourBagCreateRequest;
import pe.com.yzm.expose.request.HourBagUpdateRequest;
import pe.com.yzm.expose.response.HourBagResponse;
import pe.com.yzm.mapper.HourBagMapper;
import pe.com.yzm.model.HourBag;
import pe.com.yzm.repository.ActivityRepository;
import pe.com.yzm.repository.HourBagRepository;
import pe.com.yzm.util.ConstantMessage;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

/**
 * <b>Class</b>: HourBagServiceImpl <br/>
 * <b>Copyright</b>: 2024 Yovanny Zeballos Medina<br/>.
 *
 * @author 2024  Yovanny Zeballos Medina <br/>
 * <u>Developed by</u>: Yovanny Zeballos <br/>
 * <u>Changes:</u><br/>
 * <ul>
 *   <li>
 *     setiembre 07, 2024 Creación de Clase.
 *   </li>
 * </ul>
 */
@Service
@RequiredArgsConstructor
public class HourBagServiceImpl implements HourBagService {

    private final HourBagRepository hourBagRepository;
    private final ActivityRepository activityRepository;
    private final HourBagMapper hourBagMapper;

    /**
     * Finds all active HourBags.
     *
     * @param headerRequest The request header containing metadata for the request.
     * @return A Flux emitting the HourBagResponses for all found active HourBags.
     */
    public Flux<HourBagResponse> findAllActive(HeaderRequest headerRequest) {
        LoggerUtil.logInput(headerRequest.getTransactionId(), headerRequest.toString(), null);
        return hourBagRepository.findByStatus(Boolean.TRUE)
                .map(hourBagMapper::hourBagToHourBagResponse)
                .doOnNext(response ->
                        LoggerUtil.logOutput(headerRequest.getTransactionId(), headerRequest.toString(), response.toString()))
                .doOnError(error -> LoggerUtil.logError(headerRequest.getTransactionId(), error));
    }

    /**
     * Finds all HourBags.
     *
     * @param headerRequest The request header containing metadata for the request.
     * @return A Flux emitting the HourBagResponses for all found HourBags.
     */
    @Override
    public Flux<HourBagResponse> findAll(HeaderRequest headerRequest) {
        LoggerUtil.logInput(headerRequest.getTransactionId(), headerRequest.toString(), null);
        return hourBagRepository.findAll()
                .map(hourBagMapper::hourBagToHourBagResponse)
                .doOnNext(response ->
                        LoggerUtil.logOutput(headerRequest.getTransactionId(), headerRequest.toString(), response.toString()))
                .doOnError(error -> LoggerUtil.logError(headerRequest.getTransactionId(), error));
    }

    /**
     * Creates a new HourBag.
     *
     * @param headerRequest  The request header containing metadata for the request.
     * @param hourBagRequest The request containing the data for the HourBag to create.
     * @return A Mono emitting the HourBagResponse for the created HourBag.
     */
    @Override
    public Mono<HourBagResponse> create(HeaderRequest headerRequest, HourBagCreateRequest hourBagRequest) {
        LoggerUtil.logInput(headerRequest.getTransactionId(), headerRequest.toString(), hourBagRequest);
        return hourBagRepository.save(hourBagMapper.hourBagCreateRequestToHourBag(hourBagRequest))
                .map(hourBagMapper::hourBagToHourBagResponse)
                .doOnNext(response ->
                        LoggerUtil.logOutput(headerRequest.getTransactionId(), headerRequest.toString(), response.toString()))
                .doOnError(error -> LoggerUtil.logError(headerRequest.getTransactionId(), error));
    }

    /**
     * Updates an existing HourBag.
     *
     * @param headerRequest  The request header containing metadata for the request.
     * @param hourBagRequest The request containing the updated data for the HourBag.
     * @return A Mono emitting the HourBagResponse for the updated HourBag.
     */
    @Override
    public Mono<HourBagResponse> update(HeaderRequest headerRequest, HourBagUpdateRequest hourBagRequest) {
        LoggerUtil.logInput(headerRequest.getTransactionId(), headerRequest.toString(), hourBagRequest);
        return getHourBag(hourBagRequest.getId())
                .flatMap(hourBag -> validateUpdate(hourBag, hourBagRequest))
                .map(hourBag -> hourBagMapper.hourBagUpdateRequestToHourBag(hourBag, hourBagRequest))
                .flatMap(hourBagRepository::save)
                .map(hourBagMapper::hourBagToHourBagResponse)
                .doOnNext(response ->
                        LoggerUtil.logOutput(headerRequest.getTransactionId(), headerRequest.toString(), response.toString()))
                .doOnError(error -> LoggerUtil.logError(headerRequest.getTransactionId(), error));
    }

    /**
     * Deletes a HourBag by its ID.
     *
     * @param headerRequest The request header containing metadata for the request.
     * @param id            The ID of the HourBag to delete.
     * @return A Mono emitting completion signal once the HourBag is deleted, or Mono.empty() if no HourBag was found.
     */
    @Override
    public Mono<Void> delete(HeaderRequest headerRequest, Long id) {
        LoggerUtil.logInput(headerRequest.getTransactionId(), headerRequest.toString(), id);
        return getHourBag(id)
                .flatMap(hourBag -> validateDelete(hourBag))
                .flatMap(hourBagRepository::delete)
                .doOnSuccess(response ->
                        LoggerUtil.logOutput(headerRequest.getTransactionId(), headerRequest.toString(), null))
                .doOnError(error -> LoggerUtil.logError(headerRequest.getTransactionId(), error));
    }

    /**
     * Retrieves an HourBag entity by its ID.
     *
     * @param id The ID of the HourBag to retrieve.
     * @return A Mono emitting the found HourBag, or an error if no HourBag was found.
     */
    private Mono<HourBag> getHourBag(Long id) {
        return hourBagRepository.findById(id)
                .switchIfEmpty(Mono.error(BusinessException.createException(ConstantMessage.HOUR_BAG_GET_ERROR,
                        List.of(ConstantMessage.HOUR_BAG_NOT_FOUND), HttpStatus.NOT_FOUND)));
    }

    /**
     * Validates the HourBagUpdateRequest against the existing HourBag.
     *
     * @param hourBag        The existing HourBag.
     * @param hourBagRequest The HourBagUpdateRequest containing the updated data.
     * @return A Mono emitting the validated HourBag, or an error if the validation fails.
     */
    private Mono<HourBag> validateUpdate(HourBag hourBag, HourBagUpdateRequest hourBagRequest) {
        ArrayList<String> errors = new ArrayList<>();
        validateConsumedHours(hourBagRequest.getHours(), hourBag.getConsumedHours(), errors);

        if (!errors.isEmpty()) {
            return Mono.error(BusinessException.createException(
                    ConstantMessage.HOUR_BAG_UPDATE_ERROR, errors, HttpStatus.BAD_REQUEST));
        }

        return Mono.just(hourBag);
    }

    /**
     * Validates if the HourBag can be deleted.
     *
     * @param hourBag The HourBag to validate.
     * @return A Mono emitting the validated HourBag, or an error if the validation fails.
     */
    private Mono<HourBag> validateDelete(HourBag hourBag) {
        return validateActivityExists(hourBag)
                .thenReturn(hourBag);
    }

    /**
     * Validates if there are any activities associated with the HourBag.
     *
     * @param hourBag The HourBag to validate.
     * @return A Mono emitting completion signal if no activities are associated, or an error if activities are found.
     */
    private Mono<Void> validateActivityExists(HourBag hourBag) {
        return activityRepository.existsByHourBagId(hourBag.getId())
                .flatMap(exists -> exists ? Mono.error(BusinessException.createException(
                        ConstantMessage.HOUR_BAG_DELETE_ERROR,
                        List.of(ConstantMessage.HOUR_BAG_DELETE_ERROR),
                        HttpStatus.BAD_REQUEST)) : Mono.empty());
    }

    /**
     * Validates if the consumed hours are less than the total hours.
     *
     * @param hour         The total hours.
     * @param consumedHour The consumed hours.
     * @param errors       The list to add error messages to.
     */
    private void validateConsumedHours(Integer hour, Integer consumedHour, List<String> errors) {
        if (consumedHour > hour) {
            errors.add(ConstantMessage.HOUR_BAG_CONSUMED_HOUR_ERROR);
        }
    }
}
