package com.vpn.vpnsdktestdemo.ui.activity;

import android.content.Intent;
import android.os.Environment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.vpn.vpnsdktestdemo.R;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * 文件选择界面
 * 
 * @author yyyu
 * 
 */
public class FileExportActivity extends BaseActivity {

	public static final String FILE_CHOICE_RESULT = "FILE_CHOICE_RESULT";
	public static final int FILE_CHOICE_RESULT_CODE= 1000;

	private ListView lvFileExport;
	private SimpleAdapter adapter;
	private String rootPath = Environment.getExternalStorageDirectory().getPath();
	private String currentPath = rootPath;
	private List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
	private Toolbar tbFileExport;

	@Override
	protected int setLayoutId() {
		return R.layout.activity_file_explorer;
	}

	@Override
	protected void initView() {
		lvFileExport = getView(R.id.lv_file_export);
		tbFileExport = getView(R.id.tb_file_export);
		setSupportActionBar(tbFileExport);
	}

	@Override
	protected void initListener() {
		lvFileExport.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
									int position, long id) {
				currentPath = (String) list.get(position).get("currentPath");
				File file = new File(currentPath);
				if (file.isDirectory())
					refreshListItems(currentPath);
				else {
					Intent intent = new Intent();
					intent.putExtra(FILE_CHOICE_RESULT , file.getPath());
					setResult(FILE_CHOICE_RESULT_CODE , intent);
					finish();
				}
			}
		});
		tbFileExport.setNavigationOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				FileExportActivity.this.finish();
			}
		});
	}
	
	@Override
	protected void initData() {
		super.initData();
		adapter = new SimpleAdapter(this, list,
				R.layout.item_file_export, 
				new String[]{"name", "img"}, 
				new int[]{R.id.name, R.id.img});
		lvFileExport.setAdapter(adapter);
		refreshListItems(currentPath);
	}
	
	/**
	 * 根据path刷新文件
	 * 
	 * @param path
	 */
	 private void refreshListItems(String path) {
	        setTitle(path);
	        File[] files = new File(path).listFiles();
	        Log.e("BASE_DIALOG" , "path==="+path+"   files "+files);
	        list.clear();
	        if (files != null) {
	            for (File file : files) {
	                Map<String, Object> map = new HashMap<String, Object>();
	                if (file.isDirectory()) {
	                    map.put("img", R.drawable.directory);
	                } else {
	                    map.put("img", R.drawable.file_doc);
	                }
	                map.put("name", file.getName());
	                map.put("currentPath", file.getPath());
	                list.add(map);
	            }
	        }
	        adapter.notifyDataSetChanged();
	    }
	 
	@Override
	public void onBackPressed() {
		if (rootPath.equals(currentPath)) {
			super.onBackPressed();
		} else {
			File file = new File(currentPath);
			currentPath = file.getParentFile().getPath();
			refreshListItems(currentPath);
		}
	}
}
