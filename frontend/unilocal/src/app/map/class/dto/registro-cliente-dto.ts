export class RegistroClienteDTO {
constructor(
    public cedula: string = '',
    public name: string = '',
    public photo: string = '',
    public nickname: string = '',
    public email: string = '',
    public password: string = '',
    public confirmPassword: string = '',
    public residenceCity: string = '',
) { }
}
