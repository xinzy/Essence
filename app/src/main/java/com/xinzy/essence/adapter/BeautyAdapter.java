package com.xinzy.essence.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.xinzy.essence.R;
import com.xinzy.essence.widget.adapter.BaseRecyclerAdapter;
import com.xinzy.essence.model.Essence;
import com.xinzy.essence.view.MainView;

import java.util.List;

/**
 * Created by Xinzy on 2017-01-17.
 */
public class BeautyAdapter extends BaseRecyclerAdapter<BeautyAdapter.BeautyHolder, Essence>
{
    private MainView.OnItemClickListener mOnItemClickListener;

    public BeautyAdapter(@NonNull List<Essence> lists)
    {
        super(lists);
    }

    public void setOnItemClickListener(MainView.OnItemClickListener onItemClickListener)
    {
        this.mOnItemClickListener = onItemClickListener;
    }

    @Override
    protected int getItemLayout(int viewType)
    {
        return R.layout.item_beauty;
    }

    @Override
    protected BeautyHolder onCreateViewHolder(@NonNull View convertView)
    {
        return new BeautyHolder(convertView);
    }

    @Override
    public void onBindViewHolder(BeautyHolder holder, int position)
    {
        holder.convert(mLists.get(position));
    }

    class BeautyHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        private ImageView img;
        private TextView text;
        private Essence mData;

        BeautyHolder(@NonNull View itemView)
        {
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.itemImg);
            text = (TextView) itemView.findViewById(R.id.itemTitle);

            img.setOnClickListener(this);
            text.setOnClickListener(this);
        }

        void convert(Essence data)
        {
            mData = data;

            Picasso.with(img.getContext()).load(data.getUrl()).placeholder(R.drawable.img_default)
                    .error(R.drawable.img_default).tag(mTag).into(img);
            text.setText(data.getContent());
        }

        @Override
        public void onClick(View v)
        {
            switch (v.getId())
            {
                case R.id.itemImg:
                    if (mOnItemClickListener != null) mOnItemClickListener.onImageClick(img, mData);
                    break;
                case R.id.itemTitle:
                    if (mOnItemClickListener != null) mOnItemClickListener.onTextClick(mData);
                    break;
            }
        }
    }
}
