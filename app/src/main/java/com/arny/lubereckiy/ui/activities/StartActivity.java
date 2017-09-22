package com.arny.lubereckiy.ui.activities;

import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.*;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import com.arny.arnylib.utils.ToastMaker;
import com.arny.arnylib.utils.Utility;
import com.arny.lubereckiy.R;
import com.arny.lubereckiy.adapter.ObjectAdapter;
import com.arny.lubereckiy.adapter.ObjectViewHolder;
import com.arny.lubereckiy.common.Local;
import com.arny.lubereckiy.models.Pikobject;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class StartActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    private RecyclerView recyclerView;
    private ObjectAdapter adapter;
    private List<Pikobject> original;
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
                            tvNoItems.setVisibility(pikobjects.size() > 0 ? View.GONE : View.VISIBLE);
                            original = pikobjects;
                            initList(pikobjects);
                        },
                        throwable -> {
                            mSwipeRefreshLayout.setRefreshing(false);
                            tvNoItems.setVisibility(View.VISIBLE);
                            ToastMaker.toastError(StartActivity.this, throwable.getMessage());
                        },
                        () -> mSwipeRefreshLayout.setRefreshing(false));
    }

    private void initUI() {
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.srl_all_list);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        recyclerView = (RecyclerView) findViewById(R.id.rv_all_objects);
        tvNoItems = (TextView) findViewById(R.id.tv_noitems);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1, OrientationHelper.VERTICAL, false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        initListAdapter();
    }

    private void initListAdapter() {
        adapter = new ObjectAdapter(this, R.layout.pikobject_list_item, new ObjectViewHolder.SimpleActionListener() {
            @Override
            public void openMap(int position) {
                Local.showObjectMap(StartActivity.this, adapter.getItem(position));
            }

            @Override
            public void OnItemClickListener(int position, Object Item) {
                Local.viewObjectGenPlan(StartActivity.this, adapter.getItem(position));
            }
        });
        adapter.setFilter((constraint, item) -> Utility.matcher("(?i).*" + constraint.toString() + ".*", item.getName()));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        if (getSupportActionBar() == null) {
            return false;
        }
        SearchView sv = new SearchView(getSupportActionBar().getThemedContext());
        sv.setInputType(InputType.TYPE_CLASS_TEXT);
        MenuItemCompat.setShowAsAction(item, MenuItemCompat.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW | MenuItemCompat.SHOW_AS_ACTION_IF_ROOM);
        MenuItemCompat.setActionView(item, sv);
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.filter(query, count -> {
                    tvNoItems.setVisibility(count > 0 ? View.GONE : View.VISIBLE);
                });
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.filter(newText, count -> {
                    tvNoItems.setVisibility(count > 0 ? View.GONE : View.VISIBLE);
                });
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_filter_min_price:
//                adapter.sort((o1, o2) -> o1.getMinPrice().compareTo(o2.getMinPrice()));
//                System.out.println(adapter.getItem(0).getName());
                break;
            case R.id.action_filter_default:
//                System.out.println(adapter.getItem(0).getName());
//                initList(original);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initList(List<Pikobject> data) {
        Local.setAdapterData(adapter, data, recyclerView, false);
    }

    @Override
    public void onRefresh() {
        loadPlans();
    }
}
