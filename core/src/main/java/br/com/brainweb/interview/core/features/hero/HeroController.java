package br.com.brainweb.interview.core.features.hero;

import br.com.brainweb.interview.model.dto.HeroWithStatsDto;
import br.com.brainweb.interview.model.dto.HeroesCompareDto;
import br.com.brainweb.interview.model.request.CreateHeroRequest;
import br.com.brainweb.interview.model.request.HeroCompleteRequest;
import br.com.brainweb.interview.model.request.HeroesCompareRequest;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;

import static java.lang.String.format;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.ResponseEntity.created;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/heroes", produces = APPLICATION_JSON_VALUE)
public class HeroController {

	@Autowired
    private final HeroService heroService;

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> create(@Validated
                                           @RequestBody CreateHeroRequest createHeroRequest) {
        final Long id = heroService.create(createHeroRequest);
        return created(URI.create(format("/api/v1/heroes/%s", id))).build();
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id){
        final HeroWithStatsDto response = heroService.findById(id);
        if(response != null){
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Object Not Found!!!");
        }
    }

    @GetMapping("/findByName/{name}")
    public ResponseEntity<?> findById(@PathVariable String name){
        final HeroWithStatsDto response = heroService.findByName(name);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


     @PutMapping(consumes = APPLICATION_JSON_VALUE)
     public ResponseEntity<?> update(@Validated
                                         @RequestBody HeroCompleteRequest heroRequest){
         final Long id = heroService.update(heroRequest);
         if(id != null){
             return ResponseEntity.status(HttpStatus.OK).body("Hero " + id + " updated!!!");
         }else{
             return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Object Not Found!!!");
         }
     }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
         final boolean deleted = heroService.delete(id);
         if(deleted == true){
             return ResponseEntity.status(HttpStatus.OK).body("Hero " + id + " deleted!!!");
         }else{
             return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Object Not Found!!!");
         }
     }

    @PostMapping("compare")
    public ResponseEntity<?> compare(@Validated
                                         @RequestBody HeroesCompareRequest heroRequest){
        final HeroesCompareDto response = heroService.compare(heroRequest.getFirst(), heroRequest.getSecond());
        if(response != null){
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Objects Not Found!!!");
        }
    }
}
