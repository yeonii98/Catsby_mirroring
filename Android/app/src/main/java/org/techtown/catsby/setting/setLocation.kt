package org.techtown.catsby.setting


import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import com.google.android.gms.location.*
import com.yanzhenjie.permission.AndPermission
import com.yanzhenjie.permission.runtime.Permission
import com.yanzhenjie.permission.runtime.Permission.ACCESS_FINE_LOCATION
import kotlinx.android.synthetic.main.fragment_set_location.*
import kotlinx.android.synthetic.main.fragment_setting.*
import org.techtown.catsby.R

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private lateinit var fusedLocationClient:FusedLocationProviderClient
/**
 * A simple [Fragment] subclass.
 * Use the [setLocation.newInstance] factory method to
 * create an instance of this fragment.
 */
class setLocation : Fragment() {
    var locationClient: FusedLocationProviderClient? = null
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    public var myadd : String?=null

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
        return inflater.inflate(R.layout.fragment_set_location, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val geocoder = Geocoder(context)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

        if (ActivityCompat.checkSelfPermission( requireContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            Toast.makeText(context, "위치 권한을 설정해주세요.", Toast.LENGTH_SHORT).show()
        } else {
            myposbtn.setOnClickListener{
                requestLocation()
                fusedLocationClient.lastLocation.addOnSuccessListener {
                    val address = geocoder.getFromLocation(it.latitude, it.longitude, 1)
                    myadd = address[0].subLocality
                    mypostv.setText(myadd)

                    // 값 꾸러미(bundle) 생성 - bundle은 일반적인 형태로 생성해도 됩니다.
                    val bundle = bundleOf("myaddkey" to myadd)
                    // 요청키로 수신측의 리스너에 값을 전달
                    setFragmentResult("myaddkey", bundle)
                }

            }}
        AndPermission.with(this)
            .runtime()
            .permission(Permission.Group.LOCATION)
            .onGranted {permissions ->
                Log.d("Main", "허용된 권한 갯수 : ${permissions.size}")
            }
            .onDenied {permissions ->
                Log.d("main", "거부된 권한 갯수 : ${permissions.size}")
            }
            .start()


    }

    private fun requestLocation() {
        locationClient = LocationServices.getFusedLocationProviderClient(activity)

        try{
            locationClient?.lastLocation?.addOnSuccessListener { location ->
                if(location == null) {
                    mypostv.setText("최근 위치 확인 실패")
                } else {
                    //mypostv.setText("최근 위치 확인 성공 : ${location.latitude}, ${location.longitude}")
                }
            }
                ?.addOnFailureListener{
                    mypostv.setText("최근 위치 확인 시 에러 : ${it.message}")
                    it.printStackTrace()
                }
            val locationRequest = LocationRequest.create()
            locationRequest.run{
                priority = LocationRequest.PRIORITY_HIGH_ACCURACY
                interval = 60*1000
            }
            val locationCallback = object : LocationCallback(){
                override fun onLocationResult(p0: LocationResult?) {
                    p0?.let{
                        for((i, location) in it.locations.withIndex()) {
                            //mypostv.setText("내 위치 : ${location.latitude}, ${location.longitude}" )
                        }
                    }
                }
            }
            //location 바뀔 때 마다
            locationClient?.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper())
        }
        catch(e : SecurityException){
            e.printStackTrace()
        }
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment setLocation.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            setLocation().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}