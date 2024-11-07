export interface Property {
    id: number;
    propertyType: string;
    title: string;
    description: string;
    countryName: string;
    regionName: string;
    yearBuilt: number;
    totalBuildingFloors: number;
    apartmentFloor: number;
    totalRooms: number;
    totalBedrooms: number;
    totalBathrooms: number;
    priceInUsd: number; 
    apartmentArea: number; 
    imageIds: number[];
}