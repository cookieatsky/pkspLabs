import {IService} from "../../model/IService.ts";
import ServiceItem from "../ServiceItem/ServiceItem.tsx";

import styles from './ServiceList.module.css'

export interface IServiceListProps {
    services: IService[] | null;
    deleteService: (id: number) => void;
}

const ServiceList = ({services, deleteService}: IServiceListProps) => {
    return (
        <ul className={styles.list}>
            {services && services.map((service: IService) => (
                <ServiceItem key={service.id} service={service} deleteService={deleteService}  />
            ))}
        </ul>
    );
};

export default ServiceList;
