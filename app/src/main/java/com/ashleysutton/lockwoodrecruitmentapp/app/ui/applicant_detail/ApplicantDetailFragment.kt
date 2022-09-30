package com.ashleysutton.lockwoodrecruitmentapp.app.ui.applicant_detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.ashleysutton.lockwoodrecruitmentapp.R
import com.ashleysutton.lockwoodrecruitmentapp.app.data.entities.Applicant
import com.ashleysutton.lockwoodrecruitmentapp.app.utils.Resource
import com.ashleysutton.lockwoodrecruitmentapp.databinding.ApplicantDetailFragmentBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.bumptech.glide.request.RequestOptions
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ApplicantDetailFragment: Fragment() {

    private lateinit var viewBinding: ApplicantDetailFragmentBinding
    private val viewModel: ApplicantDetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = ApplicantDetailFragmentBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getString("id")?.let {
            viewModel.start(it)
        }

        setUpObservers()
    }

    private fun setUpObservers() {
        viewModel.applicant.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    viewBinding.progressBar.visibility = View.GONE
                    if (it.data != null) {
                        viewBinding.name.text = it.data.name
                        viewBinding.age.text = it.data.age.toString()
                        viewBinding.company.text = it.data.company
                        viewBinding.email.text = it.data.email
                        viewBinding.mobile.text = it.data.mobile
                        viewBinding.landline.text = it.data.landline
                        viewBinding.gender.text = it.data.gender
                        viewBinding.notifications.text = it.data.notifications
                        viewBinding.experience.text = it.data.experience

                        val options = RequestOptions()
                            .centerCrop()
                            .placeholder(R.mipmap.ic_launcher_round)
                            .error(R.mipmap.ic_launcher_round)

                        val url = GlideUrl(
                            it.data.picture, LazyHeaders.Builder()
                                .addHeader("User-Agent", "your-user-agent")
                                .build()
                        )

                        Glide.with(viewBinding.root)
                            .load(url)
                            .apply(options)
                            .into(viewBinding.image)

                        setUpActionButtons(it.data)
                    }
                }
                Resource.Status.ERROR -> {
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
                Resource.Status.LOADING -> {
                    viewBinding.progressBar.visibility = View.VISIBLE
                }
            }
        })
    }

    private fun setUpActionButtons(applicant: Applicant) {
        viewBinding.callLandlineButton.setOnClickListener {
            val uri = "tel: " + applicant.landline
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse(uri)
            startActivity(intent)
        }

        viewBinding.callMobileButton.setOnClickListener {
            val uri = "tel: " + applicant.mobile
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse(uri)
            startActivity(intent)
        }

        viewBinding.emailButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/plain"
            intent.putExtra(Intent.EXTRA_EMAIL, applicant.email)

            startActivity(Intent.createChooser(intent, "Send Email"))
        }
    }
}