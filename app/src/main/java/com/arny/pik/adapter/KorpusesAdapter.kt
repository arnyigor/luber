package com.arny.pik.adapter

import com.arny.pik.R
import com.arny.pik.data.models.Korpus
import com.arny.pik.utils.MathUtils
import com.arny.pik.utils.Utility
import kotlinx.android.synthetic.main.genplan_item.view.*
import pw.aristos.libs.adapters.SimpleAbstractAdapter

class KorpusesAdapter:SimpleAbstractAdapter<Korpus>(){
    override fun getLayout(): Int {
         return R.layout.genplan_item
    }

    override fun bindView(item: Korpus, viewHolder: VH) {
        viewHolder.itemView.apply {
            tv_title.text = item.title
            tv_dateOfMovingIn.text = String.format("Заселение:%s", if (Utility.empty(item.dateOfMovingIn)) "Нет данных" else item.dateOfMovingIn)
            tv_busy.text = String.format("занято:%s", if (Utility.empty(item.busy)) "-" else item.busy)
            tv_free.text = String.format("свободно:%s", if (Utility.empty(item.free)) "-" else item.free)
            val finishing = item.finishing
            tv_finishing.text = if (finishing != null) if (finishing == "1") "С отделкой" else "Без отделки" else "Отделка:?"
            tv_sold.text = String.format("Продано:%s", if (Utility.empty(item.sold)) "-" else item.sold)
            val price1 = getFlatPrice(java.lang.Double.parseDouble(if (item.minprice1 != null) item.minprice1 else "0") / 1000000)
            val price2 = getFlatPrice(java.lang.Double.parseDouble(if (item.minprice2 != null) item.minprice2 else "0") / 1000000)
            val price3 = getFlatPrice(java.lang.Double.parseDouble(if (item.minprice3 != null) item.minprice3 else "0") / 1000000)
            tv_minprice_1.text = String.format("1к:%s млн", price1.toString())
            tv_minprice_2.text = String.format("2к:%s млн", price2.toString())
            tv_minprice_3.text = String.format("3к:%s млн", price3.toString())
            setOnClickListener {
                listener?.onItemClick(viewHolder.adapterPosition,item)
            }
        }
    }

    private fun getFlatPrice(`val`: Double): Double {
        return MathUtils.round(`val`, 3)
    }

    override fun getDiffCallback(): DiffCallback<Korpus>? {
        return null
    }
}