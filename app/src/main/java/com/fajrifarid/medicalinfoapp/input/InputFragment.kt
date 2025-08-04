package com.fajrifarid.medicalinfoapp.input

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import com.fajrifarid.medicalinfoapp.R
import com.fajrifarid.medicalinfoapp.common.MedicalInfo

class InputFragment : Fragment() {
    private val btnBack by lazy {view?.findViewById<ImageView>(R.id.iv_back_arrow)}
    private val inputNameField by lazy { view?.findViewById<TextView>(R.id.input_hospital_name) }
    private val inputAddressField by lazy { view?.findViewById<TextView>(R.id.input_hospital_address) }
    private val inputPhoneNumberField by lazy { view?.findViewById<TextView>(R.id.input_hospital_phone_number) }
    private val btnSave by lazy { view?.findViewById<TextView>(R.id.btn_save) }
    private val btnDiscard by lazy { view?.findViewById<TextView>(R.id.btn_discard) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_input, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnDiscard?.setOnClickListener {
                discardData()
        }

        btnSave?.setOnClickListener {
           if(isEntryValid()) {
               saveData()
           } else {
               showToastMessage("Data tidak boleh kosong, harap lengkapi")
           }
        }
        handleOnBackPressed()

        btnBack?.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun discardData(){
        inputNameField?.setText("")
        inputAddressField?.setText("")
        inputPhoneNumberField?.setText("")
    }

    private fun handleOnBackPressed() {
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().navigateUp()
            }
        }
        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner, callback)
    }

    private fun saveData(){
        val newDataEntry = MedicalInfo(
            name = inputNameField?.text.toString(),
            address = inputAddressField?.text.toString(),
            phoneNumber = inputPhoneNumberField?.text.toString()
        )
        try{
            findNavController().previousBackStackEntry?.savedStateHandle?.set("NewMedicalInfo", newDataEntry)
            discardData()
            showToastMessage("Data berhasil ditambahkan")
            findNavController().navigateUp()
        } catch (e: Exception){
            showToastMessage("Terjadi kesalahan")
        }
    }

    private fun isEntryValid(): Boolean {
        return !(inputNameField?.text.toString().isBlank() ||
                inputAddressField?.text.toString().isBlank() ||
                inputPhoneNumberField?.text.toString().isBlank())
    }

    private fun showToastMessage(message: String){
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
    }
}