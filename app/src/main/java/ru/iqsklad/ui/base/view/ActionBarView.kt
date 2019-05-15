package ru.iqsklad.ui.base.view

import android.content.Context
import android.content.res.ColorStateList
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.PopupMenu
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.widget.ImageViewCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.material.textfield.TextInputEditText
import ru.iqsklad.R
import ru.iqsklad.utils.extensions.hide
import ru.iqsklad.utils.extensions.hideAsGone
import ru.iqsklad.utils.extensions.show
import ru.iqsklad.utils.extensions.updateTextWatcher

class ActionBarView : ConstraintLayout {

    private var actionClickListener: ActionBarClickListener? = null

    private lateinit var searchImageView: ImageView
    private lateinit var backImageView: ImageView
    private lateinit var titleTextView: TextView
    private lateinit var searchEditView: TextInputEditText
    private lateinit var logoImageView: ImageView
    private lateinit var statusImageView: ImageView
    private lateinit var moreImageView: ImageView
    private lateinit var searchCloseImageView: ImageView
    private lateinit var divider: View

    private var type: Int = DEFAULT_VALUE_TYPE
    private var theme: Int = THEME_LIGHT
    private var title: String? = null

    private var searchObservableField = MutableLiveData<String>()

    private companion object {
        const val DEFAULT_VALUE_TYPE = -1
        const val TYPE_SEARCH = 0
        const val TYPE_LOGO_WITH_BACK = 1
        const val TYPE_LOGO_WITH_BACK_TRANSPARENT = 2
        const val TYPE_LOGO_WITHOUT_BACK = 3
        const val TYPE_TEXT_WITH_BACK = 4

        const val THEME_LIGHT = 0
        const val THEME_DARK = 1
    }

    class ActionBarException(message: String?) : Exception(message)

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        LayoutInflater.from(context).inflate(R.layout.action_bar_view, this)
        initAttrs(attrs)
    }

    private fun initAttrs(attrs: AttributeSet?) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.ActionBarView)

        type = typedArray.getInt(R.styleable.ActionBarView_field_action_bar_type, DEFAULT_VALUE_TYPE)
        title = typedArray.getString(R.styleable.ActionBarView_field_action_bar_title)
        theme = typedArray.getInt(R.styleable.ActionBarView_field_action_bar_theme, THEME_LIGHT)

        if (type == DEFAULT_VALUE_TYPE) {
            throw ActionBarException("ActionBarView type is not specified")
        }

        typedArray.recycle()
    }

    override fun onFinishInflate() {
        super.onFinishInflate()

        bindViews()
        initClickListeners()
        initTextWatcher()
        updateActionBarType()
        setTheme()
    }

    private fun bindViews() {
        searchImageView = findViewById(R.id.search_image_view)
        backImageView = findViewById(R.id.back_image_view)
        titleTextView = findViewById(R.id.title_view)
        searchEditView = findViewById(R.id.search_edit_view)
        logoImageView = findViewById(R.id.logo_image_view)
        statusImageView = findViewById(R.id.status_image_view)
        moreImageView = findViewById(R.id.more_image_view)
        searchCloseImageView = findViewById(R.id.search_close_image_view)
        divider = findViewById(R.id.divider)
    }

    private fun initClickListeners() {
        searchImageView.setOnClickListener {
            statusImageView.hide()
            moreImageView.hide()
            titleTextView.hide()

            searchCloseImageView.show()
            searchEditView.show()
        }

        searchCloseImageView.setOnClickListener {
            searchEditView.text?.clear()
            searchEditView.clearFocus()
            searchEditView.hide()
            searchCloseImageView.hide()

            titleTextView.show()
            statusImageView.show()
            moreImageView.show()
        }

        backImageView.setOnClickListener {
            actionClickListener?.onBackClicked()
        }

        statusImageView.setOnClickListener {
            showStatusBottomFragment()
        }

        moreImageView.setOnClickListener {
            showPopupMenu(it)
        }
    }

    private fun initTextWatcher() {
        searchEditView.updateTextWatcher(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                searchObservableField.postValue(s.toString())
            }
        })
    }

    private fun showPopupMenu(view: View) = PopupMenu(view.context, view).run {
        menuInflater.inflate(R.menu.popup_menu, menu)
        setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.help -> actionClickListener?.onHelpClicked()
            }
            true
        }
        show()
    }

    private fun showStatusBottomFragment() {
        actionClickListener?.onStatusClicked()
    }

    private fun updateActionBarType() {
        when (type) {
            TYPE_SEARCH -> {
                backImageView.hide()
                logoImageView.hide()
                searchCloseImageView.hide()
                searchEditView.hide()

                titleTextView.text = title!!
            }
            TYPE_LOGO_WITH_BACK -> {
                hideSearchViews()
            }
            TYPE_LOGO_WITHOUT_BACK -> {
                hideSearchViews()

                backImageView.hide()
            }
            TYPE_LOGO_WITH_BACK_TRANSPARENT -> {
                hideSearchViews()

                divider.hide()
            }
            TYPE_TEXT_WITH_BACK -> {
                hideSearchViews()
                logoImageView.hide()

                titleTextView.text = title!!
            }
        }
    }

    private fun hideSearchViews() {
        searchImageView.hide()
        searchCloseImageView.hide()
        searchEditView.hide()
    }

    private fun setTheme() {
        when (theme) {
            //default theme
            THEME_LIGHT -> {
            }
            THEME_DARK -> {
                ImageViewCompat.setImageTintList(
                    backImageView,
                    ColorStateList.valueOf(ContextCompat.getColor(context, R.color.white))
                )
                ImageViewCompat.setImageTintList(
                    moreImageView,
                    ColorStateList.valueOf(ContextCompat.getColor(context, R.color.white))
                )
                logoImageView.setImageResource(R.mipmap.small_logo_dark_theme)
            }
        }
    }

    fun observeSearchText(): LiveData<String> {
        return searchObservableField
    }

    fun setActionClickListener(listener: ActionBarClickListener) {
        actionClickListener = listener
    }

    fun hideMoreButton() {
        moreImageView.hideAsGone()
    }

    interface ActionBarClickListener {

        fun onBackClicked()

        fun onStatusClicked()

        fun onHelpClicked()
    }
}