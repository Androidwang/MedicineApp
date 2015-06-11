package com.medicine.app.adapter;
import java.util.List;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.medicine.app.R;
import com.medicine.app.model.HistoryItemBean;

/**
 * 历史更知识的适配器
 * @author wangyang
 *
 */
public class HistoryListAdapter extends BaseAdapter{
	private List<HistoryItemBean> historyList;
	private Context context;
	public HistoryListAdapter(Context context, List<HistoryItemBean> historyList){
		this.context = context;
		this.historyList = historyList;
	}
	@Override
	public int getCount() {
		if (historyList != null) {
			return historyList.size();
		}
		return 0;
	}
	@Override
	public Object getItem(int position) {
		return null;
	}
	@Override
	public long getItemId(int position) {
		return 0;
	}
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
	final ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(R.layout.fragment_history_item, null);
			holder.tv_history_time = (TextView)convertView.findViewById(R.id.tv_history_time);
			holder.tv_history_content = (TextView)convertView.findViewById(R.id.tv_history_content);
			holder.tv_historyTagNum = (TextView)convertView.findViewById(R.id.tv_historyTagNum);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		HistoryItemBean bean = historyList.get(position);
		holder.tv_history_time.setText(bean.getHistoryTime());
		holder.tv_history_content.setText(bean.getHistoryContent());
		holder.tv_historyTagNum.setText(bean.getHistoryTagNum());
		
		return convertView;
	}
	private class ViewHolder {
		TextView tv_history_time;
		TextView tv_history_content;
		TextView tv_historyTagNum;
	}
}
