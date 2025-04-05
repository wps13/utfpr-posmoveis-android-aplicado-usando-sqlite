package br.edu.utfpr.usandosqlite

import android.os.Bundle
import android.widget.SimpleCursorAdapter
import androidx.appcompat.app.AppCompatActivity
import br.edu.utfpr.usandosqlite.database.DatabaseHandler
import br.edu.utfpr.usandosqlite.databinding.ActivityListarBinding

class ListarActivity : AppCompatActivity() {
    private lateinit var binding: ActivityListarBinding
    private lateinit var banco: DatabaseHandler
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityListarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        banco = DatabaseHandler(this)

        val registros = banco.read()

        val adapter = SimpleCursorAdapter(
            this,
            android.R.layout.simple_list_item_2,
            registros,
            arrayOf("nome", "telefone"),
            intArrayOf(android.R.id.text1, android.R.id.text2),
            0,
        )

        binding.lvPrincipal.adapter = adapter
    }
}