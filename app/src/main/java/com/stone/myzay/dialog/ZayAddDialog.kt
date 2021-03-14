package com.stone.myzay.dialog

import android.Manifest
import android.app.Activity.RESULT_OK
import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.view.LayoutInflater
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.DialogFragment
import com.stone.myzay.database.DatabaseProvider
import com.stone.myzay.database.IceDatabase
import com.stone.myzay.databinding.DialogAddZayBinding
import com.stone.myzay.response.Zay
import com.stone.myzay.utils.AppUtils


class ZayAddDialog : DialogFragment() {
    private lateinit var binding: DialogAddZayBinding
    private lateinit var uri: Uri
    private lateinit var database:IceDatabase
    private val PERMISSION_REQUEST_CODE = 100
    private val IMAGE_REQUEST_CODE = 101
    private lateinit var img:String

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = DialogAddZayBinding.inflate(LayoutInflater.from(context))
        database=DatabaseProvider.instance(requireContext())


        val dialog = AlertDialog.Builder(context)
            .setTitle("Add Price")
            .setView(binding.root)
            .setCancelable(false)
            .setPositiveButton("OK") { dialog, _ ->
                if (img.isNotEmpty()&& img!=null){
                addZay(
                    Zay(
                        id = 0,
                        title = binding.title.text.toString(),
                        price = binding.price.text.toString(),
                        img = img
                    )

                )
                }
                Toast.makeText(context, img, Toast.LENGTH_LONG).show()
                dialog.dismiss()

            }
            .setNegativeButton("CANCEL") { dialog, _ ->
                dialog.dismiss()
            }
        binding.chooseImage.setOnClickListener {
            //checkPermission()
            chooseImage()
            Toast.makeText(context,"click ",Toast.LENGTH_LONG).show()
        }
        return dialog.create()
    }

    private fun checkPermission() {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) !=
            PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                Array(1) { Manifest.permission.READ_EXTERNAL_STORAGE }, PERMISSION_REQUEST_CODE
            )
        }else{
           chooseImage()
        }

    }

    private fun chooseImage() {
        val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
//        val intent = Intent(Intent.ACTION_GET_CONTENT)
//        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
//        intent.type = "image/*"
        startActivityForResult(gallery, IMAGE_REQUEST_CODE)
//        val i = Intent()
//        i.type = "image/*"
//        i.action = Intent.ACTION_GET_CONTENT
//        i.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
//        // pass the constant to compare it
//        // with the returned requestCode
//
//        // pass the constant to compare it
//        // with the returned requestCode
//        startActivityForResult(Intent.createChooser(i, "Select Picture"), IMAGE_REQUEST_CODE)

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(
                    requireActivity(),
                    Array<String>(1) { Manifest.permission.READ_EXTERNAL_STORAGE },
                    PERMISSION_REQUEST_CODE
                )
                Toast.makeText(context, "Permission denied", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Permission granted", Toast.LENGTH_SHORT).show()
            }
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == IMAGE_REQUEST_CODE||resultCode==RESULT_OK) {
            //uri = data?.data!!


            val inputData = requireContext().contentResolver.openInputStream(data?.data!!)?.readBytes()


//            img=Base64.encodeToString(inputData, Base64.DEFAULT)
//            var bit=Base64.decode(img, Base64.DEFAULT)

            var bitmap=BitmapFactory.decodeByteArray(inputData, 0, inputData?.size!!)
            bitmap= Bitmap.createScaledBitmap(bitmap,400,300,true)


            img=AppUtils.bitmapToString(bitmap)


            binding.imgCover.setImageBitmap(bitmap)



        }
    }

    private fun addZay(zay: Zay) {
        database.zayDao().insert(zay)
    }

    override fun onDestroyView() {

        super.onDestroyView()
    }
//    private val fragment: Fragment = ZayFragment()
//    private val dialog = AlertDialog.Builder(context)
//    private val binding = DialogAddZayBinding.inflate(LayoutInflater.from(context))
//    fun showDialog() {
//
//        dialog
//            .setTitle("Add Price")
//            .setView(binding.root)
//            .setCancelable(false)
//            .setPositiveButton("OK") { dialog, _ ->
//                dialog.dismiss()
//            }
//            .setNegativeButton("CANCEL") { dialog, _ ->
//                dialog.dismiss()
//            }
//            .show()
//    }
//
//    fun loadImageFromGallery() {
//
//    }
//
//    private fun checkPermission() {
//        if (ActivityCompat.checkSelfPermission(
//                context,
//                Manifest.permission.READ_EXTERNAL_STORAGE
//            )
//            != PackageManager.PERMISSION_GRANTED
//        ) {
//            ActivityCompat.requestPermissions(
//                context.applicationContext as Activity,
//                Array<String>(1) { Manifest.permission.READ_EXTERNAL_STORAGE }, 100
//            )
//            return
//
//        }
//        val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
//        fragment.startActivityForResult(gallery, 100)
////        var intent=Intent(Intent.ACTION_GET_CONTENT)
////        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
////        intent.type = "image/*"
////        fragment.startActivityForResult(intent, 200)
//
//    }
//
//    fun bind(uri: Uri) {
//        binding.imgCover.setImageURI(uri)
//    }
//
//    companion object {
//        fun activityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//            if (requestCode == 200 && resultCode == RESULT_OK) {
//
//                val uri: Uri? = data?.data
//
////                    var bitmaps = java.lang.reflect.Array
////                    var imageSources = ArrayList()
////                    val clipData: ClipData = attr.data.getClipData()
////                    //clip data will be null if user select one item from gallery
////
////                    //clip data will be null if user select one item from gallery
////                    if (clipData != null) {
////                        for (i in 0 until clipData.itemCount) {
////                            val imageUri: Uri = clipData.getItemAt(i).uri
////                            try {
////                                val `is`: InputStream =
////                                    getContentResolver().openInputStream(imageUri)
////                                val bitmap = BitmapFactory.decodeStream(`is`)
////                                bitmaps.add(bitmap)
////                                val imageSource: String = ImageBitmapString.BitMapToString(bitmap)
////                                imageSources.add(imageSource)
////                            } catch (e: FileNotFoundException) {
////                                e.printStackTrace()
////                            }
////                        }
////                    }
//            }
//        }
//    }

}


