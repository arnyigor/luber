package com.arny.pik.adapter

import com.arny.pik.R
import com.arny.pik.data.models.Pikobject
import com.arny.pik.utils.DateTimeUtils
import com.arny.pik.utils.MathUtils
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.pikobject_list_item.view.*
import pw.aristos.libs.adapters.SimpleAbstractAdapter

class ObjectsAdapter(private val objectsListener: ObjectsListener? = null) : SimpleAbstractAdapter<Pikobject>() {
    override fun getLayout(): Int {
        return R.layout.pikobject_list_item
    }

    interface ObjectsListener : SimpleAbstractAdapter.OnViewHolderListener<Pikobject> {
        fun openMap(position: Int, item: Pikobject)
    }

    override fun bindView(item: Pikobject, viewHolder: VH) {
        viewHolder.itemView.apply {
            val position = viewHolder.adapterPosition
            val name = item.name
            tv_title.text = name
            val nearSettlementDate = if (item.nearSettlementDate == null) -1 else item.nearSettlementDate
            val ts = nearSettlementDate?.toLong() ?: 0*1000
            val dateTime = if (nearSettlementDate == -1) "-" else DateTimeUtils.getDateTime(ts, "dd MMM yyyy")
            val s = "заселение:$dateTime"
            val minPrice = item.minPrice ?: 0
            val all = item.flatsAll ?: 0
            val free = item.flatsFree ?: 0
            val reserve = item.flatsReserved ?: 0
            val price = MathUtils.round(minPrice.toDouble() / 1000000, 3)
            tv_info.text = String.format("%s\nОт:%s млн. \n[Все:%d/свободно:%d/резерв:%d]", item.location, price.toString(), all, free, reserve)
            val img = item.img
            if (img != null) {
                val imageMap = img.main
                Glide.with(context.applicationContext)
                        .load(imageMap)
                        .into(iv_object_preview)
            }
            imgbtn_openmap.setOnClickListener {
                objectsListener?.openMap(position, item)
            }
            setOnClickListener {
                objectsListener?.onItemClick(position, item)
            }
        }
    }

    override fun getDiffCallback(): DiffCallback<Pikobject>? {
        return null
    }
}