package pt.up.fe.nuieee.whatsup.api;

import java.net.UnknownHostException;

import android.os.AsyncTask;

public class FetchEventsTask<T> extends AsyncTask<Void, Void, T> {

	private AsyncTaskHandler<T> mHandler;
	private Exception mError;

	public FetchEventsTask(AsyncTaskHandler<T> handler) {
		this.mHandler = handler;
	}

	@Override
	protected void onPostExecute(T result) {
		
		if (mError == null) {
			mHandler.onSuccess(result);
		}
		else {
			mHandler.onFailure(mError);
		}
	}

	@Override
	protected T doInBackground(Void... params) {
		try {
			return (T) ServerAPI.getEvents();
		} catch (UnknownHostException e) {
			mError = e;
		}
		return null;
	}

}
