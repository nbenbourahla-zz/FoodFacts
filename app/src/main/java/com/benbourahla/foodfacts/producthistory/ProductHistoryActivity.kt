package com.benbourahla.foodfacts.producthistory

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.*
import com.benbourahla.foodfacts.BottomBarActivity
import com.benbourahla.foodfacts.PRODUCT_INFORMATION_EXTRA
import com.benbourahla.foodfacts.R
import com.benbourahla.foodfacts.database.CacheModule
import com.benbourahla.foodfacts.model.ProductInformation
import com.benbourahla.foodfacts.productdetails.DaggerProductDetailsComponent
import com.benbourahla.foodfacts.productdetails.ProductDetailsActivity
import com.bumptech.glide.Glide
import javax.inject.Inject

class ProductHistoryActivity: BottomBarActivity(), ProductHistoryScreen {

    val historyTitle by lazy { findViewById<TextView>(R.id.product_history_title) }
    val historyEmptyState by lazy { findViewById<TextView>(R.id.product_history_empty_state) }
    val historyRecyclerView by lazy { findViewById<RecyclerView>(R.id.product_history_list) }
    lateinit var adapter: ProductHistoryAdapter

    @Inject
    lateinit var presenter: ProductHistoryPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_product_history)

        setSupportActionBar(toolbar)

        DaggerProductHistoryComponent.builder()
                .cacheModule(CacheModule(this)).build().
                        inject(this)

        historyRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
        adapter = ProductHistoryAdapter(this)
        historyRecyclerView.adapter = adapter

        presenter.bind(this)
    }

    override fun onStart() {
        super.onStart()

        presenter.onScreenStarted()
    }

    override fun displayTitle() {
        historyTitle.text = getString(R.string.search_history_title)
    }

    override fun displayProductPicture(url: String?, productPicture: ImageView?) {
        productPicture?.let { Glide.with(this).load(url).into(it) }
    }

    override fun displayEmptyState() {
        historyEmptyState.visibility = View.VISIBLE
        historyEmptyState.text = getString(R.string.search_history_empty_state)
    }

    override fun displayProductDetails(product: ProductInformation) {
        val intent = Intent(this, ProductDetailsActivity::class.java)
        intent.putExtra(PRODUCT_INFORMATION_EXTRA, product)
        startActivity(intent)
    }

    override fun displayProductHistory(productHistoryList: List<ProductInformation>) {
        historyEmptyState.visibility = View.GONE
       adapter.addAll(productHistoryList)
    }

    override fun onDestroy() {
        presenter.unbind()
        super.onDestroy()
    }

}