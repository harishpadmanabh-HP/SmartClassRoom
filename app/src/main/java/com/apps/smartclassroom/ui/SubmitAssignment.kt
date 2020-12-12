package com.apps.smartclassroom.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.android.material.appbar.CollapsingToolbarLayout
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.apps.smartclassroom.R
import com.apps.smartclassroom.data.Apis
import com.apps.smartclassroom.data.AppData
import com.apps.smartclassroom.data.models.AddAssignmentResponse
import com.apps.smartclassroom.extensions.getFileName
import kotlinx.android.synthetic.main.activity_submit_assignment.*
import kotlinx.android.synthetic.main.content_scrolling.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

class SubmitAssignment : AppCompatActivity() {
    var selectedFile : File?=null
    val batchId by lazy {
        intent.getStringExtra("BATCH_ID")
    }
    val assignmentID by lazy {
        intent.getStringExtra("ASSGN_ID")
    }
    val subject_ by lazy {
        intent.getStringExtra("SUBJECT")
    }
    val sem by lazy {
        intent.getStringExtra("SEM")
    }
    val facultyId by lazy {
        intent.getStringExtra("FACULTY_ID")
    }
    val studentID by lazy {
        AppData.init(this).getStudentDict()?.stud_id
    }

    val assignmentImage by lazy {
        intent.getStringExtra("ASSIGN_IMAGE")

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_submit_assignment)
        setSupportActionBar(findViewById(R.id.toolbar))
        findViewById<CollapsingToolbarLayout>(R.id.toolbar_layout).title = "sdfdfvbsdfvsdfv"
        iv_assign.load(assignmentImage)
        tv_assign_sub . text = "Subject : ${subject_}"
      //  tv_assign_date . text = "Due Date : ${} "


    }

    fun onSubmit(view: View) {
        selectPdfFromStorage()
    }
    private fun selectPdfFromStorage() {
        Toast.makeText(this, "selectPDF", Toast.LENGTH_LONG).show()
        val browseStorage = Intent(Intent.ACTION_GET_CONTENT)
        browseStorage.type = "application/pdf"
        browseStorage.addCategory(Intent.CATEGORY_OPENABLE)
        startActivityForResult(
            Intent.createChooser(browseStorage, "Select PDF"), 99
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 99 && resultCode == Activity.RESULT_OK && data != null) {
            val selectedPdfFromStorage = data.data
            Log.e("PDF SELECTED",selectedPdfFromStorage.toString())
            selectedFile = File(selectedPdfFromStorage?.path)
//            Log.e("File got",selectedFile.toString())
//           // val file =


            //val selectedFilePath: String = FilePath.getPath(getActivity(), uri)
            //val file = File(selectedFilePath?.)

            val parcelFileDescriptor =
                contentResolver.openFileDescriptor(selectedPdfFromStorage!!, "r", null) ?: return

            val inputStream = FileInputStream(parcelFileDescriptor.fileDescriptor)
            val file = File(cacheDir, contentResolver.getFileName(selectedPdfFromStorage!!))
            val outputStream = FileOutputStream(file)
            inputStream.copyTo(outputStream)

            Log.e("file ",file.name)



            uploadPDF(file)
        }
    }

    private fun uploadPDF(selectedFile: File?) {

        if(selectedFile != null){

           // val batchid = RequestBody.create("text/plain".toMediaTypeOrNull(),batchId!!)
            val assign_id = RequestBody.create("text/plain".toMediaTypeOrNull(),assignmentID!!)
          //  val semester = RequestBody.create("text/plain".toMediaTypeOrNull(),sem!!)
            val stud_id = RequestBody.create("text/plain".toMediaTypeOrNull(),studentID!!)
         //   val faculty_id = RequestBody.create("text/plain".toMediaTypeOrNull(),facultyId!!)
           // val subject = RequestBody.create("text/plain".toMediaTypeOrNull(),subject_!!)
            val requestFile = RequestBody.create("image/jpeg".toMediaTypeOrNull(), selectedFile)


//                val batchid = RequestBody.create("text/plain".toMediaTypeOrNull(),"1")
//                val assign_id = RequestBody.create("text/plain".toMediaTypeOrNull(),"1")
//                val semester = RequestBody.create("text/plain".toMediaTypeOrNull(),"S1")
//                val stud_id = RequestBody.create("text/plain".toMediaTypeOrNull(),"13")
//                val faculty_id = RequestBody.create("text/plain".toMediaTypeOrNull(),"1")
//                val subject = RequestBody.create("text/plain".toMediaTypeOrNull(),"blabla")
//                val requestFile = RequestBody.create("image/jpeg".toMediaTypeOrNull(), selectedFile)
            val filePart =
                MultipartBody.Part.createFormData("avatar", selectedFile.name, requestFile)

            Apis().addAssignment(assign_id,stud_id,filePart).enqueue(object :
                Callback<AddAssignmentResponse> {
                override fun onFailure(call: Call<AddAssignmentResponse>, t: Throwable) {
                    Toast.makeText(this@SubmitAssignment, t.toString(), Toast.LENGTH_SHORT).show()
                }

                override fun onResponse(
                    call: Call<AddAssignmentResponse>,
                    response: Response<AddAssignmentResponse>
                ) {
                    if (response.isSuccessful){
                        Toast.makeText(this@SubmitAssignment, response.body()!!.status, Toast.LENGTH_SHORT).show()
                        Log.e("SUBMIT",response!!.body()!!.message)

                    }

                }
            })




        }


    }

}