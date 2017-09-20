package com.arny.lubereckiy.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.*;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import com.arny.arnylib.adapters.SimpleBindableAdapter;
import com.arny.arnylib.utils.ToastMaker;
import com.arny.arnylib.utils.Utility;
import com.arny.lubereckiy.R;
import com.arny.lubereckiy.adapter.ObjectViewHolder;
import com.arny.lubereckiy.common.Local;
import com.arny.lubereckiy.models.Pikobject;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import java.util.ArrayList;
import java.util.List;

public class StartActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    private RecyclerView recyclerView;
    private SimpleBindableAdapter adapter;
    private List<Pikobject> original;
    private List<Pikobject> workPikobjects;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private String srchTxt;
    private TextView tvNoItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initUI();
        loadPlans();
    }

    private void loadPlans() {
        Local.loadPikObjects()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> mSwipeRefreshLayout.setRefreshing(true))
                .subscribe(
                        pikobjects -> {
                            original = pikobjects;
                            initList(pikobjects, true);
                        },
                        throwable -> {
                            mSwipeRefreshLayout.setRefreshing(false);
                            ToastMaker.toastError(StartActivity.this, throwable.getMessage());
                        },
                        () -> mSwipeRefreshLayout.setRefreshing(false));
    }

    private void initList(List<Pikobject> pikobjects, boolean hasFocus) {
        System.out.println("hasFocus:" + hasFocus + " size:" + pikobjects.size() + " original:" + original.size());
        workPikobjects = hasFocus ? pikobjects : original;
        setAdapterData(workPikobjects, false);
    }

    private void initUI() {
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.srl_all_list);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        recyclerView = (RecyclerView) findViewById(R.id.rv_all_objects);
        tvNoItems = (TextView) findViewById(R.id.tv_noitems);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1, OrientationHelper.VERTICAL, false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter = new SimpleBindableAdapter<>(this, R.layout.pikobject_list_item, ObjectViewHolder.class);
        adapter.setActionListener(new ObjectViewHolder.SimpleActionListener() {
            @Override
            public void openMap(int position) {
                Local.showObjectMap(StartActivity.this, workPikobjects.get(position));
            }

            @Override
            public void OnItemClickListener(int position, Object Item) {
                Local.viewObjectGenPlan(StartActivity.this, workPikobjects.get(position));
            }
        });
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        srchTxt = "";
        MenuItem item = menu.findItem(R.id.action_search);
        if (getSupportActionBar() == null) {
            return false;
        }
        SearchView sv = new SearchView(getSupportActionBar().getThemedContext());
        sv.setInputType(InputType.TYPE_CLASS_TEXT);
        MenuItemCompat.setShowAsAction(item, MenuItemCompat.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW | MenuItemCompat.SHOW_AS_ACTION_IF_ROOM);
        MenuItemCompat.setActionView(item, sv);
        sv.setOnQueryTextFocusChangeListener((v, hasFocus) -> {
            if (!Utility.empty(srchTxt)) {
                initList(getFilteredList(srchTxt), true);
            } else {
                initList(workPikobjects, false);
            }
        });
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                srchTxt = query;
                initList(getFilteredList(query), true);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                srchTxt = newText;
                initList(getFilteredList(newText), srchTxt.length() != 0);
                return false;
            }
        });
        return true;
    }

    public ArrayList<Pikobject> getFilteredList(String query) {
        query = query.toLowerCase();
        ArrayList<Pikobject> filteredList = new ArrayList<>();
        if (workPikobjects != null) {
            for (int i = 0; i < workPikobjects.size(); i++) {
                final String text = workPikobjects.get(i).getName();
                if (!Utility.empty(query)) {
                    if (Utility.matcher("(?i).*" + query + ".*", text)) {
//                    if (text.contains(query)) {
                        filteredList.add(workPikobjects.get(i));
                    }
                } else {
                    filteredList.add(workPikobjects.get(i));
                }
            }
        }
        return filteredList;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_filter_min_price:
                initList(Local.filterObjects(workPikobjects, Local.FilterObjectsType.minPrice), true);
                break;
            case R.id.action_filter_default:
                initList(original, true);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void showLoadProgress() {
        mSwipeRefreshLayout.setRefreshing(true);
    }

    private void hideLoadProgress() {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    private void showError(String error) {
        ToastMaker.toastError(this, error);
    }

    public void navigateTo(Intent intent) {
        startActivity(intent);
    }

    private void setAdapterData(List<Pikobject> data, boolean update) {
        Local.setAdapterData(adapter, data, recyclerView, update);
    }

    @Override
    public void onRefresh() {
        loadPlans();
    }
}
