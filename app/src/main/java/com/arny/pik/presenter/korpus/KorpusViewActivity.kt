package com.arny.pik.presenter.korpus

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.*
import com.arny.pik.R
import com.arny.pik.adapter.FlatDialog
import com.arny.pik.adapter.FlatsAdapter
import com.arny.pik.data.api.API
import com.arny.pik.data.Local
import com.arny.pik.data.models.KorpusSection
import com.arny.pik.presenter.base.BaseMvpActivity
import com.arny.pik.utils.ToastMaker
import com.arny.pik.utils.Utility
import com.arny.pik.utils.adapters.AbstractArrayAdapter

class KorpusViewActivity : BaseMvpActivity<KorpusViewContract.View, KorpusViewPresenter>(), KorpusViewContract.View, AdapterView.OnItemSelectedListener {

    override fun initPresenter(): KorpusViewPresenter {
        return KorpusViewPresenter()
    }

    private var spinSection: Spinner? = null
    private var sections: List<KorpusSection>? = null
    private var spinSectionsAdapter: SectionAdapter? = null
    private var progressDraw: ProgressBar? = null
    private var korpus: String? = null
    private var objectTitle: String? = null
    private var gridView: GridView? = null
    private var adapter: FlatsAdapter? = null
    private var tvInfo: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_korpus_view)
        initUI()
        val intent = intent
        if (intent != null) {
            val url = intent.getStringExtra("url")
            val id = intent.getStringExtra("id")
            objectTitle = intent.getStringExtra("object_title")
            korpus = intent.getStringExtra("korpus")
            fillUI(url, id)
        }
    }

    override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
        adapter!!.clear()
        adapter!!.addAll(Local.getSectionFlatsArray(sections!![position]))
        gridView!!.numColumns = sections!![position].maxFlatsOnFloor + 1
        title = objectTitle
        tvInfo!!.text = String.format("%s %s", korpus, sections!![position].name)
    }

    override fun onNothingSelected(parent: AdapterView<*>) {

    }

    private inner class SectionAdapter internal constructor(context: Context, textViewResourceId: Int) : AbstractArrayAdapter<KorpusSection>(context, textViewResourceId) {
        override fun getItemTitle(item: KorpusSection): String {
            return item.name?:""
        }
    }

    private fun initUI() {
        tvInfo = findViewById(R.id.tvInfo)
        progressDraw = findViewById(R.id.progress_draw)
        gridView = findViewById(R.id.gridSection)
        spinSection = findViewById(R.id.spin_section)
        spinSectionsAdapter = SectionAdapter(this, R.layout.section_spinner_item)
        spinSection!!.adapter = spinSectionsAdapter
        spinSection!!.onItemSelectedListener = this
    }

    @SuppressLint("CheckResult")
    private fun fillUI(url: String, id: String) {
        gridView!!.visibility = View.GONE
        tvInfo!!.visibility = View.GONE
        progressDraw!!.visibility = View.VISIBLE
        spinSection!!.visibility = View.GONE
        Utility.mainThreadObservable(API.getListkorpuses(url, id))
                .subscribe({ this.setUIByData(it) },
                        { throwable ->
                            progressDraw!!.visibility = View.GONE
                            spinSection!!.visibility = View.GONE
                            tvInfo!!.visibility = View.GONE
                            throwable.printStackTrace()
                            ToastMaker.toastError(this, "Ошибка:" + throwable.message)
                        })
    }

    private fun setUIByData(korpusSections: List<KorpusSection>) {
        gridView!!.visibility = View.VISIBLE
        tvInfo!!.visibility = View.VISIBLE
        this.sections = korpusSections
        title = objectTitle
        tvInfo!!.text = String.format("%s %s", korpus, sections!![0].name)
        spinSectionsAdapter!!.addAll(sections)
        adapter = FlatsAdapter(this, R.layout.flat_item)
        adapter!!.clear()
        adapter!!.addAll(Local.getSectionFlatsArray(sections!![0]))
        gridView!!.adapter = adapter
        gridView!!.numColumns = sections!![0].maxFlatsOnFloor + 1
        progressDraw!!.visibility = View.GONE
        spinSection!!.visibility = View.VISIBLE
        gridView!!.setOnItemClickListener { parent, view, position, id ->
            val item = adapter!!.getItem(position)
            if (item?.flat != null) {
                val flat = item.flat
                val status = flat?.status
                if (status != null) {
                    val url = status.url
                    if (url == "sold") {
                        ToastMaker.toastError(this@KorpusViewActivity, "Продана")
                        return@setOnItemClickListener
                    }
                    if (url != "unavailable") {
                        FlatDialog(this, item).show()
                    } else {
                        ToastMaker.toastError(this@KorpusViewActivity, "Нет данных")
                    }
                } else {
                    if (item.type == Local.GridItemType.floorNum) {
                        FlatDialog(this, item).show()
                        return@setOnItemClickListener
                    }
                    ToastMaker.toastError(this@KorpusViewActivity, "Нет данных")
                }
            }
        }

    }

}














