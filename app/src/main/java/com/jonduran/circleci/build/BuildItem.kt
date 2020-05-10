package com.jonduran.circleci.build

import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.jonduran.circleci.R
import com.jonduran.circleci.common.android.LayoutResource
import com.jonduran.circleci.common.ui.list.BaseViewHolder
import com.jonduran.circleci.common.ui.list.Item
import com.jonduran.circleci.databinding.ListItemBuildBinding
import com.jonduran.circleci.extensions.load

data class BuildItem(
    val buildUrl: String,
    val avatarUrl: String,
    val subject: String?,
    val username: String,
    val reponame: String,
    val gitSha: String,
    val duration: String,
    val branch: String?,
    val startTime: CharSequence?
) : Item {
    override val layoutId: LayoutResource = LayoutResource(R.layout.list_item_build)

    override val uniqueId: Long = buildUrl.hashCode().toLong()

    override fun bind(holder: BaseViewHolder, payloads: MutableList<Any>?) {
        with(ListItemBuildBinding.bind(holder.itemView)) {
            avatar.load(avatarUrl) {
                val resources = holder.itemView.resources
                val radius = resources.getDimensionPixelSize(R.dimen.image_corner_radius)
                transform(RoundedCorners(radius))
            }
            subjectLabel.text = subject
            slug.text = "$username/$reponame"
            revision.text = gitSha
            durationLabel.text = duration
            branchLabel.text = branch
            startTimeLabel.text = startTime
        }
    }

    override fun unbind(holder: BaseViewHolder) {
    }
}