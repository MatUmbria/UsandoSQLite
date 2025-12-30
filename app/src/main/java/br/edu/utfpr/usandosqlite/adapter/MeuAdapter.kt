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

class MeuAdapter(val context: Context, val cursor: Cursor): BaseAdapter() {

    override fun getCount(): Int {
        return cursor.count
    }

    override fun getItem(pos: Int): Any? {
        cursor.moveToPosition(pos)

        val cadastro = Cadastro(
            cursor.getInt(DatabaseHandler.COLUMN_ID.toInt() ),
            cursor.getString(DatabaseHandler.COLUMN_NOME.toInt() ),
            cursor.getString(DatabaseHandler.COLUMN_TELEFONE.toInt() )
        )

        return cadastro
    }

    override fun getItemId(pos: Int): Long {
        cursor.moveToPosition(pos)
        return cursor.getInt(DatabaseHandler.COLUMN_ID.toInt() ).toLong()
    }

    override fun getView(
        pos: Int,
        p1: View?,
        p2: ViewGroup?
    ): View? {

        //recupera a instancia do nosso elemento lista (container com dados de cada elemento da lista)
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val v = inflater.inflate(R.layout.elemento_lista, null)

        //recupera os componentes visuais da tela
        val tvNomeElementoLista = v.findViewById<TextView>(R.id.tvNomeElementoLista)
        val tvTelefoneElementoLista = v.findViewById<TextView>(R.id.tvTelefoneElementoLista)
        val btEditarElementoLista = v.findViewById<ImageButton>( R.id.btEditarElementoLista )

        cursor.moveToPosition(pos)

        tvNomeElementoLista.text = cursor.getString(DatabaseHandler.COLUMN_NOME.toInt() )
        tvTelefoneElementoLista.text = cursor.getString(DatabaseHandler.COLUMN_TELEFONE.toInt() )

        btEditarElementoLista.setOnClickListener {
            val intent = Intent(context, MainActivity::class.java)

            //buscando os dados do banco de dados para editar
            cursor.moveToPosition(pos)

            intent.putExtra("cod", cursor.getInt(DatabaseHandler.COLUMN_ID.toInt()))
            intent.putExtra("nome", cursor.getString(DatabaseHandler.COLUMN_NOME.toInt()))
            intent.putExtra("telefone", cursor.getString(DatabaseHandler.COLUMN_TELEFONE.toInt()))

            context.startActivity(intent)
        }

        return v
    }

}