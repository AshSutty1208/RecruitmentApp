package com.ashleysutton.lockwoodrecruitmentapp.app.ui.applicants

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ashleysutton.lockwoodrecruitmentapp.R
import com.ashleysutton.lockwoodrecruitmentapp.app.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import com.ashleysutton.lockwoodrecruitmentapp.databinding.ApplicantsFragmentBinding
import kotlinx.coroutines.flow.collect
import javax.inject.Inject


@AndroidEntryPoint
class ApplicantsFragment: Fragment(), ApplicantsAdapter.ApplicantItemListener {
    private lateinit var viewBinding: ApplicantsFragmentBinding
    private val viewModel: ApplicantsViewModel by viewModels()
    private lateinit var adapter: ApplicantsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = ApplicantsFragmentBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupObservers()
    }

    private fun setupRecyclerView() {
        adapter = ApplicantsAdapter(this)
        viewBinding.applicantsRv.layoutManager = LinearLayoutManager(requireContext())
        viewBinding.applicantsRv.adapter = adapter
    }

    private fun setupObservers() {
        viewModel.applicants.observe(viewLifecycleOwner, Observer {
            when(it.status) {
                Resource.Status.SUCCESS -> {
                    viewBinding.progressBar.visibility = View.GONE
                    if (!it.data.isNullOrEmpty()) {
                        adapter.setItems(ArrayList(it.data))
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

    override fun onApplicantClicked(applicantId: String) {
        findNavController().navigate(
            R.id.action_applicantsFragment_to_applicantDetailsFragment,
            bundleOf("id" to applicantId)
        )
    }
}