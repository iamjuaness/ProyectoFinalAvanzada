package com.avanzada.unilocal;

import com.avanzada.unilocal.Unilocal.controller.*;
import com.avanzada.unilocal.Unilocal.dto.*;
import com.avanzada.unilocal.Unilocal.entity.Person;
import com.avanzada.unilocal.Unilocal.entity.Place;
import com.avanzada.unilocal.Unilocal.enums.BusinessType;
import com.avanzada.unilocal.Unilocal.enums.Role;
import com.avanzada.unilocal.Unilocal.enums.StateUnilocal;
import com.avanzada.unilocal.Unilocal.resources.Horario;
import com.avanzada.unilocal.Unilocal.resources.Location;
import com.avanzada.unilocal.Unilocal.serviceImplements.*;
import com.avanzada.unilocal.global.dto.MensajeAuthDto;
import com.avanzada.unilocal.global.dto.MessageDto;
import com.avanzada.unilocal.global.exceptions.AttributeException;
import com.avanzada.unilocal.global.exceptions.ResourceNotFoundException;
import jakarta.mail.MessagingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
@SpringBootTest
class UnilocalApplicationTests {

	@InjectMocks
	private AuthController authController;

	@InjectMocks
	private ClientController clientController;

	@InjectMocks
	private ImagenesController imagenesController;

	@InjectMocks
	private ModController modController;

	@InjectMocks
	private PlaceController placeController;

	@Mock
	private AuthServiceImp authServiceImp;

	@Mock
	private PersonService personService;

	@Mock
	private PlaceService placeService;

	@Mock
	private ImagenesServicioImp imagenesServicioImp;

