package com.avanzada.unilocal.Unilocal.interfaces;

public interface BusinessService {

    void crearNegocio();

    void actualizarNegocio();

    void eliminarNegocio(String idNegocio);

    void buscarNegocios();

    void filtrarPorEstado();

    void listarNegociosPropietario();

    void cambiarEstado();

    void registrarRevision();

}
