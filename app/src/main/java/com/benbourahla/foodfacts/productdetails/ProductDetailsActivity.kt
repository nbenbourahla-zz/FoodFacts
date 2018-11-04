package com.benbourahla.foodfacts.productdetails

import android.os.Bundle
import android.support.v4.content.res.ResourcesCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.widget.*
import com.benbourahla.foodfacts.PRODUCT_INFORMATION_EXTRA
import com.benbourahla.foodfacts.R
import com.benbourahla.foodfacts.database.CacheModule
import com.benbourahla.foodfacts.model.ProductInformation
import com.bumptech.glide.Glide
import javax.inject.Inject

class ProductDetailsActivity: AppCompatActivity(), ProductDetailsScreen {

    val toolbar by lazy { findViewById<Toolbar>(R.id.toolbar) }
    val productPicture by lazy { findViewById<ImageView>(R.id.product_picture) }
    val productTitle by lazy { findViewById<TextView>(R.id.product_name) }
    val productBrand by lazy { findViewById<TextView>(R.id.product_brand) }
    val productQuantity by lazy { findViewById<TextView>(R.id.product_quantity) }
    val productEnergy by lazy { findViewById<TextView>(R.id.product_energy) }
    val productIngredients by lazy { findViewById<LinearLayout>(R.id.product_ingredients) }

    private val productInformation by lazy { intent?.getParcelableExtra<ProductInformation>(PRODUCT_INFORMATION_EXTRA) }

    @Inject
    lateinit var presenter: ProductDetailsPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_product_details)

        setSupportActionBar(toolbar)

        setupActionbar()

        DaggerProductDetailsComponent.builder()
                .cacheModule(CacheModule(this)).build().
                        inject(this)

        presenter.bind(this)
    }

    private fun setupActionbar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        toolbar?.apply {
            navigationIcon = ResourcesCompat.getDrawable(resources, R.drawable.ic_back_arrow_white, null)
        }
        toolbar?.setNavigationOnClickListener { finish() }
    }

    override fun onStart() {
        super.onStart()

        presenter.onScreenStarted(productInformation)
    }

    override fun displayProductPicture(imgUrl: String) {
        Glide.with(this).load(imgUrl).into(productPicture)
    }

    override fun displayProductTitle(productName: String) {
        supportActionBar?.title = productName
        productTitle.text = resources.getString(R.string.product_name, productName)
    }

    override fun displayProductBrand(brand: String) {
        productBrand.text = resources.getString(R.string.product_brand, brand)
    }

    override fun displayProductQuantity(quantity: String) {
        productQuantity.text = resources.getString(R.string.product_quantity, quantity)
    }

    override fun displayProductEnergy(energyValue: String?) {
        productEnergy.text = resources.getString(R.string.product_energy, energyValue)
    }

    override fun displayProductIngredientsTitle() {
        val ingredientTitleText = layoutInflater.inflate(R.layout.ingredient_layout, null) as TextView
        ingredientTitleText.text = resources.getString(R.string.product_ingredients_title)
        productIngredients.addView(ingredientTitleText)
    }

    override fun displayProductIngredient(name: String) {
        if (name.isNotEmpty()) {
            val ingredientText = layoutInflater.inflate(R.layout.ingredient_layout, null) as TextView
            ingredientText.text = resources.getString(R.string.product_ingredients_name, name)
            productIngredients.addView(ingredientText)
        }
    }

    override fun onStop() {
        presenter.onScreenDestroyed()
        super.onStop()
    }

    override fun onDestroy() {
        presenter.unbind()
        super.onDestroy()
    }

}