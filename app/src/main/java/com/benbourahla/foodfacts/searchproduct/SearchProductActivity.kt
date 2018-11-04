package com.benbourahla.foodfacts.searchproduct

import android.content.Intent
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.design.widget.Snackbar
import android.support.design.widget.TextInputLayout
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toolbar
import com.benbourahla.foodfacts.BottomBarActivity
import com.benbourahla.foodfacts.PRODUCT_INFORMATION_EXTRA
import com.benbourahla.foodfacts.R
import com.benbourahla.foodfacts.database.CacheModule
import com.benbourahla.foodfacts.model.ProductInformation
import com.benbourahla.foodfacts.productdetails.ProductDetailsActivity
import kotlinx.android.synthetic.main.notification_template_lines_media.view.*
import javax.inject.Inject

class SearchProductActivity: BottomBarActivity(), SearchProductScreen {

    val containerLayout by lazy { findViewById<ConstraintLayout>(R.id.search_product_container) }
    val codeBarEditText by lazy { findViewById<EditText>(R.id.code_bar_edit) }
    val searchButton by lazy { findViewById<Button>(R.id.search_button) }
    val textInputLayout by lazy { findViewById<TextInputLayout>(R.id.code_bar_input_layout) }

    @Inject
    lateinit var presenter: SearchProductPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_search_scan_layout)

        DaggerSearchProductComponent.builder()
                .cacheModule(CacheModule(this)).build().
                inject(this)

        presenter.bind(this)
    }

    override fun onStart() {
        super.onStart()

        searchButton.setOnClickListener {
            presenter.onSearchButtonClicked(codeBarEditText.text.toString())
        }
    }

    override fun displayInputError() {
        textInputLayout.error = getString(R.string.bar_code_text_error)
    }

    override fun displayError(error: Throwable?) {
        Snackbar.make(containerLayout, getString(R.string.search_product_error), Snackbar.LENGTH_LONG).show()
    }

    override fun goToNextScreen(it: ProductInformation?) {
        val intent = Intent(this, ProductDetailsActivity::class.java)
        intent.putExtra(PRODUCT_INFORMATION_EXTRA, it)
        startActivity(intent)
    }

    override fun onDestroy() {
        presenter.unbind()
        super.onDestroy()
    }

}