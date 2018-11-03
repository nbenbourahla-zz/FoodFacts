package com.benbourahla.foodfacts.productdetails

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.*
import com.benbourahla.foodfacts.BottomBarActivity
import com.benbourahla.foodfacts.R
import com.benbourahla.foodfacts.database.CacheModule
import com.benbourahla.foodfacts.model.ProductInformation
import com.benbourahla.foodfacts.searchproduct.DaggerSearchProductComponent
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.notification_template_lines_media.view.*
import javax.inject.Inject

class ProductDetailsActivity: AppCompatActivity(), ProductDetailsScreen {


    val productPicture by lazy { findViewById<ImageView>(R.id.product_picture) }
    val productTitle by lazy { findViewById<TextView>(R.id.product_name) }
    val productBrand by lazy { findViewById<TextView>(R.id.product_brand) }
    val productQuantity by lazy { findViewById<TextView>(R.id.product_quantity) }
    val productEnergy by lazy { findViewById<TextView>(R.id.product_energy) }
    val productIngredients by lazy { findViewById<LinearLayout>(R.id.product_ingredients) }

    private val productInformation by lazy { intent?.getParcelableExtra<ProductInformation>("product_information_extra") }

    @Inject
    lateinit var presenter: ProductDetailsPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_product_details)

        DaggerProductDetailsComponent.builder()
                .cacheModule(CacheModule(this)).build().
                        inject(this)

        presenter.bind(this)
    }

    override fun onStart() {
        super.onStart()

        presenter.onScreenStarted(productInformation)
    }

    override fun displayProductPicture(imgUrl: String) {
        Glide.with(this).load(imgUrl).into(productPicture)
    }

    override fun displayProductTitle(productName: String) {
        productTitle.text = resources.getString(R.string.product_name, productName)
    }

    override fun displayProductBrand(brand: String) {
        productBrand.text = resources.getString(R.string.product_brand, brand)
    }

    override fun displayProductQuantity(quantity: String) {
        productQuantity.text = resources.getString(R.string.product_quantity, quantity)
    }

    override fun displayProductEnergie(energyValue: String?) {
        productEnergy.text = resources.getString(R.string.product_energy, energyValue)
    }

    override fun displayProductIngredientsTitle() {
        val ingredientTitleText = layoutInflater.inflate(R.layout.ingredient_layout, null) as TextView
        ingredientTitleText.text = resources.getString(R.string.product_ingredients_title)
        productIngredients.addView(ingredientTitleText)
    }

    override fun displayProductIngredient(name: String) {
        val ingredientText = layoutInflater.inflate(R.layout.ingredient_layout, null) as TextView
        ingredientText.text = resources.getString(R.string.product_ingredients_name, name)
        productIngredients.addView(ingredientText)
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