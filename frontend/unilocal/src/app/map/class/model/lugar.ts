import { Horario } from "./horario";

export class Lugar {
    constructor(
        public id: number = 0,
        public description: string = '',
        public name: string = '',
        public schedule: any[] = [],
        public images: string[] = [],
        public comments: string[] = [],
        public businessType: string = '',
        public phones: string[] = [],
        public stateUnilocal: any = '',
        public ownerId: string = '',
        public location: Location = new Location(),
        public qualifications: number[] = [],
        public revisions: number[] = []

    ){}
}
