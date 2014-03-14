package pt.up.fe.nuieee.whatsup.api;

import java.net.UnknownHostException;

import pt.up.fe.nuieee.whatsup.models.EventModel;
import pt.up.fe.nuieee.whatsup.models.TopItemModel;

import android.os.AsyncTask;

public class FetchAsyncTask<T> extends AsyncTask<Class, Void, T> {

	private AsyncTaskHandler<T> mHandler;
	private Exception mError;

	public FetchAsyncTask(AsyncTaskHandler<T> handler, Object... data) {
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
	protected T doInBackground(Class... params) {
		try {
			if (params[0] == EventModel.class)
			{
				return (T) ServerAPI.getEvents();
			}
			else if (params[0] == TopItemModel.class)
			{
				return (T) ServerAPI.getTopSBs();
			}
		} catch (UnknownHostException e) {
			mError = e;
		}
		return null;
	}

}
