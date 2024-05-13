export class Ciudad {
    map(arg0: (ciudad: Ciudad) => string): Ciudad[] {
      throw new Error('Method not implemented.');
    }
    constructor(
        public id: number = 0,
        public nombre: string = ''
    ){}
}
