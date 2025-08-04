package com.fajrifarid.medicalinfoapp.main

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fajrifarid.medicalinfoapp.R
import com.fajrifarid.medicalinfoapp.common.MedicalInfo
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainFragment : Fragment(), MedicalInfoListener {
    private lateinit var rvMedicalInfo : RecyclerView
    private lateinit var fabAddData : FloatingActionButton
    private lateinit var adapter : MedicalInfoAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initMedicalInfoList()
        getDataFromInputFragment()
        fabAddData.setOnClickListener {
            val bundle = bundleOf("medicalInputData" to adapter.currentList.toTypedArray())
            findNavController().navigate(R.id.action_mainFragment_to_inputFragment, bundle)
        }
    }

    private fun initViews(){
        view?.let {
            rvMedicalInfo = it.findViewById(R.id.rv_medical_info)
            fabAddData = it.findViewById(R.id.fab_add_data)
            adapter = MedicalInfoAdapter(this)
        }
    }

    private fun initMedicalInfoList(){
        rvMedicalInfo?.layoutManager = LinearLayoutManager(context)
        rvMedicalInfo?.adapter = adapter
        adapter.setData(generateDummyData())
    }

    private fun getDataFromInputFragment(){
        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<List<MedicalInfo>>("ResultKey")
            ?.observe(viewLifecycleOwner) { result ->
                adapter.setData(result)
            }
    }

    private fun generateDummyData(): List<MedicalInfo>{
        return listOf(
            MedicalInfo(name = "Hospital 1"),
            MedicalInfo(name = "Hospital 2"),
        )

    }

    override fun onPhoneNumberClicked(phoneNumber: String) {
        val intent = Intent(Intent.ACTION_DIAL)
        intent.data = Uri.parse("tel:$phoneNumber")
        startActivity(intent)
    }
}