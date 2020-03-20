package com.mutliplerequest.parallel.ui;


import android.app.Dialog;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mutliplerequest.parallel.App;
import com.mutliplerequest.parallel.AppExecutors;
import com.mutliplerequest.parallel.R;
import com.mutliplerequest.parallel.databinding.FragmentMainBinding;
import com.mutliplerequest.parallel.network.ApiInterface;
import com.mutliplerequest.parallel.network.ApiUtils;
import com.mutliplerequest.parallel.utils.ApplicationUtils;
import com.mutliplerequest.parallel.utils.DialogUtils;
import com.mutliplerequest.parallel.utils.NetworkUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import okhttp3.ResponseBody;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {

    private FragmentMainBinding binding;
    private Dialog              dialog;
    private ApiInterface        apiInterface;
    private AppExecutors        appExecutors;

    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_main, container, false);
        initResources();
        return binding.getRoot();
    }

    private void initResources() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        apiInterface = ApiUtils.getApiService();
        appExecutors = new AppExecutors();

        binding.btnNetworkCall.setOnClickListener((View)->{
            if(NetworkUtils.isConnected(App.getInstance())){
                startNetworkCall();
            }else {
              if(getActivity() != null && isAdded()){
                  dialog = DialogUtils.showNoInternetDialog(getActivity());
              }
            }
        });
    }

    private void startNetworkCall() {
        showDialog();
        appExecutors.getExeNetworkIO().execute(()->{
            List<Observable<ResponseBody>> requests = new ArrayList<>();
            requests.add(apiInterface.getContent1());
            requests.add(apiInterface.getContent2());
            requests.add(apiInterface.getContent3());

            Observable.zip(
                    requests,
                    new Function<Object[], List<String>>() {
                        @Override
                        public List<String> apply(Object[] objects) throws Exception {
                            List<String> dataResponses = new ArrayList<>();
                            if(objects != null){
                                for (Object o : objects) {
                                    ResponseBody rb = (ResponseBody) o;
                                    dataResponses.add(rb.string());
                                }
                            }
                            return dataResponses;
                        }
                    })
                    .doOnComplete(()->{dismissDialog();})
                    .subscribe(
                            new Consumer<List<String>>() {
                                @Override
                                public void accept(List<String> dataResponses) throws Exception {
                                    if(dataResponses != null && dataResponses.size() > 0){
                                        appExecutors.getExeMainThread().execute(()->{
                                           String tv1 = App.getInstance().getString(R.string.char1);
                                           String tv2 = App.getInstance().getString(R.string.char2);
                                           String tv3 = App.getInstance().getString(R.string.char3);

                                           binding.tv1.setText(tv1+" "+ApplicationUtils.first20Char(dataResponses.get(0)));
                                           binding.tv2.setText(tv2+" "+ApplicationUtils.first20Char(dataResponses.get(1)));
                                           binding.tv3.setText(tv3+" "+ApplicationUtils.first20Char(dataResponses.get(2)));
                                        });
                                    }
                                }
                            },

                            new Consumer<Throwable>() {
                                @Override
                                public void accept(Throwable e) throws Exception {
                                    Log.e("onSubscribe", "Throwable: " + e);
                                }
                            }
                    );
        });
    }

    private void showDialog(){
        if(getActivity() != null && isAdded()){
            dialog = DialogUtils.showLoadingDialog(getActivity());
        }
    }

    private void dismissDialog(){
        if(dialog != null && dialog.isShowing()){
            dialog.dismiss();
        }
    }
}
