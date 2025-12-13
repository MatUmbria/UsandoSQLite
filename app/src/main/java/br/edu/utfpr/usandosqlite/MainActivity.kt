package br.edu.utfpr.usandosqlite

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import br.edu.utfpr.usandosqlite.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var banco: SQLiteDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        banco = openOrCreateDatabase(
            "bdfile.sqlite",
            MODE_PRIVATE,
            null
        )

        banco.execSQL("CREATE TABLE IF NOT EXISTS cadastro (_id INTEGER PRIMARY KEY AUTOINCREMENT, " + "nome TEXT, telefone TEXT)")


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    fun btIncluirOnClick(view: View) {
        val registro = ContentValues()
        registro.put("nome", binding.etNome.text.toString())
        registro.put("telefone", binding.etTelefone.text.toString())
        banco.insert("cadastro", null, registro)
        Toast.makeText(
            this,
            "Registro inserido com sucesso",
            Toast.LENGTH_SHORT
        ).show()
    }
    fun btAlterarOnClick(view: View) {
        val registro = ContentValues()
        registro.put("nome", binding.etNome.text.toString())
        registro.put("telefone", binding.etTelefone.text.toString())
        banco.update("cadastro", registro, "_id = ${binding.etCod.text.toString()}", null)
        Toast.makeText(
            this,
            "Registro alterado com sucesso",
            Toast.LENGTH_SHORT
        ).show()
    }
    fun btExcluirOnClick(view: View) {
        banco.delete("cadastro", "_id = ${binding.etCod.text.toString()}", null)
        Toast.makeText(
            this,
            "Registro excluído com sucesso",
            Toast.LENGTH_SHORT
        ).show()
    }
    fun btPesquisarOnClick(view: View) {
        val registro = banco.query(
            "cadastro",
            null,
            "_id = ${binding.etCod.text.toString()}",
            null,
            null,
            null,
            null
        )
        if (registro.moveToNext()) {
            val nome = registro.getString(1)
            val telefone = registro.getString(2)
            binding.etNome.setText(nome)
            binding.etTelefone.setText(telefone)
        } else {
            binding.etNome.setText("")
            binding.etTelefone.setText("")
            Toast.makeText(
                this,
                "Registro não encontrado",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
    fun btListarOnClick(view: View) {
        val registros = banco.query(
            "cadastro",
            null,
            null,
            null,
            null,
            null,
            null
        )

        val saida = StringBuilder()

        while (registros.moveToNext()) {
            val nome = registros.getString(1)
            val telefone = registros.getString(2)
            saida.append("$nome - $telefone\n")
        }
        Toast.makeText(
            this,
            saida.toString(),
            Toast.LENGTH_SHORT
        ).show()
    }
}