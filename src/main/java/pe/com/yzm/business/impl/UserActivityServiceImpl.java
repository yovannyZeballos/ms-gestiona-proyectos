package pe.com.yzm.business.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import pe.com.yzm.business.UserActivityService;
import pe.com.yzm.core.exception.BusinessException;
import pe.com.yzm.core.logger.LoggerUtil;
import pe.com.yzm.core.model.HeaderRequest;
import pe.com.yzm.expose.request.UserActivityCreateRequest;
import pe.com.yzm.expose.request.UserActivityUpdateRequest;
import pe.com.yzm.expose.response.UserActivityResponse;
import pe.com.yzm.mapper.UserActivityMapper;
import pe.com.yzm.repository.UserActivityRepository;
import pe.com.yzm.util.ConstantMessage;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * <b>Class</b>: UserActivityServiceImpl <br/>
 * <b>Copyright</b>: 2024 Yovanny Zeballos Medina<br/>.
 *
 * @author 2024  Yovanny Zeballos Medina <br/>
 * <u>Developed by</u>: Yovanny Zeballos <br/>
 * <u>Changes:</u><br/>
 * <ul>
 *   <li>
 *     setiembre 09, 2024 Creación de Clase.
 *   </li>
 * </ul>
 */
@Service
@RequiredArgsConstructor
public class UserActivityServiceImpl implements UserActivityService {

    private final UserActivityRepository userActivityRepository;
    private final UserActivityMapper userActivityMapper;

    /**
     * Finds a UserActivity by its ID.
     *
     * @param headerRequest The request header containing metadata for the request.
     * @param id            The ID of the UserActivity to find.
     * @return A Mono emitting the UserActivityResponse for the found UserActivity, or Mono.empty() if no UserActivity was found.
     */
    @Override
    public Mono<UserActivityResponse> findById(HeaderRequest headerRequest, Long id) {
        LoggerUtil.logInput(headerRequest.getTransactionId(), headerRequest.toString(), id);
        return userActivityRepository.findById(id)
                .switchIfEmpty(Mono.error(BusinessException.createException(ConstantMessage.USER_ACTIVITY_GET_ERROR,
                        List.of(ConstantMessage.USER_ACTIVITY_NOT_FOUND), HttpStatus.NOT_FOUND)))
                .map(userActivityMapper::userActivityToUserActivityResponse)
                .doOnNext(response ->
                        LoggerUtil.logOutput(headerRequest.getTransactionId(), headerRequest.toString(), response.toString()))
                .doOnError(error -> LoggerUtil.logError(headerRequest.getTransactionId(), error));
    }

    /**
     * Finds all UserActivities.
     *
     * @param headerRequest The request header containing metadata for the request.
     * @return A Flux emitting the UserActivityResponses for all found UserActivities.
     */
    @Override
    public Flux<UserActivityResponse> findAll(HeaderRequest headerRequest) {
        LoggerUtil.logInput(headerRequest.getTransactionId(), headerRequest.toString(), null);
        return userActivityRepository.findAll()
                .map(userActivityMapper::userActivityToUserActivityResponse)
                .doOnNext(response ->
                        LoggerUtil.logOutput(headerRequest.getTransactionId(), headerRequest.toString(), response.toString()))
                .doOnError(error -> LoggerUtil.logError(headerRequest.getTransactionId(), error));
    }

    /**
     * Creates a new UserActivity.
     *
     * @param headerRequest       The request header containing metadata for the request.
     * @param userActivityRequest The request containing the data for the UserActivity to create.
     * @return A Mono emitting the UserActivityResponse for the created UserActivity.
     */
    @Override
    public Mono<UserActivityResponse> create(HeaderRequest headerRequest, UserActivityCreateRequest userActivityRequest) {
        LoggerUtil.logInput(headerRequest.getTransactionId(), headerRequest.toString(), userActivityRequest);
        return userActivityRepository.save(userActivityMapper.activityCreateRequestToActivity(userActivityRequest))
                .map(userActivityMapper::userActivityToUserActivityResponse)
                .doOnNext(response ->
                        LoggerUtil.logOutput(headerRequest.getTransactionId(), headerRequest.toString(), response.toString()))
                .doOnError(error -> LoggerUtil.logError(headerRequest.getTransactionId(), error));
    }

    /**
     * Updates an existing UserActivity.
     *
     * @param headerRequest       The request header containing metadata for the request.
     * @param userActivityRequest The request containing the updated data for the UserActivity.
     * @return A Mono emitting the UserActivityResponse for the updated UserActivity.
     */
    @Override
    public Mono<UserActivityResponse> update(HeaderRequest headerRequest, UserActivityUpdateRequest userActivityRequest) {
        LoggerUtil.logInput(headerRequest.getTransactionId(), headerRequest.toString(), userActivityRequest);
        return userActivityRepository.findById(userActivityRequest.getId())
                .switchIfEmpty(Mono.error(BusinessException.createException(ConstantMessage.USER_ACTIVITY_UPDATE_ERROR,
                        List.of(ConstantMessage.USER_ACTIVITY_NOT_FOUND), HttpStatus.NOT_FOUND)))
                .map(activity -> userActivityMapper.userActivityUpdateRequestToUserActivity(activity, userActivityRequest))
                .flatMap(userActivityRepository::save)
                .map(userActivityMapper::userActivityToUserActivityResponse)
                .doOnNext(response ->
                        LoggerUtil.logOutput(headerRequest.getTransactionId(), headerRequest.toString(), response.toString()))
                .doOnError(error -> LoggerUtil.logError(headerRequest.getTransactionId(), error));
    }

    /**
     * Deletes a UserActivity by its ID.
     *
     * @param headerRequest The request header containing metadata for the request.
     * @param id            The ID of the UserActivity to delete.
     * @return A Mono emitting completion signal once the UserActivity is deleted, or Mono.empty() if no UserActivity was found.
     */
    @Override
    public Mono<Void> delete(HeaderRequest headerRequest, Long id) {
        LoggerUtil.logInput(headerRequest.getTransactionId(), headerRequest.toString(), id);
        return userActivityRepository.findById(id)
                .switchIfEmpty(Mono.error(BusinessException.createException(ConstantMessage.USER_ACTIVITY_DELETE_ERROR,
                        List.of(ConstantMessage.USER_ACTIVITY_NOT_FOUND), HttpStatus.NOT_FOUND)))
                .flatMap(userActivityRepository::delete)
                .doOnSuccess(response ->
                        LoggerUtil.logOutput(headerRequest.getTransactionId(), headerRequest.toString(), null))
                .doOnError(error -> LoggerUtil.logError(headerRequest.getTransactionId(), error));
    }
}
