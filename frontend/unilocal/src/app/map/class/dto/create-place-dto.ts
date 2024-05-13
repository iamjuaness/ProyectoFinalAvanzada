import { Horario } from "../model/horario";
import { Location } from "../model/location";

export class CreatePlaceDto {
    constructor(
        public description: string = '',
        public name: string = '',
        public schedules: Horario[] = [new Horario()],
        public images: any[] = [],
        public businessType: any = '',
        public owner: string = '',
        public location: Location = new Location,
        public phones: any[] = []
    ) { }
    
}