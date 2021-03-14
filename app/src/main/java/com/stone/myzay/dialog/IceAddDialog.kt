package com.stone.myzay.dialog

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.stone.myzay.database.DatabaseProvider
import com.stone.myzay.databinding.DialogIceAddBinding
import com.stone.myzay.response.Ice
import com.stone.myzay.utils.AppUtils

class IceAddDialog(private val context: Context) {
   private val dialogBinding=DialogIceAddBinding.inflate(LayoutInflater.from(context))
    private val database=DatabaseProvider.instance(context)

    private val dialog = AlertDialog.Builder(context)
    fun showDialog() {
        val currentDate= AppUtils.getCurrentDate()
        dialogBinding.date.text=currentDate
        var dialogSpinner: String? = null
        var ice: Ice
        val dialog = AlertDialog.Builder(context)
        if (this.database.iceDao().getICE(currentDate)!=null){
            ice= database.iceDao().getICE(currentDate)!!
            Toast.makeText(context,"Data", Toast.LENGTH_SHORT).show()
        }else{
            ice= Ice(
                0,
                post100 = dialogBinding.edit100.text.toString(),
                post50 = dialogBinding.edit50.text.toString(),
                date = currentDate,
                place = dialogSpinner.toString(),
                remain50 = "5",
                remain100 = "0",
                price = "0"
            )
            Toast.makeText(context,"empty", Toast.LENGTH_SHORT).show()
            database.iceDao().addICE(ice)
        }


        dialogBinding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                dialogSpinner = parent?.selectedItem.toString()

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }
        dialog.setCancelable(false)
        dialog.setTitle("Add Sell")
            .setView(dialogBinding.root)
            .setPositiveButton("OK") { dialog, _ ->
                ice.post100=dialogBinding.edit100.text.toString()
                ice.post50=dialogBinding.edit50.text.toString()
                if (ice.post100.isNotEmpty() && ice.post50.isNotEmpty()) {
                    Toast.makeText(
                        context,
                        dialogBinding.edit100.text.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                    database.iceDao().addICE(ice)
                    dialog.dismiss()
                }else{
                    Toast.makeText(
                        context,
                        "Item should not be empty" ,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            .setNeutralButton("CANCEL"){dialog,_->
                dialog.dismiss()
            }
            .show()

    }
}