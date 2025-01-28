package br.com.erudio.Controllers;

import br.com.erudio.Data.Model.Author;
import br.com.erudio.Data.VO.V1.AuthorVO;
import br.com.erudio.Data.VO.V1.PersonVO;
import br.com.erudio.Services.AuthorServices;
import br.com.erudio.Util.MediaType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/autores/v1")
public class AuthorController {

    @Autowired
    private AuthorServices services;

    @GetMapping
    public Page<EntityModel<Author>> findAll(Pageable pageable) {
        Page<Author> authors = services.findAll(pageable);

        return authors.map(author -> {
            EntityModel<Author> authorModel = EntityModel.of(author);

            authorModel.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(AuthorController.class).findAll(pageable))
                    .withSelfRel());

            return authorModel;
        });
    }

    @GetMapping("/all")
    @Operation(summary = "Finds an Author", description = "Finds an Author",
            tags = {"Author"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = PersonVO.class))
                    ),
                    @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            }
    )
    public List<Author> getAllAuthors() {
        return services.getAllAuthors();
    }

    @GetMapping(value = "/{id}",
            produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML})
    @Operation(summary = "Finds a Person", description = "Finds a Person",
            tags = {"People"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = PersonVO.class))
                    ),
                    @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            }
    )
    public Author findById(@PathVariable(value = "id") Long id) {
        return services.findById(id);
    }

    @PutMapping(value = "/{id}",
            consumes = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML},
            produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML})
    @Operation(summary = "Updates an Author",
            description = "Updates an Author by passing in a JSON, XML or YML representation of the author!",
            tags = {"Author"},
            responses = {
                    @ApiResponse(description = "Updated", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = PersonVO.class))
                    ),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            }
    )
    public AuthorVO update(@RequestBody AuthorVO author) {
        return services.update(author);
    }
}
