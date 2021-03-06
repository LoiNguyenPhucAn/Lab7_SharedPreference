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
         * T???o m???i ?????i t?????ng SharedPreferences v???i file name l?? save_pref v?? mode l?? MODE_PRIVATE
         * file name c???a ?????i t?????ng SharePreferences n??y ph???i tr??ng v???i ?????i t?????ng ??? M001RegisterFragment
         * ????? c?? th??? get data ???? ???????c M001RegisterFragment l??u v??o.
         * https://developer.android.com/reference/android/content/Context#getSharedPreferences(java.lang.String,%20int)
         * */
        SharedPreferences pref = mContext.getSharedPreferences(MainActivity.SAVE_PREF, Context.MODE_PRIVATE);

        // Ki???m tra ?????a ch??? mail ???? ???????c ????ng k?? hay ch??a b???ng c??ch l???y ra d??? li???u ki???u String trong ?????i t?????ng pref
        // ki???m tra key trong ?????i t?????ng pref (SharedPreferences) n???u kh??ng c?? key t????ng ???ng th?? tr??? v??? gi?? tr??? null
        String savedPass = pref.getString(mail, null);
        if (savedPass == null) {
            Toast.makeText(mContext, "Email is not existed!", Toast.LENGTH_SHORT).show();
            return;
        }

        //Ki???m tra pass c?? tr??ng kh???p v???i pass ???? ????ng k?? hay ch??a
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
