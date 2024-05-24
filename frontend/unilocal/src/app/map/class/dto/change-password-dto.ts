export class ChangePasswordDto {
    constructor(
        public id: string | null = '',
        public password: string = ''
    ){}
}
