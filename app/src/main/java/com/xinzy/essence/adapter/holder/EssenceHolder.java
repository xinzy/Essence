package com.xinzy.essence.adapter.holder;

import android.view.View;
import android.widget.TextView;

import com.xinzy.essence.R;
import com.xinzy.essence.model.Essence;
import com.xinzy.essence.util.DateUtils;
import com.xinzy.essence.widget.OnViewEventListener;
import com.xinzy.essence.widget.adapter.MultiTypeAdapter;

/**
 * Created by xinzy on 17/1/21.
 */
public class EssenceHolder extends MultiTypeAdapter.ViewHolder<Essence> implements View.OnClickListener
{
    public static final short EVENT_CONTAINER_CLICKED = 0x100;

    private TextView     titleText;
    private TextView     timeText;
    private TextView     authorText;

    private Essence mEssence;

    private OnViewEventListener mOnViewEventListener;

    public EssenceHolder(View itemView)
    {
        super(itemView);
        titleText = get(R.id.itemTitle);
        timeText = get(R.id.timeText);
        authorText = get(R.id.authorText);
        mRootView.setOnClickListener(this);
    }

    public void setOnViewEventListener(OnViewEventListener l)
    {
        this.mOnViewEventListener = l;
    }

    @Override
    public void onClick(View v)
    {
        if (mOnViewEventListener != null)
        {
            mOnViewEventListener.onViewEvent(mRootView, EVENT_CONTAINER_CLICKED, mEssence);
        }
    }

    @Override
    public void convert(Essence essence)
    {
        mEssence = essence;
        titleText.setText(essence.getContent());
        authorText.setText(essence.getWho());
        timeText.setText(DateUtils.format(essence.getCreatedAt()));
    }
}
