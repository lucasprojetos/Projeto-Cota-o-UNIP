package br.com.cotasmart.factory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CreationFactory {
	private Connection connection;
	
	public CreationFactory() throws SQLException {
		try {
			this.connection = new ConnectionFactory().getConnection();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}
	
	public void criarTabelas() throws SQLException{ 	
		String SQL =
					"DROP TABLE cidades CASCADE; "+
					"DROP TABLE cotacao CASCADE; "+
					"DROP TABLE cotacaoprodutos CASCADE; "+
					"DROP TABLE empresa CASCADE; "+
					"DROP TABLE fornecedores CASCADE; "+
					"DROP TABLE grupoprodutos CASCADE; "+
					"DROP TABLE gruposUsuario CASCADE; "+
					"DROP TABLE produtos CASCADE; "+
					"DROP TABLE status CASCADE; "+
					"DROP TABLE uf CASCADE; "+
					"DROP TABLE usuarios CASCADE;"+
						" "+
					"CREATE TABLE IF NOT EXISTS status ( "+
					"codStatus SERIAL, "+
					"nome CHARACTER VARYING (20), "+
					"tipo CHARACTER VARYING (1),"+
					"CONSTRAINT pk_status PRIMARY KEY (codStatus)); "+
					" "+
					"CREATE TABLE  IF NOT EXISTS gruposUsuario ( "+
					"codGrupo SERIAL, "+
					"nome CHARACTER VARYING (20),"+
					"CONSTRAINT pk_grupos_usuario PRIMARY KEY (codGrupo));"+
					" "+
					"CREATE TABLE IF NOT EXISTS usuarios ( "+
					"codUsuario SERIAL, "+
					"nome CHARACTER VARYING(50), "+
					"login CHARACTER VARYING (20), "+
					"senha CHARACTER VARYING (10), "+
					"ativo BOOLEAN, "+
					"administrador BOOLEAN,"+
					"CONSTRAINT pk_usuario PRIMARY KEY (codUsuario));" +
					" "+
					"CREATE TABLE IF NOT EXISTS uf ( "+
					"codUf SERIAL, "+
					"uf CHARACTER VARYING (2), "+
					"CONSTRAINT pk_uf PRIMARY KEY (codUf));"+
					" "+
					"CREATE TABLE IF NOT EXISTS cidades ( "+
					"codCidade SERIAL, "+
					"nome CHARACTER VARYING (20), "+
					"codUf INTEGER REFERENCES uf ON DELETE CASCADE, "+
					"CONSTRAINT pk_cidades PRIMARY KEY (codCidade));"+
					" "+
					"CREATE TABLE IF NOT EXISTS fornecedores ("+
					"codFornecedor SERIAL,"+
					"cnpj CHARACTER VARYING (20),"+	
					"nome CHARACTER VARYING (50), "+
					"endereco CHARACTER VARYING (100), "+
					"codCidade INTEGER REFERENCES cidades ON DELETE CASCADE, "+
					"telefone1 CHARACTER VARYING(20), "+
					"telefone2 CHARACTER VARYING(20), "+
					"telefone3 CHARACTER VARYING(20), "+
					"codUsuario INTEGER REFERENCES gruposUsuario ON DELETE CASCADE, "+
					"ativo boolean, "+
					"CONSTRAINT pk_fornecedores PRIMARY KEY (codFornecedor));"+
					" "+
					"CREATE TABLE IF NOT EXISTS grupoProdutos ( "+
					"codGrupoProdutos SERIAL, "+
					"nome CHARACTER VARYING(20),"+
					"CONSTRAINT pk_grupoProdutos PRIMARY KEY(codGrupoProdutos) );"+
					" "+
					"CREATE TABLE IF NOT EXISTS produtos ( "+
					"codProduto SERIAL, "+
					"nome CHARACTER VARYING (50), "+
					"codBarras CHARACTER VARYING (50)," +
					"ativo boolean, "+
					"codGrupoProdutos INTEGER REFERENCES grupoProdutos ON DELETE CASCADE, "+
					"CONSTRAINT pk_produtos PRIMARY KEY (codProduto));"+
					" "+
					"CREATE TABLE IF NOT EXISTS cotacao ( "+
					"codCotacao SERIAL, "+
					"data DATE, "+
					"codUsuario INTEGER REFERENCES usuarios ON DELETE CASCADE, "+
					"valorTotal NUMERIC ," +
					"finalizado BOOLEAN,"+
					"ativo BOOLEAN,"+
					"datafinalizacao DATE,"+
					"CONSTRAINT pk_cotacao PRIMARY KEY (codCotacao) );"+
					" "+
					"CREATE TABLE IF NOT EXISTS cotacaoProdutos ( "+
					"codCotacaoProdutos SERIAL, "+
					"codCotacao INTEGER REFERENCES cotacao ON DELETE CASCADE, "+
					"codProduto INTEGER REFERENCES produtos ON DELETE CASCADE, " +
					"codFornecedor INTEGER REFERENCES fornecedores ON DELETE CASCADE);"+
					" "+
					"CREATE TABLE IF NOT EXISTS empresa ("+
					"codEmpresa SERIAL,"+
					"nome CHARACTER VARYING(50),"+
					"cnpj CHARACTER VARYING(20),"+
					"endereco CHARACTER VARYING (100),"+
					"telefone1 CHARACTER VARYING (20),"+
					"telefone2 CHARACTER VARYING (20),"+
					"CONSTRAINT pk_empresa PRIMARY KEY (codEmpresa) ); "+
					" "+
					"INSERT INTO empresa (nome) VALUES('Minha empresa');"+
					"INSERT INTO usuarios (nome,login,senha,administrador,ativo) VALUES ('Administrador', 'admin', '1234', true, true);"+
					"INSERT INTO usuarios (nome,login,senha,administrador,ativo) VALUES ('Fornecedor', 'fornecedor', '1234', false, true);"+
					"";
					
	
		try {
			PreparedStatement stmt = connection.prepareStatement(SQL);
			stmt.execute();
			stmt.close();
		} catch (SQLException e) {
			System.out.println("Erro ao criar tabelas "+e.getMessage());
			throw new RuntimeException(e);
		}
		
	}
}
