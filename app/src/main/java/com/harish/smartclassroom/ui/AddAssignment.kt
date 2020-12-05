package com.harish.smartclassroom.ui

import android.R.attr
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.harish.smartclassroom.R
import com.harish.smartclassroom.data.Apis
import com.harish.smartclassroom.data.AppData
import com.harish.smartclassroom.data.models.AddAssignmentResponse
import com.harish.smartclassroom.extensions.getFileName
import kotlinx.android.synthetic.main.activity_add_assignment.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream


class AddAssignment : AppCompatActivity() {

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_assignment)

        add.setOnClickListener {

            selectPdfFromStorage()
        }

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

            val batchid = RequestBody.create("text/plain".toMediaTypeOrNull(),batchId!!)
            val assign_id = RequestBody.create("text/plain".toMediaTypeOrNull(),assignmentID!!)
            val semester = RequestBody.create("text/plain".toMediaTypeOrNull(),sem!!)
            val stud_id = RequestBody.create("text/plain".toMediaTypeOrNull(),studentID!!)
            val faculty_id = RequestBody.create("text/plain".toMediaTypeOrNull(),facultyId!!)
            val subject = RequestBody.create("text/plain".toMediaTypeOrNull(),subject_!!)
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

                Apis().addAssignment(batchid,assign_id,semester,stud_id,faculty_id,subject,filePart).enqueue(object :Callback<AddAssignmentResponse>{
                    override fun onFailure(call: Call<AddAssignmentResponse>, t: Throwable) {
                        Toast.makeText(this@AddAssignment, t.toString(), Toast.LENGTH_SHORT).show()
                    }

                    override fun onResponse(
                        call: Call<AddAssignmentResponse>,
                        response: Response<AddAssignmentResponse>
                    ) {
                        if (response.isSuccessful){
                            Toast.makeText(this@AddAssignment, response.body()!!.status, Toast.LENGTH_SHORT).show()
                            Log.e("UPLOAD",response.body()!!.url)
                        }

                    }
                })




        }


    }


//    private fun uploadImage() {
//        if (selectedImageUri == null) {
//            layout_root.snackbar("Select an Image First")
//            return
//        }
//
//        val parcelFileDescriptor =
//            contentResolver.openFileDescriptor(selectedImageUri!!, "r", null) ?: return
//
//        val inputStream = FileInputStream(parcelFileDescriptor.fileDescriptor)
//        val file = File(cacheDir, contentResolver.getFileName(selectedImageUri!!))
//        val outputStream = FileOutputStream(file)
//        inputStream.copyTo(outputStream)
//
//        progress_bar.progress = 0
//        val body = UploadRequestBody(file, "image", this)
//        MyAPI().uploadImage(
//            MultipartBody.Part.createFormData(
//                "image",
//                file.name,
//                body
//            ),
//            RequestBody.create(MediaType.parse("multipart/form-data"), "json")
//        ).enqueue(object : Callback<UploadResponse> {
//            override fun onFailure(call: Call<UploadResponse>, t: Throwable) {
//                layout_root.snackbar(t.message!!)
//                progress_bar.progress = 0
//            }
//
//            override fun onResponse(
//                call: Call<UploadResponse>,
//                response: Response<UploadResponse>
//            ) {
//                response.body()?.let {
//                    layout_root.snackbar(it.message)
//                    progress_bar.progress = 100
//                }
//            }
//        })
//
//    }
}
//https://proandroiddev.com/uploading-a-file-with-progress-in-kotlin-6cae3aa4a2d4
//https://blog.mindorks.com/how-to-open-a-pdf-file-in-android-programmatically
//https://www.simplifiedcoding.net/android-upload-file-to-server/