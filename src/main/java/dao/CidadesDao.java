
package dao;

import objetos.Cidade;
import objetos.Estado;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author asepk
 */
public class CidadesDao {

    public ArrayList<Cidade> getLista() throws SQLException {
        Conexao c = new Conexao();
        ArrayList<Cidade> cidades;
        cidades = new ArrayList<>();
        /*
        try (PreparedStatement stmt = c.conectar().prepareStatement("SELECT * FROM usuario")) {
            rs = stmt.executeQuery();
         */
        String sql = "select * from cidades";
        try {
            Statement ps = c.conectar().createStatement();

            ResultSet rs = ps.executeQuery(sql);

            while (rs.next()) {

                Cidade cidade = new Cidade();

                cidade.setAlternative_names(rs.getString("alternative_names"));
                cidade.setCapital(rs.getBoolean("capital"));
                cidade.setIbge_id(rs.getInt("ibge_id"));
                cidade.setLat(rs.getDouble("lat"));
                cidade.setLon(rs.getDouble("lon"));
                cidade.setMesoregion(rs.getString("mesoregion"));
                cidade.setMicroregion(rs.getString("microregion"));
                cidade.setName(rs.getString("name"));
                cidade.setNo_accents(rs.getString("no_accents"));
                cidade.setUf(rs.getString("uf"));

                cidades.add(cidade);

            }
            rs.close();
        } catch (Exception e) {

        }
        return cidades;
    }

    public ArrayList<Cidade> getCapitais() throws SQLException {
        Conexao c = new Conexao();
        ArrayList<Cidade> cidades;
        cidades = new ArrayList<>();
        /*
        try (PreparedStatement stmt = c.conectar().prepareStatement("SELECT * FROM usuario")) {
            rs = stmt.executeQuery();
         */
        String sql = "select * from cidades where capital = true order by name;";
        try {
            Statement ps = c.conectar().createStatement();

            ResultSet rs = ps.executeQuery(sql);

            while (rs.next()) {

                Cidade cidade = new Cidade();

                cidade.setAlternative_names(rs.getString("alternative_names"));
                cidade.setCapital(rs.getBoolean("capital"));
                cidade.setIbge_id(rs.getInt("ibge_id"));
                cidade.setLat(rs.getDouble("lat"));
                cidade.setLon(rs.getDouble("lon"));
                cidade.setMesoregion(rs.getString("mesoregion"));
                cidade.setMicroregion(rs.getString("microregion"));
                cidade.setName(rs.getString("name"));
                cidade.setNo_accents(rs.getString("no_accents"));
                cidade.setUf(rs.getString("uf"));

                cidades.add(cidade);

            }
            rs.close();
        } catch (Exception e) {

        }
        return cidades;
    }

    public Cidade getCidadeById(int id) throws SQLException {
        Conexao c = new Conexao();
        Cidade cidade = new Cidade();

        String sql = "select * from cidades where ibge_id = (?)";

        try {
            PreparedStatement ps = c.conectar().prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {

                cidade.setAlternative_names(rs.getString("alternative_names"));
                cidade.setCapital(rs.getBoolean("capital"));
                cidade.setIbge_id(rs.getInt("ibge_id"));
                cidade.setLat(rs.getDouble("lat"));
                cidade.setLon(rs.getDouble("lon"));
                cidade.setMesoregion(rs.getString("mesoregion"));
                cidade.setMicroregion(rs.getString("microregion"));
                cidade.setName(rs.getString("name"));
                cidade.setNo_accents(rs.getString("no_accents"));
                cidade.setUf(rs.getString("uf"));

            }
            rs.close();
        } catch (Exception e) {

        }

        return cidade;
    }

    public String deleteCidade(int ibge_id) throws SQLException {
        Conexao c = new Conexao();
        Cidade cidade = new Cidade();
        String str = "OK";

        String sql = "delete from cidades where ibge_id = (?)";

        try {
            PreparedStatement ps = c.conectar().prepareStatement(sql);
            ps.setInt(1, ibge_id);
            ps.execute();

        } catch (Exception e) {
            str = e.getMessage();
        }

        return str;
    }

    public ArrayList<Cidade> getCidadesEstado(String uf) throws SQLException {
        Conexao c = new Conexao();
        Cidade cidade = new Cidade();
        ArrayList<Cidade> cidades = new ArrayList<>();

        String sql = "select * from cidades where uf = (?)";

        try {
            PreparedStatement ps = c.conectar().prepareStatement(sql);
            ps.setString(1, uf);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                cidade.setAlternative_names(rs.getString("alternative_names"));
                cidade.setCapital(rs.getBoolean("capital"));
                cidade.setIbge_id(rs.getInt("ibge_id"));
                cidade.setLat(rs.getDouble("lat"));
                cidade.setLon(rs.getDouble("lon"));
                cidade.setMesoregion(rs.getString("mesoregion"));
                cidade.setMicroregion(rs.getString("microregion"));
                cidade.setName(rs.getString("name"));
                cidade.setNo_accents(rs.getString("no_accents"));
                cidade.setUf(rs.getString("uf"));

                cidades.add(cidade);
               
              
                
            }
            
        } catch (Exception e) {

        }

