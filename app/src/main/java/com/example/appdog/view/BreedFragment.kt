package com.example.appdog.view

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.appdog.R
import com.example.appdog.databinding.FragmentBreedBinding
import com.example.appdog.viewmodel.DogViewModel


class BreedFragment : Fragment() {

    private lateinit var mBinding: FragmentBreedBinding
    private val viewModel : DogViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentBreedBinding.inflate(inflater,container,false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = BreedAdapter()
        mBinding.rv.adapter = adapter


        mBinding.rv.layoutManager =GridLayoutManager(requireContext(),1)
        viewModel.getBreedList().observe(viewLifecycleOwner) {
            it?.let {
                adapter.update(it)
            }
        }

        adapter.selectedBreed().observe(viewLifecycleOwner) {
            it?.let {
                val bundle = Bundle()
                bundle.putString("breed",it.breed)
                val imagesFragment = ImagesFragment()
                imagesFragment.arguments = bundle


                parentFragmentManager.beginTransaction()
                    .replace(R.id.frame, imagesFragment)
                    .addToBackStack(null)
                    .commit()
            }
        }

        mBinding.fabExit.setOnClickListener { showDialogExit() }
    }

    private fun launchImagesFragment(){
        val fragment = ImagesFragment()

    }

    private fun showDialogExit(){
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Salir")
        builder.setIcon(R.drawable.ic_question)
        builder.setMessage("Estas seguro que desas salir?")
        builder.setPositiveButton("SI") {dialog, _ ->
            requireActivity().finish()
            dialog.dismiss()
        }
        builder.setNegativeButton("NO") { dialog, _ ->
            dialog.dismiss()
        }
        builder.show()
    }


}