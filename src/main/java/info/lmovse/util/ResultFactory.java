package info.lmovse.util;

public class ResultFactory {
	private static final String SUCCES ="succes";
	
	public static Result getSuccesResult() {
		return new Result().setCode(ResultCode.SUCCESS)
				.setMessage(SUCCES);
	}
	
	public static Result getSuccesResult(Object data) {
		return new Result().setCode(ResultCode.SUCCESS)
				.setMessage(SUCCES)
				.setData(data);
	}
	
	public static Result getFailResult(String message) {
		return new Result().setCode(ResultCode.FAIL)
				.setMessage(message);
	}
	
	public static Result getExceptionResult(String message) {
		return new Result().setCode(ResultCode.INTERNAL_SERVER_ERROR)
				.setMessage(message);
	}

}
