package com.parishod.watomatic.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.parishod.watomatic.R;
import com.parishod.watomatic.model.GithubReleaseNotes;
import com.parishod.watomatic.network.GetReleaseNotesService;
import com.parishod.watomatic.network.RetrofitInstance;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BrandingFragment extends Fragment {
    private ImageButton githubBtn;
    private ImageButton share_layout;
    private Button watomaticSubredditBtn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_branding, container, false);

        githubBtn = view.findViewById(R.id.watomaticGithubBtn);
        share_layout = view.findViewById(R.id.share_btn);
        watomaticSubredditBtn = view.findViewById(R.id.watomaticSubredditBtn);

        share_layout.setOnClickListener(v -> launchShareIntent());

        watomaticSubredditBtn.setOnClickListener(v -> {
            String url = getString(R.string.watomatic_subreddit_url);
            startActivity(
                    new Intent(Intent.ACTION_VIEW).setData(Uri.parse(url))
            );
        });

        githubBtn.setOnClickListener(v -> {
            String url = getString(R.string.watomatic_github_url);
            startActivity(
                    new Intent(Intent.ACTION_VIEW).setData(Uri.parse(url))
            );
        });

        getGthubReleaseNotes();

        return view;
    }

    private void getGthubReleaseNotes() {
        GetReleaseNotesService releaseNotesService = RetrofitInstance.getRetrofitInstance().create(GetReleaseNotesService.class);
        Call<GithubReleaseNotes> call = releaseNotesService.getReleaseNotes();
        call.enqueue(new Callback<GithubReleaseNotes>() {
            @Override
            public void onResponse(Call<GithubReleaseNotes> call, Response<GithubReleaseNotes> response) {

            }

            @Override
            public void onFailure(Call<GithubReleaseNotes> call, Throwable t) {

            }
        });
    }

    private void launchShareIntent() {
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, getResources().getString(R.string.share_subject));
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, getResources().getString(R.string.share_app_text));
        startActivity(Intent.createChooser(sharingIntent, "Share app via"));
    }
}
