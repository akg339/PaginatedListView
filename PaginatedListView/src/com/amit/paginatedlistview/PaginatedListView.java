package com.amit.paginatedlistview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListAdapter;
import android.widget.ListView;

@SuppressWarnings("rawtypes")
public class PaginatedListView extends ListView{
	
	private OnScrollListener mChildOnScrollListener;
	private View mFooterProgressBar;
	private LoadNextPageListener mLoadNextPage;
	private int mNextPageIndex = 0;
	private AbstractPaginatedListAdapter mAdapter;
	private boolean isLoading = false;

	public PaginatedListView(Context context) {
		this(context, null);
	}

	public PaginatedListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		setupPaginationProperties();
	}

	public PaginatedListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		setupPaginationProperties();
	}
	
	private void setupPaginationProperties() {
		
		mFooterProgressBar = inflate(getContext(), R.layout.wrapped_progress_dialog, null);
		
		super.setOnScrollListener(new PaginatedOnScrollListener() {
			
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				super.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
				
				if(firstVisibleItem + visibleItemCount == totalItemCount) {
					if(!isLoading) {
						startLoadingNextPage(++mNextPageIndex);
					}
				}				
			}
		});
	}
	
	@Override
	public void setOnScrollListener(OnScrollListener l) {
		mChildOnScrollListener = l;
	}
	
	public void setLoadNextPageListener(LoadNextPageListener loadNextPage) {
		mLoadNextPage = loadNextPage;
	}
	
	private void startLoadingNextPage( int nextPageIndex) {
		
		if(mLoadNextPage != null) {
			if(mNextPageIndex < mAdapter.getTotalPage()) {
				addFooterView(mFooterProgressBar);
				mLoadNextPage.onLoadingNextPageListener(mNextPageIndex);
				isLoading = true;
			} else {
			}			
		}
	}
	
	/**
	 * After fetching data, this should be called.
	 */
	public void finishLoadingNextPage() {
		removeFooterView(mFooterProgressBar);
		isLoading = false;
	}	
	
	
	@Override
	public void setAdapter(ListAdapter adapter) {
		
		if(adapter instanceof AbstractPaginatedListAdapter) {
			mAdapter = (AbstractPaginatedListAdapter) adapter;
			mAdapter.setPaginatedListView(this);
		} else {
			throw new ClassCastException("Adapter must be an instance of SimplePaginatedListAdapter.");
		}
		
		super.setAdapter(adapter);
	}
	
	/**
	 * This scroll listener take care of the implementation of 
	 * onScrollListener implemented by consumer of this ListView
	 */
	private abstract class PaginatedOnScrollListener implements OnScrollListener {
		
		@Override
		public void onScrollStateChanged(AbsListView view, int scrollState) {
			
			if(mChildOnScrollListener != null) {
				mChildOnScrollListener.onScrollStateChanged(view, scrollState);
			}
			
		}

		@Override
		public void onScroll(AbsListView view, int firstVisibleItem,
				int visibleItemCount, int totalItemCount) {
			
			if(mChildOnScrollListener != null) {
				mChildOnScrollListener.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
			}			
		}		
	}
	
	/**
	 * 
	 * Interface to be implemented by the consumer of this ListView.
	 */
	public interface LoadNextPageListener {
		/**
		 * Method in which you can make network/database call
		 * to fetch more data.
		 * @param nextPageIndex index of next page 
		 */
		public void onLoadingNextPageListener(int nextPageIndex);
	}
}
