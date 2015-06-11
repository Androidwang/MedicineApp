package com.medicine.app.widgets;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.medicine.app.R;
import com.medicine.app.adapter.AbstractSpinerAdapter;
import com.medicine.app.adapter.AbstractSpinerAdapter.IOnItemSelectListener;
public class SpinerPopWindow extends PopupWindow implements OnItemClickListener{
	private Context mContext;
	private ListView mListView;
	private AbstractSpinerAdapter mAdapter;
	private IOnItemSelectListener mItemSelectListener;
	private List<CustemObject> nameList = new ArrayList<CustemObject>();
	
	public SpinerPopWindow(Context context)
	{
		super(context);	
		mContext = context;
		init();
	}
//	public SpinerPopWindow(Context context,String[] names)
//	{
//		super(context);
//		for (int i = 0; i < names.length; i++) {
//			CustemObject object = new CustemObject();
//			object.data = names[i];
//			nameList.add(object);
//		}
//		mAdapter = new CustemSpinerAdapter(context);
//		mAdapter.refreshData(nameList, 0);
//		mContext = context;
//		init();
//	}
	public void setItemListener(IOnItemSelectListener listener){
		mItemSelectListener = listener;
	}
	
	public void setAdatper(AbstractSpinerAdapter adapter){
		mAdapter = adapter;
		mListView.setAdapter(mAdapter);	
	}
	public void setSpinnerAdatper(String[] str){
		if (nameList != null) {
			nameList.clear();
		}
		mAdapter = new CustemSpinerAdapter(mContext);
		for (int i = 0; i < str.length; i++) {
			CustemObject object = new CustemObject();
			object.data = str[i];
			nameList.add(object);
		}
		mAdapter.refreshData(nameList, 0);
		if(mAdapter!=null){  
			mAdapter.notifyDataSetChanged();  
        }
		mListView.setAdapter(mAdapter);
	}
	
	private void init()
	{
		View view = LayoutInflater.from(mContext).inflate(R.layout.spiner_window_layout, null);
		setContentView(view);		
		setWidth(LayoutParams.WRAP_CONTENT);
		setHeight(LayoutParams.WRAP_CONTENT);
		
		setFocusable(true);
    	ColorDrawable dw = new ColorDrawable(0x00);
		setBackgroundDrawable(dw);
	
		
		mListView = (ListView) view.findViewById(R.id.listview);
		mListView.setOnItemClickListener(this);
	}
	
	
	public <T> void refreshData(List<T> list, int selIndex)
	{
		if (list != null && selIndex  != -1)
		{
			if (mAdapter != null){
				mAdapter.refreshData(list, selIndex);
			}		
		}
	}


	@Override
	public void onItemClick(AdapterView<?> arg0, View view, int pos, long arg3) {
		dismiss();
		if (mItemSelectListener != null){
			mItemSelectListener.onItemClick(pos);
		}
	}


	
}