        return cidades;
    }

    public ArrayList<Cidade> getCidadesByColumn(String column, String txt) throws SQLException {
        Conexao c = new Conexao();
        Cidade cidade = new Cidade();
        ArrayList<Cidade> cidades = new ArrayList<>();
        String str = "";

        String sql = "select * from cidades where " + column + " like '%"+ txt + "%'";

        try {
           // PreparedStatement ps = c.conectar().prepareStatement(sql);

            //ps.setString(1, txt);
            //ps.setString(1, "%" +txt + "%");
           // ResultSet rs = ps.executeQuery();
        	Statement ps = c.conectar().createStatement();

            ResultSet rs = ps.executeQuery(sql);

            while (rs.next()) {

                cidade.setAlternative_names(rs.getString("alternative_names"));
                cidade.setCapital(rs.getBoolean("capital"));
                cidade.setIbge_id(rs.getInt("ibge_id"));
                cidade.setLat(rs.getDouble("lat"));
                cidade.setLon(rs.getDouble("lon"));
                cidade.setMesoregion(rs.getString("mesoregion"));
                cidade.setMicroregion(rs.getString("microregion"));
                cidade.setName(rs.getString("name"));
                cidade.setNo_accents(rs.getString("no_accents"));
                cidade.setUf(rs.getString("uf"));

                cidades.add(cidade);

            }
            ps.close();
            rs.close();

        } catch (Exception e) {
            str = e.getMessage();
        }

        return cidades;
    }

    public ArrayList<Estado> getQtdCidadesEstado() throws SQLException {
        Conexao c = new Conexao();
        ArrayList<Estado> estados;
        estados = new ArrayList<>();
        /*
        try (PreparedStatement stmt = c.conectar().prepareStatement("SELECT * FROM usuario")) {
            rs = stmt.executeQuery();
         */
        String sql = "select count(*), uf from cidades group by uf;";
        try {
            Statement ps = c.conectar().createStatement();

            ResultSet rs = ps.executeQuery(sql);

            while (rs.next()) {

                Estado estado = new Estado();

                estado.setQtdCidadesEstado(rs.getInt(1));
                estado.setUf(rs.getString("uf"));

                estados.add(estado);

            }
            rs.close();
        } catch (Exception e) {

        }
        return estados;
    }
    
    public ArrayList<Estado> getEstado() throws SQLException {
        Conexao c = new Conexao();
        ArrayList<Estado> estados;
        estados = new ArrayList<>();
        /*
        try (PreparedStatement stmt = c.conectar().prepareStatement("SELECT * FROM usuario")) {
            rs = stmt.executeQuery();
         */
        String sql = "select count(*), uf from cidades group by uf;";
        try {
            Statement ps = c.conectar().createStatement();

            ResultSet rs = ps.executeQuery(sql);

            while (rs.next()) {

                Estado estado = new Estado();

                estado.setQtdCidadesEstado(rs.getInt(1));
                estado.setUf(rs.getString("uf"));

                estados.add(estado);

            }
            rs.close();
        } catch (Exception e) {

        }
        return estados;
    }

    public int getQtdRegistrosColumn(String column) throws SQLException {
        Conexao c = new Conexao();
        int qtd = 0;


        String sql = "select count(DISTINCT(" + column + ")) from cidades;";
        try {
            Statement ps = c.conectar().createStatement();

            ResultSet rs = ps.executeQuery(sql);

            while (rs.next()) {

               qtd = rs.getInt(1);
          
            }
            rs.close();
        } catch (Exception e) {

        }
        return qtd;
    }
    
     public int getQtdRegistros() throws SQLException {
        Conexao c = new Conexao();
        int qtd = 0;


        String sql = "select count(*) from cidades;";
        try {
            Statement ps = c.conectar().createStatement();

            ResultSet rs = ps.executeQuery(sql);

            while (rs.next()) {

               qtd = rs.getInt(1);
          
            }
            rs.close();
        } catch (Exception e) {

        }
        return qtd;
    }

    public String setCidade(Cidade cidade) throws SQLException {
    	
    	
       Conexao c = new Conexao();
        String str = "";
        String sql = "insert into cidades (ibge_id, uf, name, capital, lon, lat, no_accents, alternative_names, microregion, mesoregion)"
                + " values(?,?,?,?,?,?,?,?,?,?)";

        try {
            PreparedStatement ps = c.conectar().prepareStatement(sql);
            ps.setInt(1, cidade.getIbge_id());
            ps.setString(2, cidade.getUf());
            ps.setString(3, cidade.getName());
            ps.setBoolean(4, cidade.getCapital());
            ps.setDouble(5, cidade.getLon());
            ps.setDouble(6, cidade.getLat());
            ps.setString(7, cidade.getNo_accents());
            ps.setString(8, cidade.getAlternative_names());
            ps.setString(9, cidade.getMicroregion());
            ps.setString(10, cidade.getMesoregion());

            ps.execute();
            ps.close();
          
        } catch (Exception e) {
            str = e.getMessage();
        }
        finally{
            //Executa esse bloco independente do que acontece acima! :D 
           c.fecha();
        }

        return str;
    }

}