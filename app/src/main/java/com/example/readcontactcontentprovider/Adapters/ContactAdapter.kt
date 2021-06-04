package com.example.readcontactcontentprovider.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.ListAdapter
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.readcontactcontentprovider.Models.Contacts
import com.example.readcontactcontentprovider.R

class ContactAdapter(
    private val context: Context
)
    : ListAdapter<Contacts, ContactAdapter.ContactHolder>(DIFF_ITEM_CALLBACK) {

    companion object {
        val DIFF_ITEM_CALLBACK = object : DiffUtil.ItemCallback<Contacts>() {
            override fun areItemsTheSame(oldItem: Contacts, newItem: Contacts): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Contacts, newItem: Contacts): Boolean {
                return oldItem == newItem
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactHolder {
        val view: View = LayoutInflater.from(context).inflate(R.layout.items,parent,false)
        return ContactHolder(view)
    }


    override fun onBindViewHolder(holder: ContactHolder, position: Int) {
        holder.bind(getItem(position))
    }


    class ContactHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.name)
        val num: TextView = itemView.findViewById(R.id.number)
        val image: ImageView = itemView.findViewById(R.id.imageView)


        fun bind(contacts: Contacts) {
            name.text = contacts.name
            num.text = contacts.num

            Glide.with(itemView.context)
                .load("https://www.w3schools.com/w3images/avatar2.png")
                .into(image)

        }
    }



}