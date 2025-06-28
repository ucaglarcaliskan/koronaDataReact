
export interface NewsDTO {
    id?: string;
    date: string;
    city?: string;
    infected: number;
    death: number;
    discharged: number;
}

export interface ChartData {
    date: string;
    death: number;
    infected: number;
    discharged: number;
} 