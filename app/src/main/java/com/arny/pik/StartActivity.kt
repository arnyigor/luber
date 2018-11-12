package com.arny.pik

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.OrientationHelper
import android.support.v7.widget.SearchView
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.arny.pik.adapter.ObjectsAdapter
import com.arny.pik.data.Local
import com.arny.pik.data.api.API
import com.arny.pik.data.models.Pikobject
import com.arny.pik.utils.ToastMaker
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_home.*

class StartActivity : AppCompatActivity(), SwipeRefreshLayout.OnRefreshListener {
    private var adapter: ObjectsAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        initUI()
        loadPlans()
    }

    @SuppressLint("CheckResult")
    private fun loadPlans() {
        API.loadPikObjects()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { disposable -> srl_all_list.isRefreshing = true }
                .subscribe({ pikobjects ->
                            srl_all_list.isRefreshing = false
                            tv_noitems.visibility = if (pikobjects.size > 0) View.GONE else View.VISIBLE
                            initList(pikobjects)
                        }, { throwable ->
                            srl_all_list.isRefreshing = false
                            tv_noitems.visibility = View.VISIBLE
                            ToastMaker.toastError(this@StartActivity, throwable.message)
                        })
    }

    private fun initUI() {
        srl_all_list.setOnRefreshListener(this)
        rv_all_objects.layoutManager = GridLayoutManager(this, 1, OrientationHelper.VERTICAL, false)
        rv_all_objects.itemAnimator = DefaultItemAnimator()
        initListAdapter()
    }

    private fun initListAdapter() {
        adapter = ObjectsAdapter(object : ObjectsAdapter.ObjectsListener {
            override fun openMap(position: Int, item: Pikobject) {
                Local.showObjectMap(this@StartActivity, item)
            }

            override fun onItemClick(position: Int, item: Pikobject) {
                Local.viewObjectGenPlan(this@StartActivity, item)
            }
        })
//        adapter!!.setFilter({ constraint, item -> Utility.matcher("(?i).*" + constraint.toString() + ".*", item.getName()) })
        rv_all_objects.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater?.inflate(R.menu.main_menu, menu)
        val item = menu?.findItem(R.id.action_search)
        val sv = item?.actionView as? SearchView
        sv?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
//                adapter!!.filter(query) { count -> tv_noitems.visibility = if (count > 0) View.GONE else View.VISIBLE }
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
//                adapter!!.filter(newText) { count -> tv_noitems.visibility = if (count > 0) View.GONE else View.VISIBLE }
                return false
            }
        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        when (id) {
            R.id.action_filter_min_price -> {
            }
            R.id.action_filter_default -> {
            }
        }//                adapter.sort((o1, o2) -> o1.getMinPrice().compareTo(o2.getMinPrice()));
        //                System.out.println(adapter.getItem(0).getName());
        //                System.out.println(adapter.getItem(0).getName());
        //                initList(original);
        return super.onOptionsItemSelected(item)
    }

    private fun initList(data: List<Pikobject>) {
        adapter?.addAll(data)
    }

    override fun onRefresh() {
        loadPlans()
    }
}
