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

public class M000LoginFragment extends Fragment implements View.OnClickListener {

    Context mContext;
    private EditText edtEmail, edtPass;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.m000_frg_login, container, false);

        initView(rootView);

        return rootView;
    }

    private void initView(View v) {
        edtEmail = v.findViewById(R.id.edt_email);
        edtPass = v.findViewById(R.id.edt_pass);

        v.findViewById(R.id.tv_login).setOnClickListener(this);
        v.findViewById(R.id.tv_register).setOnClickListener(this);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    @Override
    public void onClick(View v) {
        v.startAnimation(AnimationUtils.loadAnimation(mContext, androidx.appcompat.R.anim.abc_fade_in));

        if (v.getId() == R.id.tv_login) {

            login(edtEmail.getText().toString(), edtPass.getText().toString());

        } else if (v.getId() == R.id.tv_register) {

            gotoRegisterScreen();

        }
    }

    private void login(String mail, String pass) {

        if (mail.isEmpty() || pass.isEmpty()) {

            Toast.makeText(mContext, "Empty value", Toast.LENGTH_SHORT).show();

            return;

        }

        /**
         * https://viblo.asia/p/shared-preferences-trong-android-1Je5EEvY5nL
         * https://developer.android.com/reference/android/content/SharedPreferences
         *
         * Tạo mới đối tượng SharedPreferences với file name là save_pref và mode là MODE_PRIVATE
         * file name của đối tượng SharePreferences này phải trùng với đối tượng ở M001RegisterFragment
         * để có thể get data đã được M001RegisterFragment lưu vào.
         * https://developer.android.com/reference/android/content/Context#getSharedPreferences(java.lang.String,%20int)
         * */
        SharedPreferences pref = mContext.getSharedPreferences(MainActivity.SAVE_PREF, Context.MODE_PRIVATE);

        // Kiểm tra địa chỉ mail đã được đăng ký hay chưa bằng cách lấy ra dữ liệu kiểu String trong đối tượng pref
        // kiểm tra key trong đối tượng pref (SharedPreferences) nếu không có key tương ứng thì trả về giá trị null
        String savedPass = pref.getString(mail, null);
        if (savedPass == null) {
            Toast.makeText(mContext, "Email is not existed!", Toast.LENGTH_SHORT).show();
            return;
        }

        //Kiểm tra pass có trùng khớp với pass đã đăng ký hay chưa
        if (!pass.equals(savedPass)) {
            Toast.makeText(mContext, "Password is not correct!", Toast.LENGTH_SHORT).show();
            return;
        }

        Toast.makeText(mContext, "Login account successfully!", Toast.LENGTH_SHORT).show();

    }

    private void gotoRegisterScreen() {
        ((MainActivity) mContext).gotoRegisterScreen();
    }
}
