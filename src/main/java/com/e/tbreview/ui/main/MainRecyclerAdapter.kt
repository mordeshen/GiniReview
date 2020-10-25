package com.e.tbreview.ui.main

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.Color.*
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainer
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.e.tbreview.R
import com.e.tbreview.model.ItemModel
import com.taboola.android.PublisherInfo
import com.taboola.android.Taboola
import com.taboola.android.TaboolaWidget
import kotlinx.android.synthetic.main.layout_list_item.view.*


class MainRecyclerAdapter(
    private val interaction: Interaction? = null,
    val requestManager: RequestManager
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val TAG = "MainRecyclerAdapter"

    val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ItemModel>() {

        override fun areItemsTheSame(oldItem: ItemModel, newItem: ItemModel): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: ItemModel, newItem: ItemModel): Boolean {
            return oldItem.description == newItem.description
        }

    }
    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)

    override fun getItemViewType(position: Int): Int {
        return differ.currentList[position].cellKind
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        when (viewType) {
            1 -> return ItemVH(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.layout_list_item,
                    parent,
                    false
                ),
                requestManager,
                interaction
            )
            3 -> return TaboolaFeedVH(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.layout_t_feed,
                    parent,
                    false
                ),
                interaction
            )
            2 -> return TaboolaVH(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.layout_t_widget,
                    parent,
                    false
                ),
                interaction
            )
            else -> {
                return ItemVH(
                    LayoutInflater.from(parent.context).inflate(
                        R.layout.layout_list_item,
                        parent,
                        false
                    ),
                    requestManager,
                    interaction
                )
            }
        }

    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ItemVH -> {
                holder.bind(differ.currentList[position])
            }

            is TaboolaVH -> {
                holder.bind(differ.currentList[position])
            }
            is TaboolaFeedVH -> {
                holder.bind(differ.currentList[position])
            }
        }

    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun submitList(list: List<ItemModel>) {
        differ.submitList(list)
    }

    class ItemVH
    constructor(
        itemView: View,
        private val requestManager: RequestManager,
        private val interaction: Interaction?
    ) : RecyclerView.ViewHolder(itemView) {

        @SuppressLint("SetTextI18n")
        fun bind(item: ItemModel) = with(itemView) {
            itemView.setOnClickListener {
                interaction?.onItemSelected(adapterPosition, item)
            }


            requestManager.load(item.thumbnail)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(itemView.item_image)
            itemView.item_title.setText(item.name)
            itemView.item_description.setText(item.description)

            when(item.color){
                "red" ->{itemView.setBackgroundColor(RED)}
                "green" ->{itemView.setBackgroundColor(GREEN)}
                "blue" ->{itemView.setBackgroundColor(BLUE)}
                else -> {
                    //do nothing
                     }
            }

        }
    }

    class TaboolaVH
    constructor(
        itemView: View,
        private val interaction: Interaction?
    ) : RecyclerView.ViewHolder(itemView) {


        fun bind(item: ItemModel) = with(itemView) {
            var publisherInfo: PublisherInfo =  PublisherInfo("sdk-tester");

            var taboolaWidget: TaboolaWidget = TaboolaWidget(itemView.context)

            Taboola.init(publisherInfo);

            //Assign LayoutParams to TaboolaWidget
            taboolaWidget.layoutParams =
                FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT);

//Add TaboolaWidget to your layout (This example assumes your parent layout is a FrameLayout with an arbitrary id parent_layout)

            var frameLayout : FrameLayout = findViewById(R.id.layout_widget_tab);
            frameLayout.removeAllViews()
            frameLayout.addView(taboolaWidget);

//Set the following parameters in your TaboolaWidget instance, before calling fetchContent()
            taboolaWidget.setPublisher("sdk-tester")
                .setMode("alternating-widget-without-video")
                .setPlacement("Below Article")
                .setPageUrl("https://www.google.com")
                .setPageType("article")
                .setTargetType("mix")

            when(item.color){
                "red" ->{taboolaWidget.setBackgroundColor(RED)}
                "green" ->{taboolaWidget.setBackgroundColor(GREEN)}
                "blue" ->{taboolaWidget.setBackgroundColor(BLUE)}
                else -> {
                    //do nothing
                }
            }


//fetch the content
            taboolaWidget.fetchContent();
        }

        
    }


    class TaboolaFeedVH
    constructor(
        itemView: View,
        private val interaction: Interaction?
    ) : RecyclerView.ViewHolder(itemView) {


        fun bind(item: ItemModel) = with(itemView) {
            var publisherInfo: PublisherInfo =  PublisherInfo("sdk-tester");

            var taboolaWidget: TaboolaWidget = TaboolaWidget(itemView.context)

            Taboola.init(publisherInfo);

            //Assign LayoutParams to TaboolaWidget
            taboolaWidget.layoutParams =
                FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT);

//Add TaboolaWidget to your layout (This example assumes your parent layout is a FrameLayout with an arbitrary id parent_layout)

            var frameLayout : FrameLayout = findViewById(R.id.layout_feed_tab);
            frameLayout.removeAllViews()
            frameLayout.addView(taboolaWidget);

//Set the following parameters in your TaboolaWidget instance, before calling fetchContent()
            taboolaWidget
                .setPublisher("sdk-tester")
                .setMode("thumbs-feed-01")
                .setPlacement("Feed without video")
                .setPageUrl("https://www.google.com")
                .setPageType("article").targetType = "mix"



//fetch the content
            taboolaWidget.fetchContent();

        }
    }



    interface Interaction {
        fun onItemSelected(position: Int, item: ItemModel)
    }

}
