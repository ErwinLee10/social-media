package responses;

import java.util.List;

public class ErrorResponse {

	private List<ExceptionResponse> errors;

	public List<ExceptionResponse> getErrors() {
		return errors;
	}

	public void setErrors(List<ExceptionResponse> errors) {
		this.errors = errors;
	}

}
