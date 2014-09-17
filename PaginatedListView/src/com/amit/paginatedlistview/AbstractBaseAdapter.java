package com.amit.paginatedlistview;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.BaseAdapter;

public abstract class AbstractBaseAdapter <T> extends BaseAdapter {
	
	protected Context mContext;
	protected LayoutInflater mInflater;	
	protected List<T> mList = new ArrayList<T>();
	
	public AbstractBaseAdapter(Context context, List<T> list) {
		mContext = context;
		mInflater = LayoutInflater.from(mContext);
		mList = list;		
	}
	
	public void setData(List<T> list) {
		mList = list;
		notifyDataSetChanged();
	}
	
	public List<T> getData() {
		return mList;
	}
	
	public void clear() {
		mList.clear();
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return mList.size();
	}

	@Override
	public T getItem(int position) {
		return mList.get(position);
	}
	
	@Override
	public long getItemId(int position) {
		return position;
	}
}
