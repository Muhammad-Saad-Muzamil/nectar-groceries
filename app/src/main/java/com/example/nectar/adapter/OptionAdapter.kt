import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.nectar.R

class OptionsAdapter(private val items: List<Any>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val TYPE_PROFILE = 0
        private const val TYPE_OPTION = 1
    }

    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is ProfileItem -> TYPE_PROFILE
            is OptionItem -> TYPE_OPTION
            else -> throw IllegalArgumentException("Invalid item type")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_PROFILE -> ProfileViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.item_profile, parent, false)
            )
            TYPE_OPTION -> OptionViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.item_option, parent, false)
            )
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ProfileViewHolder -> holder.bind(items[position] as ProfileItem)
            is OptionViewHolder -> holder.bind(items[position] as OptionItem)
        }
    }

    override fun getItemCount() = items.size

    class ProfileViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val profileImage: ImageView = view.findViewById(R.id.profile_image)
        private val profileName: TextView = view.findViewById(R.id.profile_name)
        private val profileEmail: TextView = view.findViewById(R.id.profile_email)

        fun bind(profile: ProfileItem) {
            profileImage.setImageResource(profile.imageResId)
            profileName.text = profile.name
            profileEmail.text = profile.email
        }
    }

    class OptionViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val optionIcon: ImageView = view.findViewById(R.id.option_icon)
        private val optionText: TextView = view.findViewById(R.id.option_text)

        fun bind(option: OptionItem) {
            optionIcon.setImageResource(option.iconResId)
            optionText.text = option.text
        }
    }
}