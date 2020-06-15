package com.larry.tinnews.ui.home;

import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.larry.tinnews.R;
import com.larry.tinnews.model.Article;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;
import com.mindorks.placeholderview.annotations.swipe.SwipeCancelState;
import com.mindorks.placeholderview.annotations.swipe.SwipeIn;
import com.mindorks.placeholderview.annotations.swipe.SwipeOut;

@Layout(R.layout.tin_news_card)
public class TinNewsCard {
    @View(R.id.news_image)
    private ImageView image;

    @View(R.id.news_title)
    private TextView newsTitle;

    @View(R.id.news_description)
    private TextView newsDescription;

    private final Article article;

    public TinNewsCard(Article news) {
        this.article = news;
    }

    @Resolve
    private void onResolved() {
        newsTitle.setText(article.title);
        newsDescription.setText(article.description);
    }

    @SwipeOut
    private void onSwipedOut() {
        Log.d("EVENT", "onSwipedOut");
    }

    @SwipeCancelState
    private void onSwipeCancelState() {
        Log.d("EVENT", "onSwipeCancelState");
    }

    @SwipeIn
    private void onSwipeIn() {
        Log.d("EVENT", "onSwipedIn");
    }

}
