package cidades;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.awt.List;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.glassfish.jersey.media.multipart.MultiPartFeature;

import com.google.gson.Gson;

import dao.CidadesDao;
import objetos.*;




@Path("/")
public class desafio{
	
	private static int wins, losses, ties;
	

	@POST
	@Path("/upload")
	public Response uploadFile(
	    @FormDataParam("file") InputStream uploadedInputStream,
	    @FormDataParam("file") FormDataContentDisposition fileDetail) throws Exception {

	  
	    String uploadedFileLocation = "C:\\compartilhada\\cidades.csv";
	    try {
	    	writeToFile(uploadedInputStream, uploadedFileLocation);
	    }
	    catch(Exception e){
	        return Response.status(400).entity(e.getCause()).build();
	    }

	    String output = "File uploaded to: " + uploadedFileLocation;
	    readCsv();
	    return Response.status(200).entity(output).build();
	}

	// save uploaded file to new location
	private void writeToFile(InputStream uploadedInputStream,
		String uploadedFileLocation) {

		try {
			OutputStream out = new FileOutputStream(new File(
					uploadedFileLocation));
			int read = 0;
			byte[] bytes = new byte[1024];

			out =		new FileOutputStream(new File(uploadedFileLocation));
			while ((read = uploadedInputStream.read(bytes)) != -1) {
				out.write(bytes, 0, read);
			}
			out.flush();
			out.close();
		} catch (IOException e) {

			e.printStackTrace();
		}

	}
	@GET
	@Path("/csv")
	@Produces("application/json")
      public String readCsv() throws IOException, SQLException {

	    String VIRGULA = ",";

	   
	        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("C:\\compartilhada\\cidades.csv")));
	        String linha = null;
	        String str = "";
	        int count = 0;
	        while ((linha = reader.readLine()) != null) {
	        	
	            String[] dadosUsuario = linha.split(VIRGULA);
	        
	            if (count > 0) {
	            Cidade cidade = new Cidade();
	            cidade.setIbge_id(Integer.parseInt(dadosUsuario[0]));
	            cidade.setUf(dadosUsuario[1]);
	            cidade.setName(dadosUsuario[2]);
	            cidade.setCapital(Boolean.parseBoolean(dadosUsuario[3]));
	            cidade.setLon(Double.parseDouble(dadosUsuario[4]));
	            cidade.setLat(Double.parseDouble(dadosUsuario[5]));
	            cidade.setNo_accents(dadosUsuario[6]);
	            cidade.setAlternative_names(dadosUsuario[7]);
	            cidade.setMicroregion(dadosUsuario[8]);
	            cidade.setMesoregion(dadosUsuario[9]);
	            
	            CidadesDao dao = new CidadesDao();
	            str += dao.setCidade(cidade);
	            str += "\n";
	            }
	            count++;
	        }
	        reader.close();
	    return str;
	}
	
	 /*
    2. Retornar somente as cidades que são capitais ordenadas por nome:
     */
    @GET
    @Produces("application/json")
    @Path("/listaCapitais")
    public String getCapitais() throws SQLException {
        ArrayList<Cidade> lista = new ArrayList<>();

        CidadesDao cd = new CidadesDao();

        lista = cd.getCapitais();

        Gson g = new Gson();

        return g.toJson(lista);
    }

  /*
    
    3. Retornar o nome do estado com a maior e menor quantidade de cidades e a
    quantidade de cidades:
*/
    @GET
    @Produces("application/json")
    @Path("listaMinMaxEstado")
    public String getMinMaxEstado() throws SQLException {
        ArrayList<Estado> lista = new ArrayList<>();

        CidadesDao cd = new CidadesDao();

        Estado estadoMin = new Estado();

        Estado estadoMax = new Estado();

        lista = cd.getQtdCidadesEstado();

        for (Estado a : lista) {
            if (estadoMin.getQtdCidadesEstado() == 0) {
                estadoMin.setQtdCidadesEstado(a.getQtdCidadesEstado());
                estadoMin.setUf(a.getUf());

            } else if (estadoMin.getQtdCidadesEstado() > a.getQtdCidadesEstado()) {
                estadoMin.setQtdCidadesEstado(a.getQtdCidadesEstado());
                estadoMin.setUf(a.getUf());

            } else if (estadoMax.getQtdCidadesEstado() < a.getQtdCidadesEstado()) {
                estadoMax.setQtdCidadesEstado(a.getQtdCidadesEstado());
                estadoMax.setUf(a.getUf());
            }
        }
        lista.clear();

        lista.add(estadoMax);
        lista.add(estadoMin);

        Gson g = new Gson();
        return g.toJson(lista);
    }
    
    /*
    4. Retornar a quantidade de cidades por estado:
     */
    @GET
    @Produces("application/json")
    @Path("listaQtdCidadesEstado")
    public String getQtdCidadesEstado() throws SQLException {
        ArrayList<Estado> lista = new ArrayList<>();

        CidadesDao cd = new CidadesDao();

        lista = cd.getQtdCidadesEstado();

        Gson g = new Gson();

        return g.toJson(lista);
    }

  //5. Obter os dados da cidade informando o id do IBGE:
    @GET
    @Produces("application/json")
    @Path("listaCidade/{ibgeId}")
    public String getCidade(@PathParam("ibgeId") int ibgeId) throws SQLException {
        Cidade c = new Cidade();

        CidadesDao cd = new CidadesDao();
        c = cd.getCidadeById(ibgeId);

        Gson g = new Gson();

        return g.toJson(c);
    }
    
  //6. Retornar o nome das cidades baseado em um estado selecionado:
    @GET
    @Produces("application/json")
    @Path("listaCidadesEstado/{uf}")
    public String getCidadesEstado(@PathParam("uf") String uf) throws SQLException {
    	 CidadesDao cd = new CidadesDao();
    	ArrayList<Cidade> lista = cd.getCidadesEstado(uf);

        Gson g = new Gson();
        return g.toJson(lista);
    }
    
    /*
    7. Permitir adicionar uma nova Cidade:
     */
    @GET
    @Produces("application/json")
    @Path("setCidade/{ibge_id}/{uf}/{name}/{capital}/{lon}/{lat}/{no_accents}/{alternative_names}/{microregion}/{mesoregion}")
    public String setCidade(
            @PathParam("ibge_id") int ibge_id,
            @PathParam("uf") String uf,
            @PathParam("name") String name,
            @PathParam("capital") boolean capital,
            @PathParam("lon") double lon,
            @PathParam("lat") double lat,
            @PathParam("no_accents") String no_accents,
            @PathParam("alternative_names") String alternative_names,
            @PathParam("microregion") String microregion,
            @PathParam("mesoregion") String mesoregion
    ) throws SQLException {
        Cidade cidade = new Cidade();

        cidade.setIbge_id(ibge_id);
        cidade.setUf(uf);
        cidade.setName(name);
        cidade.setCapital(capital);
        cidade.setLon(lon);
        cidade.setLat(lat);
        cidade.setNo_accents(no_accents);
        cidade.setAlternative_names(alternative_names);
        cidade.setMicroregion(microregion);
        cidade.setMesoregion(mesoregion);

        CidadesDao cd = new CidadesDao();
        cd.setCidade(cidade);

        return cd.setCidade(cidade);

    }
    
    //8. Permitir deletar uma cidade:
    @GET
    @Produces("application/json")
    @Path("deleteCidade/{ibgeId}")
    public String deleteCidade(@PathParam("ibgeId") int ibgeId) throws SQLException {
        CidadesDao cd = new CidadesDao();

        Gson g = new Gson();

        return g.toJson(cd.deleteCidade(ibgeId));
    }

    
    /*
    9. Permitir selecionar uma coluna (do CSV) e através dela entrar com uma string para
    filtrar. retornar assim todos os objetos que contenham tal string:
     */
    @GET
    @Produces("application/json")
    @Path("getCidadesByColumn/{column}/{txt}")
    public String getCidadesByColumn(@PathParam("column") String column, @PathParam("txt") String txt) throws SQLException {
        ArrayList<Cidade> lista = new ArrayList<>();

        CidadesDao cd = new CidadesDao();
        lista = cd.getCidadesByColumn(column, txt);

        Gson g = new Gson();

        return g.toJson(lista);
    }

    /*
    10. Retornar a quantidade de registro baseado em uma coluna. Não deve contar itens
    iguais:
     */
    @GET
    @Produces("application/json")
    @Path("getQtdRegistrosColumn/{column}")
    public String getQtdRegistrosColumn(@PathParam("column") String column) throws SQLException {

        CidadesDao cd = new CidadesDao();

     // { "qtd":"1"}
    	String pattern ="{ \"qtd\":\"%s\"}";
    	    	
    	return String.format(pattern,cd.getQtdRegistrosColumn(column));
    }
    /*
    11. Retornar a quantidade de registros total:
    cidades/getQtdRegistros 
     */
    @GET
    @Produces("application/json")
    @Path("getQtdRegistros/")	
    public String getQtdRegistros() throws SQLException {
    	
    	CidadesDao cd = new CidadesDao();
    	
    	// { "qtd":"1"}
    	String pattern ="{ \"qtd\":\"%s\"}";
    	    	
    	return String.format(pattern, cd.getQtdRegistros());
      
    }
    
    /*
    12. Retornar a distancia entre cidades
    cidades/getQtdRegistros 
     */
    @GET
    @Produces("application/json")
    @Path("distCidades")
    public String distCidades() throws SQLException {
        ArrayList<Cidade> lista = new ArrayList<>();

        CidadesDao cd = new CidadesDao();

        lista = cd.getLista();
        String cidadeA = ""; 
        String cidadeB = "";
        Double distancia = 0.;
        Double maiorDistancia = 0.;
        String lastCidade = "";
        Double lastLat= 0.;
        Double lastLon = 0.;
        
       for (Cidade cidade : lista) {
    	   if(lastCidade.equals("")) {
    		   lastCidade = cidade.getName();
        	   lastLat = cidade.getLat();
        	   lastLon = cidade.getLon();
    	   }
    	   System.out.println(cidade.getName());
    	   
    	   distancia = distance(lastLat, lastLon, cidade.getLat(), cidade.getLon());
    	   System.out.println(distancia);
    	   if (distancia > maiorDistancia) {
    		   maiorDistancia = distancia;
    		  cidadeA = lastCidade;
    		  cidadeB = cidade.getName();
    	   }
    	   lastCidade = cidade.getName();
    	   lastLat = cidade.getLat();
    	   lastLon = cidade.getLon();
       }

        
   	
   	// { "cidadeA":"1", "cidadeB":"1", "distance":"1"}
   	String pattern ="{ \"cidadeA\":\"%s\", \"cidadeB\":\"%s\", \"distance\":\"%s\"}";
   	    	
   	return String.format(pattern, cidadeA, cidadeB, maiorDistancia);
    }
    
    
    /*
    Listar todas as cidades
     */
    @GET
    @Produces("application/json")
    @Path("listaCidades")
    public String getCidades() throws SQLException {
        ArrayList<Cidade> lista = new ArrayList<>();

        CidadesDao cd = new CidadesDao();

        lista = cd.getLista();

        Gson g = new Gson();

        return g.toJson(lista);
    }
    
   

    private static double distance(double lat1, double lon1, double lat2, double lon2) {
    
    	double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        dist = dist * 1.609344;


        return (dist);
    }


    /*    converts decimal degrees to radians */

    private static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

 
    /*    converts radians to decimal degrees */
   
    private static double rad2deg(double rad) {
        return (rad * 180 / Math.PI);
    }
    

}
