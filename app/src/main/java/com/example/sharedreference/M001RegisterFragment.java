package com.example.sharedreference;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class M001RegisterFragment extends Fragment implements View.OnClickListener {

    private EditText edtEmail, edtPass, edtRepass;

    private Context mContext;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.m001_frg_register, container, false);

        initView(rootView);

        return rootView;
    }

    private void initView(View v) {

        edtEmail = v.findViewById(R.id.edt_email);

        edtPass = v.findViewById(R.id.edt_pass);

        edtRepass = v.findViewById(R.id.edt_re_pass);


        v.findViewById(R.id.tv_register).setOnClickListener(this);

        v.findViewById(R.id.iv_back).setOnClickListener(this);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    @Override
    public void onClick(View v) {
        v.startAnimation(AnimationUtils.loadAnimation(mContext, androidx.appcompat.R.anim.abc_fade_in));

        if (v.getId() == R.id.iv_back) {

            gotoLoginScreen();

        } else if (v.getId() == R.id.tv_register) {

            register(edtEmail.getText().toString(), edtPass.getText().toString(), edtRepass.getText().toString());

        }
    }

    private void register(String mail, String pass, String repass) {
        if (mail.isEmpty() || pass.isEmpty() || repass.isEmpty()) {

            Toast.makeText(mContext, "Empty value", Toast.LENGTH_SHORT).show();

            return;

        }

        if (!pass.equals(repass)) {

            Toast.makeText(mContext, "Password is not match", Toast.LENGTH_SHORT).show();

        }

        /**
         * Tạo mới đối tượng SharedPreferences với name là save_pref và mode là MODE_PRIVATE
         * https://developer.android.com/reference/android/content/Context#getSharedPreferences(java.lang.String,%20int)
         * */
        SharedPreferences pref = mContext.getSharedPreferences(MainActivity.SAVE_PREF, Context.MODE_PRIVATE);

        /**
         * Kiểm tra địa chỉ mail đã được đăng ký hay chưa
         * Bước 1: getString từ đối tượng SharedPreferences với key là giá trị mail
         * Bước 2: nếu giá trị hàm getString trả về là null nghĩa là địa chỉ mail chưa được đăng ký,
         * ngược lại thông báo mail đã tồn tại
         */


        //https://developer.android.com/reference/android/content/SharedPreferences#getString(java.lang.String,%20java.lang.String)
        //Hàm getString với tham số key là giá trị biến mail và null
        //giá trị trả về của hàm này là pass đã lưu nếu tồn tại key trong đối tượng pref (SharedPreferences) giống giá trị biến mail
        //giá trị trả về là null nếu key trong đối tượng pref không có giá trị nào giống mail
        String savedPass = pref.getString(mail, null);

        if (savedPass != null) {
            Toast.makeText(mContext, "Email is existed!", Toast.LENGTH_SHORT).show();
            return;
        }

        /**
         * thực thi lưu giá trị dữ liệu kiểu String vào đối tượng pref (SharedPreferences)
         * key: giá trị biến mail
         * value: giá trị biến pass
         **/
        pref.edit().putString(mail, pass).apply();

        Toast.makeText(mContext, "Register account successfully!", Toast.LENGTH_SHORT).show();

        gotoLoginScreen();

    }

    private void gotoLoginScreen() {
        ((MainActivity) mContext).gotoLoginScreen();
    }
}
