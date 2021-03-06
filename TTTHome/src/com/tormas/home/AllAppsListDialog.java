package com.tormas.home;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tormas.home.CellLayout.CellInfo;

public class AllAppsListDialog implements DialogInterface.OnClickListener, DialogInterface.OnCancelListener {
	private AllAppsListAdapter mAllAppsListAdapter;
	private AlertDialog mAlertDialog;
	private ArrayList<ApplicationInfo> appslist;
	private Launcher mLauncher;
	private boolean isSelectedForShortCut;
	private int cateID;
	private String TAG="AllAppsListDialog";
	
	public AllAppsListDialog(Launcher launcher, boolean flag){
		this(launcher, flag, -1);
	}
	
	public AllAppsListDialog(Launcher launcher, boolean flag, int categoryID){
		mLauncher             = launcher;
		isSelectedForShortCut = flag;
		cateID = categoryID;
		
		if(categoryID == -1)
		{
			Log.d(TAG, "for pick application");
		}
	}
	
	public Dialog createDialog(ArrayList<ApplicationInfo> list) {
		appslist = list;
		mAllAppsListAdapter = new AllAppsListAdapter(mLauncher, list);

		final AlertDialog.Builder builder = new AlertDialog.Builder(mLauncher);
		builder.setTitle(mLauncher.getResources().getString(R.string.activity_picker_label));
		builder.setAdapter(mAllAppsListAdapter, this); 

		mAlertDialog = builder.create();
		mAlertDialog.setOnCancelListener(this);

		return mAlertDialog;
	}

	private static class AllAppsListAdapter extends BaseAdapter {
		private ArrayList<ApplicationInfo> apps;
		private Context context;
		
		public AllAppsListAdapter(Context con, ArrayList<ApplicationInfo> list) {
			apps = list;
			context = con;
		}

		public int getCount() {
			return apps.size();
		}

		public Object getItem(int position) {
			return apps.get(position);
		}

		public long getItemId(int position) {
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			if (position < 0 || position >= getCount()) {
				return null;
			}

			TextView tv;
			if (convertView != null && convertView instanceof TextView) {
				tv = (TextView) convertView;
			} else {
				LayoutInflater inflate = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				tv = (TextView) inflate.inflate(R.layout.all_apps_list_item, null);
			}

			final ApplicationInfo app = apps.get(position);
			tv.setCompoundDrawablesWithIntrinsicBounds(new BitmapDrawable(app.iconBitmap), null, null, null);
			tv.setText(app.title);

			return tv;
		}
	}
	
	private void cleanup() {
		mAlertDialog.dismiss();
	}
	
	public void onClick(DialogInterface dialog, int which) {
		cleanup();
		
		final ApplicationInfo app = (ApplicationInfo) appslist.get(which);
		if(isSelectedForShortCut){
			if(aasLayout != null){
				Category.addApplicationCategory(mLauncher, cateID, app);
				aasLayout.refreshAllAppsUI();
			}
		}else{
			CellInfo cellInfo = mLauncher.getMaddItemCellInfo();
			final Workspace mWorkspace = mLauncher.getWorkspace();
			cellInfo.screen = mWorkspace.getCurrentScreen();
			if (!mLauncher.findSingleSlot(cellInfo)) return;
			
			final ShortcutInfo info = mLauncher.getLauncherModel().getShortcutInfo(mLauncher.getPackageManager(),
					app.intent, mLauncher);
			
			if (info != null) {
				info.setActivity(app.intent.getComponent(), Intent.FLAG_ACTIVITY_NEW_TASK |
						Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
				info.container = ItemInfo.NO_ID;
				mWorkspace.addApplicationShortcut(info, cellInfo, mLauncher.isWorkspaceLocked());
			}   
		}
	}

	public void onCancel(DialogInterface dialog) {
		cleanup();
	}
	
	private AllAppsScreenLayout aasLayout;
	public void setShortcutLayout(AllAppsScreenLayout layout){
		aasLayout = layout;
	}
}
