package com.mangobloggerandroid.app.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mangobloggerandroid.app.R;
import com.mangobloggerandroid.app.adapter.HomeBaseAdapter;
import com.mangobloggerandroid.app.model.HomeGroup;
import com.mangobloggerandroid.app.model.HomeItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ujjawal on 10/10/17.
 *
 */

public class HomeFragment extends Fragment {

    FragmentOnClickListener mListener;

    private RecyclerView mRecyclerView;

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
//           retreive args
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recycler_view, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setHasFixedSize(true);

        setupAdapter();

    }

   /* @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // verify that host activity implements the callback interface
        try {
            // cast to AvatarPickerDialogListener so we can send events to host
            mListener = (FragmentOnClickListener) context;
        } catch(ClassCastException e) {
            // the activity doesn't implement the interface, throw exception
            throw new ClassCastException(context.toString() +
                    " activity must implement AvatarPickerDialogListener ");
        }
    }*/

    private void setupAdapter() {
        HomeBaseAdapter itemAdapter = new HomeBaseAdapter(getContext());

        itemAdapter.addSnap(new HomeGroup(Gravity.START, HomeBaseAdapter.CARD_SIZE_SMALL, "Explore Mangoblogger", getExploreItems()));
        itemAdapter.addSnap(new HomeGroup(Gravity.START, HomeBaseAdapter.CARD_SIZE_MEDIUM, "Our Recent Blogs", getRecentBlogs()));
        itemAdapter.addSnap(new HomeGroup(Gravity.CENTER, HomeBaseAdapter.CARD_SIZE_PAGER, "Our Services", getServices()));

        mRecyclerView.setAdapter(itemAdapter);


    }

    private List<HomeItem> getExploreItems() {
        List<HomeItem> exploreItems = new ArrayList<>();
        exploreItems.add(new HomeItem("Analytics Terms",
                "https://mangoblogger-9ffff.firebaseio.com/analytics", "null",
                R.mipmap.analytics_cover, false));
        exploreItems.add(new HomeItem("Ux Terms",
                "https://mangoblogger-9ffff.firebaseio.com/ux_terms", "null",
                R.mipmap.uxterms_cover, false));
        exploreItems.add(new HomeItem("Blogs",
                "https://www.mangoblogger.com/mangoblogger-blog/", "null",
                R.mipmap.blog_cover, true));

        return exploreItems;
    }

    private List<HomeItem> getRecentBlogs() {
        List<HomeItem> blogs = new ArrayList<>();
        blogs.add(new HomeItem("Indian Mobile Congress 2017",
                "https://www.mangoblogger.com/blog/highlights-of-india-mobile-congress-2017/",
                "By : Yatin", R.mipmap.recent_blog_one_cover, true));
        blogs.add(new HomeItem("Add Social Login In WordPress Site",
                "https://www.mangoblogger.com/blog/wordpress-plugin-installation/",
                "By : Yatin", R.mipmap.recent_blog_two_cover, true));
        blogs.add(new HomeItem("Guide : Google Tag Manager Installation",
                "https://www.mangoblogger.com/blog/google-tag-manager-installation-website/",
                "By : Yatin", R.mipmap.recent_blog_three_cover, true));
        blogs.add(new HomeItem("What is Google Analytics",
                "https://www.mangoblogger.com/blog/what-is-google-analytics/",
                "By : Siddhant", R.mipmap.recent_blog_four_cover, true));
        blogs.add(new HomeItem("All About Pixel Tracking",
                "https://www.mangoblogger.com/blog/all-about-tracking-pixel/",
                "By : Mangoblogger", R.mipmap.recent_blog_five_cover, true));
        return blogs;
    }

    private List<HomeItem> getServices()  {
        List<HomeItem> services = new ArrayList<>();
        services.add(new HomeItem("Analytics",
                "https://www.mangoblogger.com/product/google-analytics-dashboard/",
                "$100", R.mipmap.service_analytics_cover, true));
        services.add(new HomeItem("Kickstarter Package",
                "https://www.mangoblogger.com/product/google-analytics-and-google-tag-manager/",
                "$200", R.mipmap.service_kickstarter_cover, true));
        services.add(new HomeItem("SEO Consultation",
                "https://www.mangoblogger.com/product/seo-consultation/",
                "$200", R.mipmap.service_seo_cover, true));
        services.add(new HomeItem("Ux Consultation",
                "https://www.mangoblogger.com/product/ux-consultation/",
                "$500", R.mipmap.service_ux_consulation_cover, true));

        return services;
    }

    public interface FragmentOnClickListener {
        void onItemClick(HomeItem homeItem);
    }

}