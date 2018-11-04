package com.benbourahla.foodfacts.producthistory

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.benbourahla.foodfacts.R
import com.benbourahla.foodfacts.model.ProductInformation

class ProductHistoryAdapter(val screen: ProductHistoryScreen) : RecyclerView.Adapter<ProductHistoryAdapter.HistoryViewHolder>() {

    val products: MutableList<ProductInformation> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.product_history_line_layout,parent,false)
        return HistoryViewHolder(v)
    }

    override fun getItemCount(): Int {
        return products.size
    }

    fun add(item:ProductInformation, position:Int) {
        products.add(position, item)
        notifyItemInserted(position)
    }

    fun addAll(productList: List<ProductInformation>) {
        products.clear()
        products.addAll(productList)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        val product = products[position]
        holder.productName.text = product.product?.productName
        screen.displayProductPicture(product.product?.imageFrontSmallUrl, holder.productPicture)
        holder.productLayout.setOnClickListener {
            screen?.displayProductDetails(product)
        }
    }

    class HistoryViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val productLayout = itemView.findViewById<LinearLayout>(R.id.product_history_line_layout)
        val productName = itemView.findViewById<TextView>(R.id.product_history_name)
        val productPicture = itemView.findViewById<ImageView>(R.id.product_history_img)
    }
}