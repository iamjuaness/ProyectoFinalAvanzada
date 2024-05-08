import { Location } from "../model/location";

export class CreatePlaceDto {
    constructor(
        description: string,
        name: string,
        schedules: string[],
        images: string[],
        businessType: BusinessType,
        owner: string,
        location: Location,
        phones: string[]
    ) { }
    
}

type BusinessType = "PANADERIA" | "CAFETERIA" | "BAR" | "RESTAURANTE" | "DISCOTECA" | "SUPERMERCADO" | "TIENDA" | "OTRO";
