/**
 * Created by Mani Shankar on 02-08-2021.
 */

package com.example.imagestraightening

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.imagestraightening.databinding.ItemRulerInvincibleBinding
import com.example.imagestraightening.databinding.ItemRulerInvincibleWideBinding
import com.example.imagestraightening.databinding.ItemRulerLargeBinding
import com.example.imagestraightening.databinding.ItemRulerSmallBinding


class RulerAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val rulerList = feed200Items()


    class LargeViewHolder(itemBinding: ItemRulerLargeBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        val binding = itemBinding

    }

    class SmallViewHolder(itemBinding: ItemRulerSmallBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        val binding = itemBinding

    }

    class InvincibleViewHolder(itemBinding: ItemRulerInvincibleBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        val binding = itemBinding
    }

    class InvincibleWideViewHolder(itemBinding: ItemRulerInvincibleWideBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        val binding = itemBinding

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            RulerType.LARGE.type -> {
                LargeViewHolder(
                    DataBindingUtil.inflate(
                        LayoutInflater.from(parent.context),
                        R.layout.item_ruler_large,
                        parent,
                        false
                    )
                )
            }

            RulerType.SMALL.type -> {
                SmallViewHolder(
                    DataBindingUtil.inflate(
                        LayoutInflater.from(parent.context),
                        R.layout.item_ruler_small,
                        parent,
                        false
                    )
                )
            }
            RulerType.INVINCIBLE_WIDE.type -> {
                InvincibleWideViewHolder(
                    DataBindingUtil.inflate(
                        LayoutInflater.from(parent.context),
                        R.layout.item_ruler_invincible_wide,
                        parent,
                        false
                    )
                )
            }
            else -> {
                InvincibleViewHolder(
                    DataBindingUtil.inflate(
                        LayoutInflater.from(parent.context),
                        R.layout.item_ruler_invincible,
                        parent,
                        false
                    )
                )
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when (getItemViewType(position)) {
            RulerType.LARGE.type -> {

            }
            else -> {

            }
        }
    }

    override fun getItemCount(): Int {
        return rulerList.size
    }

    override fun getItemViewType(position: Int): Int {

        return rulerList[position].itemType.type
    }

    fun updateRulerList(count : Int) {
        rulerList.clear()

        if(count == 100)
        {
            rulerList.addAll(feed100Items())
        }
        else
        {
            rulerList.addAll(feed200Items())
        }

        notifyDataSetChanged()
    }

    fun getRulerListSize() : Int = rulerList.size

    private fun feed200Items(): ArrayList<RulerModel> {

        val list: ArrayList<RulerModel> = ArrayList()

        for (i in -100..-1) {
            list.add(
                RulerModel(
                    get200ItemType(i)
                )
            )
        }

        list.add(RulerModel(RulerType.LARGE))

        for (i in 1..100) {
            list.add(
                RulerModel(
                    get200ItemType(i)
                )
            )
        }

        return list
    }

    private fun feed100Items() : ArrayList<RulerModel> {
        val list: ArrayList<RulerModel> = ArrayList()

        list.add(RulerModel(RulerType.LARGE))

        for (i in 1..100) {
            list.add(
                RulerModel(
                    get100ItemType(i)
                )
            )
        }
        return list
    }

    private fun get100ItemType(i: Int): RulerType {

        val largeArray = arrayOf(0, 25, 50, 75, 100)
        val smallArray = arrayOf(5, 10, 15, 20, 30, 35, 40, 45, 55, 60, 65, 70, 80, 85, 90, 95)

        return when {
            largeArray.contains(kotlin.math.abs(i)) -> {
                RulerType.LARGE
            }
            smallArray.contains(kotlin.math.abs(i)) -> {
                RulerType.SMALL
            }
            else -> {
                RulerType.INVINCIBLE_WIDE
            }
        }
    }


    private fun get200ItemType(i: Int): RulerType {

        val fiftiesArray = arrayOf(0, 25, 50, 75, 100)
        val tensArray = arrayOf(5, 10, 15, 20, 30, 35, 40, 45, 55, 60, 65, 70, 80, 85, 90, 95)

        return when {
            fiftiesArray.contains(kotlin.math.abs(i)) -> {
                RulerType.LARGE
            }
            tensArray.contains(kotlin.math.abs(i)) -> {
                RulerType.SMALL
            }
            else -> {
                RulerType.INVINCIBLE
            }
        }
    }

    data class RulerModel(
        val itemType: RulerType
    )

    enum class RulerType(val type: Int) {
        INVINCIBLE(0),
        INVINCIBLE_WIDE(1),
        SMALL(2),
        LARGE(3)
    }
}