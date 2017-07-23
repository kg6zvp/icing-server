package enterprises.mccollum.home.media.jax;

import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HEAD;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
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
	
	@PUT
	public Response add(T data){
		T save = dao.persist(data);
		return Response.accepted(save).build();
	}
	
	@POST
	public Response update(T data){
		T save = dao.save(data);
		return Response.accepted(save).build();
	}
	
	@HEAD
	@Path("{id}")
	public Response head(@PathParam("id")String id){
		if(dao.containsKey(stringToId(id)))
			return Response.ok().build();
		return Response.status(Status.NOT_FOUND).build();
	}
	
	@GET
	@Path("{id}")
	public Response get(@PathParam("id")String id){
		T data = dao.get(stringToId(id));
		if(data == null)
			return Response.status(Status.NOT_FOUND).build();
		return Response.ok(data).build();
	}
	
	@POST
	@Path("{id}")
	public Response update(@PathParam("id")String id, T data){
		data = dao.save(data);
		return Response.accepted(data).build();
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
