package com.yiyi.testrlsdk.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.yiyi.testrlsdk.R
import com.yiyikeji.oauth.bean.RewardItem

class RewardsAdapter(private val rewardsList: MutableList<RewardItem>?) :
    RecyclerView.Adapter<RewardsAdapter.RewardsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RewardsViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.reward_item_layout, parent, false)
        return RewardsViewHolder(view)
    }


    override fun onBindViewHolder(holder: RewardsViewHolder, position: Int) {
        val rewardItem = rewardsList?.get(position)
        if (rewardItem != null) {
            holder.bind(rewardItem)
        }
    }

    override fun getItemCount(): Int = rewardsList?.size ?: 0

    class RewardsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val typeLabel: AppCompatTextView = itemView.findViewById(R.id.tv_type_label)
        private val createdAt: AppCompatTextView = itemView.findViewById(R.id.tv_created_at)
        private val points: AppCompatTextView = itemView.findViewById(R.id.tv_points)


        @SuppressLint("SetTextI18n", "ResourceAsColor")
        fun bind(rewardItem: RewardItem) {
            typeLabel.text = rewardItem.type_label
            createdAt.text = rewardItem.created_at
            points.text = rewardItem.points
            if (rewardItem.type == 1) {
                points.setTextColor(itemView.context.getColor(R.color.FF0000))
                points.text = "+${rewardItem.points}"
            } else {
                points.setTextColor(itemView.context.getColor(R.color.teal_200))
                points.text = "-${rewardItem.points}"
            }

        }
    }
}