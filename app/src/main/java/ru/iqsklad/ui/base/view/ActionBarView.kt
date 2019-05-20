package ru.iqsklad.ui.base.view

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.PopupMenu
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.widget.ImageViewCompat
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.material.textfield.TextInputEditText
import ru.iqsklad.R
import ru.iqsklad.utils.extensions.hide
import ru.iqsklad.utils.extensions.hideAsGone
import ru.iqsklad.utils.extensions.show
import androidx.appcompat.view.menu.MenuBuilder
import androidx.appcompat.view.menu.MenuPopupHelper


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
    private var hint: String? = null
    private var showExit: Boolean = true

    private var searchModeOn = false

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
        hint = typedArray.getString(R.styleable.ActionBarView_field_action_bar_hint)
        showExit = typedArray.getBoolean(R.styleable.ActionBarView_field_action_bar_exit_visible, true)

        if (type == DEFAULT_VALUE_TYPE) {
            throw ActionBarException("ActionBarView type is not specified")
        }

        typedArray.recycle()
    }

    override fun onFinishInflate() {
        super.onFinishInflate()

        bindViews()
        initClickListeners()
        initSearchTextView()
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

            searchEditView.show()
            searchEditView.requestFocus()
            actionClickListener?.onSearchClicked()

            searchModeOn = true
        }

        searchCloseImageView.setOnClickListener {
            searchEditView.text?.clear()
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

    private fun initSearchTextView() {
        searchEditView.addTextChangedListener {
            if (it?.count() == 0) {
                searchCloseImageView.hideAsGone()
            } else {
                searchCloseImageView.show()
            }

            searchObservableField.postValue(it?.toString())
        }
    }

    private fun showPopupMenu(view: View) = PopupMenu(view.context, view).run {
        menuInflater.inflate(R.menu.popup_menu, menu)
        setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.help -> actionClickListener?.onHelpClicked()
                R.id.call -> actionClickListener?.onCallClicked()
                R.id.exit -> actionClickListener?.onExitClicked()
            }
            true
        }

        menu.getItem(2).isVisible = showExit

        val menuHelper = MenuPopupHelper(context, this.menu as MenuBuilder, view)
        menuHelper.setForceShowIcon(true)
        menuHelper.show()
    }

    private fun showStatusBottomFragment() {
        actionClickListener?.onStatusClicked()
    }

    private fun updateActionBarType() {
        when (type) {
            TYPE_SEARCH -> {
                backImageView.hide()
                logoImageView.hide()
                searchCloseImageView.hideAsGone()
                searchEditView.hide()

                searchEditView.hint = hint
                titleTextView.text = title
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

    fun isInSearchMode() = searchModeOn

    fun exitFromSearchMode() {
        searchEditView.text?.clear()
        searchEditView.clearFocus()
        searchEditView.hide()
        searchCloseImageView.hideAsGone()

        titleTextView.show()
        statusImageView.show()
        moreImageView.show()

        searchModeOn = false
    }

    interface ActionBarClickListener {

        fun onBackClicked()

        fun onStatusClicked()

        fun onHelpClicked()

        fun onCallClicked()

        fun onExitClicked()

        fun onSearchClicked()
    }
}