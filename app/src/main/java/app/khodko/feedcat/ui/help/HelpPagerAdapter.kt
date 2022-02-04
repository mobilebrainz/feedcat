package app.khodko.feedcat.ui.help

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.text.HtmlCompat
import androidx.viewpager.widget.PagerAdapter
import app.khodko.feedcat.R

class HelpPagerAdapter(private val list: List<String>) : PagerAdapter() {

    override fun getCount() = list.size

    override fun isViewFromObject(view: View, `object`: Any) = view === `object` as View

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view = LayoutInflater.from(container.context)
            .inflate(R.layout.page_help_layout, container, false)

        val helpView: TextView = view.findViewById(R.id.helpView)
        val footerView: TextView = view.findViewById(R.id.footerView)

        helpView.text = HtmlCompat.fromHtml(list[position], HtmlCompat.FROM_HTML_MODE_LEGACY)
        footerView.text = "Page ${position + 1} of ${list.size}"

        container.addView(view)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

}