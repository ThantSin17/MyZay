package com.stone.myzay.fragment

import android.os.Bundle
import android.text.InputType
import android.view.*
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.stone.myzay.R
import com.stone.myzay.database.DatabaseProvider
import com.stone.myzay.databinding.IceFragmentBinding
import com.stone.myzay.dialog.IceAddDialog
import com.stone.myzay.response.Ice
import com.stone.myzay.utils.AppUtils
import com.stone.myzay.viewModels.ICEViewModel

class ICEFragment : Fragment() {


    private lateinit var viewModel: ICEViewModel
    private lateinit var binding: IceFragmentBinding

    private val database by lazy {
        DatabaseProvider.instance(requireContext())
    }
    private var sell100 = 0
    private var remain100 = 0;
    private var sell50 = 0
    private var remain50 = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.ice_fragment, container, false)
        return binding.root
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ICEViewModel::class.java)

        val currentDate=AppUtils.getCurrentDate()


        val todayData = database.iceDao().getICE(currentDate)
        if (todayData!=null && todayData.post100.isNotEmpty()) {
            binding.ice = todayData
            //binding.text.text = todayData.toString()
            binding.sellTotal.text=AppUtils.getTotal(todayData.post100,todayData.post50)
            Toast.makeText(context, todayData.post100.toString(), Toast.LENGTH_SHORT).show()
        }

        binding.date.text = currentDate

        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }

        binding.sell100.setOnClickListener {
            sellPrice(binding.sell100)


        }
        binding.sell50.setOnClickListener {
            sellPrice(binding.sell50)
        }
        binding.remain100.setOnClickListener {
            remainPrice(binding.remain100)
        }
        binding.remain50.setOnClickListener {
            remainPrice(binding.remain50)
        }
        binding.btnCalculate.setOnClickListener {
            var remain = binding.remainTotal.text.toString().toInt()
            var total = binding.sellTotal.text.toString().toInt() - remain


            Toast.makeText(context, "Total : $total", Toast.LENGTH_LONG).show()
        }


    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_ice_fragment, menu)
        super.onCreateOptionsMenu(menu, inflater)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.add_ice -> IceAddDialog(requireContext()).showDialog()
        }
        return super.onOptionsItemSelected(item)
    }



    override fun onDestroyView() {

        super.onDestroyView()
    }

    private fun setIceData(ice: Ice){
        database.iceDao().addICE(ice)
    }
    private fun updateIceData(ice:Ice){
        database.iceDao().updateIce(ice)
    }
    private fun doAdd() {
        sell100 = binding.sell100.text.toString().toInt() * 100
        sell50 = binding.sell50.text.toString().toInt() * 50
        binding.sellTotal.text = (sell100 + sell50).toString()
    }

    private fun doRemain() {
        remain100 = binding.remain100.text.toString().toInt() * 100
        remain50 = binding.remain50.text.toString().toInt() * 50
        binding.remainTotal.text = (remain100 + remain50).toString()
    }

    private fun sellPrice(textView: TextView) {
        var editText = EditText(context)
        editText.inputType = InputType.TYPE_CLASS_NUMBER
        editText.setText(textView.text.toString())
        editText.requestFocus()
        AlertDialog.Builder(requireContext())
            .setTitle("Edit")
            .setView(editText)
            .setPositiveButton("OK") { dialog, _ ->
                if (editText.text.isEmpty()) {
                    textView.text = 0.toString()
                } else {
                    textView.text = editText.text.toString()
                }
                doAdd()
                dialog.dismiss()
            }
            .show()

    }

    private fun remainPrice(textView: TextView) {
        var editText = EditText(context)
        editText.inputType = InputType.TYPE_CLASS_NUMBER
        editText.setText(textView.text.toString())
        val dialog = AlertDialog.Builder(requireContext())
            .setTitle("Edit")
            .setView(editText)
            .setPositiveButton("OK") { dialog, _ ->
                if (editText.text.isEmpty()) {
                    textView.text = 0.toString()
                } else {
                    textView.text = editText.text.toString()
                }
                doRemain()
                dialog.dismiss()
            }
            .show()

    }

}

