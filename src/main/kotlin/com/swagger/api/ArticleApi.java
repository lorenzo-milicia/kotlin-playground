/**
 * NOTE: This class is auto generated by the swagger code generator program (3.0.34).
 * https://github.com/swagger-api/swagger-codegen
 * Do not edit the class manually.
 */
package com.swagger.api;

import com.swagger.model.Article;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2022-05-05T20:38:07.060466300+02:00[Europe/Berlin]")
@Validated
public interface ArticleApi {

    @Operation(summary = "Insert a new article", description = "Insert a new article", tags={ "article" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "The article was inserted successfully") })
    @RequestMapping(value = "/article",
        consumes = { "application/json" }, 
        method = RequestMethod.POST)
    ResponseEntity<Void> articlePost(@Parameter(in = ParameterIn.DEFAULT, description = "", required=true, schema=@Schema()) @Valid @RequestBody Article body);

}
