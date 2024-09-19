package pe.com.yzm.business.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import pe.com.yzm.business.ActivityService;
import pe.com.yzm.core.exception.BusinessException;
import pe.com.yzm.core.logger.LoggerUtil;
import pe.com.yzm.core.model.HeaderRequest;
import pe.com.yzm.expose.request.ActivityCreateRequest;
import pe.com.yzm.expose.request.ActivityUpdateRequest;
import pe.com.yzm.expose.response.ActivityResponse;
import pe.com.yzm.mapper.ActivityMapper;
import pe.com.yzm.repository.ActivityRepository;
import pe.com.yzm.util.ConstantMessage;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * <b>Class</b>: ActivityServiceImpl <br/>
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
@Service
@RequiredArgsConstructor
public class ActivityServiceImpl implements ActivityService {

    private final ActivityRepository activityRepository;
    private final ActivityMapper activityMapper;

    /**
     * Finds an activity by its ID.
     *
     * @param headerRequest The request header containing metadata for the request.
     * @param id            The ID of the activity to find.
     * @return A Mono emitting the ActivityResponse for the found activity, or Mono.empty() if no activity was found.
     */
    @Override
    public Mono<ActivityResponse> findById(HeaderRequest headerRequest, Long id) {
        LoggerUtil.logInput(headerRequest.getTransactionId(), headerRequest.toString(), id);
        return activityRepository.findById(id)
                .switchIfEmpty(Mono.error(BusinessException.createException(ConstantMessage.ACTIVITY_GET_ERROR,
                        List.of(ConstantMessage.ACTIVITY_NOT_FOUND), HttpStatus.NOT_FOUND)))
                .map(activityMapper::activityToActivityResponse)
                .doOnNext(response ->
                        LoggerUtil.logOutput(headerRequest.getTransactionId(), headerRequest.toString(), response.toString()))
                .doOnError(error -> LoggerUtil.logError(headerRequest.getTransactionId(), error));
    }

    /**
     * Finds all activities.
     *
     * @param headerRequest The request header containing metadata for the request.
     * @return A Flux emitting the ActivityResponses for all found activities.
     */
    @Override
    public Flux<ActivityResponse> findAll(HeaderRequest headerRequest) {
        LoggerUtil.logInput(headerRequest.getTransactionId(), headerRequest.toString(), null);
        return activityRepository.findAll()
                .map(activityMapper::activityToActivityResponse)
                .doOnNext(response ->
                        LoggerUtil.logOutput(headerRequest.getTransactionId(), headerRequest.toString(), response.toString()))
                .doOnError(error -> LoggerUtil.logError(headerRequest.getTransactionId(), error));
    }

    /**
     * Creates a new activity.
     *
     * @param headerRequest   The request header containing metadata for the request.
     * @param activityRequest The request containing the data for the activity to create.
     * @return A Mono emitting the ActivityResponse for the created activity.
     */
    @Override
    public Mono<ActivityResponse> create(HeaderRequest headerRequest, ActivityCreateRequest activityRequest) {
        LoggerUtil.logInput(headerRequest.getTransactionId(), headerRequest.toString(), activityRequest);
        return activityRepository.save(activityMapper.activityCreateRequestToActivity(activityRequest))
                .map(activityMapper::activityToActivityResponse)
                .doOnNext(response ->
                        LoggerUtil.logOutput(headerRequest.getTransactionId(), headerRequest.toString(), response.toString()))
                .doOnError(error -> LoggerUtil.logError(headerRequest.getTransactionId(), error));
    }

    /**
     * Updates an existing activity.
     *
     * @param headerRequest   The request header containing metadata for the request.
     * @param activityRequest The request containing the updated data for the activity.
     * @return A Mono emitting the ActivityResponse for the updated activity.
     */
    @Override
    public Mono<ActivityResponse> update(HeaderRequest headerRequest, ActivityUpdateRequest activityRequest) {
        LoggerUtil.logInput(headerRequest.getTransactionId(), headerRequest.toString(), activityRequest);
        return activityRepository.findById(activityRequest.getId())
                .switchIfEmpty(Mono.error(BusinessException.createException(ConstantMessage.ACTIVITY_UPDATE_ERROR,
                        List.of(ConstantMessage.ACTIVITY_NOT_FOUND), HttpStatus.NOT_FOUND)))
                .map(activity -> activityMapper.activityUpdateRequestToActivity(activity, activityRequest))
                .flatMap(activityRepository::save)
                .map(activityMapper::activityToActivityResponse)
                .doOnNext(response ->
                        LoggerUtil.logOutput(headerRequest.getTransactionId(), headerRequest.toString(), response.toString()))
                .doOnError(error -> LoggerUtil.logError(headerRequest.getTransactionId(), error));
    }

    /**
     * Deletes an activity by its ID.
     *
     * @param headerRequest The request header containing metadata for the request.
     * @param id            The ID of the activity to delete.
     * @return A Mono emitting completion signal once the activity is deleted, or Mono.empty() if no activity was found.
     */
    @Override
    public Mono<Void> delete(HeaderRequest headerRequest, Long id) {
        LoggerUtil.logInput(headerRequest.getTransactionId(), headerRequest.toString(), id);
        return activityRepository.findById(id)
                .switchIfEmpty(Mono.error(BusinessException.createException(ConstantMessage.ACTIVITY_DELETE_ERROR,
                        List.of(ConstantMessage.ACTIVITY_NOT_FOUND), HttpStatus.NOT_FOUND)))
                .flatMap(activityRepository::delete)
                .doOnSuccess(response ->
                        LoggerUtil.logOutput(headerRequest.getTransactionId(), headerRequest.toString(), null))
                .doOnError(error -> LoggerUtil.logError(headerRequest.getTransactionId(), error));
    }
}
