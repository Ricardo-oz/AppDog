import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.appdog.R
import com.example.appdog.databinding.ItemImagesBinding
import com.example.appdog.model.local.DogsImages

class ImagesAdapter : RecyclerView.Adapter<ImagesAdapter.VH>() {

    var listImages = listOf<DogsImages>()
    val selectedImage = MutableLiveData<DogsImages>()

    fun update(list: List<DogsImages>) {
        listImages = list
        notifyDataSetChanged()
    }

    fun selectedImage(): LiveData<DogsImages> = selectedImage

    inner class VH(private val binding: ItemImagesBinding) :
        RecyclerView.ViewHolder(binding.root), View.OnLongClickListener {

        fun bind(image: DogsImages) {
            // Cargar la imagen del perro
            Glide.with(binding.root)
                .load(image.imageUrl)
                .fitCenter()
                .into(binding.imgDog)

            // Configurar el ícono de favorito según el estado de 'fav'
            updateFavoriteIcon(image.fav)

            // Configurar el click listener para el ícono de favorito
            binding.imgFav.setOnClickListener {
                // Cambiar el estado de favorito
                image.fav = !image.fav
                // Actualizar el ícono
                updateFavoriteIcon(image.fav)
                // Notificar el cambio al observador si es necesario
                selectedImage.value = image
            }

            // Configurar el LongClickListener para el itemView
            itemView.setOnLongClickListener(this)
        }

        private fun updateFavoriteIcon(isFavorite: Boolean) {
            if (isFavorite) {
                binding.imgFav.setImageResource(R.drawable.ic_favorite)
            } else {
                binding.imgFav.setImageResource(R.drawable.ic_no_favorite)
            }
        }

        override fun onLongClick(v: View?): Boolean {
            selectedImage.value = listImages[adapterPosition]
            return true
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(ItemImagesBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun getItemCount(): Int = listImages.size

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(listImages[position])
    }
}
