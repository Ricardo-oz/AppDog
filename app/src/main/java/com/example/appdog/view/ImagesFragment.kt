package com.example.appdog.view

import ImagesAdapter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.appdog.R
import com.example.appdog.databinding.FragmentImagesBinding
import com.example.appdog.viewmodel.DogViewModel

class ImagesFragment : Fragment() {

    private lateinit var binding: FragmentImagesBinding
    private val viewModel: DogViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentImagesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val breed = arguments?.getString("breed") ?: ""
        val adapter = ImagesAdapter()
        binding.tbImages.setTitle("Imagenes de razas: $breed")
        binding.rvImages.adapter = adapter
        binding.rvImages.layoutManager = GridLayoutManager(requireContext(), 1)

        // Observa las imágenes desde el ViewModel
        viewModel.getImagesByBreedFromInternet(breed)
        viewModel.getImages().observe(viewLifecycleOwner) {
            it?.let { adapter.update(it) }
        }

        // Observa la imagen seleccionada y configura el diálogo de confirmación
        adapter.selectedImage().observe(viewLifecycleOwner) { image ->
            image?.let {
                val confirmationMessage = if (image.fav) {
                    "¿Estás seguro de que quieres marcar esta imagen como favorita?"
                } else {
                    "¿Estás seguro de que quieres eliminar esta imagen de tus favoritos?"
                }

                val builder = AlertDialog.Builder(requireContext())
                builder.setTitle("Confirmación")
                builder.setMessage(confirmationMessage)
                builder.setIcon(R.drawable.ic_question)

                builder.setPositiveButton("No") { dialog, _ ->
                    // Cambia el estado de favorito después de la confirmación
                    image.fav = !image.fav

                }
                builder.setNegativeButton("SI") { dialog, _ ->
                    // Actualiza en el ViewModel
                    viewModel.updateFav(image)
                    dialog.dismiss()

                }
                builder.show()
            }
        }

        binding.fabBack.setOnClickListener { parentFragmentManager.popBackStack() }
    }
}
