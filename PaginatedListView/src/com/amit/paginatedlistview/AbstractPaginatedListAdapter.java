package com.amit.paginatedlistview;

import java.util.List;

import android.content.Context;

public abstract class AbstractPaginatedListAdapter<T> extends AbstractBaseAdapter<T>{
	
	private PaginatedListView mPaginatedListView;
	private int mTotalPage = 1;

	public AbstractPaginatedListAdapter(Context context, List<T> list) {
		super(context, list);
		
	}
	
	public void addData(List<T> list) {		
		mList.addAll(list);
		notifyDataSetChanged();
		if(mPaginatedListView != null) {
			mPaginatedListView.finishLoadingNextPage();
		}
	}
	
	public void setPaginatedListView(PaginatedListView listView) {
		mPaginatedListView = listView;
	}
	
	public void setTotalPage(int totalPage) {
		mTotalPage = totalPage;
	}
	
	public int getTotalPage() {
		return mTotalPage;
	}

}
