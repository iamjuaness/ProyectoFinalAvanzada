export class Horario {
    dia: string;
    horaInicio: string;
    horaFin: string;

    constructor(dia: string = '', horaInicio: string = '', horaFin: string = '') {
        this.dia = dia;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
    }
}
