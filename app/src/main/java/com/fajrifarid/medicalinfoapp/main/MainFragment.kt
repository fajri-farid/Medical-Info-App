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
import com.fajrifarid.medicalinfoapp.utils.DataManager
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainFragment : Fragment(), MedicalInfoListener {
    private lateinit var rvMedicalInfo : RecyclerView
    private lateinit var fabAddData : FloatingActionButton
    private lateinit var adapter : MedicalInfoAdapter

    private lateinit var dataManager: DataManager
    private var medicalInfoList = mutableListOf<MedicalInfo>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataManager = DataManager(requireContext())
        initViews()
        initMedicalInfoList()
        getDataFromInputFragment()
        fabAddData.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_inputFragment)
        }
    }

    override fun onResume() {
        super.onResume()
        loadDataFromStorage()
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

        loadDataFromStorage()
    }

    private fun loadDataFromStorage() {
        medicalInfoList = dataManager.getMedicalInfoList()
        adapter.setData(medicalInfoList)
    }

    private fun getDataFromInputFragment(){
        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<MedicalInfo>("NewMedicalInfo")
            ?.observe(viewLifecycleOwner) { newData ->
                dataManager.addMedicalInfo(newData)
                loadDataFromStorage()

                findNavController().currentBackStackEntry?.savedStateHandle?.remove<MedicalInfo>("NewMedicalInfo")
            }
    }

    override fun onPhoneNumberClicked(phoneNumber: String) {
        val intent = Intent(Intent.ACTION_DIAL)
        intent.data = Uri.parse("tel:$phoneNumber")
        startActivity(intent)
    }

    override fun onEditClicked(item: MedicalInfo) {
        val bundle = bundleOf("EditMedicalInfo" to item)
        findNavController().navigate(R.id.action_mainFragment_to_inputFragment, bundle)
    }

    override fun onDeleteClicked(item: MedicalInfo) {
        dataManager.removeMedicalInfo(item)
        loadDataFromStorage()
    }
}