package pt.up.fe.nuieee.whatsup.api;

import java.net.UnknownHostException;

import pt.up.fe.nuieee.whatsup.models.EventModel;
import pt.up.fe.nuieee.whatsup.models.TopItemModel;

import android.os.AsyncTask;

public class FetchAsyncTask<T> extends AsyncTask<Class, Void, T> {

	private AsyncTaskHandler<T> mHandler;
	private Exception mError;
	private Object[] data;

	public FetchAsyncTask(AsyncTaskHandler<T> handler, Object... data) {
		this.mHandler = handler;
		this.data = data;
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
			else if(params[0] == Boolean.class) {
				
				return (T) ServerAPI.authenticateStudentBranch((String) data[0], (String) data[1]);
			}
		} catch (UnknownHostException e) {
			mError = e;
		}
		return null;
	}

}
