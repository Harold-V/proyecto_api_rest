package co.edu.unicauca.asae.proyecto_api_rest.capaControladores;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import co.edu.unicauca.asae.proyecto_api_rest.capaAccesoADatos.models.FranjaHorariaEntity;
import co.edu.unicauca.asae.proyecto_api_rest.fachadaService.dto.FranjaHorariaDTO;
import co.edu.unicauca.asae.proyecto_api_rest.fachadaService.services.IFranjaHorariaService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class FranjaHorariaRestController {

    private final IFranjaHorariaService franjaService;
    private final ModelMapper mapper;

    // POST /api/franjas -> crear (201 Created + Location) y responder DTO
    @PostMapping("/franjas")
    public ResponseEntity<FranjaHorariaDTO> crear(@RequestBody FranjaHorariaDTO dto) {
        FranjaHorariaEntity creada = franjaService.crear(dto);
        FranjaHorariaDTO body = mapper.map(creada, FranjaHorariaDTO.class);
        URI location = URI.create("/api/franjas/" + creada.getId());
        return ResponseEntity.created(location).body(body);
    }

    // GET /api/franjas/{id} -> devuelve DTO
    @GetMapping("/franjas/{id}")
    public ResponseEntity<FranjaHorariaDTO> consultar(@PathVariable Long id) {
        FranjaHorariaEntity e = franjaService.consultar(id);
        return ResponseEntity.ok(mapper.map(e, FranjaHorariaDTO.class));
    }

    // PUT /api/franjas/{id} -> actualiza y devuelve DTO
    @PutMapping("/franjas/{id}")
    public ResponseEntity<FranjaHorariaDTO> actualizar(@PathVariable Long id,
            @RequestBody FranjaHorariaDTO dto) {
        FranjaHorariaEntity e = franjaService.actualizar(id, dto);
        return ResponseEntity.ok(mapper.map(e, FranjaHorariaDTO.class));
    }

    // DELETE /api/franjas/{id} -> 204/noContent o 404
    @DeleteMapping("/franjas/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        boolean ok = franjaService.eliminar(id);
        return ok ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    // GET /api/cursos/{cursoId}/franjas -> lista DTOs
    @GetMapping("/cursos/{cursoId}/franjas")
    public ResponseEntity<List<FranjaHorariaDTO>> listarPorCurso(@PathVariable Long cursoId) {
        List<FranjaHorariaDTO> lista = franjaService.listarPorCurso(cursoId).stream()
                .map(e -> mapper.map(e, FranjaHorariaDTO.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(lista);
    }
}
