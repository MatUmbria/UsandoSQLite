package br.edu.utfpr.usandosqlite.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import br.edu.utfpr.usandosqlite.entity.Cadastro

class DatabaseHandler private constructor (context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION){

    companion object {
        val DATABASE_VERSION = 1
        val DATABASE_NAME = "bdfile.sqlite"
        val TABLE_NAME = "cadastro"
        val COLUMN_ID = "0"
        val COLUMN_NOME = "0"
        val COLUMN_TELEFONE = "0"

        @Volatile
        private var INSTANCE: DatabaseHandler? = null

        fun getInstance(context: Context): DatabaseHandler {
            if (INSTANCE == null) {
                INSTANCE = DatabaseHandler(context)
            }
            return INSTANCE as DatabaseHandler
        }
    }

    override fun onCreate(banco: SQLiteDatabase?) {
        banco?.execSQL("CREATE TABLE IF NOT EXISTS $TABLE_NAME (_id INTEGER PRIMARY KEY AUTOINCREMENT, " + "nome TEXT, telefone TEXT)")
    }

    override fun onUpgrade(
        banco: SQLiteDatabase?,
        oldVersion: Int,
        newVersion: Int
    ) {
        banco?.execSQL( "DROP TABLE IF EXISTS $TABLE_NAME")
    }

    fun inserir(cadastro: Cadastro){
        val registro = ContentValues()
        registro.put("nome", cadastro.nome)
        registro.put("telefone", cadastro.telefone)

        writableDatabase.insert(TABLE_NAME, null, registro)
    }

    fun alterar(cadastro: Cadastro){
        val registro = ContentValues()
        registro.put("nome", cadastro.nome)
        registro.put("telefone", cadastro.telefone)

        writableDatabase.update(TABLE_NAME, registro, "_id = ${cadastro._id}", null)
    }

    fun excluir(id: Int){
        writableDatabase.delete(TABLE_NAME, "_id = $id", null)
    }

    fun pesquisar(id: Int): Cadastro?{
        val registro = readableDatabase.query(
            TABLE_NAME,
            null,
            "_id = $id",
            null,
            null,
            null,
            null
        )

        var retorno: Cadastro? = null

        if (registro.moveToNext()){
            val nome = registro.getString(1)
            val telefone = registro.getString(2)

            retorno = Cadastro(id, nome, telefone)
        }

        return retorno
    }

    fun listar(): Cursor {
        val registros = writableDatabase.query(
            TABLE_NAME,
            null,
            null,
            null,
            null,
            null,
            null
        )

        return registros
    }

}