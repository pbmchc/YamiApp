package com.example.piotrek.yami.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.piotrek.yami.data.Comment;
import com.example.piotrek.yami.data.Comments;
import com.example.piotrek.yami.data.CookBook;
import com.example.piotrek.yami.itemView.CommentItemView;
import com.example.piotrek.yami.itemView.CommentItemView_;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Piotrek on 2015-01-18.
 */
@EBean
public class CommentsListAdapter extends BaseAdapter {

    @RootContext
    Context context;

    List<Comment> comments = new ArrayList<Comment>();

    public CommentsListAdapter()
    {

    }
    @Override
    public int getCount() {
        return comments.size();
    }

    @Override
    public Comment getItem(int i) {
        return comments.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        CommentItemView commentItemView;
        if (convertView == null) {
            commentItemView = CommentItemView_.build(context);
        } else {
            commentItemView = (CommentItemView) convertView;
        }

        commentItemView.bind(getItem(position));

        return commentItemView;
    }
    public void update(Comments commlist) {

        comments.clear();

        comments.addAll(commlist.opinions);

        notifyDataSetChanged();

    }
}
