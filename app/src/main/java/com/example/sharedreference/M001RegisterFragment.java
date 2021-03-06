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
         * T???o m???i ?????i t?????ng SharedPreferences v???i name l?? save_pref v?? mode l?? MODE_PRIVATE
         * https://developer.android.com/reference/android/content/Context#getSharedPreferences(java.lang.String,%20int)
         * */
        SharedPreferences pref = mContext.getSharedPreferences(MainActivity.SAVE_PREF, Context.MODE_PRIVATE);

        /**
         * Ki???m tra ?????a ch??? mail ???? ???????c ????ng k?? hay ch??a
         * B?????c 1: getString t??? ?????i t?????ng SharedPreferences v???i key l?? gi?? tr??? mail
         * B?????c 2: n???u gi?? tr??? h??m getString tr??? v??? l?? null ngh??a l?? ?????a ch??? mail ch??a ???????c ????ng k??,
         * ng?????c l???i th??ng b??o mail ???? t???n t???i
         */


        //https://developer.android.com/reference/android/content/SharedPreferences#getString(java.lang.String,%20java.lang.String)
        //H??m getString v???i tham s??? key l?? gi?? tr??? bi???n mail v?? null
        //gi?? tr??? tr??? v??? c???a h??m n??y l?? pass ???? l??u n???u t???n t???i key trong ?????i t?????ng pref (SharedPreferences) gi???ng gi?? tr??? bi???n mail
        //gi?? tr??? tr??? v??? l?? null n???u key trong ?????i t?????ng pref kh??ng c?? gi?? tr??? n??o gi???ng mail
        String savedPass = pref.getString(mail, null);

        if (savedPass != null) {
            Toast.makeText(mContext, "Email is existed!", Toast.LENGTH_SHORT).show();
            return;
        }

        /**
         * th???c thi l??u gi?? tr??? d??? li???u ki???u String v??o ?????i t?????ng pref (SharedPreferences)
         * key: gi?? tr??? bi???n mail
         * value: gi?? tr??? bi???n pass
         **/
        pref.edit().putString(mail, pass).apply();

        Toast.makeText(mContext, "Register account successfully!", Toast.LENGTH_SHORT).show();

        gotoLoginScreen();

    }

    private void gotoLoginScreen() {
        ((MainActivity) mContext).gotoLoginScreen();
    }
}
