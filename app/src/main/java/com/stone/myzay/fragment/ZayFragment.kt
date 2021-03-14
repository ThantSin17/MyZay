package com.stone.myzay.fragment

import android.app.Activity.RESULT_OK
import android.app.AlertDialog
import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import com.github.dhaval2404.imagepicker.ImagePicker
import com.stone.myzay.R
import com.stone.myzay.adapter.ZayAdapter
import com.stone.myzay.database.DatabaseProvider
import com.stone.myzay.database.IceDatabase
import com.stone.myzay.databinding.DialogAddZayBinding
import com.stone.myzay.databinding.ZayFragmentBinding
import com.stone.myzay.dialog.ZayAddDialog
import com.stone.myzay.viewModels.ZayViewModel
import java.io.File

class ZayFragment : Fragment() {

    private val adapter = ZayAdapter()
    private lateinit var database: IceDatabase
    private lateinit var binding: ZayFragmentBinding
    private lateinit var viewModel: ZayViewModel
    private lateinit var dialogBinding:DialogAddZayBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.zay_fragment, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ZayViewModel::class.java)
        dialogBinding = DialogAddZayBinding.inflate(LayoutInflater.from(activity))
        database=DatabaseProvider.instance(requireContext())
        binding.rc.setHasFixedSize(true)
        adapter.submitList(database.zayDao().getAll())
        //adapter.currentList.addAll(database.zayDao().getAll())
        binding.rc.adapter=adapter

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (resultCode) {
            RESULT_OK -> {
                //Image Uri will not be null for RESULT_OK
                val fileUri = data?.data
                dialogBinding.imgCover.setImageURI(fileUri)

                //You can get File object from intent
                val file: File = ImagePicker.getFile(data)!!

                //You can also get File Path from intent
                val filePath:String = ImagePicker.getFilePath(data)!!
            }
            ImagePicker.RESULT_ERROR -> {
                Toast.makeText(context, ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
            }
            else -> {
                Toast.makeText(context, "Task Cancelled", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.zay_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.add -> callDialog()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun callDialog() {
        val pop=ZayAddDialog()
        val fm=this.childFragmentManager
        pop.show(fm,"zzz")
    }

    private fun dialog() {
        val dialog = AlertDialog.Builder(activity)
        dialogBinding.chooseImage.setOnClickListener {
            ImagePicker.with(this)
                .galleryOnly()
                .start()
        }

        dialog
            .setTitle("Add Price")
            .setView(binding.root)
            .setCancelable(false)
            .setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
            }
            .setNegativeButton("CANCEL") { dialog, _ ->
                dialog.dismiss()
            }
            .show()

    }

}