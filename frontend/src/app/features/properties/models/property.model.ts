export interface Property {
    id: number;
    propertyType: string;
    title: string;
    description: string;
    country: string;
    address: string;
    yearBuilt: number;
    totalBuildingFloors: number;
    apartmentFloor: number;
    totalRooms: number;
    totalBedrooms: number;
    totalBathrooms: number;
    priceAmount: number; 
    currency: string;
    area: number; 
    imageIds: number[];
}