package com.avanzada.unilocal.Unilocal.controller;


import com.avanzada.unilocal.Unilocal.dto.ImagenDTO;
import com.avanzada.unilocal.Unilocal.serviceImplements.ImagenesServicioImp;
import com.avanzada.unilocal.global.dto.MensajeAuthDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.Map;
@RestController
@RequestMapping("/api/imagenes")
public class ImagenesController {

    @Autowired
    ImagenesServicioImp imagenesServicioImp;

    @PostMapping("/subir")
    public ResponseEntity<MensajeAuthDto<Map>> subir(@RequestParam("file") MultipartFile imagen)
            throws Exception{
        Map respuesta = imagenesServicioImp.subirImagen(imagen);
        return ResponseEntity.ok().body(new MensajeAuthDto<>(false, respuesta ));
    }
    @DeleteMapping("/eliminar")
    public ResponseEntity<MensajeAuthDto<Map>> eliminar(@RequestBody ImagenDTO imagenDTO) throws
            Exception{
        Map respuesta = imagenesServicioImp.eliminarImagen( imagenDTO.id() );
        return ResponseEntity.ok().body(new MensajeAuthDto<>(false, respuesta ));
    }
}
