package com.alaa.alaagallo.view.features.pairs.pairType

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.PointF
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.cardview.widget.CardView
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.davemorrissey.labs.subscaleview.ImageSource
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView
import com.alaa.alaagallo.R
import com.alaa.alaagallo.base.helpers.extensions.handleImages
import com.alaa.alaagallo.base.helpers.extensions.openActivity
import com.alaa.alaagallo.model.sub_categories.SubCategoriesModelResponse
import com.alaa.alaagallo.view.features.pairs.PairsActivity
import com.alaa.alaagallo.view.features.pairs.PairsViewModel
import com.alaa.alaagallo.view.features.pairs.SelectedPairType
import com.alaa.alaagallo.view.features.pairs.pairType.brand.PairBrandActivity
import com.alaa.alaagallo.view.features.pairs.pairType.interaction.SearchInteraction
import com.alaa.alaagallo.view.features.pairs.pairType.model.PairModelActivity
import com.alaa.alaagallo.view.home.search_comp.SearchAdapter
import com.alaa.alaagallo.view.home.search_comp.newsearchresponse.SearchRespoooo
import com.squareup.picasso.Picasso
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element

@Suppress("DEPRECATION")
class PairTypeActivity : ComponentActivity(), SearchInteraction {

    //    private val chooseTypeTV: TextView get() = findViewById(R.id.tv_choose_type)
    private val chooseBrandTV: TextView get() = findViewById(R.id.tv_choose_brand)
    private val chooseModelTV: TextView get() = findViewById(R.id.tv_choose_model)
    private val txtNumber: TextView get() = findViewById(R.id.txtNumber)
    private val pairName: TextView get() = findViewById(R.id.pair_name)
    private val txtNoData: TextView get() = findViewById(R.id.txtNoResult)
    private val imgNoData: ImageView get() = findViewById(R.id.imgNoResult)
    private val txtCardBrand: TextView get() = findViewById(R.id.txt_card_brand)
    private val txtCardModel: TextView get() = findViewById(R.id.txt_card_model)
    private val back: ImageView get() = findViewById(R.id.iv_back)
    private val imgCardBrand: ImageView get() = findViewById(R.id.img_card_brand)
    private val imgCardModel: ImageView get() = findViewById(R.id.img_card_model)
    private val chooseBrand: ConstraintLayout get() = findViewById(R.id.brand_layout)
    private val chooseModel: ConstraintLayout get() = findViewById(R.id.model_layout)
    private val cardBrand: CardView get() = findViewById(R.id.cardBrand)
    private val cardModel: CardView get() = findViewById(R.id.cardModel)

    //    private val typeChoose: ConstraintLayout get() = findViewById(R.id.type_layout)
    //private val cardInfo: CardView get() = findViewById(R.id.pair_result_cd_plus_info)
    //private val brandInfo: TextView get() = findViewById(R.id.pair_result_tv_brand_info)
    //private val modelInfo: TextView get() = findViewById(R.id.pair_result_tv_model_info)
    private val brandImage: ImageView get() = findViewById(R.id.img_brand)
    private val modelImage: ImageView get() = findViewById(R.id.img_model)
    private val arrowBrand: ImageView get() = findViewById(R.id.arrow_brand)
    private val btn_search: Button get() = findViewById(R.id.btn_search)
    private val arrowModel: ImageView get() = findViewById(R.id.arrow_model)
    private val pairResultRecyclerView: RecyclerView get() = findViewById(R.id.pair_result_recycler_view)
    lateinit var viewModel: PairsViewModel
    private val loading: ProgressBar get() = findViewById(R.id.loading)
    var modelId: String = ""
    var modelName: String = ""

    // ماركة
    var brandId: String = ""
    var tempBrandId: String = ""
    var brandName: String = ""
    var brandImageUrl: String = ""

