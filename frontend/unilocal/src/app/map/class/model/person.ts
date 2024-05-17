export class Person {
    constructor(
        public name: string = '',
        public cedula: string = '',
        public photo: string = '',
        public nickname: string = '',
        public email: string = '',
        public password: string = '',
        public residenceCity: string = '',
        public myPlaces: number[] = [],
        public role: any = '',
        public stateUnilocal: any = '',
        public lugaresFavoritos: number[] = []
    ){}
}
