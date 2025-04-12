package br.edu.utfpr.usandosqlite

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.edu.utfpr.usandosqlite.adapter.MeuAdapter
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
        val adapter = MeuAdapter(this, registros)

        binding.lvPrincipal.adapter = adapter
    }
}