package com.example.nectar.ui.cart

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.nectar.R

class CartItemAdapter(
    private val cartItems: MutableList<CartItem>,
    private val updateTotal: () -> Unit

) : RecyclerView.Adapter<CartItemAdapter.CartViewHolder>() {

    class CartViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val itemName: TextView = view.findViewById(R.id.itemName)
        val itemImage: ImageView = view.findViewById(R.id.itemImage)
        val itemDescription :TextView =view.findViewById(R.id.itemDescription)
        val itemPrice: TextView = view.findViewById(R.id.itemPrice)
        val removeItem: ImageView = view.findViewById(R.id.removeItem)
        val plusButton: ImageButton = view.findViewById(R.id.plusButton)
        val minusButton: ImageButton = view.findViewById(R.id.minusButton)
        val quantityText: TextView = view.findViewById(R.id.quantityText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_cart, parent, false)
        return CartViewHolder(view)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val item = cartItems[position]
        holder.itemName.text = item.name
        holder.itemImage.setImageResource(item.imageRes)
        holder.itemDescription.text = item.description
        holder.itemPrice.text = String.format("$%.2f", item.price * item.quantity)
        holder.quantityText.text = item.quantity.toString()


        holder.plusButton.setOnClickListener{
            item.quantity++
            holder.quantityText.text =item.quantity.toString()
            holder.itemPrice.text =String.format("$%.2f",item.price * item.quantity)
            updateTotal()}

        holder.minusButton.setOnClickListener {
            if (item.quantity > 1) {
                item.quantity--
                holder.quantityText.text = item.quantity.toString()
                holder.itemPrice.text = String.format("$%.2f", item.price * item.quantity)
                updateTotal()
            }
        }



        // Remove item when clicked
        holder.removeItem.setOnClickListener {
            cartItems.removeAt(position)
            notifyDataSetChanged()
            updateTotal()
        }
    }

    override fun getItemCount(): Int = cartItems.size
}