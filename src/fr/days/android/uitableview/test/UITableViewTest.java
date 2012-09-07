package fr.days.android.uitableview.test;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;
import fr.days.android.uitableview.R;
import fr.days.android.uitableview.adapter.UITableViewAdapter;
import fr.days.android.uitableview.listener.OnCellAccessoryClickListener;
import fr.days.android.uitableview.listener.OnCellClickListener;
import fr.days.android.uitableview.listener.OnCellLongClickListener;
import fr.days.android.uitableview.listener.OnHeaderClickListener;
import fr.days.android.uitableview.listener.OnHeaderLongClickListener;
import fr.days.android.uitableview.model.AccessoryType;
import fr.days.android.uitableview.model.IndexPath;
import fr.days.android.uitableview.model.UITableCellItem;
import fr.days.android.uitableview.model.UITableHeaderItem;
import fr.days.android.uitableview.view.UITableCellView;
import fr.days.android.uitableview.view.UITableHeaderView;
import fr.days.android.uitableview.view.UITableView;

public class UITableViewTest extends Activity {

	private UITableView tableView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		tableView = (UITableView) findViewById(R.id.listView);
		SimpleUITableViewAdapter tableViewAdapter = new SimpleUITableViewAdapter();
		tableView.setAdapter(tableViewAdapter);
		tableView.setOnCellClickListener(tableViewAdapter);
		tableView.setOnCellLongClickListener(tableViewAdapter);
		tableView.setOnCellAccessoryClickListener(tableViewAdapter);
		tableView.setOnHeaderClickListener(tableViewAdapter);
		tableView.setOnHeaderLongClickListener(tableViewAdapter);
	}

	class SimpleUITableViewAdapter extends UITableViewAdapter implements OnCellClickListener, OnCellLongClickListener, OnCellAccessoryClickListener, OnHeaderClickListener, OnHeaderLongClickListener {

		private int[] color_line1_default;
		private int[] color_line2_default;
		private int[] color_line1_pressed;
		private int[] color_line2_pressed;

		public SimpleUITableViewAdapter() {
			color_line1_default = new int[] { getResources().getColor(R.color.base_start_color_line1_default), getResources().getColor(R.color.base_end_color_line1_default) };
			color_line2_default = new int[] { getResources().getColor(R.color.base_start_color_line2_default), getResources().getColor(R.color.base_end_color_line2_default) };
			color_line1_pressed = new int[] { getResources().getColor(R.color.base_start_color_line1_pressed), getResources().getColor(R.color.base_end_color_line1_pressed) };
			color_line2_pressed = new int[] { getResources().getColor(R.color.base_start_color_line2_pressed), getResources().getColor(R.color.base_end_color_line2_pressed) };
		}

		@Override
		public int numberOfGroups() {
			return 4;
		}

		@Override
		public int numberOfRows(int group) {
			switch (group) {
			case 0:
				return 5;
			case 1:
				return 3;
			case 2:
				return 5;
			case 3:
				return 3;
			}
			return 0;
		}

		@Override
		public UITableHeaderItem headerItemForGroup(Context context, IndexPath indexPath) {
			return new UITableHeaderItem("group " + indexPath.getGroup());
		}

		@Override
		public UITableCellItem cellItemForRow(Context context, IndexPath indexPath) {
			String title = "Cell number " + indexPath.getRow() + " in group " + indexPath.getGroup() + " bla bla bla";
			String subtitle = (indexPath.getRow() % 2 == 0) ? "Subtitle " + indexPath.getRow() : null;
			return new UITableCellItem(title, subtitle);
		}

		@Override
		public UITableHeaderView headerViewForGroup(Context context, IndexPath indexPath, UITableHeaderItem headerItem, UITableHeaderView convertView) {
			UITableHeaderView headerView;
			if (convertView == null) {
				headerView = new UITableHeaderView(context, indexPath);
			} else {
				headerView = (UITableHeaderView) convertView;
			}
			headerView.setTitle(headerItem.title);

			return headerView;
		}

		@Override
		public UITableCellView cellViewForRow(Context context, IndexPath indexPath, UITableCellItem cellItem, UITableCellView convertView) {
			UITableCellView cellView;
			if (convertView == null) {
				cellView = new UITableCellView(context, indexPath);
				cellView.setMinimumHeight(80);
			} else {
				cellView = (UITableCellView) convertView;
			}

			cellView.setTitle(cellItem.title);
			cellView.setSubtitle(cellItem.subtitle);

			if (indexPath.getGroup() % 2 == 0) {
				// Image
				if (indexPath.getRow() % 3 == 0)
					cellView.setImage(getResources().getDrawable(R.drawable.ic_launcher));
				if (indexPath.getRow() % 3 == 1)
					cellView.setImage(R.drawable.ic_action_search);

				// Accessory
				if (indexPath.getRow() % 3 != 0) {
					cellView.setAccessory(AccessoryType.DISCLOSURE);
				}

				// Set alternated background color
				if (indexPath.getRow() % 2 == 0) {
					cellView.setBackgroundColor(color_line1_default, color_line1_pressed);
				} else {
					cellView.setBackgroundColor(color_line2_default, color_line2_pressed);
				}
			} else {
				cellView.setImage((Integer) null);
				cellView.setAccessory(AccessoryType.NONE);
				cellView.setBackgroundColor(color_line2_default, color_line2_pressed);
			}

			return cellView;
		}

		@Override
		public void onCellClick(IndexPath indexPath) {
			Toast.makeText(getApplicationContext(), "Cell clicked : " + indexPath, 1000).show();
		}

		@Override
		public boolean onCellLongClick(IndexPath indexPath) {
			Toast.makeText(getApplication(), "Cell long clicked : " + indexPath, 1000).show();
			return indexPath.getRow() % 2 == 0;
		}

		@Override
		public void onCellAccessoryClick(IndexPath indexPath) {
			Toast.makeText(getApplication(), "Cell accessory clicked : " + indexPath, 1000).show();
		}

		@Override
		public void onHeaderClick(IndexPath indexPath) {
			Toast.makeText(getApplicationContext(), "Header clicked : " + indexPath, 1000).show();
		}

		@Override
		public boolean onHeaderLongClick(IndexPath indexPath) {
			Toast.makeText(getApplicationContext(), "Header long clicked : " + indexPath, 1000).show();
			return indexPath.getGroup() % 2 == 0;
		}

	}
}
