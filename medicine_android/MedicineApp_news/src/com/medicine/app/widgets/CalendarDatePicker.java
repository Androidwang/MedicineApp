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
 * 日期选择自定义控件
 * @author wangyang
 *
 */
public class CalendarDatePicker {
	private Context context;
	private PopupWindow popupWindow;
	private WheelView year;
	private WheelView month;
	private WheelView day;
	TextView mDate;

	public CalendarDatePicker(Context context, TextView mDate) {
		this.context = context;
		this.mDate = mDate;
		LayoutInflater inflater = ((Activity) context).getLayoutInflater();
		View view = inflater.inflate(R.layout.calendar_date_picker, null);
		popupWindow = new PopupWindow(view, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);

		initView(view);

	}

	/**
	 * 初始化控件，并设置日期数据
	 * 
	 * @param view
	 */
	private void initView(View view) {
		Calendar c = Calendar.getInstance();
		int norYear = c.get(Calendar.YEAR);
		int curMonth = c.get(Calendar.MONTH) + 1;// 通过Calendar算出的月数要+1
		int curDate = c.get(Calendar.DATE);
		year = (WheelView) view.findViewById(R.id.year);
		year.setAdapter(new NumericWheelAdapter(1950, norYear));
		year.setLabel("年");
		year.setCyclic(true);
		year.addScrollingListener(scrollListener);

		month = (WheelView) view.findViewById(R.id.month);
		month.setAdapter(new NumericWheelAdapter(1, 12, "%02d"));
		month.setLabel("月");
		month.setCyclic(true);
		month.addScrollingListener(scrollListener);

		day = (WheelView) view.findViewById(R.id.day);
		initDay(norYear, curMonth);
		day.setLabel("日");
		day.setCyclic(true);

		year.setCurrentItem(norYear - 1950);
		month.setCurrentItem(curMonth - 1);
		day.setCurrentItem(curDate - 1);

		Button btSure = (Button) view.findViewById(R.id.bt_sure);
		/**
		 * 点击日期选择确定设置监听
		 */
		btSure.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dismiss();
				String birthday = new StringBuilder()
						.append((year.getCurrentItem() + 1950))
						.append("-")
						.append((month.getCurrentItem() + 1) < 10 ? "0"
								+ (month.getCurrentItem() + 1) : (month
								.getCurrentItem() + 1))
						.append("-")
						.append(((day.getCurrentItem() + 1) < 10) ? "0"
								+ (day.getCurrentItem() + 1) : (day
								.getCurrentItem() + 1)).toString();
				mDate.setText(birthday);
			}
		});
		/**
		 * 点击日期选择确定取消监听
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
		((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(dm);
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

	/**
	 * 判断闰年
	 * 
	 * @param year
	 * @param month
	 * @return
	 */
	private int getDay(int year, int month) {
		int day = 30;
		boolean flag = false;
		switch (year % 4) {
		case 0:
			flag = true;
			break;
		default:
			flag = false;
			break;
		}
		switch (month) {
		case 1:
		case 3:
		case 5:
		case 7:
		case 8:
		case 10:
		case 12:
			day = 31;
			break;
		case 2:
			day = flag ? 29 : 28;
			break;
		default:
			day = 30;
			break;
		}
		return day;
	}

	/**
	 */
	private void initDay(int arg1, int arg2) {
		day.setAdapter(new NumericWheelAdapter(1, getDay(arg1, arg2), "%02d"));
	}
}
