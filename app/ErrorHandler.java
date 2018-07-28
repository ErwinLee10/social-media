import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import org.apache.commons.beanutils.BeanUtils;

import exceptions.BaseException;
import play.http.HttpErrorHandler;
import play.libs.Json;
import play.mvc.Http.RequestHeader;
import play.mvc.Result;
import play.mvc.Results;
import responses.ErrorResponse;
import responses.ExceptionResponse;

public class ErrorHandler implements HttpErrorHandler{

	@Override
	public CompletionStage<Result> onClientError(RequestHeader arg0, int arg1, String arg2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CompletionStage<Result> onServerError(RequestHeader arg0, Throwable exception) {
		BaseException baseException = (BaseException) exception;
		List<ExceptionResponse> errors = new ArrayList<>();
		ExceptionResponse exceptionResponse = new  ExceptionResponse();
		try {
			BeanUtils.copyProperties(exceptionResponse, baseException);
		} catch (Exception e) {
			Results.internalServerError(Json.toJson(exception));
		}
		
		errors.add(exceptionResponse);
		ErrorResponse resp = new ErrorResponse();
		resp.setErrors(errors);
		
		if(baseException.getHttpErrorCode() == 404) {
			return CompletableFuture.completedFuture(
	                Results.notFound(Json.toJson(resp))
	        );
		}else {
			return CompletableFuture.completedFuture(
	                Results.internalServerError(Json.toJson(resp))
	        );
		}
		
		
	}

}
