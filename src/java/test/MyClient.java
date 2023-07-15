package test;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

public class MyClient {

    public static void main(String[] args) throws Exception {
        String res = "";
        String URL = "http://localhost:8080/Web/services/inquilino/";
        try {
//Creamos el cliente de conexión al API Restful
            Client client = ClientBuilder.newClient();

//Creamos el target lo cuál es nuestra URL junto con el nombre del método a llamar
            WebTarget target = client.target(URL + "ins");

//Creamos nuestra solicitud que realizará el request
            Invocation.Builder solicitud = target.request();
            
            String jsonString = "{'correo':'a2soto@mail.com','deuda':0.0,'dni':'41111042',"
                    + "'fecha_ingreso':'2019-10-08','materno':'NARVAJO',"
                    + "'nombres':'AUGUSTO','paterno':'SOTOMAYOR','telefono':'900800700'}";

//Enviamos nuestro json vía post al API Restful
            Response post = solicitud.post(Entity.json(jsonString));

//Recibimos la respuesta y la leemos en una clase de tipo String, en caso de que el json sea tipo json y no string, debemos usar la clase de tipo JsonObject.class en lugar de String.class
            String responseJson = post.readEntity(String.class);
            res = responseJson;

//Imprimimos el status de la solicitud
            System.out.println("Estatus: " + post.getStatus());

            switch (post.getStatus()) {
                case 200:
                    res = responseJson;
                    break;
                default:
                    res = "Error";
                    break;
            }

        } catch (Exception e) {
//En caso de un error en la solicitud, llenaremos res con la exceptión para verificar que sucedió
            res = e.toString();
        }
//Imprimimos la respuesta del API Restful
        System.out.println(res);
    }
}
/*
    Client client = ClientBuilder.newClient();

    
        try {
            System.out.println("*** Create ***");
        
        Response response = client.target(
                "http://localhost:8080/Web/services/inquilino/ins")
                .request().post(Entity.json(json));
        System.out.println("Status: " + response.getStatus() + response.getStatusInfo().toString());
        if (response.getStatus() != 201) {
            throw new RuntimeException(
                    "Failed to create");
        }
        response.close();
        System.out.println("**** After Update ***");
    }
    catch(Exception e

    
        ){
            System.out.println(e.getMessage());
    }

    
        finally {
            client.close();
    }
}
}*/
