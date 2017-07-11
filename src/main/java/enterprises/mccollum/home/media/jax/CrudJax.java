package enterprises.mccollum.home.media.jax;

import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import enterprises.mccollum.utils.genericentityejb.GenericPersistenceManager;

public abstract class CrudJax<T, K> {
	@Inject
	GenericPersistenceManager<T, K> dao;
	
	@GET
	public Response getAll(){
		return Response.ok(dao.getAll()).build();
	}
	
	@POST
	public Response add(T data){
		T save = dao.persist(data);
		return Response.ok(save).build();
	}
	
	@GET
	@Path("{id}")
	public Response get(@PathParam("id")String id){
		T data = dao.get(stringToId(id));
		if(data == null)
			return Response.status(Status.NOT_FOUND).build();
		return Response.ok(data).build();
	}
	
	@DELETE
	@Path("{id}")
	public Response remove(@PathParam("id")String sid){
		K id = stringToId(sid);
		T data = dao.get(id);
		if(data == null)
			return Response.status(Status.NOT_FOUND).build();
		dao.remove(data);
		return Response.accepted().build();
	}
	
	abstract K stringToId(String id);
}
