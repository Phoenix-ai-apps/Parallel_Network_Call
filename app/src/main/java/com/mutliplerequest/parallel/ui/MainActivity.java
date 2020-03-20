package com.mutliplerequest.parallel.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.mutliplerequest.parallel.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        replaceFragment(new MainFragment(), MainFragment.class.getSimpleName());
    }

    private void replaceFragment(Fragment fragment, String fragmentName) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout_main, fragment, fragmentName);
        transaction.commitAllowingStateLoss();
    }
}
