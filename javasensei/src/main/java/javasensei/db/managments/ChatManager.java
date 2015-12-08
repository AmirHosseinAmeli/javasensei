package javasensei.db.managments;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.QueryBuilder;
import java.time.Instant;
import java.util.Arrays;
import java.util.Date;
import javasensei.db.Connection;
import org.bson.types.ObjectId;

/**
 *
 * @author Rock
 */
public class ChatManager {
    private DBCollection chatCollection = Connection.getCollection().get(CollectionsDB.CHAT);
    
    public String agregarMensaje(String message, String nombreUsuario, String idUsuario, Integer idEjercicio, String color){
        Long fecha = Instant.now().toEpochMilli();
        
        BasicDBObject mensaje = new BasicDBObject("message", message);
        mensaje.put("nombreUsuario", nombreUsuario);
        mensaje.put("idUsuario", idUsuario);
        mensaje.put("idEjercicio",idEjercicio);
        mensaje.put("fecha", fecha);
        mensaje.put("color", color);
        mensaje.put("_id", chatCollection.insert(mensaje));
        
        return mensaje.toString();
    }
    
    public String leerMensajes(Long fecha, Integer idEjercicio){
        DBObject query = QueryBuilder.start("fecha")
                .is(new BasicDBObject("$gt", fecha))
                .put("idEjercicio")
                .is(idEjercicio)
                .get();
        
        return chatCollection.find(query).toArray().toString();
    }
}
