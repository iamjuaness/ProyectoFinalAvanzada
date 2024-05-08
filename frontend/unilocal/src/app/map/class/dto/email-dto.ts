export class EmailDto {
    constructor(
        public asunto: string = '',
        public body: string = '',
        public destinatario: string = ''
    ){}
}
