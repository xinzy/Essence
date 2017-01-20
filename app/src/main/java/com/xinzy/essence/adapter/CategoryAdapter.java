package com.xinzy.essence.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.View;
import android.widget.TextView;

import com.xinzy.essence.R;
import com.xinzy.essence.widget.adapter.BaseRecycleAdapter;
import com.xinzy.essence.model.Essence;
import com.xinzy.essence.view.CategoryView;

import java.util.List;

/**
 * Created by xinzy on 17/1/17.
 */

public class CategoryAdapter extends BaseRecycleAdapter<CategoryAdapter.ViewHolder, Essence>
{
    private CategoryView.OnItemClickListener mOnItemClickListener;

    public CategoryAdapter(@NonNull List<Essence> lists)
    {
        super(lists);
    }

    public void setOnItemClickListener(CategoryView.OnItemClickListener l)
    {
        this.mOnItemClickListener = l;
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

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        private TextView text;
        private Essence mEssence;

        ViewHolder(View itemView)
        {
            super(itemView);
            text = (TextView) itemView.findViewById(R.id.itemTitle);
            text.setOnClickListener(this);
        }

        @Override
        public void onClick(View v)
        {
            switch (v.getId())
            {
                case R.id.itemTitle:
                    if (mOnItemClickListener != null) mOnItemClickListener.onItemClick(mEssence);
                    break;
            }
        }

        void convert(Essence essence)
        {
            mEssence = essence;

            String info = new StringBuffer().append(" (via.").append(essence.getWho()).append(')').toString();
            SpannableString string = new SpannableString(info);
            string.setSpan(new ForegroundColorSpan(0xFF999999), 0, info.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            string.setSpan(new RelativeSizeSpan(0.8f), 0, info.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            SpannableStringBuilder builder = new SpannableStringBuilder(essence.getContent());
            builder.append(string);
            text.setText(builder);
        }
    }
}
