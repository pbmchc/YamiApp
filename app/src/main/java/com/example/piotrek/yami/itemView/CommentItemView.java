package com.example.piotrek.yami.itemView;

import android.content.Context;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.piotrek.yami.R;
import com.example.piotrek.yami.data.Comment;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

/**
 * Created by Piotrek on 2015-01-18.
 */
@EViewGroup(R.layout.comment_item_layout)
public class CommentItemView extends RelativeLayout
{


        @ViewById
        TextView commAuth;

        @ViewById
        TextView commDate;

        @ViewById
        TextView commText;

        public CommentItemView(Context context) {
            super(context);
        }

        public void bind(Comment comment) {
            //commAuth.setText(String.valueOf(comment.ownerId));
            commDate.setText(comment.created);
            commText.setText(comment.text);

        }

    }
