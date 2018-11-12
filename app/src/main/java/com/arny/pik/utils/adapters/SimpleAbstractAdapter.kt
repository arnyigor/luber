package pw.aristos.libs.adapters

import android.support.annotation.LayoutRes
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup


abstract class SimpleAbstractAdapter<T>(private var items: ArrayList<T> = arrayListOf()) : RecyclerView.Adapter<SimpleAbstractAdapter.VH>() {
    protected var listener: OnViewHolderListener<T>? = null
    protected abstract fun getLayout(): Int
    protected abstract fun bindView(item: T, viewHolder: VH)
    protected abstract fun getDiffCallback(): DiffCallback<T>?

    override fun onBindViewHolder(vh: VH, position: Int) {
        bindView(getItem(position), vh)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(parent, getLayout())
    }

    override fun getItemCount(): Int = items.size

    protected abstract class DiffCallback<T> : DiffUtil.Callback() {
        private val mOldItems = ArrayList<T>()
        private val mNewItems = ArrayList<T>()

        fun setItems(oldItems: List<T>, newItems: List<T>) {
            mOldItems.clear()
            mOldItems.addAll(oldItems)
            mNewItems.clear()
            mNewItems.addAll(newItems)
        }

        override fun getOldListSize(): Int {
            return mOldItems.size
        }

        override fun getNewListSize(): Int {
            return mNewItems.size
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return areItemsTheSame(
                    mOldItems[oldItemPosition],
                    mNewItems[newItemPosition]
            )
        }

        abstract fun areItemsTheSame(oldItem: T, newItem: T): Boolean

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return areContentsTheSame(
                    mOldItems[oldItemPosition],
                    mNewItems[newItemPosition]
            )
        }

        abstract fun areContentsTheSame(oldItem: T, newItem: T): Boolean
    }

    class VH(parent: ViewGroup, @LayoutRes layout: Int) : RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(layout, parent, false))

    interface OnViewHolderListener<T> {
        fun onItemClick(position: Int, item: T)
    }

    fun getItem(position: Int): T {
        return items[position]
    }

    fun getItems(): ArrayList<T> {
        return items
    }

    fun setViewHolderListener(listener: OnViewHolderListener<T>) {
        this.listener = listener
    }

    fun addAll(list: List<T>) {
        val diffCallback = getDiffCallback()
        if (diffCallback != null && !items.isEmpty()) {
            diffCallback.setItems(items, list)
            val diffResult = DiffUtil.calculateDiff(diffCallback)
            items.clear()
            items.addAll(list)
            diffResult.dispatchUpdatesTo(this)
        } else {
            items.addAll(list)
            notifyDataSetChanged()
        }
    }

    fun add(item: T) {
        items.add(item)
        notifyDataSetChanged()
    }

    fun add(position:Int, item: T) {
        items.add(position,item)
        notifyItemInserted(position)
    }

    fun remove(position: Int) {
        items.removeAt(position)
        notifyItemRemoved(position)
    }

    fun remove(item: T) {
        items.remove(item)
        notifyDataSetChanged()
    }

    fun clear(notify: Boolean=false) {
        items.clear()
        if (notify) {
            notifyDataSetChanged()
        }
    }
}