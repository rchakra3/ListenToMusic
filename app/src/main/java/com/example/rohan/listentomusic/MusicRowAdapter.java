package com.example.rohan.listentomusic;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.jreddit.entity.Submission;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.urx.android.AndroidClient;
import com.urx.core.ApiException;
import com.urx.core.ClientConfig;
import com.urx.core.ResponseHandler;
import com.urx.core.search.SearchResult;
import com.urx.core.search.SearchResults;
import com.urx.core.search.query.Query;
import com.urx.widget.SearchButton;

import org.sonatype.guice.bean.containers.Main;

import java.util.List;

import static com.urx.android.resolve.AndroidResolver.installedApps;
import static com.urx.core.search.query.QueryBuilder.term;

/**
 * Created by Rohan on 17/05/2015.
 */

public class MusicRowAdapter extends ArrayAdapter<Submission> {
    private final Context context;
    private final List<Submission> postList;

    public MusicRowAdapter(Context context, List<Submission> postList) {
        super(context, R.layout.single_row, postList);
        this.context = context;
        this.postList = postList;
    }

    @Override
    public View getView(int position, View rowView, ViewGroup parent) {

        if(rowView==null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.single_row, parent, false);
        }
            TextView topTextView = (TextView) rowView.findViewById(R.id.topText);
            final SearchButton button = (SearchButton) rowView.findViewById(R.id.urx_music_button);

            ClientConfig config = new ClientConfig("X9eWmhr8CScV2NV009P9jjyQyjPsD2mpANR3qslSZMUMqCAZ83lvNWxoqTz5X2s=.X9eWmhr8CScV2NV009P9jjyQyjPsD2mp", context);
            final AndroidClient client = new AndroidClient(config);
            final ImageView image = (ImageView) rowView.findViewById(R.id.postImage);
            //textView.setText(values[position]);

                topTextView.setText(postList.get(position).getTitle());

                Query q = term(postList.get(position).getTitle());

                // Execute the query asynchronously
                client.query(q, new ResponseHandler<SearchResults>() {
                    @Override
                    public void onSuccess(SearchResults results) {
                        // Handle the search results here
                        // Update our views using the first search result
                        final SearchResult firstResult = results.getResults().get(0);
                        //button.setText(firstResult.getPotentialAction().getType() + " " + firstResult.getName());
                        /*Picasso.with(context)
                                .load(firstResult.getImage())
                                .into(image, new Callback.EmptyCallback() {
                                    @Override
                                    public void onSuccess() {
                                        // Don't show the container until the image is loaded
                                        //container.setVisibility(View.VISIBLE);
                                    }
                                });*/
                   /* Log.d("Get Name from result", firstResult.getName());
                    Log.d("Get Image from result", firstResult.getImage());
                    Log.d("Get Description from result", firstResult.getDescription());
                    Log.d("Get Call To Action from result", firstResult.getPotentialAction.getName());*/
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                client.resolve(firstResult, installedApps(context));
                            }
                        });
                    }

                    @Override
                    public void onFailure(ApiException failure) {
                        // Handle any API failures here
                    }
                });
        return rowView;
    }
}
