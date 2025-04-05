package br.edu.utfpr.usandosqlite

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.edu.utfpr.usandosqlite.database.DatabaseHandler
import br.edu.utfpr.usandosqlite.databinding.ActivityMainBinding
import br.edu.utfpr.usandosqlite.entity.Cadastro

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private lateinit var banco: DatabaseHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.main)

        setButtonListeners()

        banco = DatabaseHandler(this)
    }

    private fun setButtonListeners() {
        binding.btListar.setOnClickListener {
            onBtListarClick()
        }
        binding.btAlterar.setOnClickListener {
            onBtAlterarClick()
        }
        binding.btExcluir.setOnClickListener {
            onBtExcluirClick()
        }
        binding.btIncluir.setOnClickListener {
            onBtIncluirClick()
        }
        binding.btPesquisar.setOnClickListener {
            onBtPesquisarClick()
        }
    }

    private fun onBtListarClick() {
//        val registros = banco.read()
//
//        val saida = StringBuilder()
//
//        while (registros.moveToNext()) {
//            saida.append(registros.getString(NOME))
//            saida.append(" - ")
//            saida.append(registros.getString(TELEFONE))
//            saida.append("\n")
//        }
//        Toast.makeText(this, saida.toString(), Toast.LENGTH_LONG).show()
        val intent = Intent(this, ListarActivity::class.java)
        startActivity(intent)
    }

    private fun onBtAlterarClick() {
        val cadastro =
            Cadastro(
                binding.etCod.text.toString().toInt(),
                binding.etNome.text.toString(),
                binding.etTelefone.text.toString()
            )

        banco.update(cadastro)

        Toast.makeText(this, "Registro alterado com sucesso", Toast.LENGTH_LONG).show()
    }

    private fun onBtExcluirClick() {
        banco.delete(binding.etCod.text.toString().toInt())
        Toast.makeText(this, "Registro excluído com sucesso", Toast.LENGTH_LONG).show()
    }

    private fun onBtIncluirClick() {
        val cadastro =
            Cadastro(
                0,
                binding.etNome.text.toString(),
                binding.etTelefone.text.toString()
            )

        banco.insert(cadastro)

        Toast.makeText(this, "Registro incluído com sucesso", Toast.LENGTH_LONG).show()
    }

    private fun onBtPesquisarClick() {
        val cadastro = banco.search(binding.etCod.toString().toInt())

        if (cadastro != null) {
            binding.etNome.setText(cadastro.nome)
            binding.etTelefone.setText(cadastro.telefone)
        } else {
            Toast.makeText(this, "Registro não encontrado", Toast.LENGTH_LONG).show()
        }

    }

    companion object {
        private const val ID = 0
        private const val NOME = 1
        private const val TELEFONE = 2
    }
}