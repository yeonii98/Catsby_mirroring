package com.example.catsby

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_writing_list.*
import org.techtown.catsby.R
import org.techtown.catsby.retrofit.RetrofitClient
import org.techtown.catsby.setting.data.model.MyComment
import org.techtown.catsby.setting.data.model.MyPost
import org.techtown.catsby.setting.data.service.MyWritingService
import org.techtown.catsby.setting.Comment
import org.techtown.catsby.setting.CommentAdapter
import org.techtown.catsby.setting.Writing
import org.techtown.catsby.setting.WritingListAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
/**
 * A simple [Fragment] subclass.
 * Use the [writingList.newInstance] factory method to
 * create an instance of this fragment.
 */
class writingList : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var myWritingService: MyWritingService? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_writing_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        recyclerView1.layoutManager = LinearLayoutManager(context)
        val adapter1 = WritingListAdapter()

        myWritingService = RetrofitClient.getMyWritingService()
        myWritingService?.myPosts?.enqueue(object : Callback<List<MyPost>> {
            override fun onResponse(call: Call<List<MyPost>>, response: Response<List<MyPost>>) {
                if (response.isSuccessful) {
                    //정상적으로 통신이 성공된 경우

                    val result: List<MyPost>? = response.body()

                    if (result != null) {
                        for (i in 0..result.size-1){
                            if(result.get(i).townCommunity == null)
                                adapter1.items.add(Writing(result.get(i).bowlCommunity.content,result.get(i).bowlCommunity.updateDate))

                            else if(result.get(i).bowlCommunity == null)
                                adapter1.items.add(Writing(result.get(i).townCommunity.title,result.get(i).townCommunity.date))
                        }
                    }
                    recyclerView1.adapter = adapter1

                } else {
                    println("실패!")
                }
            }

            override fun onFailure(call: Call<List<MyPost>>, t: Throwable) {
                println("통신 실패! $t")
            }
        })

        //내 글 ui ex
//        adapter1.items.add(Writing("안녕하세요","21-06-06"))

        //내 글 클릭 했을 때 글 페이지로 이어지게 하려면 0406-56:41

        writingbtn.setOnClickListener {
            recyclerView1.adapter = adapter1
        }

        commentbtn.setOnClickListener {
            recyclerView1.layoutManager = LinearLayoutManager(context)
            val adapter2 = CommentAdapter()

            myWritingService?.myComments?.enqueue(object : Callback<List<MyComment>> {
                override fun onResponse(call: Call<List<MyComment>>, response: Response<List<MyComment>>) {
                    if (response.isSuccessful) {
                        //정상적으로 통신이 성공된 경우

                        val result: List<MyComment>? = response.body()

                        if (result != null) {
                            for (i in 0..result.size-1){
                                if(result.get(i).townComment == null)
                                    adapter2.items.add(Comment(result.get(i).bowlComment.content,result.get(i).bowlComment.bowlCommunity.content))

                                else if(result.get(i).bowlComment == null)
                                    adapter2.items.add(Comment(result.get(i).townComment.content,result.get(i).townComment.townCommunity.title))
                            }
                        }
                        recyclerView1.adapter = adapter2

                    } else {
                        println("실패!")
                    }
                }

                override fun onFailure(call: Call<List<MyComment>>, t: Throwable) {
                    println("통신 실패! $t")
                }
            })
            //내 댓글 ui ex
//            adapter2.items.add(Comment("너무 귀여워요", "저희 집 고양이좀 보고가세요"))
        }

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment writingList.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                writingList().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}