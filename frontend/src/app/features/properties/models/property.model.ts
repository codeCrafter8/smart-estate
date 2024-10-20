export interface Property {
    id: number;
    title: string;
    description: string;
    countryName: string;
    regionName: string;
    priceInUsd: number;
    apartmentArea: number;
    imageIds: number[]; 
}
