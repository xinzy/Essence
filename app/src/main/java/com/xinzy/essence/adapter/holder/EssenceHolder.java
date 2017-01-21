package com.xinzy.essence.adapter.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xinzy.essence.R;
import com.xinzy.essence.model.Essence;
import com.xinzy.essence.util.DateUtils;
import com.xinzy.essence.widget.OnViewEventListener;

/**
 * Created by xinzy on 17/1/21.
 */
public class EssenceHolder extends RecyclerView.ViewHolder implements View.OnClickListener
{
    public static final short EVENT_CONTAINER_CLICKED = 0x100;

    private LinearLayout mContainer;
    private TextView     titleText;
    private TextView     timeText;
    private TextView     authorText;

    private Essence mEssence;

    private OnViewEventListener mOnViewEventListener;

    public EssenceHolder(View itemView)
    {
        super(itemView);
        mContainer = (LinearLayout) itemView.findViewById(R.id.essenceContainer);
        titleText = (TextView) itemView.findViewById(R.id.itemTitle);
        timeText = (TextView) itemView.findViewById(R.id.timeText);
        authorText = (TextView) itemView.findViewById(R.id.authorText);
        mContainer.setOnClickListener(this);
    }

    public void setOnViewEventListener(OnViewEventListener l)
    {
        this.mOnViewEventListener = l;
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
        case R.id.essenceContainer:
            if (mOnViewEventListener != null)
            {
                mOnViewEventListener.onViewEvent(mContainer, EVENT_CONTAINER_CLICKED, mEssence);
            }
            break;
        }
    }

    public void convert(Essence essence)
    {
        mEssence = essence;
        titleText.setText(essence.getContent());
        authorText.setText(essence.getWho());
        timeText.setText(DateUtils.format(essence.getCreatedAt()));
    }
}
