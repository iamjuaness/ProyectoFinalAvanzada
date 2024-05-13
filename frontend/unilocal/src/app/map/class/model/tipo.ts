export class Tipo {
    map(arg0: (ciudad: Tipo) => any): string[] {
      throw new Error('Method not implemented.');
    }
    constructor(
        public id: number = 0,
        public tipo: string = ''
    ){}
}
