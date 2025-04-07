import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.nectar.R
import com.example.nectar.model.Category

class CategoryDetailFragment : Fragment() {

    private var categoryName: String? = null
    private var categoryImageRes: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            categoryName = it.getString(ARG_CATEGORY_NAME)
            categoryImageRes = it.getInt(ARG_CATEGORY_IMAGE_RES)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_category_detail, container, false)

        // Update UI with category data
        view.findViewById<TextView>(R.id.categoryDetailName).text = categoryName
        categoryImageRes?.let {
            view.findViewById<ImageView>(R.id.categoryDetailImage).setImageResource(it)
        }

        return view
    }

    companion object {
        private const val ARG_CATEGORY_NAME = "categoryName"
        private const val ARG_CATEGORY_IMAGE_RES = "categoryImageRes"

        fun newInstance(category: Category): CategoryDetailFragment {
            return CategoryDetailFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_CATEGORY_NAME, category.name)
                    putInt(ARG_CATEGORY_IMAGE_RES, category.imageResource)
                }
            }
        }
    }
}