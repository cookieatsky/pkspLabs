export enum ServiceCategory {
    MASSAGE = "Массажи",
    FACIAL = "Уход за лицом",
    BODY_CARE = "Уход за телом",
    MANICURE = "Маникюр",
    PEDICURE = "Педикюр",
    HAIR_CARE = "Уход за волосами"
}

export interface IService {
    id: number;
    name: string;
    description: string;
    duration: number;
    price: number;
    category: ServiceCategory;
}