    // الصنف
    var typeId: String = ""
    var typeName: String = ""
    var token: String = ""
    var modelImageUrl: String = ""
    private val pairResultAdapter = SearchAdapter(this)
    private val pairResultList = arrayListOf<SubCategoriesModelResponse>()
    private val allDataList =
        mutableListOf<SearchRespoooo>() // Replace DataType with your actual data type
    private var currentPageIndex = 0
    private val pageSize = 20
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_pair_type)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val v: Int = intent.getIntExtra("pairs", 0)
        if (v == 1)
            clearTextViews()

        viewModel = ViewModelProvider(this)[PairsViewModel::class.java]
        val sharedPreferences = getSharedPreferences("your_pref_name", Context.MODE_PRIVATE)
        modelId = sharedPreferences.getString("mod_id", "").toString()
        modelName = sharedPreferences.getString("mod_name_search", "").toString()
        brandId = sharedPreferences.getString("brand_id", "").toString()
        tempBrandId = sharedPreferences.getString("temp_brand_id", "").toString()
        modelImageUrl = sharedPreferences.getString("mod_image", "").toString()
        brandName = sharedPreferences.getString("type_name_search", "").toString()
        brandImageUrl = sharedPreferences.getString("brand_image", "").toString()
        // Retrieve data from the intent
        typeId = sharedPreferences.getString("idType", "").toString()
        typeName = sharedPreferences.getString("nameType", "").toString()

        token = sharedPreferences.getString("token", null).toString()
        val position = intent.getIntExtra("position", -1)

        initView()

        initTextViews()
        loading.visibility = View.GONE

        btn_search.setOnClickListener {
            if (modelId.isNotEmpty() && brandId.isNotEmpty() && typeId.isNotEmpty() && modelName.isNotEmpty() && brandName.isNotEmpty()) {
                search(
                    "Bearer $token",
                    brandId, modelId, typeId
                )
            } else {
                Toast.makeText(this, "من فضلك تأكد من جميع البيانات المطلوبة", Toast.LENGTH_LONG)
                    .show()
            }
        }


    }


    @SuppressLint("SetJavaScriptEnabled")
    private fun initView() {
        pairName.text = typeName
        pairResultRecyclerView.layoutManager = GridLayoutManager(this, 2)
        pairResultRecyclerView.adapter = pairResultAdapter
        pairResultRecyclerView.setItemViewCacheSize(20)
        val composeView = findViewById<ComposeView>(R.id.pair_result_compose_view)

        viewModel.data.observe(this) {
            if (it.isNotEmpty()) {
                txtNumber.visibility = View.VISIBLE
                txtNumber.text = it.size.toString()
                composeView.apply {
                    setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
                    setContent {
                        val document: Document? = it[0].description?.let { it1 -> Jsoup.parse(it1) }
                        val imgElement = document?.select("img")?.first()
                        val imgUrl = imgElement?.attr("src")
                        val text =
                            document?.text().takeIf { s -> s?.isNotEmpty() ?: false } ?: ""
                        MaterialTheme {
                            Column(modifier = Modifier.fillMaxSize()) {
                                LazyVerticalGrid(
                                    verticalArrangement = Arrangement.spacedBy(12.dp),
                                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                                    contentPadding = PaddingValues(bottom = 20.dp),
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .height(if (text.isNotEmpty() || imgUrl != null) ((((it.size + 1) / 2) * 190) + 314).dp else ((((it.size + 1) / 2) * 190) + 100).dp),
                                    columns = GridCells.Fixed(2)
                                ) {
                                    if (it[0].description != null) {
                                        if (text.isNotEmpty() || imgUrl != null) {
                                            val h1: Element? = document!!.selectFirst("h1")
                                            val blockquote: Element? =
                                                document.selectFirst("blockquote")

                                            if (blockquote != null && h1 != null) {
                                                h1.after(blockquote.outerHtml())  // Move <blockquote> outside <h1>
                                                blockquote.remove()  // Remove from inside <h1>
                                            }
                                            val cleanedHtml = document.html()
                                            item(span = { GridItemSpan(2) }) {
                                                Box(
                                                    modifier = Modifier
                                                        .fillMaxWidth()
                                                        .height(200.dp)
                                                        .border(
                                                            width = 1.3.dp,
                                                            color = Color(0xffD9D9D9),
                                                            shape = RoundedCornerShape(6.dp)
                                                        )
                                                        .clip(
                                                            RoundedCornerShape(6.dp)
                                                        )
                                                ) {
                                                    AndroidView(
                                                        modifier = Modifier.fillMaxSize(),
                                                        factory = { c ->
                                                            WebView(c).apply {
                                                                // Configure WebView settings
                                                                settings.javaScriptEnabled = true
                                                                settings.useWideViewPort = true
                                                                settings.loadWithOverviewMode = true
                                                                settings.domStorageEnabled =
                                                                    true // Enable DOM storage if needed
                                                                settings.loadsImagesAutomatically =
                                                                    true
                                                                settings.layoutAlgorithm =
                                                                    WebSettings.LayoutAlgorithm.TEXT_AUTOSIZING
                                                                settings.builtInZoomControls = false
                                                                settings.displayZoomControls = false
                                                                webViewClient =
                                                                    WebViewClient() // Ensures all links are opened within the WebView
                                                                loadDataWithBaseURL(
                                                                    null,
                                                                    cleanedHtml,
                                                                    "text/html",
                                                                    "UTF-8",
                                                                    null
                                                                )
                                                            }
                                                        })
                                                    Box(
                                                        modifier = Modifier
                                                            .fillMaxSize()
                                                            .clickable {
                                                                onClickShowImage(cleanedHtml)
                                                            }
                                                    )
                                                }
                                            }
                                        }
                                    }
                                    items(it) { item ->
                                        ItemSearch(item) { url ->
                                            onClickImage(url)
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        viewModel.showNoData.observe(this) {
            if (it) {
                txtNoData.visibility = View.VISIBLE
                imgNoData.visibility = View.VISIBLE
            } else {
                txtNoData.visibility = View.GONE
                imgNoData.visibility = View.GONE
            }
        }
        viewModel.showLoading.observe(this) {
            if (it) {
                loading.visibility = View.VISIBLE
                txtNumber.visibility = View.GONE
            } else {
                loading.visibility = View.GONE
            }
        }

        viewModel.error.observe(this) {
            if (it.isNotEmpty())
                Toast.makeText(this, it, Toast.LENGTH_LONG).show()
        }
//        Handle Brand Image
        handleImages(SelectedPairType.brandImage, brandImage, arrowBrand)

        //Handle Model Image
        handleImages(SelectedPairType.modelImage, modelImage, arrowModel)

        back.setOnClickListener {
            openActivity(this, PairsActivity::class.java)
            finishAffinity()
        }

        chooseBrand.setOnClickListener {
            getSharedPreferences("your_pref_name", Context.MODE_PRIVATE)
                .edit()
                .putString("temp_brand_id", brandId)
                .apply()
            val intent = Intent(this, PairBrandActivity::class.java)
            intent.putExtra("paa", "1")
            startActivity(intent)
            finish()
//            openActivity(this, PairBrandActivity::class.java)
        }

        chooseModel.setOnClickListener {
            if (brandName.isNotEmpty()) {
                getSharedPreferences("your_pref_name", Context.MODE_PRIVATE)
                    .edit()
                    .putString("temp_brand_id", brandId)
                    .apply()
                val intent = Intent(this, PairModelActivity::class.java)
                intent.putExtra("modelBrandd", brandId)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(
                    this,
                    "من فضلك تأكد من جميع البيانات المطلوبة",
                    Toast.LENGTH_LONG
                )
                    .show()
            }
        }
    }

    private fun search(token: String, brand_id: String, mod_id: String, type_id: String) {
        if (brandImageUrl.isNotEmpty() && modelImageUrl.isNotEmpty()) {
            cardBrand.visibility = View.VISIBLE
            cardModel.visibility = View.VISIBLE
            Picasso.get().load(brandImageUrl).into(imgCardBrand)
            Picasso.get().load(modelImageUrl).into(imgCardModel)
            txtCardBrand.text = brandName
            txtCardModel.text = modelName
        }
        viewModel.searchInCompitabilty(
            token, brand_id, mod_id, type_id
        )

    }


    private fun initTextViews() {

        if (brandName.isEmpty()) {
            chooseBrandTV.text = "اختر الماركة"
        } else {
            chooseBrandTV.text = brandName
        }

        if (modelName.isEmpty() || (tempBrandId.isNotEmpty() && tempBrandId != brandId)) {
            getSharedPreferences("your_pref_name", Context.MODE_PRIVATE)
                .edit()
                .putString("mod_name_search", "")
                .putString("mod_id", "")
                .putString("mod_image", "")
                .apply()
            chooseModelTV.text = getString(R.string.choose_model)
            modelName = ""
//            modelId = ""
        } else {
            chooseModelTV.text = modelName
        }
    }

    private fun clearTextViews() {
        typeName = ""
        brandName = ""
        modelName = ""
//        chooseTypeTV.text = "اختر الصنف"
        chooseBrandTV.text = "اختر الماركة"
        chooseModelTV.text = getString(R.string.choose_model)
        val sharedPrefs = getSharedPreferences("your_pref_name", Context.MODE_PRIVATE).edit()

        sharedPrefs.putString("type_name", "")
        sharedPrefs.putString("mod_name_search", "")
        sharedPrefs.putString("type_name_search", "")
        sharedPrefs.putString("mod_id", "")
        sharedPrefs.putString("brand_id", "")
        sharedPrefs.apply()

    }

    @SuppressLint("InflateParams", "SetJavaScriptEnabled")
    override fun onClickShowImage(url: String) {
        val view = layoutInflater.inflate(R.layout.layout_dialog_image, null)
        val image = view.findViewById<ImageView>(R.id.imageDialog)
        val webView: WebView = view.findViewById(R.id.myWebView)
        webView.settings.javaScriptEnabled = true
        webView.settings.builtInZoomControls = true
        webView.settings.displayZoomControls = false
        webView.settings.useWideViewPort = true
        webView.settings.loadWithOverviewMode = true
        webView.settings.domStorageEnabled = true // Enable DOM storage if needed
        webView.settings.loadsImagesAutomatically = true
        webView.settings.layoutAlgorithm = WebSettings.LayoutAlgorithm.TEXT_AUTOSIZING
        // Ensure WebView opens links within the app
        webView.webViewClient = WebViewClient()
//        val document = Jsoup.parse(url)
//        val h1: Element? = document.selectFirst("h1")
//        val blockquote: Element? = document.selectFirst("blockquote")
//
//        if (blockquote != null && h1 != null) {
//            h1.after(blockquote.outerHtml())  // Move <blockquote> outside <h1>
//            blockquote.remove()  // Remove from inside <h1>
//        }
//        val cleanedHtml = document.html()
        webView.loadDataWithBaseURL(null, url, "text/html", "UTF-8", null)
        Dialog(this).apply {
            setContentView(view)
            window?.setBackgroundDrawableResource(R.drawable.dialog_shape)
            show()
        }
    }

    @SuppressLint("InflateParams")
    override fun onClickImage(url: String) {
        val view = layoutInflater.inflate(R.layout.layout_dialog_photo_image, null)
        val image = view.findViewById<SubsamplingScaleImageView>(R.id.photoDialog)
//        image.setImage(ImageSource.uri(url))
//        Picasso.get()
//            .load(url)
//            .into(image)
        Picasso.get().load(url).into(object : com.squareup.picasso.Target {
            override fun onBitmapLoaded(bitmap: Bitmap, from: Picasso.LoadedFrom?) {
                // Set the bitmap to Subsampling Scale Image View
                image.setImage(ImageSource.bitmap(bitmap))
                image.setMinimumScaleType(SubsamplingScaleImageView.SCALE_TYPE_CENTER_INSIDE)
                image.minScale = 1f
                image.maxScale = 10f
                image.setScaleAndCenter(1f, PointF(bitmap.width / 2f, bitmap.height / 2f))  // تعيين الزوم عند 1 والنقطة في المنتصف
//                image.setScaleAndCenter(
//                    1f, // مستوى الزوم
//                    PointF(bitmap.width / 2f, bitmap.height / 2f) // تحديد المركز
//                )
            }

            override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {
                // Handle error
            }

            override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
                // Optional: Add a placeholder if needed
            }
        })
        Dialog(this).apply {
            setContentView(view)
            window?.setBackgroundDrawableResource(R.drawable.bg_photo_view)
            window?.setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT
            )
            show()
        }
    }
}

@Composable
private fun ItemSearch(
    item: SearchRespoooo,
    onClickImage: (url: String) -> Unit,
) {
    Box(
        modifier = Modifier
            .border(
                width = 1.3.dp,
                color = Color(0xffD9D9D9),
                shape = RoundedCornerShape(topStart = 6.dp, topEnd = 6.dp)
            )
            .clip(
                RoundedCornerShape(topStart = 6.dp, topEnd = 6.dp)
            )
    ) {
        Column {
            Row(modifier = Modifier.fillMaxWidth()) {
                Text(
                    item.compatibilities_brand.uppercase(),
                    color = Color(0xff111514),
                    fontSize = 11.sp,
                    modifier = Modifier.padding(start = 4.dp, top = 2.dp)
                )
                Spacer(Modifier.weight(1f))
                Box(
                    modifier = Modifier
                        .size(height = 16.dp, width = 20.dp)
                        .clip(
                            RoundedCornerShape(topEnd = 6.dp, bottomStart = 6.dp)
                        )
                        .background(Color.Black)
                )
            }
            AsyncImage(
                modifier = Modifier
                    .size(width = 156.dp, height = 110.dp)
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onTap = {
                                onClickImage(item.compatibilities_mod_image)
                            }
                        )
                    },
                model = ImageRequest.Builder(LocalContext.current)
                    .data(item.compatibilities_mod_image)
                    .placeholder(R.drawable.img)
                    .build(),
                contentDescription = "",
                contentScale = ContentScale.Fit
            )
            Spacer(Modifier.height(2.dp))
            Box(
                Modifier
                    .fillMaxWidth()
                    .background(Color.Black)
                    .padding(8.dp)) {
                Column {
                    Text(
                        item.compatibilities_mod,
                        color = Color.White,
                        fontSize = 11.sp,
                        modifier = Modifier.fillMaxWidth(),
                        fontWeight = FontWeight.Bold
                    )
//                    if (item.description != null) {
//                        val document: Document = Jsoup.parse(item.description)
//                        val imgElement = document.select("img").first()
//                        val imgUrl = imgElement?.attr("src")
//                        val text =
//                            document.text().takeIf { it.isNotEmpty() } ?: ""
//                        if (text.isNotEmpty()) {
//                            Text(
//                                text,
//                                color = Color.White,
//                                fontSize = 11.sp,
//                                maxLines = 2,
//                                overflow = TextOverflow.Ellipsis,
//                                modifier = Modifier.fillMaxWidth(),
//                            )
//                        }
//                        if (imgUrl != null) {
//                            Text(
//                                "عرض المزيد",
//                                color = Color(0xff6292CA),
//                                fontSize = 11.sp,
//                                modifier = Modifier.fillMaxWidth().pointerInput(Unit) {
//                                    detectTapGestures(
//                                        onTap = {
//                                            onClickViewMore(item.description)
//                                        }
//                                    )
//                                },
//                            )
//                        }
//                    }
                }
            }
        }
    }
}