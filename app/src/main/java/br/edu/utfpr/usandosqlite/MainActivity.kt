package br.edu.utfpr.usandosqlite

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import br.edu.utfpr.usandosqlite.database.DatabaseHandler
import br.edu.utfpr.usandosqlite.databinding.ActivityMainBinding
import br.edu.utfpr.usandosqlite.entity.Cadastro


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var banco: DatabaseHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        banco = DatabaseHandler.getInstance(this)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    fun btIncluirOnClick(view: View) {

        //validação dos campos de tela

        val cadastro = Cadastro(
            0,
            binding.etNome.text.toString(),
            binding.etTelefone.text.toString()
        )

        //acesso ao banco
        banco.inserir(cadastro)

        //apresentação da devolutiva visual para o usuário
        Toast.makeText(
            this,
            "Inclusão efetuada com Sucesso.",
            Toast.LENGTH_SHORT
        ).show()
    }

    fun btAlterarOnClick(view: View) {

        //validação dos campos de tela

        //acesso ao banco
        val cadastro = Cadastro( binding.etCod.text.toString().toInt(),
            binding.etNome.text.toString(),
            binding.etTelefone.text.toString()
        )

        banco.alterar(cadastro)


        //apresentação da devolutiva visual para o usuário
        Toast.makeText(
            this,
            "Alteração efetuada com Sucesso.",
            Toast.LENGTH_SHORT
        ).show()
    }
    fun btExcluirOnClick(view: View) {

        //validação dos campos de tela

        //acesso ao banco
        banco.excluir( binding.etCod.text.toString().toInt() )

        //apresentação da devolutiva visual para o usuário
        Toast.makeText(
            this,
            "Exclusão efetuada com Sucesso.",
            Toast.LENGTH_SHORT
        ).show()
    }
    fun btPesquisarOnClick(view: View) {

        //validação dos campos de tela

        //acesso ao banco
        val cadastro = banco.pesquisar( binding.etCod.text.toString().toInt() )

        //apresentação da devolutiva visual para o usuário
        if ( cadastro != null ) {
            binding.etNome.setText( cadastro.nome )
            binding.etTelefone.setText( cadastro.telefone )
        } else {
            binding.etNome.setText( "" )
            binding.etTelefone.setText( "" )

            Toast.makeText(
                this,
                "Registro não encontro.",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    fun btListarOnClick(view: View) {
        val intent = Intent( this, ListarActivity::class.java )
        startActivity( intent )
    }



//        //acesso ao banco
//        val registros = banco.listar()
//
//        //apresentação da devolutiva visual para o usuário
//        val saida = StringBuilder()
//
//        while ( registros.moveToNext() ) {
//            val nome = registros.getString(DatabaseHandler.COLUMN_NOME.toInt() )
//            val telefone = registros.getString( DatabaseHandler.COLUMN_TELEFONE.toInt() )
//
//            saida.append( " ${nome} - ${telefone} \n " )
//        }
//
//        Toast.makeText(
//            this,
//            saida.toString(),
//            Toast.LENGTH_SHORT
//        ).show()



} //fim da MainActivity