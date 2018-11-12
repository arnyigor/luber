package com.arny.pik.presenter.`object`

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.OrientationHelper
import com.arny.pik.R
import com.arny.pik.adapter.KorpusesAdapter
import com.arny.pik.data.Local
import com.arny.pik.data.api.API
import com.arny.pik.data.models.Korpus
import com.arny.pik.utils.ToastMaker
import com.arny.pik.utils.Utility
import kotlinx.android.synthetic.main.activity_object_detail.*
import pw.aristos.libs.adapters.SimpleAbstractAdapter
import java.util.*

class ObjectDetailActivity : AppCompatActivity(), SwipeRefreshLayout.OnRefreshListener {
    private var adapter: KorpusesAdapter? = null
    private var url: String? = null
    private var title: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_object_detail)
        initUI()
        if (intent != null) {
            val intent = intent
            url = intent.getStringExtra("url")
            title = intent.getStringExtra("title")
            setTitle(title)
            loadObject()
        }
    }

    @SuppressLint("CheckResult")
    private fun loadObject() {
        if (url != null) {
            srl_genplan_list.isRefreshing = true
            Utility.mainThreadObservable(API.loadGenplan(url!!)
                    .map {genPlans-> genPlans.getOrNull(0)?.data?.korpuses?: arrayListOf() }
                    .map {korpuses->
                        val list = ArrayList<Korpus>()
                        for (korpus in korpuses) {
                            if (korpus.type == "flats") {
                                list.add(korpus)
                            }
                        }
                        list
                    })
                    .subscribe({ korpuses ->
                        srl_genplan_list.isRefreshing = false
                        setAdapterData(korpuses)
                    }, { throwable ->
                        srl_genplan_list.isRefreshing = false
                        ToastMaker.toastError(this@ObjectDetailActivity, throwable.message)
                    })
        }
    }

    private fun initUI() {
        srl_genplan_list.setOnRefreshListener(this)
        rv_object_genplans.layoutManager = GridLayoutManager(this, 1, OrientationHelper.VERTICAL, false)
        rv_object_genplans.itemAnimator = DefaultItemAnimator()
        adapter = KorpusesAdapter()
        adapter?.setViewHolderListener(object : SimpleAbstractAdapter.OnViewHolderListener<Korpus> {
            override fun onItemClick(position: Int, item: Korpus) {
                Local.viewKorpus(this@ObjectDetailActivity, item, url, title)
            }
        })
        rv_object_genplans.adapter = adapter
    }

    override fun onRefresh() {
        loadObject()
    }

    private fun setAdapterData(data: List<Korpus>) {
        adapter?.addAll(data)
    }
}
