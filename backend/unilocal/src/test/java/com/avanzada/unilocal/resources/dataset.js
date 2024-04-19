db = connect( 'mongodb+srv://unilocal:unilocal@unilocal-cluster.42tjncp.mongodb.net/?retryWrites=true&w=majority' );
db.users.insertMany([
    {
        cedula: "123456789",
        name: "Juan",
        photo: "",
        nickname: "juan",
        email: "cardonajuanes94@gmail.com",
        password: "juan123",
        residenceCity: "Armenia",
        _class: 'co.edu.uniquindio.proyecto.modelo.documentos.Cliente'
    },
    {
        _id: 'Cliente2',
        nickname: 'maria',
        ciudad: 'Armenia',
        fotoPerfil: 'mi foto',
        email: 'maria@email.com',
        password: 'mipassword',
        nombre: 'Maria',
        estado: 'ACTIVO',
        _class: 'co.edu.uniquindio.proyecto.modelo.documentos.Cliente'
    },
    {
        _id: 'Cliente3',
        nickname: 'pedrito',
        ciudad: 'Armenia',
        fotoPerfil: 'mi foto',
        email: 'pedro@email.com',
        password: 'mipassword',
        nombre: 'Pedro',
        estado: 'ACTIVO',
        _class: 'co.edu.uniquindio.proyecto.modelo.documentos.Cliente'
    }
]);
db.negocios.insertMany([
    {
        _id: 'Negocio1',
        nombre: 'Restaurante Mexicano',
        descripcion: 'Restaurante de comida mexicana en Armenia',
        codigoCliente: 'Cliente1',
        ubicacion: {
            latitud: 4.540130,
            longitud: -75.665660
        },
        imagenes: ['imagen1', 'imagen2'],
        tipoNegocio: 'RESTAURANTE',
        horarios: [
            {
                dia: 'LUNES',
                horaInicio: '08:00',
                horaFin: '20:00'
            }
        ],
        telefonos: ['1234567', '7654321'],
        estado: 'ACTIVO',
        _class: 'co.edu.uniquindio.proyecto.modelo.documentos.Negocio'
    }
]);
db.comentarios.insertMany([
    {
        mensaje: "Excelente sitio, muy buena atención",
        fecha: new Date(),
        codigoCliente: 'Cliente1',
        codigoNegocio: 'Negocio1',
        calificacion: 5,
        _class: 'co.edu.uniquindio.proyecto.modelo.documentos.Comentario'
    }
]);