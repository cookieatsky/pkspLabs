import {IService, ServiceCategory} from "../../model/IService.ts";
import {useState} from "react";
import axios from "axios";

export interface IServiceSearchProps {
    onSearch: (services: IService[]) => void;
}

const ServiceSearch = ({onSearch}: IServiceSearchProps) => {

    const [name, setName] = useState("");
    const [category, setCategory] = useState("")

    const handleSearch = async () => {
        try {
            const response = await axios.get(
                "http://localhost:8080/services/search",
                {
                    params: { name, category },
                },
            );
            onSearch(response.data); // Передаем результаты обратно родительскому компоненту
        } catch (error) {
            console.error("Ошибка при поиске проектов:", error);
        }
    };

    return (
        <div>
            <input type="search" placeholder="Write service name..."
                   value={name}
                   onChange={(e) => setName(e.target.value)} />
            <select
                name="category"
                id="category"
                onChange={(e) => setCategory(e.target.value)}
            >
                <option value="">Выбери категорию</option>
                {Object.keys(ServiceCategory).map((key) => (
                    <option key={key} value={key}>{key}</option>
                ))}
            </select>
            <button onClick={handleSearch}>Search</button>
        </div>
    );
};

export default ServiceSearch;
