package enterprises.mccollum.home.media.jax;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/**
 * This class is read at runtime by the JAX-RS (RS Stands for ReST Services) framework to determine the base url of the application.
 * 
 * I recommend making /web your web UI and /api your ReST API. This way, it is always clear to you as a developer whether you need to add functionality to one part of your app or another
 * 
 * Credit to the original author, Adam Bien {@code @} airhacks.com for the maven archetype this is based on.
 * 
 * @author airhacks.com
 */
@ApplicationPath("api")
public class JAXRSConfiguration extends Application {}
