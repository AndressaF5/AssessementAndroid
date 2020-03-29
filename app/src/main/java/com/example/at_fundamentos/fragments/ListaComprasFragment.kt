package com.example.at_fundamentos.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.at_fundamentos.Adapter.ListaComprasAdapter
import com.example.at_fundamentos.Model.Estabelecimento

import com.example.at_fundamentos.R
import com.example.at_fundamentos.ViewModel.ComercioViewModel
import kotlinx.android.synthetic.main.fragment_lista_compras.*

class ListaComprasFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_lista_compras, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var comercioViewModel: ComercioViewModel? = null
        activity?.let {
            comercioViewModel = ViewModelProviders.of(it).get(ComercioViewModel::class.java)
        }

        var mercadoProdutos = comercioViewModel!!.produtosMercado
        var farmaciaProdutos = comercioViewModel!!.produtosFarmacia
        var sacolaoProdutos = comercioViewModel!!.produtosSacolao

        comercioViewModel?.apply {
            todosOsProdutos.addAll(mercadoProdutos)
            todosOsProdutos.addAll(farmaciaProdutos)
            todosOsProdutos.addAll(sacolaoProdutos)
        }

        var todosOsProdutos = comercioViewModel!!.todosOsProdutos

        var listaComprasAdapter = ListaComprasAdapter(todosOsProdutos)

        rcyVwListaCompras.adapter = listaComprasAdapter
        rcyVwListaCompras.layoutManager = LinearLayoutManager(context)

        val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
            0, ItemTouchHelper.LEFT
        ){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                var comercioViewModel: ComercioViewModel? = null
                activity?.let {
                    comercioViewModel = ViewModelProviders.of(it).get(ComercioViewModel::class.java)
                }

                val position = viewHolder.adapterPosition
                todosOsProdutos.removeAt(position)
                listaComprasAdapter.notifyItemRemoved(position)
            }
        })

        itemTouchHelper.attachToRecyclerView(rcyVwListaCompras)
    }

}
