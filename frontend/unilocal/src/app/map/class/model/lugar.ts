import { Horario } from "./horario";

export class Lugar {
    map(arg0: (lugar: Lugar) => Lugar): Lugar[] {
      throw new Error('Method not implemented.');
    }
    constructor(
        public id: number = 0,
        public description: string = '',
        public name: string = '',
        public schedule: any[] = [],
        public images: string[] = [],
        public comments: string[] = [],
        public businessType: string = '',
        public phones: string[] = [],
        public stateBusiness: string = '',
        public owner: string = '',
        public location: Location = new Location(),
        public qualifications: number[] = [],
        public revisions: number[] = []

    ){}
}
