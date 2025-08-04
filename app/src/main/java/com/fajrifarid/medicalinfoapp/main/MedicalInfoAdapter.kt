package com.fajrifarid.medicalinfoapp.main

import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.fajrifarid.medicalinfoapp.R
import com.fajrifarid.medicalinfoapp.common.MedicalInfo

class MedicalInfoAdapter(val listener: MedicalInfoListener): ListAdapter<MedicalInfo, MedicalInfoAdapter.MedicalInfoViewHolder>(DiffUtilCallback()) {
    inner class MedicalInfoViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(item: MedicalInfo){
            val hospitalNameView = itemView.findViewById<TextView>(R.id.tv_hospital_name)
            val hospitalAddressView = itemView.findViewById<TextView>(R.id.tv_hospital_address)
            val phoneNumberView = itemView.findViewById<TextView>(R.id.tv_phone_number)
            val btnOptions = itemView.findViewById<View>(R.id.btn_options)

            hospitalNameView.text = item.name
            hospitalAddressView.text = item.address
            phoneNumberView.text = item.phoneNumber

            phoneNumberView.setOnClickListener {
                listener.onPhoneNumberClicked(phoneNumberView.text.toString())
            }

            btnOptions.setOnClickListener {
                val popup = PopupMenu(itemView.context, btnOptions, Gravity.END)

                popup.inflate(R.menu.item_menu)

                popup.setOnMenuItemClickListener { menuItem ->
                    when (menuItem.itemId) {
                        R.id.action_edit -> {
                            listener.onEditClicked(item)
                            true
                        }
                        R.id.action_delete -> {
                            listener.onDeleteClicked(item)
                            true
                        }
                        else -> false
                    }
                }

                popup.show()
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MedicalInfoViewHolder{
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_medical_info, parent, false)
        return MedicalInfoViewHolder(view)
    }

    override fun onBindViewHolder(holder: MedicalInfoViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    fun setData(itemList: List<MedicalInfo>){
        submitList(itemList)
    }

    fun addItems(newItems: MedicalInfo){
        submitList(currentList + newItems)
    }
}