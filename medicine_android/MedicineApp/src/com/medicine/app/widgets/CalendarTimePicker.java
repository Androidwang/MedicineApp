package com.medicine.app.widgets;

import java.util.Calendar;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;
import com.medicine.app.R;
import com.medicine.app.wheelview.NumericWheelAdapter;
import com.medicine.app.wheelview.OnWheelScrollListener;
import com.medicine.app.wheelview.WheelView;

@SuppressLint("CutPasteId")
/**
 * 时间选择自定义控件
 * @author wangyang
 *
 */
public class CalendarTimePicker {
	private Context context;
	private PopupWindow popupWindow;
	private WheelView year;
	private WheelView month;
	TextView mDate;

	public CalendarTimePicker(Context context, TextView mDate) {
		this.context = context;
		this.mDate = mDate;
		LayoutInflater inflater = ((Activity) context).getLayoutInflater();
		View view = inflater.inflate(R.layout.calendar_time_picker, null);
		popupWindow = new PopupWindow(view, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		initView(view);

	}

	/**
	 * 初始化控件，并设置时间数据
	 * 
	 * @param view
	 */
	private void initView(View view) {
		Calendar c = Calendar.getInstance();
		int hour = c.get(Calendar.HOUR_OF_DAY);
		int minute = c.get(Calendar.MINUTE);
		year = (WheelView) view.findViewById(R.id.year);
		year.setAdapter(new NumericWheelAdapter(1, 24, "%02d"));
		year.setCyclic(true);
		year.addScrollingListener(scrollListener);

		month = (WheelView) view.findViewById(R.id.month);
		month.setAdapter(new NumericWheelAdapter(1, 60, "%02d"));
		month.setCyclic(true);
		month.addScrollingListener(scrollListener);

		year.setCurrentItem(hour);
		month.setCurrentItem(minute);
		;

		Button btSure = (Button) view.findViewById(R.id.bt_sure);
		/**
		 * 点击时间选择确定设置监听
		 */
		btSure.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dismiss();
				String birthday = new StringBuilder()
						.append((year.getCurrentItem() + 1)).append(":")
						.append(month.getCurrentItem() + 1).toString();

				System.out.println("birthday" + birthday);

				mDate.setText(birthday);
			}
		});
		/**
		 * 点击时间选择确定设置监听
		 */
		Button cancel = (Button) view.findViewById(R.id.bt_cancel);
		cancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dismiss();
			}
		});

	}

	public int getScreenWidth() {
		DisplayMetrics dm = new DisplayMetrics();
		((Activity) context).getWindowManager().getDefaultDisplay()
				.getMetrics(dm);
		int screenW = dm.widthPixels;
		return screenW;
	}

	public void show(View parent, Context context) {
		popupWindow.showAtLocation(parent, Gravity.BOTTOM, 0, 0);//
		popupWindow.setAnimationStyle(R.style.popwin_anim_style);
		popupWindow.setFocusable(true);
		popupWindow.setOutsideTouchable(true);
		popupWindow.update();
	}

	public void dismiss() {
		popupWindow.dismiss();
	}

	OnWheelScrollListener scrollListener = new OnWheelScrollListener() {
		@Override
		public void onScrollingStarted(WheelView wheel) {

		}

		@Override
		public void onScrollingFinished(WheelView wheel) {
		}
	};

}
