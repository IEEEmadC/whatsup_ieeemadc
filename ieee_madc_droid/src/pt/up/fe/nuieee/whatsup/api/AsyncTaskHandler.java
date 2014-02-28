package pt.up.fe.nuieee.whatsup.api;

public interface AsyncTaskHandler<T> {
	
	public void onSuccess(T result);
	
	public void onFailure(Exception error);

}
