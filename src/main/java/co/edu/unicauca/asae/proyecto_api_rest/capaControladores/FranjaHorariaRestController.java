// src/main/java/.../capaControladores/FranjaHorariaRestController.java
package co.edu.unicauca.asae.proyecto_api_rest.capaControladores;

import java.util.List;

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

    // POST /api/franjas -> crear
    @PostMapping("/franjas")
    public ResponseEntity<FranjaHorariaEntity> crear(@RequestBody FranjaHorariaDTO dto) {
        return ResponseEntity.ok(franjaService.crear(dto));
    }

    // GET /api/franjas/{id} -> consultar por @PathVariable
    @GetMapping("/franjas/{id}")
    public ResponseEntity<FranjaHorariaEntity> consultar(@PathVariable Long id) {
        return ResponseEntity.ok(franjaService.consultar(id));
    }

    // PUT /api/franjas/{id} -> actualizar
    @PutMapping("/franjas/{id}")
    public ResponseEntity<FranjaHorariaEntity> actualizar(@PathVariable Long id,
            @RequestBody FranjaHorariaDTO dto) {
        return ResponseEntity.ok(franjaService.actualizar(id, dto));
    }

    // DELETE /api/franjas?id=... -> eliminar por @RequestParam
    @DeleteMapping("/franjas")
    public ResponseEntity<Void> eliminar(@RequestParam("id") Long id) {
        boolean ok = franjaService.eliminar(id);
        return ok ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    // GET /api/cursos/{cursoId}/franjas -> listar por curso
    @GetMapping("/cursos/{cursoId}/franjas")
    public ResponseEntity<List<FranjaHorariaEntity>> listarPorCurso(@PathVariable Long cursoId) {
        return ResponseEntity.ok(franjaService.listarPorCurso(cursoId));
    }
}