	@Mock
	private ModeradorService moderadorService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.initMocks(this);
	}



	//--------------------TEST UNITARIOS PARA AUTHCONTROLLER--------------------//
	@Test
	void testLoginUser_Success() throws Exception {
		// Arrange
		SesionUserDto sesionUserDto = new SesionUserDto("cardonajuanes94@gmail.com", "juanes123");
		TokenDto tokenDto = new TokenDto("123");
		when(authServiceImp.loginClient(sesionUserDto)).thenReturn(tokenDto);

		// Act
		ResponseEntity<MensajeAuthDto<TokenDto>> responseEntity = authController.loginUser(sesionUserDto);

		// Assert
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(tokenDto, responseEntity.getBody().respuesta());
	}

	@Test
	void testRegisterUser_Success() throws AttributeException {
		// Arrange
		RegisterUserDto registerUserDto = new RegisterUserDto("1234", "Pepe", "", "pepe123", "pepe123@gmail.com", "pepe123", "pepe123", "armenia");
		Person person = new Person("1234", "Pepe", "", "pepe123", "pepe123@gmail.com", "pepe123", "Armenia", Role.USER, StateUnilocal.Active);
		String expectedMessage = "user " + registerUserDto.name() + " have been saved";
		when(personService.signUp(any(RegisterUserDto.class))).thenReturn(person);


		// Act
		ResponseEntity<MensajeAuthDto> responseEntity = authController.registerUser(registerUserDto);

		// Assert
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(expectedMessage, responseEntity.getBody().respuesta());
	}

	@Test
	void testForgotPassword_Success() throws ResourceNotFoundException, MessagingException {
		// Arrange
		EmailDTO emailDTO = new EmailDTO("Recuperar Contrasenia", "Simulacion Recuperacion de contrasenia", "cardonajuanes94@gmail.com");
		doNothing().when(personService).sendLinkPassword(emailDTO);

		// Act
		ResponseEntity<String> responseEntity = authController.forgotPassword(emailDTO);

		// Assert
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals("Se ha enviado un correo electrónico con el enlace para restablecer la contraseña.", responseEntity.getBody());
	}

	@Test
	void testResetPassword_Success() throws ResourceNotFoundException {
		// Arrange
		ChangePasswordDTO changePasswordDTO = new ChangePasswordDTO("1234", "pepe1234");
		doNothing().when(personService).changePassword(changePasswordDTO);

		// Act
		ResponseEntity<String> responseEntity = authController.resetPassword(changePasswordDTO);

		// Assert
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals("Contraseña cambiada exitosamente.", responseEntity.getBody());
	}

	@Test
	void testLoginMod_Success() throws Exception {
		// Arrange
		SesionUserDto sesionUserDto = new SesionUserDto("cardonajuanes94@gmail.com", "juanes123");
		TokenDto tokenDto = new TokenDto("345");
		when(authServiceImp.loginMod(sesionUserDto)).thenReturn(tokenDto);

		// Act
		ResponseEntity<MensajeAuthDto<TokenDto>> responseEntity = authController.loginMod(sesionUserDto);

		// Assert
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(tokenDto, responseEntity.getBody().respuesta());
	}

	@Test
	void testGetAllPlaces_Success() {
		// Arrange
		List<Place> places = new ArrayList<>();
		when(placeService.getAll()).thenReturn(places);

		// Act
		ResponseEntity<List<Place>> responseEntity = authController.getAll();

		// Assert
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(places, responseEntity.getBody());
	}


	//--------------------TEST UNITARIOS PARA CLIENTCONTROLLER--------------------//

	@Test
	void testGetAll() {
		// Arrange
		List<Person> expectedPersons = Arrays.asList(new Person(), new Person());
		when(personService.getAll()).thenReturn(expectedPersons);

		// Act
//		ResponseEntity<List<Person>> responseEntity = clientController.getAllU();
//
//		// Assert
//		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
//		assertEquals(expectedPersons, responseEntity.getBody());
	}

	@Test
	void testGetOneClient() throws ResourceNotFoundException {
		// Arrange
		String id = "1";
		Person expectedPerson = new Person();
		when(personService.getOne(id)).thenReturn(expectedPerson);

		// Act
		ResponseEntity<Person> responseEntity = clientController.getOne(id);

		// Assert
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(expectedPerson, responseEntity.getBody());
	}

	@Test
	void testUpdateClient() throws ResourceNotFoundException, AttributeException {
		// Arrange
		String id = "1234";
		UpdateUserDto updateUserDto = new UpdateUserDto("Pepe", "", "pepe123","pepe@gmail.com","Manizales");
		Person updatedPerson = new Person(id,"Pepe", "", "pepe123","pepe@gmail.com","Manizales");
		when(personService.profileEdit(updateUserDto, id)).thenReturn(updatedPerson);

		// Act
		ResponseEntity<MessageDto> responseEntity = clientController.update(id, updateUserDto);

		// Assert
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals("user " + updatedPerson.getName() + " have been updated", responseEntity.getBody().message());
	}

	@Test
	void testDeleteClient() throws ResourceNotFoundException {
		// Arrange
		String id = "1";
		Person deletedPerson = new Person();
		when(personService.delete(id)).thenReturn(deletedPerson);

		// Act
		ResponseEntity<MessageDto> responseEntity = clientController.delete(id);

		// Assert
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals("user " + deletedPerson.getName() + " have been deleted", responseEntity.getBody().message());
	}

	@Test
	void testAgregarFavorito() throws ResourceNotFoundException {
		// Arrange
		String usuarioId = "1";
		int lugarId = 1;
		doNothing().when(personService).agregarFavorito(usuarioId, lugarId);

		// Act
		ResponseEntity<String> responseEntity = clientController.agregarFavorito(usuarioId, lugarId);

		// Assert
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals("Lugar agregado a favoritos.", responseEntity.getBody());
	}

	@Test
	void testEliminarFavorito() throws ResourceNotFoundException {
		// Arrange
		String usuarioId = "1";
		int lugarId = 1;
		doNothing().when(personService).eliminarFavorito(usuarioId, lugarId);

		// Act
		ResponseEntity<String> responseEntity = clientController.eliminarFavorito(usuarioId, lugarId);

		// Assert
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals("Lugar eliminado de favoritos.", responseEntity.getBody());
	}

	@Test
	void testObtenerFavoritos() throws ResourceNotFoundException {
		// Arrange
		String usuarioId = "1";
		List<Place> favoritos = Arrays.asList(new Place(), new Place());
		when(personService.obtenerFavoritos(usuarioId)).thenReturn(favoritos);

		// Act
		ResponseEntity<List<Place>> responseEntity = clientController.obtenerFavoritos(usuarioId);

		// Assert
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(favoritos, responseEntity.getBody());
	}

	@Test
	void testAgregarComentario() throws ResourceNotFoundException {
		// Arrange
		int lugarId = 1;
		CommentDTO comentario = new CommentDTO("Un gran restaurante");
		doNothing().when(personService).addComment(lugarId, comentario);

		// Act
		ResponseEntity<String> responseEntity = clientController.agregarComentario(lugarId, comentario);

		// Assert
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals("Comentario agregado exitosamente.", responseEntity.getBody());
	}

	@Test
	void testObtenerLugaresUsuario() throws ResourceNotFoundException {
		// Arrange
		String id = "1";
		List<Place> expectedPlaces = Arrays.asList(new Place(), new Place());
		when(personService.obtenerLugaresUsuario(id)).thenReturn(expectedPlaces);

		// Act
		List<Place> obtainedPlaces = clientController.obtenerLugaresUsuario(id);

		// Assert
		assertEquals(expectedPlaces, obtainedPlaces);
	}

	@Test
	void testResponderComentario() throws ResourceNotFoundException {
		// Arrange
		int id = 1;
		CommentDTO respuesta = new CommentDTO("Totalmente de acuerdo");
		doNothing().when(personService).responderComentario(id, respuesta);

		// Act
		clientController.responderComentario(id, respuesta);

		// Assert
		verify(personService, times(1)).responderComentario(id, respuesta);
	}

	@Test
	void testEliminarComentario() throws ResourceNotFoundException {
		// Arrange
		int id = 1;
		String idCliente = "1";
		doNothing().when(personService).eliminarComentario(id, idCliente);

		// Act
		clientController.eliminarComentario(id, idCliente);

		// Assert
		verify(personService, times(1)).eliminarComentario(id, idCliente);
	}

	@Test
	void testAgregarCalificacion() throws ResourceNotFoundException {
		// Arrange
		int lugarId = 1;
		QualificationDTO qualificationDTO = new QualificationDTO(4.5, "1234");
		doNothing().when(personService).addQualification(lugarId, qualificationDTO);

		// Act
		ResponseEntity<MensajeAuthDto> responseEntity = clientController.agregarCalificacion(lugarId, qualificationDTO);

		// Assert
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals("Comentario agregado exitosamente.", responseEntity.getBody());
	}



	//--------------------TEST UNITARIOS PARA IMAGENESCONTROLLER--------------------//

	@Test
	void testSubir() throws Exception {
		// Arrange
		MultipartFile imagen = mock(MultipartFile.class);
		Map<String, Object> respuesta = new HashMap<>();
		respuesta.put("mensaje", "Imagen subida correctamente");
		when(imagenesServicioImp.subirImagen(imagen)).thenReturn(respuesta);

		// Act
		ResponseEntity<MensajeAuthDto<Map>> responseEntity = imagenesController.subir(imagen);

		// Assert
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(respuesta, responseEntity.getBody().respuesta());
	}

	@Test
	void testEliminar() throws Exception {
		// Arrange
		ImagenDTO imagenDTO = new ImagenDTO("1", "");
		Map<String, Object> respuesta = new HashMap<>();
		respuesta.put("mensaje", "Imagen eliminada correctamente");
		when(imagenesServicioImp.eliminarImagen("1")).thenReturn(respuesta);

		// Act
		ResponseEntity<MensajeAuthDto<Map>> responseEntity = imagenesController.eliminar(imagenDTO);

		// Assert
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(respuesta, responseEntity.getBody().respuesta());
	}

	//--------------------TEST UNITARIOS PARA MODCONTROLLER--------------------//

	@Test
	void testAutorizarLugar() throws ResourceNotFoundException, MessagingException {
		// Arrange
		int lugarId = 1;
		RegisterRevisionDto registerRevisionDto = new RegisterRevisionDto("mod", "Cumple con todos los requisitos");
		doNothing().when(moderadorService).autorizarLugar(lugarId, registerRevisionDto);

		// Act
		ResponseEntity<String> responseEntity = modController.autorizarLugar(lugarId, registerRevisionDto);

		// Assert
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals("Se ha enviado un correo electronico al usuario avisando que su negocio fue autorizado.", responseEntity.getBody());
	}

	@Test
	void testRechazarLugar() throws ResourceNotFoundException, MessagingException {
		// Arrange
		int lugarId = 1;
		doNothing().when(moderadorService).rechazarLugar(lugarId);

		// Act
		ResponseEntity<String> responseEntity = modController.rechazarLugar(lugarId);

		// Assert
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals("Se ha enviado un correo electronico al usuario avisando que su negocio fue rechazado.", responseEntity.getBody());
	}

	@Test
	void testGetLugaresPendientes() {
		// Arrange
		List<Place> expectedPlaces = Arrays.asList(new Place(), new Place());
		when(moderadorService.getLugaresPendientes()).thenReturn(expectedPlaces);

		// Act
		List<Place> obtainedPlaces = modController.getLugaresPendientes();

		// Assert
		assertEquals(expectedPlaces, obtainedPlaces);
	}

	@Test
	void testGetLugaresAutorizados() {
		// Arrange
		List<Place> expectedPlaces = Arrays.asList(new Place(), new Place());
		when(moderadorService.getLugaresAutorizados()).thenReturn(expectedPlaces);

		// Act
		List<Place> obtainedPlaces = modController.getLugaresAutorizados();

		// Assert
		assertEquals(expectedPlaces, obtainedPlaces);
	}

	@Test
	void testGetLugaresRechazados() {
		// Arrange
		List<Place> expectedPlaces = Arrays.asList(new Place(), new Place());
		when(moderadorService.getLugaresRechazados()).thenReturn(expectedPlaces);

		// Act
		List<Place> obtainedPlaces = modController.getLugaresRechazados();

		// Assert
		assertEquals(expectedPlaces, obtainedPlaces);
	}

	//--------------------TEST UNITARIOS PARA PLACECONTROLLER--------------------//

	@Test
	void testGetOnePlace() throws ResourceNotFoundException {
		// Arrange
		int placeId = 1;
		Place expectedPlace = new Place();
		when(placeService.getOne(placeId)).thenReturn(expectedPlace);

		// Act
		ResponseEntity<Place> responseEntity = placeController.getOne(placeId);

		// Assert
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(expectedPlace, responseEntity.getBody());
	}

	@Test
	void testSave() throws AttributeException, ResourceNotFoundException {
		// Arrange
		List<Horario> schedules = new ArrayList<>();
		List<String> images = new ArrayList<>();
		List<String> phones = new ArrayList<>();
		CreatePlaceDto createPlaceDto = new CreatePlaceDto("", "TestName", schedules, images, "Restarante", "1234", new Location(1, 2), phones);
		Place place = new Place();
		place.setName("TestName");
		String message = "place TestName have been saved";
		when(placeService.createBusiness(createPlaceDto)).thenReturn(place);

		// Act
		ResponseEntity<MessageDto> responseEntity = placeController.save(createPlaceDto);

		// Assert
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(message, responseEntity.getBody().message());
	}

	@Test
	void testUpdate() throws ResourceNotFoundException, AttributeException {
		// Arrange
		int placeId = 1;
		List<Horario> schedules = new ArrayList<>();
		List<String> images = new ArrayList<>();
		List<String> phones = new ArrayList<>();
		CreatePlaceDto createPlaceDto = new CreatePlaceDto("", "", schedules, images, "Restaurante", "", new Location(1, 2), phones);
		Place place = new Place();
		place.setName("TestName");
		String message = "place TestName have been updated";
		when(placeService.updateBusiness(placeId, createPlaceDto)).thenReturn(place);

		// Act
		ResponseEntity<MessageDto> responseEntity = placeController.update(placeId, createPlaceDto);

		// Assert
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(message, responseEntity.getBody().message());
	}

	@Test
	void testDelete() throws ResourceNotFoundException {
		// Arrange
		int placeId = 1;
		Place place = new Place();
		place.setName("TestName");
		String message = "place TestName have been deleted";
		when(placeService.deleteBusiness(placeId)).thenReturn(place);

		// Act
		ResponseEntity<MessageDto> responseEntity = placeController.delete(placeId);

		// Assert
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(message, responseEntity.getBody().message());
	}

}
