package com.example.anitron.ui.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.anitron.data.datasource.Movie
import com.example.anitron.databinding.MovieBinding
import com.example.anitron.ui.activity.MovieInfoActivity

class MovieRecyclerAdapter(): RecyclerView.Adapter<MovieRecyclerAdapter.MovieViewHolder>() {
    var movies = mutableListOf<Movie>()


    fun setMovieList(movies: List<Movie>) {
        this.movies = movies.toMutableList()
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): MovieViewHolder {
        val binding = MovieBinding.inflate(
        LayoutInflater.from(parent.context),parent,false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movies[position]
        holder.bind(movie)
    }

    override fun getItemCount(): Int {
        return movies.size
    }


    inner  class MovieViewHolder(private val binding: MovieBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(movieInfo: Movie){

            binding.title.text = movieInfo.title
            binding.year.text = movieInfo.year

            binding.root.setOnClickListener{
                v ->
                val intent = Intent(v.context, MovieInfoActivity::class.java)
                val movieId = movieInfo.imdbID
                intent.putExtra("imdbId", movieId)
                v.context.startActivity(intent)
            }

            Glide.with(binding.poster.context).load(movieInfo.poster).into(binding.poster)

        }

    }

}