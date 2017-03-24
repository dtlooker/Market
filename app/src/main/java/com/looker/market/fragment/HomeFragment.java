package com.looker.market.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.looker.market.Constants;
import com.looker.market.R;
import com.looker.market.adapter.HomeCampRecyclerAdapter;
import com.looker.market.adapter.MPagerAdapter;
import com.looker.market.adapter.decoration.DividerItemDecoration;
import com.looker.market.bean.Banner;
import com.looker.market.bean.HomeCampaign;
import com.looker.market.listener.MPagerListener;
import com.looker.market.okhttp.LoadCallback;
import com.looker.market.okhttp.OKHttpHelper;
import com.looker.market.widget.MyIndicator;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private View mView;

    private SliderLayout mSliderLayout;

    /***
     * banner
     ***/
    private ViewPager mBannerPager;
    private MyIndicator mBannerIndicator;
    private List<Banner> mBanners;
    private List<View> mViewList;


    private RecyclerView mRecycler;
    private HomeCampRecyclerAdapter mAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (mView == null) {
            mView = inflater.inflate(R.layout.fragment_home, container, false);
        }

        initView(mView);
        requestBanner();
        requestCampaign();

        return mView;
    }

    private void initView(View view) {

       // mSliderLayout = (SliderLayout) mView.findViewById(R.id.slider);

        /***初始化banner*/
        mBannerPager = (ViewPager) view.findViewById(R.id.indicator_pager);
        mBannerIndicator = (MyIndicator) view.findViewById(R.id.indicator);

        mRecycler = (RecyclerView) view.findViewById(R.id.recycler_view);

    }

    private void requestBanner() {

        OKHttpHelper.getInstance().get(Constants.API.BANNER, new LoadCallback<List<Banner>>(getContext()) {


            @Override
            public void onSuccess(Response response, List<Banner> banners) {
                mBanners = banners;
                initBanner();
            }

            @Override
            public void onResponseErroe(Response response, int code, Exception e) {

            }
        });
    }

    private void requestCampaign() {

        OKHttpHelper.getInstance().get(Constants.API.CAMPAIGN_HOME, new LoadCallback<List<HomeCampaign>>(getContext()) {

            @Override
            public void onSuccess(Response response, List<HomeCampaign> homeCampaigns) {
                initRecycler(homeCampaigns);
            }

            @Override
            public void onResponseErroe(Response response, int code, Exception e) {

            }
        });
    }

    private void initRecycler(List<HomeCampaign> homeCampaigns) {

        if (HomeCampRecyclerAdapter.getViewType() == HomeCampRecyclerAdapter.VIEW_TYPE_L){
            mAdapter = new HomeCampRecyclerAdapter(homeCampaigns, getContext(), R.layout.template_home_left);
        }else if (HomeCampRecyclerAdapter.getViewType() == HomeCampRecyclerAdapter.VIEW_TYPE_R){
            mAdapter = new HomeCampRecyclerAdapter(homeCampaigns, getContext(), R.layout.template_home_right);
        }

        mRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecycler.setAdapter(mAdapter);
        mRecycler.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL_LIST));

    }

    private void initBanner() {
        mViewList = new ArrayList<>();

        for (Banner banner : mBanners) {
            Log.i("TAG", "banner.getImgUrl(): " + banner.getImgUrl());
            View inflate = LayoutInflater.from(getContext()).inflate(R.layout.template_banner, null);
            ImageView bannerItem = (ImageView) inflate.findViewById(R.id.temp_banner_iv);
            Glide.with(getContext()).load(banner.getImgUrl()).into(bannerItem);
            mViewList.add(inflate);
        }
        mBannerPager.setAdapter(new MPagerAdapter(mViewList));
        mBannerPager.addOnPageChangeListener(new MPagerListener(mBannerIndicator));

    }

    public void initSlider() {

        for (Banner banner : mBanners) {
            Log.e("TAG", "banner.getImagUrl(): " + banner.getImgUrl());
            Log.e("TAG", "banner.getName(): " + banner.getName());
            Log.e("TAG", "banner.getDescription(): " + banner.getDescription());
            DefaultSliderView textSliderView = new DefaultSliderView(this.getActivity());
            textSliderView.image(banner.getImgUrl());
            textSliderView.description(banner.getName());
            textSliderView.setScaleType(BaseSliderView.ScaleType.Fit);
            mSliderLayout.addSlider(textSliderView);
        }
    }

}
