sealed class ListItem

data class ProfileItem(
    val imageResId: Int,
    val name: String,
    val email: String
) : ListItem()

