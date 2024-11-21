import './styles/App.css'
import {SetStateAction, useEffect, useState} from "react";
import {getServices} from "../entities/Service/api/getServices.ts";
import ServiceList from "../entities/Service/ui/ServiceList/ServiceList.tsx";
import {IService} from "../entities/Service/model/IService.ts";
import ServiceSearch from "../entities/Service/ui/ServiceSearch/ServiceSearch.tsx";
import axios from "axios";

function App() {
  const [services, setServices] = useState<IService[]>([]);

  useEffect(() => {
    const fetchServices = async () => {
      const services = await getServices();
      setServices(services);
    }

    fetchServices();
  }, [])

  const deleteService = async (id: number) => {
    try {
      const response = await axios.delete(
          `http://localhost:8080/services/${id}`,
      );
      console.log("Сервис успешно удален:", response.data);
      setServices((prevState) =>
        prevState.filter((service) => service.id !== id )
      );
    } catch (error) {
      console.error("Ошибка при удалении сервиса:", error);
    }
  };

  const handleSearchResults = (searchedServices: SetStateAction<IService[] | null>) => {
    setServices(searchedServices); // Устанавливаем найденные проекты
  };

  return (
    <div className="app">
      <ServiceSearch onSearch={handleSearchResults} />
      <ServiceList deleteService={deleteService} services={services}  />
    </div>
  )
}

export default App
