package com.ashleysutton.lockwoodrecruitmentapp.app.ui.applicants

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ashleysutton.lockwoodrecruitmentapp.R
import com.ashleysutton.lockwoodrecruitmentapp.app.data.entities.Applicant
import com.ashleysutton.lockwoodrecruitmentapp.databinding.ListItemApplicantBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.bumptech.glide.request.RequestOptions


/**
 * Responsible for adapting the data from db into the Applicants list view
 *
 * This class uses resource binding which is discussed here:
 * https://developer.android.com/topic/libraries/data-binding
 */

class ApplicantsAdapter(private val listener: ApplicantItemListener) :
    RecyclerView.Adapter<ApplicantViewHolder>() {

    interface ApplicantItemListener {
        fun onApplicantClicked(applicantId: String)
    }

    private val items = ArrayList<Applicant>()

    fun setItems(items: ArrayList<Applicant>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ApplicantViewHolder {
        val binding: ListItemApplicantBinding = ListItemApplicantBinding.inflate(
            LayoutInflater.from(
                parent.context
            ), parent, false
        )
        return ApplicantViewHolder(binding, listener)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ApplicantViewHolder, position: Int) = holder.bind(items[position])
}

class ApplicantViewHolder(
    private val itemBinding: ListItemApplicantBinding,
    private val listener: ApplicantsAdapter.ApplicantItemListener
) : RecyclerView.ViewHolder(itemBinding.root),
    View.OnClickListener {

    private lateinit var applicant: Applicant

    init {
        itemBinding.root.setOnClickListener(this)
    }

    fun bind(item: Applicant) {
        this.applicant = item
        itemBinding.name.text = item.name
        itemBinding.age.text = item.age.toString()
        itemBinding.company.text = item.company

        val options = RequestOptions()
            .centerCrop()
            .placeholder(R.mipmap.ic_launcher_round)
            .error(R.mipmap.ic_launcher_round)

        val url = GlideUrl(
                item.picture, LazyHeaders.Builder()
                .addHeader("User-Agent", "your-user-agent")
                .build()
        )

        Glide.with(itemBinding.root)
            .load(url)
            .apply(options)
            .into(itemBinding.picture)
    }

    override fun onClick(v: View?) {
        listener.onApplicantClicked(applicant.id)
    }
}