package com.xinzy.essence.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.xinzy.essence.R;
import com.xinzy.essence.base.BaseRecycleAdapter;
import com.xinzy.essence.model.Essence;

import java.util.List;

/**
 * Created by xinzy on 17/1/17.
 */

public class CategoryAdapter extends BaseRecycleAdapter<CategoryAdapter.ViewHolder, Essence>
{

    public CategoryAdapter(@NonNull List<Essence> lists)
    {
        super(lists);
    }

    @Override
    protected int getItemLayout(int viewType)
    {
        return R.layout.item_essence;
    }

    @Override
    protected ViewHolder onCreateViewHolder(@NonNull View convertView)
    {
        return new ViewHolder(convertView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {
        holder.convert(mLists.get(position));
    }

    class ViewHolder extends RecyclerView.ViewHolder
    {
        private TextView text;


        public ViewHolder(View itemView)
        {
            super(itemView);
            text = (TextView) itemView.findViewById(R.id.itemTitle);
        }

        void convert(Essence essence)
        {
            text.setText(essence.getContent());
        }
    }
}
