package br.edu.utfpr.usandosqlite

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
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

    override fun onStart(){
        super.onStart()
        initView()
    }

    private fun initView() {
        if ( intent.getIntExtra("cod", 0) != 0) {
            binding.etCod.setText( intent.getIntExtra("cod", 0).toString() )
            binding.etNome.setText( intent.getStringExtra("nome") )
            binding.etTelefone.setText( intent.getStringExtra("telefone") )
        } else {
            //tratar inclusão (novo registro)
            binding.btExcluir.visibility = View.GONE
            binding.btPesquisar.visibility = View.GONE
        }
    }

    fun btSalvarOnClick(view: View) {

        //validação dos campos de tela

        //acesso ao banco

        var msg = ""

        if (binding.etCod.text.toString().isEmpty()){
            //incluir
            val cadastro = Cadastro(
                0,
                binding.etNome.text.toString(),
                binding.etTelefone.text.toString()
            )

            //acesso ao banco
            banco.inserir(cadastro)
            msg = "Inclusão efetuada com Sucesso."
        } else {
            //alterar
            val cadastro = Cadastro( binding.etCod.text.toString().toInt(),
                binding.etNome.text.toString(),
                binding.etTelefone.text.toString()
            )

            banco.alterar(cadastro)
            msg = "Alteração efetuada com sucesso."
        }



        //apresentação da devolutiva visual para o usuário
        Toast.makeText(
            this,
            msg,
            Toast.LENGTH_SHORT
        ).show()

        finish()
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

        finish()
    }
    fun btPesquisarOnClick(view: View) {

        //validação dos campos de tela

        //acesso ao banco

        val etCodPesquisar = EditText(this)

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Digite o código")
        builder.setView(etCodPesquisar)
        builder.setCancelable(false)
        builder.setNegativeButton("Fechar", null)
        builder.setPositiveButton(
            "Pesquisar",
            { dialog, which ->
                val cadastro: Cadastro? = banco.pesquisar( etCodPesquisar.text.toString().toInt() )

                //apresentação da devolutiva visual para o usuário
                if ( cadastro != null ) {
                    binding.etCod.setText( etCodPesquisar.text.toString() )
                    binding.etNome.setText( cadastro.nome )
                    binding.etTelefone.setText( cadastro.telefone )
                } else {
                    binding.etNome.setText( "" )
                    binding.etTelefone.setText( "" )

                    Toast.makeText(
                        this,
                        "Registro não encontrado.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        )
        builder.show()




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