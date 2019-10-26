package ascelion.kalah.shared.endpoint;

import java.util.List;
import java.util.UUID;

import javax.validation.Valid;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import ascelion.kalah.shared.model.POJO;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;

public interface ViewEntityEndpoint<V> {
	@AllArgsConstructor
	class Search<V> extends POJO {
		@NotNull
		public final V probe;
		public final String[] sort;
		@Min(0)
		public final Integer page;
		@Min(10)
		public final Integer size;

		public final boolean any;

		@AssertTrue
		@JsonIgnore
		public boolean isPageValid() {
			return this.page != null ? this.size != null : true;
		}
	}

	@GET
	@Produces(APPLICATION_JSON)
	List<V> getAll(@QueryParam("sort") List<String> sort,
			@QueryParam("page") @Min(0) Integer page,
			@QueryParam("page") @DefaultValue("10") @Min(10) int size);

	@GET
	@Path("{id}")
	@Produces(APPLICATION_JSON)
	V get(@Parameter(name = "id", description = "The entity ID.", schema = @Schema(type = SchemaType.STRING)) @PathParam("id") UUID id);

	@POST
	@Path("search")
	@Consumes(APPLICATION_JSON)
	@Produces(APPLICATION_JSON)
	List<V> search(@NotNull @Valid Search<V> search);
}
