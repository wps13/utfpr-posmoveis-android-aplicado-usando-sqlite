package br.edu.utfpr.usandosqlite

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
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

        initData()

        banco = DatabaseHandler(this)
    }

    private fun initData() {
        if (intent.getIntExtra("cod", 0) != 0) {
            binding.etCod.setText(intent.getIntExtra("cod", 0).toString())
            binding.etNome.setText(intent.getStringExtra("nome"))
            binding.etTelefone.setText(intent.getStringExtra("telefone"))
        } else {
            binding.btPesquisar.visibility = View.GONE
            binding.btExcluir.visibility = View.GONE
        }
    }

    private fun setButtonListeners() {
        binding.btAlterar.setOnClickListener {
            onBtAlterarClick()
        }
        binding.btExcluir.setOnClickListener {
            onBtExcluirClick()
        }
        binding.btPesquisar.setOnClickListener {
            onBtPesquisarClick()
        }
    }

    private fun onBtAlterarClick() {

        if (binding.etCod.text.toString().isEmpty()) {
            _includeCadastro()
        } else {
            val cadastro =
                Cadastro(
                    binding.etCod.text.toString().toInt(),
                    binding.etNome.text.toString(),
                    binding.etTelefone.text.toString()
                )

            banco.update(cadastro)

            Toast.makeText(this, "Registro alterado com sucesso", Toast.LENGTH_LONG).show()
        }
        finish()
    }

    private fun onBtExcluirClick() {
        banco.delete(binding.etCod.text.toString().toInt())
        Toast.makeText(this, "Registro excluído com sucesso", Toast.LENGTH_LONG).show()
        finish()
    }

    private fun _includeCadastro() {
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
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Código da pessoa")
        val etCodPesquisa = EditText(this)
        builder.setView(etCodPesquisa)
        builder.setCancelable(false)
        builder.setNegativeButton("Fechar", null)
        builder.setPositiveButton("Pesquisar", { dialogInterface, i ->
            val cadastro = banco.search(etCodPesquisa.text.toString().toInt())

            if (cadastro != null) {
                binding.etCod.setText(etCodPesquisa.text.toString())
                binding.etNome.setText(cadastro.nome)
                binding.etTelefone.setText(cadastro.telefone)
            } else {
                Toast.makeText(this, "Registro não encontrado", Toast.LENGTH_LONG).show()
            }
        })
        builder.show()
    }
}