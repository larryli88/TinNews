package com.larry.tinnews.ui.home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.larry.tinnews.R;
import com.larry.tinnews.databinding.FragmentHomeBinding;
import com.larry.tinnews.model.Article;
import com.larry.tinnews.model.NewsResponse;
import com.larry.tinnews.repository.NewsRepository;
import com.larry.tinnews.repository.NewsViewModelFactory;
import com.mindorks.placeholderview.SwipeDecor;

public class HomeFragment extends Fragment {

    private HomeViewModel viewModel;
    private FragmentHomeBinding binding;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding
                .swipeView
                .getBuilder()
                .setDisplayViewCount(3)
                .setSwipeDecor(
                        new SwipeDecor()
                                .setPaddingTop(10)
                                .setRelativeScale(0.01f));

        binding.rejectBtn.setOnClickListener(v -> binding.swipeView.doSwipe(false));
        binding.acceptBtn.setOnClickListener(v -> binding.swipeView.doSwipe(true));


        NewsRepository repository = new NewsRepository(getContext());

        viewModel = new ViewModelProvider(this, new NewsViewModelFactory(repository))
                .get(HomeViewModel.class);

        viewModel.setCountryInput("us");
        viewModel
                .getTopHeadlines()
                .observe(getViewLifecycleOwner(), new Observer<NewsResponse>() {
                    @Override
                    public void onChanged(NewsResponse newsResponse) {
                        if (newsResponse != null) {
                            for (Article article: newsResponse.articles) {
                                TinNewsCard tinNewsCard = new TinNewsCard(article);

                                binding.swipeView.addView(tinNewsCard);
                            }
                        }
                    }
                });

    }
}