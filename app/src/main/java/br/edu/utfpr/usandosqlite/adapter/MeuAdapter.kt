package br.edu.utfpr.usandosqlite.adapter

import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageButton
import android.widget.TextView
import br.edu.utfpr.usandosqlite.MainActivity
import br.edu.utfpr.usandosqlite.R
import br.edu.utfpr.usandosqlite.database.DatabaseHandler
import br.edu.utfpr.usandosqlite.entity.Cadastro

class MeuAdapter(var context: Context, var cursor: Cursor) : BaseAdapter() {
    override fun getCount(): Int {
        return cursor.count
    }

    override fun getItem(position: Int): Any {
        cursor.moveToPosition(position)

        val cadastro = Cadastro(
            cursor.getInt(DatabaseHandler.ID).toInt(),
            cursor.getString(DatabaseHandler.NOME).toString(),
            cursor.getString(DatabaseHandler.TELEFONE).toString()
        )
        return cadastro
    }

    override fun getItemId(position: Int): Long {
        cursor.moveToPosition(position)
        return cursor.getInt(0).toLong()
    }

    override fun getView(position: Int, p1: View?, p2: ViewGroup?): View {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        val elementoLista = inflater.inflate(R.layout.elemento_lista, null)

        val tvNomeElementoLista = elementoLista.findViewById<TextView>(R.id.tvNomeElementoLista)
        val tvTelefoneElementoLista =
            elementoLista.findViewById<TextView>(R.id.tvTelefoneElementoLista)
        val btEditarElementoLista =
            elementoLista.findViewById<ImageButton>(R.id.btEditarElementoLista)

        cursor.moveToPosition(position)

        tvNomeElementoLista.text = cursor.getString(DatabaseHandler.NOME)
        tvTelefoneElementoLista.text = cursor.getString(DatabaseHandler.TELEFONE)

        btEditarElementoLista.setOnClickListener {
            val intent = Intent(context, MainActivity::class.java)

            cursor.moveToPosition(position)

            intent.putExtra("cod", cursor.getInt(DatabaseHandler.ID))
            intent.putExtra("nome", cursor.getString(DatabaseHandler.NOME))
            intent.putExtra("telefone", cursor.getString(DatabaseHandler.TELEFONE))

            context.startActivity(intent)
        }

        return elementoLista

    }
}