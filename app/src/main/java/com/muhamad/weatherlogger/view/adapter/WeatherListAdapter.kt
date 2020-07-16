package com.muhamad.weatherlogger.view.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.muhamad.weatherlogger.BR
import com.muhamad.weatherlogger.databinding.ItemWeatherBinding
import com.muhamad.weatherlogger.interfaces.OnItemClickListener
import com.muhamad.weatherlogger.model.WeatherModel
import com.muhamad.weatherlogger.view.utils.Constants
import kotlinx.android.synthetic.main.item_weather.view.*

class WeatherListAdapter(private val onItemClickListener: OnItemClickListener) :
    RecyclerView.Adapter<WeatherListAdapter.ItemViewHolder>() {

    var dataList: List<WeatherModel?>? = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val dataBinding = ItemWeatherBinding.inflate(inflater, parent, false)
        return ItemViewHolder(dataBinding, onItemClickListener)
    }

    override fun getItemCount(): Int = dataList!!.size

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        dataList?.get(position)?.let { holder.bindView(it) }
    }

    fun updateRepoList(list: List<WeatherModel?>?) {
        this.dataList = list
        notifyDataSetChanged()
    }

    class ItemViewHolder(
        private val dataBinding: ViewDataBinding,
        private val onItemClickListener: OnItemClickListener?
    ) :
        RecyclerView.ViewHolder(dataBinding.root) {

        @SuppressLint("SetTextI18n")
        fun bindView(item: WeatherModel) {
            dataBinding.setVariable(BR.model, item)
            dataBinding.executePendingBindings()

            itemView.tv_date.text = "last update: " + Constants.getDate(item.getDt()*1000)

            itemView.ib_more.setOnClickListener {
                onItemClickListener?.onMoreClick(item, itemView.ib_more)
            }

            itemView.setOnClickListener {
                onItemClickListener?.onDetailsClick(item)
            }
        }
    }

}